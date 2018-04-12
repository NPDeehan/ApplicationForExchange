package camunda.consulting.poc.ApplicationForExchange;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

public class DeleteUserDelegate implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {

		String username = (String) execution.getVariable("username");
		String authorizationId = (String) execution.getVariable("authorizationId");
		
		//Remove the user from the database
		IdentityService identityService = execution.getProcessEngineServices().getIdentityService();
		identityService.deleteUser(username);
		
		//Remove the authorization from the database
		AuthorizationService authorizationService = execution.getProcessEngineServices().getAuthorizationService();
		authorizationService.deleteAuthorization(authorizationId);
		
	}

}
