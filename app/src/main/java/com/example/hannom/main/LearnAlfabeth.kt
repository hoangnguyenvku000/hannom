package com.example.hannom.main

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hannom.R
import com.example.hannom.databinding.ActivityLearnAlfabethBinding
import kotlin.collections.iterator

class LearnAlfabeth : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var binding: ActivityLearnAlfabethBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnAlfabethBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonback.setOnClickListener {
            onBackPressed()
        }

        // Data alphabet
        val alphabetData = mapOf(
            binding.buttonA to Triple("Aa", "Apple", "Táo"),
            binding.buttonB to Triple("Bb", "Ball", "Bóng"),
            binding.buttonC to Triple("Cc", "Cat", "Kucing"),
            binding.buttonD to Triple("Dd", "Doctor", "Doktor"),
            binding.buttonE to Triple("Ee", "Elephant", "Gajah"),
            binding.buttonF to Triple("Ff", "Fire truck", "Mobil pemadam kebakaran"),
            binding.buttonG to Triple("Gg", "Guitar", "Gitar"),
            binding.buttonH to Triple("Hh", "Horse", "Kuda"),
            binding.buttonI to Triple("Ii", "Ice cream", "Es krim"),
            binding.buttonJ to Triple("Jj", "Jar", "Toples"),
            binding.buttonK to Triple("Kk", "Kite", "Layangan"),
            binding.buttonL to Triple("Ll", "Ladder", "Tangga"),
            binding.buttonM to Triple("Mm", "Mango", "Mangga"),
            binding.buttonN to Triple("Nn", "Nest", "Sarang"),
            binding.buttonO to Triple("Oo", "Owl", "Burung hantu"),
            binding.buttonP to Triple("Pp", "Panda", "Panda"),
            binding.buttonQ to Triple("Qq", "Queen", "Ratu"),
            binding.buttonR to Triple("Rr", "Rabbit", "Kelinci"),
            binding.buttonS to Triple("Ss", "Socks", "Kaus kaki"),
            binding.buttonT to Triple("Tt", "Turtle", "Kura - kura"),
            binding.buttonU to Triple("Uu", "Umbrella", "Payung"),
            binding.buttonV to Triple("Vv", "Volleyball", "Bola voli"),
            binding.buttonW to Triple("Ww", "Wolf", "Serigala"),
            binding.buttonX to Triple("Xx", "Xylophone", "Gambang"),
            binding.buttonY to Triple("Yy", "Yoyo", "Yoyo"),
            binding.buttonZ to Triple("Zz", "Zebra", "Zebra")
        )

// Apply listeners in loop
        for ((button, data) in alphabetData) {
            button.setOnClickListener {
                binding.huruf.text = data.first
                binding.bahasaInggris.text = data.second
                binding.bahasaIndo.text = data.third
                soundon()
            }
        }
    }
    private fun soundon() {
        val key = binding.huruf.text.toString().lowercase().trim()
        val resId = resources.getIdentifier(key, "raw", packageName)

        if (resId != 0) {
            val player = MediaPlayer.create(this, resId)
            player.start()
            player.setOnCompletionListener {
                player.release()
            }
        } else {
            Toast.makeText(this, "Âm thanh cho \"$key\" không tồn tại", Toast.LENGTH_SHORT)
                .show()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}