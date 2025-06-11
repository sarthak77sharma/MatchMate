package com.example.matchmate.historyscreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.data.room.ProfilesEntity
import com.example.matchmate.MatchMateAppBar
import com.example.matchmate.R
import com.example.matchmate.matchscreen.MyItemCard

@Composable
fun HistoryScreen() {

    val viewModel: HistoryViewModel = hiltViewModel()
    val profilesState = viewModel.consumableState().collectAsState()

    LaunchedEffect(key1 = Unit) {
        Log.d("sarthak", "viewModel.getProfileMatches(): ")
        //viewModel.fetchMatchProfiles()
        viewModel.getProfileHistory()
    }

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
        topBar = {
            MatchMateAppBar(title = "History")
        },
    ) {paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(top = paddingValues.calculateTopPadding()),
            contentPadding = PaddingValues(
                horizontal = 8.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            items(profilesState.value.matchProfiles,
                key = { item -> item.userId }
            ) { itemData ->

                Log.d("sarthak", "MyListScreen: ${itemData.name}")
                MyItemCard(item = itemData)
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

@Composable
fun MyItemCard(
    item: ProfilesEntity
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
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = item.city,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {



                if(item.status == 0){
                    Text(
                        text = "Rejected :(",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color(0XFFDD4141)
                    )
                } else {
                    Text(
                        text = "Accepted :)",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color(0xEA5BDD41)
                    )
                }
            }

        }
    }
}