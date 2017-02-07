package es.csic.descarga.descargascopus.ws.client.services;
import es.gob.afirma.ws.client.modelo.CertificadoType;
import es.gob.afirma.ws.client.modelo.IdentidadResType;
import es.gob.afirma.ws.client.modelo.IdentidadType;
import es.gob.afirma.ws.client.modelo.ListaUsuarioSistemaType;
import es.gob.afirma.ws.client.modelo.UsuarioSistemaExtendType;
import es.gob.afirma.ws.client.modelo.UsuarioSistemaType;
//import es.csic.descarga.descargascopus.ws.client.modelo.AffilCiscResponseDetails;
//import es.csic.descarga.descargascopus.ws.client.modelo.Affiliacion;
import es.gob.afirma.ws.client.services.ConsultaDicoefService;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:applicationContext-ws-client.xml"})
public class ConsultaDicoefServiceTest {

    @Autowired
     private ConsultaDicoefService consultaDicoefService;
    
    private static final String UPLOAD_FOLDER = "C:\\temp\\";
    
    @Test
    public void shouldBeInjected() throws Exception {
        assertNotNull(consultaDicoefService);
    }    
    
    //@Test
    public void ConsultaDicoefServiceTest(){
        IdentidadResType identidad = new IdentidadResType();
        identidad.setUid("we");
        identidad.setNombre("we");
        identidad.setApellido1("we");
        identidad.setApellido2("we");
        identidad.setNombreCompleto("we");
        identidad.setDNI("we");
        identidad.setMail("we");
        identidad.setNumEmpleado("we");
        identidad.setSexo("we");
        identidad.setFecNacimiento("we");
        ListaUsuarioSistemaType listaUsuarioSistema = consultaDicoefService.busquedaPersonas(identidad);
        for(UsuarioSistemaExtendType usuarioSistemaExtend : listaUsuarioSistema.getUsuarioSistemaExtend()){
        	      System.out.println(usuarioSistemaExtend.getDNI()+"----"+usuarioSistemaExtend.getCN());            
        }
    }

    //@Test
    public void consultarIdentidadTest(){
    	UsuarioSistemaType identidad = new UsuarioSistemaType();
        identidad.setUid("we");
        //identidad.setDNI("we");
        IdentidadType usuario = consultaDicoefService.consultarIdentidad(identidad);
	    System.out.println(usuario.getApellido1());
	    System.out.println(usuario.getNombre());
    }

    
    
    @Test
    public void obtenerCertificadoTest(){
    	UsuarioSistemaType identidad = new UsuarioSistemaType();
        identidad.setUid("we");
        //identidad.setDNI("we");
        CertificadoType usuario = consultaDicoefService.obtenerCertificado(identidad);
        escribirFichero(usuario.getCertificado());
        
    }
    
    private void escribirFichero(byte[] bFile){
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

        try  {
        	FileOutputStream fileOuputStream = new FileOutputStream(fileDest);
            fileOuputStream.write(bFile);
            fileOuputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
    
}
