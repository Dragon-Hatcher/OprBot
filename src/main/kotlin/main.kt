import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on

suspend fun main() {
    val discordToken = System.getenv("BOT_TOKEN")
        ?: throw Exception("Must include bot token in environment variable for bot to run")
    val kord = Kord(discordToken)

    println("Initializing")

    val commandRegex = "!opr (\\d+)".toRegex()

    kord.on<MessageCreateEvent> {
        if(message.author?.isBot != false) return@on

        println(message.content)
        val command = commandRegex.find(message.content.trim()) ?: return@on
        val teamNumber = command.groups[1]!!.value.toInt()

        val oprs = getOPRs(teamNumber)?.sortedDescending()
        message.channel.createMessage(when {
            oprs == null -> "There was an error fetching the data."
            oprs.isEmpty() -> "We don't have any OPRs for team $teamNumber this season."
            else -> "Team $teamNumber's best OPR this season is ${oprs.maxOf { it }}"
        })
    }

    kord.login()
}