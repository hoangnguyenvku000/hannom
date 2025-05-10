package com.example.hannom

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.hannom.databinding.ActivityGamesColorBinding
import java.util.*
import androidx.core.graphics.drawable.toDrawable

class GamesColor : AppCompatActivity() {

    private lateinit var binding: ActivityGamesColorBinding
    private lateinit var popup: Dialog
    private lateinit var textToSpeech: TextToSpeech
    private var mediaPlayer: MediaPlayer? = null

    // Base colors (code to color value, Vietnamese name, Hán-Nôm)
    private val baseColors = mapOf(
        "1" to Triple(Color.YELLOW, "vàng", "鐄"),
        "2" to Triple(Color.BLUE, "xanh lam", "青藍"),
        "3" to Triple(Color.RED, "đỏ", "𧹻"),
        "4" to Triple(Color.BLACK, "đen", "黰"),
        "5" to Triple(Color.WHITE, "trắng", "𤽸")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamesColorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPopup()
        initTextToSpeech()
        setupClickListeners()
    }

    private fun initPopup() {
        popup = Dialog(this).apply {
            setContentView(R.layout.helpdialog)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            findViewById<ImageButton>(R.id.button_xhelp)?.setOnClickListener { dismiss() }
            findViewById<TextView>(R.id.help)?.text = "Chọn 1-3 màu rồi nhấn 'Pha màu' để xem kết quả!"
        }
    }

    private fun initTextToSpeech() {
        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale("vi", "VN")
            }
        }
    }

    private fun setupClickListeners() {
        // Navigation
        binding.buttonback.setOnClickListener { finish() }
        binding.buttonHelp.setOnClickListener { popup.show() }

        // Color selection
        binding.yellow.setOnClickListener { selectColor("1") }
        binding.blue.setOnClickListener { selectColor("2") }
        binding.red.setOnClickListener { selectColor("3") }
        binding.black.setOnClickListener { selectColor("4") }
        binding.white.setOnClickListener { selectColor("5") }

        // Slot handling
        binding.slotOne.setOnClickListener { clearSlot(binding.slotOne) }
        binding.slotTwo.setOnClickListener { clearSlot(binding.slotTwo) }
        binding.slotThree.setOnClickListener { clearSlot(binding.slotThree) }

        // Actions
        binding.mix.setOnClickListener { mixSelectedColors() }
        binding.soundon.setOnClickListener { speakColorName(binding.colorNameQN.text.toString()) }
    }

    private fun selectColor(colorCode: String) {
        val slots = listOf(binding.slotOne, binding.slotTwo, binding.slotThree)

        // Tìm slot trống đầu tiên
        val emptySlot = slots.firstOrNull { it.text.isEmpty() }

        if (emptySlot != null) {
            // Cho phép chọn màu trùng nếu chưa đủ 3 màu
            updateSlot(emptySlot, colorCode)
        } else {
            showToast("Đã chọn đủ 3 màu!")
        }
    }

    private fun updateSlot(slot: TextView, colorCode: String) {
        baseColors[colorCode]?.let { (color, nameVN, nameNom) ->
            slot.text = colorCode
            val drawable = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setColor(color)
                setStroke(2, Color.DKGRAY)
            }
            slot.background = drawable
            updateColorButton(colorCode, true)
        }
    }

    private fun clearSlot(slot: TextView) {
        val colorCode = slot.text.toString()
        if (colorCode.isNotEmpty()) {
            slot.text = ""
            slot.setBackgroundResource(R.drawable.buttonmixcolor)
            updateColorButton(colorCode, false)
        }
    }

    private fun mixSelectedColors() {
        val selectedColors = listOf(binding.slotOne.text.toString(), binding.slotTwo.text.toString(), binding.slotThree.text.toString())
            .filter { it.isNotEmpty() }
            .sorted()

        if (selectedColors.isEmpty()) {
            showToast("Vui lòng chọn ít nhất 1 màu!")
            return
        }

        val (mixedColor, nameVN, nameNom) = mixColors(selectedColors)
        displayMixedColor(mixedColor, nameVN, nameNom)
        speakColorName(nameVN)
    }

    private fun mixColors(colorCodes: List<String>): Triple<Int, String, String> {
        val colors = colorCodes.mapNotNull { baseColors[it]?.first }
        if (colors.isEmpty()) return Triple(Color.TRANSPARENT, "không màu", "")

        // Xác định màu cơ bản khi pha trộn
        val baseMix = when {
            colorCodes.contains("1") && colorCodes.contains("3") -> "cam"
            colorCodes.contains("2") && colorCodes.contains("3") -> "tím"
            colorCodes.contains("1") && colorCodes.contains("2") -> "xanh lục"
            colorCodes.contains("3") -> "đỏ"
            colorCodes.contains("1") -> "vàng"
            colorCodes.contains("2") -> "xanh lam"
            else -> ""
        }

        // Xử lý độ đậm/nhạt
        return when {
            colorCodes.contains("4") -> getDarkVariant(baseMix)
            colorCodes.contains("5") -> getLightVariant(baseMix)
            else -> getNormalColor(baseMix)
        }
    }

    private fun getDarkVariant(base: String): Triple<Int, String, String> {
        return when (base) {
            "đỏ" -> Triple(Color.rgb(139, 0, 0), "đỏ đậm", "𧹻酖")
            "cam" -> Triple(Color.rgb(165, 42, 42), "nâu", "𣘽")
            "vàng" -> Triple(Color.rgb(204, 204, 0), "vàng đậm", "鐄酖")
            "xanh lục" -> Triple(Color.rgb(0, 100, 0), "xanh lục đậm", "青綠酖")
            "xanh lam" -> Triple(Color.rgb(0, 0, 139), "xanh lam đậm", "青藍酖")
            "tím" -> Triple(Color.rgb(85, 0, 85), "tím đậm", "𦻳酖")
            else -> Triple(Color.DKGRAY, "xám đậm", "繿酖")
        }
    }

    private fun getLightVariant(base: String): Triple<Int, String, String> {
        return when (base) {
            "đỏ" -> Triple(Color.rgb(255, 182, 193), "hồng", "紅")
            "cam" -> Triple(Color.rgb(255, 203, 164), "cam nhạt", "柑溂")
            "vàng" -> Triple(Color.rgb(255, 255, 153), "vàng nhạt", "鐄溂")
            "xanh lục" -> Triple(Color.rgb(144, 238, 144), "xanh lục nhạt", "青綠溂")
            "xanh lam" -> Triple(Color.rgb(173, 216, 230), "xanh lam nhạt", "青藍溂")
            "tím" -> Triple(Color.rgb(221, 160, 221), "tím nhạt", "𦻳溂")
            else -> Triple(Color.LTGRAY, "xám nhạt", "繿溂")
        }
    }

    private fun getNormalColor(base: String): Triple<Int, String, String> {
        return when (base) {
            "đỏ" -> Triple(Color.RED, "đỏ", "𧹻")
            "cam" -> Triple(Color.rgb(255, 165, 0), "cam", "柑")
            "vàng" -> Triple(Color.YELLOW, "vàng", "鐄")
            "xanh lục" -> Triple(Color.GREEN, "xanh lục", "青綠")
            "xanh lam" -> Triple(Color.BLUE, "xanh lam", "青藍")
            "tím" -> Triple(Color.rgb(128, 0, 128), "tím", "𦻳")
            else -> Triple(Color.GRAY, "xám", "繿")
        }
    }


    private fun displayMixedColor(color: Int, nameQN: String, nameNom: String) {
        binding.colorPreview.setImageDrawable(color.toDrawable())
        binding.colorNameQN.text = nameQN
        binding.colorNameNom.text = nameNom
    }

    private fun speakColorName(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun updateColorButton(colorCode: String, isSelected: Boolean) {
        val button = when (colorCode) {
            "1" -> binding.yellow
            "2" -> binding.blue
            "3" -> binding.red
            "4" -> binding.black
            "5" -> binding.white
            else -> null
        }
        button?.isSelected = isSelected
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        textToSpeech.shutdown()
        mediaPlayer?.release()
        popup.dismiss()
        super.onDestroy()
    }
}