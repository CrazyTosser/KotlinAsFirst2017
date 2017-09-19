@file:Suppress("UNUSED_PARAMETER")

package lesson5.task1

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateStrToDigit(str: String): String {
    val wrL = str.split(" ").toMutableList()
    val tes = listOf<String>("января", "февраля", "марта", "апреля", "мая",
            "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря")
    try {
        val valid = tes.indexOf(wrL[1])
        if (valid == -1) return ""
        wrL[0] = twoDigitStr(wrL[0].toInt())
        wrL[1] = twoDigitStr(valid + 1)
        return wrL.joinToString(separator = ".")
    } catch (e: Exception) {
        return ""
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateDigitToStr(digital: String): String {
    val wrL = digital.split(".").toMutableList()
    val tes = listOf<String>("января", "февраля", "марта", "апреля", "мая",
            "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря")
    try {
        if (wrL.count() != 3) return ""
        wrL[0] = if (wrL[0][0] == '0') wrL[0].substring(1) else wrL[0]
        wrL[1] = tes[wrL[1].toInt() - 1]
        return wrL.joinToString(separator = " ")
    } catch (e: Exception) {
        return ""
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    if (!phone.matches(Regex("""^[ \d\+\-\(\)]*$"""))) return ""
    return phone.replace(Regex("""[ \-\(\)]"""), "")
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    if (!jumps.matches(Regex("""^[ \d\-%]*$"""))) return -1
    if (jumps.matches(Regex("""^[ \-%]*$"""))) return -1
    val j = jumps.replace(Regex("""[\-%]"""), "").split(Regex(""" """)).filter { a -> !a.isEmpty() }.map { it.toInt() }
    return j.max()!!
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    if (!jumps.matches(Regex("""^[ \d\-%\+]*$"""))) return -1
    val wr = jumps.split(' ')
    var res = -1
    try {
        for (i in 0 until wr.count() step 2) {
            if (wr[i + 1].contains('+')) {
                val tmp = wr[i].toInt()
                if (res < tmp)
                    res = tmp
            }
        }
    } catch (e: Exception) {
        return -1
    }
    return res
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val wr = expression.split(' ')
    var mg = 1
    for (s in wr) {
        if (s.matches(Regex("""(\d+)+"""))) {
            mg++; if (mg > 2 || mg < 0) throw IllegalArgumentException()
        } else if (s.matches(Regex("""[\+-]+"""))) {
            mg--; if (mg > 2 || mg < 0) throw IllegalArgumentException()
        } else throw IllegalArgumentException()
    }
    var res = wr[0].toInt()
    for (s in 1 until wr.count() step 2) {
        if (wr[s].equals("+")) {
            res += wr[s + 1].toInt()
        } else {
            res -= wr[s + 1].toInt()
        }
    }
    return res
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val res = mutableListOf<String>()
    var dup = ""
    for (s in str.toLowerCase().split(' ')) {
        if (s !in res) res.add(s)
        else {
            dup = s; break
        }
    }
    if (dup.isEmpty()) return -1
    else return str.indexOf(" " + dup + "") + 1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62.5; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть положительными
 */
fun mostExpensive(description: String): String {
    val matchResult = Regex("""(([а-яА-Я]* ([0-9]*\.?[0-9]+));?)""").findAll(description)
    if (matchResult.count() == 0) return ""
    val res = mutableListOf<Pair<String, Double>>()
    for (m in matchResult) {
        val tmp = m.value.split(' ')
        res.add(Pair(tmp[0], tmp[1].replace(";", "").toDouble()))
    }
    return res.maxBy { it.second }!!.first
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    var res = -1
    var tmp = 0
    if (roman.isEmpty()) res = 0
    if (roman.startsWith("M")) {
        tmp = fromRoman(roman.substring(1)); return if (tmp != -1) 1000 + tmp else -1
    }
    if (roman.startsWith("CM")) {
        tmp = fromRoman(roman.substring(2)); return if (tmp != -1) 900 + tmp else -1
    }
    if (roman.startsWith("D")) {
        tmp = fromRoman(roman.substring(1)); return if (tmp != -1) 500 + tmp else -1
    }
    if (roman.startsWith("CD")) {
        tmp = fromRoman(roman.substring(2)); return if (tmp != -1) 400 + tmp else -1
    }
    if (roman.startsWith("C")) {
        tmp = fromRoman(roman.substring(1)); return if (tmp != -1) 100 + tmp else -1
    }
    if (roman.startsWith("XC")) {
        tmp = fromRoman(roman.substring(2)); return if (tmp != -1) 90 + tmp else -1
    }
    if (roman.startsWith("L")) {
        tmp = fromRoman(roman.substring(1)); return if (tmp != -1) 50 + tmp else -1
    }
    if (roman.startsWith("XL")) {
        tmp = fromRoman(roman.substring(2)); return if (tmp != -1) 40 + tmp else -1
    }
    if (roman.startsWith("X")) {
        tmp = fromRoman(roman.substring(1)); return if (tmp != -1) 10 + tmp else -1
    }
    if (roman.startsWith("IX")) {
        tmp = fromRoman(roman.substring(2)); return if (tmp != -1) 9 + tmp else -1
    }
    if (roman.startsWith("V")) {
        tmp = fromRoman(roman.substring(1)); return if (tmp != -1) 5 + tmp else -1
    }
    if (roman.startsWith("IV")) {
        tmp = fromRoman(roman.substring(2)); return if (tmp != -1) 4 + tmp else -1
    }
    if (roman.startsWith("I")) {
        tmp = fromRoman(roman.substring(1)); return if (tmp != -1) 1 + tmp else -1
    }
    return res
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val res = MutableList(cells, { 0 })
    if (commands.isEmpty()) return res
    if (!commands.matches(Regex("""[\[><\+\-\] ]+"""))) throw IllegalArgumentException()
    if (commands.count { it == '[' } != commands.count { it == ']' }) throw IllegalArgumentException()
    val jAdr = mutableListOf<Pair<Int, Int>>()
    var rec = 0
    for (i in 0 until commands.length) {
        if (commands[i] == '[') {
            rec++
            var tmp = i + 1
            while (rec > 0 && tmp < commands.length) {
                if (commands[tmp] == '[') rec++
                if (commands[tmp] == ']') rec--
                tmp++
            }
            if (rec != 0) throw IllegalArgumentException()
            jAdr.add(Pair(i, tmp - 1))
        }
    }
    //'+' - mul, '-' - dec, ' ' - nop, '[' - jz, ']' - rnz
    var curC = cells / 2
    var pc = 0
    var lim = 0
    while (lim < limit && pc < commands.length) {
        when (commands[pc]) {
            '+' -> res[curC]++
            '-' -> res[curC]--
            '>' -> curC++
            '<' -> curC--
            '[' ->
                if (res[curC] == 0) {
                    pc = jAdr.find { it.first == pc }?.second!!
                }
            ']' ->
                if (res[curC] != 0) {
                    pc = jAdr.find { it.second == pc }?.first!!
                }
            ' ' -> {
            }
            else -> throw IllegalArgumentException()
        }
        if (curC !in 0 until cells) throw IllegalStateException()
        lim++; pc++
    }
    return res
}