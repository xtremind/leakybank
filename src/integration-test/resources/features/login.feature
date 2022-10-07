Feature: Login
  Everybody wants to know when it's Friday

  Scenario: Login OK
    Given a user with a login and with a password
    When the user try to log
    Then the response is redirect

  Scenario: Login without user
    Given a user without a login and with a password
    When the user try to log
    Then the response is ok
    And the answer is with error

  Scenario: Login without password
    Given a user with a login and without a password
    When the user try to log
    Then the response is ok
    And the answer is with error
    
  Scenario: Login with an unknown user
    Given an unknown user
    When the user try to log
    Then the response is ok
    And the answer is with error