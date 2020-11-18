package com.example.mvvmproject.util

import java.nio.charset.Charset
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class HashSecurity {
    @Throws(NoSuchAlgorithmException::class)
    fun sha256(msg: String): String {
        val HEX_CHARS = "0123456789ABCDEF"
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(msg.toByteArray())
        val result = StringBuilder(bytes.size * 2)
        bytes.forEach {
            val i = it.toInt()
            result.append(HEX_CHARS[i shr 4 and 0x0f])
            result.append(HEX_CHARS[i and 0x0f])
        }
        return result.toString()
    }
}