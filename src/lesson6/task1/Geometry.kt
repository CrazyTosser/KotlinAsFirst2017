@file:Suppress("UNUSED_PARAMETER")

package lesson6.task1

import lesson1.task1.sqr
import java.lang.Math.cos
import java.lang.Math.tan

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

    fun getCenter() = Point((begin.x + end.x) / 2, (begin.y + end.y) / 2)
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
fun circleByDiameter(diameter: Segment): Circle =
        Circle(diameter.getCenter(), diameter.begin.distance(diameter.getCenter()))

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
        if (this.angle == Math.PI / 2) return Point(-this.b, (-this.b) *
                tan(other.angle) + other.b / cos(other.angle))
        if (other.angle == Math.PI / 2) return Point(-other.b, (-other.b) *
                tan(this.angle) + this.b / cos(this.angle))
        return Point(-(this.b / cos(this.angle) - other.b / cos(other.angle)) /
                (tan(this.angle) - tan(other.angle)),
                (-(this.b / cos(this.angle) - other.b / cos(other.angle)) /
                        (tan(this.angle) - tan(other.angle))) *
                        tan(this.angle) + this.b / cos(this.angle))
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
    var ang = Math.atan2((s.end.y - s.begin.y), (s.end.x - s.begin.x))
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
    var ang = if (lineByPoints(a, b).angle <= Math.PI / 2) Math.PI / 2 + lineByPoints(a, b).angle
    else lineByPoints(a, b).angle - Math.PI / 2
    if (ang == Math.PI) ang = 0.0
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
    var res = Pair(circles[0], circles[1])
    circles.forEach { c1 ->
        circles.filter { it != c1 }
                .asSequence()
                .filter { res.first.distance(res.second) > c1.distance(it) }
                .forEach { res = Pair(c1, it) }
    }
    return res
}

/*fun isNormal(a: Point, b: Point, c: Point): Boolean {
    val yDeltaA = b.y - a.y
    val xDeltaA = b.x - a.x
    val yDeltaB = c.y - b.y
    val xDeltaB = c.x - b.x
    return when {
        abs(xDeltaA) <= 1e-9 && abs(yDeltaB) <= 1e-9 -> false
        abs(yDeltaA) <= 1e-7 -> true
        abs(yDeltaB) <= 1e-7 -> true
        abs(xDeltaA) <= 1e-9 -> true
        else -> abs(xDeltaB) <= 1e-9
    }
}*/

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
    val bis1 = bisectorByPoints(a, b)
    val bis2 = bisectorByPoints(b, c)
    val center = bis1.crossPoint(bis2)
    return Circle(center, center.distance(a))
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
    var res = Circle(Point(0.0, 0.0), 1e+10000000)
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