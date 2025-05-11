package com.example.hannom.auth

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.hannom.databinding.ActivityRegisterBinding

class ResgisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var dbhelp=DBClass(applicationContext)
        var db=dbhelp.writableDatabase
        binding.btnrgs.setOnClickListener {
            var name=binding.ed1.text.toString()
            var username=binding.ed2.text.toString()
            var password=binding.ed3.text.toString()
            if(name.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
                var data = ContentValues()
                data.put("name", binding.ed1.text.toString())
                data.put("username", binding.ed2.text.toString())
                data.put("pswd", binding.ed3.text.toString())
                var rs:Long = db.insert("user", null, data)
                if(rs != -1L) {
                    var ad = AlertDialog.Builder(this)
                    ad.setTitle("Message")
                    ad.setMessage("Account registered successfully")
                    ad.setPositiveButton("Ok", null)
                    ad.show()
                    binding.ed1.text.clear()
                    binding.ed2.text.clear()
                    binding.ed3.text.clear()
                }else{
                    var ad = AlertDialog.Builder(this)
                    ad.setTitle("Message")
                    ad.setMessage("Record not added")
                    ad.setPositiveButton("Ok", null)
                    ad.show()
                    binding.ed1.text.clear()
                    binding.ed2.text.clear()
                    binding.ed3.text.clear()
                }
            }else{
                Toast.makeText(this,"All fields required",Toast.LENGTH_SHORT).show()
            }
        }
        binding.loginLink.setOnClickListener {
            val intent=Intent(this,LoginFormActivity::class.java)
            startActivity(intent)
        }
    }
}