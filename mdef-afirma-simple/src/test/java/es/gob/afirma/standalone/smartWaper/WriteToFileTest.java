package es.gob.afirma.standalone.smartWaper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;

import es.gob.afirma.core.misc.Platform;
import es.gob.afirma.standalone.SimpleAfirma;

public class WriteToFileTest {

	public static final String APPLICATION_HOME_PSSDEF = Platform.getUserHome() + File.separator + ".afirma" + 
	File.separator + "AutoFirma"+ File.separator + "PssdefService"; //$NON-NLS-1$ //$NON-NLS-2$
	
	public void writeSmartWapper() throws IOException {

		FileWriter fstream;
		BufferedWriter out;

		// create your filewriter and bufferedreader
		fstream = new FileWriter(APPLICATION_HOME_PSSDEF + File.separator + "smartwrapper.properties");
		out = new BufferedWriter(fstream);

		Map<String, String> doc = SmartwrapperMapping.getprepareDocumentPublicacioMap();
		for (Entry<String, String> entrada : doc.entrySet()) {
			System.out.println(entrada.getKey().concat(" = ").concat(entrada.getValue()));
			out.write(entrada.getKey().concat(" = ").concat(entrada.getValue()) + "\n");
		}
		out.close();
	}

	public void writePssdefProperties() throws IOException {

		FileWriter fstream;
		BufferedWriter out;

		// create your filewriter and bufferedreader
		fstream = new FileWriter(APPLICATION_HOME_PSSDEF + File.separator + "PSSDEF.properties");
		out = new BufferedWriter(fstream);

		Map<String, String> doc = PssdefMapping.getprepareDocumentPublicacioMap();
		for (Entry<String, String> entrada : doc.entrySet()) {
			System.out.println(entrada.getKey().concat(" = ").concat(entrada.getValue()));
			out.write(entrada.getKey().concat(" = ").concat(entrada.getValue()) + "\n");
		}
		out.close();
	}
	
	public void copycertPssdef(String file) throws IOException {
		InputStream initialStream;
		initialStream = getClass().getResourceAsStream("/"+file);		
	    byte[] buffer = new byte[initialStream.available()];
	    initialStream.read(buffer);
	    //System.setProperty("smartwrapper.propertiesFile", "C:/temp/smartwrapper.properties");
	    
	    File targetFile = new File(APPLICATION_HOME_PSSDEF + File.separator + file);
	    OutputStream outStream = new FileOutputStream(targetFile);
	    outStream.write(buffer);

	}
	
	private void createDirectory(){
		File targetFile = new File(APPLICATION_HOME_PSSDEF);
		boolean result=false;
	    if(!targetFile.exists()){
	    	targetFile.mkdir();
	    	result=true;
	    }

	}
	
	private void mappingToSystemPrperties(){
	    System.setProperty("smartwrapper.propertiesFile", APPLICATION_HOME_PSSDEF+ File.separator +"smartwrapper.properties");
		
	}

	public static void main(String args[]) throws IOException {
		WriteToFileTest writeToFileTest = new WriteToFileTest();
		writeToFileTest.createDirectory();
		writeToFileTest.copycertPssdef("trustedx-inethandler.truststore");
		writeToFileTest.copycertPssdef("PSSDEF_Autofirma.crt");
		writeToFileTest.copycertPssdef("PSSDEF_Autofirma.jks");
		writeToFileTest.writeSmartWapper();
		writeToFileTest.writePssdefProperties();
		writeToFileTest.mappingToSystemPrperties();
		
	}
}
