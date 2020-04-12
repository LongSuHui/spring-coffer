package geektime.spring.springbucks.barista;

import geektime.spring.springbucks.barista.integration.Waiter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
//用来扫描和发现指定包及其子包中的Repository定义
@SpringBootApplication
//Spring Boot核心注解，用于开启自动配置。可以解决根类或者配置类  当前类里以 @Bean 注解标记的方法的实例注入到srping容器中，实例名即为方法名。
@EnableBinding(Waiter.class)
//带着一个或多个接口作为参数  即扫描Waiter中的类 一个接口往往声名了输入和输出的渠道
public class BaristaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaristaServiceApplication.class, args);
	}

}
