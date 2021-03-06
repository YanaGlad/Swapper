package com.example.nomoneytrade.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nomoneytrade.CURRENT_USER_ID
import com.example.nomoneytrade.MAIN_SCREEN
import com.example.nomoneytrade.R
import com.example.nomoneytrade.SHOWCASE_SCREEN
import com.example.nomoneytrade.SIGN_UP_SCREEN
import com.example.nomoneytrade.mvi.effect.AuthEffect
import com.example.nomoneytrade.mvi.event.AuthEvent
import com.example.nomoneytrade.ui.utils.UiUtilsExtendedFloatingButton
import com.example.nomoneytrade.ui.utils.UiUtilsPasswordTextField
import com.example.nomoneytrade.ui.utils.UiUtilsTextField
import com.example.nomoneytrade.ui.utils.UiUtilsToolbarButton

@Composable
fun AuthSignInScreen(navController: NavController, viewModel: AuthViewModel, showClose: Boolean = false) {

    val color = MaterialTheme.colors.primary

    var passwordText by remember { mutableStateOf("") }
    var emailText by remember { mutableStateOf("") }
    var colorState by remember { mutableStateOf(color) }
    var progressState by remember { mutableStateOf(false) }

    val eventState = viewModel.event.collectAsState()

    when (val event = eventState.value) {
        is AuthEvent.Error -> {}
        is AuthEvent.Loading -> {
            progressState = true
        }
        is AuthEvent.Success -> {
            colorState = color
            progressState = false
            viewModel.navigateShowcase()
            CURRENT_USER_ID = event.state.user.id.toLong()
        }
        is AuthEvent.FailedToLogin -> {
            colorState = Color.Red
        }
        AuthEvent.None -> {}
    }

    val effectState = viewModel.effect.collectAsState()

    when (val effect = effectState.value) {
        is AuthEffect.NavigateShowcase -> {
            navController.navigate(MAIN_SCREEN)
            viewModel.effect.value = AuthEffect.None
        }
        is AuthEffect.Navigate -> {
            navController.navigate(SIGN_UP_SCREEN)
            viewModel.effect.value = AuthEffect.None
        }
        is AuthEffect.None -> {
        }
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

        if(showClose) {
            UiUtilsToolbarButton(navController, MAIN_SCREEN, R.drawable.ic_close)
        }

        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_handshake),
            modifier = Modifier
                .wrapContentWidth()
                .height(170.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp),
            contentDescription = "App Icon",
        )
        Text(
            text = stringResource(R.string.app_name),
            textAlign = TextAlign.Center,
            fontSize = 35.sp,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = stringResource(R.string.exchange_service),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
        )

        Text(
            text = stringResource(R.string.authentication),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp),
        )

        UiUtilsTextField(label = stringResource(R.string.email), padding = 15, text = emailText, color = colorState) { text ->
            emailText = text
        }

        UiUtilsPasswordTextField(label = stringResource(R.string.password), padding = 15, text = passwordText, color = colorState) { text ->
            passwordText = text
        }

        UiUtilsExtendedFloatingButton(stringResource(R.string.sign_in), showProgress = progressState) {
            viewModel.signInClick(username = emailText, password = passwordText)
        }

        Text(
            text = stringResource(R.string.no_account_yet),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = true) {
                    viewModel.navigate()
                },
        )
    }
}
