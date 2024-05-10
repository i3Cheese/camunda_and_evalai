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
class CheckNewOrganizationApplication: JavaDelegate {
    override fun execute(execution: DelegateExecution) {
        val xmlNewOrganizationApplication = execution.getVariableTyped<StringValue>("xmlNewOrganizationApplication", true).value;
        val newOrganizationApplication: ru.i3cheese.camundakotlin.xml.genmodel.NewOrganizationApplication
        try {
            newOrganizationApplication = XMLUtils.unmarshal<ru.i3cheese.camundakotlin.xml.genmodel.NewOrganizationApplication>(
                xmlNewOrganizationApplication
            );
        } catch (e: Exception) {
            println("Error unmarshalling xmlNewOrganizationApplication: $e")
            execution.setVariable("approved", false)
            execution.setVariable("rejectionReason", "Bad XML")
            return
        }
        execution.setVariable("approved", true)
    }
}
