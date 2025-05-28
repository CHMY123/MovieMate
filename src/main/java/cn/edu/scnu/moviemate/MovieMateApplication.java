package cn.edu.scnu.moviemate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("cn.edu.scnu.moviemate.mapper")
public class MovieMateApplication {
    public static void main(String[] args) {
        SpringApplication.run(MovieMateApplication.class, args);
    }
}

