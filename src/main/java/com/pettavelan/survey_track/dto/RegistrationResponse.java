package com.pettavelan.survey_track.dto;

public record RegistrationResponse(
        String email,
        String message,
        String status
) {}