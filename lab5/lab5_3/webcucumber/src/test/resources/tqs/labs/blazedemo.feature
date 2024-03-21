Feature: Blazedemo

  Scenario: Select cities
    Given that I im on 'https://blazedemo.com/'
    When I select departure and destination cities
    And I click on Find Flights button
    Then I go to the 'BlazeDemo - reserve' page

  Scenario: Reserve fligth
    Given that I im on 'https://blazedemo.com/reserve.php'
    When I select a flight
    Then I go to the 'BlazeDemo Purchase' page

  Scenario: Purchase fligth
    Given that I im on 'https://blazedemo.com/purchase.php'
    When I fill in personal information
    And I proceed to purchase
    Then the purchase is confirmed successfull