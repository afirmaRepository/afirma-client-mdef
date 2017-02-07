package es.gob.afirma.standalone.ui.envelopes;

import static es.gob.afirma.standalone.ui.preferences.PreferencesManager.PREFERENCE_URL_WEB_SERVICES_DICODEF;

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

import es.gob.afirma.standalone.InicializarSpring;
import es.gob.afirma.standalone.ui.preferences.PreferencesManager;
import es.gob.afirma.ws.client.modelo.IdentidadResType;
import es.gob.afirma.ws.client.modelo.ListaUsuarioSistemaType;
import es.gob.afirma.ws.client.modelo.UsuarioSistemaExtendType;
import es.gob.afirma.ws.client.modelo.UsuarioSistemaType;
import es.gob.afirma.ws.client.services.ConsultaDicoefService;

final class LDAPMDEFManager implements CertificateDirectoryProvider {

	// variable para consultar los servicios web de dicoef
	private ConsultaDicoefService consultaDicoefService;

	LDAPMDEFManager() {
		// Se iniciacila el contexto de spring
		consultaDicoefService = (ConsultaDicoefService) InicializarSpring.getCtx().getBean("consultaDicoefService");
		//esto será borrado y cambiado por las opciones de preferencias
		consultaDicoefService.setConsultaDicoefWsdl(PreferencesManager.get(PREFERENCE_URL_WEB_SERVICES_DICODEF, "https://www.google.es"));
	}

	@Override
	public Users[] getUsers(final String substring) {
		// se utiliza la entidad de entrada creada en el xsd del servicio web
		IdentidadResType identidad = new IdentidadResType();
		// se llama al servcio web de la búsqueda de personas en el ldap
		ListaUsuarioSistemaType listaUsuarioSistema = consultaDicoefService.busquedaPersonas(identidad);
		final List<Users> users = new ArrayList<>();
		for (UsuarioSistemaExtendType usuarioSistemaExtend : listaUsuarioSistema.getUsuarioSistemaExtend()) {
			users.add(new Users(usuarioSistemaExtend.getCN(), usuarioSistemaExtend.getMail(),
					usuarioSistemaExtend.getUid())); // $NON-NLS-1$
		}
		return users.toArray(new Users[0]);
	}

	@Override
	public X509Certificate getCertificate(final String uid) throws IOException, CertificateException {
		UsuarioSistemaType identidad = new UsuarioSistemaType();
		identidad.setUid(uid);
		InputStream inputstream;
		String path;
		// como prueba discriminamos de forma provisional entre el certificado que recuperamos del fileSystem local 
		if (uid.equals("uid")) { //$NON-NLS-1$
			String basePath = new File("").getAbsolutePath();
			path = new File("src/main/resources/testCer-DER.cer").getAbsolutePath();
			inputstream = new FileInputStream(path);
		// y el servicio web de dicodef para obtener certificados
		} else {
			inputstream = new ByteArrayInputStream(
					consultaDicoefService.obtenerCertificado(identidad).getCertificado());
		}
		final CertificateFactory cf = CertificateFactory.getInstance("X.509"); //$NON-NLS-1$
		return (X509Certificate) cf.generateCertificate(inputstream);
	}

}
