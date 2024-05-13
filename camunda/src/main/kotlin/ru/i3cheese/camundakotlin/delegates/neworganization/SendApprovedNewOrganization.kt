package ru.i3cheese.camundakotlin.delegates.neworganization

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component


@Component
class SendApprovedNewOrganization: JavaDelegate {
    override fun execute(execution: DelegateExecution) {
        println("Sending approving")
    }
}
