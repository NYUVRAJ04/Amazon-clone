package org.happiest.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.happiest.dto.AuthResponse;
import org.happiest.model.Users;
import org.happiest.repository.UserRepository;
import org.happiest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;

public class LoginSteps {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private ResponseEntity<AuthResponse> response;

    @Given("a user with name {string}, email {string}, and password {string} exists")
    public void a_user_with_name_email_and_password_exists(String name, String email, String password) {
        Users user = new Users();
        user.setName(name); // Use the parameter to set the name dynamically
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("buyer");
        userRepository.save(user);
    }


    @When("the user attempts to login with email {string} and password {string}")
    public void the_user_attempts_to_login_with_email_and_password(String email, String password) {
        Users user = new Users();
        user.setEmail(email);
        user.setPassword(password);
        response = userService.verify(user);
    }

    @Then("the login should be successful")
    public void the_login_should_be_successful() {
        assertEquals(200, response.getStatusCodeValue());
    }

    @Then("a JWT token should be generated")
    public void a_jwt_token_should_be_generated() {
        assertNotNull(response.getBody().getToken());
    }

    @Then("the response should contain the user's name, role, and relevant IDs")
    public void the_response_should_contain_the_user_s_name_role_and_relevant_i_ds() {
        AuthResponse authResponse = response.getBody();
        assertNotNull(authResponse.getName());
        assertNotNull(authResponse.getRole());
        if (authResponse.getRole().equalsIgnoreCase("buyer")) {
            assertNotNull(authResponse.getBuyerId());
        } else if (authResponse.getRole().equalsIgnoreCase("seller")) {
            assertNotNull(authResponse.getSellerId());
        }
    }

    @Then("the login should fail")
    public void the_login_should_fail() {
        assertEquals(401, response.getStatusCodeValue());
    }

    @Then("an error message {string} should be returned")
    public void an_error_message_should_be_returned(String errorMessage) {
        assertEquals(errorMessage, response.getBody().getMessage());
    }

    @Given("no user with email {string} exists")
    public void no_user_with_email_exists(String email) {
        userRepository.deleteById(email);
    }
}
