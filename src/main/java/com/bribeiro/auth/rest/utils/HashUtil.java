package com.bribeiro.auth.rest.utils;

import com.bribeiro.auth.config.RootAppConfig;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class HashUtil {

    public static String hashPassword(String password, char[] salt) {
        try {

            MessageDigest md = MessageDigest.getInstance(RootAppConfig.getInstance().getHashAlgorithm());
            md.update(Hex.decodeHex(salt));
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

            return Hex.encodeHexString(hashedPassword);
        } catch (NoSuchAlgorithmException | DecoderException e) {
            return null;
        }
    }
}
