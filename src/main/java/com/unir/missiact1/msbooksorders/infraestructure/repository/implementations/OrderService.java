package com.unir.missiact1.msbooksorders.infraestructure.repository.implementations;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unir.missiact1.msbooksorders.domain.interfaces.IOrderRepository;
import com.unir.missiact1.msbooksorders.application.dtos.OrderCreateRequest;
import com.unir.missiact1.msbooksorders.application.dtos.OrderDto;
import com.unir.missiact1.msbooksorders.application.request.mapster.OrderMapper;
import com.unir.missiact1.msbooksorders.commons.exceptions.CustomException;
import com.unir.missiact1.msbooksorders.commons.enums.ApiErrorCode;
import com.unir.missiact1.msbooksorders.domain.Order;

@Service
public class OrderService {
    private final IOrderRepository repo;
    public OrderService(IOrderRepository repo) { this.repo = repo; }

    @Transactional
    public OrderDto create(OrderCreateRequest req) {
        Order o = OrderMapper.toEntity(req);
        Order saved = repo.save(o);
        return OrderMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public OrderDto findById(UUID id) {
        return repo.findById(id)
            .map(OrderMapper::toDto)
            .orElseThrow(() -> new CustomException("Pedido no encontrado", ApiErrorCode.NotFound));
    }

    @Transactional(readOnly = true)
    public List<OrderDto> findAll() {
        return repo.findAll().stream()
            .map(OrderMapper::toDto)
            .collect(Collectors.toList());
    }
}