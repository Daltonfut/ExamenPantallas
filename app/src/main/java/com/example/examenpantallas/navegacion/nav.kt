package com.example.examenpantallas.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation3.NavDisplay
import androidx.navigation3.entry
import androidx.navigation3.entryProvider
import androidx.navigation3.rememberNavBackStack
import com.example.examenpantallas.pantallas.Home
import com.example.examenpantallas.pantallas.Login
import com.example.examenpantallas.pantallas.NuevoJugador
import com.google.firebase.auth.FirebaseAuth
@Composable
fun NavDisplay(auth: FirebaseAuth) {
    val backStack = rememberNavBackStack(initialKey = Routes.Login)

    NavDisplay(
        backstack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Routes.Login> {
                Login(
                    auth = auth,
                    onLoginSuccess = {
                        backStack.clear()
                        backStack.add(Routes.Home)
                    }
                )
            }
            entry<Routes.Home> {
                Home(
                    auth = auth,
                    onAddJugador = { backStack.add(Routes.NuevoJugador) }
                )
            }
            entry<Routes.NuevoJugador> {
                NuevoJugador(
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}
