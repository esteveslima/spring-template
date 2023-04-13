package com.template.spring.demo.adapters.entrypoints.controllers;

import com.template.spring.demo.adapters.entrypoints.controllers.dtos.user.RegisterUserRestControllerEntrypointDTO;
import com.template.spring.demo.application.interfaces.dtos.usecases.user.RegisterUserUseCaseDTO;
import com.template.spring.demo.application.usecases.user.RegisterUserUseCase;
import com.template.spring.demo.domain.exceptions.user.UserAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
public class UserControllerEntrypoint {

    @Autowired private RegisterUserUseCase registerUserUseCase;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterUserRestControllerEntrypointDTO.Response.Body registerUser(
            @Valid @RequestBody RegisterUserRestControllerEntrypointDTO.Request.Body requestBody
    ) {
        try {
            RegisterUserUseCaseDTO.Params useCaseParamsDTO = new RegisterUserUseCaseDTO.Params(
                    requestBody.username,
                    requestBody.email,
                    requestBody.password
            );

            RegisterUserUseCaseDTO.Result useCaseResultDTO = registerUserUseCase.execute(useCaseParamsDTO);

            return new RegisterUserRestControllerEntrypointDTO.Response.Body(
                    useCaseResultDTO.username,
                    useCaseResultDTO.email,
                    useCaseResultDTO.id
            );
        } catch (UserAlreadyExistsException exception){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User data already exists");
        }
    }

}
