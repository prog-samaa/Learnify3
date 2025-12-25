package com.example.learnify.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.learnify.R
import com.example.learnify.data.model.CategoryItem

@Composable
fun CategoryButton(
    selected: String?,
    onSelect: (String) -> Unit
) {
    val categories = listOf(
        CategoryItem("Marketing", painterResource(R.drawable.marketing)),
        CategoryItem("Medical", painterResource(R.drawable.medical)),
        CategoryItem("Language", painterResource(R.drawable.language)),
        CategoryItem("Engineering", painterResource(R.drawable.engineering)),
        CategoryItem("Human Development", painterResource(R.drawable.human_development)),
        CategoryItem("Programming", painterResource(R.drawable.programming))
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) { category ->
            val isSelected = selected == category.name

            var pressed by remember { mutableStateOf(false) }
            val scale by animateFloatAsState(if (pressed) 0.95f else 1f)

            Card(
                modifier = Modifier
                    .size(width = 160.dp, height = 170.dp)
                    .padding(5.dp)
                    .graphicsLayer { scaleX = scale; scaleY = scale }
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                pressed = true
                                tryAwaitRelease()
                                pressed = false
                            },
                            onTap = {
                                onSelect(category.name)
                            }
                        )
                    },
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Image(
                    painter = category.icon,
                    contentDescription = category.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
