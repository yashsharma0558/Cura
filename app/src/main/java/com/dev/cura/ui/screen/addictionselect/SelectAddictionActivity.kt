package com.dev.cura.ui.screen.addictionselect

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev.cura.R
import com.dev.cura.domain.model.RemedialPlan

class SelectAddictionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_addiction)

        findViewById<RadioButton>(R.id.radioAlcohol).text = RemedialPlan.ALCOHOL.addictionType
        findViewById<RadioButton>(R.id.radioSubstance).text = RemedialPlan.SUBSTANCE.addictionType
        findViewById<RadioButton>(R.id.radioSmoking).text = RemedialPlan.SMOKING.addictionType
        findViewById<RadioButton>(R.id.radioCaffeine).text = RemedialPlan.CAFFEINE.addictionType

        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)
        val submitButton: TextView = findViewById(R.id.tvnext)

        submitButton.setOnClickListener {
            // Get the selected RadioButton's text
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId

            if (selectedRadioButtonId != -1) {
                val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
                val selectedAddiction = selectedRadioButton.text.toString()

                // Pass the selected addiction to the next activity
                val intent = Intent(this, AddictionQuestionsActivity::class.java)
                intent.putExtra("addictionName", selectedAddiction)
                startActivity(intent)
            } else {
                // Handle case where no option is selected
                Toast.makeText(this, "Please select an addiction type", Toast.LENGTH_SHORT).show()
            }
        }


    }

}