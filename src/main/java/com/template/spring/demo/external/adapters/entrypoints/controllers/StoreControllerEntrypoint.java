package com.template.spring.demo.external.adapters.entrypoints.controllers;

import com.template.spring.demo.core.application.interfaces.auth_token.AuthTokenPayloadDTO;
import com.template.spring.demo.core.application.interfaces.usecases.dtos.store.GetStoreUseCaseDTO;
import com.template.spring.demo.core.application.interfaces.usecases.dtos.store.ModifyStoreUseCaseDTO;
import com.template.spring.demo.core.application.interfaces.usecases.dtos.store.RegisterStoreUseCaseDTO;
import com.template.spring.demo.core.application.interfaces.usecases.dtos.store.SearchStoresUseCaseDTO;
import com.template.spring.demo.core.application.usecases.store.GetStoreUseCase;
import com.template.spring.demo.core.application.usecases.store.ModifyStoreUseCase;
import com.template.spring.demo.core.application.usecases.store.RegisterStoreUseCase;
import com.template.spring.demo.core.application.usecases.store.SearchStoresUseCase;
import com.template.spring.demo.core.domain.exceptions.store.StoreAlreadyExistsException;
import com.template.spring.demo.core.domain.exceptions.store.StoreFieldFailedValidationException;
import com.template.spring.demo.core.domain.exceptions.store.StoreNotFoundException;
import com.template.spring.demo.external.adapters.entrypoints.controllers.dtos.store.GetStoreRestControllerEntrypointDTO;
import com.template.spring.demo.external.adapters.entrypoints.controllers.dtos.store.ModifyStoreRestControllerEntrypointDTO;
import com.template.spring.demo.external.adapters.entrypoints.controllers.dtos.store.RegisterStoreRestControllerEntrypointDTO;
import com.template.spring.demo.external.adapters.entrypoints.controllers.dtos.store.SearchStoresRestControllerEntrypointDTO;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/stores")
@Validated  // exclusively added for path/query params validation in controllers
public class StoreControllerEntrypoint {

    @Autowired private RegisterStoreUseCase registerStoreUseCase;
    @Autowired private GetStoreUseCase getStoreUseCase;
    @Autowired private SearchStoresUseCase searchStoresUseCase;
    @Autowired private ModifyStoreUseCase modifyStoreUseCase;

    //

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @Secured({AuthTokenPayloadDTO.AuthRoleEnum.MapValue.USER})
    public RegisterStoreRestControllerEntrypointDTO.Response.Body registerStore(
            Authentication authentication,
            @Valid @RequestBody RegisterStoreRestControllerEntrypointDTO.Request.Body requestBody
    ) {
        try {
            AuthTokenPayloadDTO authTokenPayload = (AuthTokenPayloadDTO) authentication.getPrincipal();

            RegisterStoreUseCaseDTO.Params useCaseParamsDTO = new RegisterStoreUseCaseDTO.Params(
                    authTokenPayload.userId,
                    new RegisterStoreUseCaseDTO.Params.Payload(
                            requestBody.name
                    )
            );

            RegisterStoreUseCaseDTO.Result useCaseResultDTO = registerStoreUseCase.execute(useCaseParamsDTO);

            return new RegisterStoreRestControllerEntrypointDTO.Response.Body(
                    useCaseResultDTO.id,
                    useCaseResultDTO.userId,
                    useCaseResultDTO.name
            );
        } catch (StoreFieldFailedValidationException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        } catch (StoreAlreadyExistsException exception){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Store data already exists");
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PermitAll
    public GetStoreRestControllerEntrypointDTO.Response.Body getStore(
            @PathVariable @NotNull @Min(0) int id
    ) {
        try {
            GetStoreUseCaseDTO.Params useCaseParamsDTO = new GetStoreUseCaseDTO.Params(id);

            GetStoreUseCaseDTO.Result useCaseResultDTO = getStoreUseCase.execute(useCaseParamsDTO);

            return new GetStoreRestControllerEntrypointDTO.Response.Body(
                    useCaseResultDTO.id,
                    useCaseResultDTO.userId,
                    useCaseResultDTO.name
            );
        } catch (StoreNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found");
        }
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Secured({AuthTokenPayloadDTO.AuthRoleEnum.MapValue.USER})
    public SearchStoresRestControllerEntrypointDTO.Response.Body searchStores(
            @RequestParam @NotBlank String searchQuery,
            @RequestParam @NotNull @Min(1) @Max(10) int limit,
            @RequestParam @NotNull @Min(0) @Max(10) int offset
    ) {
        try {
            SearchStoresUseCaseDTO.Params useCaseParamsDTO = new SearchStoresUseCaseDTO.Params(
                    searchQuery,
                    limit,
                    offset
            );

            SearchStoresUseCaseDTO.Result useCaseResultDTO = searchStoresUseCase.execute(useCaseParamsDTO);

            return new SearchStoresRestControllerEntrypointDTO.Response.Body(useCaseResultDTO.stores.stream().map(
                    (store) -> new SearchStoresRestControllerEntrypointDTO.Response.Body.ResultItem(
                            store.id,
                            store.userId,
                            store.name
                    )
            ).toList());
        } catch (StoreFieldFailedValidationException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @PatchMapping()
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Secured({AuthTokenPayloadDTO.AuthRoleEnum.MapValue.STORE})
    public ModifyStoreRestControllerEntrypointDTO.Response.Body modifyStore(
            Authentication authentication,
            @Valid @RequestBody ModifyStoreRestControllerEntrypointDTO.Request.Body requestBody
    ) {
        try {
            AuthTokenPayloadDTO authTokenPayload = (AuthTokenPayloadDTO) authentication.getPrincipal();

            ModifyStoreUseCaseDTO.Params useCaseParamsDTO = new ModifyStoreUseCaseDTO.Params(
                    authTokenPayload.userId,
                    new ModifyStoreUseCaseDTO.Params.Payload(
                            requestBody.name
                    )
            );

            ModifyStoreUseCaseDTO.Result useCaseResultDTO = modifyStoreUseCase.execute(useCaseParamsDTO);

            return new ModifyStoreRestControllerEntrypointDTO.Response.Body(
                    useCaseResultDTO.id,
                    useCaseResultDTO.userId,
                    useCaseResultDTO.name
            );
        } catch (StoreFieldFailedValidationException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        } catch (StoreNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found");
        } catch (StoreAlreadyExistsException exception){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Store data already exists");
        }
    }
}
