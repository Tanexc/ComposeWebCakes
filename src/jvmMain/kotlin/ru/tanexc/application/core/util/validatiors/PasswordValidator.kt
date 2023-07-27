package ru.tanexc.application.core.util.validatiors

object PasswordValidator {
    private const val minLength = 8
    private const val minDigitsCount = 1

    fun String.isValid() = this.length >= minLength && this.count(Char::isDigit) >= minDigitsCount
}