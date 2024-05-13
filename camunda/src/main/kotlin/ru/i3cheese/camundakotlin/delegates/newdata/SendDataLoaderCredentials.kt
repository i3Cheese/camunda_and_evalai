package ru.i3cheese.camundakotlin.delegates.newdata

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.variable.value.StringValue
import org.springframework.stereotype.Component
import ru.i3cheese.camundakotlin.xml.utils.XMLUtils


@Component
class SendDataLoaderCredentials: JavaDelegate {
    override fun execute(execution: DelegateExecution) {
        val xmlNewDataCredentialsApplication = execution.getVariableTyped<StringValue>("xmlNewDataCredentialsApplication", true).value;
        val newDataCredentialsApplication: ru.i3cheese.camundakotlin.xml.genmodel.NewDataCredentialsApplication
        try {
            newDataCredentialsApplication = XMLUtils.unmarshal<ru.i3cheese.camundakotlin.xml.genmodel.NewDataCredentialsApplication>(
                xmlNewDataCredentialsApplication
            );
        } catch (e: Exception) {
            println("Error unmarshalling xmlNewDataCredentialsApplication: $e")
            return
        }
        val accessKey = execution.getVariable("newMinioAccessKey") as String
        val secretKey = execution.getVariable("newMinioSecretKey") as String
        // TODO: send
        println("Sending credentials to ${newDataCredentialsApplication.organizationContactPerson.email} " +
                "with access key $accessKey and secret key $secretKey")
    }
}
