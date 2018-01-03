package com.eden.orchid.utilities

import com.eden.common.json.JSONElement
import com.eden.common.util.EdenUtils
import org.apache.commons.lang3.StringUtils
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import java.util.regex.Pattern

fun String?.empty(): Boolean {
    return EdenUtils.isEmpty(this)
}

fun String?.wrap(width: Int = 80): List<String> {
    val matchList = ArrayList<String>()
    if (this != null) {
        if (!this.empty()) {
            val regex = Pattern.compile("(.{1,$width}(?:\\s|$))|(.{0,$width})", Pattern.DOTALL)
            val regexMatcher = regex.matcher(this)
            while (regexMatcher.find()) {
                val line = regexMatcher.group().trim { it <= ' ' }
                if (!EdenUtils.isEmpty(line)) {
                    matchList.add(line)
                }
            }
        }
    }

    return matchList
}

fun JSONElement?.isObject(): Boolean {
    return this != null && this.element != null && this.element is JSONObject
}

fun JSONElement?.isArray(): Boolean {
    return this != null && this.element != null && this.element is JSONArray
}

fun JSONElement?.isString(): Boolean {
    return this != null && this.element != null && this.element is String
}


// string conversions
fun String.from(mapper: String.() -> Array<String>): Array<String> {
    return mapper(this)
}

fun Array<String>.to(mapper: Array<String>.() -> String): String {
    return mapper(this)
}

fun Array<String>.with(mapper: String.() -> String): Array<String> {
    return this.map { mapper(it) }.toTypedArray()
}

// "from" mappers
fun String.camelCase(): Array<String> {
    return StringUtils.splitByCharacterTypeCamelCase(this)
}
fun String.camelCase(mapper: String.() -> String): Array<String> {
    return camelCase().with(mapper)
}

fun String.words(): Array<String> {
    return StringUtils.splitByWholeSeparator(this, null)
}
fun String.words(mapper: String.() -> String): Array<String> {
    return words().with(mapper)
}

fun String.snakeCase(): Array<String> {
    return StringUtils.splitByWholeSeparator(this, "_")
}
fun String.snakeCase(mapper: String.() -> String): Array<String> {
    return snakeCase().with(mapper)
}

fun String.dashCase(): Array<String> {
    return StringUtils.splitByWholeSeparator(this, "-")
}
fun String.dashCase(mapper: String.() -> String): Array<String> {
    return dashCase().with(mapper)
}

// "to" mappers
fun Array<String>.pascalCase(): String {
    return map { it.toLowerCase().capitalize() }.joinToString(separator = "")
}
fun List<String>.pascalCase(): String {
    return toTypedArray().pascalCase()
}
fun Array<String>.pascalCase(mapper: String.() -> String): String {
    return map(mapper).pascalCase()
}

fun Array<String>.camelCase(): String {
    return pascalCase().decapitalize()
}
fun List<String>.camelCase(): String {
    return toTypedArray().camelCase()
}
fun Array<String>.camelCase(mapper: String.() -> String): String {
    return map(mapper).camelCase()
}

fun Array<String>.words(): String {
    return joinToString(separator = " ")
}
fun List<String>.words(): String {
    return toTypedArray().words()
}
fun Array<String>.words(mapper: String.() -> String): String {
    return map(mapper).words()
}

fun Array<String>.snakeCase(): String {
    return map { it.toUpperCase() }.joinToString(separator = "_")
}
fun List<String>.snakeCase(): String {
    return toTypedArray().snakeCase()
}
fun Array<String>.snakeCase(mapper: String.() -> String): String {
    return map(mapper).snakeCase()
}

fun Array<String>.dashCase(): String {
    return joinToString(separator = "-")
}
fun List<String>.dashCase(): String {
    return toTypedArray().dashCase()
}
fun Array<String>.dashCase(mapper: String.() -> String): String {
    return map(mapper).dashCase()
}

fun Array<String>.slug(): String {
    return dashCase().toLowerCase()
}
fun List<String>.slug(): String {
    return toTypedArray().slug()
}
fun Array<String>.slug(mapper: String.() -> String): String {
    return map(mapper).slug()
}

// "with" mappers
fun String.urlSafe(): String {
    return replace("\\s+".toRegex(), "-").replace("[^\\w-_]".toRegex(), "")
}
fun String.urlSafe(mapper: String.() -> String): String {
    return urlSafe().mapper()
}

