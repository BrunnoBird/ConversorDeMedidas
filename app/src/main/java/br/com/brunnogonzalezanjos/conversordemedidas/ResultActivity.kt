package br.com.brunnogonzalezanjos.conversordemedidas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val result = intent.getDoubleExtra("RESULT", 0.0)
        val label = intent.getStringExtra("LABEL")

        val tvValue: TextView = findViewById(R.id.tvValue)
        tvValue.text = result.toString()
        val tvValueLabel: TextView = findViewById(R.id.tvValueLabel)
        tvValueLabel.text = label

        val btnClose: Button = findViewById(R.id.btnClose)
        btnClose.setOnClickListener {
            finish()
        }
    }
}