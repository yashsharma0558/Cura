package com.dev.cura.domain.model

data class UserDetails(
    val name: String,
    val email: String,
    val photo: String,
    val token: String,
    val surveyResponse: SurveyResponse
)
