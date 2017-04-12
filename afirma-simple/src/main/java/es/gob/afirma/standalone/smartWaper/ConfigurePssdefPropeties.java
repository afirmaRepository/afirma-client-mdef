package es.gob.afirma.standalone.smartWaper;

import static es.gob.afirma.standalone.ui.preferences.PreferencesManager.PREFERENCE_SEVICE_POLICE_PSSDEF_SERVICE;
import static es.gob.afirma.standalone.ui.preferences.PreferencesManager.PREFERENCE_KEY_SUBJET_PSSDEF_SERVICE;
import static es.gob.afirma.standalone.ui.preferences.PreferencesManager.PREFERENCE_URI_PSSDEF_SERVICE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.logging.Logger;

import es.gob.afirma.core.misc.Platform;
import es.gob.afirma.pssdef.config.ConfigPssdef;
import es.gob.afirma.standalone.ui.preferences.PreferencesManager;


/** clase que crea el entorno necesario para acceder a los servicios de PSSDEF.
 * @author Fernando Hern&aacute;ndez Cebri&aacute;n. */
public class ConfigurePssdefPropeties {

	static final Logger LOGGER = Logger.getLogger("es.gob.afirma"); //$NON-NLS-1$

	public static final String APPLICATION_HOME_PSSDEF = Platform.getUserHome() + File.separator + ".afirma"
			+ File.separator + "AutoFirma" + File.separator + "PssdefService"; //$NON-NLS-1$ //$NON-NLS-2$

	private static ConfigurePssdefPropeties configurePssdefPropeties;

	public static void inizializerProperties() {

		if (configurePssdefPropeties == null) {

			configurePssdefPropeties = new ConfigurePssdefPropeties();
		}
	}
	

	public static void chagePssdefProperties() {

		if(!configurePssdefPropeties.checkValuesPssdefProperties()){
			try {
				configurePssdefPropeties.writePssdefProperties("PSSDEF.properties");
			} catch (IOException e) {
				LOGGER.severe("Error en el fichero pssdef " + e.getMessage());
			}
		}
	}
	
	
	/** Inicia todos los procesos necesarios para construir 
	 * el entorno necesario para los servicios PSSDEF.
	 */
	private ConfigurePssdefPropeties() {
		try {
			createDirectory();
			if (!fileExit("trustedx-inethandler.truststore")) {
				copycertPssdef("trustedx-inethandler.truststore");
			}
			if (!fileExit("PSSDEF_Autofirma.crt")) {
				copycertPssdef("PSSDEF_Autofirma.crt");
			}
			if (!fileExit("PSSDEF_Autofirma.jks")) {
				copycertPssdef("PSSDEF_Autofirma.jks");
			}
			if (!fileExit("smartwrapper.properties")) {
				writeSmartWapper("smartwrapper.properties");
			}
			if (!fileExit("PSSDEF.properties") || !checkValuesPssdefProperties()) {
				writePssdefProperties("PSSDEF.properties");
			}
			mappingToSystemProperties();
		} catch (IOException e) {
			LOGGER.severe("Error en los ficheros" + e.getMessage());
		}

	}

	/** Indica si el existe el fichero necesario para el servicios 
	 * @param filePath Ruta del fichero.
	 * @return devuelve true si existe .
	 */
	private boolean fileExit(String file) {
		boolean result = false;
		File filePro = new File(APPLICATION_HOME_PSSDEF + File.separator + file);
		if (filePro.exists()) {
			result = true;
		}
		return result;
	}

	/** construye el archivo de propiedades de smartwraper y lo 
	 * deposita en el directorio necesario para los pssdef 
	 * @param filePath Ruta del fichero.
	 * @throws IOException Error al construir el documento.
	 */
	public void writeSmartWapper(String propertyFile) throws IOException {

		FileWriter fstream;
		BufferedWriter out;

		fstream = new FileWriter(APPLICATION_HOME_PSSDEF + File.separator + propertyFile);
		out = new BufferedWriter(fstream);

		Map<String, String> doc = SmartwrapperMapping.getprepareDocumentPublicacioMap();
		for (Entry<String, String> entrada : doc.entrySet()) {
			out.write(entrada.getKey().concat(" = ").concat(entrada.getValue()) + "\n");
		}
		out.close();
	}

	/** construye el archivo de propiedades de pssdef y lo 
	 * deposita en el directorio necesario para los pssdef 
	 * @param filePath Ruta del fichero.
	 * @throws IOException Error al construir el documento.
	 */	
	public void writePssdefProperties(String propertyFile) throws IOException {

		FileWriter fstream;
		BufferedWriter out;

		fstream = new FileWriter(APPLICATION_HOME_PSSDEF + File.separator + propertyFile);
		out = new BufferedWriter(fstream);

		Map<String, String> doc = PssdefMapping.getprepareDocumentPublicacioMap();
		for (Entry<String, String> entrada : doc.entrySet()) {
			checksValuesRegistry(entrada);
			out.write(entrada.getKey().concat(" = ").concat(entrada.getValue()) + "\n");
		}
		out.close();
	}

	/** copia los certificados necesarios para emplear los servicios 
	 * @param filePath Ruta del fichero.
	 * @throws IOException Error al construir el documento.
	 */	
	public void copycertPssdef(String file) throws IOException {
		InputStream is = getClass().getResourceAsStream("/" + file);

		OutputStream os = new FileOutputStream(APPLICATION_HOME_PSSDEF + File.separator + file);

		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = is.read(buffer)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		is.close();
		os.flush();
		os.close();
	}

	/** crea el directorio para depositar los ficheros
	 */
	private void createDirectory() {
		File targetFile = new File(APPLICATION_HOME_PSSDEF);
		boolean result = false;
		if (!targetFile.exists()) {
			targetFile.mkdir();
			result = true;
		}

	}

	/** incluye las variables del sistema para que sean recuperadas por las 
	 * clases que gestionan el servicio 
	 */
	private void mappingToSystemProperties() {
		System.setProperty("smartwrapper.propertiesFile",
				APPLICATION_HOME_PSSDEF + File.separator + "smartwrapper.properties");
		System.setProperty("pssdf.propertiesFile", APPLICATION_HOME_PSSDEF + File.separator + "PSSDEF.properties");

	}

	/** Cambia los valores de pssdef si hay contenido en las variables de  
	 * entorno del registro del sistema  
	 */
	private void checksValuesRegistry(Entry<String, String> entrada) {
		String uri = PreferencesManager.get(PREFERENCE_URI_PSSDEF_SERVICE, "");
		String keySubjetName = PreferencesManager.get(PREFERENCE_KEY_SUBJET_PSSDEF_SERVICE, "");
		String servicePolice = PreferencesManager.get(PREFERENCE_SEVICE_POLICE_PSSDEF_SERVICE, "");
		if(entrada.getKey().equals(PssdefMapping.descPssDefUri)){
			if(!uri.isEmpty()){
				entrada.setValue(uri);
			}			
		}
		if(entrada.getKey().equals(PssdefMapping.descKeySubjectName)){
			if(!keySubjetName.isEmpty()){
				entrada.setValue(keySubjetName);
			}			
		}
		if(entrada.getKey().equals(PssdefMapping.descDsvServPolicy)){
			if(!servicePolice.isEmpty()){
				entrada.setValue(servicePolice);
			}			
		}
		
	}

	/** Comprueba si los valores son dis  
	 * entorno del registro del sistema  
	 */
	private boolean checkValuesPssdefProperties(){
		boolean checkRegistryPssdef = false;
		int parameterContains = 0;
		String uri = PreferencesManager.get(PREFERENCE_URI_PSSDEF_SERVICE, "");
		String keySubjetName = PreferencesManager.get(PREFERENCE_KEY_SUBJET_PSSDEF_SERVICE, "");
		String servicePolice = PreferencesManager.get(PREFERENCE_SEVICE_POLICE_PSSDEF_SERVICE, "");
		
		
		File file = new File(APPLICATION_HOME_PSSDEF + File.separator + "PSSDEF.properties");

		try {
		    Scanner scanner = new Scanner(file);
		    //int lineNum = 0;
		    while (scanner.hasNextLine()) {
		        String line = scanner.nextLine();
		        //lineNum++;
		        if(!uri.isEmpty() && line.contains(uri) && compareLineRegistry(line,PssdefMapping.descPssDefUri,uri)) { 
		        	parameterContains++;
		        }
				if(!keySubjetName.isEmpty() && line.contains(keySubjetName) && compareLineRegistry(line,PssdefMapping.descKeySubjectName,keySubjetName)){
					parameterContains++;
				}
				if(!servicePolice.isEmpty() && line.contains(servicePolice) && compareLineRegistry(line,PssdefMapping.descDsvServPolicy,servicePolice)){
					parameterContains++;
				}
		    }
		    if(parameterContains == 3){
		    	checkRegistryPssdef = true;
		    }
			scanner.close();
		} catch(FileNotFoundException e) { 
			LOGGER.severe("No existe el directorio" + e.getMessage());
		}		
		return checkRegistryPssdef;
	}



	private boolean compareLineRegistry(String line, String KeyData, String dataRegistry) {
		String lineCompare = line.replace(KeyData, "");
		lineCompare = lineCompare.replaceFirst("=", "");
		
		return lineCompare.trim().equals(dataRegistry.trim());
	}
	

}
