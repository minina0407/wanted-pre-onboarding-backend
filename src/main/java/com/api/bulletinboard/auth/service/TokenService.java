package com.api.bulletinboard.auth.service;



import com.api.bulletinboard.auth.dto.Token;
import com.api.bulletinboard.auth.utils.JwtTokenProvider;
import com.api.bulletinboard.exception.dto.BadRequestException;
import com.api.bulletinboard.user.dto.AuthorizeUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private static final String REFRESH_TOKEN_PREFIX = "refreshToken:";

    public Token login(AuthorizeUser user) {

        UsernamePasswordAuthenticationToken authenticationToken = user.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String accessToken = jwtTokenProvider.createAccessToken(authentication, authorities);
        String refreshToken = jwtTokenProvider.createRefreshToken(authentication, authorities);

        Token token = Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        return token;
    }


}
