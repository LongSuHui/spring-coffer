package geektime.spring.springbucks.barista.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "T_ORDER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeOrder {
    @Id//主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//为一个实体生成一个唯一标识的主键   主键由数据库自动生成（主要是自动增长型）
    private Long id;
    private String customer;
    private String waiter;
    private String barista;
    @Enumerated
    //标记Enum类型
    @Column(nullable = false)
    //用来标识实体类中属性与数据表中字段的对应关系
    private OrderState state;

    @Column(updatable = false)
    //用来标识实体类中属性与数据表中字段的对应关系
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date updateTime;
}
