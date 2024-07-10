package com.green.project2nd.user.datacheck;

import java.security.SecureRandom;

public class Const {

    private static final String NICKNAME_REGEX = "^[0-9a-zA-Zㄱ-ㅎ가-힣]{4,10}$";

    public static boolean isValidNickname(String nickname) {
        return nickname.matches(NICKNAME_REGEX);
    }

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9]{6,12}@[a-z]{3,7}\\.(com|net|org){1}$";

    public static boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }
    private static final String BIRTHDATE_REGEX = "^(19|20)\\d{2}-(0[13578]|1[02])-31$|^(19|20)\\d{2}-(0[1,3-9]|1[0-2])-(29|30)$|^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|1\\d|2[0-8])$";
    public static boolean isValidBirthDate(String birth) {
        return birth.matches(BIRTHDATE_REGEX);
    }

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@^*?";

    private static final SecureRandom RANDOM = new SecureRandom();
    public static String tempPassword(int length) {
        StringBuilder password = new StringBuilder(length);
        password.append(UPPER.charAt(RANDOM.nextInt(UPPER.length())));
        password.append(LOWER.charAt(RANDOM.nextInt(LOWER.length())));
        password.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
        password.append(SPECIAL.charAt(RANDOM.nextInt(SPECIAL.length())));
        String allChars = UPPER + LOWER + DIGITS;
        for (int i = 4; i < length; i++) {
            password.append(allChars.charAt(RANDOM.nextInt(allChars.length())));
        }
        return password.toString();
    }

}
