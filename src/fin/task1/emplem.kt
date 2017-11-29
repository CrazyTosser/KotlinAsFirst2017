@file:Suppress("UNUSED_PARAMETER")

package fin.task1

class Compl {
    var dest = 0
    var irr = 0

    constructor(d: Int, i: Int) {
        dest = d
        irr = i
    }

    constructor(str: String) {
        val wr = str.split(" ")
        when (wr.size) {
            1 -> if (wr[0].contains('i')) {
                dest = 0
                irr = if (wr[0].replace("i", "").isEmpty()) 1
                else wr[0].replace("i", "").toInt()
            } else {
                dest = wr[0].toInt()
                irr = 0
            }
            2 ->
                if (wr[0] == "-") {
                    dest = 0
                    irr = if (wr[1].replace("i", "").isEmpty()) -1
                    else wr[1].replace("i", "").toInt() * -1
                }
            else -> {
                dest = wr[0].toInt()
                if (wr[1] == "-") {
                    irr = if (wr[2].replace("i", "").isEmpty()) -1
                    else wr[2].replace("i", "").toInt() * -1
                } else {
                    irr = if (wr[2].replace("i", "").isEmpty()) 1
                    else wr[2].replace("i", "").toInt()
                }
            }
        }
    }

    override fun equals(other: Any?): Boolean = other is Compl && dest == other.dest && irr == other.irr

    override fun hashCode(): Int {
        var result = dest
        result = 31 * result + irr
        return result
    }

    override fun toString(): String {
        val res = StringBuilder()
        if (dest != 0) res.append(dest)
        if (irr < 0) {
            res.append(" - ")
            res.append(irr * -1)
        } else {
            res.append(" + ")
            res.append(irr)
        }
        res.append("i")
        return res.toString()
    }

    operator fun times(other: Compl): Compl {
        val res: Compl
        res = if (other.irr == 0 || irr == 0) {
            if (other.irr == 0)
                Compl(dest * other.dest, irr * other.dest)
            else
                Compl(dest * other.dest, dest * other.irr)
        } else {
            Compl(dest * other.dest - irr * other.irr,
                    dest * other.irr + irr * other.dest)
        }
        return res
    }
}

fun mulKompl(str: String): Compl {
    val wr = str.split(";").filter { it.isNotEmpty() }
    var res = Compl(wr[0])
    for (i in 1 until wr.size) {
        res *= Compl(wr[i])
    }
    return res
}