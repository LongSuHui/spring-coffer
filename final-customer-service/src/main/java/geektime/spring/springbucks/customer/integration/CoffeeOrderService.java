package geektime.spring.springbucks.customer.integration;

import geektime.spring.springbucks.customer.model.CoffeeOrder;
import geektime.spring.springbucks.customer.model.NewOrderRequest;
import geektime.spring.springbucks.customer.model.OrderStateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "waiter-service", contextId = "coffeeOrder")
//name 这两个就同一个意思:对应的是调用的微服务的服务名,对用服务发现、走网关调用,这个很关键.
//因为 CoffeeService和CoffeeOrderService的name相同 为了区分 增加contextId 防止产生冲突
public interface CoffeeOrderService {
    @GetMapping("/order/{id}")
    CoffeeOrder getOrder(@PathVariable("id") Long id);//@PathVariable 可以将 URL 中占位符参数绑定到控制器处理方法的入参中：URL 中的 {xxx} 占位符可以通过@PathVariable(“xxx“) 绑定到操作方法的入参中。

    @PostMapping(path = "/order/", consumes = MediaType.APPLICATION_JSON_VALUE,//表示request的content-type必须为application/json
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE) //表示返回给接口请求的是json数据
    CoffeeOrder create(@RequestBody NewOrderRequest newOrder);

    @PutMapping("/order/{id}")
    CoffeeOrder updateState(@PathVariable("id") Long id,
                            @RequestBody OrderStateRequest orderState);
}
