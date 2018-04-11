package camunda.consulting.poc.ApplicationForExchange;

import org.camunda.bpm.engine.delegate.DelegateTask;
import java.awt.HeadlessException;
import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;  
import org.camunda.bpm.engine.delegate.TaskListener;

public class SendTaskEmail implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {


		String taskId = delegateTask.getId();
		String userId = delegateTask.getAssignee();
		Boolean emailsOn = (Boolean) delegateTask.getVariable("emailsOn");
		String server = (String) delegateTask.getVariable("ServerURL");
		
		String firstName = (String) delegateTask.getVariable("candidateFirstName");
		String lastName = (String) delegateTask.getVariable("candidateLastName");
		String emailAddress = (String) delegateTask.getVariable("candidateEmail");
		
		String userName = (String) delegateTask.getVariable("userName");
		String password = (String) delegateTask.getVariable("password");
		
		if(server == null) {
			server = "http://localhost:8080/";
		}
		if(emailsOn == null) {
			emailsOn = true;
		}
		
		String taskLink = server+"camunda/app/tasklist/default/#/?task="+taskId;
		if(emailsOn) {
			email(userId, taskLink, firstName, lastName, userName, password, emailAddress);
		}else {
			System.out.println("Task emails are turned off... ");
		}

	}
	
	public void email(String userId, String taskLink, String firstName, String lastName, String userName, String userPassword, String emailAddress) 
	{
		
        String from = "rebbrownbotw@gmail.com";
        String password = "SuperPass";
        String to = emailAddress;
        String subject = "Task needs to be done";
        String msg = 
        		"Hi "  + firstName + 
        		"\n\n" + 
        		"You have a task waiting, please follow this link: "
        				+ "\n" + taskLink
        				+ "\n\n\n" + " Please log in with the following Credentials " +"\n"
        				+ "User Name = " + userName + "\n" 
        				+ "password = " + userPassword + "\n" 
        				;
        Properties properties = System.getProperties();
        properties = setProp(from, to);
        Session session = Session.getDefaultInstance(properties);

        try {
            Address address = new InternetAddress(to, "Rotory Club Exchange Program");
            MimeMessage message = new MimeMessage(session);
            message.setFrom(address);
            message.addRecipient(Message.RecipientType.TO, address);
            message.setSubject(subject);
            message.setText(msg);
            message.saveChanges();
            Transport transport = session.getTransport();
            System.out.println("connecting...");
            transport.connect(from, password);
            System.out.println("connected!");
            System.out.println("sending...");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("Sent message successfully....");

        } catch (Exception mex) {
            mex.printStackTrace();
        }

    }

    private Properties setProp(String email, String targetEmail) {
        Properties props = null;
        try {
            props = System.getProperties();
            if (email.contains(",") || targetEmail.contains(",")) {
                System.out.println("Please send one email to one person at a time...");
            } else if (email.contains("@yahoo.com")) {
                props.put("mail.smtp.host", "smtp.mail.yahoo.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.host", "smtp.mail.yahoo.com");
                props.put("mail.smtp.auth", true);
            } else if (email.contains("@gmail.com")) {
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");
                props.put("mail.transport.protocol", "smtp");
            } else {
                System.out.println("Your Email Address is invalid\n or host not supported!");
            }

        } catch (HeadlessException exp) {
            System.out.println(exp);
        }
        return props;
    }


}
