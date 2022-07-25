#Author: Muthuukumar Gandhi - Email: muthukumar.gandhi@espacenetworks.io
#This feature file will cover the Test Scenarios on the QoeProbe-Page of Neuron_Fleet Application


@tag
Feature: This feature covers test scenarios under the QoeProbe-page of Neuron_Fleet application
  
  @smoke
  Scenario: TC01_Verify that the user is able to validate the QoeProbe Page UI of Neuron Fleet application
    Given I access the Neuron Fleet application
    When I navigate to the QOE Probe page
    Then I should be able to validate the UI of the QOE Probe page
        
 @smoke
  Scenario: TC02_Verify that the user is able to validate the presence of data in the Sites section of QoeProbe Page
    Given I access the Neuron Fleet application
    When I navigate to the QOE Probe page
    Then I should be able to validate the presence of data in the Sites section of QoeProbe Page
        
  @smoke
  Scenario: TC03_Verify that the user is able to validate the colour coding of data in the Sites section of QoeProbe Page
    Given I access the Neuron Fleet application
    When I navigate to the QOE Probe page
    Then I should be able to validate the colour coding of data in the Sites section of QoeProbe Page
    	 
	@smoke
  Scenario: TC04_Verify that the user is able to validate the Dropdown filter options in the Sites section of QoeProbe Page
    Given I access the Neuron Fleet application
    When I navigate to the QOE Probe page
    Then I should be able to validate the Dropdown filter options in the Sites section of QoeProbe Page
        
  @smoke
  Scenario: TC05_Verify that the user is able to validate the Quick Select Filter options in the Sites section of QoeProbe Page
    Given I access the Neuron Fleet application
    When I navigate to the QOE Probe page
    Then I should be able to validate the Quick Select Filter options in the Sites section of QoeProbe Page
        
  @smoke
  Scenario: TC06_Verify that the user is able to validate the Default StartDate and EndDate Filter options in the Sites section of QoeProbe Page
    Given I access the Neuron Fleet application
    When I navigate to the QOE Probe page
    Then I should be able to validate the Default StartDate and EndDate Filter options in the Sites section of QoeProbe Page
        
  @smoke
  Scenario: TC07_Verify that the user is able to validate that data is displayed in the QOE Score Analysis section of QoeProbe Page
    Given I access the Neuron Fleet application
    When I navigate to the QOE Probe page
    Then I should be able to validate that data is displayed in the QOE Score Analysis section of QoeProbe Page
        
  @smoke
  Scenario: TC08_Verify that the user is able to validate that data is displayed in the QOE Score Analysis Page Load Time section of QoeProbe Page
    Given I access the Neuron Fleet application
    When I navigate to the QOE Probe page
    Then I should be able to validate that data is displayed in the QOE Score Analysis Page Load Time section of QoeProbe Page
      
  @smoke
  Scenario: TC09_Verify that the user is able to validate that data is displayed in the QOE probe Metrics section of QoeProbe Page
    Given I access the Neuron Fleet application
    When I navigate to the QOE Probe page
    Then I should be able to validate that data is displayed in the QOE probe Metrics section of QoeProbe Page
    
  @smoke
  Scenario: TC010_Verify that the API call works in the QOE score Analysis
  When I do a POST with required inputs in EndPoint URL of the QOE score Analysis
  Then I should be able to verify the response
      
