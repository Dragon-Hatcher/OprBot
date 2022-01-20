import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on

suspend fun main() {
    val token = System.getenv("BOT_TOKEN")
        ?: throw Exception("Must include bot token in environment variable for bot to run")
    val kord = Kord(token)

    kord.on<MessageCreateEvent> {//runs every time a message is created that our bot can read

        //ignore other bots, even ourselves. We only serve humans here!
        if(message.author?.isBot != false) return@on

        //check if our command is being invoked
        if(message.content != "!ping") return@on

        //all clear, give them the pong!
        message.channel.createMessage("pong!")
    }

    kord.login()
}