@file:Suppress("UNUSED_PARAMETER")

package lesson6.task1

import lesson1.task1.sqr
import java.lang.Math.abs

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = Math.sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return Math.sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double =
            Math.max(this.center.distance(other.center) - (this.radius + other.radius), 0.0)

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean =
            Math.sqrt(sqr(p.x - center.x) + sqr(p.y - center.y)) <= radius
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
            other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    if (points.count() < 2) throw IllegalArgumentException()
    var max = 0.0
    var res = Pair(Point(0.0, 0.0), Point(0.0, 0.0))
    for (i in points)
        for (j in points) {
            if (i == j) continue
            if (i.distance(j) > max) {
                res = Pair(i, j)
                max = i.distance(j)
            }
        }
    return Segment(res.first, res.second)
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle {
    val x = (diameter.begin.x + diameter.end.x) / 2
    val y = (diameter.begin.y + diameter.end.y) / 2
    return Circle(Point(x, y), diameter.begin.distance(Point(x, y)))
}

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        assert(angle >= 0 && angle < Math.PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * Math.cos(angle) - point.x * Math.sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        val del = Math.tan(this.angle) * Math.cos(other.angle) - Math.sin(other.angle)
        val dCos = Math.cos(other.angle) / Math.cos(this.angle)
        val x = (other.b - this.b * dCos) / del
        val y = x * Math.tan(this.angle) + (this.b / Math.cos(this.angle))
        return Point(x, y)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${Math.cos(angle)} * y = ${Math.sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line {
    //var ang = Math.atan((s.end.y - s.begin.y) / (s.end.x - s.begin.x))
    var ang = Math.atan2((s.end.y - s.begin.y), (s.end.x - s.begin.x))
    /*if (ang == Double.NEGATIVE_INFINITY || ang == Double.POSITIVE_INFINITY)
        ang = 1.0*/
    if (ang < 0) ang += Math.PI
    if (ang >= Math.PI) ang -= Math.PI
    return Line(s.begin, ang)
}

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line = lineBySegment(Segment(a, b))

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val ang =
            if (lineByPoints(a, b).angle + Math.PI / 2 >= Math.PI)
                lineByPoints(a, b).angle - Math.PI / 2
            else
                lineByPoints(a, b).angle + Math.PI / 2
    val mid = Point((a.x + b.x) / 2, (a.y + b.y) / 2)
    return Line(mid, ang)
}

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    if (circles.count() < 2) throw IllegalArgumentException()
    var res = Pair<Circle, Circle>(circles[0], circles[1])
    for (c1 in circles)
        for (c2 in circles) {
            if (c1 == c2) continue
            if (res.first.distance(res.second) > c1.distance(c2))
                res = Pair(c1, c2)
        }
    return res
}

fun isNormal(a: Point, b: Point, c: Point): Boolean {
    val yDeltaA = b.y - a.y
    val xDeltaA = b.x - a.x
    val yDeltaB = c.y - b.y
    val xDeltaB = c.x - b.x
    return when {
        abs(xDeltaA) <= 0.000000001 && abs(yDeltaB) <= 0.000000001 -> false
        abs(yDeltaA) <= 0.0000001 -> true
        abs(yDeltaB) <= 0.0000001 -> true
        abs(xDeltaA) <= 0.000000001 -> true
        else -> abs(xDeltaB) <= 0.000000001
    }
}

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val pointArr =
            when {
                !isNormal(a, b, c) -> listOf(a, b, c)
                !isNormal(a, c, b) -> listOf(a, c, b)
                !isNormal(b, a, c) -> listOf(b, a, c)
                !isNormal(b, c, a) -> listOf(b, c, a)
                !isNormal(c, b, a) -> listOf(c, b, a)
                !isNormal(c, a, b) -> listOf(c, a, b)
                else -> return Circle(Point(0.0, 0.0), 0.0)
            }
    val yDeltaA = (pointArr[1].y - pointArr[0].y)
    val yDeltaB = (pointArr[2].y - pointArr[1].y)
    val xDeltaA = (pointArr[1].x - pointArr[0].x)
    val xDeltaB = (pointArr[2].x - pointArr[1].x)
    val mid: Point
    if (Math.abs(xDeltaA) <= 1e-5 && Math.abs(yDeltaB) <= 1e-5) {
        mid = Point(0.5 * (pointArr[1].x + pointArr[2].x), 0.5 * (pointArr[0].y + pointArr[1].y))
    } else {
        val aSlope = yDeltaA / xDeltaA
        val bSlope = yDeltaB / xDeltaB
        val midX =
                (aSlope * bSlope * (pointArr[0].y - pointArr[2].y) + bSlope * (pointArr[0].x + pointArr[1].x) - aSlope * (pointArr[1].x + pointArr[2].x)) / (2 * (bSlope - aSlope))
        val midY = -1 * (midX - (pointArr[0].x + pointArr[1].x) / 2) / aSlope + (pointArr[0].y + pointArr[1].y) / 2
        mid = Point(midX, midY)
    }
    return Circle(mid, mid.distance(a))
}

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle {
    if (points.count() == 0) throw IllegalArgumentException()
    if (points.count() == 1) return Circle(points[0], 0.0)
    if (points.count() == 2) return circleByDiameter(Segment(points[0], points[1]))
    var res = Circle(Point(0.0, 0.0), 100000000000000000.0)
    for (a in points) {
        for (b in points.filter { it != a }) {
            for (c in points.filter { it != a && it != b }) {
                val tmp = circleByThreePoints(a, b, c)
                if (points.all { tmp.contains(it) }) {
                    if (tmp.radius < res.radius)
                    res = tmp
                }
            }
        }
    }
    val resD = circleByDiameter(diameter(*points))
    return if (res.radius < resD.radius || !points.all { resD.contains(it) }) res else resD
}