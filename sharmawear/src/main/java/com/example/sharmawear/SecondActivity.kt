package com.example.sharmawear

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.sharmainteriordesign.api.ServiceBuilder

class SecondActivity : AppCompatActivity() {
    private lateinit var name:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        name=findViewById(R.id.name)
        name.setText(ServiceBuilder.name!!)
    }
}