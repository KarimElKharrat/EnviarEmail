package dad.enviaremail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class GestionarEmail {
	
	public static void enviarEmail(EnviarEmailModel model) throws EmailException {

		Email email = new SimpleEmail();
		
		email.setHostName(model.getIp());
		email.setSmtpPort(model.getPuerto());
		email.setAuthenticator(new DefaultAuthenticator(model.getRemitente(), model.getPassword()));
		email.setSSLOnConnect(model.isSsl());
		email.setFrom(model.getRemitente());
		email.setSubject(model.getAsunto());
		email.setMsg(model.getMensaje());
		email.addTo(model.getDestinatario());
		email.send();
	}
	
}
