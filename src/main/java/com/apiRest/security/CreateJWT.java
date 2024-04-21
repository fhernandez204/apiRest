package com.apiRest.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

import static com.apiRest.utils.ReadProp.getProperties;


@Service
public class CreateJWT {
    private static final Logger logger = LogManager.getLogger();

    private String secretKey = getProperties().getProperty("pro.secret.key");

    public String createJWT(String id, String issuer, String subject, long ttlMillis) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

	
	  public Claims decodeJWT(String jwt) {
		  //This line will throw an exception if it is not a signed JWS (as expected)   
		  JwtParserBuilder jwtParserBuilder = Jwts.parser()
		  .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey));
		  Claims claims = jwtParserBuilder.build().parseClaimsJwt(jwt).getBody();
		  //.parseClaimsJws(jwt).getBody(); 
		  return claims; 
	  }	 

}
