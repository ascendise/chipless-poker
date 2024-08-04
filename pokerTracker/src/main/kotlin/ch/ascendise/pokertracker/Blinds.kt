package ch.ascendise.pokertracker

data class Blinds(val small: Int, val big: Int) {
    init {
       if(small > big)
           throw IllegalArgumentException("Big Blinds cannot be less than small blinds")
    }
}