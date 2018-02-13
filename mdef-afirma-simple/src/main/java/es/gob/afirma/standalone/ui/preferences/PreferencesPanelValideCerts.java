package es.gob.afirma.standalone.ui.preferences;

import static es.gob.afirma.standalone.ui.preferences.PreferencesManager.PREFERENCE_CHECK_CERTIFICATE_PSSDEF;
import static es.gob.afirma.standalone.ui.preferences.PreferencesManager.PREFERENCE_CN_CA_CERT_SERVICE;
import static es.gob.afirma.standalone.ui.preferences.PreferencesManager.PREFERENCE_KEY_SUBJET_PSSDEF_SERVICE;
import static es.gob.afirma.standalone.ui.preferences.PreferencesManager.PREFERENCE_SEVICE_POLICE_PSSDEF_SERVICE;
import static es.gob.afirma.standalone.ui.preferences.PreferencesManager.PREFERENCE_URI_CRL_SERVICE;
import static es.gob.afirma.standalone.ui.preferences.PreferencesManager.PREFERENCE_URI_PSSDEF_SERVICE;
import static es.gob.afirma.standalone.ui.preferences.PreferencesManager.PREFERENCE_URI_VAOSCP_SERVICE;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.gob.afirma.standalone.SimpleAfirma;
import es.gob.afirma.standalone.SimpleAfirmaMessages;
import es.gob.afirma.standalone.smartWaper.ConfigurePssdefPropeties;
import es.gob.afirma.standalone.smartWaper.PssdefMapping;

/** Panel de preferencias para la validaci&oacute;n de certificados. */
public class PreferencesPanelValideCerts extends JPanel {

	private static final long serialVersionUID = 3776016646875294106L;

	private final JTextField cnCaURI = new JTextField();

	JTextField getCnCaURI() {
		return this.cnCaURI;
	}

	private final JTextField vaURI = new JTextField();

	String getVaURI() {
		return this.vaURI.getText();
	}

	private final JTextField crlURI = new JTextField();

	String getCrlURI() {
		return this.crlURI.getText();
	}


	private final JCheckBox vaURICheck = new JCheckBox(SimpleAfirmaMessages.getString("PreferencesPanelValideCerts.9")); //$NON-NLS-1$
	private final JCheckBox pssdef = new JCheckBox(SimpleAfirmaMessages.getString("PreferencesPanelValideCerts.3")); //$NON-NLS-1$

//	boolean ispssdefSelected() {
//		return this.pssdef.isSelected();
//	}
//
//	boolean ispvaURIChecked() {
//		return this.vaURICheck.isSelected();
//	}
	
	private final JTextField pssdefURI = new JTextField();

	private final JTextField keySubjectName = new JTextField();

	private final JTextField sevicePolice = new JTextField();

	String getPssdefURI() {
		return this.pssdefURI.getText();
	}


	static final Logger LOGGER = Logger.getLogger("es.gob.afirma"); //$NON-NLS-1$

	PreferencesPanelValideCerts(final KeyListener keyListener, final ModificationListener modificationListener,
			final boolean unprotected) {

		createUI(keyListener, modificationListener, unprotected);
	}

	void createUI(final KeyListener keyListener, final ModificationListener modificationListener,
			final boolean unprotected) {

		getAccessibleContext().setAccessibleDescription(
			SimpleAfirmaMessages.getString("PreferencesPanelValideCerts.0") //$NON-NLS-1$
		);

		setLayout(new GridBagLayout());

		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;

		final JPanel mdefPanel = new JPanel(new GridBagLayout());
		mdefPanel.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createTitledBorder(SimpleAfirmaMessages.getString("PreferencesPanelValideCerts.1")) //$NON-NLS-1$
		));

		final GridBagConstraints mdpc = new GridBagConstraints();
		mdpc.fill = GridBagConstraints.HORIZONTAL;
		mdpc.weightx = 1.0;
		mdpc.gridx = 0;
		mdpc.gridy = 0;
		mdpc.insets = new Insets(5, 0, 0, 7);

//		final JLabel vaCnCaLabel = new JLabel(SimpleAfirmaMessages.getString("PreferencesPanelValideCerts.9") //$NON-NLS-1$
//		);

//		vaCnCaLabel.setLabelFor(this.cnCaURI);

		final JLabel vaUriLabel = new JLabel(SimpleAfirmaMessages.getString("PreferencesPanelValideCerts.4") //$NON-NLS-1$
		);
		vaUriLabel.setLabelFor(this.vaURI);

		final JLabel crlUriLabel = new JLabel(SimpleAfirmaMessages.getString("PreferencesPanelValideCerts.5") //$NON-NLS-1$
		);
		crlUriLabel.setLabelFor(this.crlURI);

		final JLabel pssdefUriLabel = new JLabel(SimpleAfirmaMessages.getString("PreferencesPanelValideCerts.6") //$NON-NLS-1$
		);
		pssdefUriLabel.setLabelFor(this.pssdefURI);
	    this.pssdefURI.addKeyListener(modificationListener);
	    this.pssdefURI.addKeyListener(keyListener);


		final JLabel keySubjectNameLabel = new JLabel(
			SimpleAfirmaMessages.getString("PreferencesPanelValideCerts.7") //$NON-NLS-1$
		);
		keySubjectNameLabel.setLabelFor(this.keySubjectName);
	    this.keySubjectName.addKeyListener(modificationListener);
	    this.keySubjectName.addKeyListener(keyListener);

		final JLabel sevicePoliceLabel = new JLabel(SimpleAfirmaMessages.getString("PreferencesPanelValideCerts.8") //$NON-NLS-1$
		);
		sevicePoliceLabel.setLabelFor(this.sevicePolice);
	    this.sevicePolice.addKeyListener(modificationListener);
	    this.sevicePolice.addKeyListener(keyListener);


//		mdefPanel.add(vaCnCaLabel, mdpc);
//		mdpc.gridy++;
//		mdefPanel.add(this.cnCaURI, mdpc);		
//		mdpc.gridy++;
		this.vaURICheck.setMnemonic('U');
		this.vaURICheck.addItemListener(modificationListener);
		this.vaURICheck.addKeyListener(keyListener);
		this.vaURICheck.setSelected(PreferencesManager.getBoolean(
				PreferencesManager.PREFERENCE_CN_CA_CERT_SERVICE,
				false
			)
		);
		mdpc.gridy++;
//		this.vaURICheck.addItemListener(
//			e -> {
//				if (e.getStateChange() == ItemEvent.SELECTED) {
//					enableComponents(true);
//				}
//				else {
//					enableComponents(false);
//				}
//			}
//		);

		mdefPanel.add(this.vaURICheck, mdpc);
		mdpc.gridy++;
		mdefPanel.add(vaUriLabel, mdpc);
		mdpc.gridy++;
		mdefPanel.add(this.vaURI, mdpc);
		mdpc.gridy++;
		mdefPanel.add(crlUriLabel, mdpc);
		mdpc.gridy++;
		mdefPanel.add(this.crlURI, mdpc);
		mdpc.gridy++;
		this.pssdef.setMnemonic('U');
		this.pssdef.addItemListener(modificationListener);
		this.pssdef.addKeyListener(keyListener);
		this.pssdef.setSelected(PreferencesManager.getBoolean(
				PreferencesManager.PREFERENCE_CHECK_CERTIFICATE_PSSDEF,
				false
			)
		);
		this.pssdef.addItemListener(
			e -> {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					enableComponents(true);
				}
				else {
					enableComponents(false);
				}
			}
		);

		mdefPanel.add(this.pssdef, mdpc);
		mdpc.gridy++;
		mdefPanel.add(pssdefUriLabel, mdpc);
		mdpc.gridy++;
		mdefPanel.add(this.pssdefURI, mdpc);
		mdpc.gridy++;
		mdefPanel.add(keySubjectNameLabel, mdpc);
		mdpc.gridy++;
		mdefPanel.add(this.keySubjectName, mdpc);
		mdpc.gridy++;
		mdefPanel.add(sevicePoliceLabel, mdpc);
		mdpc.gridy++;
		mdefPanel.add(this.sevicePolice, mdpc);

		final JPanel noMdefPanel = new JPanel(new GridBagLayout());
		noMdefPanel.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createTitledBorder(SimpleAfirmaMessages.getString("PreferencesPanelValideCerts.2")) //$NON-NLS-1$
		));

		final GridBagConstraints nmdpc = new GridBagConstraints();
		nmdpc.fill = GridBagConstraints.HORIZONTAL;
		nmdpc.weightx = 1.0;
		nmdpc.gridx = 0;
		nmdpc.gridy = 0;
		nmdpc.insets = new Insets(5, 0, 0, 7);


		add(mdefPanel, gbc);
		//gbc.gridy++;
		//add(noMdefPanel, gbc);
		gbc.gridy++;
		gbc.weighty = 1.0;
		add(new JPanel(), gbc);

		loadPreferences();
		enableComponents(this.pssdef.isSelected());
		//enableComponents(this.vaURICheck.isSelected());

	}

	void savePreferences() {
		PreferencesManager.putBoolean(PREFERENCE_CN_CA_CERT_SERVICE, this.vaURICheck.isSelected());
		PreferencesManager.put(PREFERENCE_URI_VAOSCP_SERVICE, this.vaURI.getText());
		//PreferencesManager.put(PREFERENCE_URI_VAOSCP_SERVICE, this.vaURICheck.isSelected());
		PreferencesManager.put(PREFERENCE_URI_CRL_SERVICE, getCrlURI());
		// Opciones de la plataforma PSSDEF
		PreferencesManager.putBoolean(PREFERENCE_CHECK_CERTIFICATE_PSSDEF, this.pssdef.isSelected());
		PreferencesManager.put(PREFERENCE_URI_PSSDEF_SERVICE, this.pssdefURI.getText());
		PreferencesManager.put(PREFERENCE_KEY_SUBJET_PSSDEF_SERVICE, this.keySubjectName.getText());
		PreferencesManager.put(PREFERENCE_SEVICE_POLICE_PSSDEF_SERVICE, this.sevicePolice.getText());
    	ConfigurePssdefPropeties.chagePssdefProperties();

	}

	void loadPreferences() {
		this.vaURICheck.setSelected(PreferencesManager.getBoolean(PREFERENCE_CN_CA_CERT_SERVICE, false)); //$NON-NLS-1$
		//this.cnCaURI.setText( String.join(" ",SimpleAfirma.arrayIssuerverifiedCaChain));
		this.vaURI.setText(PreferencesManager.get(PREFERENCE_URI_VAOSCP_SERVICE, "")); //$NON-NLS-1$
		this.crlURI.setText(PreferencesManager.get(PREFERENCE_URI_CRL_SERVICE, "")); //$NON-NLS-1$
		// Opciones de la plataforma PSSDEF
		this.pssdef.setSelected(PreferencesManager.getBoolean(PREFERENCE_CHECK_CERTIFICATE_PSSDEF, false));
		this.pssdefURI.setText(PreferencesManager.get(PREFERENCE_URI_PSSDEF_SERVICE, "")); //$NON-NLS-1$
		this.keySubjectName.setText(PreferencesManager.get(PREFERENCE_KEY_SUBJET_PSSDEF_SERVICE, "")); //$NON-NLS-1$
		this.sevicePolice.setText(PreferencesManager.get(PREFERENCE_SEVICE_POLICE_PSSDEF_SERVICE, "")); //$NON-NLS-1$
		if(this.pssdefURI.getText().isEmpty()){
			this.pssdefURI.setText(PssdefMapping.getUrlPssdefDefault());
		}
		if(this.keySubjectName.getText().isEmpty()){
			this.keySubjectName.setText(PssdefMapping.getKeySubjectNameDefault());

		}
		if(this.sevicePolice.getText().isEmpty()){
			this.sevicePolice.setText(PssdefMapping.getDsvServPolicyDefault());

		}

	}

	void enableComponents(final boolean enable) {
		this.pssdefURI.setEnabled(enable);
		this.keySubjectName.setEnabled(enable);
		this.sevicePolice.setEnabled(enable);
	}


}
