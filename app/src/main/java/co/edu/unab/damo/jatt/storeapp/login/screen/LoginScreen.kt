package co.edu.unab.damo.jatt.storeapp.login.screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import co.edu.unab.damo.jatt.storeapp.R
import co.edu.unab.damo.jatt.storeapp.StoreAppDestinations.RegisterDestination
import co.edu.unab.damo.jatt.storeapp.core.ui.activity.MainActivity
import co.edu.unab.damo.jatt.storeapp.login.LoginUIState
import co.edu.unab.damo.jatt.storeapp.login.viewmodel.LoginViewModel
import coil.compose.AsyncImage

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<LoginUIState>(
        initialValue = LoginUIState.Default,
        lifecycle,
        viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { isLogin ->
                value = isLogin
            }
        }
    }

    if (uiState == LoginUIState.Loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .alpha(0.8f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(64.dp)
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    HeaderLogin()
                    BodyLogin(viewModel = viewModel)
                    Spacer(modifier = Modifier.weight(1f))
                    FooterLogin(navController = navController)
                }

                when (uiState) {
                    LoginUIState.Default -> {

                    }

                    LoginUIState.Error -> {
                        LaunchedEffect(Unit) {
                            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                            viewModel.onChangeUIState(LoginUIState.Default)
                        }
                    }

                    LoginUIState.Loading -> {}

                    LoginUIState.Success -> {
                        LaunchedEffect(Unit) {
                            Toast.makeText(context, "Iniciando Sesión", Toast.LENGTH_LONG).show()
                        }

                        val sharedPreferences = context.getSharedPreferences(
                            stringResource(R.string.storeapp_prefs),
                            Context.MODE_PRIVATE
                        ).edit()
                        sharedPreferences.putBoolean("login", true)
                        sharedPreferences.apply()

                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                        (context as Activity).finish()
                    }
                }

            }
        }
    }
}

@Composable
fun HeaderLogin() {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (imgLogo, txtLogin) = createRefs()
//        Image(
//            painter = painterResource(id = R.drawable.svg_6),
//            contentDescription = null,
//            modifier = Modifier.width(150.dp)
//                .height(150.dp)
//                .constrainAs(imgLogo) {
//                top.linkTo(parent.top, (-48).dp)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//            },
//            contentScale = ContentScale.Crop
//        )
        AsyncImage(
            model = "https://download.logo.wine/logo/Google_Storage/Google_Storage-Logo.wine.png",
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .constrainAs(imgLogo) {
                    top.linkTo(parent.top, 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentScale = ContentScale.Crop
        )

        Text(
            text = stringResource(R.string.txt_iniciar_sesion),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(txtLogin) {
                    top.linkTo(imgLogo.bottom, 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}

@Composable
fun BodyLogin(viewModel: LoginViewModel) {
    val context = LocalContext.current
    val emailValue by viewModel.email.observeAsState("")
    val passwordValue by viewModel.password.observeAsState("")
    val errorMessage by viewModel.errorMessage.observeAsState()

    errorMessage?.let {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        // Limpiar el mensaje de error después de mostrar
        viewModel.onMessageChange(null)
    }

//    val isLogin by viewModel.isLogin.observeAsState(false)
//    val isError by viewModel.isError.observeAsState(false)

//    var isLogin by rememberSaveable {
//        mutableStateOf(false)
//    }
//
//    var isError by rememberSaveable {
//        mutableStateOf(false)
//    }

//    if (isLogin) {
//        Toast.makeText(context, "Iniciando Sesión", Toast.LENGTH_LONG).show()
//
//        val intent = Intent(context, MainActivity::class.java)
//        context.startActivity(intent)
//        (context as Activity).finish()
//    } else if (isError) {
//        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
//    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        val (tfEmail, tfPassword, btnLogin) = createRefs()

        var showPass by rememberSaveable {
            mutableStateOf(false)
        }

        TextField(
            value = emailValue,
            onValueChange = { viewModel.onLoginChange(email = it, password = passwordValue) },
            label = {
                Text(text = stringResource(R.string.txt_correo_login))
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                focusedIndicatorColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(tfEmail) {
                    top.linkTo(parent.top, 80.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                }
        )

        TextField(
            value = passwordValue,
            onValueChange = { viewModel.onLoginChange(email = emailValue, password = it) },
            label = {
                Text(text = stringResource(R.string.txt_password_login))
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = null)
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                focusedIndicatorColor = MaterialTheme.colorScheme.primary
            ),
            trailingIcon = {
                IconButton(onClick = { showPass = !showPass }) {
                    val icon = if (showPass) {
                        painterResource(id = R.drawable.ic_eye_slash)
                    } else {
                        painterResource(id = R.drawable.ic_eye)
                    }
                    Icon(painter = icon, contentDescription = null)
                }
            },
            visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(tfPassword) {
                    top.linkTo(tfEmail.bottom, 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Button(
            onClick = {
                viewModel.verifyLogin()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .constrainAs(btnLogin) {
                    top.linkTo(tfPassword.bottom, 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.txt_ingresar),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun FooterLogin(navController: NavController) {
    val context = LocalContext.current

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    ) {
        val (divider, textSignUp, linkSignUp) = createRefs()

        HorizontalDivider(
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            modifier = Modifier
                .constrainAs(divider) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = stringResource(R.string.txt_sin_cuenta_login),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .constrainAs(textSignUp) {
                    top.linkTo(divider.bottom, 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = stringResource(R.string.txt_registrate),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier
                .clickable {
                    Log.e("xDDDDDDDDDD", "Click en registrarse")
                    navController.navigate(RegisterDestination.route)
                }
                .constrainAs(linkSignUp) {
                    top.linkTo(textSignUp.bottom, 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

//        createHorizontalChain(textSignUp, linkSignUp, chainStyle = ChainStyle.Spread)
    }
}