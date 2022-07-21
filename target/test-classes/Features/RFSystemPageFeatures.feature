#Author: Muthuukumar Gandhi - Email: muthukumar.gandhi@espacenetworks.io
#This feature file will cover the Test Scenarios on the RF System-Page of Neuron_Fleet Application


@tag
Feature: This feature covers test scenarios under the RF System-page of Neuron_Fleet application
  
  @smoke
  Scenario: TC01_Verify that the user is able to validate the RF Statistics Page UI of Neuron Fleet application
    Given I access the Neuron Fleet application
    When I navigate to the RF Statistics page
    Then I should be able to validate the UI of the RF Statistics page
    
  @smoke
  Scenario: TC02_Verify that the user is able to validate the Quick Select Filter options in the RF Statistics Page
    Given I access the Neuron Fleet application
    When I navigate to the RF Statistics page
    Then I should be able to validate the Quick Select Filter options in the RF Statistics Page
    
  @smoke
  Scenario: TC03_Verify that the user is able to validate the Default StartDate and EndDate Filter options in the RF Statistics Page
    Given I access the Neuron Fleet application
    When I navigate to the RF Statistics page
    Then I should be able to validate the Default StartDate and EndDate Filter options in the RF Statistics Page
    
  @smoke
  Scenario: TC04_Verify that the user is able to validate that data is displayed in the RF Statistics Page
    Given I access the Neuron Fleet application
    When I navigate to the RF Statistics page
    Then I should be able to validate that data is displayed in the RF Statistics Page
    
 