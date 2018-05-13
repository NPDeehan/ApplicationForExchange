package camunda.consulting.poc.ApplicationForExchange;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.camunda.bpm.engine.test.Deployment;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.junit.Assert.*;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
public class InMemoryH2Test {

  @ClassRule
  @Rule
  public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

  private static final String PROCESS_DEFINITION_KEY = "ApplicationForExchange";

  static {
    LogFactory.useSlf4jLogging(); // MyBatis
  }

  @Before
  public void setup() {
    init(rule.getProcessEngine());
  }

  /**
   * Just tests if the process definition is deployable.
   */
  @Test
  @Deployment(resources = {"process.bpmn", "decideOnDistrictRespon.dmn"})
  public void testParsingAndDeployment() {
    // nothing is done here, as we just want to check for exceptions during deployment
  }

  @Test
  @Deployment(resources = {"process.bpmn", "decideOnDistrictRespon.dmn"})
  public void testHappyPath() {
	  
	  
	  ProcessInstance processInstance = 
			  processEngine()
			  .getRuntimeService()
			  .startProcessInstanceByKey(PROCESS_DEFINITION_KEY,
					  withVariables(
							  "candidateFirstName", "Nele", 
							  "candidateLastName", "Camunda", 
							  "candidateEmail", "nele.uhlemann@camunda.com",
							  "DistrictID", 1890,
							  "emailsOn", false
							  ));
	  
	  // Now: Drive the process by API and assert correct behavior by camunda-bpm-assert
  }
  @Test
  @Deployment(resources = {"process.bpmn", "decideOnDistrictRespon.dmn"})
  public void testPDFCreate() {
	  
	  processEngine()
	  .getRuntimeService()
	  .createProcessInstanceByKey(PROCESS_DEFINITION_KEY)
	  .startBeforeActivity("Task_1bogluy")
	  .setVariables( withVariables(
			  "candidateFirstName", "Nele", 
			  "candidateLastName", "Camunda", 
			  "candidateEmail", "nele.uhlemann@camunda.com",
			  "DistrictID", 1890,
			  "emailsOn", false
			  ))
	  
	  .execute();	  
  }

}
