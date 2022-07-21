#Author: Muthuukumar Gandhi - Email: muthukumar.gandhi@espacenetworks.io
#This feature file will cover the Test Scenarios on the Home-Page of Neuron_Fleet Application


@tag
Feature: This feature covers test scenarios under the Home-page of Neuron_Fleet application
  
  @smoke @Regression
  Scenario: TC01_Verify that the user is able to login into Neuron Fleet application
    Given I have the URL of Neuron Fleet application
    When I login the Neuron Fleet application with valid username and password
    Then I should be able to login into the Neuron Fleet application
    And I should be able to close the Neuron Fleet application
    
	 