package es.gob.afirma.pssdef.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import es.mdef.PSSDEF.PSSDEF;

/**
 * Clase que recupera el archivo de properties de PSSDEF
 * y del que hacen uso para la configuración de los servicios web.  
 * @author Fernando Hern&aacute;ndez Cebri&aacute;n.
 */
public class ConfigPssdef {

	private static final Logger LOGGER = Logger.getLogger("es.gob.afirma"); //$NON-NLS-1$

	private PSSDEF pssdef;

	private static ConfigPssdef configPssdef;

	public static ConfigPssdef getConfigurador() {

		if (configPssdef == null) {

			configPssdef = new ConfigPssdef();
		}
		return configPssdef;
	}

	private ConfigPssdef() {

		pssdef = new PSSDEF();
		Properties prop = new Properties();
		FileInputStream in;

		try {
			in = new FileInputStream(new File(System.getProperty("pssdf.propertiesFile")));

			LOGGER.severe("Comienza a cargar valores ");
			prop.load(in);
			LOGGER.severe("termina de cargar valores ");

			this.pssdef.SetProperties(prop);
			LOGGER.severe("introduce los valores de pssdef ");
			// BufferedReader reader = new BufferedReader(new
			// InputStreamReader(in));
			// StringBuilder out = new StringBuilder();
			// String line;
			// while ((line = reader.readLine()) != null) {
			// out.append(line);
			// }
			// System.out.println(out.toString()); // Prints the string content
			// read from input stream

		} catch (FileNotFoundException e) {
			LOGGER.severe("Error: No se ha encontrado el fichero de propiedades del servicio PSSDEF: " + e);
		} catch (IOException e) {
			LOGGER.severe("Error: Los parámetros no son los esperados en los ficheros de propieades: " + e);
		}

	}

	public PSSDEF getPssdef() {
		return pssdef;
	}

	public static ConfigPssdef getConfigPssdef() {
		return configPssdef;
	}

}
