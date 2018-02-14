package es.gob.afirma.keystores.chain.verifier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import es.gob.afirma.keystores.AOKeyStoreManager;

// fhc mod 2018 clase creada para las cadenas de responsabilidad

public final class CertChainValidator {

    protected static final Logger LOGGER = Logger.getLogger("es.gob.afirma"); //$NON-NLS-1$
	
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
    public static boolean validateKeyChain(X509Certificate client,
            KeyStore keyStore) throws KeyStoreException, CertificateException,
            InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            NoSuchProviderException {
        X509Certificate[] certs = new X509Certificate[keyStore.size()];
        int i = 0;
        Enumeration<String> alias = keyStore.aliases();
 
        while (alias.hasMoreElements()) {
            certs[i++] = (X509Certificate) keyStore.getCertificate(alias
                    .nextElement());
        }
 
        return validateKeyChain(client, certs);
    }
 
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
    public static boolean validateKeyChain(X509Certificate client,
            X509Certificate... trustedCerts) throws CertificateException,
            InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            NoSuchProviderException {
        boolean found = false;
        int i = trustedCerts.length;
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        TrustAnchor anchor;
        Set anchors;
        CertPath path;
        List list;
        PKIXParameters params;
        CertPathValidator validator = CertPathValidator.getInstance("PKIX");
 
        while (!found && i > 0) {
            anchor = new TrustAnchor(trustedCerts[--i], null);
            anchors = Collections.singleton(anchor);
 
            list = Arrays.asList(new Certificate[] { client });
            path = cf.generateCertPath(list);
 
            params = new PKIXParameters(anchors);
            params.setRevocationEnabled(false);
 
            if (client.getIssuerDN().equals(trustedCerts[i].getSubjectDN())) {
                try {
                    validator.validate(path, params);
                    if (isSelfSigned(trustedCerts[i])) {
                        // found root ca
                        found = true;
                        //System.out.println("validating root" + trustedCerts[i].getSubjectX500Principal().getName());
                        LOGGER.info("validating root" + trustedCerts[i].getSubjectX500Principal().getName());
                    } else if (!client.equals(trustedCerts[i])) {
                        // find parent ca
                        //System.out.println("validating via:" + trustedCerts[i].getSubjectX500Principal().getName());
                        LOGGER.info("validating via:" + trustedCerts[i].getSubjectX500Principal().getName());
                        found = validateKeyChain(trustedCerts[i], trustedCerts);
                    }
                } catch (CertPathValidatorException e) {
                    // validation fail, check next certifiacet in the trustedCerts array
                }
            }
        }
 
        return found;
    }
 
    /**
     *
     * @param cert is X509Certificate that will be tested
     * @return true if cert is self signed, false otherwise
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public static boolean isSelfSigned(X509Certificate cert)
            throws CertificateException, NoSuchAlgorithmException,
            NoSuchProviderException {
        try {
            PublicKey key = cert.getPublicKey();
 
            cert.verify(key);
            return true;
        } catch (SignatureException sigEx) {
            return false;
        } catch (InvalidKeyException keyEx) {
            return false;
        }
    }
 
    public static Set<String> getIssuerValid(final String keyStoreChainUri, final String storepass, final AOKeyStoreManager ksm) throws KeyStoreException,
    NoSuchAlgorithmException, CertificateException, IOException,
    InvalidAlgorithmParameterException, NoSuchProviderException {
    	Set <String>issuerList = null;
        FileInputStream fin = null;
        fin = new FileInputStream(keyStoreChainUri);
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(fin, storepass.toCharArray());
        FileInputStream Key = null;
        for(String alias :ksm.getAliases())
        if (validateKeyChain(ksm.getCertificate(alias), ks)) {
        	if(null==issuerList || issuerList.isEmpty()){
        		issuerList = new HashSet<String>();
        	}
        	issuerList.add(ksm.getCertificate(alias).getIssuerX500Principal().toString());
            //System.out.println("validate success");
            LOGGER.info("validate success");
        } else {
            //System.out.println("validate fail");
            LOGGER.info("validate fail");
        }
        
        return issuerList;
    	
    }

}
