package com.example.sharmainteriordesign.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.sharmainteriordesign.R
import com.example.sharmainteriordesign.repository.UserRepository
import com.example.sharmainteriordesign.roomdatabase.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {
    private lateinit var name:EditText
    private lateinit var address:EditText
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var con_password:EditText
    private lateinit var signup:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        name=findViewById(R.id.name)
        address=findViewById(R.id.address)
        email=findViewById(R.id.email)
        password=findViewById(R.id.password)
        con_password=findViewById(R.id.con_password)
        signup=findViewById(R.id.signup)
        signup.setOnClickListener(){
           addUSerInRoomDatabse()
        }
    }


    fun addUSerInRoomDatabse(){
        val user=User(name =  name.text.toString(),address = address.text.toString(),email =  email.text.toString(),password =  password.text.toString())
        CoroutineScope(Dispatchers.IO).launch {
            val repository=UserRepository()
            val response=repository.registerUSer(user)
            if(response.success==true){
                withContext(Main){
                    Toast.makeText(this@SignUpActivity, "Register Succefully", Toast.LENGTH_SHORT).show()

                }
            }
            else{
                withContext(Main){
                    Toast.makeText(this@SignUpActivity, "${response.msg}", Toast.LENGTH_SHORT).show()

                }
            }
           // Db.getInstance(this@SignUpActivity).getUserDao().AddUSer(user)
//            Db.getInstance(this@SignUpActivity).getUserDao().AddUSer(user)
            withContext(Main){
                Toast.makeText(this@SignUpActivity, "Register Succefully", Toast.LENGTH_SHORT).show()

            }
        }
    }
}