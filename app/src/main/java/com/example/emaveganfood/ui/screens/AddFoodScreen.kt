package com.example.emaveganfood.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.emaveganfood.ComposeFileProvider
import com.example.emaveganfood.R
import com.example.emaveganfood.navigation.NavigationItem
import com.example.emaveganfood.ui.models.Food
import com.example.emaveganfood.ui.theme.Primary
import com.example.emaveganfood.ui.viewmodels.AddFoodViewModel
import com.example.emaveganfood.utils.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Preview
@Composable
fun AddFoodScreen(
    modifier: Modifier = Modifier,
    viewModel: AddFoodViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

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
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current

        Spacer(modifier = Modifier.padding(24.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                val uri = ComposeFileProvider.getImageUri(context)
                imageUri = uri
                cameraLauncher.launch(uri)
            }) {
                Text(text = "Take Picture")
            }

            Text("sau", modifier = Modifier.padding(start = 16.dp, end = 16.dp))

            Button(onClick = {
                imagePicker.launch("image/*")
            }) {
                Text(text = "Select Picture")
            }
        }

        Spacer(modifier = Modifier.padding(16.dp))

        if (hasImage && imageUri != null) {
            AsyncImage(
                model = imageUri,
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
                    .width(200.dp)
                    .height(200.dp)
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            value = viewModel.foodTitle,
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
            value = viewModel.foodDescription,
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
                    coroutineScope.launch {
                        addFood(viewModel)
                    }
                }
            ),
        )

        Spacer(modifier = Modifier.padding(32.dp))

    }
}

private suspend fun addFood(viewModel: AddFoodViewModel) {
    viewModel.addFood(Food(title = "test", description = "test")).collect() {
        when(it) {
            is State.Failed -> {
                println("vlad: failed")
            }
            is State.Loading -> {
                println("vlad: loading")
            }
            is State.Success -> {
                println("vlad: success")
            }
        }
    }
}

