package code.exampleshop.infrastructure.order;

import code.exampleshop.domain.order.Order;
import code.exampleshop.domain.order.OrderRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

interface SpringDataOrderJpaRepository extends JpaRepository<Order, Long> {
}

@Repository
public class JpaOrderRepository implements OrderRepository {

    private final SpringDataOrderJpaRepository jpaRepository;

    public JpaOrderRepository(SpringDataOrderJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Order save(Order order) {
        return jpaRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return jpaRepository.findById(id);
    }
}