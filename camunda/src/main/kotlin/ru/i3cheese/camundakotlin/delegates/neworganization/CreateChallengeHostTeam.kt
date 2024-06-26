package ru.i3cheese.camundakotlin.delegates.neworganization

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.variable.value.StringValue
import org.springframework.stereotype.Component
import ru.i3cheese.camundakotlin.evalai.EvalAISession


@Component
class CreateChallengeHostTeam: JavaDelegate {
    @Serializable
    private data class ChallengeHostTeamBody(val team_name: String, val team_url: String)
    override fun execute(execution: DelegateExecution) {
        val teamName: String = execution.getVariableTyped<StringValue>("teamName", true).value
        val teamUrl: String = execution.getVariableTyped<StringValue>("teamUrl", true).value
        println("Creating team $teamName with url $teamUrl")
        val response = EvalAISession.doPost("/api/hosts/challenge_host_team/",
            Json.encodeToString(
                ChallengeHostTeamBody(teamName, teamUrl)
            )
        )
        println(response)
    }
}
