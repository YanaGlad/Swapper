package internal

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.nomoneytrade.R
import com.example.nomoneytrade.entity.Product
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ProductListItem(product: Product, onClick: () -> Unit) {
    var progressState by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .padding(top = 16.dp, end = 8.dp, start = 8.dp)
            .clickable(
                onClick = {
                    onClick()
                }
            )
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)) {
            
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = product.imageUrl)
                        .allowHardware(false)
                        .build()
                ),

                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp, start = 5.dp, bottom = 10.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colors.primary),
                contentDescription = "profile photo",
                contentScale = ContentScale.Crop,
            )
            Text(
                text = product.title,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = product.tags.getTags(),
                fontSize = 10.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp)
            )

            Text(
                text = product.description.getModifiedText(),
                fontSize = 15.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 5.dp),
            )
        }
    }
}


private fun List<String>.getTags(): String {
    var result = ""
    this.forEach {
        result += "$it "
    }
    return result
}

private fun String.getModifiedText(): String {
    return if (length > 75)
        substring(0, 70) + "..."
    else
        this
}
