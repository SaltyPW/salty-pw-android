package pw.salty

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.math.max
import kotlin.math.min

class Salty {
    private val alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_=!@#\$%^&*()[]{}|;:,.<>/?`~ \\'\"+-".toCharArray()
    private val hashAlgorithm = "SHA-256"

    fun hash(basePassword:String, salt:String): String{
        if (basePassword == "" && salt == "")
            return ""
        val digest = MessageDigest.getInstance(hashAlgorithm)
        var hashValue = digest.digest((basePassword + salt).toByteArray())
        val strHashLong = toAlpha(BigInteger(1, hashValue.slice(0..7).toByteArray()))
        return strHashLong.substring(0, min(10, strHashLong.length))
    }

    fun toAlpha(value : BigInteger):String {
        if (value.equals(0.toBigInteger()))
            return "0"
        var n = value;
        var result = ""
        val base = alphabet.size.toBigInteger()
        while (n.compareTo(0.toBigInteger()) > 0) {
            result = alphabet[n.mod(base).toInt()] + result;
            n = n.divide(base)
        }
        return result;
    }
}