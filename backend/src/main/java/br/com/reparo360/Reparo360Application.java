package br.com.reparo360;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;



@SpringBootApplication
public class Reparo360Application {
    public static void main(String[] args) {
        SpringApplication.run(Reparo360Application.class, args);
        Logger logger = (Logger) LoggerFactory.getLogger("org.springframework.security");
        logger.setLevel(Level.DEBUG);



    }
}

