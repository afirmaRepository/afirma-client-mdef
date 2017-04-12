package es.gob.afirma.ws.client.services;

import java.net.URISyntaxException;
import java.util.logging.Logger;

import javax.xml.bind.JAXBElement;

import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import es.gob.afirma.ws.client.modelo.CertificadoType;
import es.gob.afirma.ws.client.modelo.IdentidadType;
import es.gob.afirma.ws.client.modelo.ObjectFactory;
import es.gob.afirma.ws.client.modelo.UsuarioSistemaMailType;
import es.gob.afirma.ws.client.modelo.UsuarioSistemaType;

@Service
public class DicodefClientWs extends WebServiceGatewaySupport {

	private static final Logger LOGGER = Logger.getLogger("es.gob.afirma"); //$NON-NLS-1$

//	@SuppressWarnings("unchecked")
//	public ListaUsuarioSistemaType busquedaPersonas(IdentidadResType identidad) {
//		return ((JAXBElement<ListaUsuarioSistemaType>) getWebServiceTemplate()
//				.marshalSendAndReceive(new ObjectFactory().
//						createBusquedaPersonaRequest(identidad))).getValue();
//	}
	
//    @SuppressWarnings("unchecked")
//	public IdentidadType consultarIdentidad(UsuarioSistemaType identidad) {
//    	return ((JAXBElement<IdentidadType>)getWebServiceTemplate()
//    			.marshalSendAndReceive(new ObjectFactory().
//    					createConsultarIdentidadRequest(identidad))).getValue();
//    }
	
    @SuppressWarnings("unchecked")
	public IdentidadType consultarIdentidad(UsuarioSistemaMailType identidad) throws URISyntaxException, Exception {
    	
		LOGGER.info("Direccion jre: " + System.getProperty("java.class.path")); //$NON-NLS-1$ //$NON-NLS-2$
		LOGGER.info("Direccion jhome: " + System.getProperty("java.home")); //$NON-NLS-1$ //$NON-NLS-2$
    	
         Object object = getWebServiceTemplate()
         		.marshalSendAndReceive(new ObjectFactory()
         				.createConsultarIdentidadRequest(identidad),new SoapActionCallback("/MINISDEF/DICODEF/Servicios/ConsultarDICODEF/ConsultarDICODEF.serviceagent/ConsultarDICODEFEndpoint1/ConsultarIdentidad"));
    	 JAXBElement<IdentidadType> jaxbElement = (JAXBElement<IdentidadType>) object;
        IdentidadType response = jaxbElement.getValue();    
        return response;
    }
	
    
    @SuppressWarnings("unchecked")
	public CertificadoType obtenerCertificado(UsuarioSistemaType identidad) {
        return ((JAXBElement<CertificadoType>)getWebServiceTemplate()
    			.marshalSendAndReceive(new ObjectFactory().
    					createObtenerCertificadoRequest(identidad))).getValue();
    }    
}
