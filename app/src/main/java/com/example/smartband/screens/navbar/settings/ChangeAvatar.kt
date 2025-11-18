package com.example.smartband.screens.navbar.settings

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smartband.R
import com.google.firebase.auth.FirebaseAuth
import com.example.smartband.data.AvatarViewModel
import com.example.smartband.navigation.Screen
import com.example.smartband.screens.auth.headlineFont
import com.example.smartband.screens.navbar.bodyFont
import com.example.smartband.screens.navbar.headlineFont2

@Composable
fun ChangeAvatar(navController : NavController, viewModel: AvatarViewModel) {
    val selectedAvatar by viewModel.selectedAvatar.collectAsState()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

    val avatars = listOf(
        R.drawable.woman_1,
        R.drawable.woman_2,
        R.drawable.woman_4,
        R.drawable.man_1,
        R.drawable.man_2,
        R.drawable.man_3,
        R.drawable.man_4,
        R.drawable.woman_3
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(11.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            IconButton(
                onClick = { navController.navigate(Screen.Profile.route) },
                modifier = Modifier.size(53.dp) //the clickable area
            ){
                androidx.compose.material3.Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back icon",
                    modifier = Modifier.size(32.dp)
                )
            }

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Change avatar",
                    fontSize = 23.sp,
                    letterSpacing = 0.02.em,
                    textAlign = TextAlign.Start,
                    fontFamily = headlineFont,
                    modifier = Modifier.offset(x = (15).dp).padding(horizontal = 15.dp)
                )

                Spacer(modifier = Modifier.width(13.dp))

                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(26.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Refresh your profile image with a fun new avatar",
                    fontSize = 15.sp,
                    letterSpacing = 0.02.em,
                    textAlign = TextAlign.Start,
                    fontFamily = bodyFont,
                    color = Color.Gray,
                    modifier = Modifier.offset(x=(15).dp).padding(horizontal = 15.dp, vertical = 10.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    colorResource(R.color.yellow),
                    shape = RoundedCornerShape(
                        topStart = 30.dp,
                        topEnd = 30.dp
                    )
                ),
        ) {
            AvatarSelectionScreen(
                avatars = avatars,
                selectedAvatar = selectedAvatar,
                onAvatarSelected = { avatar ->
                    viewModel._selectedAvatar.value = avatar // just update selection locally
                }
            )

        }
    }
    }


@Composable
fun AvatarSelectionScreen(
    avatars: List<Int>, // list of drawable resource IDs
    selectedAvatar: Int?,
    onAvatarSelected: (Int) -> Unit
) {
    var tempSelectedAvatar by remember { mutableStateOf(selectedAvatar) }
    val context = LocalContext.current
    val viewModel: AvatarViewModel = viewModel()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(avatars) { avatarRes ->
            val isSelected = avatarRes == tempSelectedAvatar // check current temp selection

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .size(85.dp)
                    .border(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) Color.Black else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable { tempSelectedAvatar = avatarRes }
                    .clip(CircleShape),
           // contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = painterResource(id = avatarRes),
                    contentDescription = "Avatar",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Inside
                )
            }
        }


    }
    Spacer(modifier = Modifier.height(20.dp))

    Row(
        modifier = Modifier.fillMaxWidth().offset(y = 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                tempSelectedAvatar?.let { avatar ->
                    viewModel.selectAvatar(avatar)
                    Toast.makeText(context, "Profile updated!", Toast.LENGTH_SHORT).show()
                }
            },

            colors = ButtonDefaults.buttonColors(Color(0xFF000000)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(16.dp)
                )
                .height(42.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp, // 👈 built-in Material icon
                contentDescription = "update avatar",
                tint = Color.White,
                modifier = Modifier.size(18.dp) // adjust icon size
            )

            Spacer(modifier = Modifier.width(8.dp))

            androidx.compose.material3.Text(
                text = "Update Avatar",
                fontFamily = headlineFont2,
                color = Color.White,
                fontSize = 15.sp,
                letterSpacing = 0.02.em
            )
        }
    }
}
