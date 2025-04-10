package com.smkth.app_finalproject.Fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.smkth.app_finalproject.R
import com.smkth.app_finalproject.ResultActivity

class CountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_count, container, false)

        val weightEditText = view.findViewById<TextInputEditText>(R.id.weightEditText)
        val heightEditText = view.findViewById<TextInputEditText>(R.id.heightEditText)
        val calculateButton = view.findViewById<Button>(R.id.calculateButton)

        calculateButton.setOnClickListener {
            val weightStr = weightEditText.text?.toString()?.trim()?.replace(',', '.')
            val heightStr = heightEditText.text?.toString()?.trim()?.replace(',', '.')

            if (!weightStr.isNullOrEmpty() && !heightStr.isNullOrEmpty()) {
                try {
                    val weight = weightStr.toFloat()
                    val height = heightStr.toFloat() / 100
                    val bmi = weight / (height * height)
                    val bmiFormatted = "%.1f".format(bmi)
                    showResultDialog(bmiFormatted)
                } catch (e: NumberFormatException) {
                    showErrorDialog("Format angka tidak valid. Gunakan titik untuk desimal (contoh: 65.5)")
                }
            } else {
                showErrorDialog("Harap isi berat dan tinggi badan")
            }
        }

        return view
    }

    private fun showResultDialog(bmi: String) {
        val (category, color) = when (bmi.toFloat()) {
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
