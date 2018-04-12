package camunda.consulting.poc.ApplicationForExchange;

import java.util.Random;


import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;

public class CreateUserListener implements JavaDelegate {

	Random rando = new Random();

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String firstName = (String)execution.getVariable("candidateFirstName");
		String lastName = (String)execution.getVariable("candidateLastName");
		String email = (String) execution.getVariable("candidateEmail");
	
		String password = getRandomPassword();
		String userName = generateUserName(execution, firstName, lastName);
		execution.setVariable("password", password);
		execution.setVariable("userName", userName);

		IdentityService identityService = execution.getProcessEngineServices()
		.getIdentityService();
		
	      User user = identityService.newUser(userName);
	      user.setFirstName(firstName);
	      user.setLastName(lastName);
	      user.setPassword(password);
	      user.setEmail(email);
	      identityService.saveUser(user);

	}
	
	public String getRandomPassword() {
		//return "password";
//	    StringBuffer password = new StringBuffer(20);
//	    int next = rando.nextInt(13) + 8;
//	    password.append(rando.);
		 String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	        StringBuilder salt = new StringBuilder();
	        Random rnd = new Random();
	        while (salt.length() < 8) { // length of the random string.
	            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
	            salt.append(SALTCHARS.charAt(index));
	        }
	        String saltStr = salt.toString();
	        return saltStr;
	}
	
	public String generateUserName(DelegateExecution execution, String firstName, String lastName) {
		
		
		String userName = null;
		IdentityService identityService = execution.getProcessEngineServices()
				.getIdentityService();
		
	     User singleResult = identityService.createUserQuery().userId(firstName+lastName).singleResult();
	      if (singleResult == null) {
	        return firstName+lastName;
	      } else {
	    	 userName = generateUserName(execution, firstName, lastName+rando.nextInt());
	      }
	      
	      return userName;
	}



}
