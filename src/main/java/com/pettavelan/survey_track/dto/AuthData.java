package com.pettavelan.survey_track.dto;

public record AuthData(
        String accessToken,
        String refreshToken,
        String email,
        String role
) {}
