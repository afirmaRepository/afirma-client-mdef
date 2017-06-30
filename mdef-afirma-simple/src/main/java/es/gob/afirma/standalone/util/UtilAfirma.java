package es.gob.afirma.standalone.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UtilAfirma {


	/** Indica si el existe el fichero necesario para el servicios 
	 * @param filePath Ruta del fichero.
	 * @return devuelve true si existe .
	 */
	public static boolean fileExit(String file) {
		boolean result = false;
		File filePro = new File(file);
		if (filePro.exists()) {
			result = true;
		}
		return result;
	}

	/** copia los certificados necesarios para emplear los servicios 
	 * @param filePath Ruta del fichero.
	 * @throws IOException Error al construir el documento.
	 */	
	public static void copyFile(String file, InputStream is) throws IOException {

		OutputStream os = new FileOutputStream(file);

		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = is.read(buffer)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		is.close();
		os.flush();
		os.close();
	}
	
}
