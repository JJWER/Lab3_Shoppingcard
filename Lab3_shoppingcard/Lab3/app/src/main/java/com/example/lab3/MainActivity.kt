package com.example.lab3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.icons.Icons
import androidx.compose.material3.icons.filled.Add
import androidx.compose.material3.icons.filled.ArrowBack
import androidx.compose.material3.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.lab3.ui.theme.Lab3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab3Theme {
                ShoppingCartApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShoppingCartApp() {
    var data by remember { mutableStateOf(mutableStateListOf<String>()) }
    var isShowDialog by remember { mutableStateOf(false) }

    if (isShowDialog) {
        InputDialog(
            onCancel = { isShowDialog = false },
            onAddButtonClick = { newItemName ->
                data.add(newItemName)
                isShowDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shopping Cart") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.LightGray,
                    titleContentColor = Color.Magenta
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isShowDialog = true },
                containerColor = Color.Magenta
            ) {
                Icon(Icons.Filled.Add, "Add new Items", tint = Color.White)
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(data) { item ->
                CartItem(item)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputDialog(
    onCancel: () -> Unit,
    onAddButtonClick: (String) -> Unit
) {
    Dialog(
        onDismissRequest = onCancel,
    ) {
        var textValue by remember { mutableStateOf("") }
        Card(
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
            ) {
                TextField(
                    value = textValue,
                    onValueChange = { textValue = it },
                    label = { Text("Itemname") }
                )
                TextButton(onClick = { onAddButtonClick(textValue) }) {
                    Text("Add")
                }
            }
        }
    }
}

@Composable
private fun CartItem(itemname: String) {
    var amount by remember { mutableStateOf(0) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        Text(
            "$itemname",
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp)
        )
        IconButton(onClick = {
            if (amount > 0) {
                amount--
            }
        }) {
            Icon(Icons.Filled.ArrowBack, "Decrease")
        }
        Text("$amount")
        IconButton(onClick = { amount++ }) {
            Icon(Icons.Filled.ArrowForward, "Increase")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    InputDialog(onCancel = {}, onAddButtonClick = {})
}
