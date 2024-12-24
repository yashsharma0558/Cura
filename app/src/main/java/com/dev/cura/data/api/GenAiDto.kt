package com.dev.cura.data.api


data class Remedy(
    val name: String,
    val time: String? // Nullable
)

data class SurveyResponse(
    val selected_remedies: List<Remedy>?, // Match JSON key: "selected_remedies"
    val severity_tags: List<String>?     // Match JSON key: "severity_tags"
)
