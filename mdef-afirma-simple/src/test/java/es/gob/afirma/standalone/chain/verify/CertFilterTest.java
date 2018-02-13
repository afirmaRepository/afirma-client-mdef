package es.gob.afirma.standalone.chain.verify;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import es.gob.afirma.keystores.AOCertificatesNotFoundException;
import es.gob.afirma.keystores.AOKeyStore;
import es.gob.afirma.keystores.AOKeyStoreDialog;
import es.gob.afirma.keystores.AOKeyStoreManager;
import es.gob.afirma.keystores.AOKeyStoreManagerException;
import es.gob.afirma.keystores.AOKeyStoreManagerFactory;
import es.gob.afirma.keystores.AOKeystoreAlternativeException;
import es.gob.afirma.keystores.filters.CertificateFilter;
import es.gob.afirma.keystores.filters.MultipleCertificateFilter;
import es.gob.afirma.keystores.filters.TextContainedCertificateFilter;

public class CertFilterTest {

	//private static final String CERT_FILE = "src/test/resources/chain/firmaMMarPrueba170118.cer";
	private static final String CERT_FILE = "src/test/resources/chain/google.com.cer";	
	//private static final String CERT_FILE = "src/test/resources/chain/MMarPrueba080218.cer";
	
//	public static void main(final String[] args) throws Exception {
//		recuperaClavePrivada(getCertFilters());
//	}
	
	@SuppressWarnings("static-method")
	@Test
	public void testFirmaXAdESJMulticardSignature() throws Exception {
		recuperaClavePrivada(getCertFilters());
	}

		
	private List<? extends CertificateFilter> getCertFilters() {
		final List<CertificateFilter> filters = new ArrayList<>();
		//filters.add(new KeyUsageFilter(KeyUsageFilter.SIGN_CERT_USAGE));
		//filters.add(new PseudonymFilter());
        FileInputStream fin = null;
        FileInputStream finCertSource = null;
    	try {
			finCertSource = new FileInputStream(CERT_FILE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        X509Certificate validCer = recuperaCertificado (finCertSource);
		//String[] paramArrayOfString1 = {validCer.getSubjectX500Principal().toString()};
        String[] paramArrayOfString1 = null;
		String[] paramArrayOfString2 = {validCer.getIssuerX500Principal().toString()};
		filters.add(new TextContainedCertificateFilter(paramArrayOfString1, paramArrayOfString2));
		if (filters.size() > 1) {
			return Arrays.asList(
				new MultipleCertificateFilter(filters.toArray(new CertificateFilter[0]))
			);
		}
		else if (filters.size() == 1) {
			return filters;
		}
		return null;
	}	
	
	private X509Certificate recuperaCertificado(FileInputStream file){
		try {
			return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(file);
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		
	}    
 	
	
	public PrivateKeyEntry recuperaClavePrivada(List<? extends CertificateFilter> filters)
			throws UnrecoverableEntryException, AOCertificatesNotFoundException, AOKeyStoreManagerException,
			KeyStoreException, NoSuchAlgorithmException, AOKeystoreAlternativeException, IOException {
		final AOKeyStore ks = AOKeyStore.TEMD;
		AOKeyStoreManager ksm;
		System.out.println();
			ksm = AOKeyStoreManagerFactory.getAOKeyStoreManager(
					ks,
					null,
					null,
					ks.getStorePasswordCallback(null),
					//pc,
					null);
		final AOKeyStoreDialog dialog = new AOKeyStoreDialog(
				ksm,
				null,
				true,             // Comprobar claves privadas
				false,            // Mostrar certificados caducados
				true,             // Comprobar validez temporal del certificado
				filters, 				// Filtros
				false             // mandatoryCertificate
			);
    	dialog.show();
    	ksm.setParentComponent(null);
    	return ksm.getKeyEntry(
			dialog.getSelectedAlias()
		);
	}	
}


