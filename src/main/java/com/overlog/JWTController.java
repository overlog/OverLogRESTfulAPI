package com.overlog;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.util.Base64;


import org.json.JSONObject;

public class JWTController {

    public static final String SECRET_KEY = "overlog";

    public static String generateJWT(String username, String passwd, String id) throws Exception {

        JSONObject HEADER = new JSONObject();
        HEADER.append("alg", "HS256");
        HEADER.append("typ", "JWT");

        JSONObject PAYLOAD = new JSONObject();
        PAYLOAD.append("username", username);
        PAYLOAD.append("passwd", passwd);
        PAYLOAD.append("id", id);


        String PART1 = doBASE64(HEADER.toString());
        String PART2 = doBASE64(PAYLOAD.toString());

        String PART1_PART2 = PART1 + "." + PART2;

        String PART3 = doBASE64(doHMACSHA256(PART1_PART2, SECRET_KEY));

        String JWT_TOKEN = PART1_PART2 + "." + PART3;

        return JWT_TOKEN;
    }

    private static String doHMACSHA256(String part1AndPart2, String secretKey) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secretKey.getBytes(), "HmacSHA256"));

        byte[] hashBytes = mac.doFinal(part1AndPart2.getBytes());
        String hash = doBASE64(hashBytes);
        return hash;
    }

    private static String doBASE64(byte[] bytes) {
        Base64.Encoder encoder = Base64.getEncoder();
        String base64 = encoder.encodeToString(bytes);
        return base64;
    }

    private static String doBASE64(String input)throws Exception {
        byte[] bytes = input.getBytes(Charset.forName("UTF-8"));
        String base64 = doBASE64(bytes);

        byte[] bytess = "Hello, World!".getBytes("UTF-8");
        String encoded = Base64.getEncoder().encodeToString(bytes);
        byte[] decoded = Base64.getDecoder().decode(base64);
        String str = new String(decoded);




        return base64;
    }

    public static boolean validateJWT(String jwt) throws Exception {

        String[] parts = jwt.split("\\.");
        String PART1 = parts[0];
        String PART2 = parts[1];
        String PART3 = parts[2];


        String PART1_PART2 = PART1 + "." + PART2;

        String jwtSignature = doBASE64(doHMACSHA256(PART1_PART2, SECRET_KEY));

        return jwtSignature.equals(PART3);

    }

    public static String getID(String JWT){

        String[] parts = JWT.split("\\.");
        String PAYLOAD = parts[1];

        byte[] decoded = Base64.getDecoder().decode(PAYLOAD);
        String payloadStr = new String(decoded);

        JSONObject payloadJSon = new JSONObject(payloadStr);

        String idString = payloadJSon.get("id").toString();
        idString = idString.substring(2, idString.lastIndexOf("\""));



        return idString ;
    }


}
