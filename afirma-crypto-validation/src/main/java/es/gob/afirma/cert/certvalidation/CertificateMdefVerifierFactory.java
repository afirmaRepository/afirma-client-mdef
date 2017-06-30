/* Copyright (C) 2011 [Gobierno de Espana]
 * This file is part of "Cliente @Firma".
 * "Cliente @Firma" is free software; you can redistribute it and/or modify it under the terms of:
 *   - the GNU General Public License as published by the Free Software Foundation;
 *     either version 2 of the License, or (at your option) any later version.
 *   - or The European Software License; either version 1.1 or (at your option) any later version.
 * Date: 11/01/11
 * You may contact the copyright holder at: soporte.afirma5@mpt.es
 */

package es.gob.afirma.cert.certvalidation;

import java.security.cert.X509Certificate;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.zip.CRC32;


/** Factor&iacute;a para la obtenci&oacute;n de un validador de certificados.
 * Clase cedida por <a href="http://www.yohago.com/">YoHago</a>.
 * @author Tom&aacute;s Garc&iacute;a-Mer&aacute;s */
public final class CertificateMdefVerifierFactory {

	private static final Logger LOGGER = Logger.getLogger("es.gob.afirma"); //$NON-NLS-1$

	public static final String OCSCP = "es.gob.afirma.cert.certvalidation.OcspCertificateVerifier"; //$NON-NLS-1$

	public static final String CRL = "es.gob.afirma.cert.certvalidation.CrlCertificateVerifier"; //$NON-NLS-1$
	
	private static String validationClass = "es.gob.afirma.cert.certvalidation.OcspCertificateVerifier";;
	
	private CertificateMdefVerifierFactory() {
		// No permitimos la instanciacion
	}


	/** Obtiene un validador para el certificado proporcionado.
	 * @param cert Certificado a validar.
	 * @return Validador para el certificado proporcionado. */
	public static CertificateVerificable getCertificateVerifier(final X509Certificate cert, Properties p) {

		try {
			final Class<?> certVerifierClass = Class.forName(validationClass);
			final CertificateVerificable certVerif = (CertificateVerificable) certVerifierClass.getConstructor().newInstance();
			certVerif.setOcspUrlPreference(null);
			if(null!= p.getProperty("responderUrlOcsp") && !p.getProperty("responderUrlOcsp").isEmpty()){
				certVerif.setOcspUrlPreference(p.getProperty("responderUrlOcsp"));
			}
			certVerif.setValidationProperties(cert);
			certVerif.setSubjectCert(cert);
			return certVerif;
		}
		catch (final ClassNotFoundException e) {
			LOGGER.warning("No se encuentran la clase validadora " + validationClass + ": " + e.toString()); //$NON-NLS-1$ //$NON-NLS-2$
		}
		catch (final Exception e) {
			LOGGER.warning("No se ha podido instanciar el verificador del certificado " + validationClass + ": " + e); //$NON-NLS-1$ //$NON-NLS-2$
		}

		throw new IllegalStateException(
			"No se soporta el medio de validacion: " + validationClass //$NON-NLS-1$
		);
	}

	public static void setValidationClass(String validationClass) {
		CertificateMdefVerifierFactory.validationClass = validationClass;
	}
	
	
}
