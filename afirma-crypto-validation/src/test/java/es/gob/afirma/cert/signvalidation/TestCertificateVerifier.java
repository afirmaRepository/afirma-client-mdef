package es.gob.afirma.cert.signvalidation;

import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.junit.Assert;
import org.junit.Test;

import es.gob.afirma.cert.certvalidation.OcspCertificateVerifier;
import es.gob.afirma.cert.certvalidation.ValidationResult;
import es.gob.afirma.core.misc.AOUtil;

public class TestCertificateVerifier {

	
	/** Prueba de certificados FNMT Componentes.
	 * @throws Exception En cualquier error. */
	@SuppressWarnings("static-method")
	//@Test
	public void testFnmt() throws Exception {
		final X509Certificate cert = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate( //$NON-NLS-1$
				TestCertValidation.class.getResourceAsStream("/certificadocifradoPKI23.cer") //$NON-NLS-1$
			);		
		OcspCertificateVerifier certificateVerifier = new OcspCertificateVerifier();
		final X509Certificate certCA = certificateVerifier.getCAfromCacert(cert);
		Assert.assertNotNull(certCA);
		Assert.assertEquals(AOUtil.getCN(cert.getIssuerX500Principal().getName()), AOUtil.getCN(certCA));
	}

	/** Prueba de certificados FNMT Componentes.
	 * @throws Exception En cualquier error. */
	@SuppressWarnings("static-method")
	@Test
	public void testFnmt2() throws Exception {
		final X509Certificate cert = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate( //$NON-NLS-1$
				TestCertValidation.class.getResourceAsStream("/certificadocifradoPKI23.cer") //$NON-NLS-1$
			);		
		OcspCertificateVerifier certVerif = new OcspCertificateVerifier();
		certVerif.setValidationProperties(cert);
		certVerif.setSubjectCert(cert);
		 ValidationResult vr = certVerif.validateCertificate();
		 vr.check();
	}

	/** Prueba de certificados FNMT Componentes.
	 * @throws Exception En cualquier error. */
	@SuppressWarnings("static-method")
	//@Test
	public void testFnmt3() throws Exception {
		final X509Certificate cert = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate( //$NON-NLS-1$
				TestCertValidation.class.getResourceAsStream("/cert_test_fnmt.cer") //$NON-NLS-1$
			);		
		OcspCertificateVerifier certVerif = new OcspCertificateVerifier();
		certVerif.setValidationProperties(cert);
		certVerif.setSubjectCert(cert);
		 ValidationResult vr = certVerif.validateCertificate();
		 System.out.println("resultado : "+vr);
		 vr.check();
	}
	
}
