package es.gob.afirma.keystores;

import java.util.List;

import es.gob.afirma.keystores.AOKeyStore;

public class KeyStoreConfigurationGroup {

	
    private final List<AOKeyStore> type;
    private final String name;

    
    public KeyStoreConfigurationGroup(List<AOKeyStore> type, String name) {
		super();
		this.type = type;
		this.name = name;
	}
	public List<AOKeyStore> getType() {
		return type;
	}
	public String getName() {
		return name;
	}
    
    @Override
    public String toString() {
        return this.name;
    }
    
	
}
