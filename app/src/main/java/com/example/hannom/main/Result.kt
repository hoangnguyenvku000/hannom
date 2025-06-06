package com.example.hannom.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hannom.databinding.ActivityResultBinding

class Result : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val grade = intent.getIntExtra("grade", 0)
        val level = intent.getStringExtra("level")

        if (grade <= 60){
            binding.announcement.text = "Điểm của bạn quá thấp :("
        }
        else {
            binding.announcement.text = "Chúc mừng! Điểm số của bạn là ..."
        }

        when (level) {
            "1" -> {
                binding.textlevel.text = "Beginner Level"
            }
            "2" -> {
                binding.textlevel.text = "Advanced Level"
            }
            else -> {
                binding.textlevel.text = "Professional Level"
            }
        }

        val result = grade.toString()

        binding.grade.text = "$result/100"
        binding.share.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Điểm số của tôi trong bài trắc nghiệm $result")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, "Chia sẻ đến ...")
            startActivity(shareIntent)
        }

        binding.back.setOnClickListener {
            finish()
        }

    }
}
