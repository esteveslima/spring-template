package com.template.spring.demo.external.adapters.entrypoints.controllers;

import com.template.spring.demo.core.application.interfaces.usecases.dtos.auth.LoginStoreUseCaseDTO;
import com.template.spring.demo.core.application.usecases.auth.LoginStoreUseCase;
import com.template.spring.demo.core.domain.exceptions.store.StoreNotFoundException;
import com.template.spring.demo.external.adapters.entrypoints.controllers.dtos.auth.LoginStoreRestControllerEntrypointDTO;
import com.template.spring.demo.external.adapters.entrypoints.controllers.dtos.auth.LoginUserRestControllerEntrypointDTO;
import com.template.spring.demo.core.application.exceptions.usecases.auth.UnauthorizedException;
import com.template.spring.demo.core.application.interfaces.usecases.dtos.auth.LoginUserUseCaseDTO;
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
    @Autowired private LoginStoreUseCase loginStoreUseCase;

    //

    @PostMapping("/login/user")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PermitAll
    public LoginUserRestControllerEntrypointDTO.Response.Body loginUser(
            @Valid @RequestBody LoginUserRestControllerEntrypointDTO.Request.Body requestBody
    ) {
        try{
            LoginUserUseCaseDTO.Params useCaseParamsDTO = new LoginUserUseCaseDTO.Params(requestBody.username, requestBody.password);

            LoginUserUseCaseDTO.Result useCaseResultDTO = loginUserUseCase.execute(useCaseParamsDTO);

            return new LoginUserRestControllerEntrypointDTO.Response.Body(useCaseResultDTO.token);
        } catch (UserNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } catch (UnauthorizedException exception){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login unauthorized");
        }
    }

    @PostMapping("/login/store")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PermitAll
    public LoginStoreRestControllerEntrypointDTO.Response.Body loginStore(
            @Valid @RequestBody LoginStoreRestControllerEntrypointDTO.Request.Body requestBody
    ) {
        try{
            LoginStoreUseCaseDTO.Params useCaseParamsDTO = new LoginStoreUseCaseDTO.Params(requestBody.username, requestBody.password);

            LoginStoreUseCaseDTO.Result useCaseResultDTO = loginStoreUseCase.execute(useCaseParamsDTO);

            return new LoginStoreRestControllerEntrypointDTO.Response.Body(useCaseResultDTO.token);
        } catch (UserNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } catch (StoreNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found for user");
        } catch (UnauthorizedException exception){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login unauthorized");
        }
    }
}
