package com.dev.cura.domain.model

enum class RemedialPlan(val displayName: String) {
    ALCOHOL("Alcohol"),
    SMOKING("Smoking"),
    SUBSTANCE("Substance"),
    CAFFEINE("Caffeine"),
    OTHERS("Others");

    // A helper method to get a plan by its display name
    companion object {
        fun fromDisplayName(name: String): RemedialPlan? {
            return entries.find { it.displayName.equals(name, ignoreCase = true) }
        }
    }
}