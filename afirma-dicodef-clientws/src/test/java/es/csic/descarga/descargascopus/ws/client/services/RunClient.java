package es.csic.descarga.descargascopus.ws.client.services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import es.gob.afirma.ws.client.modelo.CertificadoType;
import es.gob.afirma.ws.client.modelo.IdentidadResType;
import es.gob.afirma.ws.client.modelo.IdentidadType;
import es.gob.afirma.ws.client.modelo.ListaUsuarioSistemaType;
import es.gob.afirma.ws.client.modelo.UsuarioSistemaExtendType;
import es.gob.afirma.ws.client.modelo.UsuarioSistemaMailType;
import es.gob.afirma.ws.client.modelo.UsuarioSistemaType;
import es.gob.afirma.ws.client.services.ConfigParameters;
import es.gob.afirma.ws.client.services.DicodefClientWs;
import es.gob.afirma.ws.client.services.SoapClientConfig;

public class RunClient {

    private static final String UPLOAD_FOLDER = "C:\\temp\\";
	static AnnotationConfigApplicationContext context;
    
    public static void main(String[] args) {
	    System.out.println("entrar en el sistema");
	    ConfigParameters.inicializeValues("url", "usuario", "password");
	    context = new AnnotationConfigApplicationContext(SoapClientConfig.class);
      DicodefClientWs client = context.getBean(DicodefClientWs.class);

      UsuarioSistemaMailType identidad = new UsuarioSistemaMailType();
      identidad.setUid("SILOPDEF03");
      IdentidadType usuario;
		try {
			usuario = client.consultarIdentidad(identidad);
		    System.out.println(usuario.getApellido1());
		    System.out.println(usuario.getNombre());
		} catch (URISyntaxException e) {
		    System.out.println(e.getReason());
			//e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
      
//        context = new AnnotationConfigApplicationContext(SoapClientConfig.class);
//        DicodefClientWs client = context.getBean(DicodefClientWs.class);
//        client.setDefaultUri("http://localhost:8080/HelloService/ConsultarDicodefImplDummy?wsdl");
//        ListaUsuarioSistemaType response = client.busquedaPersonas(new IdentidadResType());
//        for(UsuarioSistemaExtendType usuario : response.getUsuarioSistemaExtend()){
//            System.out.println("usuarios response: " + usuario.getCN());
//        	
//        }
//        
//    	UsuarioSistemaType identidad = new UsuarioSistemaType();
//        identidad.setUid("we");
//        IdentidadType usuario = client.consultarIdentidad(identidad);
//	    System.out.println(usuario.getApellido1());
//	    System.out.println(usuario.getNombre());
//	    
//        CertificadoType certificadoUsuario = client.obtenerCertificado(new UsuarioSistemaType());
//        escribirFichero(certificadoUsuario.getCertificado());
        
    }
    
    private static void escribirFichero(byte[] bFile){
        FileInputStream fileInputStream = null;

        try {

            //File file = new File("C:\\temp\\testCer-DER.cer");

            //save bytes[] into a file
            writeBytesToFile(bFile, UPLOAD_FOLDER + "testCer-DER.cer");

            System.out.println("Done");

        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }    	
    
    
    private static void writeBytesToFile(byte[] bFile, String fileDest) {

        try (FileOutputStream fileOuputStream = new FileOutputStream(fileDest)) {
            fileOuputStream.write(bFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }    
        
}
