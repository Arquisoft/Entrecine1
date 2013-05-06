Feature: Testing for register page

Scenario: Login without password
    Given I am in the register page
    Then I type admin in the Usuario input field
    And I click in the Entrar button
    Then I am in the login page

Scenario: Login without username
    Given I am in the register page
    Then I type admin in the Password input field
    And I click in the Entrar button
    Then I am in the login page

Scenario: Login with bad username
    Given I am in the register page
    Then I type admintest in the Usuario input field
    Then I type admin in the Password input field
    And I click in the Entrar button
    Then I am in the login page

Scenario: Login with bad password
    Given I am in the register page
    Then I type admin in the Usuario input field
    Then I type admintest in the Password input field
    And I click in the Entrar button
    Then I am in the login page

Scenario: Successful login
    Given I am in the register page
    Then I type admin in the Usuario input field
    Then I type admin in the Password input field
    And I click in the Entrar button
    Then I am in the main page


