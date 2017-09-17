package lesson5.task1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Tests {
    @Test
    @Tag("Example")
    fun timeStrToSeconds() {
        assertEquals(36000, timeStrToSeconds("10:00:00"))
        assertEquals(41685, timeStrToSeconds("11:34:45"))
        assertEquals(86399, timeStrToSeconds("23:59:59"))
    }

    @Test
    @Tag("Example")
    fun twoDigitStr() {
        assertEquals("00", twoDigitStr(0))
        assertEquals("09", twoDigitStr(9))
        assertEquals("10", twoDigitStr(10))
        assertEquals("99", twoDigitStr(99))
    }

    @Test
    @Tag("Example")
    fun timeSecondsToStr() {
        assertEquals("10:00:00", timeSecondsToStr(36000))
        assertEquals("11:34:45", timeSecondsToStr(41685))
        assertEquals("23:59:59", timeSecondsToStr(86399))
    }

    @Test
    @Tag("Normal")
    fun dateStrToDigit() {
        assertEquals("15.07.2016", dateStrToDigit("15 июля 2016"))
        assertEquals("", dateStrToDigit("3 мартобря 1918"))
        assertEquals("18.11.2018", dateStrToDigit("18 ноября 2018"))
        assertEquals("", dateStrToDigit("23"))
        assertEquals("03.04.2011", dateStrToDigit("3 апреля 2011"))
    }

    @Test
    @Tag("Normal")
    fun dateDigitToStr() {
        assertEquals("15 июля 2016", dateDigitToStr("15.07.2016"))
        assertEquals("", dateDigitToStr("01.02.20.19"))
        assertEquals("", dateDigitToStr("28.00.2000"))
        assertEquals("3 апреля 2011", dateDigitToStr("03.04.2011"))
        assertEquals("", dateDigitToStr("ab.cd.ef"))
    }

    @Test
    @Tag("Normal")
    fun flattenPhoneNumber() {
        assertEquals("+79211234567", flattenPhoneNumber("+7 (921) 123-45-67"))
        assertEquals("123456798", flattenPhoneNumber("12 --  34- 5 -- 67 -98"))
        assertEquals("", flattenPhoneNumber("ab-123"))
        assertEquals("+12345", flattenPhoneNumber("+12 (3) 4-5"))
        assertEquals("", flattenPhoneNumber("134_+874"))
    }

    @Test
    @Tag("Normal")
    fun bestLongJump() {
        assertEquals(717, bestLongJump("706 % - 717 - 703"))
        assertEquals(-1, bestLongJump("% - - % -"))
        assertEquals(754, bestLongJump("700 717 707 % 754"))
        assertEquals(-1, bestLongJump("700 + 700"))

    }

    @Test
    @Tag("Hard")
    fun bestHighJump() {
        assertEquals(226, bestHighJump("226 +"))
        assertEquals(-1, bestHighJump("???"))
        assertEquals(230, bestHighJump("220 + 224 %+ 228 %- 230 + 232 %%- 234 %"))
        assertEquals(995095371, bestHighJump("43596743 %%- 416245493 %+ 0 + 834855272 %%+ 1 %+ 349588239 %+ 901852777 %- 147483648 %+ 147483648 %- 528963993 %- 147483648 %%+ 531552873 %- 147483648 %- 10969929 %- 534990776 %+ 773962086 %%+ 1 %%- 900391447 + 147483648 %%- 697418101 %%+ 109716038 %%- 402254796 + 83711397 %- 43974330 %%- 147483648 %- 542375411 %+ 488864449 %- 239009420 + 0 %+ 147483648 %%- 557335869 %%- 415541231 %+ 147483648 %- 0 + 897249025 + 37379035 %+ 1 %- 494324567 + 297509502 %%- 948111518 + 829779775 %+ 995095371 %+ 462634773 + 837565597 %+ 0 %%- 409568316 %%+ 1 %- 147483648 %+ 147483647 %+ 57315969 %- 455275629 + 147483648 + 147483647 %+ 0 %%- 147483647 %- 147483647 + 0 %+ 1 %%- 917033340 %- 147483647 %%- 1 %%- 147483647 %+ 0 %- 502625923 + 147483647 %+ 147483647 %%+ 50200807 + 0 %+ 192164914 %+ 203873053 %+ 320276716 + 1 %- 514933219 + 1 %- 147483648 + 147483648 %- 167257942 %%- 147483648 %- 147483648 %%- 1 %%- 522334498 %%- 147483648 %+ 260569437 + 171149482 %%- 149567452 %%+ 147483647 %- 929576009 %- 39078892 + 1 %%- 147483648 %%- 0 %- 837421476 + 59266801 %%+ 748665644 %%- 0 %%- 147483648 + 324685450 %- 0 %%- 147483647 %%- 425074197 %%+ 1 %- 147483648 %- 279754218 + 147483648 %- 50168480 %%+ 335058672 %+ 1 %%- 1 %%+ 205965019 %%- 147483648 %%- 850093430 %+ 147483647 %%- 147483648 %+ 0 %+ 555176674 %+ 539488170 %- 0 %- 419217833 %- 312228996 %- 1 %+ 906465297 %- 147483647 %- 147483648 %%- 0 + 721710709 + 147483647 %%- 147483648 %- 553519152 %- 0 %%+ 934779947 %+ 147483647 %+ 0 + 0 + 147483647 %+ 403973431 %- 782050672 %+ 668246811 %%- 147483648 + 147483647 %- 888338466 %- 1 + 1 %%- 992941485 + 325309333 %+ 147483647 %+ 1 %- 0 %- 147483648 + 862778109 %%- 147483648 %%+ 0 %%- 757483518 %%- 97226553 %+ 859454350 %+ 1 %+ 26718181 %+ 1 + 1 + 272761962 %+ 792372048 %- 0 %+ 167850832 %%+ 693225124 %%+ 700367354 %%- 0 %%- 189337136 %%- 917460470 %+ 147483648 %+ 929619830 %%- 1 %+ 147483647 %- 147483647 %+ 192143730 %- 147483647 %- 147483647 %- 147483648 %- 902787241 %+ 979476227 %- 147483648 %+ 147483647 %%- 1 %- 922722431 %%- 1 %- 147483648 %+ 676439043 %+ 147483648 %%+ 147483648 + 720697418 %+ 147483648 %- 147483648 + 147483647 %- 0 + 1 + 797474663 %%- 308564438 + 1 %%+ 220936815 %+ 66310325 %%- 427103532 %- 530822798 %+ 147483648 %+ 148854094 %+ 0 %+ 1 %- 1 %%+ 1 %%- 68457051 %+ 157690131 %+ 19249485 %%+ 147483648 %+ 1 %%- 0 %- 788743956 %+ 147483647 %+ 147483648 + 147483648 %%- 572521424 + 147483647 %%- 364020094 %%- 147483647 %%+ 732655701 %%- 223913199 + 453260362 %%- 638537171 + 1 %+ 471301998 %+"))
    }

    @Test
    @Tag("Hard")
    fun plusMinus() {
        assertEquals(0, plusMinus("0"))
        assertEquals(4, plusMinus("2 + 2"))
        assertEquals(6, plusMinus("2 + 31 - 40 + 13"))
        assertEquals(-1, plusMinus("0 - 1"))
    }

    @Test
    @Tag("Hard")
    fun firstDuplicateIndex() {
        assertEquals(-1, firstDuplicateIndex("Привет"))
        assertEquals(9, firstDuplicateIndex("Он пошёл в в школу"))
        assertEquals(7, firstDuplicateIndex("Яблоко упало на ветку с ветки оно упало на на землю"))
        assertEquals(9, firstDuplicateIndex("Мы пошли прямо Прямо располагался магазин"))
    }

    @Test
    @Tag("Hard")
    fun mostExpensive() {
        assertEquals("", mostExpensive(""))
        assertEquals("Курица", mostExpensive("Хлеб 39.9; Молоко 62.5; Курица 184.0; Конфеты 89.9"))
        assertEquals("Вино", mostExpensive("Вино 255.0"))

    }

    @Test
    @Tag("Hard")
    fun fromRoman() {
        assertEquals(1, fromRoman("I"))
        assertEquals(3000, fromRoman("MMM"))
        assertEquals(1978, fromRoman("MCMLXXVIII"))
        assertEquals(694, fromRoman("DCXCIV"))
        assertEquals(49, fromRoman("XLIX"))
        assertEquals(-1, fromRoman("Z"))
    }

    @Test
    @Tag("Impossible")
    fun computeDeviceCells() {
        assertEquals(listOf(0, 0, 0, 0, 0, 1, 1, 1, 1, 1), computeDeviceCells(10, "+>+>+>+>+", 10000))
        assertEquals(listOf(-1, -1, -1, -1, -1, 0, 0, 0, 0, 0), computeDeviceCells(10, "<-<-<-<-<-", 10000))
        assertEquals(listOf(1, 1, 1, 1, 1, 0, 0, 0, 0, 0), computeDeviceCells(10, "- <<<<< +[>+]", 10000))
        assertEquals(listOf(0, 8, 7, 6, 5, 4, 3, 2, 1, 0, 0),
                computeDeviceCells(11, "<<<<< + >>>>>>>>>> --[<-] >+[>+] >++[--< <[<] >+[>+] >++]", 10000))

        assertEquals(listOf(0, 0, 0, 0, 0, 1, 1, 0, 0, 0), computeDeviceCells(10, "+>+>+>+>+", 4))
        assertEquals(listOf(0, 0, -1, -1, -1, 0, 0, 0, 0, 0), computeDeviceCells(10, "<-<-<-<-<-", 6))
        assertEquals(listOf(1, 1, 1, 0, 0, -1, 0, 0, 0, 0), computeDeviceCells(10, "- <<<<< +[>+]", 17))
        assertEquals(listOf(0, 6, 5, 4, 3, 2, 1, 0, -1, -1, -2),
                computeDeviceCells(11, "<<<<< + >>>>>>>>>> --[<-] >+[>+] >++[--< <[<] >+[>+] >++]", 256))
    }
}