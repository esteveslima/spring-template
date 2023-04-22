package com.template.spring.demo.external.adapters.entrypoints.controllers;

import com.template.spring.demo.external.adapters.entrypoints.controllers.dtos.auth.LoginRestControllerEntrypointDTO;
import com.template.spring.demo.core.application.exceptions.auth.UnauthorizedException;
import com.template.spring.demo.core.application.interfaces.dtos.usecases.auth.LoginUseCaseDTO;
import com.template.spring.demo.core.application.usecases.auth.LoginUserUseCase;
import com.template.spring.demo.core.domain.exceptions.user.UserNotFoundException;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthControllerEntrypoint {

    @Autowired private LoginUserUseCase loginUserUseCase;


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PermitAll
    public LoginRestControllerEntrypointDTO.Response.Body login(
            @Valid @RequestBody LoginRestControllerEntrypointDTO.Request.Body requestBody
    ) {
        try{
            LoginUseCaseDTO.Params useCaseParamsDTO = new LoginUseCaseDTO.Params(requestBody.username, requestBody.password);

            LoginUseCaseDTO.Result useCaseResultDTO = loginUserUseCase.execute(useCaseParamsDTO);

            return new LoginRestControllerEntrypointDTO.Response.Body(useCaseResultDTO.token);
        } catch (UserNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } catch (UnauthorizedException exception){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login unauthorized");
        }


    }
}