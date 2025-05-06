package com.example.hannom

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hannom.databinding.ActivityResultBinding

class Result : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nilai = intent.getIntExtra("nilai", 0)
        val level = intent.getStringExtra("level")

        if (nilai <= 60){
            binding.predikat.text = "Skor quiz kamu masih terlalu kecil :("
        }
        else {
            binding.predikat.text = "Selamat! Skor quiz kamu..."
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

        val hasil = nilai.toString()

        binding.grade.text = "$hasil/100"
        binding.bagikan.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Wow nilai ku diquiz hannom $hasil, ayo download aplikasinya sekarang di Play Store!")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, "Bagikan ke...")
            startActivity(shareIntent)
        }

        binding.back.setOnClickListener {
            finish()
        }

    }
}
