package com.example.matchmate.myprofilescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.matchmate.R
import com.example.matchmate.matchscreen.MatchProfilesViewModel

@Composable
fun MyProfileScreen() {

    val viewModel: MatchProfilesViewModel = hiltViewModel()



    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var username by remember { mutableStateOf("") }

            Image (
                painter = painterResource(R.drawable.profile_img),
                contentDescription = "Login Image",
                modifier = Modifier
                    .size(400.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Sarthak Sharma , 27",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 26.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Delhi, India",
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(12.dp))

            /*Image(
                painter = painterResource(R.drawable.match_mate),
                contentDescription = "Login Image",
                modifier = Modifier
                    .width(200.dp)
                    .height(100.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Welcome to MatchMate!", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(100.dp))
            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                },
                label = {
                    Text(text = "username")
                },
            )

            OutlinedTextField(value = "", onValueChange = {}, label = {
                Text(text = "password")
            })
            Spacer(modifier = Modifier.height(40.dp))
            Button(onClick = {}) {
                Text(text = "Login")
            }*/
        }
    }
}