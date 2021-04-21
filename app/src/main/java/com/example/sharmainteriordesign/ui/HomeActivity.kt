package com.example.sharmainteriordesign.ui

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.sharmainteriordesign.R
import com.example.sharmainteriordesign.ui.Fragments.AccountFragment
import com.example.sharmainteriordesign.ui.Fragments.FavorateFragment
import com.example.sharmainteriordesign.ui.Fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity(),SensorEventListener {
    private lateinit var fragmentsHolder: FrameLayout;
    private val Home = HomeFragment()
    private val fav = FavorateFragment()
    private val account = AccountFragment()
    private lateinit var bottom_navigation: BottomNavigationView;
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        fragmentsHolder = findViewById(R.id.fragmentsHolder)
        bottom_navigation = findViewById(R.id.bottomnav)
        replacefragment(Home)
        bottom_navigation.setOnNavigationItemSelectedListener() {
            when (it.itemId) {
                R.id.ic_home -> {
                    replacefragment(Home)
                    true
                }
                R.id.ic_fav -> {
                    replacefragment(fav)
                    true
                }

                R.id.ic_account -> {
                    replacefragment(account)
                    true
                }

                else -> {
                    false
                }
            }
        }
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if (!checkSensor())
            return
        else {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    private fun checkSensor(): Boolean {
        var flag = true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) == null) {
            flag = false
        }
        return flag
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val values = event!!.values[1]
//        if (values < 0)
//            replacefragment(fav)
//        else if (values > 0) {
//            replacefragment(Home)
//        }
    }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }

        private fun replacefragment(fragment: Fragment) {
            if (fragment != null) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentsHolder, fragment)
                transaction.commit()
            }
        }
}
