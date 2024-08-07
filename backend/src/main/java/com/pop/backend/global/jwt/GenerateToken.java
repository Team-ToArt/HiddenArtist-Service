package com.pop.backend.global.jwt;

public record GenerateToken(
    String accessToken,
    RefreshToken refreshToken
) {

}
