package ru.i3cheese.camundakotlin.delegates

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.variable.value.StringValue
import org.springframework.stereotype.Component
import ru.i3cheese.camundakotlin.evalai.EvalAISession
import ru.i3cheese.camundakotlin.xml.utils.XMLUtils


@Component
class SendRejectedNewOrganization: JavaDelegate {
    override fun execute(execution: DelegateExecution) {
        val rejectionReason = execution.getVariable("rejectionReason") as String;
        println("Sending rejection: $rejectionReason")
    }
}
