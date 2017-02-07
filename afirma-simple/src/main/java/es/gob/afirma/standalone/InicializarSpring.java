package es.gob.afirma.standalone;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/** Clase que inicializa el contexto de spring para la aplicación y utilizar los servicios web.
 * @author Fernando Hernandez Cebrían. */
public class InicializarSpring {

	static ApplicationContext ctx;

	public static ApplicationContext getCtx() {
		if(null == ctx){
			ctx = new ClassPathXmlApplicationContext("classpath*:**/applicationContext-ws-client*.xml");
		}
		return ctx;
	}
	
}
