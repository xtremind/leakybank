Feature: Transactions functionalities

  Scenario: Transaction OK
    Given a user with debitAccount and with creditAccount and with amount
    When the user try to transfer money from one account to another
    Then Money Transfer response is redirect

  Scenario: Transaction without debitAccount
    Given a user without debitAccount and with creditAccount and with amount
    When the user try to transfer money from one account to another
    Then Money Transfer response is ok
    And Money Transfer answer is with error

  Scenario: Transaction without creditAccount
    Given a user with debitAccount and without creditAccount and with amount
    When the user try to transfer money from one account to another
    Then Money Transfer response is ok
    And Money Transfer answer is with error

  Scenario: Transaction without amount
    Given a user with debitAccount and with creditAccount and without amount
    When the user try to transfer money from one account to another
    Then Money Transfer response is ok
    And Money Transfer answer is with error

  Scenario: Transaction on same account
    Given a user with debitAccount and with creditAccount and with amount
    When the user try to transfer money on same account
    Then Money Transfer response is ok
    And Money Transfer answer is with error
