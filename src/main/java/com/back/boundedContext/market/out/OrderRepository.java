package com.back.boundedContext.market.out;

import com.back.boundedContext.market.app.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
