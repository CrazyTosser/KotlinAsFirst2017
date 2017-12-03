package fin.task1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Tests {
    @Test
    fun mulKompl() {
        assertEquals(Compl(-4900, 5950), mulKompl("2 + 4i;5;7i;45 - 20i"))
        assertEquals(Compl("-5 - i"), mulKompl("2 + 3i;-1 + i"))
        assertEquals(Compl(2, 0), mulKompl("2"))
        assertEquals(Compl(-80, 0), mulKompl("2;5;8;-1"))
        assertEquals(Compl(-80, 0), mulKompl("2i;5i;8i;- 1i"))
        assertEquals(Compl(0, 1202), mulKompl("1202i"))

    }

}