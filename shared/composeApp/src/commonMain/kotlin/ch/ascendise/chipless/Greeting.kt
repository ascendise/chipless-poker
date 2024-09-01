import ch.ascendise.chipless.getPlatform
import ch.ascendise.pokertracker.Table

class Greeting {
    private val platform = getPlatform()

    fun greet(table: Table): String {
        return "Hello, ${platform.name}!"
    }
}