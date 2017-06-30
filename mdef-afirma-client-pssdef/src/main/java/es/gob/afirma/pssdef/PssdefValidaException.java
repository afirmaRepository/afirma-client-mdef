package es.gob.afirma.pssdef;

/**
 * Error en la gesti&oacute;n de la consulta de los  certificados 
 * del servicio pssdef.  
 * @author Fernando Hern&aacute;ndez Cebri&aacute;n.
 */
public class PssdefValidaException extends Exception {

	private static final long serialVersionUID = -7418222120511264939L;

	/**
	 * Contruye una excepci&oacute;n gen&eacute;rica con mensaje.
	 * 
	 * @param msg
	 *            Mensaje de la excepci&oacute;n
	 */
	public PssdefValidaException(final String msg) {
		super(msg);
	}

	/**
	 * Contruye una excepci&oacute;n gen&eacute;rica con mensaje y define su
	 * causa.
	 * 
	 * @param msg
	 *            Descripci&oacute;n del error.
	 * @param cause
	 *            Causa del error.
	 */
	public PssdefValidaException(final String msg, final Throwable cause) {
		super(msg, cause);
	}

	/**
	 * Contruye una excepci&oacute;n gen&eacute;rica definiendo su causa.
	 * 
	 * @param cause
	 *            Causa del error.
	 */
	public PssdefValidaException(final Throwable cause) {
		super(cause);
	}
}