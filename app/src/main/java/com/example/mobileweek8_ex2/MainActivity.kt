package com.example.mobileweek8_ex2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var editTextNumber: EditText
    private lateinit var listViewResults: ListView
    private lateinit var textViewEmpty: TextView

    private lateinit var radioOdd: RadioButton
    private lateinit var radioEven: RadioButton
    private lateinit var radioPrime: RadioButton
    private lateinit var radioSquare: RadioButton
    private lateinit var radioPerfect: RadioButton
    private lateinit var radioFibonacci: RadioButton

    private lateinit var radioButtons: List<RadioButton>

    private lateinit var resultsList: ArrayList<Int>
    private lateinit var adapter: ArrayAdapter<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Sửa: Đã xóa .xml

        editTextNumber = findViewById(R.id.editTextNumber)
        listViewResults = findViewById(R.id.listViewResults)
        textViewEmpty = findViewById(R.id.textViewEmpty)

        radioOdd = findViewById(R.id.radioOdd)
        radioEven = findViewById(R.id.radioEven)
        radioPrime = findViewById(R.id.radioPrime)
        radioSquare = findViewById(R.id.radioSquare)
        radioPerfect = findViewById(R.id.radioPerfect)
        radioFibonacci = findViewById(R.id.radioFibonacci)

        radioButtons = listOf(radioOdd, radioEven, radioPrime, radioSquare, radioPerfect, radioFibonacci)

        resultsList = ArrayList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, resultsList)

        listViewResults.adapter = adapter

        setupListeners()

        updateList()
    }


    private fun setupListeners() {
        editTextNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                updateList()
            }
        })

        // click listener chung cho 6 nút
        val onRadioClicked = View.OnClickListener { view ->
            (view as RadioButton).isChecked = true

            for (button in radioButtons) {
                if (button.id != view.id) {
                    button.isChecked = false
                }
            }

            updateList()
        }

        for (button in radioButtons) {
            button.setOnClickListener(onRadioClicked)
        }
    }


    private fun updateList() {
        val limitNumber = editTextNumber.text.toString().toIntOrNull() ?: 0

        val selectedFilterId = when {
            radioOdd.isChecked -> R.id.radioOdd
            radioEven.isChecked -> R.id.radioEven
            radioPrime.isChecked -> R.id.radioPrime
            radioSquare.isChecked -> R.id.radioSquare
            radioPerfect.isChecked -> R.id.radioPerfect
            radioFibonacci.isChecked -> R.id.radioFibonacci
            else -> R.id.radioOdd // Mặc định là "Số lẻ" nếu không ai được chọn
        }

        resultsList.clear()

        for (i in 1 until limitNumber) {
            val shouldAdd: Boolean = when (selectedFilterId) {
                R.id.radioOdd -> isOdd(i)
                R.id.radioEven -> isEven(i)
                R.id.radioPrime -> isPrime(i)
                R.id.radioSquare -> isPerfectSquare(i)
                R.id.radioPerfect -> isPerfectNumber(i)
                R.id.radioFibonacci -> isFibonacci(i)
                else -> false
            }

            if (shouldAdd) {
                resultsList.add(i)
            }
        }

        if (resultsList.isEmpty()) {
            listViewResults.visibility = View.GONE
            textViewEmpty.visibility = View.VISIBLE
        } else {
            listViewResults.visibility = View.VISIBLE
            textViewEmpty.visibility = View.GONE
        }

        adapter.notifyDataSetChanged()
    }


    private fun isOdd(n: Int): Boolean = n % 2 != 0
    private fun isEven(n: Int): Boolean = n % 2 == 0

    private fun isPrime(n: Int): Boolean {
        if (n < 2) return false
        val limit = sqrt(n.toDouble()).toInt()
        for (i in 2..limit) {
            if (n % i == 0) return false
        }
        return true
    }

    private fun isPerfectSquare(n: Int): Boolean {
        if (n < 0) return false
        val sqrt = sqrt(n.toDouble()).toInt()
        return sqrt * sqrt == n
    }

    private fun isPerfectNumber(n: Int): Boolean {
        if (n <= 1) return false
        var sum = 1
        val limit = sqrt(n.toDouble()).toInt()
        for (i in 2..limit) {
            if (n % i == 0) {
                sum += i
                if (i * i != n) {
                    sum += n / i
                }
            }
        }
        return sum == n
    }

    private fun isFibonacci(n: Int): Boolean {
        if (n < 0) return false
        val nLong = n.toLong()
        val test1 = 5 * nLong * nLong + 4
        val test2 = 5 * nLong * nLong - 4
        return isPerfectSquareLong(test1) || isPerfectSquareLong(test2)
    }

    private fun isPerfectSquareLong(n: Long): Boolean {
        if (n < 0) return false
        val sqrt = sqrt(n.toDouble()).toLong()
        return sqrt * sqrt == n
    }
}