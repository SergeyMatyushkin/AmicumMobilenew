package com.example.utils

import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test

class AssistantTest {

    @Test
    fun toJson_TrueWorks_Equils() {
        val jsonFinal = "[1,2,3]"
        val source = arrayOf(1, 2, 3)
        val jsonResult = Assistant.toJson(source)
        Assert.assertEquals(jsonFinal, jsonResult)
    }

    @Test
    fun convertDateToFront_Equils() {
        val dateFinal = "01.12.2021"
        val source = "2021-12-01"
        val jsonResult = Assistant.convertDateToFront(source)
        Assert.assertEquals(dateFinal, jsonResult)
    }

    @Test
    fun convertDateToFront_notEquils() {
        val dateFinal = "01.12.202"
        val source = "2021-12-01"
        val jsonResult = Assistant.convertDateToFront(source)
        Assert.assertNotEquals(dateFinal, jsonResult)
    }
}