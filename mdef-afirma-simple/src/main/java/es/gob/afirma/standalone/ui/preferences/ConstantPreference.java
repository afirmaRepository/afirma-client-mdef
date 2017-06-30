package es.gob.afirma.standalone.ui.preferences;

public class ConstantPreference {

	private static String[] CIPHER_ALGOS = new String[] { "AES256", //$NON-NLS-1$
			"AES128", //$NON-NLS-1$
//			"Algoritmo 3", //$NON-NLS-1$
	};

	private static String[] ACCESS_METHODS = new String[] { "Metodo 1", //$NON-NLS-1$
			"Metodo 2", //$NON-NLS-1$
			"Metodo 3", //$NON-NLS-1$
	};

	public static String[] getCIPHER_ALGOS() {
		return CIPHER_ALGOS;
	}


	public static String[] getAccessMethods() {
		return ACCESS_METHODS;
	}

	
	public static Integer getAesKeySize(){
		Integer sizeKey = null;
		if(PreferencesManager.get(PreferencesManager.PREFERENCE_CIPHERMENT_ALGORITHM, ConstantPreference.getCIPHER_ALGOS()[0]).equals(ConstantPreference.getCIPHER_ALGOS()[0])){
			sizeKey = Integer.valueOf(256);
		}
		if(PreferencesManager.get(PreferencesManager.PREFERENCE_CIPHERMENT_ALGORITHM, ConstantPreference.getCIPHER_ALGOS()[0]).equals(ConstantPreference.getCIPHER_ALGOS()[1])){
			sizeKey = Integer.valueOf(128);
		}
		return sizeKey;
		
	}	

}
