package com.mycompany.keycloak;

// Restricts which classes can implement this interface
public interface AuthenticationStep {

//    permits AuthenticationStep.PhoneCollection,AuthenticationStep.CodeVerification
//
//    {
//
//        record PhoneCollection() implements AuthenticationStep {
//        }
//        record CodeVerification(VerificationData data) implements AuthenticationStep {
//        }
//
//
//        // Pattern matching helper
//        static AuthenticationStep fromString(String step) {
//        return switch (step) {
//            case null, "1" -> new PhoneCollection();
//            case "2" -> new CodeVerification(null); // Will be populated from session
//            default -> new PhoneCollection();
//        };
//    }
//
//    }
}
