package com.template.spring.demo.adapters.entrypoints.controllers;

import com.template.spring.demo.adapters.entrypoints.controllers.dtos.auth.LoginRestControllerEntrypointDTO;
import com.template.spring.demo.application.exceptions.auth.UnauthorizedException;
import com.template.spring.demo.application.interfaces.dtos.usecases.auth.LoginUseCaseDTO;
import com.template.spring.demo.application.usecases.auth.LoginUseCase;
import com.template.spring.demo.domain.exceptions.user.UserNotFoundException;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthControllerEntrypoint {

    @Autowired private LoginUseCase loginUseCase;


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PermitAll
    public LoginRestControllerEntrypointDTO.Response.Body login(
            @Valid @RequestBody LoginRestControllerEntrypointDTO.Request.Body requestBody
    ) {
        try{
            LoginUseCaseDTO.Params useCaseParamsDTO = new LoginUseCaseDTO.Params(
                    requestBody.username,
                    requestBody.password
            );

            LoginUseCaseDTO.Result useCaseResultDTO = loginUseCase.execute(useCaseParamsDTO);

            return new LoginRestControllerEntrypointDTO.Response.Body(
                    useCaseResultDTO.token
            );
        } catch (UserNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } catch (UnauthorizedException exception){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login unauthorized");
        }


    }
}
