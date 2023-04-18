package com.template.spring.demo.domain.entities;

import com.template.spring.demo.domain.exceptions.user.UserFieldFailedValidationException;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Data
public class UserEntity {

    public final int id;
    public final String username;
    public final String email;
    public final String password;

    public final EnumUserRole role;

    public enum EnumUserRole {
        USER,
        STORE;

        public class MapValue {
            public static final String USER = "USER";
            public static final String STORE = "STORE";
        }
    }

    //

    public static void validateUsername(String username) throws UserFieldFailedValidationException {
        if(username == null){
            String errorMessage = "Username cannot be null";
            Map<String, Object> validationFailuresMap = new HashMap<String, Object>();
            validationFailuresMap.put("username", username);
            throw new UserFieldFailedValidationException(errorMessage, validationFailuresMap);
        }

        int usernameMinLength = 3;
        int usernameMaxLength = 100;
        boolean isAdequateLength = username.length() >= usernameMinLength && username.length() <= usernameMaxLength;
        if(!isAdequateLength){
            String errorMessage = String.format("Username length must be between %d and %d", usernameMinLength, usernameMaxLength);
            Map<String, Object> validationFailuresMap = new HashMap<String, Object>();
            validationFailuresMap.put("username", username);
            throw new UserFieldFailedValidationException(errorMessage, validationFailuresMap);
        }

        return;
    }

    public static void validateEmail(String email) throws UserFieldFailedValidationException {
        if(email == null){
            String errorMessage = "Email cannot be null";
            Map<String, Object> validationFailuresMap = new HashMap<String, Object>();
            validationFailuresMap.put("email", email);
            throw new UserFieldFailedValidationException(errorMessage, validationFailuresMap);
        }

        Pattern emailRegexPattern = Pattern.compile("^(.+)@(\\S+)$");
        boolean isValidEmail = emailRegexPattern.matcher(email).matches();
        if(!isValidEmail){
            String errorMessage = "Invalid email";
            Map<String, Object> validationFailuresMap = new HashMap<String, Object>();
            validationFailuresMap.put("email", email);
            throw new UserFieldFailedValidationException(errorMessage, validationFailuresMap);
        }

        return;
    }

    public static void validatePassword(String password) throws UserFieldFailedValidationException {
        if(password == null){
            String errorMessage = "Password cannot be null";
            Map<String, Object> validationFailuresMap = new HashMap<String, Object>();
            validationFailuresMap.put("password", password);
            throw new UserFieldFailedValidationException(errorMessage, validationFailuresMap);
        }

        String oneUppercaseCharacterRegexStringPattern = "(?=.*[A-Z])";
        String oneLowercaseCharacterRegexStringPattern = "(?=.*[a-z])";
        String oneSpecialCharacterRegexStringPattern = "(?=.*[!@#$%&*()_=+-])";
        String oneDigitCharacterRegexStringPattern = "(?=.*[0-9])";
        String minimumLengthRegexStringPattern = ".{8,}";
        String strongPasswordStringPattern = String.format("^%s%s%s%s%s$",
                oneUppercaseCharacterRegexStringPattern,
                oneLowercaseCharacterRegexStringPattern,
                oneSpecialCharacterRegexStringPattern,
                oneDigitCharacterRegexStringPattern,
                minimumLengthRegexStringPattern
                );
        Pattern strongPasswordRegexPattern = Pattern.compile(strongPasswordStringPattern);
        boolean isValidPassword = strongPasswordRegexPattern.matcher(password).matches();
        if(!isValidPassword){
            String errorMessage = "Weak password";
            Map<String, Object> validationFailuresMap = new HashMap<String, Object>();
            validationFailuresMap.put("password", password);
            throw new UserFieldFailedValidationException(errorMessage, validationFailuresMap);
        }

        return;
    }
}
