package es.gob.afirma.standalone.ui.envelopes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.util.Enumeration;
import java.util.zip.DataFormatException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Assert;
import org.junit.Test;

import es.gob.afirma.core.AOException;
import es.gob.afirma.core.ciphers.AOCipherConfig;
import es.gob.afirma.core.ciphers.CipherConstants.AOCipherAlgorithm;
import es.gob.afirma.core.ciphers.CipherConstants.AOCipherBlockMode;
import es.gob.afirma.core.ciphers.CipherConstants.AOCipherPadding;
import es.gob.afirma.envelopers.cms.AOCMSEnveloper;
//import es.gob.afirma.envelopers.cms.Pkcs11WrapOperationException;

/** Prueba simple de firma con PKCS#11.
 * @author Tom&aacute;s Garc&iacute;a-Mer&aacute;s */
public final class TestPkcs11FMNT {

	private static final String LIB_NAME = "C:\\WINDOWS\\system32\\FNMT_P11_x64.dll"; //$NON-NLS-1$
	private static final char[] PIN = "A111111a".toCharArray(); //$NON-NLS-1$

	/** Prueba de firma con PKCS#11.
	 * @throws Exception En cualquier error. */

	/** Prueba de firma con PKCS#11 usando directamente JRE.
	 * @throws Exception En cualquier error. */
	@SuppressWarnings("static-method")
	@Test
	//@Ignore // Dependiente del PKCS#11
	public void testRawPkcs11() throws Exception {

        final Constructor<?> sunPKCS11Contructor = Class.forName("sun.security.pkcs11.SunPKCS11").getConstructor(InputStream.class); //$NON-NLS-1$
        final Provider p = (Provider) sunPKCS11Contructor.newInstance(
    		new ByteArrayInputStream((
				"name=pkcs11-win_dll\n" + //$NON-NLS-1$
				"library=" + LIB_NAME + "\n" + //$NON-NLS-1$ //$NON-NLS-2$
				"showInfo=false" //$NON-NLS-1$
			).getBytes())
		);

		Security.addProvider(p);

		final KeyStore ks = KeyStore.getInstance("PKCS11"); //$NON-NLS-1$
		ks.load(null, PIN);
		final Enumeration<String> aliases = ks.aliases();
		envelopeRecoverdData(aliases, ks);
	}
	
	private static void envelopeRecoverdData(Enumeration<String> aliases, KeyStore ks) throws KeyStoreException, CertificateEncodingException, 
			InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, 
			IllegalBlockSizeException, BadPaddingException, IOException, UnrecoverableEntryException, InvalidKeySpecException, 
			//Pkcs11WrapOperationException, 
			AOException, DataFormatException{
		final byte[] content ="Hola mundo".getBytes(); //$NON-NLS-1$
		
		String selectedAlias = null;
		X509Certificate dest = null;

		while (aliases.hasMoreElements()) {
			final String alias = aliases.nextElement();
			final X509Certificate tmpCer = (X509Certificate)ks.getCertificate(alias);
			if (
				//tmpCer.getIssuerX500Principal().toString().contains("O=MDEF") && //$NON-NLS-1$
				tmpCer.getIssuerX500Principal().toString().contains("O=MINISTERIO DE DEFENSA") &&
				isCipherCert(tmpCer.getKeyUsage())
			) {
				selectedAlias = alias;
				dest = tmpCer;
			}
		}

		Assert.assertNotNull(dest);
		Assert.assertNotNull(selectedAlias);

		// Destinatario del sobre

		final byte[] envelope = new AOCMSEnveloper().createCMSEnvelopedData(
			content,
			null,
			new AOCipherConfig(
				AOCipherAlgorithm.AES,
				AOCipherBlockMode.ECB,
				AOCipherPadding.PKCS5PADDING
			),
			new X509Certificate[] { dest },
			Integer.valueOf(128) // <- CAMBIAR A 256 SI SE TIENE DESACTIVADA LA RESTRICCION EN EL JRE
		);

		final PrivateKeyEntry pke = (PrivateKeyEntry) ks.getEntry(
			selectedAlias,
			new KeyStore.PasswordProtection("A111111a".toCharArray()) //$NON-NLS-1$
		);

		// Comprobacion de que se esta intentando usar el certificado bueno
		Assert.assertEquals(
			"Prueba mal concebida, se esta intentando abrir el sobre con un certificado que no es destinatario", //$NON-NLS-1$
			dest.getSerialNumber().toString(),
			((X509Certificate)pke.getCertificate()).getSerialNumber().toString()
		);

		// Y comprobacion de que abre bien
		final byte[] recoveredData = new AOCMSEnveloper().recoverData(envelope, pke);
		Assert.assertEquals(
			"El contenido desensobrado no coincide con el ensobrado", //$NON-NLS-1$
			new String(content),
			new String(recoveredData)
		);

		System.out.println(new String(recoveredData));
		
		
		
	}
	
	private static boolean isCipherCert(final boolean[] keyUsage) {
		return keyUsage != null     &&
			   keyUsage.length == 9 &&
			   keyUsage[2]          &&
			   keyUsage[3];
	}	


}
