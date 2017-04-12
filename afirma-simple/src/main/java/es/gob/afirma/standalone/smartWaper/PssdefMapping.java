package es.gob.afirma.standalone.smartWaper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * repositorio de propiedades del archivo PSSDEF.properties
 * @author Fernando Hernández
 *
 */
public class PssdefMapping {
	
	public final static String descPssDefUri = "URL";
	public final static String descKeySubjectName = "KEY_SUBJECT_NAME";
	public final static String descDsvServPolicy = "DSV_SERVICE_POLICY";

    private final static Map<String, String> documentPublicacionMap = prepareDocumentSmartWrapperMap();
    
    private static Map<String, String> prepareDocumentSmartWrapperMap() {
        Map<String, String> LinkedHashMap = new LinkedHashMap<String, String>();

        LinkedHashMap.put("# AUTENTICACIÓN: Usuario y Contraseña ------------------------------------------------------","titulo");
        LinkedHashMap.put("TYPE_OF_AUTH","CERT");
//        LinkedHashMap.put("#TYPE_OF_AUTH","USR_PWD");

        LinkedHashMap.put("# TrustedX VM Ware ------------------------------------------------------","titulo");
//        LinkedHashMap.put("#URL","http://10.7.253.56:2345/trustedx-gw/SoapGateway");
//        LinkedHashMap.put("#URL","https://svcspssdes.mdef.es:8443/trustedx-gw/SoapGateway");
//        LinkedHashMap.put("#URL","https://svcspsspre.mdef.es:8443/trustedx-gw/SoapGateway");
//        LinkedHashMap.put("#URL","https://10.7.253.57:8443/trustedx-gw/SoapGateway");
//        LinkedHashMap.put("#URL","https://10.7.113.137:8443/trustedx-gw/SoapGateway");
//        LinkedHashMap.put("#URL","https://10.71.16.14:8443/trustedx-gw/SoapGateway");
//        LinkedHashMap.put("#URL","https://10.71.17.24:8443/trustedx-gw/SoapGateway");
        LinkedHashMap.put(descPssDefUri,"https://10.71.17.11:8443/trustedx-gw/SoapGateway");
//        LinkedHashMap.put("#URL","https://10.71.16.14:8443/trustedx-gw/SoapGateway");
//        LinkedHashMap.put("#URL","https://10.7.114.206:8443/trustedx-gw/SoapGateway");

        LinkedHashMap.put("USER","");
        LinkedHashMap.put("PWD","");

        LinkedHashMap.put("# Selector de la clave de firma ------------------------------------------------------","titulo");
        LinkedHashMap.put(descKeySubjectName,"CN=AUTOFIRMADEF 001, OID.2.5.4.97=#0C0656415445532D, OU=PSSDEF, OU=GENERICAS, O=MINISTERIO DE DEFENSA, C=ES");

        LinkedHashMap.put("# Perfil de firma ------------------------------------------------------","titulo");
//        LinkedHashMap.put("#DS_PROFILE","wss");

        LinkedHashMap.put("# Perfil de verificacion ------------------------------------------------------","titulo");
//        LinkedHashMap.put("#DSV_PROFILE","wss");

        LinkedHashMap.put("# Sello de tiempo ------------------------------------------------------","titulo");
//        LinkedHashMap.put("#TIME_STAMP","true");
//
//        LinkedHashMap.put("#SIG_PLACE","detached");
//        LinkedHashMap.put("#SIG_PLACE","enveloping");
//        LinkedHashMap.put("#SIG_PLACE","enveloped");

        LinkedHashMap.put("#Forzar política de verificación","titulo");
//        LinkedHashMap.put("#DS_SERVICE_POLICY","urn:pssdef:servicios:politicas:gf-vf:mdef:age");
//        LinkedHashMap.put("#DS_SERVICE_POLICY","urn:pssdef:servicios:politicas:gf-gf:mdef:pro:age");
//        LinkedHashMap.put("#DSV_SERVICE_POLICY","urn:pssdef:servicios:politicas:gf-vf:mdef:long");
        LinkedHashMap.put("DSV_SERVICE_POLICY","urn:pssdef:servicios:politicas:gf-vf:mdef");
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
	
	private static String getDefaultValue(String KeyProp){
		
		return documentPublicacionMap.entrySet().stream()
		  .filter(e -> e.getKey().equals(KeyProp))
		  .map(Map.Entry::getValue)
		  .findFirst()
		  .orElse(null);	
		
	}
    
}
