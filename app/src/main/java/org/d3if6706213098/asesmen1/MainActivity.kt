package org.d3if6706213098.asesmen1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import org.d3if6706213098.asesmen1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val aktifitas = arrayOf("Sangat Jarang Olahraga", "Jarang Olahraga (1-2 kali seminggu)", "Olahraga Normal (2-3 kali seminggu)", "Sering Olahraga (4-5 kali seminggu)", "Sangat Sering Olahraga (2 kali sehari)")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener { hitungBMR() }
        binding.button2.setOnClickListener { reset() }

        val spinner = findViewById<Spinner>(R.id.aktifitasSpinner)
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, aktifitas)
        spinner.adapter = arrayAdapter


    }

    private fun hitungBMR() {
        val umur = binding.umurEditText.text.toString()
        if (TextUtils.isEmpty(umur)) {
            Toast.makeText(this, R.string.umur_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val berat = binding.beratEditText.text.toString()
        if (TextUtils.isEmpty(berat)) {
            Toast.makeText(this, R.string.berat_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val tinggi = binding.tinggiEditText.text.toString()
        if (TextUtils.isEmpty(tinggi)) {
            Toast.makeText(this, R.string.tinggi_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val bmr: Float
        val selectedId = binding.radioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(this, R.string.gender_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val isMale = selectedId == R.id.priaRadioButton
        if (isMale) {
            bmr = (66.5 + (13.7*berat.toFloat()) + (5*tinggi.toFloat()) - (6.8*umur.toFloat())).toFloat()
        } else {
            bmr = (65.5 + (9.6*berat.toFloat()) + (1.8*tinggi.toFloat()) - (4.7*umur.toFloat())).toFloat()
        }
        val intensitas = getAktifitas(bmr)
        val tdee = bmr * intensitas


        binding.result.text = getString(R.string.bmr_x, tdee)
    }

    private fun getAktifitas(bmr: Float): Float {
        val spinner = findViewById<Spinner>(R.id.aktifitasSpinner)
        val selectAktifitas = spinner.getSelectedItem().toString()
        val tingkatAktifitas: Float
        if (selectAktifitas.equals("Sangat Jarang Olahraga")){
            tingkatAktifitas = 1.2F
        } else if (selectAktifitas.equals("Jarang Olahraga (1-2 kali seminggu)")){
            tingkatAktifitas = 1.375F
        } else if (selectAktifitas.equals("Olahraga Normal (2-3 kali seminggu)")){
            tingkatAktifitas = 1.55F
        } else if (selectAktifitas.equals("Sering Olahraga (4-5 kali seminggu)")){
            tingkatAktifitas = 1.725F
        } else {
            tingkatAktifitas = 1.9F
        }
        return tingkatAktifitas
    }

    private fun reset(){
        binding.umurEditText.text.clear()
        binding.tinggiEditText.text.clear()
        binding.radioGroup.clearCheck()
        binding.beratEditText.text.clear()
        binding.result.text = ""
    }
}