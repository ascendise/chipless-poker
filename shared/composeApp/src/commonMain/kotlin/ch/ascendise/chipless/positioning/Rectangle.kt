package ch.ascendise.chipless.positioning

internal class Rectangle(
    val width: Double,
    val height: Double
) {

    fun splitEvenly(points: Int): Array<Point> {
        val vertices: MutableList<Point> = mutableListOf()
        val perimeter = width * 2 + height * 2
        val steps = perimeter / points
        var point = Point(0.0, height / 2)
        while(point.x <= width / 2) {
            vertices.add(point)
            point = point.copy(x = point.x + steps)
        }
        var difference = point.x - (width / 2)
        if(difference > 0)
            point = point.copy(x = point.x - difference, y = point.y - difference)
        while(point.y >= height / -2) {
            vertices.add(point)
            point = point.copy(y = point.y - steps)
        }
        difference = point.y - (height / -2)
        if(difference < 0)
            point = point.copy(x = point.x + difference, y = point.y - difference)
        while(point.x >= width / -2) {
            vertices.add(point)
            point = point.copy(x = point.x - steps)
        }
        difference = point.x - (width / -2)
        if(difference < 0)
            point = point.copy(x = point.x - difference, y = point.y - difference)
        while(point.y <= height / 2) {
            vertices.add(point)
            point = point.copy(y = point.y + steps)
        }
        difference = point.y - (height / 2)
        if(difference > 0)
            point = point.copy(x = point.x + difference, y = point.y - difference)
        while(point.x < 0) {
            vertices.add(point)
            point = point.copy(x = point.x + steps)
        }
        return vertices.toTypedArray()
    }
}