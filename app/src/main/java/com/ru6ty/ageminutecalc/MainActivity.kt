package com.ru6ty.ageminutecalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var selectedDate : TextView? = null
    private var selectedDateInMinutes : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker = findViewById<Button>(R.id.buttonDatePicker)
        selectedDate = findViewById(R.id.selectedDate)
        selectedDateInMinutes = findViewById(R.id.selectedDateInMinutes)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
//            Toast.makeText(this, "Button Works", Toast.LENGTH_LONG).show()
        }
    }

    private fun clickDatePicker(){

        val cal = Calendar.getInstance()
        val restrictFutureDatePick = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
//                Toast.makeText(this,
//                "Selected date was $dayOfMonth/${month + 1}/$year", Toast.LENGTH_LONG).show()

                val pickedDate = "$dayOfMonth/${month + 1}/$year"
                selectedDate?.text = pickedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(pickedDate)

                // Run only if theDate is not null
                theDate?.let {
                    val pickedDateInMinutes = theDate.time / 60000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    // Run only if currentDate is not null
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000

                        val differenceInMinutes = currentDateInMinutes - pickedDateInMinutes

                        selectedDateInMinutes?.text = differenceInMinutes.toString()
                    }

                }


            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )

        restrictFutureDatePick.datePicker.maxDate = System.currentTimeMillis() - 86400000
        restrictFutureDatePick.show()
    }
}