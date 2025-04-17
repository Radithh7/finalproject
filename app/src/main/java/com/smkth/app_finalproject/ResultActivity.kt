package com.smkth.app_finalproject

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.anastr.speedviewlib.TubeSpeedometer
import com.github.anastr.speedviewlib.components.Section

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val resultTextView = findViewById<TextView>(R.id.result)
        val categoryTextView = findViewById<TextView>(R.id.category)
        val kaloriTextView = findViewById<TextView>(R.id.kaloriText)
        val makananTextView = findViewById<TextView>(R.id.makananText)
        val olahragaTextView = findViewById<TextView>(R.id.olahragaText)
        val backButton = findViewById<Button>(R.id.btnback)
        val speedView = findViewById<TubeSpeedometer>(R.id.speedView)

        // Ambil data dari intent
        val bmi = intent.getFloatExtra("BMI", 0f)
        val category = intent.getStringExtra("CATEGORY") ?: "Tidak Diketahui"
        val color = intent.getIntExtra("COLOR", Color.BLACK)

        resultTextView.text = "Hasil BMI: %.1f".format(bmi)
        categoryTextView.text = "Kategori: $category"
        categoryTextView.setTextColor(color)

        speedView.apply {
            minSpeed = 10f
            maxSpeed = 40f
            withTremble = false
            unit = ""

            clearSections()
            addSections(
                Section(10f, 18.5f, Color.BLUE),
                Section(18.5f, 24.9f, Color.GREEN),
                Section(24.9f, 29.9f, Color.YELLOW),
                Section(29.9f, 40f, Color.RED)
            )

            indicator.color = Color.TRANSPARENT
            invalidate()
            speedTo(bmi)
        }

        // Estimasi kebutuhan kalori harian (rata-rata kasar: 25 x berat ideal)
        val heightInMeter = 1.7f // kamu bisa modifikasi jika punya data tinggi user
        val idealWeight = 22 * heightInMeter * heightInMeter // BMI ideal = 22
        val estimatedCalories = (idealWeight * 25).toInt()

        kaloriTextView.text = "Estimasi kalori harian: $estimatedCalories kkal"

        when (category) {
            "Kurus" -> {
                makananTextView.text = "Rekomendasi Makanan: Nasi, roti gandum, alpukat, telur, susu, daging merah."
                olahragaTextView.text = "Rekomendasi Olahraga: Latihan beban ringan, yoga, jalan kaki."
            }
            "Normal" -> {
                makananTextView.text = "Rekomendasi Makanan: Sayur, buah, karbohidrat kompleks, ikan, ayam tanpa kulit."
                olahragaTextView.text = "Rekomendasi Olahraga: Jogging, bersepeda, bodyweight training."
            }
            "Gemuk" -> {
                makananTextView.text = "Rekomendasi Makanan: Oatmeal, sayuran hijau, buah-buahan, dada ayam rebus."
                olahragaTextView.text = "Rekomendasi Olahraga: Kardio intensitas sedang, HIIT ringan, berenang."
            }
            "Obesitas" -> {
                makananTextView.text = "Rekomendasi Makanan: Makanan rendah kalori dan lemak, tinggi serat. Hindari gorengan dan gula."
                olahragaTextView.text = "Rekomendasi Olahraga: Jalan cepat, sepeda statis, aerobik ringan (konsultasi dokter)."
            }
            else -> {
                makananTextView.text = "Rekomendasi Makanan: -"
                olahragaTextView.text = "Rekomendasi Olahraga: -"
            }
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}
