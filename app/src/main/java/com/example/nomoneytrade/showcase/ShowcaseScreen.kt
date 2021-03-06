package com.example.nomoneytrade.showcase

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nomoneytrade.PRODUCT_INFO_SCREEN
import com.example.nomoneytrade.entity.Product
import com.example.nomoneytrade.mvi.event.ShowcaseEvent
import com.example.nomoneytrade.ui.utils.SearchView
import com.example.nomoneytrade.ui.utils.UiUtilsLoadingFullScreen
import internal.ProductListItem
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Locale

@Composable
fun ShowcaseScreen(navController: NavController, viewModel: ShowcaseViewModel) {

    val viewState = viewModel.event.collectAsState()

    when (val event = viewState.value) {
        ShowcaseEvent.Error -> {}
        ShowcaseEvent.Loading -> {
            UiUtilsLoadingFullScreen()
        }
        is ShowcaseEvent.Success -> {
            ProductList(navController, event.products)
        }
    }
}

@Composable
private fun ProductList(navController: NavController, list: List<Product>) {

    Column(modifier = Modifier.padding(bottom = 60.dp)) {
        val textState = remember { mutableStateOf(TextFieldValue("")) }
        SearchView(textState)

        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
        ) {

            list.filter { product ->
                product.title.lowercase(Locale.getDefault()).contains(textState.value.text.lowercase(Locale.getDefault()))
            }.forEach {
                ProductListItem(product = it) {
                    val encodedUrl = URLEncoder.encode(it.imageUrl, StandardCharsets.UTF_8.toString())

                    val tags = buildString {
                        it.tags.forEach {
                            append(" #$it")
                        }
                    }
                    val encodedTag = URLEncoder.encode(tags, StandardCharsets.UTF_8.toString()).replace("+", " ")

                    val extags = buildString {
                        it.exchangeTags.forEach {
                            append(" #$it")
                        }
                    }
                    val encodedExTag = URLEncoder.encode(extags, StandardCharsets.UTF_8.toString()).replace("+", " ")
                    navController.navigate("$PRODUCT_INFO_SCREEN/${it.id}/$encodedUrl/${it.userId}/${it.title}/${it.description}/$encodedTag/$encodedExTag/${it.city}/${it.time}")
                }
            }
        }
    }
}

private fun buildAnnotatedStringWithColors(text: String): AnnotatedString {
    val words: List<String> = text.split("\\s+".toRegex())
    val colors = listOf(Color.Red, Color.Magenta, Color.Blue, Color.Green).shuffled() //create custom colors with theme!

    val builder = AnnotatedString.Builder()
    for ((count, word) in words.withIndex()) {
        builder.withStyle(style = SpanStyle(color = colors[count % 4])) {
            append("$word ")
        }
    }
    return builder.toAnnotatedString()
}
