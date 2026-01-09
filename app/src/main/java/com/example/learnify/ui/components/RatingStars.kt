package com.example.learnify.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.learnify.ui.theme.ActiveStar
import com.example.learnify.ui.theme.unActiveStar

@Composable
fun RatingStars(
    rating: Float,
    starSize: Int = 16
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) { index ->
            val tint =
                if (index < rating.toInt()) ActiveStar else unActiveStar

            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(starSize.dp)
            )
        }
    }
}
