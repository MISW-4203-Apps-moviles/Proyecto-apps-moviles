package com.miso.vinilos.e2e.utils

import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

object TestUtils {
    inline fun <reified T : Enum<T>> getRandomEnumProperty(enumClass: KClass<T>, propertyName: String): String {
        // Get all enum entries
        val values = enumClass.java.enumConstants

        // Select a random entry
        val randomValue = values?.get(Random.nextInt(values.size))

        // Get the property by name
        val property = enumClass.memberProperties.find { it.name == propertyName }
            ?: throw IllegalArgumentException("Property $propertyName does not exist on $enumClass")

        // Return the property value of the random entry
        return randomValue?.let { property.get(it) } as? String
            ?: throw IllegalArgumentException("Property $propertyName is not of type String")
    }
}