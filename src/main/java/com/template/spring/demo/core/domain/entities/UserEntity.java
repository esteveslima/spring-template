package com.template.spring.demo.core.domain.entities;

import com.template.spring.demo.core.domain.exceptions.user.UserFieldFailedValidationException;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Entity @Table(name = "users")  // TODO: Find a way to separate DB annotations from domain entities or ensure a data mapper is stable and performant(had a separated model class, changed ORM annotations to entity to avoid having mappings on repositories)
public class UserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    public int id;

    @Column(name = "username", unique = true)
    public String username;

    @Column(name = "email", unique = true)
    public String email;

    @Column(name = "encoded_password")
    public String encodedPassword;

    //

    public UserEntity() {
    }

    public UserEntity(String username, String email, String encodedPassword) {
        UserEntity.validateUsername(username);
        UserEntity.validateEmail(email);

        this.id = Integer.MIN_VALUE;
        this.username = username;
        this.email = email;
        this.encodedPassword = encodedPassword;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        UserEntity.validateUsername(username);
        this.username = username;
    }

    public void setEmail(String email) throws UserFieldFailedValidationException{
        UserEntity.validateEmail(email);
        this.email = email;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
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

    public static void validatePlainPassword(String password) throws UserFieldFailedValidationException {
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
