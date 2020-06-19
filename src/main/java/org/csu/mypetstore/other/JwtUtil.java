package org.csu.mypetstore.other;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.csu.mypetstore.domain.Item;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class JwtUtil {
    private static final long EXPIRE_TIME = 1000 * 60 * 1000;
    private static final String secret = "as3gj1wk2kj21yka1skj2v3u3aus6p152qs7ua9r21a";

    public static String createJWT(String username){
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            Map<String,Object> header = new HashMap<>();
            header.put("Type","JWT");
            header.put("alg","HS256");
            return JWT.create()
                    .withHeader(header)
                    .withClaim("username",username)
                    .withExpiresAt(date)
                    .sign(algorithm);
    }

    public static boolean verify(String token){
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT jwt = jwtVerifier.verify(token);
        return jwt != null;
    }

}
