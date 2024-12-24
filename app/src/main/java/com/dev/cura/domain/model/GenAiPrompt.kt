package com.dev.cura.domain.model

class GenAiPrompt {
    fun generatePrompt(jsonString: String): String {
        return "Given a structured list of surveys where each survey contains:\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"- A list of questions and answers related to addiction,\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"- The type of addiction,\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"- Severity levels,\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"- A predefined list of remedies,\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"Generate a JSON response. The response should include 4 selected remedies based on the severity level (e.g., mild, moderate, dangerous). Each remedy should have a name and an associated time, if available, or null if no specific time is provided. The time should be taken from the provided question and answer when available.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"- Input Format:\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"  - A structured JSON input with:\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"    - survey: A list of questions and answers related to addiction.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"    - type_of_addiction: The type of addiction (e.g., Alcohol, Drugs).\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"    - tags: Severity tags (e.g., mild, moderate, dangerous).\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"    - severity_level: Severity level (e.g., mild, moderate, dangerous).\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"    - predefined_remedies: A list of predefined remedies (e.g., Safety Reminder, Meditation, Book Reading, Following Passion, Follow Group).\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"- Output Format:\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"  - JSON response with:\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"    - selected_remedies: A list of 4 remedies, each with a name and an associated time, if available, or null if no time is provided.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"    - severity_tags: Associated severity tags.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"Example Input:\\n\" +\n" +
                "                \"\\n\" +\n" + { jsonString } +
                "                \"\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"NOTE: JUST GAVE THE REQUIRED OUTPUT AND NOTHING ELSE. DO NOT USE WORD Json AND DO NOT INCLUDE ''' AND ''':ONLY PROVIDE JSON OUTPUT\""
    }
}