package com.apiRest.security;

import static com.apiRest.utils.ReadProp.getProperties;

public class Constans {

    // Spring Security
    public static final String LOGIN_URL = "/api/users";
    public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";

    // JWT
    public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 day
    public static final String SECRET_KEY = getProperties().getProperty("prop.secret.key");
    public static final long JW_TIME_TO_LIVE = Long.parseLong(getProperties().getProperty("prop.jwt.time.to.live")); // used to calculate expiration (claim = exp)
    //VALIDATIONS
    public static final String EMAIL_WITH_FORMAT = getProperties().getProperty("prop.email.with.format");
    public static final String USER_EXISTS_BD = getProperties().getProperty("prop.user.exists.bd");
    public static final String ERROR_INSERT_STATUS = getProperties().getProperty("prop.error.insert.status");
    public static final String ERROR_EXCEPTION_THROWN = getProperties().getProperty("prop.error.exception.thrown");
    public static final String USER_NOT_EXISTS_BD = getProperties().getProperty("prop.user.not.exists.bd");

}
