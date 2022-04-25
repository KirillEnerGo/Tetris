package com.example.tetrisnoactivity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tetrisnoactivity.storage.AppPreferences

class GameActivity: AppCompatActivity(R.layout.activity_game) {
	var tvHigScore: TextView? = null
	var tvCurrentScore: TextView? = null
	var appPreferences: AppPreferences? = null

	public override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_game)
		appPreferences = AppPreferences(this)

		val btnRestart = findViewById<Button>(R.id.btn_restart)
		tvHigScore = findViewById<TextView>(R.id.tv_high_score)
		tvCurrentScore = findViewById<TextView>(R.id.tv_current_score)
		updateHighScore()
		updateCurrentScore()
	}

	private fun updateCurrentScore() {
		tvCurrentScore?.text = "0"
	}

	private fun updateHighScore() {
		tvHigScore?.text = "${appPreferences?.getHighScore()}"
	}

}