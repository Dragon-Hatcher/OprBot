import kotlinx.serialization.json.*
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

private val toaAPIKey = System.getenv("TOA_API_KEY")
    ?: throw Exception("Must include toa token in environment variable for toa to run")

private fun getFromTOAApi(data: String): JsonArray? {
    val client = HttpClient.newBuilder().build();
    val request = HttpRequest.newBuilder()
        .uri(URI.create("https://theorangealliance.org/api/$data"))
        .setHeader("Content-Type", "application/json")
        .setHeader("X-TOA-Key", toaAPIKey)
        .setHeader("X-Application-Origin", "OPR Bot")
        .build()

    val response: HttpResponse<String>
    try {
        response = client.send(request, HttpResponse.BodyHandlers.ofString())
    } catch (e: Exception) {
        return null
    }
    return Json.parseToJsonElement(response.body()).jsonArray
}

typealias EventId = String

fun getOPRs(teamNumber: Int): List<Double>? {
    val rankings = getRankings(teamNumber) ?: return null
    return rankings.map { it["opr"].toString().toDouble() }
}

fun getRankings(teamNumber: Int): List<JsonObject>? {
    val rankings = getFromTOAApi("team/$teamNumber/results/2122") ?: return null
    val rankingsList = mutableListOf<JsonObject>()
    rankings.forEach { rankingsList.add(it.jsonObject) }
    return rankingsList
}
