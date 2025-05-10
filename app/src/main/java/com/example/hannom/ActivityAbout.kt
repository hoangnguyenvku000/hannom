package com.example.hannom

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.hannom.databinding.ActivityAboutBinding
import androidx.core.net.toUri
import androidx.core.graphics.drawable.toDrawable


class ActivityAbout : AppCompatActivity() {

    private lateinit var popUpDetail: Dialog
    private lateinit var devPhoto: ImageView
    private lateinit var tenDev: TextView
    private lateinit var profDev: TextView
    private lateinit var descDev: TextView
    private lateinit var exitButtonPopUp: ImageButton
    private lateinit var kerjaIcon: ImageButton
    private lateinit var buttonkerja1: TextView
    private lateinit var buttonkerja2: ImageButton
    private lateinit var buttonkerja3: ImageButton
    private lateinit var buttonkerja4: ImageButton
    private lateinit var buttonlienlac: Button

    private lateinit var popupdonate : Dialog
    private lateinit var textdonate : TextView
    private lateinit var xButton : ImageButton

    private lateinit var binding: ActivityAboutBinding

    var ten = ""
    var photo = ""
    var linh_vuc = ""
    var desc = ""
    var kerja = ""
    var link1 = ""
    var link2 = ""
    var link3 = ""
    var link4 = ""
    var lienlac = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        popUpDetail = Dialog(this)
        popupdonate = Dialog(this)

        binding.buttonBack.setOnClickListener {
            finish()
        }
        binding.profilesadam.setOnClickListener {
            ten = "范黃源\n (Phạm Hoàng Nguyên)"
            photo = "sadam"
            linh_vuc = "Nhà phát triển"
            desc =
                "Tôi là một lập trình viên sống tại thành phố Đà Nẵng, đam mê học hỏi và khám phá những điều mới để nâng cao kỹ năng của mình."
            kerja = "bh"
            link1 = ""
            link2 = ""
            link3 = ""
            link4 = ""
            lienlac = "nguyenph23ite@vku.udn.vn"
            dialog()
        }
        binding.profiledeka.setOnClickListener {
            ten = "潘清福\n (Phan Thanh Phúc)"
            photo = "deka"
            linh_vuc = "Lập trình viên"
            desc =
                ""
            kerja = "fb"
            link1 = ""
            link2 = ""
            link3 = ""
            link4 = ""
            lienlac = ""
            dialog()
        }

        binding.donate.setOnClickListener {
            popupdonate.setContentView(R.layout.helpdialog)
            textdonate = popupdonate.findViewById(R.id.help)
            xButton = popupdonate.findViewById(R.id.button_xhelp)
            xButton.setOnClickListener {
                popupdonate.dismiss()
            }
            textdonate.text = "Bạn có thể liên hệ với nhà phát triển để đóng góp (thông tin liên hệ có thể tìm thấy trong phần hồ sơ của nhà phát triển bên trên)."
            popupdonate.window!!.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            popupdonate.show()
        }
    }

    fun dialog() {
        popUpDetail.setContentView(R.layout.popupdetailabout)
        devPhoto = popUpDetail.findViewById(R.id.devphoto)
        tenDev = popUpDetail.findViewById(R.id.tendev)
        profDev = popUpDetail.findViewById(R.id.linh_vucdev)
        descDev = popUpDetail.findViewById(R.id.descdev)
        exitButtonPopUp = popUpDetail.findViewById(R.id.exit_button_popup)
        kerjaIcon = popUpDetail.findViewById(R.id.kerja2)
        buttonkerja1 = popUpDetail.findViewById(R.id.kerja1)
        buttonkerja2 = popUpDetail.findViewById(R.id.kerja2)
        buttonkerja3 = popUpDetail.findViewById(R.id.kerja3)
        buttonkerja4 = popUpDetail.findViewById(R.id.kerja4)
        buttonlienlac = popUpDetail.findViewById(R.id.lienlac)
//

        buttonkerja1.setOnClickListener {
            val kerja = Intent(Intent.ACTION_VIEW)
            kerja.data = link1.toUri()
            startActivity(kerja)
        }
        buttonkerja2.setOnClickListener {
            val kerja = Intent(Intent.ACTION_VIEW)
            kerja.data = link2.toUri()
            startActivity(kerja)
        }
        buttonkerja3.setOnClickListener {
            val kerja = Intent(Intent.ACTION_VIEW)
            kerja.data = link3.toUri()
            startActivity(kerja)
        }
        buttonkerja4.setOnClickListener {
            val kerja = Intent(Intent.ACTION_VIEW)
            kerja.data = link4.toUri()
            startActivity(kerja)
        }
        buttonlienlac.setOnClickListener {
            val i = Intent(Intent.ACTION_SENDTO)
            i.data = "mailto:$lienlac".toUri()
            try {
                startActivity(i)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
            }
        }

        when (photo) {
            "sadam" -> {
                devPhoto.setImageResource(R.drawable.potoaing)

            }
            else -> {
                devPhoto.setImageResource(R.drawable.adrian)
            }
        }

        when (kerja) {
            "bh" -> {
                kerjaIcon.setImageResource(R.mipmap.behance)
            }
            else -> {
                kerjaIcon.setImageResource(R.mipmap.whatsapp)
            }
        }

        tenDev.text = ten
        profDev.text = linh_vuc
        descDev.text = desc
        exitButtonPopUp.setOnClickListener {
            popUpDetail.dismiss()
        }
//
        popUpDetail.window!!.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        popUpDetail.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
    }
}
