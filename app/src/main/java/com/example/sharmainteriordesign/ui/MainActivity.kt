package com.example.sharmainteriordesign.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import com.example.sharmainteriordesign.R
import com.example.sharmainteriordesign.api.ServiceBuilder
import com.example.sharmainteriordesign.repository.UserRepository
import com.example.sharmainteriordesign.roomdatabase.entity.User
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class MainActivity : AppCompatActivity() {
    private lateinit var firsttext:TextView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firsttext=findViewById(R.id.firsttext)
        val sharedPref = getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
        val emailPref = sharedPref.getString("email", null)
        val passwordPref = sharedPref.getString("password", "")
        val animation=AnimationUtils.loadAnimation(this, R.anim.fade_in)
        firsttext.startAnimation(animation)
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            if (emailPref != null) {
                withContext(Main){
                    Toast.makeText(this@MainActivity, "$emailPref + ", Toast.LENGTH_SHORT).show()
                    val repository = UserRepository()
                    val user = User(email = emailPref, password = passwordPref)
                    val response = repository.checkUSer(user)
                    if (response.success == true) {
                        ServiceBuilder.token="Bearer ${response.token}"
                        ServiceBuilder.id=response.id
                        delay(500)
                        startActivity(Intent(this@MainActivity, HomeActivity::class.java)
                        )
                        finish()
                    }
                }
            } else {
                withContext(Main){
                    startActivity(
                            Intent(
                                    this@MainActivity,
                                    LoginActivity::class.java
                            )
                    )
                }
                finish()
            }
        }


    }
    fun startActivity(){
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
    }
}