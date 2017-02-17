package es.gob.afirma.ws.client.services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapClientConfig {

	private String consultaDicoefWsdl;
	
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
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

	public String getConsultaDicoefWsdl() {
		return consultaDicoefWsdl;
	}

	public void setConsultaDicoefWsdl(String consultaDicoefWsdl) {
		this.consultaDicoefWsdl = consultaDicoefWsdl;
	}

}