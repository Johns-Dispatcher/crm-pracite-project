package pers.johns.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ClassName    : Application
 * <br/>
 * Description  : 主启动文件 应用入口
 * <br/>
 * CreateTime   : 2024/6/24 13:52
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@SpringBootApplication
@MapperScan("pers.johns.crm.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
