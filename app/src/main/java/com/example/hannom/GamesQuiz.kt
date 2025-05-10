package com.example.hannom

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.hannom.databinding.ActivityGamesQuizBinding
import androidx.core.graphics.drawable.toDrawable

class GamesQuiz : AppCompatActivity() {

    private lateinit var binding: ActivityGamesQuizBinding
    private lateinit var popup: Dialog
    private var selectedLevel: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamesQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupPopup()
    }

    private fun setupViews() {
        binding.buttonback.setOnClickListener { onBackPressed() }

        binding.button.setOnClickListener { selectLevel("1", binding.button) }
        binding.button2.setOnClickListener { selectLevel("2", binding.button2) }
        binding.button3.setOnClickListener { selectLevel("3", binding.button3) }

        binding.batdau.setOnClickListener {
            if (selectedLevel.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn level trước!", Toast.LENGTH_SHORT).show()
            } else {
                startQuizActivity()
            }
        }

        binding.buttonHelp.setOnClickListener {
            popup.show()
        }
    }

    private fun selectLevel(level: String, selectedButton: Button) {
        selectedLevel = level
        resetAllButtons()

        selectedButton.setBackgroundResource(R.drawable.circlegreen)
        selectedButton.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
    }

    private fun resetAllButtons() {
        listOf(binding.button, binding.button2, binding.button3).forEach { button ->
            button.setBackgroundResource(R.drawable.circlepurple)
            button.setTextColor(ContextCompat.getColor(this, R.color.white))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupPopup() {
        popup = Dialog(this).apply {
            setContentView(R.layout.helpdialog)
            window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

            findViewById<ImageButton>(R.id.button_xhelp).setOnClickListener { dismiss() }
            findViewById<TextView>(R.id.help).text = "Chọn số lượng câu hỏi bạn muốn"
        }
    }

    private fun startQuizActivity() {
        try {
            val intent = Intent(this, Quiz::class.java).apply {
                putExtra("Level", selectedLevel)
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Lỗi khi mở màn hình quiz", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}