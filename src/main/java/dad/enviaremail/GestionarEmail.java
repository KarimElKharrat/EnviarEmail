package dad.enviaremail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class GestionarEmail {
	
	public static void enviarEmail(String host, String nombre, String password, String emailFrom, 
			String emailTo, String asunto, String mensaje, int puerto, boolean ssl) throws EmailException {
		Email email = new SimpleEmail();
		
		email.setHostName(host);
		email.setSmtpPort(puerto);
		email.setAuthenticator(new DefaultAuthenticator(nombre, password));
		email.setSSLOnConnect(ssl);
		email.setFrom(emailFrom);
		email.setSubject(asunto);
		email.setMsg(mensaje);
		email.addTo(emailTo);
		email.send();
	}
	
}
