package es.gob.afirma.ws.client.services;

import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Clase que inicializa el contexto de spring para la aplicación y utilizar los
 * servicios web.
 * 
 * @author Fernando Hernandez Cebrían.
 */
public class InicializarSpring {

	static AnnotationConfigApplicationContext context;
	static final Logger LOGGER = Logger.getLogger("es.gob.afirma"); //$NON-NLS-1$

	public static ApplicationContext getCtx() {
		if (null == context) {
			try {
				LOGGER.info("Dentro de InicializaSpring busca el contexto para inicializar spring Boot");
		        context = new AnnotationConfigApplicationContext(SoapClientConfig.class);
				LOGGER.info("Ha iniciado el contexto de spring");
			} catch (Exception e) {
				LOGGER.info("no se ha podido inicializar el contexto :"+e);
			}
		}
		return context;
	}

}
