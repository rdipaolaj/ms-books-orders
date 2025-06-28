package com.unir.missiact1.msbooksorders.domain.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.unir.missiact1.msbooksorders.domain.OrderItem;

@Repository
public interface IOrderItemRepository extends JpaRepository<OrderItem, Long> { }