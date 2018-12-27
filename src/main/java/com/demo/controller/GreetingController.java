package com.demo.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class GreetingController {

    @GetMapping("/greeting")
    public String getMessage() {
        return "Hello!";
    }

    /*
     * Example of circuit breaker where response executes fallback when timeout is reached
     */
    @RequestMapping(value = "/greeting/hello", method = GET)
    @HystrixCommand(fallbackMethod = "fallback_hello",
            commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
            })
    public String hello() throws InterruptedException {
        Thread.sleep(3000);
        return "Welcome Hystrix";
    }

    private String fallback_hello() {
        return "Request fails. It takes a long time to respond";
    }
}
