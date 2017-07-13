package es.gob.afirma.standalone.ui;

import java.util.Properties;

import es.gob.afirma.core.signers.AOSignConstants;
import es.gob.afirma.core.signers.AOSigner;
import es.gob.afirma.signers.pades.AOPDFSigner;
import es.gob.afirma.signers.xades.AOFacturaESigner;
import es.gob.afirma.signers.xades.AOXAdESSigner;
import es.gob.afirma.standalone.ui.preferences.CertificationLevelResources;
import es.gob.afirma.standalone.ui.preferences.PreferencesManager;

final class ExtraParamsHelper {

	private ExtraParamsHelper() {
		// No permitimos la instanciacion
	}

	final static Properties loadExtraParamsForSigner(final AOSigner signer) {

		final Properties p;
		if (signer instanceof AOFacturaESigner) {
        	p = loadFacturaEExtraParams();
        }
		else if (signer instanceof AOXAdESSigner) {
        	p = loadXAdESExtraParams();
        }
        else if (signer instanceof AOPDFSigner) {
        	p = loadPAdESExtraParams();
        }
        else {
        	p = loadCAdESExtraParams();
        }

		return p;
	}

	/** Obtiene la configuraci&oacute;n para las firmas Factura-E.
	 * @return Propiedades para la configuraci&oacute;n de las firmas Factura-E. */
	private static Properties loadFacturaEExtraParams() {
		final Properties p = new Properties();

		// Metadatos sobre la "produccion" de la firma de la factura
        if (!PreferencesManager.get(PreferencesManager.PREFERENCE_FACTURAE_SIGNATURE_PRODUCTION_CITY, "").trim().isEmpty()) {  //$NON-NLS-1$
        	p.put(
    			"signatureProductionCity", //$NON-NLS-1$
    			PreferencesManager.get(PreferencesManager.PREFERENCE_FACTURAE_SIGNATURE_PRODUCTION_CITY, "") //$NON-NLS-1$
			);
        }
        if (!PreferencesManager.get(PreferencesManager.PREFERENCE_FACTURAE_SIGNATURE_PRODUCTION_PROVINCE, "").trim().isEmpty()) {  //$NON-NLS-1$
        	p.put(
    			"signatureProductionProvince", //$NON-NLS-1$
    			PreferencesManager.get(PreferencesManager.PREFERENCE_FACTURAE_SIGNATURE_PRODUCTION_PROVINCE, "") //$NON-NLS-1$
			);
        }
        if (!PreferencesManager.get(PreferencesManager.PREFERENCE_FACTURAE_SIGNATURE_PRODUCTION_POSTAL_CODE, "").trim().isEmpty()) {  //$NON-NLS-1$
        	p.put(
    			"signatureProductionPostalCode", //$NON-NLS-1$
    			PreferencesManager.get(PreferencesManager.PREFERENCE_FACTURAE_SIGNATURE_PRODUCTION_POSTAL_CODE, "") //$NON-NLS-1$
			);
        }
        if (!PreferencesManager.get(PreferencesManager.PREFERENCE_FACTURAE_SIGNATURE_PRODUCTION_COUNTRY, "").trim().isEmpty()) {  //$NON-NLS-1$
        	p.put(
    			"signatureProductionCountry", //$NON-NLS-1$
    			PreferencesManager.get(PreferencesManager.PREFERENCE_FACTURAE_SIGNATURE_PRODUCTION_COUNTRY, "") //$NON-NLS-1$
			);
        }

        // Papel del firmante de la factura, es un campo acotado
        if (!PreferencesManager.get(PreferencesManager.PREFERENCE_FACTURAE_SIGNER_ROLE, "").trim().isEmpty()) { //$NON-NLS-1$
        	p.put(
        		"signerClaimedRoles", //$NON-NLS-1$
        		PreferencesManager.get(PreferencesManager.PREFERENCE_FACTURAE_SIGNER_ROLE, "") //$NON-NLS-1$
    		);
        }
        
		return p;
	}

	/** Obtiene la configuraci&oacute;n para las firmas XAdES.
	 * @return Propiedades para la configuraci&oacute;n de las firmas XAdES. */
	private static Properties loadXAdESExtraParams() {

		final Properties p = new Properties();
        p.put("ignoreStyleSheets", "false"); //$NON-NLS-1$ //$NON-NLS-2$

        // Preferencias de politica de firma
        final String policyId = PreferencesManager.get(PreferencesManager.PREFERENCE_XADES_POLICY_IDENTIFIER, ""); //$NON-NLS-1$

        if (!policyId.trim().isEmpty()) {
        	p.put(
        		"policyIdentifier", //$NON-NLS-1$
        		policyId
        	);

        	if (!PreferencesManager.get(PreferencesManager.PREFERENCE_XADES_POLICY_IDENTIFIER_HASH, "").trim().isEmpty()) { //$NON-NLS-1$
        		p.put(
        			"policyIdentifierHash", //$NON-NLS-1$
        			PreferencesManager.get(PreferencesManager.PREFERENCE_XADES_POLICY_IDENTIFIER_HASH, "") //$NON-NLS-1$
        		);
        	}
        	if (!PreferencesManager.get(PreferencesManager.PREFERENCE_XADES_POLICY_IDENTIFIER_HASH_ALGORITHM, "").trim().isEmpty()) { //$NON-NLS-1$
        		p.put(
        			"policyIdentifierHashAlgorithm", //$NON-NLS-1$
        			PreferencesManager.get(PreferencesManager.PREFERENCE_XADES_POLICY_IDENTIFIER_HASH_ALGORITHM, "") //$NON-NLS-1$
        		);
        	}
        	if (!PreferencesManager.get(PreferencesManager.PREFERENCE_XADES_POLICY_QUALIFIER, "").trim().isEmpty()) { //$NON-NLS-1$
        		p.put(
        			"policyQualifier", //$NON-NLS-1$
        			PreferencesManager.get(PreferencesManager.PREFERENCE_XADES_POLICY_QUALIFIER, "") //$NON-NLS-1$
        		);
        	}
        }

        if (!PreferencesManager.get(PreferencesManager.PREFERENCE_XADES_SIGNER_CLAIMED_ROLE, "").trim().isEmpty()) { //$NON-NLS-1$
            p.put(
        		"signerClaimedRoles", //$NON-NLS-1$
        		PreferencesManager.get(PreferencesManager.PREFERENCE_XADES_SIGNER_CLAIMED_ROLE, "") //$NON-NLS-1$
    		);
        }

        if (!PreferencesManager.get(PreferencesManager.PREFERENCE_XADES_SIGNATURE_PRODUCTION_CITY, "").trim().isEmpty()) {  //$NON-NLS-1$
        	p.put(
    			"signatureProductionCity", //$NON-NLS-1$
    			PreferencesManager.get(PreferencesManager.PREFERENCE_XADES_SIGNATURE_PRODUCTION_CITY, "") //$NON-NLS-1$
			);
        }
        if (!PreferencesManager.get(PreferencesManager.PREFERENCE_XADES_SIGNATURE_PRODUCTION_PROVINCE, "").trim().isEmpty()) {  //$NON-NLS-1$
        	p.put(
    			"signatureProductionProvince", //$NON-NLS-1$
    			PreferencesManager.get(PreferencesManager.PREFERENCE_XADES_SIGNATURE_PRODUCTION_PROVINCE, "") //$NON-NLS-1$
			);
        }
        if (!PreferencesManager.get(PreferencesManager.PREFERENCE_XADES_SIGNATURE_PRODUCTION_POSTAL_CODE, "").trim().isEmpty()) {  //$NON-NLS-1$
        	p.put(
    			"signatureProductionPostalCode", //$NON-NLS-1$
    			PreferencesManager.get(PreferencesManager.PREFERENCE_XADES_SIGNATURE_PRODUCTION_POSTAL_CODE, "") //$NON-NLS-1$
			);
        }
        if (!PreferencesManager.get(PreferencesManager.PREFERENCE_XADES_SIGNATURE_PRODUCTION_COUNTRY, "").trim().isEmpty()) {  //$NON-NLS-1$
        	p.put(
    			"signatureProductionCountry", //$NON-NLS-1$
    			PreferencesManager.get(PreferencesManager.PREFERENCE_XADES_SIGNATURE_PRODUCTION_COUNTRY, "") //$NON-NLS-1$
			);
        }
        if (!PreferencesManager.get(PreferencesManager.PREFERENCE_XADES_SIGN_FORMAT, "").trim().isEmpty()) {  //$NON-NLS-1$
        	p.put(
    			"format", //$NON-NLS-1$
    			PreferencesManager.get(PreferencesManager.PREFERENCE_XADES_SIGN_FORMAT, "") //$NON-NLS-1$
			);
        }

		return p;
	}

	/** Obtiene la configuraci&oacute;n para las firmas PAdES.
	 * @return Propiedades para la configuraci&oacute;n de las firmas PAdES. */
	private static Properties loadPAdESExtraParams() {

		final Properties p = new Properties();
        p.put("allowSigningCertifiedPdfs", "false"); //$NON-NLS-1$ //$NON-NLS-2$

        // Preferencias de politica de firma PAdES
        final String policyId = PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_POLICY_IDENTIFIER, ""); //$NON-NLS-1$
        if (!policyId.trim().isEmpty()) {
        	p.put(
        		"policyIdentifier", //$NON-NLS-1$
        		policyId
        	);
        	if (!PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_POLICY_IDENTIFIER_HASH, "").trim().isEmpty()) { //$NON-NLS-1$
        		p.put(
        			"policyIdentifierHash", //$NON-NLS-1$
        			PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_POLICY_IDENTIFIER_HASH, "") //$NON-NLS-1$
        		);
        	}
        	if (!PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_POLICY_IDENTIFIER_HASH_ALGORITHM, "").trim().isEmpty()) { //$NON-NLS-1$
        		p.put(
        			"policyIdentifierHashAlgorithm", //$NON-NLS-1$
        			PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_POLICY_IDENTIFIER_HASH_ALGORITHM, "") //$NON-NLS-1$
        		);
        	}
        	if (!PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_POLICY_QUALIFIER, "").trim().isEmpty()) { //$NON-NLS-1$
        		p.put(
        			"policyQualifier", //$NON-NLS-1$
        			PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_POLICY_QUALIFIER, "") //$NON-NLS-1$
        		);
        	}
        }

        // Metadatos PAdES
        if (!PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_SIGN_REASON, "").trim().isEmpty()) {  //$NON-NLS-1$
        	p.put(
    			"signReason", //$NON-NLS-1$
    			PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_SIGN_REASON, "") //$NON-NLS-1$
			);
        }
        if (!PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_SIGN_PRODUCTION_CITY, "").trim().isEmpty()) {  //$NON-NLS-1$
        	p.put(
    			"signatureProductionCity", //$NON-NLS-1$
    			PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_SIGN_PRODUCTION_CITY, "") //$NON-NLS-1$
			);
        }
        if (!PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_SIGNER_CONTACT, "").trim().isEmpty()) {  //$NON-NLS-1$
        	p.put(
    			"signerContact", //$NON-NLS-1$
    			PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_SIGNER_CONTACT, "") //$NON-NLS-1$
			);
        }

        // PAdES BES/Basic
        if (PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_FORMAT, null) != null) {
        	p.put(
    			"signatureSubFilter", //$NON-NLS-1$
    			PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_FORMAT, AOSignConstants.PADES_SUBFILTER_BASIC)
			);
        }

        // Nivel de certificacion de firma firma
        if (PreferencesManager.get(
        		PreferencesManager.PREFERENCE_PADES_CERTIFICATION_LEVEL,
        		null) != null) {

        	p.put(
        		"certificationLevel", //$NON-NLS-1$
        		PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_CERTIFICATION_LEVEL,
        				Integer.toString(CertificationLevelResources.ORDINARY.getIndex()))
    		);

        }

        loadTsaExtraParams(p);
        return p;

	}

	/** Obtiene la configuraci&oacute;n para las firmas CAdES.
	 * @return Propiedades para la configuraci&oacute;n de las firmas CAdES. */
	private static Properties loadCAdESExtraParams() {
		final Properties p = new Properties();

        // Preferencias de politica de firma
        final String policyId = PreferencesManager.get(PreferencesManager.PREFERENCE_CADES_POLICY_IDENTIFIER, ""); //$NON-NLS-1$
        if (!policyId.trim().isEmpty()) {
			p.put("policyIdentifier", policyId); //$NON-NLS-1$
	    }
        if (!PreferencesManager.get(PreferencesManager.PREFERENCE_CADES_POLICY_HASH, "").trim().isEmpty()) { //$NON-NLS-1$
			p.put(
		    	"policyIdentifierHash", //$NON-NLS-1$
		        PreferencesManager.get(PreferencesManager.PREFERENCE_CADES_POLICY_HASH, "") //$NON-NLS-1$
		    );
	    }
	    if (!PreferencesManager.get(PreferencesManager.PREFERENCE_CADES_POLICY_HASH_ALGORITHM, "").trim().isEmpty()) { //$NON-NLS-1$
			p.put(
		    	"policyIdentifierHashAlgorithm", //$NON-NLS-1$
		        PreferencesManager.get(PreferencesManager.PREFERENCE_CADES_POLICY_HASH_ALGORITHM, "") //$NON-NLS-1$
			);
	    }
	    if (!PreferencesManager.get(PreferencesManager.PREFERENCE_CADES_POLICY_QUALIFIER, "").trim().isEmpty()) { //$NON-NLS-1$
			p.put(
		    	"policyQualifier", //$NON-NLS-1$
		        PreferencesManager.get(PreferencesManager.PREFERENCE_CADES_POLICY_QUALIFIER, "") //$NON-NLS-1$
		    );
	    }

        // Preferencias de CAdES
        // Esta propiedad se comparte con otros formatos, hay que comprobar que signer tenemos
        p.put(
    		"mode", //$NON-NLS-1$
    		PreferencesManager.getBoolean(PreferencesManager.PREFERENCE_CADES_IMPLICIT, true) ?
				"implicit" : //$NON-NLS-1$
					"explicit" //$NON-NLS-1$
		);
        
        loadTsaExtraParams(p);
        return p;
	}
	
	/** Obtiene la url de la va del servicio ocsp.
	 * @return Propiedades para la configuraci&oacute;n de servicio de verificaci&oacute;n por ocsp. */
	public static Properties loadValidationExtraParams() {
		final Properties p = new Properties();

        // Url de la va del servicio de validación de certificados por ocsp
        final String UrlVaOcsp = PreferencesManager.get(PreferencesManager.PREFERENCE_URI_VAOSCP_SERVICE, ""); //$NON-NLS-1$
        if (!UrlVaOcsp.trim().isEmpty()) {
			p.put("responderUrlOcsp", UrlVaOcsp); //$NON-NLS-1$
	    }
        return p;
	}
	
	/** Parametros para el sello de tiempo*/
	private static void loadTsaExtraParams(Properties p){
		
        if(PreferencesManager.getBoolean(PreferencesManager.PREFERENCE_TIMESTAMP_CONFIGURE, true)){
            /** URL de la autoridad de sello de tiempo (si no se indica no se a&ntilde;ade sello de tiempo). */
            if (!PreferencesManager.get(PreferencesManager.PREFERENCE_TIMESTAMP_TSA_URL, "").trim().isEmpty()) {  //$NON-NLS-1$
            	p.put(
        			"tsaURL", //$NON-NLS-1$
        			PreferencesManager.get(PreferencesManager.PREFERENCE_TIMESTAMP_TSA_URL, "") //$NON-NLS-1$
    			);
            }
            
            /** Tipo de sello de tiempo a aplicar:
             * <ul>
        	 *  <li>1 = Solo sello a nivel de firma.</li>
        	 *  <li>2 = Solo sello a nivel de documento.</li>
        	 *  <li>3 = Dos sellos, uno a nivel de firma y otro a nivel de documento.</li>
        	 * </ul> */     
            if (!PreferencesManager.get(PreferencesManager.PREFERENCE_TIMESTAMP_STAMP_TYPE, "").trim().isEmpty()) {  //$NON-NLS-1$
            	p.put(
        			"tsType", //$NON-NLS-1$
        			PreferencesManager.get(PreferencesManager.PREFERENCE_TIMESTAMP_STAMP_TYPE, "") //$NON-NLS-1$
    			);
            }

            /** Pol&iacute;tica de sellado de tiempo. */
            if (!PreferencesManager.get(PreferencesManager.PREFERENCE_TIMESTAMP_STAMP_POLICY, "").trim().isEmpty()) {  //$NON-NLS-1$
            	p.put(
        			"tsaPolicy", //$NON-NLS-1$
        			PreferencesManager.get(PreferencesManager.PREFERENCE_TIMESTAMP_STAMP_POLICY, "") //$NON-NLS-1$
    			);
            }
            
            /** Algoritmo de huella digital a usar para el sello de tiempo (si no se establece se usa SHA-1). */
            if (!PreferencesManager.get(PreferencesManager.PREFERENCE_TIMESTAMP_HASHALGORITHM, "").trim().isEmpty()) {  //$NON-NLS-1$
            	p.put(
        			"tsaHashAlgorithm", //$NON-NLS-1$
        			PreferencesManager.get(PreferencesManager.PREFERENCE_TIMESTAMP_HASHALGORITHM, "") //$NON-NLS-1$
    			);
            }
            
            /** Contrase&ntilde;a del usuario de la TSA. Se ignora si no se ha establecido adem&aacute;s <code>tsaUsr</code>. */
            if (!PreferencesManager.get(PreferencesManager.PREFERENCE_TIMESTAMP_TSA_USR, "").trim().isEmpty()) {  //$NON-NLS-1$
            	p.put(
        			"tsaUsr", //$NON-NLS-1$
        			PreferencesManager.get(PreferencesManager.PREFERENCE_TIMESTAMP_TSA_USR, "") //$NON-NLS-1$
    			);
            }
            
            /** Contrase&ntilde;a del usuario de la TSA. Se ignora si no se ha establecido adem&aacute;s <code>tsaUsr</code>. */
            if (!PreferencesManager.get(PreferencesManager.PREFERENCE_TIMESTAMP_TSA_PWD, "").trim().isEmpty()) {  //$NON-NLS-1$
            	p.put(
        			"tsaPwd", //$NON-NLS-1$
        			PreferencesManager.get(PreferencesManager.PREFERENCE_TIMESTAMP_TSA_PWD, "") //$NON-NLS-1$
    			);
            }
            
            
            // parametro que nos indica si existe error o no en la consecución de la firma de la tsa
            System.setProperty("tsaIsError", "false");
        	
        }else{
            /** URL de la autoridad de sello de tiempo (si no se indica no se a&ntilde;ade sello de tiempo). */
        	p.remove("tsaURL");
            
            /** Tipo de sello de tiempo a aplicar:
             * <ul>
        	 *  <li>1 = Solo sello a nivel de firma.</li>
        	 *  <li>2 = Solo sello a nivel de documento.</li>
        	 *  <li>3 = Dos sellos, uno a nivel de firma y otro a nivel de documento.</li>
        	 * </ul> */     
        	p.remove("tsType"); //$NON-NLS-1$

            /** Pol&iacute;tica de sellado de tiempo. */
        	p.remove("tsaPolicy"); //$NON-NLS-1$
            
            /** Algoritmo de huella digital a usar para el sello de tiempo (si no se establece se usa SHA-1). */
        	p.remove("tsaHashAlgorithm"); //$NON-NLS-1$
            /** Contrase&ntilde;a del usuario de la TSA. Se ignora si no se ha establecido adem&aacute;s <code>tsaUsr</code>. */
        	p.remove("tsaUsr"); //$NON-NLS-1$
            /** Contrase&ntilde;a del usuario de la TSA. Se ignora si no se ha establecido adem&aacute;s <code>tsaUsr</code>. */
        	p.remove("tsaPwd"); //$NON-NLS-1$
            
        }
		
	}
	
}
