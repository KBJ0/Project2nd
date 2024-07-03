package com.green.project2nd.project2nd.user.datacheck;

public class Const {

    private static final String NICKNAME_REGEX = "^[0-9a-zA-Zㄱ-ㅎ가-힣]{4,10}$";

    public static boolean isValidNickname(String nickname) {
        return nickname.matches(NICKNAME_REGEX);
    }

    private static final String Email_REGEX = "^[a-zA-Z0-9]{6,12}@[a-z]{3,7}\\.(com|net|org){1}$";

    public static boolean isValidEmail(String email) {
        return email.matches(Email_REGEX);
    }

}
