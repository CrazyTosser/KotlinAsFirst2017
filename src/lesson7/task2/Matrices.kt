@file:Suppress("UNUSED_PARAMETER")

package lesson7.task2

import lesson7.task1.Cell
import lesson7.task1.Matrix
import lesson7.task1.createMatrix

// Все задачи в этом файле требуют наличия реализации интерфейса "Матрица" в Matrix.kt

/**
 * Пример
 *
 * Транспонировать заданную матрицу matrix.
 * При транспонировании строки матрицы становятся столбцами и наоборот:
 *
 * 1 2 3      1 4 6 3
 * 4 5 6  ==> 2 5 5 2
 * 6 5 4      3 6 4 1
 * 3 2 1
 */
fun <E> transpose(matrix: Matrix<E>): Matrix<E> {
    if (matrix.width < 1 || matrix.height < 1) return matrix
    val result = createMatrix(height = matrix.width, width = matrix.height, e = matrix[0, 0])
    for (i in 0 until matrix.width) {
        for (j in 0 until matrix.height) {
            result[i, j] = matrix[j, i]
        }
    }
    return result
}

/**
 * Пример
 *
 * Сложить две заданные матрицы друг с другом.
 * Складывать можно только матрицы совпадающего размера -- в противном случае бросить IllegalArgumentException.
 * При сложении попарно складываются соответствующие элементы матриц
 */
operator fun Matrix<Int>.plus(other: Matrix<Int>): Matrix<Int> {
    if (width != other.width || height != other.height) throw IllegalArgumentException()
    if (width < 1 || height < 1) return this
    val result = createMatrix(height, width, this[0, 0])
    for (i in 0 until height) {
        for (j in 0 until width) {
            result[i, j] = this[i, j] + other[i, j]
        }
    }
    return result
}

/**
 * Сложная
 *
 * Заполнить матрицу заданной высоты height и ширины width
 * натуральными числами от 1 до m*n по спирали,
 * начинающейся в левом верхнем углу и закрученной по часовой стрелке.
 *
 * Пример для height = 3, width = 4:
 *  1  2  3  4
 * 10 11 12  5
 *  9  8  7  6
 */
fun generateSpiral(height: Int, width: Int): Matrix<Int> {
    var value = 1
    val res = createMatrix(height, width, 0)
    var k = 0
    while (value <= height * width) {
        k++
        for (j in k - 1 until width - k + 1) {
            if (value > height * width) break
            res[Cell(k - 1, j)] = value++
        }
        for (j in k until height - k + 1) {
            if (value > height * width) break
            res[Cell(j, width - k)] = value++
        }
        for (j in width - k - 1 downTo k - 1) {
            if (value > height * width) break
            res[Cell(height - k, j)] = value++
        }
        for (j in height - k - 1 downTo k) {
            if (value > height * width) break
            res[Cell(j, k - 1)] = value++
        }
    }
    return res
}

/**
 * Сложная
 *
 * Заполнить матрицу заданной высоты height и ширины width следующим образом.
 * Элементам, находящимся на периферии (по периметру матрицы), присвоить значение 1;
 * периметру оставшейся подматрицы – значение 2 и так далее до заполнения всей матрицы.
 *
 * Пример для height = 5, width = 6:
 *  1  1  1  1  1  1
 *  1  2  2  2  2  1
 *  1  2  3  3  2  1
 *  1  2  2  2  2  1
 *  1  1  1  1  1  1
 */
fun generateRectangles(height: Int, width: Int): Matrix<Int> {
    var value = 1
    val res = createMatrix(height, width, 0)
    var k = 0
    while (value <= height * width) {
        k++
        for (j in k - 1 until width - k + 1) {
            if (value > height * width) break
            res[Cell(k - 1, j)] = k
            value++
        }
        for (j in k until height - k + 1) {
            if (value > height * width) break
            res[Cell(j, width - k)] = k
            value++
        }
        for (j in width - k - 1 downTo k - 1) {
            if (value > height * width) break
            res[Cell(height - k, j)] = k
            value++
        }
        for (j in height - k - 1 downTo k) {
            if (value > height * width) break
            res[Cell(j, k - 1)] = k
            value++
        }
    }
    return res
}

/**
 * Сложная
 *
 * Заполнить матрицу заданной высоты height и ширины width диагональной змейкой:
 * в левый верхний угол 1, во вторую от угла диагональ 2 и 3 сверху вниз, в третью 4-6 сверху вниз и так далее.
 *
 * Пример для height = 5, width = 4:
 *  1  2  4  7
 *  3  5  8 11
 *  6  9 12 15
 * 10 13 16 18
 * 14 17 19 20
 */
fun generateSnake(height: Int, width: Int): Matrix<Int> {
    val res = createMatrix(height, width, 0)
    var value = 1
    var row = 0
    var col = 0
    var curRow = 0
    var curCol = 0
    while (value <= height * width) {
        res[row, col] = value
        if (col == 0 || row == height - 1) {
            if (curCol == width - 1) curRow++
            else if (curCol < width - 1) curCol++
        }
        row++
        col--
        if (col == -1 || row == height) {
            row = curRow
            col = curCol
        }
        value++
    }
    return res
}

/**
 * Средняя
 *
 * Содержимое квадратной матрицы matrix (с произвольным содержимым) повернуть на 90 градусов по часовой стрелке.
 * Если height != width, бросить IllegalArgumentException.
 *
 * Пример:    Станет:
 * 1 2 3      7 4 1
 * 4 5 6      8 5 2
 * 7 8 9      9 6 3
 */
fun <E> rotate(matrix: Matrix<E>): Matrix<E> {
    if (matrix.height != matrix.width) throw IllegalArgumentException()
    val res = createMatrix(matrix.height, matrix.width, matrix[0, 0])
    for (i in 0 until matrix.width) {
        for (j in 0 until matrix.width) {
            res[Cell(j, matrix.width - i - 1)] = matrix[Cell(i, j)]
        }
    }
    return res
}

/**
 * Сложная
 *
 * Проверить, является ли квадратная целочисленная матрица matrix латинским квадратом.
 * Латинским квадратом называется матрица размером n x n,
 * каждая строка и каждый столбец которой содержат все числа от 1 до n.
 * Если height != width, вернуть false.
 *
 * Пример латинского квадрата 3х3:
 * 2 3 1
 * 1 2 3
 * 3 1 2
 */
fun isLatinSquare(matrix: Matrix<Int>): Boolean {
    if (matrix.height != matrix.width) return false
    val numEtal = 1..matrix.width
    (0 until matrix.height).forEach { i ->
        val numR = mutableListOf<Int>()
        val numC = mutableListOf<Int>()
        (0 until matrix.width).forEach { j ->
            if (!numR.contains(matrix[i, j])) numR.add(matrix[i, j])
            else return false
            if (!numC.contains(matrix[j, i])) numC.add(matrix[j, i])
            else return false
        }
        numR.sort()
        numC.sort()
        numEtal
                .filter { n -> numR.count { it == n } != 1 }
                .forEach { return false }
        numEtal
                .filter { n -> numC.count { it == n } != 1 }
                .forEach { return false }
    }
    return true
}

/**
 * Средняя
 *
 * В матрице matrix каждый элемент заменить суммой непосредственно примыкающих к нему
 * элементов по вертикали, горизонтали и диагоналям.
 *
 * Пример для матрицы 4 x 3: (11=2+4+5, 19=1+3+4+5+6, ...)
 * 1 2 3       11 19 13
 * 4 5 6  ===> 19 31 19
 * 6 5 4       19 31 19
 * 3 2 1       13 19 11
 *
 * Поскольку в матрице 1 х 1 примыкающие элементы отсутствуют,
 * для неё следует вернуть как результат нулевую матрицу:
 *
 * 42 ===> 0
 */
fun sumNeighbours(matrix: Matrix<Int>): Matrix<Int> {
    val dv = listOf(Pair(1, 0), Pair(0, 1), Pair(-1, 0), Pair(0, -1), Pair(-1, -1), Pair(-1, 1), Pair(1, -1), Pair(1, 1))
    val res = createMatrix(matrix.height, matrix.width, 0)
    for (row in 0 until res.height) {
        for (col in 0 until res.width) {
            var sum = 0
            dv.filter {
                it.first + row in 0 until res.height &&
                        it.second + col in 0 until res.width
            }
                    .forEach { sum += matrix[Cell(row + it.first, col + it.second)] }
            res[Cell(row, col)] = sum
        }
    }
    return res
}

/**
 * Средняя
 *
 * Целочисленная матрица matrix состоит из "дырок" (на их месте стоит 0) и "кирпичей" (на их месте стоит 1).
 * Найти в этой матрице все ряды и колонки, целиком состоящие из "дырок".
 * Результат вернуть в виде Holes(rows = список дырчатых рядов, columns = список дырчатых колонок).
 * Ряды и колонки нумеруются с нуля. Любой из спискоов rows / columns может оказаться пустым.
 *
 * Пример для матрицы 5 х 4:
 * 1 0 1 0
 * 0 0 1 0
 * 1 0 0 0 ==> результат: Holes(rows = listOf(4), columns = listOf(1, 3)): 4-й ряд, 1-я и 3-я колонки
 * 0 0 1 0
 * 0 0 0 0
 */
fun findHoles(matrix: Matrix<Int>): Holes {
    val colL = MutableList(matrix.width) { true }
    val rowL = MutableList(matrix.height) { true }
    (0 until matrix.height).forEach { row ->
        (0 until matrix.width).forEach { col ->
            rowL[row] = rowL[row] && matrix[row, col] == 0
        }
    }
    (0 until matrix.width).forEach { col ->
        (0 until matrix.height).forEach { row ->
            colL[col] = colL[col] && matrix[row, col] == 0
        }
    }
    val cRes = mutableListOf<Int>()
    val rRes = mutableListOf<Int>()
    (0 until colL.size).forEach { i ->
        if (colL[i]) cRes.add(i)
    }
    (0 until rowL.size).forEach { i ->
        if (rowL[i]) rRes.add(i)
    }
    return Holes(rRes, cRes)
}

/**
 * Класс для описания местонахождения "дырок" в матрице
 */
data class Holes(val rows: List<Int>, val columns: List<Int>)

/**
 * Средняя
 *
 * В целочисленной матрице matrix каждый элемент заменить суммой элементов подматрицы,
 * расположенной в левом верхнем углу матрицы matrix и ограниченной справа-снизу данным элементом.
 *
 * Пример для матрицы 3 х 3:
 *
 * 1  2  3      1  3  6
 * 4  5  6  =>  5 12 21
 * 7  8  9     12 27 45
 *
 * К примеру, центральный элемент 12 = 1 + 2 + 4 + 5, элемент в левом нижнем углу 12 = 1 + 4 + 7 и так далее.
 */
fun sumSubMatrix(matrix: Matrix<Int>): Matrix<Int> {
    val res = createMatrix(matrix.height, matrix.width, 0)
    for (row in 0 until matrix.height) {
        for (col in 0 until matrix.width) {
            val sub = getSubMatrix(matrix, row + 1, col + 1, 0, 0)
            var sum = 0
            for (sRow in 0 until sub.height)
                for (sCol in 0 until sub.width)
                    sum += sub[sRow, sCol]
            res[row, col] = sum
        }
    }
    return res
}

fun <E> getSubMatrix(matrix: Matrix<E>, height: Int, width: Int, lx: Int, ly: Int): Matrix<E> {
    if (ly + height > matrix.height || lx + width > matrix.width)
        throw IllegalArgumentException("$ly + $height | $lx + $width || ${matrix.height} * ${matrix.width}")
    val res = createMatrix(height, width, matrix[0, 0])
    var x: Int
    (ly until ly + height).withIndex().forEach { (y, row) ->
        x = 0
        (lx until lx + width).forEach { col -> res[y, x++] = matrix[row, col] }
    }
    return res
}

fun revertBin(matrix: Matrix<Int>): Matrix<Int> {
    val res = createMatrix(matrix.height, matrix.width, 0)
    (0 until matrix.height).forEach { row ->
        (0 until matrix.width).forEach { col ->
            res[row, col] = if (matrix[row, col] == 1) 0 else 1
        }
    }
    return res
}

/**
 * Сложная
 *
 * Даны мозаичные изображения замочной скважины и ключа. Пройдет ли ключ в скважину?
 * То есть даны две матрицы key и lock, key.height <= lock.height, key.width <= lock.width, состоящие из нулей и единиц.
 *
 * Проверить, можно ли наложить матрицу key на матрицу lock (без поворота, разрешается только сдвиг) так,
 * чтобы каждой единице в матрице lock (штырь) соответствовал ноль в матрице key (прорезь),
 * а каждому нулю в матрице lock (дырка) соответствовала, наоборот, единица в матрице key (штырь).
 * Ключ при сдвиге не может выходить за пределы замка.
 *
 * Пример: ключ подойдёт, если его сдвинуть на 1 по ширине
 * lock    key
 * 1 0 1   1 0
 * 0 1 0   0 1
 * 1 1 1
 *
 * Вернуть тройку (Triple) -- (да/нет, требуемый сдвиг по высоте, требуемый сдвиг по ширине).
 * Если наложение невозможно, то первый элемент тройки "нет" и сдвиги могут быть любыми.
 */
fun canOpenLock(key: Matrix<Int>, lock: Matrix<Int>): Triple<Boolean, Int, Int> {
    val keyClean = revertBin(key)
    if (key.height == lock.height && key.width == lock.width) return Triple(keyClean == lock, 0, 0)
    (0..lock.height - key.height).forEach { row ->
        (0..lock.width - key.width)
                .filter { keyClean == getSubMatrix(lock, key.height, key.width, it, row) }
                .forEach { return Triple(true, row, it) }
    }
    return Triple(false, 0, 0)
}

/**
 * Простая
 *
 * Инвертировать заданную матрицу.
 * При инвертировании знак каждого элемента матрицы следует заменить на обратный
 */
operator fun Matrix<Int>.unaryMinus(): Matrix<Int> {
    val res = createMatrix(this.height, this.width, 0)
    for (i in 0 until this.height) {
        for (j in 0 until this.width) {
            res[i, j] = this[i, j] * -1
        }
    }
    return res
}

/**
 * Средняя
 *
 * Перемножить две заданные матрицы друг с другом.
 * Матрицы можно умножать, только если ширина первой матрицы совпадает с высотой второй матрицы.
 * В противном случае бросить IllegalArgumentException.
 * Подробно про порядок умножения см. статью Википедии "Умножение матриц".
 */
operator fun Matrix<Int>.times(other: Matrix<Int>): Matrix<Int> {
    if (this.width != other.height) throw IllegalArgumentException()
    val res = createMatrix(this.height, other.width, 0)
    for (rowA in 0 until this.height) {
        for (colB in 0 until other.width) {
            for (k in 0 until this.width) {
                res[rowA, colB] += this[rowA, k] * other[k, colB]
            }
        }
    }
    return res
}

/**
 * Сложная
 *
 * В матрице matrix размером 4х4 дана исходная позиция для игры в 15, например
 *  5  7  9  1
 *  2 12 14 15
 *  3  4  6  8
 * 10 11 13  0
 *
 * Здесь 0 обозначает пустую клетку, а 1-15 – фишки с соответствующими номерами.
 * Напомним, что "игра в 15" имеет квадратное поле 4х4, по которому двигается 15 фишек,
 * одна клетка всегда остаётся пустой. Цель игры -- упорядочить фишки на игровом поле.
 *
 * В списке moves задана последовательность ходов, например [8, 6, 13, 11, 10, 3].
 * Ход задаётся номером фишки, которая передвигается на пустое место (то есть, меняется местами с нулём).
 * Фишка должна примыкать к пустому месту по горизонтали или вертикали, иначе ход не будет возможным.
 * Все номера должны быть в пределах от 1 до 15.
 * Определить финальную позицию после выполнения всех ходов и вернуть её.
 * Если какой-либо ход является невозможным или список содержит неверные номера,
 * бросить IllegalStateException.
 *
 * В данном случае должно получиться
 * 5  7  9  1
 * 2 12 14 15
 * 0  4 13  6
 * 3 10 11  8
 */
fun <E> indexOf(cur: Matrix<Int>, el: E): Cell {
    (0 until cur.height).forEach { row ->
        (0 until cur.width)
                .filter { cur[row, it] == el }
                .forEach { return Cell(row, it) }
    }
    return Cell(-1, -1)
}

fun fifteenGameMoves(matrix: Matrix<Int>, moves: List<Int>): Matrix<Int> {
    val delta = listOf(Pair(0, 1), Pair(1, 0), Pair(-1, 0), Pair(0, -1))
    for (move in moves) {
        val curCell = indexOf(matrix, move)
        var curZero = Cell(-1, -1)
        if (curCell.row == -1) throw IllegalStateException()
        for (d in delta) {
            if (curCell.row + d.first in 0..3 && curCell.column + d.second in 0..3)
                if (matrix[Cell(curCell.row + d.first, curCell.column + d.second)] == 0)
                    curZero = Cell(curCell.row + d.first, curCell.column + d.second)
        }
        if (curZero.row == -1) throw IllegalStateException()
        matrix[curZero] = matrix[curCell]
        matrix[curCell] = 0
    }
    return matrix
}

/**
 * Очень сложная
 *
 * В матрице matrix размером 4х4 дана исходная позиция для игры в 15, например
 *  5  7  9  2
 *  1 12 14 15
 *  3  4  6  8
 * 10 11 13  0
 *
 * Здесь 0 обозначает пустую клетку, а 1-15 – фишки с соответствующими номерами.
 * Напомним, что "игра в 15" имеет квадратное поле 4х4, по которому двигается 15 фишек,
 * одна клетка всегда остаётся пустой.
 *
 * Цель игры -- упорядочить фишки на игровом поле, приведя позицию к одному из следующих двух состояний:
 *
 *  1  2  3  4          1  2  3  4
 *  5  6  7  8   ИЛИ    5  6  7  8
 *  9 10 11 12          9 10 11 12
 * 13 14 15  0         13 15 14  0
 *
 * Можно математически доказать, что РОВНО ОДНО из этих двух состояний достижимо из любой исходной позиции.
 *
 * Вернуть решение -- список ходов, приводящих исходную позицию к одной из двух упорядоченных.
 * Каждый ход -- это перемена мест фишки с заданным номером с пустой клеткой (0),
 * при этом заданная фишка должна по горизонтали или по вертикали примыкать к пустой клетке (но НЕ по диагонали).
 * К примеру, ход 13 в исходной позиции меняет местами 13 и 0, а ход 11 в той же позиции невозможен.
 *
 * Одно из решений исходной позиции:
 *
 * [8, 6, 14, 12, 4, 11, 13, 14, 12, 4,
 * 7, 5, 1, 3, 11, 7, 3, 11, 7, 12, 6,
 * 15, 4, 9, 2, 4, 9, 3, 5, 2, 3, 9,
 * 15, 8, 14, 13, 12, 7, 11, 5, 7, 6,
 * 9, 15, 8, 14, 13, 9, 15, 7, 6, 12,
 * 9, 13, 14, 15, 12, 11, 10, 9, 13, 14,
 * 15, 12, 11, 10, 9, 13, 14, 15]
 *
 * Перед решением этой задачи НЕОБХОДИМО решить предыдущую
 */
fun fifteenGameSolution(matrix: Matrix<Int>): List<Int> {
    var count = 0
    var transpos = 0
    var value = 0
    val a = MutableList(16, { 0 })

    for (i in 0..3) {
        if (i % 2 == 0) {
            for (j in 0..3) {
                value = matrix[i, j];
                if (value > 0) {
                    a[count] = value
                    count++
                }
            }
        } else {
            for (j in 3 downTo 0) {
                value = matrix[i, j]
                if (value > 0) {
                    a[count] = value
                    count++
                }
            }
        }
    }
    for (i in 0..count - 2) {
        for (j in i + 1..count - 1) {
            if (a[i] > a[j]) transpos++
        }
    }
    if (transpos % 2 != 1) {
        val tmp = matrix[matrix.indexOf(14)]
        matrix[matrix.indexOf(14)] = matrix[matrix.indexOf(15)]
        matrix[matrix.indexOf(15)] = tmp
    }
    val board = Board(matrix)
    val solv = Astar(board, Astar.SolverState(board, 0, null))
    val res = solv.solve()
    return listOf()
}
