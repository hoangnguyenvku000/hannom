package com.example.hannom

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hannom.databinding.ActivityLearnColorBinding
import android.widget.ImageButton
import android.widget.TextView

class LearnColor : AppCompatActivity() {

    private lateinit var popup: Dialog
    private lateinit var popupbutton: ImageButton
    private lateinit var popuptext: TextView
    private lateinit var binding: ActivityLearnColorBinding
    private var currentPlayer: MediaPlayer? = null
    private var color: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnColorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        popup = Dialog(this)

        binding.buttonback.setOnClickListener {
            onBackPressed()
        }

        // Sử dụng hàm lambda cho các nút
        val colorClickListener = { selectedColor: String ->
            color = selectedColor
            soundon()
        }

        // Gán sự kiện cho tất cả các phần tử tương ứng với màu
        binding.cardView4.setOnClickListener { colorClickListener("Green") }
        binding.cardviewMerah.setOnClickListener { colorClickListener("Red") }
        binding.blue.setOnClickListener { colorClickListener("Blue") }
        binding.yellow.setOnClickListener { colorClickListener("Yellow") }
        binding.kuaci.setOnClickListener { colorClickListener("Yellow") }
        binding.imageView3.setOnClickListener { colorClickListener("Red") }
        binding.imageView5.setOnClickListener { colorClickListener("Blue") }
        binding.soundblue.setOnClickListener { colorClickListener("Blue") }
        binding.soundgreen.setOnClickListener { colorClickListener("Green") }
        binding.soundred.setOnClickListener { colorClickListener("Red") }
        binding.soundyellow.setOnClickListener { colorClickListener("Yellow") }

        binding.buttonHelp.setOnClickListener {
            popup.setContentView(R.layout.helpdialog)
            popupbutton = popup.findViewById(R.id.button_xhelp)
            popuptext = popup.findViewById(R.id.help)
            popuptext.text =
                "Tekan warna yang ingin dipilih untuk mendengarkan cara pelafalan warna dalam bahasa Inggris beserta contoh gambar di sampingnya."
            popupbutton.setOnClickListener {
                popup.dismiss()
            }
            popup.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            popup.show()
        }
    }

    private fun soundon() {
        currentPlayer?.stop()
        currentPlayer?.release()

        val resource = when (color) {
            "Blue" -> R.raw.blue
            "Red" -> R.raw.red
            "Green" -> R.raw.green
            else -> R.raw.yellow
        }

        currentPlayer = MediaPlayer.create(this, resource)
        currentPlayer?.start()
        currentPlayer?.setOnCompletionListener {
            currentPlayer?.release()
            currentPlayer = null
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}
