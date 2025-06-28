package com.unir.missiact1.msbooksorders.controller.v1;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unir.missiact1.msbooksorders.application.dtos.OrderCreateRequest;
import com.unir.missiact1.msbooksorders.application.dtos.OrderDto;
import com.unir.missiact1.msbooksorders.commons.responses.ApiResponse;
import com.unir.missiact1.msbooksorders.commons.responses.ApiResponseHelper;
import com.unir.missiact1.msbooksorders.infraestructure.repository.implementations.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Órdenes")
@RestController
@RequestMapping(path = "/v1/api/orders", headers = "X-Api-Version=1")
public class OrderControllerV1 {
    private final OrderService service;
    public OrderControllerV1(OrderService service) { this.service = service; }

    @PostMapping("/create")
    @Operation
    (
  	  summary     = "Crear pedido",
  	  description = "Crea un nuevo pedido con ítems y direcciones",
  	  operationId = "createOrder"
  	)
    public ResponseEntity<ApiResponse<OrderDto>> create(@Valid @RequestBody OrderCreateRequest req) {
        OrderDto created = service.create(req);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponseHelper.createSuccessResponse(created, "Pedido creado correctamente"));
    }

    @GetMapping("/findById/{id}")
    @Operation
    (
      summary     = "Obtener pedido",
      description = "Busca un pedido por su UUID",
      operationId = "getOrderById"
    )
    public ResponseEntity<ApiResponse<OrderDto>> findById(@PathVariable UUID id) {
        OrderDto dto = service.findById(id);
        return ResponseEntity.ok(ApiResponseHelper.createSuccessResponse(dto));
    }

    @GetMapping("/findAll")
    @Operation
    (
      summary     = "Listar pedidos",
      description = "Devuelve todos los pedidos existentes",
      operationId = "listOrders"
    )
    public ResponseEntity<ApiResponse<List<OrderDto>>> findAll() {
        List<OrderDto> list = service.findAll();
        return ResponseEntity.ok(ApiResponseHelper.createSuccessResponse(list));
    }
}