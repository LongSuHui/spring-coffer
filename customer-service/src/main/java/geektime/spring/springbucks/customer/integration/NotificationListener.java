package geektime.spring.springbucks.customer.integration;

import geektime.spring.springbucks.customer.model.CoffeeOrder;
import geektime.spring.springbucks.customer.model.OrderState;
import geektime.spring.springbucks.customer.model.OrderStateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationListener {
    @Autowired
    private CoffeeOrderService orderService;
    @Value("${customer.name}")
    private String customer;

    @StreamListener(Waiter.NOTIFY_ORDERS)//监听NOTIFY_ORDERS   一旦有NOTIFY_ORDERS出现 进行下列取走咖啡操作

    public void takeOrder(@Payload Long id) {
        CoffeeOrder order = orderService.getOrder(id);
        if (OrderState.BREWED == order.getState()) {
            log.info("Order {} is READY, I'll take it.", id);
            orderService.updateState(id,
                    OrderStateRequest.builder().state(OrderState.TAKEN).build());
        } else {
            log.warn("Order {} is NOT READY. Why are you notify me?", id);
        }
    }
}
