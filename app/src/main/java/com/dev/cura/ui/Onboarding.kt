package com.dev.cura.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.dev.cura.R
import com.dev.cura.ui.auth.LoginActivity

class Onboarding : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        findViewById<Button>(R.id.nextButton).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}