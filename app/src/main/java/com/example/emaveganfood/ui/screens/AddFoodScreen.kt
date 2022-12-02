package com.example.emaveganfood.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.emaveganfood.ComposeFileProvider

@Composable
fun AddFoodScreen(
    modifier: Modifier = Modifier,
) {
    var hasImage by remember {
        mutableStateOf(false)
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
        }
    )

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current

        Button(onClick = {
            val uri = ComposeFileProvider.getImageUri(context)
            imageUri = uri
            cameraLauncher.launch(uri)
        }) {
            Text(text = "Take Picture")
        }

        Spacer(modifier = Modifier.padding(32.dp))

        Button(onClick = {
            imagePicker.launch("image/*")
        }) {
            Text(text = "Select Picture")
        }

        Spacer(modifier = Modifier.padding(32.dp))

        if (hasImage && imageUri != null) {
            AsyncImage(
                model = imageUri,
                modifier = Modifier.fillMaxWidth(),
                contentDescription = "Selected image",
            )
        }
    }
}