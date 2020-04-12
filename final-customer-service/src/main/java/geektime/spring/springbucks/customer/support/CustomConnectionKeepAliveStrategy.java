package geektime.spring.springbucks.customer.support;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.util.Arrays;

public class CustomConnectionKeepAliveStrategy implements ConnectionKeepAliveStrategy {
    private final long DEFAULT_SECONDS = 30;

    @Override
    public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
        return Arrays.asList(response.getHeaders(HTTP.CONN_KEEP_ALIVE))//获取KEEP_ALIVE响应头
                .stream()//转换成文本
                .filter(h -> StringUtils.equalsIgnoreCase(h.getName(), "timeout")
                        && StringUtils.isNumeric(h.getValue()))//在里面找到timeout这个属性并判断有没有  判断Value是不是个数字    //如果是数字 将文本转换成long类型   如果转换失败 会使用默认值代替（30s） 如果没有取到这个头 也会使用默认值  如果有KEEP_ALIVE头，就使用KEEP_ALIVE头里面设置的timeout 没有的话 这个连接视为永久有效
                .findFirst()
                .map(h -> NumberUtils.toLong(h.getValue(), DEFAULT_SECONDS))
                .orElse(DEFAULT_SECONDS) * 1000;

    }
}
