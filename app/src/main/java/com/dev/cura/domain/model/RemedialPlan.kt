package com.dev.cura.domain.model


enum class RemedialPlan(
    val addictionType: String,
    val questions: List<String>,
    val remedy: List<Map<String, String>>,
) {
    ALCOHOL(
        "Alcohol", listOf(
            "How often do you consume alcohol?",
            "How many drinks do you have on a typical drinking day?",
            "Have you experienced blackouts or memory loss due to alcohol?",
            "Do you feel a strong urge to drink alcohol?",
            "How has your alcohol consumption impacted your personal relationships?",
            "Have you had legal issues or work-related problems because of drinking?"
        ), Remedies.ALCOHOL_REMEDY.remedy
    ),
    SUBSTANCE(
        "Substance", listOf(
            "What substances are you using (e.g., drugs, medications)?",
            "How frequently do you use the substance?",
            "Have you experienced withdrawal symptoms?",
            "Do you use substances to cope with emotional or psychological issues?",
            "Have your substance-related behaviors affected your family, friends, or work?",
            "Do you spend a significant amount of time obtaining or using substances?"
        ), Remedies.SUBSTANCE_REMEDY.remedy
    ),
    SMOKING(
        "Smoking", listOf(
            "How many cigarettes do you smoke per day?",
            "Have you attempted to quit smoking in the past?",
            "Do you experience withdrawal symptoms when not smoking?",
            "How does smoking affect your health and physical well-being?",
            "Are you concerned about the impact of smoking on your loved ones’ health?",
            "How much money do you spend on cigarettes weekly or monthly?"
        ), Remedies.SMOKING_REMEDY.remedy
    ),
    CAFFEINE(
        "Caffeine", listOf(
            "How many cups of coffee or caffeinated beverages do you consume per day?",
            "Do you experience withdrawal symptoms if you don’t consume caffeine?",
            "Have you felt the need to increase your caffeine intake over time?",
            "How has caffeine consumption affected your sleep patterns?",
            "Are you unable to focus or feel fatigued without caffeine?",
            "How has caffeine consumption impacted your physical health (e.g., headaches, digestive issues)?"
        ), Remedies.CAFFEINE_REMEDY.remedy
    );

}

enum class Remedies(val remedy: List<Map<String, String>>) {
    ALCOHOL_REMEDY(
        listOf(
            mapOf(
                "Safety Reminder" to "Avoid social triggers at {time}; plan ahead to stay alcohol-free."
            ),
            mapOf(
                "Meditation" to "It’s {time}! Take 10 minutes to meditate and center yourself."
            ),
            mapOf(
                "Book Reading" to "Grab your book at {time} to distract yourself and stay motivated."
            ),
            mapOf(
                "Following Passion" to "Engage in painting, dancing, or writing at {time} to redirect your energy."
            ),
            mapOf(
                "Follow Group" to "Share your journey with the support group at {time} to stay accountable."
            ),
            mapOf(
                "Hydration Reminder" to "Remember to drink water at {time} to manage cravings effectively."
            ),
            mapOf(
                "Sleep Reminder" to "Wind down by {time} to ensure better rest and recovery."
            )
        )
    ),
    SUBSTANCE_REMEDY(
        listOf(
            mapOf(
                "Safety Reminder" to "Avoid high-risk places or people around {time}; keep your goals in mind."
            ),
            mapOf(
                "Meditation" to "Calm your mind with meditation at {time} to manage withdrawal symptoms."
            ),
            mapOf(
                "Book Reading" to "Take a reading break at {time} with an inspiring book to boost your willpower."
            ),
            mapOf(
                "Following Passion" to "Start your creative session (e.g., music or crafting) at {time} to beat cravings."
            ),
            mapOf(
                "Follow Group" to "Connect with your recovery group at {time} for motivation and tips."
            ),
            mapOf(
                "Healthy Eating Reminder" to "Eat a healthy snack at {time} to stabilize your energy and mood."
            ),
            mapOf(
                "Relapse Prevention Reminder" to "Review your relapse prevention plan at {time} to stay focused."
            )
        )
    ),
    SMOKING_REMEDY(
        listOf(
            mapOf(
                "Safety Reminder" to "Avoid smoking zones at {time}; take a different route or distract yourself."
            ),
            mapOf(
                "Meditation" to "It’s {time}! Deep breathing can help reduce nicotine cravings."
            ),
            mapOf(
                "Book Reading" to "Read a motivational story at {time} to inspire your smoke-free journey."
            ),
            mapOf(
                "Following Passion" to "Start journaling, gardening, or playing music at {time} to stay engaged."
            ),
            mapOf(
                "Follow Group" to "Update your progress with the group at {time} to celebrate small wins."
            ),
            mapOf(
                "Hydration Reminder" to "Drink water or herbal tea at {time} to fight cravings."
            ),
            mapOf(
                "Chewing Gum Reminder" to "Chew gum or have a mint at {time} to replace the urge to smoke."
            )
        )
    ),
    CAFFEINE_REMEDY(
        listOf(
            mapOf(
                "Safety Reminder" to "Avoid coffee shops at {time}; switch to herbal tea instead."
            ),
            mapOf(
                "Meditation" to "Start your meditation session at {time} to stay calm and energized."
            ),
            mapOf(
                "Book Reading" to "Dive into a caffeine-free lifestyle guide at {time} to stay on track."
            ),
            mapOf(
                "Following Passion" to "Focus on hiking, photography, or journaling at {time} to avoid coffee cravings."
            ),
            mapOf(
                "Follow Group" to "Share your success in reducing caffeine at {time} with your support group."
            ),
            mapOf(
                "Hydration Reminder" to "Drink a glass of water at {time} to replace caffeinated beverages."
            ),
            mapOf(
                "Sleep Reminder" to "Prepare for better sleep by avoiding caffeine after {time}."
            )
        )
    )

}
