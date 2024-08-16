@file:OptIn(ExperimentalMaterial3Api::class)

package com.shashank.countrylist.identity.presentation.screen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.shashank.countrylist.R
import com.shashank.countrylist.core.presentation.ui.component.AlertDialog
import com.shashank.countrylist.core.utils.Constants.Routes.COUNTRY_LIST
import com.shashank.countrylist.core.utils.NetworkResult
import com.shashank.countrylist.identity.presentation.viewModels.IdentityViewModel

/**
 * 'LoginForm' is a group of composable like TextFields and button, basically forming a Login page.
 * It also checks in case of an error during networking call / identity verification and displays a
 * dialog displaying message received from the server.
 */

@Composable
fun LoginForm(navController: NavController, identityViewModel: IdentityViewModel = hiltViewModel()) {
    Surface {
        var credentials by remember { mutableStateOf(Credentials()) }
        var loadingSpinner by remember { mutableStateOf(false) }

        val infiniteTransition = rememberInfiniteTransition(label = "")
        val angle by infiniteTransition.animateFloat(
            initialValue = 0F,
            targetValue = 360F,
            animationSpec = infiniteRepeatable(
                animation = tween(10000, easing = LinearEasing)
            ), label = ""
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(id = R.dimen.spacer_30))
        ) {
            Image(
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.spacer_250))
                    .height(dimensionResource(id = R.dimen.spacer_250))
                    .rotate(angle),
                painter = painterResource(id = R.drawable.globe),
                contentDescription = stringResource(id = R.string.app_icon))
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_40)))
            UsernameField(
                value = credentials.username,
                onChange = { data -> credentials = credentials.copy(username = data) },
                modifier = Modifier.fillMaxWidth()
            )
            PasswordField(
                value = credentials.password,
                onChange = { data -> credentials = credentials.copy(password = data) },
                submit = {
                    if (!checkCredentials(credentials, identityViewModel)) credentials = Credentials()
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_20)))

            if(loadingSpinner){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }else{
                Button(
                    onClick = {
                        if (!checkCredentials(credentials, identityViewModel)) credentials = Credentials()
                    },
                    enabled = credentials.isNotEmpty(),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.spacer_5)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(id = R.string.login))
                }
            }
        }

        val verificationResult = identityViewModel.verifyResult.collectAsStateWithLifecycle().value

        when(verificationResult){
            is NetworkResult.Error -> {
                if (verificationResult.message.toString() != ""){
                    AlertDialog(
                        titleText = stringResource(id = R.string.error),
                        messageText = verificationResult.message.toString(),
                        buttonPositiveText = stringResource(id = R.string.ok),
                        buttonNegativeText = null,
                        onPositiveClick = {
                            identityViewModel.clearError()
                            credentials.username = ""
                            credentials.password = ""
                        }) {}
                }
                loadingSpinner = false
            }
            is NetworkResult.Loading -> {
                loadingSpinner = true
            }
            is NetworkResult.Success -> {
                navController.popBackStack()
                navController.navigate(COUNTRY_LIST)
            }
        }

    }
}

/**
 * 'checkCredentials' checks whether username and password are filled. If not then it returns status
 * and keeps the login disabled and when filled it enables login btn and allows user to verify credentials.
 */

fun checkCredentials(creds: Credentials, identityViewModel: IdentityViewModel): Boolean {
    if (creds.isNotEmpty()) {
        identityViewModel.doVerification(creds.username, creds.password)
        return true
    } else {
        return false
    }
}


data class Credentials(
    var username: String = "",
    var password: String = ""
) {
    fun isNotEmpty(): Boolean {
        return username.isNotEmpty() && password.isNotEmpty()
    }
}

/**
 * Respective 'Username' & 'Password fields. We can make them generic however in future it could become
 * un-maintainable due variations in UI.
 */

@Composable
fun UsernameField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = stringResource(id = R.string.username),
    placeholder: String = stringResource(id = R.string.username_hint)
) {

    val focusManager = LocalFocusManager.current
    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Person,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }

    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        placeholder = { Text(placeholder) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation = VisualTransformation.None
    )
}

@Composable
fun PasswordField(
    value: String,
    onChange: (String) -> Unit,
    submit: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = stringResource(id = R.string.password),
    placeholder: String = stringResource(id = R.string.password_message)
) {

    var isPasswordVisible by remember { mutableStateOf(false) }

    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Key,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    val trailingIcon = @Composable {
        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
            Icon(
                if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onDone = { submit() }
        ),
        placeholder = { Text(placeholder) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )

}