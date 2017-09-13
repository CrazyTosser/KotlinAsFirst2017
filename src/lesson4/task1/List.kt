@file:Suppress("UNUSED_PARAMETER")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import java.lang.Math.pow
import java.lang.Math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = Math.sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + Math.sqrt(d)) / (2 * a)
    val y2 = (-b - Math.sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    var res = 0.0
    for (i in v)
        res += sqr(i)
    return sqrt(res)
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    if (list.count() == 0) return 0.0
    return list.sum() / list.count()
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    if (list.count() == 0) return list
    val mid = mean(list)
    for (c in 0..list.count() - 1) {
        list[c] -= mid
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double {
    var res = 0.0
    for (i in 0..a.count() - 1) {
        res += a[i] * b[i]
    }
    return res
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double {
    if (p.count() == 0) return 0.0
    var res = 0.0
    for ((i, elem) in p.withIndex()) {
        res += elem * pow(x, i.toDouble())
    }
    return res
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    if (list.count() == 0) return list
    var res = list[0]
    var i = 1
    while (i < list.count()) {
        res += list[i]
        list[i] = res
        i++
    }
    return list
}

////Решето Эратосфена
//fun erat(x:Int): List<Int> {
//    var res = mutableListOf<Int>()
//    for(i in 0..x)
//        res.add(i)
//    var i = 2
//    res[1] = 0
//    while(i<res.count()) {
//        if (res[i] != 0) {
//            var j = i * 2
//            while (j < res.count()) {
//                res[j] = 0
//                j += i
//            }
//        }
//        i++
//    }
//    res.remove(0)
//    return res.toList().distinct()
//}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var workN = n
    val res: MutableList<Int> = mutableListOf()
    for (i in 2..sqrt(n.toDouble()).toInt() - 1)
        while (workN % i == 0) {
            res.add(i)
            workN /= i
        }
    if (workN != 1) res.add(workN)
    return res
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    if (n == 0) return listOf(0)
    val res = mutableListOf<Int>()
    var a = n
    while (a != 0) {
        res.add(a % base)
        a /= base
    }
    res.reverse()
    return res
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String {
    val res = convert(n, base).map { i -> if (i <= 9) i.toString() else ('a' + (i - 10)).toString() }
    return res.joinToString(separator = "")
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var res = 0
    var r = digits.count().toDouble() - 1
    for (i in digits) {
        res += (i * pow(base.toDouble(), r)).toInt()
        r--
    }
    return res
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int {
    val resTmp = mutableListOf<Int>()
    for (i in str) {
        resTmp.add(if (i.toInt() < 58) i.toInt() - 48 else i.toInt() - 87)
    }
    return decimal(resTmp.toList(), base)
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var wN = n
    val rom = mutableListOf<Pair<Int, String>>(Pair(1000, "M"), Pair(900, "CM"), Pair(500, "D"), Pair(400, "CD"),
            Pair(100, "C"), Pair(90, "XC"), Pair(50, "L"), Pair(40, "XL"), Pair(10, "X"), Pair(9, "IX"),
            Pair(5, "V"), Pair(4, "IV"), Pair(1, "I"))
    val res = StringBuilder()
    while (rom.count() > 0) {
        if (wN < rom[0].first)
            rom.removeAt(0)
        else {
            wN -= rom[0].first
            res.append(rom[0].second)
        }
    }
    return res.toString()
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    if (n == 0) return "ноль"
    val sex = listOf<List<String>>(listOf("", "один", "два", "три", "четыре", "пять", "шесть", "семь",
            "восемь", "девять"),
            listOf("", "одна", "две", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"))
    val str100 = listOf<String>("", "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот",
            "семьсот", "восемьсот", "девятьсот")
    val str11 = listOf<String>("", "десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать",
            "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать", "двадцать")
    val str10 = listOf<String>("", "десять", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят",
            "восемьдесят", "девяносто")
    val form = listOf<String>("тысяча", "тысячи", "тысяч")
    val segments = mutableListOf<Int>()
    var tmp = n
    while (tmp > 999) {
        val seg = tmp / 1000
        segments.add(tmp - seg * 1000)
        tmp = seg
    }
    segments.add(tmp)
    segments.reverse()
    val res = StringBuilder()
    var lev = segments.count() - 1
    for (ri in segments) {
        val sexi = lev
        val r1 = ri / 100
        val r22 = ri % 100
        val r2 = r22 / 10
        val r3 = ri % 10
        if (ri > 99) res.append(str100[r1]).append(" ")
        if (r22 > 20) {
            res.append(str10[r2]).append(" ")
            res.append(sex[sexi][r3]).append(" ")
        } else {
            if (r22 > 9) res.append(str11[r22 - 9]).append(" ")
            else if (r22 in 1..9) res.append(sex[sexi][r3]).append(" ")
        }
        if (lev == 1) {
            val n10 = ri % 100
            val n1 = n10 % 10
            if (n10 in 11..19) res.append(form[2]).append(" ")
            else if (n1 in 2..4) res.append(form[1]).append(" ")
            else if (n1 == 1) res.append(form[0]).append(" ")
            else res.append(form[2]).append(" ")
        }
        lev--
    }
    return res.toString().replace("  ", " ").trim()
}