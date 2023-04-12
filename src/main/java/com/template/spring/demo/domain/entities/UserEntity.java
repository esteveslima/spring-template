package com.template.spring.demo.domain.entities;

import com.template.spring.demo.domain.exceptions.user.validation.UserFailedValidationPasswordFieldException;
import lombok.Data;
import com.template.spring.demo.domain.exceptions.user.validation.UserFailedValidationEmailFieldException;
import com.template.spring.demo.domain.exceptions.user.validation.UserFailedValidationUsernameFieldException;

import java.util.regex.Pattern;

@Data
public class UserEntity {

    public final int id;
    public final String username;
    public final String email;
    public final String password;

    public static void validateUsername(String username) throws UserFailedValidationUsernameFieldException {
        if(username == null){
            String errorMessage = "Username cannot be null";
            throw new UserFailedValidationUsernameFieldException(errorMessage, username);
        }

        int usernameMinLength = 3;
        int usernameMaxLength = 100;
        boolean isAdequateLength = username.length() >= usernameMinLength && username.length() <= usernameMaxLength;
        if(!isAdequateLength){
            String errorMessage = String.format("Username length must be between %d and %d", usernameMinLength, usernameMaxLength);
            throw new UserFailedValidationUsernameFieldException(errorMessage, username);
        }

        return;
    }

    public static void validateEmail(String email) throws UserFailedValidationEmailFieldException {
        if(email == null){
            String errorMessage = "Email cannot be null";
            throw new UserFailedValidationEmailFieldException(errorMessage, email);
        }

        Pattern emailRegexPattern = Pattern.compile("^(.+)@(\\S+)$");
        boolean isValidEmail = emailRegexPattern.matcher(email).matches();
        if(!isValidEmail){
            String errorMessage = "Invalid email";
            throw new UserFailedValidationEmailFieldException(errorMessage, email);
        }

        return;
    }

    public static void validatePassword(String password) throws UserFailedValidationPasswordFieldException {
        if(password == null){
            String errorMessage = "Password cannot be null";
            throw new UserFailedValidationPasswordFieldException(errorMessage, password);
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
            String errorMessage = "Password weak";
            throw new UserFailedValidationPasswordFieldException(errorMessage, password);
        }

        return;
    }
}
