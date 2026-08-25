package com.akranta.perfex_sb.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PasswordEncryptionUtil {

    public static String encryptPassword(String password, int userPin) {

        int tempUserPin = getFormattedUserPin(userPin);

        StringBuilder encrypted = new StringBuilder();

        for (int i = 0; i < password.length(); i++) {

            int sum = password.charAt(i) + tempUserPin;

            if (sum < 128) {
                encrypted.append((char) sum);
            } else {
                encrypted.append((char) (32 + (sum - 128) + tempUserPin));
            }
        }

        return encrypted.toString();
    }

    private static int getFormattedUserPin(int userPin) {

        NumberFormat formatter = new DecimalFormat("0000");
        String pin = formatter.format(userPin);

        if (Integer.parseInt(pin) == 0) {
            throw new IllegalArgumentException("Invalid User Pin");
        }

        int sum = 0;

        for (char c : pin.toCharArray()) {
            sum += c;
        }

        String sumStr = Integer.toString(sum);

        while (sum > 9) {

            sum = 0;

            for (char c : sumStr.toCharArray()) {
                sum += c - '0';
            }

            sumStr = Integer.toString(sum);
        }

        return sum;
    }
}