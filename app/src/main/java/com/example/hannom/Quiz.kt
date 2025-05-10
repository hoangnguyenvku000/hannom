package com.example.hannom

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.hannom.databinding.ActivityQuizBinding
import androidx.core.graphics.drawable.toDrawable

class Quiz : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private lateinit var exitDialog: Dialog

    private var currentQuestionIndex = 0
    private var correctAnswers = 0
    private var selectedAnswer = ""
    private var questionNumber = 1

    private lateinit var level: String
    private val questions = arrayOf(
        "1. Màu đỏ viết thành chữ Nôm là gì?",
        "2. Con mèo viết thành chữ Nôm là gì?",
        "3. Cách viết đúng của 'cà rốt' bằng chữ Nôm?",
        "4. Trộn màu vàng và đỏ sẽ được màu gì?",
        "5. Cách viết nào sau đây là đúng?",
        "6. Màu xanh lụcđược tạo từ màu nào?",
        "7. Loại quả nào khi viết bằng chữ Quốc ngữ bắt đầu bằng chữ 'T'?",
        "8. Hoa hướng dương có màu gì?",
        "9. Rau củ thường có màu gì?",
        "10. Màu tím viết thành chữ Nôm là gì?",
        "11. 'Bắp cải' viết thành chữ Nôm là gì?",
        "12. Ngựa vằn có những màu nào?",
        "13. 'Xe con' viết thành chữ Nôm là gì?",
        "14. Màu cờ của Việt Nam là gì?",
        "15. 'Cá heo' viết thành chữ Nôm là gì?"
    )

    private val answers = arrayOf(
        "𧹻",
        "貓",
        "茄𡳝",
        "柑",
        "彈箏",
        "青藍吧鐄",
        "棗",
        "鐄",
        "青綠",
        "𦻳",
        "𣔟芥",
        "𤽸吧黰",
        "車𡥵",
        "𧹻吧鐄",
        "𩵜㺧"
    )

    private val options = arrayOf(
        "𧹻", "黰", "紅", "柑",
        "恐龍", "大鵬", "㹥", "貓",
        "茄𡳝", "茄𠸜", "茄𦻳", "茄䣷",
        "青綠", "黰", "𣘽", "柑",
        "彈箏", "僤箏", "僤爭", "彈爭",
        "𤽸吧青藍", "青藍吧𧹻", "𧹻吧鐄", "鐄吧𧹻",
        "𣒱", "茄𡳝", "荎", "棗",
        "鐄", "黰", "𧹻", "𤽸",
        "𧹻", "鐄", "青綠", "𤽸",
        "青藍", "鐄", "𧹻", "𦻳",
        "𣔟芥", "茄𡳝", "豆槻", "葻芥青",
        "𤽸吧黰", "黰吧𧹻", "黰吧繿", "𤽸吧青藍",
        "車𡥵", "車𣛠", "飛機", "船",
        "青藍吧𧹻", "𧹻吧黰", "𤽸吧青藍", "𧹻吧鐄",
        "貓", "𩵜㺧", "㹯高𦙶", "㺔"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        setupViews()
        loadQuestion()
    }

    private fun initData() {
        level = intent.getStringExtra("Level") ?: "1"



        binding.nomorlevel.text = when (level) {
            "1" -> "/5"
            "2" -> "/10"
            else -> "/15"
        }
    }

    private fun setupViews() {
        binding.buttonback.setOnClickListener { showExitDialog() }

        binding.buttonjawabA.setOnClickListener { selectAnswer(0) }
        binding.buttonjawabB.setOnClickListener { selectAnswer(1) }
        binding.buttonjawabC.setOnClickListener { selectAnswer(2) }
        binding.buttonjawabD.setOnClickListener { selectAnswer(3) }

        binding.next.setOnClickListener { handleNextButton() }
    }

    private fun selectAnswer(optionIndex: Int) {
        resetAnswerButtons()
        selectedAnswer = when (optionIndex) {
            0 -> binding.optionA.text.toString()
            1 -> binding.optionB.text.toString()
            2 -> binding.optionC.text.toString()
            else -> binding.optionD.text.toString()
        }

        val selectedButton = when (optionIndex) {
            0 -> binding.buttona
            1 -> binding.buttonb
            2 -> binding.buttonc
            else -> binding.buttond
        }

        selectedButton.setBackgroundResource(R.drawable.circlegreen)
        selectedButton.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
    }

    private fun resetAnswerButtons() {
        listOf(binding.buttona, binding.buttonb, binding.buttonc, binding.buttond).forEach {
            it.setBackgroundResource(R.drawable.circlepurple)
            it.setTextColor(ContextCompat.getColor(this, R.color.white))
        }
    }

    private fun loadQuestion() {
        if (currentQuestionIndex >= questions.size) {
            showResult()
            return
        }

        binding.soal.text = questions[currentQuestionIndex]
        binding.optionA.text = options[currentQuestionIndex * 4]
        binding.optionB.text = options[currentQuestionIndex * 4 + 1]
        binding.optionC.text = options[currentQuestionIndex * 4 + 2]
        binding.optionD.text = options[currentQuestionIndex * 4 + 3]
        binding.textView7.text = questionNumber.toString()
    }

    private fun handleNextButton() {
        if (selectedAnswer.isEmpty()) {
            Toast.makeText(this, "Pilih jawaban terlebih dahulu!", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedAnswer == answers[currentQuestionIndex]) {
            correctAnswers++
        }

        currentQuestionIndex++
        questionNumber++

        when (level) {
            "1" -> if (currentQuestionIndex == 5) showResult() else loadQuestion()
            "2" -> if (currentQuestionIndex == 10) showResult() else loadQuestion()
            "3" -> if (currentQuestionIndex == 15) showResult() else loadQuestion()
        }
    }

    private fun showResult() {
        val marks = when (level) {
            "1" -> correctAnswers * 20
            "2" -> correctAnswers * 10
            else -> (correctAnswers * 6) + 10
        }

        Intent(this, Result::class.java).apply {
            putExtra("nilai", marks)
            putExtra("level", level)
            startActivity(this)
        }
        finish()
    }

    private fun showExitDialog() {
        exitDialog = Dialog(this).apply {
            setContentView(R.layout.alertdialog)
            window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

            findViewById<Button>(R.id.button_yes).setOnClickListener {
                dismiss()
                finish()
            }
            findViewById<Button>(R.id.button_no).setOnClickListener { dismiss() }
            findViewById<ImageButton>(R.id.button_x).setOnClickListener { dismiss() }
        }
        exitDialog.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        showExitDialog()
    }
}