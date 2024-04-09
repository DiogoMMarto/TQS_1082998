Feature: Book

  Scenario: Select cities
    Given that I im on 'http://localhost:8080/'
    When I select departure and destination cities
    And I select the dates
    And I select return trip
    And I click on Find Trips button
    Then I go to the 'Reservation Details' page

  Scenario: Reserve fligth
    Given that I im on 'http://localhost:8080/reserve?departureCity=New+York&destinationCity=Los+Angeles&dateStart=&dateEnd='
    When I select a seat
    And I click on the 'Reserve' button
    Then I go to the 'Product Purchase' page
    When I fill in personal information
    And I proceed to purchase
    Then I go to the 'Purchase Confirmation' page
    When I click on the 'Confirm' button
    Then I go to the 'Reservation Details' page