package es.gob.afirma.pssdef;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import es.gob.afirma.pssdef.ValidaCertificadoImpl;

public class iniciaServicioPssdef {

	public static void main(String[] args) {
		System.setProperty("smartwrapper.propertiesFile",System.getProperty("user.home")
				+ File.separator + ".afirma"
				+ File.separator + "AutoFirma" + File.separator + "PssdefService" 
				+ File.separator +"smartwrapper.properties");
		System.setProperty("pssdf.propertiesFile",System.getProperty("user.home")
				+ File.separator + ".afirma"
				+ File.separator + "AutoFirma" + File.separator + "PssdefService" 
				+ File.separator +"PSSDEF.properties");

		ValidaCertificadoImpl validaCertificado = new  ValidaCertificadoImpl();		
		String certificado =  new File("src/main/resources/PKI42-1.cer").getAbsolutePath();
		
		try {
			System.out.println(validaCertificado.validaCert(Files.readAllBytes(Paths.get(certificado))));;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
