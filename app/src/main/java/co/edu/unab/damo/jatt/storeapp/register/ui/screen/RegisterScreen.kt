package co.edu.unab.damo.jatt.storeapp.register.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import co.edu.unab.damo.jatt.storeapp.R
import co.edu.unab.damo.jatt.storeapp.register.ui.viewmodel.RegisterViewModel
import coil.compose.AsyncImage

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel
) {
    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoading) {
        LoadingIndicator()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    HeaderRegister()
                    BodyRegister(navController = navController, viewModel = viewModel)

                    Spacer(modifier = Modifier.weight(1f))

                    FooterRegister()
                }
            }
        }
    }
}

@Composable
fun LoadingIndicator() {
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
}

@Composable
fun HeaderRegister() {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (imgLogo, txtLogin) = createRefs()

        AsyncImage(
            model = "https://download.logo.wine/logo/Google_Storage/Google_Storage-Logo.wine.png",
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .constrainAs(imgLogo) {
                    top.linkTo(parent.top, 28.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentScale = ContentScale.Fit
        )

        Text(
            text = stringResource(R.string.txt_registrarse),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(txtLogin) {
                    top.linkTo(imgLogo.bottom, 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}

@Composable
fun BodyRegister(navController: NavController, viewModel: RegisterViewModel) {
    var name: String by rememberSaveable {
        mutableStateOf("")
    }

    var document: String by rememberSaveable {
        mutableStateOf("")
    }

    var password: String by rememberSaveable {
        mutableStateOf("")
    }

    var email: String by rememberSaveable {
        mutableStateOf("")
    }

    val image: String by rememberSaveable {
        mutableStateOf("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png")
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<Boolean>(initialValue = false, lifecycle, viewModel) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.onCreateAccount.collect { isCreated ->
                value = isCreated
            }
        }
    }

    when (uiState) {
        true -> {
            LaunchedEffect(Unit) {
                viewModel.onChangeCreateAccount(false)
                navController.popBackStack()
            }
        }

        false -> {
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    val context = LocalContext.current
                    val (tfName, tfEmail, tfDocument, tfPassword, btnBack, btnRegister) = createRefs()
                    var showPass by rememberSaveable {
                        mutableStateOf(false)
                    }

                    TextField(
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Person, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(R.string.txt_name_register))
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(tfName) {
                                top.linkTo(parent.top, 48.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    TextField(
                        value = email,
                        onValueChange = {
                            email = it
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Email, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(R.string.txt_correo_login))
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(tfEmail) {
                                top.linkTo(tfName.bottom, 16.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    TextField(
                        value = document,
                        onValueChange = {
                            document = it
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(R.string.documento))
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(tfDocument) {
                                top.linkTo(tfEmail.bottom, 16.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    TextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(R.string.txt_password_login))
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
                                top.linkTo(tfDocument.bottom, 16.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    OutlinedButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .constrainAs(btnBack) {
                                top.linkTo(tfPassword.bottom, 16.dp)
                                start.linkTo(parent.start)
                                end.linkTo(btnRegister.start)
                            }
                            .width(120.dp)
                    ) {
                        Text(text = stringResource(R.string.txt_back))
                    }

                    Button(
                        onClick = {
                            viewModel.createAccount(name, document, email, password, image)
                        },
                        modifier = Modifier
                            .constrainAs(btnRegister) {
                                top.linkTo(tfPassword.bottom, 16.dp)
                                start.linkTo(btnBack.end, 8.dp)
                                end.linkTo(parent.end)
                            }
                            .width(120.dp)
                    ) {
                        Text(text = stringResource(R.string.txt_btn_registrarse))
                    }
                }
            }
        }
    }
}

@Composable
fun FooterRegister() {

}