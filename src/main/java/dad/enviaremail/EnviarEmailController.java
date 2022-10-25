package dad.enviaremail;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class EnviarEmailController implements Initializable {
	
	// model
	
	private EnviarEmailModel model = new EnviarEmailModel();
	
	// view
	
	@FXML
	private GridPane view;
	
	@FXML
	private CheckBox sslBox;
	
	@FXML
	private TextField ipText, puertoText, remitenteText, destinatarioText, asuntoText;
	
	@FXML
	private TextArea mensajeTextArea;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private VBox buttonsPanel;
	
	@FXML
	private Button enviarButton, vaciarButton, cerrarButton;
	
	public EnviarEmailController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EnviarEmailView.fxml"));
		loader.setController(this);
		loader.load();
	}

	public void initialize(URL location, ResourceBundle resources) {
		
		// bindings
		
		model.ipProperty().bind(ipText.textProperty());
		model.puertoProperty().bind(puertoText.textProperty());
		model.remitenteProperty().bind(remitenteText.textProperty());
		model.destinatarioProperty().bind(destinatarioText.textProperty());
		model.asuntoProperty().bind(asuntoText.textProperty());
		model.mensajeProperty().bind(mensajeTextArea.textProperty());
		model.passwordProperty().bind(passwordField.textProperty());
		model.sslProperty().bind(sslBox.selectedProperty());
		
		
	}
	
	@FXML
	private void onEnviarAction(ActionEvent e) {
		try {
			GestionarEmail.enviarEmail(model.getIp(), model.getRemitente(), model.getPassword(), model.getRemitente(), 
					model.getDestinatario(), model.getAsunto(), model.getMensaje(), model.getPuerto(), model.isSsl());
			emailEnviado(model.getDestinatario());
		} catch(Exception exception) {
			emailError(exception.getMessage());
		}
	}
	
	@FXML
	private void onVaciarAction(ActionEvent e) {
		ipText.setText("");
		puertoText.setText("");
		remitenteText.setText("");
		destinatarioText.setText("");
		asuntoText.setText("");
		mensajeTextArea.setText("");
		passwordField.setText("");
		sslBox.setSelected(false);
	}
	
	@FXML
	private void onCerrarAction(ActionEvent e) {
		EnviarEmailApp.primaryStage.close();
	}
	
	private void emailError(String exceptionMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(EnviarEmailApp.primaryStage); // para que la ventana salga centralizada en torno al dueño
		alert.setTitle("Error");
		alert.setHeaderText("No se pudo enviar el email.");
		alert.setContentText(exceptionMessage);
		alert.showAndWait();
	}
	
	private void emailEnviado(String email) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(EnviarEmailApp.primaryStage); // para que la ventana salga centralizada en torno al dueño
		alert.setTitle("Mensaje enviado");
		alert.setHeaderText("Mensaje enviado con éxito a \"" + email + "\".");
		alert.showAndWait();
		
		destinatarioText.setText("");
		asuntoText.setText("");
		mensajeTextArea.setText("");
	}

	public GridPane getView() {
		return view;
	}
}
