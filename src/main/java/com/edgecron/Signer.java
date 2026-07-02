package com.edgecron;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class Signer {
    private Signer() {}

    public static String sign(String secret, String timestamp, Map<String, String> query, byte[] body) {
        List<String> keys = new ArrayList<>();
        if (query != null) {
            keys.addAll(query.keySet());
        }
        Collections.sort(keys);

        StringBuilder payload = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            if (i > 0) payload.append('&');
            payload.append(keys.get(i)).append('=').append(query.get(keys.get(i)));
        }
        if (body != null && body.length > 0) {
            if (payload.length() > 0) payload.append('&');
            payload.append(new String(body, StandardCharsets.UTF_8));
        }
        String toSign = timestamp + "\n" + payload;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] hmac = mac.doFinal(toSign.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hmac) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException("edgecron: failed to sign request", e);
        }
    }
}
