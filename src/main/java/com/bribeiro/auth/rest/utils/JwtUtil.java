package com.bribeiro.auth.rest.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.bribeiro.auth.config.KeyStoreLoader;
import com.bribeiro.auth.config.RootAppConfig;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.util.Date;

public class JwtUtil {

    private static KeyStore ks;

    public static String createJwt(int userId) {
        try {

            if (ks == null) {
                ks = new KeyStoreLoader().load();
            }

            RSAPrivateKey privateKey =
                (RSAPrivateKey) ks.getKey("1", RootAppConfig.getInstance().getKeyStorePassword());

            Algorithm algorithm = Algorithm.RSA256(null, privateKey);

            Date issued = new Date();
            Date expires = new Date(issued.getTime() + RootAppConfig.getInstance().getSessionTime());

            return JWT.create()
                    .withIssuer(String.valueOf(userId))
                    .withIssuedAt(issued)
                    .withExpiresAt(expires)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            return null;
        } catch (IOException | CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException | KeyStoreException e) {
            return null;
        }
    }

}
