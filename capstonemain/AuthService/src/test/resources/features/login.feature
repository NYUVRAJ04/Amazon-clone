Feature: User Login

  Scenario: Successful login for a registered user
    Given a user with email "user@example.com" and password "password123" exists
    When the user attempts to login with email "user@example.com" and password "password123"
    Then the login should be successful
    And a JWT token should be generated
    And the response should contain the user's name, role, and relevant IDs

  Scenario: Unsuccessful login due to incorrect password
    Given a user with email "user@example.com" and password "password123" exists
    When the user attempts to login with email "user@example.com" and password "wrongpassword"
    Then the login should fail
    And an error message "Invalid password" should be returned

  Scenario: Unsuccessful login due to non-existent user
    Given no user with email "nonexistent@example.com" exists
    When the user attempts to login with email "nonexistent@example.com" and password "password123"
    Then the login should fail
    And an error message "User not found" should be returned
