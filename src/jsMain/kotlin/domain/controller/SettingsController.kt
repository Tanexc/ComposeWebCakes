package domain.controller

import androidx.compose.material3.ColorScheme
import domain.interfaces.StringResources
import domain.model.Theme
import presentation.style.strings.Strings

interface SettingsController {

    val theme: ColorScheme
    fun updateTheme(theme: Theme)

    val locale: StringResources
    fun updateLocale(locale: StringResources)



}