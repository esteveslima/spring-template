package com.template.spring.demo.adapters.entrypoints.controllers;

import com.template.spring.demo.adapters.entrypoints.controllers.dtos.request.auth.LoginRestRequestBodyDTO;
import com.template.spring.demo.adapters.entrypoints.controllers.dtos.response.auth.LoginRestResponseDTO;
import com.template.spring.demo.application.exceptions.auth.LoginUnauthorizedException;
import com.template.spring.demo.application.usecases.auth.LoginUseCase;
import com.template.spring.demo.application.interfaces.usecases.auth.login.LoginUseCaseParametersDTO;
import com.template.spring.demo.application.interfaces.usecases.auth.login.LoginUseCaseResultDTO;
import com.template.spring.demo.domain.exceptions.user.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthControllerEntrypoint {

    @Autowired private LoginUseCase loginUseCase;


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginRestResponseDTO login(@Valid @RequestBody LoginRestRequestBodyDTO requestBody) {
        try{
            LoginUseCaseParametersDTO useCaseParamsDTO = new LoginUseCaseParametersDTO(
                    requestBody.username,
                    requestBody.password
            );

            LoginUseCaseResultDTO useCaseResultDTO = loginUseCase.execute(useCaseParamsDTO);

            return new LoginRestResponseDTO(
                    useCaseResultDTO.token
            );
        } catch (UserNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } catch (LoginUnauthorizedException exception){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login unauthorized");
        }


    }
}
