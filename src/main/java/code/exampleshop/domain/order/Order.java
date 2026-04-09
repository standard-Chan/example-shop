package code.exampleshop.domain.order;

import code.exampleshop.domain.common.BaseEntity;
import code.exampleshop.domain.order.vo.Money;
import code.exampleshop.domain.order.vo.OrderNumber;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    private Long id;

    @Embedded
    private OrderNumber orderNumber;

    @jakarta.persistence.Enumerated(jakarta.persistence.EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "total_price", nullable = false, precision = 19, scale = 2))
    })
    private Money totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    protected Order() {
    }

    private Order(List<OrderItem> items) {
        this.orderNumber = OrderNumber.newOrderNumber();
        this.status = OrderStatus.CREATED;
        this.totalPrice = calculateTotal(items);
        addItems(items);
    }

    public static Order create(List<OrderItem> items) {
        return new Order(items);
    }

    public void markPaid() {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("CREATED 상태에서만 결제 완료 처리 가능합니다.");
        }
        this.status = OrderStatus.PAID;
    }

    public void startPreparing() {
        if (status != OrderStatus.PAID) {
            throw new IllegalStateException("PAID 상태에서만 준비 시작 가능합니다.");
        }
        this.status = OrderStatus.PREPARING;
    }

    public void ship() {
        if (status != OrderStatus.PREPARING) {
            throw new IllegalStateException("PREPARING 상태에서만 배송 가능합니다.");
        }
        this.status = OrderStatus.SHIPPED;
    }

    public void cancel() {
        this.status = OrderStatus.CANCELLED;
    }

    public boolean isStatus(OrderStatus status) {
        return this.status == status;
    }

    public Money totalPrice() {
        return totalPrice;
    }

    public String orderNumber() {
        return orderNumber.value();
    }

    public List<OrderItem> getItems() {
        return List.copyOf(items);
    }

    private void addItems(List<OrderItem> items) {
        for (OrderItem item : items) {
            item.assignTo(this);
            this.items.add(item);
        }
    }

    private Money calculateTotal(List<OrderItem> items) {
        return items.stream()
            .map(OrderItem::subtotal)
            .reduce(Money.of(0), Money::add);
    }
}