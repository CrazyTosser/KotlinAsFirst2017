package fin.task1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Tests {
    @Test
    fun mulKompl() {
        assertEquals(Compl(-4900, 5950), mulKompl("2 + 4i;5;7i;45 - 20i"))
        assertEquals(Compl("-5 - i"), mulKompl("2 + 3i;-1 + i"))
    }

}