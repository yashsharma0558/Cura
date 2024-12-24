package com.dev.cura.domain.model

data class GenAiRequest(
    val predefined_remedies: List<String>,
    val severity_level: String,
    val survey: List<Survey>,
    val tags: List<String>,
    val type_of_addiction: String
)