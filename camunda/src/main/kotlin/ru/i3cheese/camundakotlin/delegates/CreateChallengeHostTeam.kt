package ru.i3cheese.camundakotlin.delegates

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component
import ru.i3cheese.camundakotlin.evalai.EvalAISession


@Component
class CreateChallengeHostTeam: JavaDelegate {
    @Serializable
    private data class ChallengeHostTeamBody(val test_team_name: String, val team_team_url: String)
    override fun execute(execution: DelegateExecution) {
        val response = EvalAISession.doPost("/api/hosts/challenge_host_team/internal",
            Json.encodeToString(
                ChallengeHostTeamBody("test_team_name", "team_team_url")
            )
        )
        println(response)
    }
}
