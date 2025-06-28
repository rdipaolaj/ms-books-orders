package com.unir.missiact1.msbooksorders.controller.v1;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unir.missiact1.msbooksorders.application.dtos.*;
import com.unir.missiact1.msbooksorders.commons.responses.ApiResponse;
import com.unir.missiact1.msbooksorders.commons.responses.ApiResponseHelper;
import com.unir.missiact1.msbooksorders.infraestructure.repository.implementations.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Carrito")
@RestController
@RequestMapping(path = "/v1/api/carts", headers = "X-Api-Version=1")
public class CartControllerV1 {
    private final CartService service;

    public CartControllerV1(CartService service) {
        this.service = service;
    }

    @GetMapping("/{customerId}")
    @Operation(summary = "Obtener carrito", description = "Recupera el carrito de un cliente")
    public ResponseEntity<ApiResponse<CartDto>> getCart(@PathVariable UUID customerId) {
        CartDto dto = service.getCart(customerId);
        return ResponseEntity.ok(ApiResponseHelper.createSuccessResponse(dto));
    }

    @PostMapping("/{customerId}/items")
    @Operation(summary = "Añadir ítem", description = "Agrega un ítem al carrito")
    public ResponseEntity<ApiResponse<CartDto>> addItem(
            @PathVariable UUID customerId,
            @Valid @RequestBody CartItemRequest req) {
        CartDto dto = service.addItem(customerId, req);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponseHelper.createSuccessResponse(dto, "Ítem añadido al carrito"));
    }

    @PutMapping("/{customerId}/items")
    @Operation(summary = "Actualizar ítem", description = "Modifica la cantidad y precio de un ítem")
    public ResponseEntity<ApiResponse<CartDto>> updateItem(
            @PathVariable UUID customerId,
            @Valid @RequestBody CartItemRequest req) {
        CartDto dto = service.updateItem(customerId, req);
        return ResponseEntity.ok(ApiResponseHelper.createSuccessResponse(dto, "Ítem actualizado"));
    }

    @DeleteMapping("/{customerId}/items/{bookId}")
    @Operation(summary = "Eliminar ítem", description = "Elimina un ítem del carrito")
    public ResponseEntity<ApiResponse<CartDto>> removeItem(
            @PathVariable UUID customerId,
            @PathVariable UUID bookId) {
        CartDto dto = service.removeItem(customerId, bookId);
        return ResponseEntity.ok(ApiResponseHelper.createSuccessResponse(dto, "Ítem eliminado"));
    }

    @DeleteMapping("/{customerId}")
    @Operation(summary = "Vaciar carrito", description = "Elimina todos los ítems del carrito")
    public ResponseEntity<ApiResponse<String>> clearCart(@PathVariable UUID customerId) {
        service.clearCart(customerId);
        return ResponseEntity.ok(ApiResponseHelper.createSuccessResponse(null, "Carrito vaciado"));
    }
}