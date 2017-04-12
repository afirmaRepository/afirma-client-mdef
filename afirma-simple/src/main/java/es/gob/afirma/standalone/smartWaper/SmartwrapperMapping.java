package es.gob.afirma.standalone.smartWaper;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class SmartwrapperMapping {
	
	
	/**
	 * repositorio de propiedades del archivo smartwrapper.properties
	 * @author Fernando Hernández
	 *
	 */
    private final static Map<String, String> documentPublicacionMap = prepareDocumentSmartWrapperMap();
    
    private static Map<String, String> prepareDocumentSmartWrapperMap() {
        Map<String, String> LinkedHashMap = new LinkedHashMap<String, String>();

        LinkedHashMap.put("Truststore.active","true");
        LinkedHashMap.put("Truststore.path", (ConfigurePssdefPropeties.APPLICATION_HOME_PSSDEF + File.separator + "trustedx-inethandler.truststore").replaceAll("\\\\", "/"));
        LinkedHashMap.put("Truststore.password","trustedx");
        LinkedHashMap.put("Keystore.active","true");
        LinkedHashMap.put("Keystore.path", (ConfigurePssdefPropeties.APPLICATION_HOME_PSSDEF + File.separator + "PSSDEF_Autofirma.jks").replaceAll("\\\\", "/"));
        LinkedHashMap.put("Keystore.password","Autofirma");
        LinkedHashMap.put("Proxy.active","false");
        LinkedHashMap.put("Proxy.host","");
        LinkedHashMap.put("Proxy.port","");
        LinkedHashMap.put("Timeout","295000");
        //se tiene que activar"
        LinkedHashMap.put("req-log.active","false");
        LinkedHashMap.put("req-log.savePath","log/pet");
        //se tiene que activar"
        LinkedHashMap.put("res-log.active","false");
        LinkedHashMap.put("res-log.savePath","log/res");
        LinkedHashMap.put("Request.loadPath","tmp/");
        LinkedHashMap.put("Response.savePath","tmp/");
        LinkedHashMap.put("Ssl.allowCriticalExts","true");
        LinkedHashMap.put("authN.policy","urn:pssdef:servicios:politicas:autenticacion:predeterminada");
        return LinkedHashMap;
    }


    public static Map<String, String> getprepareDocumentPublicacioMap() {
        return documentPublicacionMap;
    }
	
}
