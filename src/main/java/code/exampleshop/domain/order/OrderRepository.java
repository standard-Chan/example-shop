package code.exampleshop.domain.order;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(Long id);
}