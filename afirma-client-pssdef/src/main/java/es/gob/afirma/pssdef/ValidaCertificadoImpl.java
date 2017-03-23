package es.gob.afirma.pssdef;

import java.security.cert.X509Certificate;
import java.util.logging.Logger;

import es.gob.afirma.pssdef.config.ConfigPssdef;
import es.gob.afirma.pssdef.config.PssdefConstants;
import es.mdef.PSSDEF.Constants;
import es.mdef.PSSDEF.beans.DatosCertificadosBean;
import es.mdef.PSSDEF.beans.DatosServicioValidacionBean;

public class ValidaCertificadoImpl implements ValidaCertificado {

	private static final Logger LOGGER = Logger.getLogger("es.gob.afirma"); //$NON-NLS-1$
	ConfigPssdef configPssdef = ConfigPssdef.getConfigurador();
	DatosServicioValidacionBean datosServicio = null;

	@Override
	public String validaCert(X509Certificate cert) throws PssdefValidaException {
		try {
			return validaCert(cert.getEncoded());
		} catch (final Exception e) {
			LOGGER.severe("Error en la transformación del certificado a arrayBytes: " + e); 
			 throw new PssdefValidaException("Error en la transformación del certificado a arrayBytes: " + e, e); //$NON-NLS-1$
		}
	}

	@Override
	public String validaCertBase64(String cert) throws PssdefValidaException {
		DatosCertificadosBean datosCertificado = new DatosCertificadosBean();
		try {
			boolean result = configPssdef.getPssdef().ValidateCertBase64(cert, datosCertificado);
			return messageValidateCert(result, datosCertificado);
		} catch (final Exception e) {
			LOGGER.severe("Error al configurar o acceder a los servicios PSSDEF: " + e); 
			 throw new PssdefValidaException("Error al configurar o acceder a los servicios PSSDEF: " + e, e); //$NON-NLS-1$
		}
	}

	@Override
	public String validaCert(byte[] cert) throws PssdefValidaException {
		DatosCertificadosBean datosCertificado = new DatosCertificadosBean();
		try {
			LOGGER.info("comienza el servicio");
			boolean result = configPssdef.getPssdef().ValidateCert(cert, datosCertificado);
			LOGGER.info("finaliza el servicio");
			return messageValidateCert(result, datosCertificado);
		} catch (final Exception e) {
			LOGGER.severe("Error al configurar o acceder a los servicios PSSDEF: " + e); 
			 throw new PssdefValidaException("Error al configurar o acceder a los servicios PSSDEF: " + e, e); //$NON-NLS-1$
		}
	}

	/**
	 * Construye el mensaje de la validaci&oacute;n.
	 * @param validateCert Certificado en formato X509Certificate.
	 * @param cert Certificado en formato X509Certificate.
	 * @return mensaje de la validaci&oacute;n del certificado.
	 */	
	private String messageValidateCert(boolean validateCert, DatosCertificadosBean datosCertificado) {
		String message = "";
		if (validateCert) {
			message = PssdefConstants.certOK + PssdefConstants.confianceDegree + datosCertificado.getNivelConfianza();
		} else {
			switch (datosCertificado.getEstadoCertificado()) {
			case Constants.PSSDEF_CERTIFICATE_STATE_EXPIRE:
				message = PssdefConstants.errorCertExpired;
				break;
			case Constants.PSSDEF_CERTIFICATE_STATE_REVOKED:
				message = PssdefConstants.errorCertRevoked;
				break;
			case Constants.PSSDEF_CERTIFICATE_STATE_UNKNOWN:
				message = PssdefConstants.errorCertUnknown;
				break;
			}
		}
		return message;
	}


}
