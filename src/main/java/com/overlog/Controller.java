package com.overlog;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
public class Controller {

    @PostMapping(path = "/log")
    public String boo(@RequestParam String id) throws IOException, TimeoutException {
        System.out.println(id);
        SendLog logSender = new SendLog();
        return id;
    }
}