#Author: Muthuukumar Gandhi - Email: muthukumar.gandhi@espacenetworks.io
#This feature file will cover the Test Scenarios on the WAN-Page of Neuron_Fleet Application


@tag
Feature: This feature covers test scenarios under the WAN-page of Neuron_Fleet application
  
  @smoke
  Scenario: TC01_Verify that the user is able to validate the WAN Performance Page UI of Neuron Fleet application
    Given I access the Neuron Fleet application
    When I navigate to the Wan Performance page
    Then I should be able to validate the UI of the Wan Performance page
    
  @smoke
  Scenario: TC02_Verify that the user is able to validate the presence of various graphs under WAN Performance Page
    Given I access the Neuron Fleet application
    When I navigate to the Wan Performance page
    Then I should be able to validate the presence of various graphs under WAN Performance Page
    
  @smoke1
  Scenario: TC03_Verify that the user is able to validate the presence of graphical data under WAN Performance Page
    Given I access the Neuron Fleet application
    When I navigate to the Wan Performance page
    Then I should be able to validate the presence of graphical data under WAN Performance Page
    
    
    