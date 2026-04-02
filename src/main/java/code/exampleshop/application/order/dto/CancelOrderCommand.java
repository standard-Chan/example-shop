package code.exampleshop.application.order.dto;

// 왜 CacelOrderCommand를 만들어야 하는가?
// -> Use Case의 입력 데이터를 캡슐화하기 위해서, 그리고 Use Case의 인터페이스를 명확하게 하기 위해서
// -> CancelOrderCommand는 CancelOrderUseCase의 입력 데이터를 캡슐화하는 객체로, 주문을 취소하는 데 필요한 정보를 담고 있다
public class CancelOrderCommand {
    CancelOrderCommand() {}
}
