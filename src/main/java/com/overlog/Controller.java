package com.overlog;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
public class Controller {

    @PostMapping(path = "/log")
    public String boo(@RequestParam String type, @RequestParam String text) throws IOException, TimeoutException, InterruptedException {


        SendLog SendLogObject = new SendLog();
        return SendLogObject.sendMessage(type + "," + text);

    }
}