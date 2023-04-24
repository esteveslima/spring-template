package com.template.spring.demo.external.adapters.entrypoints.controllers;

import com.template.spring.demo.core.application.exceptions.usecases.product.ProductNotFromUserStoreException;
import com.template.spring.demo.core.application.interfaces.auth_token.AuthTokenPayloadDTO;
import com.template.spring.demo.core.application.interfaces.usecases.dtos.product.*;
import com.template.spring.demo.core.application.usecases.product.*;
import com.template.spring.demo.core.domain.entities.ProductEntity;
import com.template.spring.demo.core.domain.exceptions.product.ProductFieldFailedValidationException;
import com.template.spring.demo.core.domain.exceptions.product.ProductNotFoundException;
import com.template.spring.demo.external.adapters.entrypoints.controllers.dtos.product.*;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/products")
@Validated  // exclusively added for path/query params validation in controllers
public class ProductControllerEntrypoint {

    @Autowired private RegisterProductUseCase registerProductUseCase;
    @Autowired private GetProductUseCase getProductUseCase;
    @Autowired private SearchProductsUseCase searchProductsUseCase;
    @Autowired private ModifyProductUseCase modifyProductUseCase;
    @Autowired private DeleteProductUseCase deleteProductUseCase;

    //

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @Secured({AuthTokenPayloadDTO.AuthRoleEnum.MapValue.STORE})
    public RegisterProductRestControllerEntrypointDTO.Response.Body registerProduct(
            Authentication authentication,
            @Valid @RequestBody RegisterProductRestControllerEntrypointDTO.Request.Body requestBody
    ) {
        try {
            AuthTokenPayloadDTO authTokenPayload = (AuthTokenPayloadDTO) authentication.getPrincipal();

            RegisterProductUseCaseDTO.Params useCaseParamsDTO = new RegisterProductUseCaseDTO.Params(
                    authTokenPayload.userId,
                    new RegisterProductUseCaseDTO.Params.Payload(
                            requestBody.name,
                            requestBody.description,
                            requestBody.price,
                            ProductEntity.CurrencyEnum.valueOf(requestBody.currency)
                    )
            );

            RegisterProductUseCaseDTO.Result useCaseResultDTO = registerProductUseCase.execute(useCaseParamsDTO);

            return new RegisterProductRestControllerEntrypointDTO.Response.Body(
                    useCaseResultDTO.id,
                    useCaseResultDTO.storeId,
                    useCaseResultDTO.name,
                    useCaseResultDTO.description,
                    useCaseResultDTO.price,
                    useCaseResultDTO.currency
            );
        } catch (ProductFieldFailedValidationException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PermitAll
    public GetProductRestControllerEntrypointDTO.Response.Body getProduct(
            @PathVariable @NotNull @Min(0) int id
    ) {
        try {
            GetProductUseCaseDTO.Params useCaseParamsDTO = new GetProductUseCaseDTO.Params(id);

            GetProductUseCaseDTO.Result useCaseResultDTO = getProductUseCase.execute(useCaseParamsDTO);

            return new GetProductRestControllerEntrypointDTO.Response.Body(
                    useCaseResultDTO.id,
                    useCaseResultDTO.storeId,
                    useCaseResultDTO.name,
                    useCaseResultDTO.description,
                    useCaseResultDTO.price,
                    useCaseResultDTO.currency
            );
        } catch (ProductNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PermitAll
    public SearchProductsRestControllerEntrypointDTO.Response.Body searchProducts(
            @RequestParam @NotBlank String searchQuery,
            @RequestParam(required = false, defaultValue = "0") int storeId,
            @RequestParam @NotNull @Min(1) @Max(10) int limit,
            @RequestParam @NotNull @Min(0) @Max(10) int offset
    ) {
        try {
            SearchProductsUseCaseDTO.Params useCaseParamsDTO = new SearchProductsUseCaseDTO.Params(
                    searchQuery,
                    storeId,
                    limit,
                    offset
            );

            SearchProductsUseCaseDTO.Result useCaseResultDTO = searchProductsUseCase.execute(useCaseParamsDTO);

            return new SearchProductsRestControllerEntrypointDTO.Response.Body(useCaseResultDTO.products.stream().map(
                    (product) -> new SearchProductsRestControllerEntrypointDTO.Response.Body.ResultItem(
                            product.id,
                            product.storeId,
                            product.name,
                            product.description,
                            product.price,
                            product.currency
                    )
            ).toList());
        } catch (ProductFieldFailedValidationException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Secured({AuthTokenPayloadDTO.AuthRoleEnum.MapValue.STORE})
    public ModifyProductRestControllerEntrypointDTO.Response.Body modifyProduct(
            Authentication authentication,
            @PathVariable @NotNull @Min(0) int id,
            @Valid @RequestBody ModifyProductRestControllerEntrypointDTO.Request.Body requestBody
    ) {
        try {
            AuthTokenPayloadDTO authTokenPayload = (AuthTokenPayloadDTO) authentication.getPrincipal();

            ModifyProductUseCaseDTO.Params useCaseParamsDTO = new ModifyProductUseCaseDTO.Params(
                    id,
                    authTokenPayload.userId,
                    new ModifyProductUseCaseDTO.Params.Payload(
                            requestBody.name,
                            requestBody.description,
                            requestBody.price,
                            ProductEntity.CurrencyEnum.valueOf(requestBody.currency)
                    )
            );

            ModifyProductUseCaseDTO.Result useCaseResultDTO = modifyProductUseCase.execute(useCaseParamsDTO);

            return new ModifyProductRestControllerEntrypointDTO.Response.Body(
                    useCaseResultDTO.id,
                    useCaseResultDTO.storeId,
                    useCaseResultDTO.name,
                    useCaseResultDTO.description,
                    useCaseResultDTO.price,
                    useCaseResultDTO.currency
            );
        } catch (ProductFieldFailedValidationException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        } catch (ProductNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        } catch (ProductNotFromUserStoreException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for user");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PermitAll
    public DeleteProductRestControllerEntrypointDTO.Response.Body deleteProduct(
            Authentication authentication,
            @PathVariable @NotNull @Min(0) int id
    ) {
        try {
            AuthTokenPayloadDTO authTokenPayload = (AuthTokenPayloadDTO) authentication.getPrincipal();

            DeleteProductUseCaseDTO.Params useCaseParamsDTO = new DeleteProductUseCaseDTO.Params(id, authTokenPayload.userId);

            DeleteProductUseCaseDTO.Result useCaseResultDTO = deleteProductUseCase.execute(useCaseParamsDTO);

            return new DeleteProductRestControllerEntrypointDTO.Response.Body(
                    useCaseResultDTO.id,
                    useCaseResultDTO.storeId,
                    useCaseResultDTO.name,
                    useCaseResultDTO.description,
                    useCaseResultDTO.price,
                    useCaseResultDTO.currency
            );
        } catch (ProductNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        } catch (ProductNotFromUserStoreException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for user");
        }
    }
}
