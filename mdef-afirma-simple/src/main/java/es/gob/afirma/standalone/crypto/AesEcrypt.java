package es.gob.afirma.standalone.crypto;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//import org.apache.xml.security.utils.Base64;

/**
 * Clase que contiene los métodos encrypt y descrypt, cuyos objetivos son
 * encriptar y desencriptar respectivamente, utilizando los algoritmos y codificación
 * definidas en las variables estáticas alg y cI. Y también:
 * key la llave en tipo String a utilizar
 * iv el vector de inicialización a utilizar
 */
  public class AesEcrypt {

   private AesEcrypt() {
   }

	// Definición del tipo de algoritmo a utilizar (AES, DES, RSA)
    private final static String alg = "AES";
    // Definición del modo de cifrado a utilizar
    private final static String cI = "AES/CBC/PKCS5Padding";
 
    private final static String key = "92AE31A79FEEB2A3"; // llave
    
    private final static String iv = "0123456789ABCDEF"; // vector de inicialización    
    
    
    /**
     * Función de tipo String que recibe el texto que se desea cifrar
     * @param cleartext el texto sin cifrar a encriptar
     * @return el texto cifrado en modo String
     * @throws Exception puede devolver excepciones de los siguientes tipos: NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException
     */
    public static String encrypt(String cleartext) throws Exception {
            Cipher cipher = Cipher.getInstance(cI);
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), alg);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(cleartext.getBytes());
            return new String(Base64.getEncoder().encode(encrypted));
    }
 
    /**
     * Función de tipo String que recibe el texto que se desea descifrar 
     * @param encrypted el texto cifrado en modo String
     * @return el texto desencriptado en modo String
     * @throws Exception puede devolver excepciones de los siguientes tipos: NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException
     */
    public static String decrypt(String encrypted) throws Exception {
            Cipher cipher = Cipher.getInstance(cI);
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), alg);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
            byte[] enc = Base64.getDecoder().decode(encrypted);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
            byte[] decrypted = cipher.doFinal(enc);
            return new String(decrypted);
    }
	
}