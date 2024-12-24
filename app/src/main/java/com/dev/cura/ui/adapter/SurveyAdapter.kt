package com.dev.cura.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.dev.cura.R
import com.dev.cura.domain.model.Survey

class SurveyAdapter(private val questions: List<String>) : RecyclerView.Adapter<SurveyAdapter.ViewHolder>() {

    // Store user answers
    private val answers = mutableListOf<String>()

    init {
        // Initialize answers list with empty strings
        answers.addAll(List(questions.size) { "" })
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questionTextView: TextView = view.findViewById(R.id.questionTextView)
        val answerEditText: EditText = view.findViewById(R.id.answerEditText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questions[position]
        holder.questionTextView.text = question

        // Update the answers list as the user types
        holder.answerEditText.setText(answers[position])
        holder.answerEditText.addTextChangedListener {
            answers[position] = it.toString()
        }
    }

    override fun getItemCount(): Int = questions.size

    // Retrieve user inputs
    fun getSurveyResponses(): List<Survey> {
        return questions.mapIndexed { index, question ->
            Survey(answer = answers[index], question = question)
        }
    }
}
