package com.overlog;

import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@RestController
public class Controller {

    @PostMapping(path = "/log")
    public String sendLog(@RequestParam String type, @RequestParam String text, @RequestParam String JWT) throws Exception {



        EngineConnection databaseConnectionObject = new EngineConnection();
        if (JWTController.validateJWT(JWT)){

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = new String(sdf.format(timestamp));

            return databaseConnectionObject.sendMessage("log" +  "seperator" + type + "seperator" + text +  "seperator" + JWTController.getID(JWT) + "seperator" + date);
        }
        else return "Invalid JWT";

    }

    @PostMapping(path = "/login")
    public String signin(@RequestParam String username, @RequestParam String passwd) throws Exception {


        EngineConnection databaseConnectionObject = new EngineConnection();
        String id = databaseConnectionObject.sendMessage("user" + "seperator" + username + "seperator" + passwd);



        return id.equals("-1") ? "Incorrenct username or password" :JWTController.generateJWT(username, passwd, id);
    }

}