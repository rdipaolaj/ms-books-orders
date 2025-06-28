package com.unir.missiact1.msbooksorders.domain.interfaces;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.unir.missiact1.msbooksorders.domain.Order;

public interface IOrderRepository extends JpaRepository<Order, UUID> { }