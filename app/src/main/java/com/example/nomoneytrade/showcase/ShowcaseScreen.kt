package com.example.nomoneytrade.showcase

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.navigation.NavController
import com.example.nomoneytrade.PRODUCT_INFO_SCREEN
import com.example.nomoneytrade.mvi.event.ShowcaseEvent
import com.example.nomoneytrade.ui.utils.ComposeScreen
import com.example.nomoneytrade.ui.utils.SearchView
import internal.ProductListItem
import java.util.*

class ShowcaseScreen(val navController: NavController, private val viewModel: ShowcaseViewModel) : ComposeScreen<ShowcaseViewModel>(navController, viewModel) {

    override val ON_CLOSE_DESTINATION: String? = null
    override val ON_BACK_DESTINATION: String? = null
    override val showCloseButton: Boolean = false
    override val showBackButton: Boolean = false

    @Composable
    override fun Screen() {

        val viewState = viewModel.event.collectAsState()

        when (val event = viewState.value) {
            ShowcaseEvent.Error -> {}
            ShowcaseEvent.Loading -> {}
            is ShowcaseEvent.Success -> {
                ProductList(navController, event.products)
            }
        }
    }
}

@Composable
private fun ProductList(navController: NavController, list: List<ProductPreview>) {

    Column {
        val textState = remember { mutableStateOf(TextFieldValue("")) }
        SearchView(textState)

        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
        ) {

            list.filter { product ->
                product.title.lowercase(Locale.getDefault()).contains(textState.value.text.lowercase(Locale.getDefault()))
            }.forEach {
                ProductListItem(productPreview = it) {
                    navController.navigate("$PRODUCT_INFO_SCREEN/${it.id}/${it.userId}/${it.title}/${it.description}")
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
