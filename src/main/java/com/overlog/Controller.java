package com.overlog;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
public class Controller {

    @PostMapping(path = "/log")
    public void boo(@RequestParam String type, @RequestParam String text) throws IOException, TimeoutException {



        SendLog.sendMessage(type + "," + text);
    }
}