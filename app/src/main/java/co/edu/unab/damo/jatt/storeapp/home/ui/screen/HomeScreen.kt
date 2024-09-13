package co.edu.unab.damo.jatt.storeapp.home.ui.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import co.edu.unab.damo.jatt.storeapp.StoreAppDestinations.ProductDetailDestination
import co.edu.unab.damo.jatt.storeapp.core.ui.model.Product
import co.edu.unab.damo.jatt.storeapp.home.ui.ProductListUIState
import co.edu.unab.damo.jatt.storeapp.home.ui.viewmodel.HomeViewModel
import coil.compose.AsyncImage
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HomeScreen(modifier: Modifier, viewModel: HomeViewModel, navController: NavController) {
//    val productList: List<Product> by viewModel.productList.observeAsState(emptyList())
    val context = LocalContext.current

    //Flow 3:
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<ProductListUIState>(
        initialValue = ProductListUIState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { state ->
                value = state
            }
        }
    }

    when (uiState) {
        is ProductListUIState.Error -> {}

        ProductListUIState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }

        is ProductListUIState.Success -> {
            val productList = (uiState as ProductListUIState.Success).productList
            if (productList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No hay productos disponibles.",
                        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(productList) {
                        ProductItem(
                            product = it,
                            onLongPressItem = { productRemove ->
                                viewModel.removeProduct(productRemove)
                            }
                        ) { productValue ->
                            navController.navigate(ProductDetailDestination.createRoute(productValue.id)) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = false
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    onLongPressItem: (Product) -> Unit,
    onSelectedItem: (Product) -> Unit
) {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault()).apply {
        maximumFractionDigits = 0
        minimumFractionDigits = 0
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onSelectedItem(product) },
                    onLongPress = { onLongPressItem(product) }
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            val (imgProduct, nameProduct, priceProduct) = createRefs()

            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .constrainAs(imgProduct) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    },
                contentScale = ContentScale.Crop,
            )

            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.constrainAs(nameProduct) {
                    start.linkTo(imgProduct.end, 8.dp)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
                textAlign = TextAlign.Start
            )

            Text(
                text = numberFormat.format(product.price),
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier.constrainAs(priceProduct) {
                    start.linkTo(imgProduct.end, 8.dp)
                    top.linkTo(nameProduct.bottom, 4.dp)
                    end.linkTo(parent.end)
                },
                textAlign = TextAlign.Start
            )

            createVerticalChain(nameProduct, priceProduct, chainStyle = ChainStyle.Packed)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestHomeScreen() {
//    ProductItem(product = getFakeProductList().first()) {}
}