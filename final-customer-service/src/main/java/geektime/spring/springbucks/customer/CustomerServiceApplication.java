package geektime.spring.springbucks.customer;

import geektime.spring.springbucks.customer.integration.Waiter;
import geektime.spring.springbucks.customer.support.CustomConnectionKeepAliveStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
@EnableDiscoveryClient//能够让注册中心能够发现，扫描到改服务
@EnableFeignClients//开启Feign支持
@EnableAspectJAutoProxy//开启apo支持
@EnableBinding(Waiter.class)
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    public CloseableHttpClient httpClient() {
        return HttpClients.custom()
                .setConnectionTimeToLive(30, TimeUnit.SECONDS)//连接存活时间，如果不设置，则根据长连接信息决定
                .evictIdleConnections(30, TimeUnit.SECONDS)//空闲连接退出时间
                .setMaxConnTotal(200)//连接池中最大连接数
                .setMaxConnPerRoute(20)//分配给同一个route(路由)最大的并发连接数
                .disableAutomaticRetries() //关闭自动重试 重试：请求处理的时候，系统进行了处理，但是返回响应的时候，没有把响应返回，客户端，会认为该操作没有成功，会再次尝试。这对于打款之类的敏感操作是有问题的 有 Keep-Alive 认里面的值，没有的话永久有效
                //.setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)  也可以使用官方提供的请求策略
                .setKeepAliveStrategy(new CustomConnectionKeepAliveStrategy())//设置请求策略
                .build();
    }
}
