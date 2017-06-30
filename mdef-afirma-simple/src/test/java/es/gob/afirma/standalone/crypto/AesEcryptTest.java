package es.gob.afirma.standalone.crypto;

import org.junit.Test;

/**
 * Clase que consume los métodos de la clase AesEcrypt
 */
public class AesEcryptTest {

	@SuppressWarnings("static-method")
	@Test
	public void testAdESJEcriptUser() throws Exception {
		String cleartext = "prueba";
		System.out.println("Texto encriptado User: " + AesEcrypt.encrypt(cleartext));
		System.out.println("Texto desencriptado User: "
				+ AesEcrypt.decrypt(AesEcrypt.encrypt(cleartext)));
	}


	@SuppressWarnings("static-method")
	@Test
	public void testAdESJEcriptPass() throws Exception {
		String cleartext = "SILOPDEF";
		System.out.println("Texto encriptado Pass: " + AesEcrypt.encrypt(cleartext));
		System.out.println("Texto desencriptado Pass: "
				+ AesEcrypt.decrypt(AesEcrypt.encrypt(cleartext)));
	}
	
}