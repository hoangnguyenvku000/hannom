package com.example.hannom

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        Handler().postDelayed({
            val player = MediaPlayer.create(this, R.raw.splash2)
            player.start()
            val pindah = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(pindah)
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            finish()
        }, 1000)
    }
}
