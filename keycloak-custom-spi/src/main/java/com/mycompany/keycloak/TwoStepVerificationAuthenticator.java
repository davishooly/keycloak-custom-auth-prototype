package com.mycompany.keycloak;

import jakarta.ws.rs.core.MultivaluedMap;
import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;

import java.security.SecureRandom;
import jakarta.ws.rs.core.Response;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;


public class TwoStepVerificationAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(TwoStepVerificationAuthenticator.class);
    private static final String STEP_ATTR = "two_step_current_step";
    private static final String VERIFICATION_DATA = "verification_data";
    private static final SecureRandom random = new SecureRandom();

    @Override
    public void authenticate(AuthenticationFlowContext context) {
      // Step:1 Display phone number collection form
        Response challengeResponse = context.form()
                .setAttribute("step", '1')
                .createForm("two-step-verification.ftl");
        context.challenge(challengeResponse);

    }

    @Override
    public void action(AuthenticationFlowContext authenticationFlowContext) {
       // Handle form action
        MultivaluedMap<String, String> formData = authenticationFlowContext.getHttpRequest().getDecodedFormParameters();

        if (formData.containsKey("cancel")) {
            authenticationFlowContext.resetFlow();
            return;
        }

        String currentStep = authenticationFlowContext.getAuthenticationSession().getAuthNote(STEP_ATTR);

        switch (currentStep) {
            case "1" -> handleStepOne(authenticationFlowContext, formData);
            case "2" -> handleStepTwo(authenticationFlowContext, formData);
            default -> {
                logger.errorf("Unknown step: %s", currentStep);
                authenticationFlowContext.failure(AuthenticationFlowError.INTERNAL_ERROR);
            }
        }
    }

    private void handleStepOne(AuthenticationFlowContext authenticationFlowContext, MultivaluedMap<String, String> formData) {

    }

    private void handleStepTwo(AuthenticationFlowContext authenticationFlowContext, MultivaluedMap<String, String> formData) {}

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        return false;
    }

    @Override
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {

    }

    @Override
    public void close() {

    }
}
