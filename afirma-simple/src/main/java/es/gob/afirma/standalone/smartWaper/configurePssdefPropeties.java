package es.gob.afirma.standalone.smartWaper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.springframework.security.crypto.codec.Base64;

import es.gob.afirma.core.misc.Platform;


/** clase que crea el entorno necesario para acceder a los servicios de PSSDEF.
 * @author Fernando Hern&aacute;ndez Cebri&aacute;n. */
public class configurePssdefPropeties {

	static final Logger LOGGER = Logger.getLogger("es.gob.afirma"); //$NON-NLS-1$

	public static final String APPLICATION_HOME_PSSDEF = Platform.getUserHome() + File.separator + ".afirma"
			+ File.separator + "AutoFirma" + File.separator + "PssdefService"; //$NON-NLS-1$ //$NON-NLS-2$

	private static configurePssdefPropeties configurePssdefPropeties;

	public static void inizializerProperties() {

		if (configurePssdefPropeties == null) {

			configurePssdefPropeties = new configurePssdefPropeties();
		}
	}

	/** Inicia todos los procesos necesarios para construir 
	 * el entorno necesario para los servicios PSSDEF.
	 */
	private configurePssdefPropeties() {
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
			if (!fileExit("PSSDEF.properties")) {
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
		System.out.println("existe archivo : " + result);
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

}
