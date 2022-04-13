package com.example.konversiuang

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import com.example.konversiuang.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var idBind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        idBind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(idBind.root)
        idBind.nilaiMataUang.setOnKeyListener{view, keyCode, _ -> handleKeyEvent(view, keyCode)}

        idBind.textRupiah.isVisible = false
        idBind.convertButton.setOnClickListener{konversi()}
    }

    private fun konversi() {
        val stringInTextField = idBind.nilaiMataUang.text.toString()
        val nilaiUang = stringInTextField.toDoubleOrNull()
        if (nilaiUang == null) {
            idBind.textRupiah.text =" "
            return
        }

        val convert = when (idBind.currencyGroup.checkedRadioButtonId){
            R.id.euro_button -> 15886.76
            R.id.usd_button -> 14376.05
            R.id.yen_button -> 117.34
            else -> 3832.17
        }

        val totalRupiah = nilaiUang * convert
        val indonesianLocale = Locale("in", "ID")
        val rupiahFormat = NumberFormat.getCurrencyInstance(indonesianLocale).format(totalRupiah)
        idBind.textRupiah.text = getString(R.string.rupiah_value, rupiahFormat)
        idBind.textRupiah.isVisible = true

    }

    private fun handleKeyEvent(view: View, keyCode:Int): Boolean{
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}