package com.example.myapplication1

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var cups = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun submitOrder(view: View) {
        var oneCupPrice = 5
        if (findViewById<CheckBox>(R.id.checkbox_chocolate).isChecked) oneCupPrice+=2
        if (findViewById<CheckBox>(R.id.checkbox_cream).isChecked) oneCupPrice+=1
        display(cups)
        displayPrice(cups * oneCupPrice)
    }

    fun display(num: Int) {
        val outPut = findViewById<TextView>(R.id.quantity_text_view)
        outPut.setText("" + num)
    }

    fun displayPrice(num: Int) {
        val name: String = findViewById<EditText>(R.id.edit_name).text.toString()
        val cream: String = findViewById<CheckBox>(R.id.checkbox_cream).isChecked.toString()
        val chocolate = findViewById<CheckBox>(R.id.checkbox_chocolate).isChecked.toString()
        val text = getString(R.string.order_text, name,cream,chocolate,cups.toString(), num.toString())
        fun composeEmail(text: String, subject: String) {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:") // only email apps should handle this
                putExtra(Intent.EXTRA_TEXT, text)
                putExtra(Intent.EXTRA_SUBJECT, subject)
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
        composeEmail(text, "Coffee order for ${findViewById<EditText>(R.id.edit_name).text}")

    }

    fun increment(view: View) {
        cups++
        if (cups > 100) {
            cups = 100
            Toast.makeText(applicationContext, "You cannot have more then 100 coffee", Toast.LENGTH_SHORT).show()
        }
        display(cups)
    }

    fun decrement(view: View) {
        cups--
        if (cups < 1) {
            cups = 1
            Toast.makeText(applicationContext, "You cannot have less then 1 coffee", Toast.LENGTH_LONG).show()
        }
        display(cups)
    }
}