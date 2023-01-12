package dad.enviaremail;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class EnviarEmailApp extends Application {
	
	private EnviarEmailController controller;
	public static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		controller = new EnviarEmailController();
		EnviarEmailApp.primaryStage = primaryStage;
		
		primaryStage.setTitle("Enviar Email");
		primaryStage.getIcons().add(new Image(getClass().getResource("/images/email-send-icon-32x32.png").toString()));
		primaryStage.setScene(new Scene(controller.getView()));
		primaryStage.show();

	}

}
