package es.gob.afirma.ws.client.services;

import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/** Inicializa el contexto de spring para la aplicaci&oacute;n y utilizar los
 * servicios web.
 * @author Fernando Hern&aacute;ndez Cebri&aacute;n. */
public class InicializarSpring {

	static AnnotationConfigApplicationContext context;
	static final Logger LOGGER = Logger.getLogger("es.gob.afirma"); //$NON-NLS-1$

	public static ApplicationContext getCtx() {
		if (null == context) {
			try {
				LOGGER.info("Dentro de InicializaSpring busca el contexto para inicializar spring Boot");
		        context = new AnnotationConfigApplicationContext(SoapClientConfig.class);
				LOGGER.info("Ha iniciado el contexto de spring");
			} catch (final Exception e) {
				LOGGER.severe("no se ha podido inicializar el contexto :"+e);
			}
		}
		return context;
	}

}
