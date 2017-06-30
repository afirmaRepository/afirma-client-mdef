package es.gob.afirma.cert.signvalidation;

import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.junit.Test;

import es.gob.afirma.cert.certvalidation.MdefCertificateVerifier;

public class TestMdefCertificateVerifier {
	
	/** Prueba de certificados FNMT Componentes.
	 * @throws Exception En cualquier error. */
	@SuppressWarnings("static-method")
	@Test
	public void testFnmt2() throws Exception {
		final X509Certificate cert = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate( //$NON-NLS-1$
				//TestCertValidation.class.getResourceAsStream("/certificadocifradoPKI23.cer") //$NON-NLS-1$
				TestCertValidation.class.getResourceAsStream("/cert_test_fnmt.cer") //$NON-NLS-1$
			);		
		MdefCertificateVerifier mdefCertificateVerifier = new MdefCertificateVerifier();
		mdefCertificateVerifier.verifyRevocation(cert).check(); 
	}	

}
