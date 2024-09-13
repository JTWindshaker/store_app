package co.edu.unab.damo.jatt.storeapp.core.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.edu.unab.damo.jatt.storeapp.R
import co.edu.unab.damo.jatt.storeapp.StoreAppDestinations.LoginDestination
import co.edu.unab.damo.jatt.storeapp.StoreAppDestinations.RegisterDestination
import co.edu.unab.damo.jatt.storeapp.login.screen.LoginScreen
import co.edu.unab.damo.jatt.storeapp.login.viewmodel.LoginViewModel
import co.edu.unab.damo.jatt.storeapp.register.ui.screen.RegisterScreen
import co.edu.unab.damo.jatt.storeapp.register.ui.viewmodel.RegisterViewModel
import co.edu.unab.damo.jatt.storeapp.ui.theme.StoreAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        println("onResume...")
    }

    override fun onStart() {
        super.onStart()
        println("onStart...")
    }

    override fun onStop() {
        super.onStop()
        println("onStop...")
    }

    override fun onPause() {
        super.onPause()
        println("onPause...")
    }

    override fun onRestart() {
        super.onRestart()
        println("onRestart...")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate...")

        val isLogin = getSharedPreferences(
            getString(R.string.storeapp_prefs),
            Context.MODE_PRIVATE
        ).getBoolean("login", false)

        if (isLogin) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

//        startActivity(intent)

        enableEdgeToEdge()
        setContent {
            StoreAppTheme(dynamicColor = false) {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = LoginDestination.route) {
                    composable(LoginDestination.route) {
                        LoginScreen(navController = navController, loginViewModel)
                    }

                    composable(RegisterDestination.route) {
                        RegisterScreen(navController = navController, registerViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun MyBoxLayout() {
    Box(
        modifier = Modifier
            .background(color = Color.Cyan)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.White)
                .size(100.dp)
        )
    }
}

@Composable
fun MyColumnLayout() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.Yellow)
                .size(100.dp)
                .weight(1f)
        )
        Box(
            modifier = Modifier
                .background(color = Color.Blue)
                .size(100.dp)
                .weight(0.5f)
        )
        Box(
            modifier = Modifier
                .background(color = Color.Red)
                .size(100.dp)
                .weight(0.5f)
        )
    }
}

@Composable
fun MyRowLayout() {
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.Yellow)
                .size(100.dp)
                .weight(1f)
        )
        Box(
            modifier = Modifier
                .background(color = Color.Blue)
                .size(100.dp)
                .weight(0.5f)
        )
        Box(
            modifier = Modifier
                .background(color = Color.Red)
                .size(100.dp)
                .weight(0.5f)
        )
    }
}

@Composable
fun MyConstraintLayout() {
    Column {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (boxCont) = createRefs()

            Column(
                modifier = Modifier
                    .background(color = Color.Cyan)
                    .fillMaxSize()
                    .constrainAs(boxCont) {
                        bottom.linkTo(parent.bottom)
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ConstraintLayout(
                    modifier = Modifier.border(2.dp, Color.Black)
                ) {
                    val (boxYellow, boxBlue, boxRed) = createRefs()

                    Box(
                        modifier = Modifier
                            .background(color = Color.Yellow)
                            .size(100.dp)
                            .constrainAs(boxYellow) {
                                bottom.linkTo(boxRed.top)
                                top.linkTo(parent.top)
                                end.linkTo(boxRed.start)
                            }
                    )
                    Box(
                        modifier = Modifier
                            .background(color = Color.Blue)
                            .size(100.dp)
                            .constrainAs(boxBlue) {
                                top.linkTo(boxYellow.bottom)
                                end.linkTo(boxYellow.start)
                            }
                    )
                    Box(
                        modifier = Modifier
                            .background(color = Color.Red)
                            .size(100.dp)
                            .constrainAs(boxRed) {
                                top.linkTo(boxYellow.bottom)
                                start.linkTo(boxBlue.end)
                                bottom.linkTo(parent.bottom)
                            }
                    )

                    createHorizontalChain(boxBlue, boxRed, chainStyle = ChainStyle.Packed)
                    createVerticalChain(boxYellow, boxRed, chainStyle = ChainStyle.Packed)
                }
            }
        }
    }
}