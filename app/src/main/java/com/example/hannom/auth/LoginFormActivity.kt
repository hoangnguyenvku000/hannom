package com.example.hannom.auth
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.hannom.databinding.ActivityLoginFormBinding
import com.example.hannom.main.HomeActivity
import com.example.hannom.main.MainActivity

class LoginFormActivity : AppCompatActivity() {
    private lateinit var bind : ActivityLoginFormBinding
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityLoginFormBinding.inflate(layoutInflater)
        setContentView(bind.root)
        var dbhelp=DBClass(applicationContext)
        var db=dbhelp.readableDatabase
        bind.btnlogin.setOnClickListener {
            var username=bind.logtxt.text.toString()
            var password=bind.ed3.text.toString()
            val query= "SELECT * FROM user WHERE username='$username' AND pswd='$password'"
            val rs=db.rawQuery(query,null)
            if(rs.moveToFirst()){
                val name=rs.getString(rs.getColumnIndex("name"))
                rs.close()
                startActivity(Intent(this, MainActivity::class.java).putExtra("name",name))
            }
            else{
                var ad = AlertDialog.Builder(this)
                ad.setTitle("Message")
                ad.setMessage("Username or password is incorrect!")
                ad.setPositiveButton("Ok", null)
                ad.show()
            }
        }
        bind.regisLink.setOnClickListener {
            val intent= Intent(this,ResgisterActivity::class.java)
            startActivity(intent)
        }
    }
}