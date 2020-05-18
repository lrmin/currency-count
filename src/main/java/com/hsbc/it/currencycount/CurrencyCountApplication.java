package com.hsbc.it.currencycount;

import com.hsbc.it.currencycount.show.ConsoleShow;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class CurrencyCountApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyCountApplication.class, args);
        ConsoleShow consoleShow = new ConsoleShow();
        consoleShow.printConsole();
    }

}
