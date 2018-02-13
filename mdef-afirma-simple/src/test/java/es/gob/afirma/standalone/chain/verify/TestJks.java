package es.gob.afirma.standalone.chain.verify;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;


public class TestJks {
	//String filePath 			= "C://eclispe_wk_mdef/afirma-client-mdef/mdef-afirma-simple/src/main/resources/PSSDEF_Autofirma.jks";
	
	private static final String JKS_FILE = "src/test/resources/chain/PSSDEF_Autofirma.jks";
	private static final String CERT_FILE = "src/test/resources/chain/firmaMMarPrueba170118.cer";
	
	private static X509Certificate recuperaCertificado(FileInputStream file){
		try {
			return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(file);
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		
	}

	public static void main(final String[] args) throws Exception {
		
		File source = new File(JKS_FILE);
		File certSource = new File(CERT_FILE);
		
		//InputStream initialStream;
		//initialStream = getClass().getResourceAsStream("/"+file);		
		
		CertificateFactory cf 		= CertificateFactory.getInstance("X.509");
		CertPathValidator validator = CertPathValidator.getInstance("PKIX");		
		
        char[] storepass 			= "Autofirma".toCharArray();
        KeyStore keyStore 			= KeyStore.getInstance("JKS");
        
        FileInputStream fin = null;
        FileInputStream finCertSource = null;
        try {
	        //fin = new FileInputStream(filePath);
        	fin = new FileInputStream(source);
        	finCertSource = new FileInputStream(certSource);
            X509Certificate validCer = recuperaCertificado (finCertSource);
	        keyStore.load(fin, storepass);
	        
	        X509Certificate[] keyStoreCertificates = obtenerCertificadosDelKeyStore(keyStore);
	        Enumeration<String> aliases = keyStore.aliases();
	        while(aliases.hasMoreElements()){
	            String alias = aliases.nextElement();
	            	                	                
	            if(keyStore.getCertificate(alias).getType().equals("X.509")){
	            	
	            	System.out.println("........................................................");
	            	System.out.println("alias:" + alias);
	            	System.out.println("........................................................");
	            	/*
	            	X509Certificate currentCertX509 = (X509Certificate) keyStore.getCertificate(alias);	            	
	            	boolean validationResult = validateKeyChain(cf,validator, currentCertX509, keyStoreCertificates);
	            	
	                if(validationResult) {
	                    System.out.println("validate success");
	                } else {
	                    System.out.println("validate fail");
	                }	
	                */
	            }	                
	        }//end while	            
	    } 
	    catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } 
	    finally {
	    	fin.close();
	    }
	}//end method

	/**
	 * 
	 * @param keyStore
	 * @return
	 */
	public static X509Certificate[] obtenerCertificadosDelKeyStore(KeyStore keyStore){
       X509Certificate[] certs = null;
       try {
    	   Enumeration<String> alias = keyStore.aliases();
    	   List<String> listAlias = new ArrayList<String>();
    	   int countItems = 0;
    	   while (alias.hasMoreElements()) {    		  
    		   countItems++;
    		   listAlias.add(alias.nextElement());
    	   }
    	   certs = new X509Certificate[countItems];
    	   for(int idx=0;idx<countItems;idx++){
    		   certs[idx] = (X509Certificate) keyStore.getCertificate(listAlias.get(idx)); 
    	   }			
       } 
       catch (KeyStoreException e) {
    	   
			e.printStackTrace();
       }
       return certs;
	}//end method
				
    /**
    *
    * @param cert is X509Certificate that will be tested
    * @return true if cert is self signed, false otherwise
    * @throws CertificateException
    * @throws NoSuchAlgorithmException
    * @throws NoSuchProviderException
    */
   public static boolean isSelfSigned(X509Certificate cert) throws CertificateException, NoSuchAlgorithmException,
           														   NoSuchProviderException {
       try {
           PublicKey key = cert.getPublicKey();
           cert.verify(key);
           return true;
       } 
       catch (SignatureException sigEx) {
           return false;
       } 
       catch (InvalidKeyException keyEx) {
           return false;
       }
   }//end method
   

   
   /**
    * Validate keychain
    * @param client is the client X509Certificate
    * @param trustedCerts is Array containing all trusted X509Certificate
    * @return true if validation until root certificate success, false otherwise
    * @throws CertificateException
    * @throws InvalidAlgorithmParameterException
    * @throws NoSuchAlgorithmException
    * @throws NoSuchProviderException
    */
   @SuppressWarnings({ "rawtypes", "unchecked" })
   public static boolean validateKeyChain(CertificateFactory cf,CertPathValidator validator,
		   								  X509Certificate client,X509Certificate... trustedCerts) 
		   		throws CertificateException,InvalidAlgorithmParameterException,NoSuchAlgorithmException,NoSuchProviderException {
	   
       boolean found = false;
       int indCert = trustedCerts.length;
       
       while (!found && indCert > 0) {
    	   indCert--;
    	   
    	   TrustAnchor anchor = new TrustAnchor(trustedCerts[indCert], null);
    	   Set anchors = Collections.singleton(anchor);

           List list = Arrays.asList(new Certificate[] { client });
           CertPath path = cf.generateCertPath(list);

           PKIXParameters params = new PKIXParameters(anchors);
           params.setRevocationEnabled(false);

           if (client.getIssuerDN().equals(trustedCerts[indCert].getSubjectDN())) {
               try {
                   validator.validate(path, params);
                   if (isSelfSigned(trustedCerts[indCert])) {
                       // found root ca
                       found = true;
                       System.out.println("validating root" + trustedCerts[indCert].getSubjectX500Principal().getName());
                   } 
                   else if (!client.equals(trustedCerts[indCert])) {
                       // find parent ca
                       System.out.println("validating via:" + trustedCerts[indCert].getSubjectX500Principal().getName());
                       found = validateKeyChain(cf,validator,trustedCerts[indCert], trustedCerts);
                   }
               } 
               catch (CertPathValidatorException e) {
                   // validation fail, check next certifiacet in the trustedCerts array
            	   System.out.println("CertPathValidatorException");
               }
           }//end if
           
       }//end while

       return found;
   }//end method


/*
if(currentCertType.equals("X.509")){
	
	System.out.println("........................................................");
	System.out.println("alias:" + alias);
	System.out.println("........................................................");
	
	X509Certificate currentCertX509 = (X509Certificate) ks.getCertificate(alias);
	X500Principal principalX500 = currentCertX509.getSubjectX500Principal();
	X500Principal issuerX500 = currentCertX509.getIssuerX500Principal();

	String strPrincipalX500 = currentCertX509.getSubjectX500Principal().toString();
	String strIssuerX500 = currentCertX509.getIssuerX500Principal().toString();

	System.out.println("principalX500:" + principalX500 );
	System.out.println("strPrincipalX500:" + strPrincipalX500 );
	System.out.println("........................................................");
	
	System.out.println("issuerX500:" + issuerX500 );
	System.out.println("strIssuerX500:" + strIssuerX500 );
	
}	 
 */
   
   /**
    * Validate keychain
    * @param client is the client X509Certificate
    * @param keyStore containing all trusted certificate
    * @return true if validation until root certificate success, false otherwise
    * @throws KeyStoreException
    * @throws CertificateException
    * @throws InvalidAlgorithmParameterException
    * @throws NoSuchAlgorithmException
    * @throws NoSuchProviderException
    */
   /*
   public static boolean validateX509Certificate(CertificateFactory cf,CertPathValidator validator,X509Certificate client,KeyStore keyStore) 
		   throws KeyStoreException,CertificateException,InvalidAlgorithmParameterException,NoSuchAlgorithmException,NoSuchProviderException {
       X509Certificate[] certs = new X509Certificate[keyStore.size()];
       int i = 0;
       Enumeration<String> alias = keyStore.aliases();
       while (alias.hasMoreElements()) {
           certs[i++] = (X509Certificate) keyStore.getCertificate(alias.nextElement());
       }
       return validateKeyChain(cf,validator,client, certs);
   }//end method  
   */
	
}//end class
