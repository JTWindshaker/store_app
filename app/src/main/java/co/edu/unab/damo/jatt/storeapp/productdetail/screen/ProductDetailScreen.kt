package co.edu.unab.damo.jatt.storeapp.productdetail.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import co.edu.unab.damo.jatt.storeapp.R
import co.edu.unab.damo.jatt.storeapp.StoreAppDestinations.ProductUpdateDestination
import co.edu.unab.damo.jatt.storeapp.core.ui.model.Product
import co.edu.unab.damo.jatt.storeapp.productdetail.viewmodel.ProductDetailViewModel
import coil.compose.AsyncImage

@Composable
fun ProductDetailScreen(
    modifier: Modifier,
    id: Int,
    navController: NavController,
    viewModel: ProductDetailViewModel
) {
    val context = LocalContext.current

    // Observe the product from the ViewModel
    val product by viewModel.getProductById(id).observeAsState(
        initial = Product(
            id = 0,
            image = "",
            name = "",
            price = 0
        )
    )
//    viewModel.getProduct(id)
    // Ensure the product ID is not zero before rendering
    if (product.id != 0) {
        // Load the product when the composable is first launched
//        LaunchedEffect(Unit) {
//            Log.e("xDD", "Ejecutando el lauched")
////            viewModel.getProduct(id)
////            Toast.makeText(context, "Test", Toast.LENGTH_LONG).show()
//        }

        ConstraintLayout(
            modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            val (nameField, priceField, descriptionField, imgField, imgView, saveButton, cancelButton) = createRefs()

            AsyncImage(
                model = product.image,
                contentDescription = product.name,
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .constrainAs(imgField) {
                        top.linkTo(parent.top, margin = 32.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    },
                contentScale = ContentScale.Crop
            )

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        append(stringResource(R.string.nombre))
                    }
                    append(product.name)
                },
                modifier = Modifier
                    .constrainAs(nameField) {
                        top.linkTo(imgField.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(bottom = 8.dp),
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        append(stringResource(R.string.precio))
                    }
                    append(product.price.toString())
                },
                modifier = Modifier
                    .constrainAs(priceField) {
                        top.linkTo(nameField.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(bottom = 8.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        append(stringResource(R.string.descripcion))
                    }
                    append(product.description)
                },
                modifier = Modifier
                    .constrainAs(descriptionField) {
                        top.linkTo(priceField.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(bottom = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            Button(
                onClick = {
                    navController.navigate(ProductUpdateDestination.createRoute(id)) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier
                    .width(150.dp)
                    .height(60.dp)
                    .padding(horizontal = 4.dp)
                    .constrainAs(saveButton) {
                        top.linkTo(descriptionField.bottom, margin = 24.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(text = stringResource(R.string.editar_producto))
            }

            OutlinedButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .width(150.dp)
                    .height(60.dp)
                    .padding(horizontal = 4.dp)
                    .constrainAs(cancelButton) {
                        top.linkTo(descriptionField.bottom, margin = 24.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(text = stringResource(R.string.regresar))
            }

            createHorizontalChain(cancelButton, saveButton, chainStyle = ChainStyle.Packed)
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}