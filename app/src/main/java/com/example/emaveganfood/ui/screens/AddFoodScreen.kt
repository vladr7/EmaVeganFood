package com.example.emaveganfood.ui.screens

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
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
import com.example.emaveganfood.ComposeFileProvider
import com.example.emaveganfood.R
import com.example.emaveganfood.ui.theme.PrimaryTransparent
import com.example.emaveganfood.ui.viewmodels.AddFoodViewModel
import com.example.emaveganfood.utils.State
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.launch
import java.io.*

@Preview
@Composable
fun AddFoodScreen(
    modifier: Modifier = Modifier,
    viewModel: AddFoodViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PrimaryTransparent)
    ) {
        val coroutineScope = rememberCoroutineScope()

        var hasImage by remember {
            mutableStateOf(false)
        }

        var imageUri by remember {
            mutableStateOf<Uri?>(null)
        }

        var isLoading by remember {
            mutableStateOf(false)
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    val uri = ComposeFileProvider.getImageUri(context)
                    imageUri = uri
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
                        .width(50.dp)
                        .height(50.dp)
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
                        if (!isLoading) {
                            isLoading = true
                            coroutineScope.launch {
                                addFood(fileUri = imageUri, viewModel, context, onLoading = {
                                    isLoading = it
                                })
                            }
                        }
                    }
                ),
            )

            Spacer(modifier = Modifier.padding(32.dp))

        }
    }

}

private suspend fun addFood(
    fileUri: Uri?,
    viewModel: AddFoodViewModel,
    context: Context,
    onLoading: (Boolean) -> Unit
) {
    viewModel.addFood(fileUri = fileUri).collect() {
        when (it) {
            is State.Failed -> {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
            is State.Loading -> {
                onLoading(true)
            }
            is State.Success -> {
                addFoodImageToStorage(fileUri, viewModel, context, onLoading)
            }
        }
    }
}

private suspend fun addFoodImageToStorage(
    fileUri: Uri?,
    viewModel: AddFoodViewModel,
    context: Context,
    onLoading: (Boolean) -> Unit
) {
    val compressedImage = fileUri?.let { getCompressedImage(it, context) }
    viewModel.addFoodImageToStorage(fileUri = compressedImage).collect {
        when (it) {
            is State.Failed -> {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
            is State.Loading -> {
                Toast.makeText(context, "Loading..", Toast.LENGTH_SHORT).show()
                onLoading(true)
            }
            is State.Success -> {
                Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
                onLoading(false)
            }
        }
    }
}

suspend fun getCompressedImage(imageUri: Uri, context: Context): Uri {
    val fileUri = getFilePathFromUri(imageUri, context)
    val compressedImageFile = Compressor.compress(context, File(fileUri?.path.toString())) {
        quality(50) // combine with compressor constraint
        size(maxFileSize = 600000)
        format(Bitmap.CompressFormat.JPEG)
    }
    return Uri.fromFile(compressedImageFile)
}

@Throws(IOException::class)
fun getFilePathFromUri(uri: Uri, context: Context?): Uri? {
    val fileName: String? = getFileName(uri, context)
    val file = File(context?.externalCacheDir, fileName)
    file.createNewFile()
    FileOutputStream(file).use { outputStream ->
        context?.contentResolver?.openInputStream(uri).use { inputStream ->
            copyFile(inputStream, outputStream)
            outputStream.flush()
        }
    }
    return Uri.fromFile(file)
}

@Throws(IOException::class)
private fun copyFile(`in`: InputStream?, out: OutputStream) {
    val buffer = ByteArray(1024)
    var read: Int? = null
    while (`in`?.read(buffer).also({ read = it!! }) != -1) {
        read?.let { out.write(buffer, 0, it) }
    }
}//copyFile ends

fun getFileName(uri: Uri, context: Context?): String? {
    var fileName: String? = getFileNameFromCursor(uri, context)
    if (fileName == null) {
        val fileExtension: String? = getFileExtension(uri, context)
        fileName = "temp_file" + if (fileExtension != null) ".$fileExtension" else ""
    } else if (!fileName.contains(".")) {
        val fileExtension: String? = getFileExtension(uri, context)
        fileName = "$fileName.$fileExtension"
    }
    return fileName
}

fun getFileExtension(uri: Uri, context: Context?): String? {
    val fileType: String? = context?.contentResolver?.getType(uri)
    return MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType)
}

fun getFileNameFromCursor(uri: Uri, context: Context?): String? {
    val fileCursor: Cursor? = context?.contentResolver
        ?.query(uri, arrayOf<String>(OpenableColumns.DISPLAY_NAME), null, null, null)
    var fileName: String? = null
    if (fileCursor != null && fileCursor.moveToFirst()) {
        val cIndex: Int = fileCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (cIndex != -1) {
            fileName = fileCursor.getString(cIndex)
        }
    }
    return fileName
}