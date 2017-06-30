package es.gob.afirma.cert.certvalidation;

import java.security.cert.X509Certificate;
import java.util.Properties;

/** Clase que valida los certificados de Defensa.
 * @author Sergio Mart&iacute;nez Rico. */
public class MdefCertificateVerifier extends CertificateVerifier {
    
	Properties p;
	
	@Override
	public ValidationResult verifyRevocation(final X509Certificate cert) {
		return CertificateMdefVerifierFactory.getCertificateVerifier(
				cert, p
			).validateCertificate();
	}

	@Override
	public ValidationResult validateCertificate(final X509Certificate cert) {
		// TODO verificar con servicio web
		return ValidationResult.VALID;
	}

	public void setP(Properties p) {
		this.p = p;
	}


}
