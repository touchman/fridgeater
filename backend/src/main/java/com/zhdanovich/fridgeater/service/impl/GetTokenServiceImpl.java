package com.zhdanovich.fridgeater.service.impl;

import com.zhdanovich.fridgeater.service.GetTokenService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class GetTokenServiceImpl implements GetTokenService {

    private final UserDetailsService userDetailsService;

    @Override
    public String getToken(final String username, final String password) throws Exception {
        if (username == null || password == null)
            return null;
        final User user = (User) userDetailsService.loadUserByUsername(username);
        final Map<String, Object> tokenData = new HashMap<>();
        final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        if (encoder.matches(password, user.getPassword())) {
            tokenData.put("clientType", "user");
            tokenData.put("username", username);
            tokenData.put("token_create_date", new Date().getTime());
            final Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, 100);
            tokenData.put("token_expiration_date", calendar.getTime());
            final JwtBuilder jwtBuilder = Jwts.builder();
            jwtBuilder.setExpiration(calendar.getTime());
            jwtBuilder.setClaims(tokenData);
            final String key = "key123";
            return jwtBuilder.signWith(SignatureAlgorithm.HS512, key).compact();
        } else {
            throw new Exception("Authentication error");
        }
    }

}