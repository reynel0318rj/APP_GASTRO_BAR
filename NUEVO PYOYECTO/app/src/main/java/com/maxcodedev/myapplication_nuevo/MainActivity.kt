package com.maxcodedev.myapplication_nuevo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.maxcodedev.myapplication_nuevo.ui.theme.MyApplication_NUEVOTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.fromColorLong
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplication_NUEVOTheme {
                MyApplication_NUEVOApp()
            }
        }
    }
}



// -------------------- PANTALLAS --------------------


@Composable
fun LoginScreen(modifier: Modifier = Modifier) {

    // Variables solo para visualizar (sin lógica real)
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "INICIAR SESIÓN",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo usuario
        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón (sin lógica)
        Button(
            onClick = { /* Sin acción por ahora */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("INGRESAR")
        }
    }
}



// DATA CLASS
data class Plato(
    val nombre: String,
    val descripcion: String,
    val precio: String,
    val imagen: Int
)

// LISTA DE EJEMPLO
val listaPlatos = listOf(
    Plato("Hamburguesa BBQ", "Carne + queso + salsa BBQ", "$25.000", R.drawable.hamburguesa),
    Plato("Pizza Artesanal", "Queso + pepperoni", "$30.000", R.drawable.pizza),
    Plato("Alitas BBQ", "8 piezas con salsa", "$22.000", R.drawable.alitas),
    Plato("Cerveza Artesanal", "Rubia / Negra", "$12.000", R.drawable.bebidas),
)

// -------------------- PANTALLA MENU --------------------

@Composable
fun MenuScreen(modifier: Modifier = Modifier) {

    Column(modifier = modifier.fillMaxSize()) {

        // HEADER
        Text(
            text = "GASTRO BAR 3AM",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(16.dp)
        )

        // LISTA DE PRODUCTOS
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(listaPlatos) { plato ->
                CardPlato(plato)
            }
        }
    }
}

// -------------------- CARD DE CADA PRODUCTO --------------------

@Composable
fun CardPlato(plato: Plato) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // IMAGEN
            Image(
                painter = painterResource(plato.imagen),
                contentDescription = plato.nombre,
                modifier = Modifier
                    .size(80.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // INFORMACIÓN
            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = plato.nombre,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = plato.descripcion,
                    style = MaterialTheme.typography.bodySmall)

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = plato.precio,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            // BOTÓN (solo visual)
            Button(onClick = { }) {
                Text("Pedir")
            }
        }
    }
}

@Composable
fun InfoScreen(modifier: Modifier = Modifier) {
    Text("Pantalla INFO", modifier = modifier)
}

@Composable
fun ExtraScreen(modifier: Modifier = Modifier) {
    Text("Pantalla EXTRA", modifier = modifier)
}

// -------------------- APP PRINCIPAL --------------------

@Composable
fun MyApplication_NUEVOApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            painter = painterResource(it.icon),
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

            when (currentDestination) {
                AppDestinations.HOME -> {
                    LoginScreen(Modifier.padding(innerPadding))
                }
                AppDestinations.FAVORITES -> {
                    MenuScreen(Modifier.padding(innerPadding))
                }
                AppDestinations.PROFILE -> {
                    InfoScreen(Modifier.padding(innerPadding))
                }
                AppDestinations.MENU -> {
                    ExtraScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// -------------------- DESTINOS --------------------

enum class AppDestinations(
    val label: String,
    val icon: Int,
) {
    HOME("LOGIN", R.drawable.ic_home),
    FAVORITES("MENU", R.drawable.ic_home),
    PROFILE("PERFIL", R.drawable.ic_account_box),
    MENU("EXTRA", R.drawable.ic_account_box),
}

// -------------------- FUNCION DE PRUEBA --------------------

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

// -------------------- PREVIEW --------------------

@PreviewScreenSizes
@Composable
fun MyApplicationPreview() {
    MyApplication_NUEVOTheme {
        MyApplication_NUEVOApp()
    }
}