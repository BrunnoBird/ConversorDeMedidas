package br.com.brunnogonzalezanjos.conversordemedidas.models.strategies

class MetersToCentimetersStrategy : CalculationStrategy {
    override fun calculate(value: Double): Double = value * 100

    override fun getResultLabel(isPlural: Boolean): String = if (isPlural) "centímetros" else
        "centímetro"
}