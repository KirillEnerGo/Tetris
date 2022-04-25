package com.example.tetrisnoactivity

import android.content.Intent
import android.content.Intent.getIntent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.example.tetrisnoactivity.storage.AppPreferences
import com.google.android.material.snackbar.Snackbar
import com.google.androidgamesdk.GameActivity


class MainActivity : AppCompatActivity() {

	var tvHighScore: TextView? = null
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		supportActionBar?.hide()
		val btnNewGame = findViewById<Button>(R.id.btn_new_game)
		val btnResetScore = findViewById<Button>(R.id.btn_reset_score)
		val btnExit = findViewById<Button>(R.id.btn_exit)
		tvHighScore = findViewById<TextView>(R.id.tv_high_score)

		btnNewGame.setOnClickListener(this::onBTNNewGameClick)
		btnResetScore.setOnClickListener(this::onBtnResetScoreClick)
		btnExit.setOnClickListener(this::onBtnExitClick)
	}
	private fun onBTNNewGameClick(view: View){
		val intent = Intent(
			this,
			com.example.tetrisnoactivity.GameActivity::class.java)
		startActivity(intent)
	}
	private fun onBtnResetScoreClick(view: View){
		val preferences = AppPreferences(this)
		preferences.clearHighScore()
		Snackbar.make(view, "Score successfully reset",
			Snackbar.LENGTH_SHORT).show()
		tvHighScore?.text = "High score: ${preferences.getHighScore()}"
	}
	private fun onBtnExitClick(view: View){
		System.exit(0)
	}

}