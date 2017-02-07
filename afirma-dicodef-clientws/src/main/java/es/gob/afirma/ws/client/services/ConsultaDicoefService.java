package es.gob.afirma.ws.client.services;

import es.gob.afirma.ws.client.modelo.CertificadoType;
import es.gob.afirma.ws.client.modelo.IdentidadResType;
import es.gob.afirma.ws.client.modelo.IdentidadType;
import es.gob.afirma.ws.client.modelo.ListaUsuarioSistemaType;
import es.gob.afirma.ws.client.modelo.ObjectFactory;
import es.gob.afirma.ws.client.modelo.UsuarioSistemaType;

import javax.xml.bind.JAXBElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Service
public class ConsultaDicoefService {
	@Autowired
	private WebServiceTemplate webServiceTemplate;

	@Value("${ws.consultaDicoef.wsdl}")
	private String consultaDicoefWsdl;

	public ListaUsuarioSistemaType busquedaPersonas(IdentidadResType identidad) {
		try {
			SoapActionCallback callback = new SoapActionCallback(consultaDicoefWsdl);
			Object object = webServiceTemplate
					.marshalSendAndReceive(new ObjectFactory().createBusquedaPersonaRequest(identidad), callback);
			JAXBElement<ListaUsuarioSistemaType> jaxbElement = (JAXBElement<ListaUsuarioSistemaType>) object;
			ListaUsuarioSistemaType response = jaxbElement.getValue();
			return response;
		} catch (Exception e) {
			System.out.println("error" + e);
			return null;
		}
	}

	public IdentidadType consultarIdentidad(UsuarioSistemaType identidad) {
		try {
			SoapActionCallback callback = new SoapActionCallback(consultaDicoefWsdl);
			Object object = webServiceTemplate
					.marshalSendAndReceive(new ObjectFactory().createConsultarIdentidadRequest(identidad), callback);
			JAXBElement<IdentidadType> jaxbElement = (JAXBElement<IdentidadType>) object;
			IdentidadType response = jaxbElement.getValue();
			return response;
		} catch (Exception e) {
			System.out.println("error" + e);
			return null;
		}
	}

	public CertificadoType obtenerCertificado(UsuarioSistemaType identidad) {
		try {
			SoapActionCallback callback = new SoapActionCallback(consultaDicoefWsdl);
			Object object = webServiceTemplate
					.marshalSendAndReceive(new ObjectFactory().createObtenerCertificadoRequest(identidad), callback);
			JAXBElement<CertificadoType> jaxbElement = (JAXBElement<CertificadoType>) object;
			CertificadoType response = jaxbElement.getValue();
			return response;
		} catch (Exception e) {
			System.out.println("error" + e);
			return null;
		}
	}

	public String getConsultaDicoefWsdl() {
		return consultaDicoefWsdl;
	}

	public void setConsultaDicoefWsdl(String consultaDicoefWsdl) {
		this.consultaDicoefWsdl = consultaDicoefWsdl;
	}

}