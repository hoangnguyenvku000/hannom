package com.example.hannom

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.example.hannom.databinding.ActivityLearnColorBinding
import java.util.*
import androidx.core.graphics.drawable.toDrawable

class LearnColor : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityLearnColorBinding
    private lateinit var popup: Dialog
    private lateinit var textToSpeech: TextToSpeech
    private var currentPlayer: MediaPlayer? = null

    // Map màu sắc (tiếng Việt)
    private val colorSoundMap = mapOf(
        "Xanh lam" to "Xanh lam",
        "Đỏ" to "Đỏ",
        "Xanh lục" to "Xanh lục",
        "Vàng" to "Vàng"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnColorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Khởi tạo TextToSpeech
        textToSpeech = TextToSpeech(this, this)

        initViews()
        initPopup()
    }

    private fun initViews() {
        binding.buttonback.setOnClickListener { onBackPressed() }

        val viewColorPairs: List<Pair<View, String>> = listOf(
            binding.cardView4 as View to "Xanh lục",
            binding.cardViewdo as View to "Đỏ",
            binding.blue as View to "Xanh lam",
            binding.yellow as View to "Vàng",
            binding.kuaci as View to "Vàng",
            binding.imageView3 as View to "Đỏ",
            binding.imageView5 as View to "Xanh lam",
            binding.soundblue as View to "Xanh lam",
            binding.soundgreen as View to "Xanh lục",
            binding.soundred as View to "Đỏ",
            binding.soundyellow as View to "Vàng"
        )

        // Duyệt qua danh sách
        viewColorPairs.forEach { (view, colorName) ->
            view.setOnClickListener {
                speakColorName(colorName)
            }
        }

        binding.buttonHelp.setOnClickListener { showHelpPopup() }
    }

    @SuppressLint("SetTextI18n")
    private fun initPopup() {
        popup = Dialog(this).apply {
            window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            setContentView(R.layout.helpdialog) // CHỈ set content, KHÔNG show

            findViewById<ImageButton>(R.id.button_xhelp)?.setOnClickListener {
                dismiss()
            }

            findViewById<TextView>(R.id.help)?.text =
                "Nhấn vào màu để nghe phát âm tiếng Việt"
        }
    }

    private fun showHelpPopup() {
        if (!popup.isShowing) {
            popup.show()
        }
    }

    // Xử lý TextToSpeech
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale("vi"))

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Ngôn ngữ Tiếng Việt không được hỗ trợ")
            }
        } else {
            Log.e("TTS", "Khởi tạo TextToSpeech thất bại")
        }
    }

    private fun speakColorName(colorName: String) {
        // Dừng phát âm thanh cũ nếu có
        currentPlayer?.release()

        // Phát âm bằng TTS
        textToSpeech.speak(colorSoundMap[colorName], TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onDestroy() {
        // Giải phóng tài nguyên
        textToSpeech.stop()
        textToSpeech.shutdown()
        currentPlayer?.release()
        popup.dismiss()
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}