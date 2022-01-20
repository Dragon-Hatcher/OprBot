import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on

suspend fun main() {
    val discordToken = System.getenv("BOT_TOKEN")
        ?: throw Exception("Must include bot token in environment variable for bot to run")
    val kord = Kord(discordToken)

    val commandRegex = "!opr (\\d+)".toRegex()

    kord.on<MessageCreateEvent> {

        //ignore other bots, even ourselves. We only serve humans here!
        if(message.author?.isBot != false) return@on

        println(message.content)
        val command = commandRegex.find(message.content.trim()) ?: return@on
        val teamNumber = command.groups[1]!!.value.toInt()

        //all clear, give them the pong!
        message.channel.createMessage("I would find info on team $teamNumber")
    }

    kord.login()
}