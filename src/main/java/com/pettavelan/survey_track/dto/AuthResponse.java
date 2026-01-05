package com.pettavelan.survey_track.dto;

public record AuthResponse(
   String message,
   String status,
   String email
) {}
