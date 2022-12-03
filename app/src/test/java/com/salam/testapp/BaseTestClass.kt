package com.salam.testapp

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

open class BaseTestClass {

    @Throws(Exception::class)
    fun loadJSONFromAsset(location: String): String? {
        val reader = BufferedReader(InputStreamReader(FileInputStream(location)))
        val stringBuilder = StringBuilder()
        var line = reader.readLine()
        while (line != null){
            stringBuilder.append(line)
            line = reader.readLine()
        }

        return stringBuilder.toString()
    }
}