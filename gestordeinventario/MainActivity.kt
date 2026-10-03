package com.example.gestordeinventario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            InventarioApp()
        }
    }
}

@Composable
fun InventarioApp() {

    var mostrarProductos by remember { mutableStateOf(false) }
    var mostrarAgregarProducto by remember { mutableStateOf(false) }

    var productos by remember {
        mutableStateOf(
            listOf(
                Producto(
                    id = 1,
                    nombre = "Arroz",
                    stock = 20
                ),
                Producto(
                    id = 2,
                    nombre = "Leche",
                    stock = 15
                ),
                Producto(
                    id = 3,
                    nombre = "Pan",
                    stock = 10
                )
            )
        )
    }

    if (mostrarAgregarProducto) {

        PantallaAgregarProducto(
            guardar = { nuevoProducto ->

                productos = productos + nuevoProducto

                mostrarAgregarProducto = false
                mostrarProductos = true
            },
            cancelar = {
                mostrarAgregarProducto = false
            }
        )

    } else if (mostrarProductos) {

        PantallaProductos(
            productos = productos,

            volver = {
                mostrarProductos = false
            },

            agregarProducto = {
                mostrarAgregarProducto = true
            }
        )

    } else {

        PantallaPrincipal(
            abrirProductos = {
                mostrarProductos = true
            }
        )
    }
}

@Composable
fun PantallaPrincipal(
    abrirProductos: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Gestor de Inventario",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Control de productos y ventas"
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = abrirProductos
        ) {
            Text("Productos")
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = { }
        ) {
            Text("Registrar Venta")
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = { }
        ) {
            Text("Reportes")
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = { }
        ) {
            Text("Bajo Stock")
        }
    }
}

@Composable
fun PantallaProductos(
    productos: List<Producto>,
    volver: () -> Unit,
    agregarProducto: () -> Unit
) {

    var mostrarMenu by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Productos",
                style = MaterialTheme.typography.headlineMedium
            )

            Box {

                Button(
                    onClick = {
                        mostrarMenu = true
                    }
                ) {
                    Text("Menú")
                }

                DropdownMenu(
                    expanded = mostrarMenu,
                    onDismissRequest = {
                        mostrarMenu = false
                    }
                ) {

                    DropdownMenuItem(
                        text = {
                            Text("Agregar producto")
                        },
                        onClick = {
                            mostrarMenu = false
                            agregarProducto()
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text("Editar producto")
                        },
                        onClick = {
                            mostrarMenu = false
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text("Eliminar producto")
                        },
                        onClick = {
                            mostrarMenu = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Lista de productos:"
        )

        Spacer(modifier = Modifier.height(15.dp))

        productos.forEach { producto ->

            Text(
                text = "• ${producto.nombre} - Stock: ${producto.stock}"
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = volver
        ) {
            Text("Volver")
        }
    }
}

@Composable
fun PantallaAgregarProducto(
    guardar: (Producto) -> Unit,
    cancelar: () -> Unit
) {

    var nombre by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Agregar producto",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
            },
            label = {
                Text("Nombre del producto")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = stock,
            onValueChange = {
                stock = it
            },
            label = {
                Text("Stock")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Button(
                onClick = cancelar,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancelar")
            }

            Button(
                onClick = {

                    val stockNumero = stock.toIntOrNull() ?: 0

                    if (nombre.isNotBlank()) {

                        guardar(
                            Producto(
                                id = 0,
                                nombre = nombre,
                                stock = stockNumero
                            )
                        )
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Guardar")
            }
        }
    }
}