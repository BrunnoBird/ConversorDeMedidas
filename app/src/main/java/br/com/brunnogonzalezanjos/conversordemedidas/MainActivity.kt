package br.com.brunnogonzalezanjos.conversordemedidas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import br.com.brunnogonzalezanjos.conversordemedidas.models.CalculationStrategyHolder
import br.com.brunnogonzalezanjos.conversordemedidas.models.Calculator
import br.com.brunnogonzalezanjos.conversordemedidas.models.strategies.*
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private lateinit var edtValue: EditText
    private lateinit var spConversions: Spinner

    private val supportedCalculationStrategies = arrayOf(
        CalculationStrategyHolder(
            name = "Quilômetro para centímetros",
            calculationStrategy = KilometerToCentimetersStrategy()
        ),
        CalculationStrategyHolder(
            name = "Quilômetro para metros",
            calculationStrategy = KilometerToMeterStrategy()
        ),
        CalculationStrategyHolder(
            name = "Metros para centimetros", calculationStrategy =
            MetersToCentimetersStrategy()
        ),
        CalculationStrategyHolder(
            name = "Metros para quilômetros",
            calculationStrategy = MetersToKilometerStrategy()
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Persistindo dados ao rotacionar a tela
        var value = 0.0
        var position = 0
        savedInstanceState?.let {
            value = it.getDouble("VALUE")
            position = it.getInt("POSITION")
        }

        initUi()
        setUi(value, position)
        setActions()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        try {
            outState.putDouble("VALUE", edtValue.text.toString().toDouble())
        } catch (e: NumberFormatException) {
        }
        outState.putInt("POSITION", spConversions.selectedItemPosition)
    }

    private fun initUi() {
        spConversions = findViewById(R.id.spConversions)
        edtValue = findViewById(R.id.edtValue)
    }

    private fun setActions() {
        val btnConvert: Button = findViewById(R.id.btnConvert)
        btnConvert.setOnClickListener {
            try {
                val value = edtValue.text.toString().toDouble()
                val calculationType =
                    supportedCalculationStrategies[spConversions.selectedItemPosition].calculationStrategy

                Calculator.setCalculationStrategy(calculationType)
                val result = Calculator.calculate(value)
                val label = calculationType.getResultLabel(result != 1.toDouble())

                showResult(result, label)
            } catch (e: NumberFormatException) {
                edtValue.error = "Valor inválido"
                edtValue.requestFocus()
            }
        }

        val btnClean: Button = findViewById(R.id.btnClean)
        btnClean.setOnClickListener {
            Clean()
        }
    }

    private fun Clean() {
        edtValue.setText("")
        edtValue.error = null

        spConversions.setSelection(0)
    }

    private fun showResult(result: Double, label: String) {

        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("RESULT", result)
        intent.putExtra("LABEL", label)

        startActivity(intent)
    }

    private fun setUi(value: Double, position: Int) {
        edtValue.setText(value.toString())
        spConversions.setSelection(position)
        val spAdapter = ArrayAdapter(this, R.layout.res_spinner_item, getSpinnerData())
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spConversions.adapter = spAdapter
    }

    private fun getSpinnerData(): MutableList<String> {
        val spinnerData = mutableListOf<String>()
        supportedCalculationStrategies.forEach {
            spinnerData.add(it.name)
        }
        return spinnerData
    }
}