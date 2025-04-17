package com.smkth.app_finalproject.Fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.smkth.app_finalproject.R

class HistoryFragment : Fragment() {

    private lateinit var historyListView: ListView
    private lateinit var deleteAllButton: Button
    private val historyKey = "BMI_HISTORY"
    private lateinit var adapter: HistoryAdapter
    private var historyList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        historyListView = view.findViewById(R.id.historyListView)
        deleteAllButton = view.findViewById(R.id.deleteAllButton)

        loadHistory()

        adapter = HistoryAdapter(requireContext(), historyList)
        historyListView.adapter = adapter

        deleteAllButton.setOnClickListener {
            historyList.clear()
            adapter.notifyDataSetChanged()
            val sharedPref = requireContext().getSharedPreferences("BMI_PREFS", Context.MODE_PRIVATE)
            sharedPref.edit().remove(historyKey).apply()
            Toast.makeText(context, "Semua riwayat dihapus", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun loadHistory() {
        val sharedPref = requireContext().getSharedPreferences("BMI_PREFS", Context.MODE_PRIVATE)
        val history = sharedPref.getStringSet(historyKey, emptySet()) ?: emptySet()
        historyList = history.sortedDescending().toMutableList()
    }

    fun saveToHistory(newEntry: String) {
        val sharedPref = requireContext().getSharedPreferences("BMI_PREFS", Context.MODE_PRIVATE)
        val historySet = sharedPref.getStringSet(historyKey, mutableSetOf())?.toMutableSet() ?: mutableSetOf()

        // Hindari duplikasi
        if (!historySet.contains(newEntry)) {
            historySet.add(newEntry)
            sharedPref.edit().putStringSet(historyKey, historySet).apply()
        }
    }

    inner class HistoryAdapter(context: Context, private val items: MutableList<String>) :
        ArrayAdapter<String>(context, R.layout.listhistori, items) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.listhistori, parent, false)

            val text = view.findViewById<TextView>(R.id.historyText)
            val deleteBtn = view.findViewById<Button>(R.id.deleteButton)

            text.text = items[position]
            text.setTextColor(resources.getColor(android.R.color.black, null))

            deleteBtn.setOnClickListener {
                items.removeAt(position)
                notifyDataSetChanged()
                updateSharedPref()
                Toast.makeText(context, "Riwayat dihapus", Toast.LENGTH_SHORT).show()
            }

            return view
        }

        private fun updateSharedPref() {
            val sharedPref = context.getSharedPreferences("BMI_PREFS", Context.MODE_PRIVATE)
            sharedPref.edit().putStringSet(historyKey, items.toSet()).apply()
        }
    }
}
