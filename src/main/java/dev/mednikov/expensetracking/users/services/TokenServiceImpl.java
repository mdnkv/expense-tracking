package dev.mednikov.expensetracking.users.services;

import dev.mednikov.expensetracking.users.exceptions.TokenException;
import dev.mednikov.expensetracking.users.models.User;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    private final JWSVerifier verifier;
    private final JWSSigner signer;

    public TokenServiceImpl(@Value("${application.security.jwt-secret}") String secret) throws Exception{
        byte[] keyBytes = secret.getBytes();
        this.signer = new MACSigner(keyBytes);
        this.verifier = new MACVerifier(keyBytes);
    }

    @Override
    public String getToken(User user) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Date issuedAt = Timestamp.valueOf(currentDateTime);
        Date expiresAt = Timestamp.valueOf(currentDateTime.plusHours(12));
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("Expense Tracking")
                .issueTime(issuedAt)
                .expirationTime(expiresAt)
                .build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        try {
            signedJWT.sign(this.signer);
            return signedJWT.serialize();
        } catch (Exception e) {
            throw new TokenException();
        }
    }
}
