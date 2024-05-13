package ru.i3cheese.camundakotlin.delegates

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component


@Component
class SendRejection: JavaDelegate {
    override fun execute(execution: DelegateExecution) {
        val rejectionReason = execution.getVariable("rejectionReason") as String;
        println("Sending rejection: $rejectionReason")
    }
}
