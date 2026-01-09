package com.example.learnify.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.learnify.ui.viewModels.CourseViewModel

@Composable
fun CourseGridScreen(
    cardWeight: Int = 245,
    cardHeight: Int = 280,
    query: String,
    isSearch: Boolean = false,
    viewModel: CourseViewModel,
    navController: NavHostController
) {
    val categoryKey = viewModel.detectCategoryKeyFromQuery(query)

    val courses by if (isSearch) {
        viewModel.searchResults.observeAsState(emptyList())
    } else {
        viewModel.generalCoursesByCategory(categoryKey).observeAsState(emptyList())
    }

    val isSearchLoading by viewModel.isSearchLoading.observeAsState(false)
    val isGeneralLoading by viewModel.isGeneralLoading.observeAsState(false)

    val isLoading = if (isSearch) isSearchLoading else isGeneralLoading

    val searchError by viewModel.searchError.observeAsState(null)
    val generalError by viewModel.generalError.observeAsState(null)

    val error = if (isSearch) searchError else generalError

    LaunchedEffect(query) {
        when {
            isSearch -> viewModel.searchCoursesDirect(query)
            query.isNotBlank() -> viewModel.searchCourses(query)
        }
    }

    when {
        isLoading -> Loading()
        error != null -> UnknownError()
        !isLoading && courses.isEmpty() -> NoCoursesUiError()
        else -> {
            val rows = (courses.size + 1) / 2
            val gridHeight = (cardHeight * rows) + (16.dp.value * (rows - 1))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(gridHeight.dp)
            ) {
                items(courses) { course ->
                    CourseCard(
                        course = course,
                        cardWeight = cardWeight,
                        cardHeight = cardHeight,
                        onCourseClick = { selectedCourse ->
                            navController.navigate("courseDetails/${selectedCourse.id}")
                        }
                    )
                }
            }
        }
    }
}
