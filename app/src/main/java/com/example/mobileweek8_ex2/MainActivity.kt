package com.example.mobileweek8_ex2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var editTextNumber: EditText
    private lateinit var radioGroupFilters: RadioGroup
    private lateinit var listViewResults: ListView
    private lateinit var textViewEmpty: TextView


    private lateinit var resultsList: ArrayList<Int>
    private lateinit var adapter: ArrayAdapter<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextNumber = findViewById(R.id.editTextNumber)
        radioGroupFilters = findViewById(R.id.radioGroupFilters)
        listViewResults = findViewById(R.id.listViewResults)
        textViewEmpty = findViewById(R.id.textViewEmpty)

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

        radioGroupFilters.setOnCheckedChangeListener { group, checkedId ->
            updateList()
        }
    }


    private fun updateList() {
        val limitNumber = editTextNumber.text.toString().toIntOrNull() ?: 0

        val selectedFilterId = radioGroupFilters.checkedRadioButtonId

        resultsList.clear()


        for (i in 1 until limitNumber) {
            val shouldAdd: Boolean = when (selectedFilterId) {
                R.id.radioOdd -> isOdd(i)
                R.id.radioEven -> isEven(i)
                R.id.radioPrime -> isPrime(i)
                R.id.radioSquare -> isPerfectSquare(i)
                R.id.radioPerfect -> isPerfectNumber(i)
                R.id.radioFibonacci -> isFibonacci(i)
                else -> false // Mặc định là không thêm
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


    // Số lẻ
    private fun isOdd(n: Int): Boolean {
        return n % 2 != 0
    }

    // Số chẵn
    private fun isEven(n: Int): Boolean {
        return n % 2 == 0
    }

    // Số nguyên tố
    private fun isPrime(n: Int): Boolean {
        if (n < 2) return false
        val limit = sqrt(n.toDouble()).toInt()
        for (i in 2..limit) {
            if (n % i == 0) {
                return false
            }
        }
        return true
    }

    // Số chính phương
    private fun isPerfectSquare(n: Int): Boolean {
        if (n < 0) return false
        val sqrt = sqrt(n.toDouble()).toInt()
        return sqrt * sqrt == n
    }

    // Số hoàn hảo (tổng các ước bằng chính nó)
    private fun isPerfectNumber(n: Int): Boolean {
        if (n <= 1) return false
        var sum = 1 // Bắt đầu với ước là 1
        val limit = sqrt(n.toDouble()).toInt()
        for (i in 2..limit) {
            if (n % i == 0) {
                sum += i
                // Nếu 'i' là ước, thì 'n/i' cũng là ước
                // Thêm 'n/i' chỉ khi nó không bằng 'i' (trường hợp số chính phương)
                if (i * i != n) {
                    sum += n / i
                }
            }
        }
        return sum == n
    }

    // Số Fibonacci
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