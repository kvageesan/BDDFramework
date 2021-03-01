@userflows
Feature: Slack web user flows

  @starmessage
  Scenario: user stars message and searches for the recently starred message
    Given I login to snap web application
    And I send and save a random message
    When I search for starred message using "has:star"
    Then I verify the message is displayed
    And I verify the message  is displayed under saved message section