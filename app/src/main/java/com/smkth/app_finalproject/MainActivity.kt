package com.smkth.app_finalproject

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Sesuai dengan nama layout XML

        // Inisialisasi komponen UI
        val weightEditText = findViewById<TextInputEditText>(R.id.weightEditText)
        val heightEditText = findViewById<TextInputEditText>(R.id.heightEditText)
        val calculateButton = findViewById<Button>(R.id.calculateButton)

        calculateButton.setOnClickListener {
            val weightStr = weightEditText.text.toString()
            val heightStr = heightEditText.text.toString()

            if (weightStr.isNotEmpty() && heightStr.isNotEmpty()) {
                try {
                    val weight = weightStr.toFloat()
                    val height = heightStr.toFloat() / 100 // Konversi cm ke meter
                    val bmi = weight / (height * height)
                    val bmiFormatted = "%.1f".format(bmi)

                    showResultDialog(bmiFormatted)
                } catch (e: NumberFormatException) {
                    showErrorDialog("Format angka tidak valid")
                }
            } else {
                showErrorDialog("Harap isi berat dan tinggi badan")
            }
        }
    }

    private fun showResultDialog(bmi: String) {
        val (category, color) = when (bmi.toFloat()) {
            in 0.0..18.4 -> Pair("Kurus", Color.BLUE)
            in 18.5..24.9 -> Pair("Normal", Color.GREEN)
            in 25.0..29.9 -> Pair("Gemuk", Color.YELLOW)
            else -> Pair("Obesitas", Color.RED)
        }

        AlertDialog.Builder(this)
            .setTitle("Hasil BMI")
            .setMessage("Nilai BMI: $bmi\nKategori: $category")
            .setPositiveButton("Detail") { _, _ ->
                val intent = Intent(this, ResultActivity::class.java).apply {
                    putExtra("BMI", bmi)
                    putExtra("CATEGORY", category)
                    putExtra("COLOR", color)
                }
                startActivity(intent)
            }
            .setNegativeButton("Tutup", null)
            .create()
            .show()
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Kesalahan Input")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}