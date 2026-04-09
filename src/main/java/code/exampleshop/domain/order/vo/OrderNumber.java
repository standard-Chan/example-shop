package code.exampleshop.domain.order.vo;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.UUID;

@Embeddable
public class OrderNumber {

    @Column(name = "order_number", nullable = false, unique = true, updatable = false)
    private String value;

    protected OrderNumber() {
    }

    private OrderNumber(String value) {
        this.value = value;
    }

    public static OrderNumber newOrderNumber() {
        return new OrderNumber(UUID.randomUUID().toString());
    }

    public static OrderNumber of(String value) {
        return new OrderNumber(value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OrderNumber that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}