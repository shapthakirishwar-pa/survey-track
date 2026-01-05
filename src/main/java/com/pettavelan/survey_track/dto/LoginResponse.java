package com.pettavelan.survey_track.dto;

public record LoginResponse(
        String accessToken,
        String email,
        String role,
        String status
) {
}
