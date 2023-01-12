package dad.enviaremail;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class EnviarEmailController implements Initializable {
	
	// logic
	
	private Task<Void> envio;
	
	// model
	
	private ObjectProperty<EnviarEmailModel> model = new SimpleObjectProperty<>();
	
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
		
		// listeners
		
		model.set(new EnviarEmailModel());

		// bindings
		
		model.get().ipProperty().bind(ipText.textProperty());
		model.get().puertoProperty().bind(puertoText.textProperty());
		model.get().remitenteProperty().bind(remitenteText.textProperty());
		model.get().destinatarioProperty().bind(destinatarioText.textProperty());
		model.get().asuntoProperty().bind(asuntoText.textProperty());
		model.get().mensajeProperty().bind(mensajeTextArea.textProperty());
		model.get().passwordProperty().bind(passwordField.textProperty());
		model.get().sslProperty().bind(sslBox.selectedProperty());
		
		// load data

		enviarButton.contentDisplayProperty().set(ContentDisplay.TEXT_ONLY);
		enviarButton.setGraphic(new ImageView(new Image("images/loading.gif")));
		
		
	}

	@FXML
	private void onEnviarAction(ActionEvent e) {
		
		envio = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				GestionarEmail.enviarEmail(model.get());
				updateMessage("Proceso completado");
				return null;
			}
			
		};
		envio.setOnSucceeded(l -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(EnviarEmailApp.primaryStage);
			alert.setTitle("Mensaje enviado");
			alert.setHeaderText("Mensaje enviado con éxito a \"" + model.get().getDestinatario() + "\".");
			alert.showAndWait();
		});
		envio.setOnFailed(l -> {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(EnviarEmailApp.primaryStage);
			alert.setTitle("Error");
			alert.setHeaderText("No se pudo enviar el email.");
			alert.setContentText(l.getSource().getException().getMessage());
			alert.showAndWait();
		});
		envio.setOnCancelled(l -> {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(EnviarEmailApp.primaryStage);
			alert.setTitle("Tarea cancelada");
			alert.setHeaderText("Tarea cancelada");
			alert.show();
		});
		
		enviarButton.disableProperty().bind(envio.runningProperty());
		
		enviarButton.disableProperty().addListener(this::onDisabledChanged);
		
		new Thread(envio).start();
			
	}
	
	private void onDisabledChanged(ObservableValue<? extends Boolean> o, Boolean ov, Boolean nv) {
		
		if(nv)
			enviarButton.contentDisplayProperty().set(ContentDisplay.GRAPHIC_ONLY);
		else
			enviarButton.contentDisplayProperty().set(ContentDisplay.TEXT_ONLY);
		
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
	
	public GridPane getView() {
		return view;
	}
	
}

