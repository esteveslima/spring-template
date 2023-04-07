package com.template.spring.demo.adapters.entrypoints.controllers.user;

import com.template.spring.demo.adapters.entrypoints.controllers.user.dtos.request.user.RegisterUserRestRequestBodyDTO;
import com.template.spring.demo.adapters.entrypoints.controllers.user.dtos.response.user.RegisterUserRestResponseDTO;
import com.template.spring.demo.application.usecases.register_user.RegisterUserUseCase;
import com.template.spring.demo.application.usecases.register_user.RegisterUserUseCaseParametersDTO;
import com.template.spring.demo.application.usecases.register_user.RegisterUserUseCaseResultDTO;
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
    public RegisterUserRestResponseDTO registerUser(@Valid @RequestBody RegisterUserRestRequestBodyDTO requestBody) {
        try {
            RegisterUserUseCaseParametersDTO useCaseParamsDTO = new RegisterUserUseCaseParametersDTO(
                    requestBody.username,
                    requestBody.email,
                    requestBody.password
            );

            RegisterUserUseCaseResultDTO useCaseResultDTO = registerUserUseCase.execute(useCaseParamsDTO);

            return new RegisterUserRestResponseDTO(
                    useCaseResultDTO.username,
                    useCaseResultDTO.email,
                    useCaseResultDTO.id
            );
        } catch (UserAlreadyExistsException exception){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
