package com.kabir.kabirbackend.config.security.encryption;

public class Encryption {

    public static String strDecrypt(String str, int key) {
        StringBuilder strEncrypt_returnvalue = new StringBuilder();
        String strEncrypted = str;

        int i;
        int newValue;
        final int len = strEncrypted.length();

        try {
            strEncrypted = strEncrypted.replace((char) 32, (char) 34);
            strEncrypted = strEncrypted.replace((char) 33, (char) 39);

            for (i = 0; i < len; i++) {
                newValue = strEncrypted.charAt(i) - 34;
                newValue -= (i * (i % 7)) + len + key;
                while (newValue < 0)
                    newValue += 93;
                strEncrypt_returnvalue.append((char) (newValue + 34));
            }

            strEncrypt_returnvalue = new StringBuilder(strEncrypt_returnvalue.toString().replace((char) 34, (char) 32));
            strEncrypt_returnvalue = new StringBuilder(strEncrypt_returnvalue.toString().replace((char) 39, (char) 33));

            return (strEncrypt_returnvalue.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public static String strEncrypt(String str, int key) {
        StringBuilder strEncrypt_returnvalue = new StringBuilder();
        String strEncrypted = str;

        int i;
        int newValue;
        final int len = strEncrypted.length();

        try {
            strEncrypted = strEncrypted.replace((char) 34, (char) 32);
            strEncrypted = strEncrypted.replace((char) 39, (char) 32);

            strEncrypted = strEncrypted.replace((char) 32, (char) 34);
            strEncrypted = strEncrypted.replace((char) 33, (char) 39);

            for (i = 0; i < len; i++) {
                newValue = strEncrypted.charAt(i) - 34;
                newValue += (i * (i % 7)) + len + key;
                if (newValue > 93)
                    newValue %= 93;
                strEncrypt_returnvalue.append((char) (newValue + 34));
            }

            strEncrypt_returnvalue = new StringBuilder(strEncrypt_returnvalue.toString().replace((char) 34, (char) 32));
            strEncrypt_returnvalue = new StringBuilder(strEncrypt_returnvalue.toString().replace((char) 39, (char) 33));

            return (strEncrypt_returnvalue.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        String s1 = "test";
        String s2 = "";
        encrypt(s1);
        decrypt(s2);
    }

    private static void encrypt(String s1) {
        String s2;
        s2 = strEncrypt(s1, 7);
        System.out.println(s2);
    }

    private static void decrypt(String s1) {
        String s2;
        s2 = strDecrypt(s1, 7);
        System.out.println(s2);
    }
}

