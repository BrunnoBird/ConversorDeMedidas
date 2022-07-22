package br.com.brunnogonzalezanjos.conversordemedidas.models

import br.com.brunnogonzalezanjos.conversordemedidas.models.strategies.CalculationStrategy

data class CalculationStrategyHolder(
    val name: String,
    val calculationStrategy: CalculationStrategy
)