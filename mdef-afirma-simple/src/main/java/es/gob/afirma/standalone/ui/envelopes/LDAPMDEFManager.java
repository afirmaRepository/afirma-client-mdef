package es.gob.afirma.standalone.ui.envelopes;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.gob.afirma.standalone.ui.preferences.PreferencesManager;
import es.gob.afirma.ws.client.modelo.IdentidadResType;
import es.gob.afirma.ws.client.modelo.UsuarioSistemaType;
import es.gob.afirma.ws.client.services.DicodefClientWs;
import es.gob.afirma.ws.client.services.InicializarSpring;

final class LDAPMDEFManager implements CertificateDirectoryProvider {

	// variable para consultar los servicios web de dicoef
	private final DicodefClientWs client;

    /** Inicio (en min&uacute;sculas) de una ruta que invoca a la aplicaci&oacute;n por protocolo. */
    private static final String URL_DICODEF = "http://localhost:8080/HelloService/ConsultarDicodefImplDummy?wsdl"; //$NON-NLS-1$

	static final Logger LOGGER = Logger.getLogger("es.gob.afirma"); //$NON-NLS-1$

	LDAPMDEFManager() {
		LOGGER.info("Se inicia la carga del contexto de spring");
		// Se iniciacila el contexto de spring
		this.client = (DicodefClientWs) InicializarSpring.getCtx().getBean(DicodefClientWs.class);
		LOGGER.info("Se acaba la carga del contexto de spring");
		//esto sera borrado y cambiado por las opciones de preferencias
        this.client.setDefaultUri(PreferencesManager.get(PreferencesManager.PREFERENCE_URL_WEB_SERVICES_DICODEF, URL_DICODEF));

		//consultaDicoefService.setConsultaDicoefWsdl(PreferencesManager.get(PREFERENCE_URL_WEB_SERVICES_DICODEF, "https://www.google.es"));
	}

	@Override
	public Users[] getUsers(final String substring) {
		// se utiliza la entidad de entrada creada en el xsd del servicio web
		final IdentidadResType identidad = new IdentidadResType();
		// se llama al servcio web de la búsqueda de personas en el ldap
//		ListaUsuarioSistemaType listaUsuarioSistema = client.busquedaPersonas(identidad);
		final List<Users> users = new ArrayList<>();
//		for (UsuarioSistemaExtendType usuarioSistemaExtend : listaUsuarioSistema.getUsuarioSistemaExtend()) {
//			users.add(new Users(usuarioSistemaExtend.getCN(), usuarioSistemaExtend.getMail(),
//					usuarioSistemaExtend.getUid())); // $NON-NLS-1$
//		}
		users.add(new Users("cn1", "prueba@prueba1.com",
				"ui1")); // $NON-NLS-1$
		users.add(new Users("cn2", "prueba@prueba2.com",
				"ui2")); // $NON-NLS-1$

		return users.toArray(new Users[0]);
	}

	@Override
	public X509Certificate getCertificate(final String uid) throws IOException, CertificateException {
		final UsuarioSistemaType identidad = new UsuarioSistemaType();
		identidad.setUid(uid);
		InputStream inputstream;
		String path;
		// como prueba discriminamos de forma provisional entre el certificado que recuperamos del fileSystem local
		if (uid.equals("uid")) { //$NON-NLS-1$
			final String basePath = new File("").getAbsolutePath();
			path = new File("src/main/resources/testCer-DER.cer").getAbsolutePath();
			inputstream = new FileInputStream(path);
		// y el servicio web de dicodef para obtener certificados
		} else {
			inputstream = new ByteArrayInputStream(
					this.client.obtenerCertificado(identidad).getCertificado());
		}
		final CertificateFactory cf = CertificateFactory.getInstance("X.509"); //$NON-NLS-1$
		return (X509Certificate) cf.generateCertificate(inputstream);
	}

}
