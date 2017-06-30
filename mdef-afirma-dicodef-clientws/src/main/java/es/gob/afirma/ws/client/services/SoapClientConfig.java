package es.gob.afirma.ws.client.services;

import org.apache.ws.security.WSConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.soap.security.wss4j.Wss4jSecurityInterceptor;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

@Configuration
public class SoapClientConfig {

	private String consultaDicoefWsdl;
	
    //para configurar la seguridad
    @Bean
    public Wss4jSecurityInterceptor securityInterceptor(){
        System.out.println("usuario wss : " + ConfigParameters.getUSER());
        System.out.println("password  wss: " + ConfigParameters.getPASSWORD());
        System.out.println("url wss : " + ConfigParameters.getURL());
    	
        Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
        //wss4jSecurityInterceptor.setSecurementPassword(WSConstants.PW_TEXT);
        wss4jSecurityInterceptor.setSecurementPasswordType(WSConstants.PW_TEXT);
        wss4jSecurityInterceptor.setSecurementActions("UsernameToken");
        wss4jSecurityInterceptor.setSecurementUsername(ConfigParameters.getUSER());
        wss4jSecurityInterceptor.setSecurementPassword(ConfigParameters.getPASSWORD());
        return wss4jSecurityInterceptor;
    }	
    //para configurar la seguridad
	
	
	@Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("es.gob.afirma.ws.client.modelo");
        return marshaller;
    }

    @Bean
    public DicodefClientWs dicodefClient(Jaxb2Marshaller marshaller) {
    	DicodefClientWs client = new DicodefClientWs();
        //client.setDefaultUri("http://localhost:8080/HelloService/ConsultarDicodefImplDummy?wsdl");
    	client.setDefaultUri(ConfigParameters.getURL());
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        
        //para configurar la seguridad

         ClientInterceptor[] interceptors = new ClientInterceptor[] {securityInterceptor()};
        client.setInterceptors(interceptors);
        
        //para configurar la seguridad
        
        return client;
    }

	public String getConsultaDicoefWsdl() {
		return consultaDicoefWsdl;
	}

	public void setConsultaDicoefWsdl(String consultaDicoefWsdl) {
		this.consultaDicoefWsdl = consultaDicoefWsdl;
	}

}