package co.edu.unab.damo.jatt.storeapp.profile.ui.screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.edu.unab.damo.jatt.storeapp.R
import co.edu.unab.damo.jatt.storeapp.core.ui.activity.LoginActivity
import co.edu.unab.damo.jatt.storeapp.profile.ProfileUIState
import co.edu.unab.damo.jatt.storeapp.profile.ui.viewmodel.ProfileViewModel
import coil.compose.AsyncImage

@Composable
fun ProfileScreen(modifier: Modifier, viewModel: ProfileViewModel, uid: String) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val sharedPreferences = context.getSharedPreferences(
            context.getString(R.string.storeapp_prefs),
            Context.MODE_PRIVATE
        )
        sharedPreferences.getString("uid", null)?.let { viewModel.loadUserProfile(it) }
    }

    val profileState by viewModel.profileState.collectAsState()

    when (profileState) {
        is ProfileUIState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is ProfileUIState.Success -> {
            val userProfile = (profileState as ProfileUIState.Success).userProfile

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Fondo para la imagen de perfil
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .border(4.dp, MaterialTheme.colorScheme.onPrimary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = userProfile.image,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(2.dp, MaterialTheme.colorScheme.onPrimary, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Información del perfil
                Text(
                    text = userProfile.name,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = userProfile.email,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = userProfile.document,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Botón de cerrar sesión
                Button(
                    onClick = {
                        val sharedPreferences = context.getSharedPreferences(
                            context.getString(R.string.storeapp_prefs),
                            Context.MODE_PRIVATE
                        ).edit()
                        sharedPreferences.remove("login")
                        sharedPreferences.remove("uid")
                        sharedPreferences.apply()

                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent)
                        (context as Activity).finish()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(text = "Cerrar Sesión")
                }
            }
        }

        is ProfileUIState.Error -> {
            val message = (profileState as ProfileUIState.Error).message

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = message, color = Color.Red)
            }
        }
    }
}
