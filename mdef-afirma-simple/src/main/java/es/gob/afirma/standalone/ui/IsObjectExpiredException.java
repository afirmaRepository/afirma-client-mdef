package es.gob.afirma.standalone.ui;

/** Excepcion que encapsula la excepcion de la tarjetas del ejecito cuando el timepo ha expirado.
 * @author Fernando Hern&aacute;ndez Cebri&aacute;n. */
public class IsObjectExpiredException extends Exception {

	private static final long serialVersionUID = 3756189019018723691L;

	public IsObjectExpiredException(final String message) {
		super(message);
	}

	public IsObjectExpiredException(final Throwable cause) {
		super(cause);
	}

	public IsObjectExpiredException(final Throwable cause, final String message) {
		super(message,cause);
	}
	
}
