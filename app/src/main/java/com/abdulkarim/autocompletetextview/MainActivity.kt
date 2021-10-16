package com.abdulkarim.autocompletetextview

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.abdulkarim.autocompletetextview.adapter.FromLocationAdapter
import com.abdulkarim.autocompletetextview.adapter.FromLocationItem
import com.abdulkarim.autocompletetextview.adapter.ToLocationAdapter
import com.abdulkarim.autocompletetextview.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fLocationAdapter: FromLocationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fLocationAdapter = FromLocationAdapter(this, getFromLocationList())

        binding.fromLocationAct.setAdapter(fLocationAdapter)
        binding.fromLocationAct.threshold = 1

        binding.fromLocationAct.setOnItemClickListener { parent, _, position, id ->

            val selectedFromLocation = parent.adapter.getItem(position) as FromLocationItem
            binding.fromLocationAct.setText(selectedFromLocation.fLocation)
            binding.fromLocationAct.setSelection(binding.fromLocationAct.text.length)

            val toLocationAdapter = ToLocationAdapter(
                this@MainActivity,
                selectedFromLocation.toLocationList
            )

            binding.toLocationAct.setAdapter(toLocationAdapter)
            binding.toLocationAct.threshold = 1

            binding.fromLocationAct.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?) {
                    binding.toLocationAct.setText("")
                }

            })

        }

        binding.toLocationAct.setOnItemClickListener { parent, _, position, id ->

            val selectedToLocation = parent.adapter.getItem(position) as String
            binding.toLocationAct.setText(selectedToLocation)
            binding.toLocationAct.setSelection(binding.toLocationAct.text.length)

            binding.startBtn.isVisible = true
        }

        binding.startBtn.setOnClickListener {

           binding.fromLocationAndToLocationTv.text =
                "From Location : ${binding.fromLocationAct.text} \nTo Location : ${binding.toLocationAct.text}"

        }

    }

    private fun getFromLocationList(): List<FromLocationItem> {

        val toLocationList1 = listOf(
            "Jhenaidah", "Khulna", "Rajshahi"
        )
        val toLocationList2 = listOf(
            "Dhanmondi", "Banani", "Gabtoli", "Mirpur"
        )

        val toLocationList3 = listOf(
            "Jessore", "Chowadanga", "Khulna", "Magura"
        )

        val fromLocationList = listOf(
            FromLocationItem("Dhaka", toLocationList1),
            FromLocationItem("Mohammodpur", toLocationList2),
            FromLocationItem("Banani", toLocationList3),
            FromLocationItem("Jhenaidah", toLocationList3),
        )

        return fromLocationList
    }

}