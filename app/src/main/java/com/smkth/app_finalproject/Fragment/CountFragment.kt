package com.smkth.app_finalproject.Fragment

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.smkth.app_finalproject.R
import com.smkth.app_finalproject.ResultActivity

class CountFragment : Fragment() {

    private lateinit var rotate: ObjectAnimator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_count, container, false)

        val weightEditText = view.findViewById<TextInputEditText>(R.id.weightEditText)
        val heightEditText = view.findViewById<TextInputEditText>(R.id.heightEditText)
        val calculateButton = view.findViewById<Button>(R.id.calculateButton)
        val btnImg = view.findViewById<ImageButton>(R.id.btnimg)


        // Setup animasi rotasi
        rotate = ObjectAnimator.ofFloat(btnImg, "rotation", 0f, -360f).apply {
            duration = 1500
            repeatCount = ObjectAnimator.INFINITE
            interpolator = LinearInterpolator()
            interpolator = android.view.animation.AccelerateDecelerateInterpolator()
        }

        btnImg.setOnClickListener {
            if (!rotate.isRunning) {
                rotate.start()
                btnImg.postDelayed({
                    rotate.cancel()
                    btnImg.rotation = 0f // Reset rotasi ke awal
                    // Reset form (opsional)
                    weightEditText.text?.clear()
                    heightEditText.text?.clear()
                }, 1500) // Durasi loading palsu
            }
        }

        calculateButton.setOnClickListener {
            val weightStr = weightEditText.text?.toString()?.trim()?.replace(',', '.')
            val heightStr = heightEditText.text?.toString()?.trim()?.replace(',', '.')

            if (!weightStr.isNullOrEmpty() && !heightStr.isNullOrEmpty()) {
                val weight = weightStr.toFloatOrNull()
                val height = heightStr.toFloatOrNull()?.div(100)

                if (weight != null && height != null && height > 0) {
                    val bmi = weight / (height * height)
                    val bmiFormatted = "%.1f".format(bmi)
                    showResultDialog(bmiFormatted)
                } else {
                    showErrorDialog("Format angka tidak valid. Gunakan titik/koma untuk desimal.")
                }
            } else {
                showErrorDialog("Harap isi berat dan tinggi badan.")
            }
        }

        return view
    }

    private fun showResultDialog(bmi: String) {
        val bmiValue = bmi.replace(',', '.').toFloatOrNull()

        if (bmiValue == null) {
            showErrorDialog("Terjadi kesalahan saat membaca nilai BMI.")
            return
        }

        val (category, color) = when (bmiValue) {
            in 0.0..18.4 -> Pair("Kurus", Color.BLUE)
            in 18.5..24.9 -> Pair("Normal", Color.GREEN)
            in 25.0..29.9 -> Pair("Gemuk", Color.YELLOW)
            else -> Pair("Obesitas", Color.RED)
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Hasil BMI")
            .setMessage("Nilai BMI: $bmi\nKategori: $category")
            .setPositiveButton("Detail") { _, _ ->
                val intent = Intent(requireContext(), ResultActivity::class.java).apply {
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
        AlertDialog.Builder(requireContext())
            .setTitle("Kesalahan Input")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
