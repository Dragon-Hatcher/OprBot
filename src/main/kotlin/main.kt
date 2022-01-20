import me.jakejmattson.discordkt.api.dsl.bot
import me.jakejmattson.discordkt.api.dsl.commands

fun main() {
    val token = System.getenv("BOT_TOKEN")
        ?: throw Exception("Must include bot token in environment variable for bot to run")

    bot(token) {
        prefix { "!" }
        commands("Commands") {
            command("ping") {
                description = "Says pong"
                execute {
                    respond("pong!")
                }
            }
//            command("opr") {
//                description = "Gives the most recent opr of the given team"
//                execute(IntegerArg) {
//                    val team
//                }
//            }
        }
    }
}