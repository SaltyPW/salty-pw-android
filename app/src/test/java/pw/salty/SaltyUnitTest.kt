package pw.salty

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SaltyUnitTest {
    @Test
    fun testNormalOperation() {
        val salty = Salty()
        assertEquals("GH.O+5\\[qt", salty.hash("123", "456"))
        assertEquals("L;A7qhfHS6", salty.hash("abc", "def"))
    }

    @Test
    fun testShortHash() {
        val salty = Salty()
        assertEquals("d/f4.mNb,", salty.hash("123", "4"))
    }

    @Test
    fun testEmpty() {
        var salty = Salty()
        assertEquals("", salty.hash("", ""))
    }
}