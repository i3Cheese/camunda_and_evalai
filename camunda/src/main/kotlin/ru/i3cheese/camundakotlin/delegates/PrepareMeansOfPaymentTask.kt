package ru.i3cheese.camundakotlin.delegates

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component
class PrepareMeansOfPaymentTask: JavaDelegate {
    override fun execute(execution: DelegateExecution?) {
        TODO("Not yet implemented")
    }
}