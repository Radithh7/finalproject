package com.smkth.app_finalproject.Fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputEditText
import com.smkth.app_finalproject.R
import com.smkth.app_finalproject.ResultActivity
import com.smkth.app_finalproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var FragmentManager: FragmentManager
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        gotoFragment(CountFragment())

        binding.homeButton.setOnClickListener {
            gotoFragment(CountFragment())
        }
        binding.bmiButton.setOnClickListener {
            gotoFragment(HistoryFragment())
        }

    }
    private fun gotoFragment(fragment: Fragment) {
        FragmentManager = supportFragmentManager
        FragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }
}
