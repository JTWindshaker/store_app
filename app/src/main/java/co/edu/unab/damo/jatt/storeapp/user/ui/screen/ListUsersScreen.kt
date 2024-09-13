package co.edu.unab.damo.jatt.storeapp.users.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.edu.unab.damo.jatt.storeapp.R
import co.edu.unab.damo.jatt.storeapp.users.domain.model.User
import co.edu.unab.damo.jatt.storeapp.users.ui.viewmodel.UsersViewModel
import coil.compose.AsyncImage

@Composable
fun ListUsersScreen(modifier: Modifier, viewModel: UsersViewModel) {
    val context = LocalContext.current
    val users by viewModel.users.collectAsState()

    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = stringResource(R.string.usuarios),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(users) { user ->
                UserItem(user = user) { selectedUser ->
                    Toast.makeText(context, "Ítem: ${selectedUser.name}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun UserItem(user: User, onSelectedItem: (User) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = {
            onSelectedItem(user)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = user.image,
                contentDescription = user.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(40.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = user.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )

            Text(
                text = user.email,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary),
                textAlign = TextAlign.Center
            )
        }
    }
}


//@Composable
//fun ListUsersScreen(modifier: Modifier) {
//    val context = LocalContext.current
//    LazyRow(
//        modifier = modifier.fillMaxSize(),
//        contentPadding = PaddingValues(8.dp),
//        horizontalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        items(getFakeUsersList()) {
//            UserItem(user = it) { productValue ->
//                Toast.makeText(context, "Ítem: $productValue", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//}

//@Composable
//fun UserItem(user: User, onSelectedItem: (User) -> Unit) {
//    Card(
//        modifier = Modifier
//            .width(400.dp)
//            .height(200.dp)
//            .padding(8.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.surface,
//            contentColor = MaterialTheme.colorScheme.onSurface
//        ),
//        shape = RoundedCornerShape(16.dp),
//        elevation = CardDefaults.cardElevation(8.dp),
//        onClick = {
//            onSelectedItem(user)
//        }
//    ) {
//        ConstraintLayout(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            val (imgProduct, nameProduct, priceProduct) = createRefs()
//
//            AsyncImage(
//                model = user.image,
//                contentDescription = null,
//                modifier = Modifier
//                    .size(150.dp)
//                    .constrainAs(imgProduct) {
//                        top.linkTo(parent.top)
//                        start.linkTo(parent.start)
//                        bottom.linkTo(parent.bottom)
//                    }
//                    .clip(
//                        shape = RoundedCornerShape(8.dp)
//                    ),
//                contentScale = ContentScale.Crop,
//            )
//
//            Text(
//                text = user.name,
//                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
//                modifier = Modifier.constrainAs(nameProduct) {
//                    start.linkTo(imgProduct.end, 16.dp)
//                    top.linkTo(parent.top)
//                    end.linkTo(parent.end)
//                },
//                textAlign = TextAlign.Center
//            )
//
//            Text(
//                text = user.email,
//                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary),
//                modifier = Modifier.constrainAs(priceProduct) {
//                    start.linkTo(imgProduct.end, 16.dp)
//                    top.linkTo(nameProduct.bottom, 8.dp)
//                    end.linkTo(parent.end)
//                },
//                textAlign = TextAlign.Center
//            )
//
//            createVerticalChain(nameProduct, priceProduct, chainStyle = ChainStyle.Packed)
//        }
//    }
//}

//private fun getFakeUsersList(): List<User> {
//    return listOf(
//        User(
//            name = "Juan Pérez",
//            email = "juan.perez@example.com",
//            image = "https://media.istockphoto.com/id/1090878494/es/foto/retrato-de-joven-sonriente-a-hombre-guapo-en-camiseta-polo-azul-aislado-sobre-fondo-gris-de.jpg?s=612x612&w=0&k=20&c=dHFsDEJSZ1kuSO4wTDAEaGOJEF-HuToZ6Gt-E2odc6U=",
////            password = "Password123!"
//        ),
//        User(
//            name = "Ana Gómez",
//            email = "ana.gomez@example.com",
//            image = "https://media.istockphoto.com/id/682897825/es/foto/confident-businesswoman-over-gray-background.jpg?s=612x612&w=0&k=20&c=WSlpnPQfEqYL77qKRBZ49wbUd4Ey6rd1RB1HCNKOusQ=",
////            password = "Ana2024$"
//        ),
//        User(
//            name = "Carlos Fernández",
//            email = "carlos.fernandez@example.com",
//            image = "https://e7.pngegg.com/pngimages/986/315/png-clipart-chin-cheek-beard-forehead-portrait-beard-face-people.png",
////            password = "Carlos#2024"
//        ),
//        User(
//            name = "Laura Martínez",
//            email = "laura.martinez@example.com",
//            image = "https://wl-genial.cf.tsp.li/resize/728x/jpg/3c2/346/f096635353b24378b3d8e4b447.jpg",
////            password = "Laura!2024"
//        ),
//        User(
//            name = "Miguel Ruiz",
//            email = "miguel.ruiz@example.com",
//            image = "https://modaellos.com/wp-content/uploads/2017/11/cortes-tipo-rostro-ovalado-istock.jpg",
////            password = "Miguel2024@"
//        ),
//        User(
//            name = "Sofía López",
//            email = "sofia.lopez@example.com",
//            image = "https://img.europapress.es/fotoweb/fotonoticia_20150331134913-15031252319_1200.jpg",
////            password = "Sofia$2024"
//        ),
//        User(
//            name = "Javier Morales",
//            email = "javier.morales@example.com",
//            image = "https://www.sopitas.com/site/wp-content/uploads/2015/03/archetypal-male-fa_3249635c.jpg",
////            password = "Javier2024#"
//        ),
//        User(
//            name = "María Fernández",
//            email = "maria.fernandez@example.com",
//            image = "https://tenimage.es/wp-content/uploads/2014/10/Fotolia_rectangular.jpg",
////            password = "Maria2024!"
//        ),
//        User(
//            name = "Luis Rodríguez",
//            email = "luis.rodriguez@example.com",
//            image = "https://w7.pngwing.com/pngs/796/49/png-transparent-chin-cheek-child-actor-forehead-patrik-child-face-photography.png",
////            password = "Luis2024@#"
//        ),
//        User(
//            name = "Carmen Sánchez",
//            email = "carmen.sanchez@example.com",
//            image = "https://www.e-consulta.com/_next/image?url=https%3A%2F%2Ffotos.e-consulta.com%2Frusas.jpg&w=3840&q=75",
////            password = "Carmen2024$"
//        )
//    )
//}