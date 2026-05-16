package com.example.rakshakavach.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PPEAvatar(
    checkedGears: Set<String>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        // Base worker icon
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Worker",
            modifier = Modifier.fillMaxSize(0.8f),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Overlay Helmet
        AnimatedVisibility(
            visible = checkedGears.contains("helmet"),
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 8.dp)
        ) {
            // Placeholder icon for Helmet
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Helmet On",
                tint = com.example.rakshakavach.ui.theme.IndustrialYellow,
                modifier = Modifier.size(32.dp)
            )
        }

        // Overlay Gloves
        AnimatedVisibility(
            visible = checkedGears.contains("gloves"),
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Build,
                contentDescription = "Gloves On",
                tint = com.example.rakshakavach.ui.theme.CautionOrange,
                modifier = Modifier.size(24.dp)
            )
        }
        
        // Boots etc. could go here...
    }
}
