package com.unir.missiact1.msbooksorders.domain.interfaces;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.unir.missiact1.msbooksorders.domain.Cart;

@Repository
public interface ICartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByCustomerId(UUID customerId);
}