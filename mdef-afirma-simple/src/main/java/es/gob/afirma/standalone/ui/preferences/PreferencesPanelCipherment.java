package es.gob.afirma.standalone.ui.preferences;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.gob.afirma.core.ui.AOUIFactory;
import es.gob.afirma.standalone.SimpleAfirma;
import es.gob.afirma.standalone.SimpleAfirmaMessages;
import es.gob.afirma.standalone.crypto.AesEcrypt;
import es.gob.afirma.ws.client.modelo.IdentidadType;
import es.gob.afirma.ws.client.modelo.UsuarioSistemaMailType;
import es.gob.afirma.ws.client.services.ConfigParameters;
import es.gob.afirma.ws.client.services.DicodefClientWs;
import es.gob.afirma.ws.client.services.InicializarSpring;

final class PreferencesPanelCipherment extends JPanel {

	private static final long serialVersionUID = -6602008231996534490L;
	static final Logger LOGGER = Logger.getLogger("es.gob.afirma"); //$NON-NLS-1$

	private static final String URL_DICODEF = "https://dpdes01.mdef.es:10005/Servicios/ConsultarDICODEF"; //$NON-NLS-1$

	private boolean unprotected = false;

	private final JCheckBox onlyEncipherment = new JCheckBox(
			SimpleAfirmaMessages.getString("PreferencesPanelCipherment.21")); //$NON-NLS-1$

	private boolean isOnlyEncipherment() {
		return this.onlyEncipherment.isSelected();
	}

	private final JComboBox<String> cipherAlgorithms = new JComboBox<>(ConstantPreference.getCIPHER_ALGOS());

	String getSelectedCipherAlgorithm() {
		return this.cipherAlgorithms.getSelectedItem().toString();
	}

	private final JComboBox<String> accessMethods = new JComboBox<>(ConstantPreference.getAccessMethods());

	String getSelectedAccessMethod() {
		return this.accessMethods.getSelectedItem().toString();
	}

	private final JTextField directoryURI = new JTextField();

	String getURI() {
		return this.directoryURI.getText();
	}

	private final JButton checkUriButton = new JButton(SimpleAfirmaMessages.getString("PreferencesPanelCipherment.16")); //$NON-NLS-1$

	//............................................................................................................
	//isr 2018
	private final JTextField dicodefUser = new JTextField();
	public JTextField getDirectoryURI() {
		return directoryURI;
	}

	private final JTextField dicodefPassword = new JTextField();
	public JTextField getDicodefUser() {
		return dicodefUser;
	}	
	//............................................................................................................
	
	PreferencesPanelCipherment(final KeyListener keyListener, final ItemListener modificationListener,final boolean unprotected) {
		this.unprotected = unprotected;
		createUI(keyListener, modificationListener);
	}

	// variable para consultar los servicios web de dicoef
	private DicodefClientWs client;

	void savePreferences() {
		
		
		PreferencesManager.putBoolean(PreferencesManager.PREFERENCE_CIPHERMENT_ONLY_CYPHER_CERTS, isOnlyEncipherment());
		PreferencesManager.put(PreferencesManager.PREFERENCE_CIPHERMENT_ALGORITHM, getSelectedCipherAlgorithm());
		PreferencesManager.put(PreferencesManager.PREFERENCE_CIPHERMENT_METHOD, getSelectedAccessMethod());
		PreferencesManager.put(PreferencesManager.PREFERENCE_CIPHERMENT_URI_DICODEF, getURI());
		/*		if (getURI() != null && !getURI().trim().isEmpty()) {
			if (checkURI()) {
				PreferencesManager.put(PreferencesManager.PREFERENCE_CIPHERMENT_URI_DICODEF, getURI());
			} else {
				AOUIFactory.showErrorMessage(getParent(),
						SimpleAfirmaMessages.getString("PreferencesPanelCipherment.18"), //$NON-NLS-1$
						SimpleAfirmaMessages.getString("PreferencesPanelCipherment.19"), //$NON-NLS-1$
						JOptionPane.ERROR_MESSAGE);
			}
		}*/
	}

	void loadPreferences() {

		this.onlyEncipherment.setSelected(
				PreferencesManager.getBoolean(PreferencesManager.PREFERENCE_CIPHERMENT_ONLY_CYPHER_CERTS, true));

		this.cipherAlgorithms.setSelectedItem(
				PreferencesManager.get(PreferencesManager.PREFERENCE_CIPHERMENT_ALGORITHM, ConstantPreference.getCIPHER_ALGOS()[0]));

		this.accessMethods.setSelectedItem(
				PreferencesManager.get(PreferencesManager.PREFERENCE_CIPHERMENT_METHOD, ConstantPreference.getAccessMethods()[0]));

		this.directoryURI.setText(PreferencesManager.get(PreferencesManager.PREFERENCE_CIPHERMENT_URI_DICODEF, "" ));//$NON-NLS-1$
		
		
		if(this.unprotected){
			String dicodefUserDecrypt = "";
			String dicodefPasswordDecrypt = "";
			try {
				dicodefUserDecrypt = AesEcrypt .decrypt(PreferencesManager.get(PreferencesManager.PREFERENCE_CIPHERMENT_USER_DICODEF, "" ));
				dicodefPasswordDecrypt = AesEcrypt.decrypt(PreferencesManager.get(PreferencesManager.PREFERENCE_CIPHERMENT_PASS_DICODEF, "" ));
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			this.dicodefUser.setText(dicodefUserDecrypt);		
			this.dicodefPassword.setText(dicodefPasswordDecrypt);			
		}				
	}

	/*
	AesEcrypt .decrypt(PreferencesManager.get(PreferencesManager.PREFERENCE_CIPHERMENT_USER_DICODEF, "" )),
	AesEcrypt.decrypt(PreferencesManager.get(PreferencesManager.PREFERENCE_CIPHERMENT_PASS_DICODEF, "" ))
	 */
	boolean checkURI() {
		boolean ConectionOK = false;
		if (getURI() != null && !getURI().trim().isEmpty()) {
			try {
				final URL url = new URL(getURI());
				url.toURI();
				LOGGER.info("Se inicia la carga del contexto de spring");
				
				// Se iniciacila el contexto de spring
				ConfigParameters.inicializeValues(
						PreferencesManager.get(PreferencesManager.PREFERENCE_CIPHERMENT_URI_DICODEF, "" ),
						AesEcrypt .decrypt(PreferencesManager.get(PreferencesManager.PREFERENCE_CIPHERMENT_USER_DICODEF, "" )),
						AesEcrypt.decrypt(PreferencesManager.get(PreferencesManager.PREFERENCE_CIPHERMENT_PASS_DICODEF, "" ))
						);
				client = (DicodefClientWs) InicializarSpring.getCtx().getBean(DicodefClientWs.class);
				LOGGER.info("Se acaba la carga del contexto de spring");
				// esto será borrado y cambiado por las opciones de
				// preferencias
				client.setDefaultUri(
						//PreferencesManager.get(PreferencesManager.PREFERENCE_URL_WEB_SERVICES_DICODEF, URL_DICODEF));
						PreferencesManager.get(
								PreferencesManager.PREFERENCE_CIPHERMENT_URI_DICODEF, URL_DICODEF));
				UsuarioSistemaMailType identidad = new UsuarioSistemaMailType();
				identidad.setUid("SILOPDEF03");
				IdentidadType usuario;
				usuario = client.consultarIdentidad(identidad);
				LOGGER.info(usuario.getApellido1());
				LOGGER.info(usuario.getNombre());

				if (!usuario.getNombre().isEmpty()) {
					ConectionOK = true;
					AOUIFactory.showMessageDialog(getParent(),
							SimpleAfirmaMessages.getString("PreferencesPanelCipherment.24"), //$NON-NLS-1$
							SimpleAfirmaMessages.getString("PreferencesPanelCipherment.15"), //$NON-NLS-1$
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					LOGGER.info("no ha recuperado NADA Y ENTRA POR AQUI");
					AOUIFactory.showErrorMessage(getParent(),
							SimpleAfirmaMessages.getString("PreferencesPanelCipherment.23"), //$NON-NLS-1$
							SimpleAfirmaMessages.getString("PreferencesPanelCipherment.19"), //$NON-NLS-1$
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (URISyntaxException e1) {
				LOGGER.info("excepción uri" + e1.getMessage());
				AOUIFactory.showErrorMessage(getParent(),
						SimpleAfirmaMessages.getString("PreferencesPanelCipherment.23"), //$NON-NLS-1$
						SimpleAfirmaMessages.getString("PreferencesPanelCipherment.19"), //$NON-NLS-1$
						JOptionPane.ERROR_MESSAGE);
			} catch (Exception e1) {
				LOGGER.info("excepción común" + e1.getMessage());
				AOUIFactory.showErrorMessage(getParent(),
						SimpleAfirmaMessages.getString("PreferencesPanelCipherment.23"), //$NON-NLS-1$
						SimpleAfirmaMessages.getString("PreferencesPanelCipherment.19"), //$NON-NLS-1$
						JOptionPane.ERROR_MESSAGE);
			}

		}
		return ConectionOK;
	}

	void createUI(final KeyListener keyListener, final ItemListener modificationListener) {

		getAccessibleContext().setAccessibleDescription(SimpleAfirmaMessages.getString("PreferencesPanelCipherment.0")); //$NON-NLS-1$
		

		setLayout(new GridBagLayout());

		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;

		final JPanel cipherConfigPanel = new JPanel(new GridBagLayout());
		cipherConfigPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createTitledBorder(SimpleAfirmaMessages.getString("PreferencesPanelCipherment.1")) //$NON-NLS-1$
		));

		final GridBagConstraints ccc = new GridBagConstraints();
		ccc.fill = GridBagConstraints.HORIZONTAL;
		ccc.weightx = 1.0;
		ccc.gridx = 0;
		ccc.gridy = 0;

		final JPanel keyUsagesPanel = new JPanel(new GridBagLayout());
		keyUsagesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
				SimpleAfirmaMessages.getString("PreferencesPanelCipherment.2") //$NON-NLS-1$
		));

		final GridBagConstraints kupc = new GridBagConstraints();
		kupc.fill = GridBagConstraints.HORIZONTAL;
		kupc.weightx = 1.0;
		kupc.gridx = 0;
		kupc.gridy = 0;
		kupc.insets = new Insets(5, 0, 0, 7);

		this.onlyEncipherment.getAccessibleContext()
				.setAccessibleDescription(SimpleAfirmaMessages.getString("PreferencesPanelCipherment.22") //$NON-NLS-1$
		);
		this.onlyEncipherment.setMnemonic('r');
		this.onlyEncipherment.addItemListener(modificationListener);
		this.onlyEncipherment.addKeyListener(keyListener);
		this.onlyEncipherment.setEnabled(this.unprotected);

		kupc.gridy++;
		keyUsagesPanel.add(this.onlyEncipherment, kupc);

		final JPanel algorithmPanel = new JPanel(new GridBagLayout());
		algorithmPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
				SimpleAfirmaMessages.getString("PreferencesPanelCipherment.12") //$NON-NLS-1$
		));

		final GridBagConstraints apc = new GridBagConstraints();
		apc.fill = GridBagConstraints.NONE;
		apc.anchor = GridBagConstraints.LINE_START;
		apc.weightx = 1.0;
		apc.gridx = 0;
		apc.gridy = 0;
		apc.insets = new Insets(5, 0, 0, 7);

		this.cipherAlgorithms.addKeyListener(keyListener);
		this.cipherAlgorithms.addItemListener(modificationListener);
		this.cipherAlgorithms.setEnabled(unprotected);

		algorithmPanel.add(this.cipherAlgorithms, apc);

		final JPanel repositoryPanel = new JPanel(new GridBagLayout());
		repositoryPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
				SimpleAfirmaMessages.getString("PreferencesPanelCipherment.13") //$NON-NLS-1$
		));

		final GridBagConstraints rpc = new GridBagConstraints();
		rpc.fill = GridBagConstraints.HORIZONTAL;
		rpc.anchor = GridBagConstraints.LINE_START;
		rpc.weightx = 1.0;
		rpc.gridx = 0;
		rpc.gridy = 0;
		//rpc.insets = new Insets(5, 5, 0, 7);

		//....................................................................................................................
		final JLabel dicodefUserLabel = new JLabel("Usuario"); //SimpleAfirmaMessages.getString("Usuario")
		dicodefUserLabel.setLabelFor(this.dicodefUser);
		
		final JLabel dicodefPasswordLabel = new JLabel("Password"); //SimpleAfirmaMessages.getString("Password")
		dicodefPasswordLabel.setLabelFor(this.dicodefPassword);
		//....................................................................................................................
		
		/*
		final JLabel directoryURILabel = new JLabel(SimpleAfirmaMessages.getString("PreferencesPanelCipherment.14") //$NON-NLS-1$
		);
		directoryURILabel.setLabelFor(this.directoryURI);

		this.directoryURI.addKeyListener(keyListener);
		this.directoryURI.setEnabled(unprotected);

		this.checkUriButton.setMnemonic('b');
		this.checkUriButton.getAccessibleContext()
				.setAccessibleDescription(SimpleAfirmaMessages.getString("PreferencesPanelCipherment.17") //$NON-NLS-1$
		);
		
		
		this.checkUriButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				checkURI();
			}
		});

		final JLabel methodLabel = new JLabel(SimpleAfirmaMessages.getString("PreferencesPanelCipherment.15") //$NON-NLS-1$
		);
		methodLabel.setLabelFor(this.accessMethods);

		this.accessMethods.addKeyListener(keyListener);
		this.accessMethods.addItemListener(modificationListener);
		this.accessMethods.setEnabled(unprotected);
*/
		//....................................................................................................................

		rpc.insets = new Insets(0, 0, 0, 7);
		repositoryPanel.add(dicodefUserLabel, rpc);
		rpc.insets = new Insets(0, 70, 0, 7);
		repositoryPanel.add(this.dicodefUser, rpc);
		this.dicodefUser.addKeyListener(keyListener);

		rpc.insets = new Insets(60, 0, 0, 7);
		repositoryPanel.add(dicodefPasswordLabel, rpc);
		rpc.insets = new Insets(60, 70, 0, 7);
		repositoryPanel.add(this.dicodefPassword, rpc);		
		rpc.fill = GridBagConstraints.NONE;
		
		
		
		//....................................................................................................................
		
		/*
		repositoryPanel.add(directoryURILabel, rpc);
		rpc.gridy++;
		rpc.insets = new Insets(50, 0, 0, 7);
		repositoryPanel.add(this.directoryURI, rpc);		
		rpc.gridx++;
		rpc.gridwidth = GridBagConstraints.REMAINDER;
		rpc.weightx = 0.0;
		repositoryPanel.add(this.checkUriButton, rpc);
		rpc.gridx--;
		rpc.gridwidth = GridBagConstraints.LINE_START;
		rpc.gridy++;
		rpc.insets = new Insets(10, 5, 0, 7);
		repositoryPanel.add(methodLabel, rpc);
		rpc.insets = new Insets(50, 0, 0, 7);
		rpc.gridy++;
		repositoryPanel.add(this.accessMethods, rpc);
		*/
		
		cipherConfigPanel.add(keyUsagesPanel, ccc);
		ccc.gridy++;
		ccc.insets = new Insets(20, 0, 0, 0);
		cipherConfigPanel.add(algorithmPanel, ccc);
		ccc.insets = new Insets(20, 0, 0, 0);
		ccc.gridy++;
		
		cipherConfigPanel.add(repositoryPanel, ccc);

		add(cipherConfigPanel, gbc);
		gbc.gridy++;
		gbc.weighty = 1.0;
		add(new JPanel(), gbc);
		loadPreferences();
	}

}
