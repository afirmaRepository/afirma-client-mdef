package es.gob.afirma.ws.client.services;

import javax.xml.bind.JAXBElement;

import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import es.gob.afirma.ws.client.modelo.CertificadoType;
import es.gob.afirma.ws.client.modelo.IdentidadResType;
import es.gob.afirma.ws.client.modelo.IdentidadType;
import es.gob.afirma.ws.client.modelo.ListaUsuarioSistemaType;
import es.gob.afirma.ws.client.modelo.ObjectFactory;
import es.gob.afirma.ws.client.modelo.UsuarioSistemaType;

@Service
public class DicodefClientWs extends WebServiceGatewaySupport {

	@SuppressWarnings("unchecked")
	public ListaUsuarioSistemaType busquedaPersonas(IdentidadResType identidad) {
		return ((JAXBElement<ListaUsuarioSistemaType>) getWebServiceTemplate()
				.marshalSendAndReceive(new ObjectFactory().
						createBusquedaPersonaRequest(identidad))).getValue();
	}
	
    @SuppressWarnings("unchecked")
	public IdentidadType consultarIdentidad(UsuarioSistemaType identidad) {
    	return ((JAXBElement<IdentidadType>)getWebServiceTemplate()
    			.marshalSendAndReceive(new ObjectFactory().
    					createConsultarIdentidadRequest(identidad))).getValue();
    }
    
    @SuppressWarnings("unchecked")
	public CertificadoType obtenerCertificado(UsuarioSistemaType identidad) {
        return ((JAXBElement<CertificadoType>)getWebServiceTemplate()
    			.marshalSendAndReceive(new ObjectFactory().
    					createObtenerCertificadoRequest(identidad))).getValue();
    }    
	
	
	

}
