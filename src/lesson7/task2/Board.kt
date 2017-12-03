package lesson7.task2

import lesson7.task1.Cell
import lesson7.task1.Matrix
import lesson7.task1.createMatrix

fun getGoalBoard(w: Int, h: Int): Matrix<Int> {
    val range = w * h
    val goal = createMatrix(h, w, 0)
    var n = 1
    for (y in 0 until h)
        for (x in 0 until w) {
            goal[y, x] = if (n < range) n else 0
            n++
        }
    return goal
}

fun flattenBoard(b: Matrix<Int>): List<Int> {
    val res = mutableListOf<Int>()
    var cur = 0
    for (y in 0 until b.height)
        for (x in 0 until b.width) {
            cur = cur * 10 + b[y, x]
            res.add(cur)
        }
    return res
}

fun getZeroPosition(board: Matrix<Int>): Cell {
    for (y in 0 until board.height) {
        for (x in 0 until board.width) {
            if (board[y, x] == 0) {
                return Cell(y, x)
            }
        }
    }
    return Cell(-1, -1)
}


fun newBoardFromPosition(reference: Matrix<Int>, position: Cell, oldPosition: Cell): Board {
    val tiles = reference.copy()
    // Replace the old 0 with the number being moved before assigning 0 to its new position
    tiles[oldPosition] = tiles[position]
    tiles[position] = 0
    return Board(tiles, position)
}

class Board {

    val board: Matrix<Int>
    val goal: Matrix<Int>
    val zeroPosition: Cell
    val height: Int
    val width: Int

    constructor(tiles: Matrix<Int>, position: Cell? = null) {
        board = tiles
        height = tiles.height
        width = tiles.width
        zeroPosition = position ?: getZeroPosition(this.board)
        goal = getGoalBoard(this.width, this.height)
    }

    constructor(tiles: Board, position: Cell? = null) {
        board = tiles.board
        height = tiles.height
        width = tiles.width
        zeroPosition = position ?: getZeroPosition(this.board)
        goal = getGoalBoard(this.width, this.height)
    }

    override fun toString(): String {
        val res = StringBuilder()
        for (y in 0 until height) {
            for (x in 0 until width)
                res.append("${board[y, x]} ")
        }

        return res.toString()
    }

    fun equals(other: Board): Boolean = this.toString() == other.toString()

    fun hamming(moves: Int = 0): Int {
        // 1 should be at the 0 index, 2 at 1, etc.
        var res = 0
        for ((n, idx) in flattenBoard(this.board).withIndex()) {
            if (n != idx + 1) {
                res++
            }
        }
        return res + moves;
    }

    /**
     * Manhattan priority function.
     * The sum of the distances (vertical + horizontal) of blocks to their goal positions, plus the number of moves made so far.
     * @param {Int} moves The moves taken so far.
     * @returns {Int} The Manhattan priority value.
     */
    fun manhattan(moves: Int = 0): Int {
        val numTiles = this.height * this.width
        var priority = moves

        for (i in 0 until numTiles) {
            // fst - board, scd - goal
            var coords = Pair(Cell(-1, -1), Cell(-1, -1))
            // Iterate through rows and see if the value exists
            for (y in 0 until this.board.height) {
                val boardCoord = this.board.indexOf(i)
                val goalCoord = this.goal.indexOf(i)
                if (boardCoord != Cell(-1, -1) && goalCoord != Cell(-1, -1)) {
                    coords = Pair(boardCoord, goalCoord)
                } else {
                    break
                }
            }
            priority += Math.abs(coords.first.column - coords.second.column) +
                    Math.abs(coords.first.row - coords.second.row)
        }

        return priority;
    }

    fun getPriority(moves: Int): Int {
        val hamming = this.hamming(moves);
        val manhattan = this.manhattan(moves);
        return if (hamming > manhattan) manhattan else hamming
    }

    fun getNeighbors(): List<Board> {
        val result = mutableListOf<Board>()

        // Work from right to left since most puzzles favour that direction
        for (i in 1 downTo -2 step 2) {
            // Check horizontally first...
            if (this.zeroPosition.column + i >= 0 && this.zeroPosition.column + i < this.width) {
                result.add(newBoardFromPosition(this.board,
                        Cell(this.zeroPosition.row, this.zeroPosition.column + i), this.zeroPosition));
            }

            // Now vertically...
            if (this.zeroPosition.row + i >= 0 && this.zeroPosition.row + i < this.height) {
                result.add(newBoardFromPosition(this.board, Cell(
                        this.zeroPosition.row + i, this.zeroPosition.column
                ), this.zeroPosition));
            }
        }

        return result;
    }
}