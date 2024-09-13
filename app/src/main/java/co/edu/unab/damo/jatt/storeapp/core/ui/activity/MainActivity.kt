package co.edu.unab.damo.jatt.storeapp.core.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import co.edu.unab.damo.jatt.storeapp.StoreAppDestinations.HomeDestination
import co.edu.unab.damo.jatt.storeapp.StoreAppDestinations.HomeDestination.mainScreens
import co.edu.unab.damo.jatt.storeapp.StoreAppDestinations.ProductAddDestination
import co.edu.unab.damo.jatt.storeapp.StoreAppDestinations.ProductDetailDestination
import co.edu.unab.damo.jatt.storeapp.StoreAppDestinations.ProductUpdateDestination
import co.edu.unab.damo.jatt.storeapp.StoreAppDestinations.ProfileDestination
import co.edu.unab.damo.jatt.storeapp.StoreAppDestinations.UserDestination
import co.edu.unab.damo.jatt.storeapp.home.ui.screen.HomeScreen
import co.edu.unab.damo.jatt.storeapp.home.ui.viewmodel.HomeViewModel
import co.edu.unab.damo.jatt.storeapp.productadd.ui.ProductAddScreen
import co.edu.unab.damo.jatt.storeapp.productadd.viewmodel.AddProductViewModel
import co.edu.unab.damo.jatt.storeapp.productdetail.screen.ProductDetailScreen
import co.edu.unab.damo.jatt.storeapp.productdetail.viewmodel.ProductDetailViewModel
import co.edu.unab.damo.jatt.storeapp.productupdate.ui.screen.ProductUpdateScreen
import co.edu.unab.damo.jatt.storeapp.productupdate.ui.viewmodel.UpdateProductViewModel
import co.edu.unab.damo.jatt.storeapp.profile.ui.screen.ProfileScreen
import co.edu.unab.damo.jatt.storeapp.profile.ui.viewmodel.ProfileViewModel
import co.edu.unab.damo.jatt.storeapp.ui.theme.StoreAppTheme
import co.edu.unab.damo.jatt.storeapp.users.ui.screen.ListUsersScreen
import co.edu.unab.damo.jatt.storeapp.users.ui.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val productDetailViewModel: ProductDetailViewModel by viewModels()
    private val productAddViewModel: AddProductViewModel by viewModels()
    private val productUpdateViewModel: UpdateProductViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val usersViewModel: UsersViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        lifecycleScope.launch(Dispatchers.IO) {
//            withContext(Dispatchers.Main) {
//                Toast.makeText(baseContext, "Test", Toast.LENGTH_LONG).show()
//            }
//        }

//        homeViewModel.productList.observe(this) {
//            if (it != null) {
//                homeViewModel.loadProduct()
//            }
//        }

        setContent {
            val navController = rememberNavController()
            val currentBackStack by navController.currentBackStackEntryAsState()
            val currentRoute = currentBackStack?.destination

            val currentScreen = mainScreens().find {
                it.route == currentRoute?.route
            }

            StoreAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (currentScreen != null) {
                                        Text(
                                            text = currentScreen.title,
                                            style = MaterialTheme.typography.titleLarge,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            modifier = Modifier
                                .shadow(4.dp)
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .shadow(8.dp)
                                .height(120.dp)
                        ) {
                            NavigationBarItem(
                                modifier = Modifier.width(16.dp),
                                selected = HomeDestination.route == (currentRoute?.route ?: ""),
                                onClick = {
                                    navController.navigate(HomeDestination.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        modifier = Modifier.size(24.dp),
                                        imageVector = Icons.Filled.Home,
                                        contentDescription = null,
                                    )
                                },
                                label = {
                                    Text(
                                        text = "Home",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            )

                            NavigationBarItem(
                                selected = UserDestination.route == (currentRoute?.route ?: ""),
                                onClick = {
                                    navController.navigate(UserDestination.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = false
                                    }
                                },
                                icon = {
                                    Icon(
                                        modifier = Modifier.size(24.dp),
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null
                                    )
                                },
                                label = {
                                    Text(
                                        text = "Users",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            )

                            NavigationBarItem(
                                selected = ProfileDestination.route == (currentRoute?.route ?: ""),
                                onClick = {


                                    navController.navigate(ProfileDestination.createRoute("123")) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        modifier = Modifier.size(24.dp),
                                        imageVector = Icons.Filled.AccountCircle,
                                        contentDescription = null
                                    )
                                },
                                label = {
                                    Text(
                                        text = "Profile",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            )
                        }
                    },
                    floatingActionButton = {
                        if (HomeDestination.route == (currentRoute?.route ?: "")) {
                            FloatingActionButton(
                                onClick = {
                                    navController.navigate(ProductAddDestination.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "Add Product"
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = HomeDestination.route
                    ) {
                        composable(HomeDestination.route) {
                            HomeScreen(
                                Modifier.padding(innerPadding),
                                homeViewModel,
                                navController = navController
                            )
                        }

                        composable(
                            route = ProfileDestination.route,
                            arguments = listOf(
                                navArgument("uid") { type = NavType.StringType }
                            )
                        ) {
                            val uid = it.arguments?.getString("uid") ?: ""

                            ProfileScreen(
                                Modifier.padding(innerPadding),
                                viewModel = profileViewModel,
                                uid = uid
                            )
                        }

                        composable(UserDestination.route) {
                            ListUsersScreen(Modifier.padding(innerPadding), usersViewModel)
                        }

                        composable(ProductAddDestination.route) {
                            ProductAddScreen(
                                Modifier.padding(innerPadding),
                                navController = navController,
                                viewModel = productAddViewModel
                            )
                        }

                        composable(
                            route = ProductDetailDestination.route,
                            arguments = listOf(
                                navArgument("id") { type = NavType.IntType }
                            )
                        ) {
                            val id = it.arguments?.getInt("id") ?: 0

                            ProductDetailScreen(
                                modifier = Modifier.padding(innerPadding),
                                id = id,
                                navController = navController,
                                productDetailViewModel
                            )
                        }

                        composable(
                            route = ProductUpdateDestination.route,
                            arguments = listOf(
                                navArgument("id") { type = NavType.IntType }
                            )
                        ) {
                            val id = it.arguments?.getInt("id") ?: 0

                            ProductUpdateScreen(
                                modifier = Modifier.padding(innerPadding),
                                id = id,
                                navController = navController,
                                viewModel = productUpdateViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}