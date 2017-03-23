package es.gob.afirma.standalone.ui.preferences;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.gob.afirma.core.ui.AOUIFactory;
import es.gob.afirma.standalone.SimpleAfirmaMessages;
import es.gob.afirma.ws.client.modelo.IdentidadType;
import es.gob.afirma.ws.client.modelo.UsuarioSistemaMailType;
import es.gob.afirma.ws.client.services.DicodefClientWs;
import es.gob.afirma.ws.client.services.InicializarSpring;

/** Panel de preferencias para la validaci&oacute;n de certificados. */
public class PreferencesPanelValideCerts extends JPanel {

	private static final long serialVersionUID = 3776016646875294106L;

	private final JTextField vaURI = new JTextField();

	String getVaURI() {
		return this.vaURI.getText();
	}

	private final JTextField crlURI = new JTextField();

	String getCrlURI() {
		return this.crlURI.getText();
	}

	private final JCheckBox pssdef = new JCheckBox(SimpleAfirmaMessages.getString("PreferencesPanelValideCerts.3"), //$NON-NLS-1$
			false);

	private final JTextField pssdefURI = new JTextField();

	String getPssdefURI() {
		return this.pssdefURI.getText();
	}

	private final JButton checkUriButton = new JButton(SimpleAfirmaMessages.getString("PreferencesPanelCipherment.16")); //$NON-NLS-1$

	// variable para consultar los servicios web de dicoef
	private DicodefClientWs client;

	static final Logger LOGGER = Logger.getLogger("es.gob.afirma"); //$NON-NLS-1$
	private static final String URL_DICODEF = "https://dpdes01.mdef.es:10005/Servicios/ConsultarDICODEF"; //$NON-NLS-1$

	PreferencesPanelValideCerts(final KeyListener keyListener, final ModificationListener modificationListener,
			final boolean unprotected) {

		createUI(keyListener, modificationListener, unprotected);
	}

	void createUI(final KeyListener keyListener, final ModificationListener modificationListener,
			final boolean unprotected) {

		getAccessibleContext().setAccessibleDescription(SimpleAfirmaMessages.getString("PreferencesPanelValideCerts.0") //$NON-NLS-1$
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

		final JLabel vaUriLabel = new JLabel(SimpleAfirmaMessages.getString("PreferencesPanelValideCerts.4") //$NON-NLS-1$
		);
		vaUriLabel.setLabelFor(this.vaURI);

		final JLabel crlUriLabel = new JLabel(SimpleAfirmaMessages.getString("PreferencesPanelValideCerts.5") //$NON-NLS-1$
		);
		crlUriLabel.setLabelFor(this.crlURI);

		final JLabel pssdefUriLabel = new JLabel(SimpleAfirmaMessages.getString("PreferencesPanelValideCerts.6") //$NON-NLS-1$
		);
		pssdefUriLabel.setLabelFor(this.pssdefURI);

		mdefPanel.add(vaUriLabel, mdpc);
		mdpc.gridy++;
		mdefPanel.add(this.vaURI, mdpc);
		mdpc.gridy++;
		mdefPanel.add(crlUriLabel, mdpc);
		mdpc.gridy++;
		mdefPanel.add(this.crlURI, mdpc);
		mdpc.gridy++;
		mdefPanel.add(this.pssdef, mdpc);
		mdpc.gridy++;
		mdefPanel.add(pssdefUriLabel, mdpc);
		mdpc.gridy++;
		mdefPanel.add(this.pssdefURI, mdpc);

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

		this.checkUriButton.getAccessibleContext()
				.setAccessibleDescription(SimpleAfirmaMessages.getString("PreferencesPanelCipherment.17") //$NON-NLS-1$
		);
		this.checkUriButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					LOGGER.info("Se inicia la carga del contexto de spring");
					// Se iniciacila el contexto de spring
					client = (DicodefClientWs) InicializarSpring.getCtx().getBean(DicodefClientWs.class);
					LOGGER.info("Se acaba la carga del contexto de spring");
					// esto será borrado y cambiado por las opciones de
					// preferencias
					client.setDefaultUri(PreferencesManager.get(PreferencesManager.PREFERENCE_URL_WEB_SERVICES_DICODEF,
							URL_DICODEF));
					UsuarioSistemaMailType identidad = new UsuarioSistemaMailType();
					identidad.setUid("SILOPDEF03");
					IdentidadType usuario;
					usuario = client.consultarIdentidad(identidad);
					LOGGER.info(usuario.getApellido1());
					LOGGER.info(usuario.getNombre());

					if (!usuario.getNombre().isEmpty()) {
						AOUIFactory.showMessageDialog(getParent(),
								SimpleAfirmaMessages.getString("PreferencesPanelCipherment.20"), //$NON-NLS-1$
								SimpleAfirmaMessages.getString("PreferencesPanelCipherment.19"), //$NON-NLS-1$
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						LOGGER.info("no ha recuperado NADA Y ENTRA POR AQUI");
						AOUIFactory.showErrorMessage(getParent(),
								SimpleAfirmaMessages.getString("PreferencesPanelCipherment.18"), //$NON-NLS-1$
								SimpleAfirmaMessages.getString("PreferencesPanelCipherment.19"), //$NON-NLS-1$
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (URISyntaxException e1) {
					LOGGER.info("excepción uri"+e1.getMessage());
					AOUIFactory.showErrorMessage(getParent(),
							SimpleAfirmaMessages.getString("PreferencesPanelCipherment.18"), //$NON-NLS-1$
							SimpleAfirmaMessages.getString("PreferencesPanelCipherment.19"), //$NON-NLS-1$
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e1) {
					LOGGER.info("excepción común"+e1.getMessage());
					AOUIFactory.showErrorMessage(getParent(),
							SimpleAfirmaMessages.getString("PreferencesPanelCipherment.18"), //$NON-NLS-1$
							SimpleAfirmaMessages.getString("PreferencesPanelCipherment.19"), //$NON-NLS-1$
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		mdpc.gridy++;
		mdefPanel.add(this.checkUriButton, mdpc);

		add(mdefPanel, gbc);
		gbc.gridy++;
		add(noMdefPanel, gbc);
		gbc.gridy++;
		gbc.weighty = 1.0;
		add(new JPanel(), gbc);

	}
}
