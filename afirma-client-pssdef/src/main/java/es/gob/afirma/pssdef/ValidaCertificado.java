package es.gob.afirma.pssdef;

import java.security.cert.X509Certificate;


/**
 * Interfaz de los servicios de validaci&oacute;n de certificados contra la web de
 * pssdef
 * @author Fernando Hern&aacute;ndez Cebri&aacute;n.
 */
public interface ValidaCertificado {

	/**
	 * accede a los servicios de pssdef.
	 * @param cert Certificado en formato X509Certificate.
	 * @return el resultado de la validación del certificado.
	 * @throws PssdefValidaException Error en el acceso de los servicios .
	 */
	public String validaCert(X509Certificate cert) throws PssdefValidaException;

	/**
	 * accede a los servicios de pssdef.
	 * @param cert Certificado en formato Base64.
	 * @return el resultado de la validación del certificado.
	 * @throws PssdefValidaException Error en el acceso de los servicios .
	 */
	public String validaCertBase64(String cert) throws PssdefValidaException;

	/**
	 * accede a los servicios de pssdef.
	 * @param cert Certificado en formato byte[].
	 * @return el resultado de la validación del certificado.
	 * @throws PssdefValidaException Error en el acceso de los servicios .
	 */
	public String validaCert(byte[] cert) throws PssdefValidaException;;

}
