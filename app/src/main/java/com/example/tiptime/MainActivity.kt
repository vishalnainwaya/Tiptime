package com.example.tiptime

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculateTipButton.setOnClickListener{ calculateTip()}
        binding.costOfServiceEditText.setOnKeyListener{view,keyCode,_ -> handleKeyEvent(view,keyCode)}
    }

    fun calculateTip(){
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost ==null)
        {
            binding.tipResult.text=""
            Toast.makeText(applicationContext,"Please enter Cost", Toast.LENGTH_SHORT).show()
            return
        }
        val tip = binding.tipOptions.checkedRadioButtonId
        val tipPercent = when(tip){
            R.id.twenty_option -> 0.20
            R.id.eighteen_option -> 0.18
            else -> 0.15
        }

        var tempResult = cost*tipPercent
        val roundUp = binding.switchButton.isChecked
        if (roundUp){
            tempResult = kotlin.math.ceil(tempResult)
        }

        val formattedResult = NumberFormat.getCurrencyInstance().format(tempResult)
        binding.tipResult.text = getString(R.string.tip_amount,formattedResult)
        
    }
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}