@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import java.lang.Math.*

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    for (m in 2..Math.sqrt(n.toDouble()).toInt()) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 */
fun digitNumber(n: Int): Int = abs(n).toString().length //Эх, если скажете переделаю на while, но также прооооще ;)

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var x = 1
    var y = 0
    for (i in 2..n) {
        x += y; y = x - y
    }
    return x
}

/**
 * Функция определения наибольшего общего делителя
 */
fun nod(a: Int, b: Int): Int {
    if (b == 0) return a
    return nod(b, a % b)
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int = m / nod(m, n) * n

//Чет мне понравились функции с лямбдой
fun div(n:Int,wrFun:(Int,Int)->Int): Int {
    val tmp = round(sqrt(n.toDouble())).toInt()
    for (div in 2..tmp)
        if (wrFun(n, div) != -1) return wrFun(n, div)
    return n
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int = div(n) { a, b -> if (a % b == 0) b else -1 }

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int =
        if (div(n) { a, b -> if (a % (a / b) == 0) a / b else -1 } == n) 1
        else div(n) { a, b -> if (a % (a / b) == 0) a / b else -1 }

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean = nod(m, n) == 1

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    for (i in m..n)
        if (sqrt(i.toDouble()) % 1.0 == 0.0) return true
    return false
}

fun sqr(x:Int):Int = x*x

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    var xClean = x
    var counter = 1
    var dif = if(xClean > 2* Math.PI) -2.0 else 2.0
    while (xClean > 2 * Math.PI || xClean < 0)
        xClean += dif * Math.PI
    var res = xClean
    dif = xClean
    var mn = -1
    while (abs(dif) > eps) {
        dif = mn * pow(xClean,2.0*counter+1)/ factorial(2*counter+1)
        res += dif
        counter++
        mn *= -1
    }
    return res
}

fun power(x: Double, p: Int): Double {
    var res = 1.0
    for (i in 0 until p)
        res *= x
    return res
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    var xClean = x
    var counter = 1
    var dif = if (xClean > Math.PI) -2.0 else 2.0
    while (xClean > Math.PI || xClean < -Math.PI)
        xClean += dif * Math.PI
    var res = 1.0
    dif = xClean
    var mn = -1
    while (abs(dif) > eps) {
        dif = mn * power(xClean, 2 * counter) / factorial(2 * counter)
        res += dif
        counter++
        mn *= -1
    }
    return res
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 * Не использовать строки при решении задачи.
 */
fun revert(n: Int): Int {
    var res = 0
    var tmp = n
    while (tmp > 0) {
        res *= 10
        res += tmp % 10
        tmp /= 10
    }
    return res
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 */
fun isPalindrome(n: Int): Boolean {
    val str = n.toString()
    val len = str.length
    if (len == 1) return true
    val center: Int
    center = if (len % 2 == 1) {
        (len - 1) / 2
    } else {
        len / 2
    }
    for (i in 0..center)
        if (str[i] != str[len - i - 1]) return false
    return true
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 */
fun hasDifferentDigits(n: Int): Boolean {
    val nStr = n.toString()
    val tmp = nStr[0]
    for (i in nStr)
        if (tmp != i) return true
    return false
}

//Фунция для squareSequenceDigit и fibSequenceDigit
//Принимает n - номер искомой позиции в ряду и wrFun - лябда-функцию согласно которой ряд генерируется
fun genStr(n: Int, wrFun: (Int) -> String): Int {
    var counter = 1
    var strLen = 0
    var res = ""
    while (strLen < n) {
        res = wrFun(counter)
        strLen += res.length
        counter++
    }
    val resI = res.length - (strLen - n) - 1
    return res[resI].toString().toInt()
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 */
fun squareSequenceDigit(n: Int): Int = genStr(n) { a -> "${a * a}" }

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 */
fun fibSequenceDigit(n: Int): Int = genStr(n) { a -> "${fib(a)}" }