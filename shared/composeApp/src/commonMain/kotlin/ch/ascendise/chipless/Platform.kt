package ch.ascendise.chipless

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform