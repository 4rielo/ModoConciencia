package org.ascarafia.modoconciencia.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationIndex

@Serializable
object MainScreenIndex: NavigationIndex

@Serializable
object LogListScreenIndex: NavigationIndex

@Serializable
data class LogDetailScreenIndex(val logId: String): NavigationIndex

@Serializable
data class CreateLogScreenIndex(val logId: String? = null): NavigationIndex