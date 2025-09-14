package com.example.smartband.screens

import android.R.attr.title
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import com.example.smartband.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.Typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.smartband.navigation.Screen
import com.example.smartband.screens.auth.headlineFont
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.LineHeightStyle
import com.example.smartband.screens.navbar.bodyFont
import com.example.smartband.screens.navbar.headlineFont2
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LandingPage(navController : NavController){

    var menuSelection: String? by remember { mutableStateOf ("")}

//    val parentFirstName = remember { mutableStateOf("") }
//    val uid = FirebaseAuth.getInstance().currentUser?.uid
//    val db = FirebaseFirestore.getInstance()
//
//    LaunchedEffect(uid) {
//        if (uid != null) {
//            db.collection("users").document(uid).get()
//                .addOnSuccessListener { document ->
//                    if (document.exists()) {
//                        parentFirstName.value = document.getString("firstName") ?: "Unknown User"
//
//                    }
//                }
//                .addOnFailureListener {
//                    parentFirstName.value = "Error loading"
//                }
//        }
//    }

    DetailedDrawerExample (
        navController = navController
    ){ innerPadding ->
        Box(
            modifier = Modifier
            .padding(innerPadding)
            .background(Color(0xFFFCE798))
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(120.dp)
                        .background(
                            Color(0xFFFCE798),
                            shape = RoundedCornerShape(16.dp)
                        )
                      //  .clip(RoundedCornerShape(16.dp))
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Eliana is ",
                            fontSize = 17.sp,
                            color = Color.Gray,
                            fontFamily = bodyFont
                        )
                    }


                    Row() {
                        Text(
                            text = "happy",
                            fontSize =25.sp,
                            letterSpacing = 0.02.em,
                            fontFamily = headlineFont
                        )

                        // insert the icon to show the mood
                        Spacer(modifier = Modifier.width(10.dp))

                        Icon(
                            imageVector = Icons.Default.SentimentSatisfied,
                            contentDescription = "Mood: Happy",
                            tint = Color.DarkGray,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(
                            Color(0xFFFDFCFC),
                            shape = RoundedCornerShape(
                                topStart = 30.dp,
                                topEnd = 30.dp
                            )
                        )
                        .padding(10.dp),
                  horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Real-time data collected
                    DashboardGrid()

                    Spacer(modifier = Modifier.height(10.dp))

                    // Parent tip of the day
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .padding(horizontal = 10.dp)
                    )
                    {
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(){
                            Image(
                                painter = painterResource(id = R.drawable.bulb), // your drawable
                                contentDescription = "My bulb",
                                modifier = Modifier.size(20.dp) // adjust size as needed
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = "Parenting Tip",
                                letterSpacing = 0.03.em,
                                fontFamily = bodyFont,
                                fontSize = 15.sp,
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                       DailyTipCard()

                    }
                }
            }
        }
    }
}
@Composable
fun SlidingBottomNavBar(navController: NavHostController) {
    val items = listOf(
        Screen.Analytics,
        Screen.Landing,
        Screen.Profile
    )

    var selectedIndex by remember { mutableStateOf(0) }

    // Circle offset animation
    val indicatorOffset by animateDpAsState(
        targetValue = (selectedIndex * 100).dp, // 👈 adjust slot width for spacing
        label = "circleIndicator"
    )

    Box(
        modifier = Modifier.fillMaxWidth(),   // parent takes full width
        contentAlignment = Alignment.Center   // child centered inside

    ){


    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(60.dp)
            .offset(y = (-10).dp)
            .clip(RoundedCornerShape(35.dp))
            .background(Color(0xFF1E1E1E)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(0.9f),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, screen ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    // Circle background (sliding)
                    if (selectedIndex == index) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .offset(x = 0.dp, y = 0.dp)
                                .background(Color(0xFFA56ABD), shape = CircleShape)
                        )
                    }

                    IconButton(
                        onClick = {
                            selectedIndex = index
                            navController.navigate(screen.route)
                        }
                    ) {
                        Icon(
                            imageVector = when (screen) {
                                is Screen.Profile -> Icons.Default.Person
                                is Screen.Landing -> Icons.Default.Home
                                is Screen.Analytics -> Icons.Default.AutoGraph
                                else -> Icons.Default.History
                            },
                            contentDescription = screen.route,
                            tint = if (selectedIndex == index) Color.White else Color.Gray,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            }
        }
    }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashboardGrid() {
    Column(
        modifier = Modifier
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FlowRow (
            //modifier = Modifier.height(300.dp).fillMaxWidth().padding(8.dp)
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(1.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        )
        {
            DashboardCard(
                title="Heart Rate",
                value ="82 bpm",
                icon = Icons.Default.Favorite,
                backgroundColor = Color(0xFFF6E7FD),
                modifier = Modifier.weight(1f)
            )
            DashboardCard(
                title ="Mood",
                value ="Happy",
                icon = Icons.Default.Mood,
                backgroundColor = Color(0xFFFFFFFF),
                modifier = Modifier.weight(1f)
            )

            DashboardCard(
                title = "Sleep",
                value ="7 hrs",
                icon = Icons.Default.Hotel,
                backgroundColor = Color(0xFFFFFFFF),
                modifier = Modifier.weight(1f))
            DashboardCard(
                title ="Steps",
                "10,200",
                icon = Icons.Default.DirectionsWalk,
                backgroundColor = Color(0xFFFFFFFF),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    value: String,
    icon: ImageVector,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(110.dp)
            .height(90.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor // 👈 background color here
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = Color.DarkGray,
                    letterSpacing = 0.02.em,
                    fontFamily = bodyFont,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(15.dp)
                )
            }

            Text(
                text = value,
                fontSize = 20.sp,
                fontFamily = bodyFont
            )
        }
    }
}


@Composable
fun FilterChipsMenu(
    options: List<String>,
    selectedOption: String?,
    onSelectionChanged: (String) -> Unit
) {

    FlowRow (
        //modifier = Modifier.height(300.dp).fillMaxWidth().padding(8.dp)
        modifier = Modifier
            .wrapContentHeight()
            .horizontalScroll(rememberScrollState())
            .padding(4.dp),
    )
    {
        options.forEach { option ->
            FilterChip(
                selected = option == selectedOption,
                onClick = { onSelectionChanged(option) },
                modifier = Modifier
                        .padding(vertical = 3.dp),
                shape = RoundedCornerShape(19.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color.Black,   // 👈 background when selected
                    containerColor = Color.White
                ),
                label = {
                    Text(
                        text= option,
                        fontSize = 13.sp,
                        color = if (option == selectedOption) Color.White else Color.Black,
                        modifier = Modifier.padding(horizontal=4.dp, vertical = 7.dp))
                },
                leadingIcon = if (option == selectedOption) {
                    {
//                        Icon(
//                            imageVector = Icons.Filled.Done,
//                            contentDescription = "Done icon",
//                            modifier = Modifier.size(FilterChipDefaults.IconSize)
//                        )
                    }
                } else {
                    null
                },
            )
            Spacer(modifier = Modifier.width(13.dp))
        }

    }

}

@Composable
fun MenuSelection(onMenuSelected: (String?) -> Unit){
    var selectedMenu by remember { mutableStateOf<String?>(null) }

    FilterChipsMenu(
        options = listOf("Emotions", "Location", "Other"),
        selectedOption = selectedMenu,
        onSelectionChanged = {
            selectedMenu = it
            onMenuSelected(it)
        },

        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedDrawerExample(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit,
) {
    val drawerState = androidx.compose.material3.rememberDrawerState(
        initialValue = androidx.compose.material3.DrawerValue.Closed
    )
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        modifier = Modifier
            .background(Color(0xFFECDFF5)),

        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(270.dp)
                    .background(Color(0xFFECDFF5)),
                drawerContainerColor = Color.Transparent // stops default M3 background

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFECDFF5))
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth().height(100.dp).padding(horizontal = 15.dp),
                        horizontalArrangement = Arrangement.Start
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.boy), // replace with your image
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                        )
                    }

                    Spacer(Modifier.height(15.dp))

                    Text("Theo Buyale",
                        fontSize = 20.sp,
                        fontFamily = headlineFont2,
                        letterSpacing = 0.02.em,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(Modifier.height(14.dp))

                    Text("22/08/2021",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        letterSpacing = 0.02.em,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(Modifier.height(15.dp))

                    HorizontalDivider(modifier = Modifier.fillMaxWidth(0.7f).padding(horizontal = 14.dp))

                    NavigationDrawerItem(
                        label = { Text("Profile",fontFamily = bodyFont, fontSize = 15.sp) },
                        selected = false,
                        icon = { Icon(Icons.Outlined.Person, contentDescription = null) },
                        badge = { Text("") }, // Placeholder
                        onClick = { navController.navigate(Screen.Profile.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                        }
                    )

                    NavigationDrawerItem(
                        label = { Text("Reports",fontFamily = bodyFont, fontSize = 15.sp) },
                        selected = false,
                        icon = { Icon(Icons.Outlined.Analytics, contentDescription = null) },
                        badge = { Text("") }, // Placeholder
                        onClick = { navController.navigate(Screen.Analytics.route) }
                    )

                    NavigationDrawerItem(
                        label = { Text("Location",fontFamily = bodyFont, fontSize = 15.sp) },
                        selected = false,
                        icon = { Icon(Icons.Outlined.Map, contentDescription = null) },
                        badge = { Text("") }, // Placeholder
                        onClick = { /* Handle click */ }
                    )

                    NavigationDrawerItem(
                        label = { Text("Settings",fontFamily = bodyFont, fontSize = 15.sp)},
                        selected = false,
                        icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                        badge = { Text("20") }, // Placeholder
                        onClick = { /* Handle click */ }
                    )

                    NavigationDrawerItem(
                        label = { Text("Help and feedback",fontFamily = bodyFont, fontSize = 15.sp)},
                        selected = false,
                        icon = { Icon(Icons.AutoMirrored.Outlined.Help, contentDescription = null) },
                        onClick = { /* Handle click */ },
                    )

                    Spacer(Modifier.height(30.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        //verticalAlignment = Alignment.BottomCenter,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Button(
                            onClick = { navController.navigate(Screen.Landing.route) },
                            colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                               .border( width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp) )
                                .height(42.dp)
                        ) {
                            Icon(
                            imageVector = Icons.Default.ExitToApp, // 👈 built-in Material icon
                            contentDescription = "Logout",
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp) // adjust icon size
                        )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "Log Out ",
                                fontFamily = headlineFont2,
                                color = Color.Black,
                                fontSize = 15.sp,
                                letterSpacing = 0.02.em
                            )
                        }
                    }
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFFCE798),
                    )
                )
            },
            modifier = Modifier.fillMaxSize(),
            containerColor = Color(0xFFFCE798),
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ViewPage(){
    LandingPage(navController = rememberNavController())
}