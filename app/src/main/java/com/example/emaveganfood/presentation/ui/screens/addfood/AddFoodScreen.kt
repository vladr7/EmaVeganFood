package com.example.emaveganfood.presentation.ui.screens.addfood

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.emaveganfood.core.utils.ComposeFileProvider
import com.example.emaveganfood.R
import com.example.emaveganfood.presentation.theme.PrimaryTransparent
import com.example.emaveganfood.core.utils.State
import com.example.emaveganfood.core.utils.getCompressedImage
import kotlinx.coroutines.launch

@Preview
@Composable
fun AddFoodScreen(
    modifier: Modifier = Modifier,
    viewModel: AddFoodViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    if (state.errorMessage != null) {
        Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PrimaryTransparent)
    ) {

        val imagePicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                viewModel.updateHasImage(uri != null)
                viewModel.updateImageUri(uri)
            }
        )

        val cameraLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = { success ->
                viewModel.updateHasImage(success)
            }
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.padding(24.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    val uri = ComposeFileProvider.getImageUri(context)
                    viewModel.updateImageUri(uri)
                    cameraLauncher.launch(uri)
                }) {
                    Text(text = "Camera")
                }

                Text("sau", modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))

                Button(onClick = {
                    imagePicker.launch("image/*")
                }) {
                    Text(text = "Selecteaza din telefon")
                }
            }

            Spacer(modifier = Modifier.padding(16.dp))

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(150.dp)
                )
            }

            if (state.hasImage && state.imageUri != null) {
                AsyncImage(
                    model = state.imageUri,
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp),
                    contentDescription = "Selected image",
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_no_photography_24),
                    contentDescription = null,
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            OutlinedTextField(
                value = state.foodTitle,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 16.dp),
                onValueChange = {
                    if (it.length <= 40) {
                        viewModel.updateFoodTitle(it)
                    }
                },
                label = {
                    Text(stringResource(R.string.titlu))
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                keyboardActions = KeyboardActions(
                    onDone = {

                    }
                ),
            )

            OutlinedTextField(
                value = state.foodDescription,
                singleLine = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(start = 32.dp, end = 32.dp, top = 16.dp),
                onValueChange = {
                    if (it.length <= 400) {
                        viewModel.updateFoodDescription(it)
                    }
                },
                label = {
                    Text(stringResource(R.string.descriere))
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.addFoodImageToStorage()
                        viewModel.addFood()
                    }
                ),
            )

            Spacer(modifier = Modifier.padding(32.dp))

        }
    }
}




