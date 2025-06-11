package com.example.matchmate.matchscreen

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.data.room.ProfilesEntity
import com.example.matchmate.MatchMateAppBar
import com.example.matchmate.R
import com.example.matchmate.ui.theme.MatchMateTheme // Replace with your actual theme


@Composable
fun HomeScreen() {
    val viewModel: MatchProfilesViewModel = hiltViewModel()
    //val profilesList = viewModel.fetchMatchProfiles();
    //val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val profilesState = viewModel.consumableState().collectAsState()

    val localContext = LocalContext.current
    /*LaunchedEffect(key1 = Unit) {
        Log.d("sarthak", "viewModel.getProfileMatches(): ")
        //viewModel.fetchMatchProfiles()
        //viewModel.getMatchProfiles()
    }*/
    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
        topBar = {
            MatchMateAppBar(title = "Start Matching")
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
            contentPadding = PaddingValues(
                horizontal = 8.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {


            items(profilesState.value.matchProfiles,
                key = { item -> item.userId }
            ) { itemData ->

                if(profilesState.value.matchProfiles.indexOf(itemData) > profilesState.value.matchProfiles.size - 2){
                    viewModel.addNewProfiles()
                }
                Log.d("sarthak", "MyListScreen: ${itemData.name}")
                MyItemCard(item = itemData, {
                    //viewModel.addNewProfiles()

                    Toast.makeText(localContext, "ACCEPTED :)", Toast.LENGTH_SHORT).show()

                    viewModel.removeProfile(it,1)

                }, {
                    Toast.makeText(localContext, "REJECTED :(", Toast.LENGTH_SHORT).show()
                    viewModel.removeProfile(it,0)
                })
            }

            /*when (uiState) {
                is ProfilesStates.Success -> {
                    itemsIndexed((uiState as ProfilesStates.Success).matchProfileList) { id, it ->

                        MyItemCard(it,{ item ->
                            Log.d("sarthak", "MyListScreen: $item")
                            (uiState as ProfilesStates.Success).matchProfileList.remove(item)
                        },{
                            Log.d("sarthak", "MyListScreen: $it")
                            viewModel.removeProfile()
                            (uiState as ProfilesStates.Success).matchProfileList.remove(it)
                            Log.d("sarthak", "MyListScreen: ${(uiState as ProfilesStates.Success).matchProfileList.size}")



                        })

                    }

                    *//*items(
                    items = uiState.matchProfileList,
                    key = { it }
                ) { itemData ->
                    MyItemCard(item = itemData)
                }*//*
            }

            is ProfilesStates.Error -> {
                Log.e("sarthak", "Error: ")
            }

            is ProfilesStates.Ideal -> {
                Log.d("sarthak", "Ideal: ")
            }
        }*/


            item {
                Text(
                    "End of list",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MyListScreenPreview() {
    MatchMateTheme {
        HomeScreen()
    }
}

/*@Preview(showBackground = true)
@Composable
fun MyItemCardPreview() {
    MatchMateTheme {
        MyItemCard(item = MyItem(0, "Sample Title", "Sample description for the card."))
    }
}*/

@Composable
fun MyItemCard(
    item: ProfilesEntity,
    onAccept : (ProfilesEntity) -> Unit,
    onDecline : (ProfilesEntity) -> Unit,
    ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors()

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = item.profilePicUrl,
                contentDescription = "profile_image",
                placeholder = painterResource(R.drawable.baseline_sports_gymnastics_24),
                imageLoader =
                    ImageLoader
                        .Builder(context = LocalContext.current)
                        .crossfade(enable = true)
                        .build(),
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally),
                alignment = Alignment.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${item.name} , ${item.age}",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = item.city,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "MM Score: ${item.profileScore}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.End),
                color = Color.Red,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        Log.d("sarthak", "onClick:decline ")
                        onDecline(item)
                    },
                    shape = CircleShape,
                    modifier = Modifier.size(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0XFFDD4141)),
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cancel),
                        contentDescription = "Cancel",
                        modifier = Modifier.size(50.dp)
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                Button(
                    onClick = {

                        onAccept(item)
                    },
                    shape = CircleShape,
                    modifier = Modifier.size(53.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0XEA5BDD41)),
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.checkmark),
                        contentDescription = "Accept",
                        modifier = Modifier.size(50.dp)
                    )
                }
            }

        }
    }
}

/*
data class MyItem(
    val id: Int,
    val title: String,
    val description: String
)*/
enum class ButtonState { Pressed, Idle }
fun Modifier.shakeClickEffect() = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }

    val tx by animateFloatAsState(
        targetValue = if (buttonState == ButtonState.Pressed) 0f else -50f,
        animationSpec = repeatable(
            iterations = 2,
            animation = tween(durationMillis = 50, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    this
        .graphicsLayer {
            translationX = tx
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { }
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}


