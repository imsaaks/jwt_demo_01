package com.iung.dev.common.constants;

public class AppConstants {

    public static final String APP_NAME = "IUNG_DEV";
    public static final String ACCESS_TOKEN =APP_NAME + "_ACCESS_TOKEN";
    public static final String DEFAULT_TIMEZONE_KO = "Asia/Seoul";
    public static final String SIGN_IN_PATH = "/api/v1/signin";
    public static final String SIGN_OUT_PATH = "/api/v1/signout";
    public static final String SIGN_UP_PATH = "/api/v1/users/signup";
    public static final String CHECK_ID_DUPLICATE_PATH = "/api/v1/users/checkId";
    public static final String TOKEN_CHECK_PATH = "/api/v1/users/checkToken";

    public static final int MEMBER_ID_MIN_LENGTH = 5;
    public static final int MEMBER_ID_MAX_LENGTH = 12;
    public static final int MEMBER_PW_MIN_LENGTH = 8;
    public static final int MEMBER_PW_MAX_LENGTH = 15;

    // "^[a-zA-Z0-9]*$";  // 알파벳과 숫자만 허용
    // ^[a-zA-Z][a-zA-Z0-9_@]{4,11}$  알파벳으로 시작하며 5~12자의 알파벳, 숫자, 밑줄(_), @만 가능
    public static final String MEMBER_ID_REGEX = "^[a-zA-Z][a-zA-Z0-9_@]{4,11}$";

    // ^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$
    // 영문, 숫자, 특수문자 포함, 8자 이상, 15자 이하
    public static final String MEMBER_PW_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,15}$";

    public static final String EMAIL_REGEX = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$";

    public static final int TOKEN_EXPIRATION_HOUR = 1;



}
