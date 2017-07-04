package es.gob.afirma.standalone.smartWaper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * repositorio de propiedades del archivo PSSDEF.properties
 * @author Fernando Hernández
 *
 */
public class PssdefMapping {

	public final static String descPssDefUri = "URL"; //$NON-NLS-1$
	public final static String descKeySubjectName = "KEY_SUBJECT_NAME"; //$NON-NLS-1$
	public final static String descDsvServPolicy = "DSV_SERVICE_POLICY"; //$NON-NLS-1$

    private final static Map<String, String> documentPublicacionMap = prepareDocumentSmartWrapperMap();

    private static Map<String, String> prepareDocumentSmartWrapperMap() {
        final Map<String, String> LinkedHashMap = new LinkedHashMap<>();

        LinkedHashMap.put("# AUTENTICACION: Usuario y Contrasena ------------------------------------------------------","titulo"); //$NON-NLS-1$ //$NON-NLS-2$
        LinkedHashMap.put("TYPE_OF_AUTH","CERT"); //$NON-NLS-1$ //$NON-NLS-2$
//        LinkedHashMap.put("#TYPE_OF_AUTH","USR_PWD");

        LinkedHashMap.put("# TrustedX VM Ware ------------------------------------------------------","titulo"); //$NON-NLS-1$ //$NON-NLS-2$
//        LinkedHashMap.put("#URL","http://10.7.253.56:2345/trustedx-gw/SoapGateway");
//        LinkedHashMap.put("#URL","https://svcspssdes.mdef.es:8443/trustedx-gw/SoapGateway");
//        LinkedHashMap.put("#URL","https://svcspsspre.mdef.es:8443/trustedx-gw/SoapGateway");
//        LinkedHashMap.put("#URL","https://10.7.253.57:8443/trustedx-gw/SoapGateway");
//        LinkedHashMap.put("#URL","https://10.7.113.137:8443/trustedx-gw/SoapGateway");
//        LinkedHashMap.put("#URL","https://10.71.16.14:8443/trustedx-gw/SoapGateway");
//        LinkedHashMap.put("#URL","https://10.71.17.24:8443/trustedx-gw/SoapGateway");
        LinkedHashMap.put(descPssDefUri,"https://10.71.17.11:8443/trustedx-gw/SoapGateway"); //$NON-NLS-1$
//        LinkedHashMap.put("#URL","https://10.71.16.14:8443/trustedx-gw/SoapGateway");
//        LinkedHashMap.put("#URL","https://10.7.114.206:8443/trustedx-gw/SoapGateway");

        LinkedHashMap.put("USER",""); //$NON-NLS-1$ //$NON-NLS-2$
        LinkedHashMap.put("PWD",""); //$NON-NLS-1$ //$NON-NLS-2$

        LinkedHashMap.put("# Selector de la clave de firma ------------------------------------------------------","titulo"); //$NON-NLS-1$ //$NON-NLS-2$
        LinkedHashMap.put(descKeySubjectName,"CN=AUTOFIRMADEF 001, OID.2.5.4.97=#0C0656415445532D, OU=PSSDEF, OU=GENERICAS, O=MINISTERIO DE DEFENSA, C=ES"); //$NON-NLS-1$

        LinkedHashMap.put("# Perfil de firma ------------------------------------------------------","titulo"); //$NON-NLS-1$ //$NON-NLS-2$
//        LinkedHashMap.put("#DS_PROFILE","wss");

        LinkedHashMap.put("# Perfil de verificacion ------------------------------------------------------","titulo"); //$NON-NLS-1$ //$NON-NLS-2$
//        LinkedHashMap.put("#DSV_PROFILE","wss");

        LinkedHashMap.put("# Sello de tiempo ------------------------------------------------------","titulo"); //$NON-NLS-1$ //$NON-NLS-2$
//        LinkedHashMap.put("#TIME_STAMP","true");
//
//        LinkedHashMap.put("#SIG_PLACE","detached");
//        LinkedHashMap.put("#SIG_PLACE","enveloping");
//        LinkedHashMap.put("#SIG_PLACE","enveloped");

        LinkedHashMap.put("#Forzar política de verificación","titulo"); //$NON-NLS-1$ //$NON-NLS-2$
//        LinkedHashMap.put("#DS_SERVICE_POLICY","urn:pssdef:servicios:politicas:gf-vf:mdef:age");
//        LinkedHashMap.put("#DS_SERVICE_POLICY","urn:pssdef:servicios:politicas:gf-gf:mdef:pro:age");
//        LinkedHashMap.put("#DSV_SERVICE_POLICY","urn:pssdef:servicios:politicas:gf-vf:mdef:long");
        LinkedHashMap.put("DSV_SERVICE_POLICY","urn:pssdef:servicios:politicas:gf-vf:mdef"); //$NON-NLS-1$ //$NON-NLS-2$
//        LinkedHashMap.put("#DSV_SERVICE_POLICY","urn:pssdef:servicios:politicas:gf-vf:afirma:long");
//        LinkedHashMap.put("#DSV_SERVICE_POLICY","urn:pssdef:servicios:politicas:gf-vf:afirma:long");
//        LinkedHashMap.put("#DSV_SERVICE_POLICY","urn:pssdef:servicios:politicas:gf-vf:mdef:pro:age");
//        LinkedHashMap.put("#DSV_SERVICE_POLICY","urn:pssdef:servicios:politicas:gf-vf:mdef:pro");
//        LinkedHashMap.put("#DSV_SERVICE_POLICY","urn:pssdef:servicios:politicas:gf-af:mdef:long");

//        LinkedHashMap.put("#DR_SERVICE_POLICY","urn:pssdef:servicios:politicas:gf-af:dnie:long");
//        LinkedHashMap.put("#DSVCERT_SERVICE_POLICY","   ");

//        LinkedHashMap.put("#DR_SERVICE_POLICY","urn:pssdef:servicios:politicas:gf-af:mdef:long");
//        LinkedHashMap.put("#DR_SERVICE_POLICY","urn:pssdef:servicios:politicas:gf-vf:afirma:long");
//        LinkedHashMap.put("#DR_FILTER_SUBJECT_DN","CN=ALVAREZ TORRELLAS BENIGNO |12766337A, OU=PERSONAS, O=MDEF, C=ES");
//        LinkedHashMap.put("#DR_SIG_UPDATE","ES-T");
        return LinkedHashMap;
    }


    public static Map<String, String> getprepareDocumentPublicacioMap() {
        return documentPublicacionMap;
    }

	public static String getUrlPssdefDefault(){
		return getDefaultValue(descPssDefUri);
	}

	public static String getKeySubjectNameDefault(){
		return getDefaultValue(descKeySubjectName);
	}

	public static String getDsvServPolicyDefault(){
		return  getDefaultValue(descDsvServPolicy);
	}

	private static String getDefaultValue(final String KeyProp){

		return documentPublicacionMap.entrySet().stream()
		  .filter(e -> e.getKey().equals(KeyProp))
		  .map(Map.Entry::getValue)
		  .findFirst()
		  .orElse(null);

	}

}
