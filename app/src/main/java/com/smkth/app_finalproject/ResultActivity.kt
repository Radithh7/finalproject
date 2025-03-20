package com.smkth.app_finalproject

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Inisialisasi komponen UI
        val resultTextView = findViewById<TextView>(R.id.result)
        val categoryTextView = findViewById<TextView>(R.id.category)
        val backButton = findViewById<Button>(R.id.btnback)

        // Terima data dari Intent
        val bmi = intent.getStringExtra("BMI") ?: "0.0"
        val category = intent.getStringExtra("CATEGORY") ?: "Tidak Diketahui"
        val color = intent.getIntExtra("COLOR", Color.BLACK)

        // Tampilkan data
        resultTextView.text = "Hasil BMI: $bmi"
        categoryTextView.text = "Kategori: $category"
        categoryTextView.setTextColor(color)

        // Handle tombol kembali
        backButton.setOnClickListener {
            finish()
        }
    }
}
