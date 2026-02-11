package com.example.examenpantallas.mvvm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _loginError = mutableStateOf<String?>(null)
    val loginError: State<String?> = _loginError

    private val _loginSuccess = mutableStateOf(false)
    val loginSuccess: State<Boolean> = _loginSuccess

    fun onEmailChange(email: String) {
        _email.value = email
    }
    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun clearLoginError() {
        _loginError.value = null
    }
    suspend fun login() {
        try {
            auth.signInWithEmailAndPassword(_email.value, _password.value).await()
            _loginSuccess.value = true
        } catch (e: Exception) {
            _loginError.value = "Usuario o contraseña incorrectos"
        }
    }
}
class HomeViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _jugadores = mutableStateOf<List<Jugador>>(emptyList())
    val jugadores: State<List<Jugador>> = _jugadores

    init {
        obtenerJugadores()
    }

    private fun obtenerJugadores() {
        db.collection("Jugadores").addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            if (snapshot != null) {
                _jugadores.value = snapshot.toObjects(Jugador::class.java)
            }
        }
    }
}
