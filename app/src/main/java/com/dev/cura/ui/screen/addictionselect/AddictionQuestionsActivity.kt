package com.dev.cura.ui.screen.addictionselect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.cura.R
import com.dev.cura.data.api.RetrofitClient
import com.dev.cura.domain.model.GenAiPrompt
import com.dev.cura.domain.model.GenAiRequest
import com.dev.cura.domain.model.RemedialPlan
import com.dev.cura.domain.model.Remedies
import com.dev.cura.domain.model.SurveyResponse
import com.dev.cura.ui.adapter.SurveyAdapter
import com.dev.cura.ui.screen.home.HomeActivity
import com.google.ai.client.generativeai.GenerativeModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class AddictionQuestionsActivity : AppCompatActivity() {
    private lateinit var remedyList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addiction_questions)

        val addictionName = intent.getStringExtra("addictionName") ?: return
        title = addictionName

        remedyList = when (addictionName) {
            RemedialPlan.ALCOHOL.addictionType -> Remedies.ALCOHOL_REMEDY.remedy.flatMap { it.keys }


            RemedialPlan.SUBSTANCE.addictionType -> Remedies.SUBSTANCE_REMEDY.remedy.flatMap { it.keys }


            RemedialPlan.SMOKING.addictionType -> Remedies.SMOKING_REMEDY.remedy.flatMap { it.keys }


            RemedialPlan.CAFFEINE.addictionType -> Remedies.CAFFEINE_REMEDY.remedy.flatMap { it.keys }


            else -> listOf("No questions available.")
        }

        val questions = when (addictionName) {
            RemedialPlan.ALCOHOL.addictionType -> {
                RemedialPlan.ALCOHOL.questions
            }

            RemedialPlan.SUBSTANCE.addictionType -> {
                RemedialPlan.SUBSTANCE.questions
            }

            RemedialPlan.SMOKING.addictionType -> {
                RemedialPlan.SMOKING.questions
            }

            RemedialPlan.CAFFEINE.addictionType -> {
                RemedialPlan.CAFFEINE.questions
            }

            else -> listOf("No questions available.")
        }

        // Set up RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.questionsRecyclerView)
        val adapter = SurveyAdapter(questions)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Submit button
        val submitButton: Button = findViewById(R.id.submitButton)
        submitButton.setOnClickListener {
            // Collect responses as List<Survey>
            val surveyResponses = adapter.getSurveyResponses()
            val genAiPrompt = GenAiRequest(
                predefined_remedies = remedyList,
                severity_level = "mild",
                survey = surveyResponses,
                tags = listOf("mild", "preventive"),
                type_of_addiction = addictionName
            )
            println("GENAIPROMPT: $genAiPrompt")
            lifecycleScope.launch {
                val jsonSurveyResponse = generateContent(GenAiPrompt().generatePrompt(genAiPrompt.toString()))
                if (jsonSurveyResponse != null) {
                    // Execute your logic using surveyResponse
                    println("Survey Response: $jsonSurveyResponse")
                    try {
                        val email = getUserDetails(this@AddictionQuestionsActivity)["email"]?:return@launch
                        val response = RetrofitClient.instance.setRemedy(email, jsonSurveyResponse)
                        if (response.isSuccessful) {
                            val updatedRemedyJson = response.body()
                            println("Updated Remedy JSON: $updatedRemedyJson")
                            val sharedPreferences = applicationContext.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putBoolean("hasCompletedSurvey", true)
                            editor.apply()
                            startActivity(Intent(this@AddictionQuestionsActivity, HomeActivity::class.java ))
                            finish()
                        } else {
                            Log.d("AIHERELOL", "${response.errorBody()?.string()}")

                        }
                    } catch (e: Exception) {
                        Log.d("AIHERELOL", "${e.message}")
                    }
                    // Example: Update UI or process data
                } else {
                    Log.d("AIHERELOL", "Survey Response is null")
                }

            }

        }
    }

    private suspend fun generateContent(prompt: String): JsonObject? {
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = com.dev.cura.BuildConfig.API_KEY
        )
        val response = generativeModel.generateContent(prompt)
        val finalString = response.text?.let { extractBracesContent(it) } ?:return null
        val gson = Gson()
        Log.d("AIHERELOL", "SurveyResponse: ${gson.fromJson(finalString, SurveyResponse::class.java)}")
        return gson.fromJson(finalString, JsonObject::class.java)

    }

    private fun extractBracesContent(input: String): String? {
        val startIndex = input.indexOf('{')
        val endIndex = input.lastIndexOf('}')
        return if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            input.substring(startIndex, endIndex + 1)
        } else {
            null
        }
    }

    fun getUserDetails(context: Context): Map<String, String?> {
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        return mapOf(
            "name" to sharedPreferences.getString("name", null),
            "email" to sharedPreferences.getString("email", null),
            "photo" to sharedPreferences.getString("photo", null),
            "token" to sharedPreferences.getString("token", null)
        )
    }


}
