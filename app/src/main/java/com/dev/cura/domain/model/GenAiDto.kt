package com.dev.cura.domain.model


data class Remedy(
    val name: String,
    val time: String?
)

data class SurveyResponse(
    val selected_remedies: List<Remedy>?,
    val severity_tags: List<String>?
)
