package org.ascarafia.modoconciencia.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NavigationDrawer(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    onClose: () -> Unit,
    drawerWidth: Dp = 250.dp,
    drawerContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {

        content()

        if( isOpen ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onClose()
                    }
            )
        }

        AnimatedVisibility(
            visible = isOpen,
            enter = slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(300)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(300)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(x = 10.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Box(
                    Modifier
                        .width(drawerWidth)
                        .fillMaxHeight()
                        .background(
                            MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(16.dp),
                ) {
                    drawerContent()
                }
            }
        }
    }
}