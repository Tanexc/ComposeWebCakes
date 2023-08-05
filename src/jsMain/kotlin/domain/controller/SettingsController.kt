package domain.controller

import androidx.compose.material3.ColorScheme
import domain.interfaces.StringResources
import domain.model.Theme
import org.koin.core.component.KoinComponent

interface SettingsController: KoinComponent {

    val theme: ColorScheme
    fun updateTheme(theme: Theme)

    val locale: StringResources
    fun updateLocale(locale: StringResources)



}