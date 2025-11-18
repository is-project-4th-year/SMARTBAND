package com.example.smartband.screens

import android.Manifest
import android.R.attr.title
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import com.example.smartband.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import com.example.smartband.screens.navbar.bodyFont
import com.example.smartband.screens.navbar.headlineFont2
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartband.influx.InfluxViewModel
import androidx.compose.runtime.collectAsState
import com.example.smartband.influx.SensorReading
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.outlined.Factory
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import com.example.smartband.screens.navbar.InfluxDataScreen
import com.example.smartband.screens.navbar.settings.NotificationHelper
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LandingPage(navController : NavController, viewModel: InfluxViewModel) {

    var menuSelection: String? by remember { mutableStateOf("") }

    val parentFirstName = remember { mutableStateOf("") }
    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    val db = FirebaseFirestore.getInstance()

    val sensorData by viewModel.sensorData.collectAsState()
    var currentEmotion by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()

    DetailedDrawerExample(
        navController = navController,
        uid = uid,
        db = db

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.yellow))
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(
                            colorResource(R.color.yellow),
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
                            text = "Theo is",
                            fontSize = 17.sp,
                            color = Color.Gray,
                            fontFamily = bodyFont
                        )
                    }


                    Row() {
                        LatestEmotionDisplay(sensorData = sensorData)
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
                        .fillMaxSize()
                        .background(
                            Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(
                                topStart = 30.dp,
                                topEnd = 30.dp
                            )
                        )
                        .offset(y = 10.dp)
                        .verticalScroll(scrollState)
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Real-time data collected
                    DashboardGrid(sensorData = sensorData)

                    Spacer(modifier = Modifier.height(10.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.94f)
                            .height(170.dp)
                            .background(colorResource(R.color.purple), shape = RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .padding(10.dp)

                    )
                    {
                        Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Weekly Report",
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    letterSpacing = 0.02.em,
                                    fontFamily = headlineFont2,
                                    modifier = Modifier.offset(x = 12.dp)
                                )
                                Spacer(modifier = Modifier.width(17.dp))
                                Icon(
                                    imageVector = Icons.Outlined.Analytics,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(16.dp)
                                )
                            }


                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Spacer(modifier = Modifier.width(6.dp))

//                            DonutChartWithLegend(
//                                values = listOf(40f, 30f, 30f),
//                                colors = listOf(
//                                    Color(0xFFFFB57B), // Purple
//                                    Color(0xFF8BC34A), // Yellow
//                                    Color(0xFF03A9F4)  // Green
//                                ),
//                                labels = listOf("Calm", "Stressed", "Neutral")
//                            )
                            EmotionDonutChart(viewModel = viewModel)
                        }

                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.94f)
                            .height(130.dp)
                            .background(colorResource(R.color.white), shape = RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .padding(10.dp)

                    )
                    {
                        Spacer(modifier = Modifier.height(7.dp))

                        Text(
                            text = "Parenting Tip",
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontFamily = headlineFont2,
                            modifier = Modifier.offset(x = 13.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        //        // Longer parent tips
                        val tips = listOf(
                            "Consistent schedules reduce anxiety and make life predictable.",
                            "Care for yourself first to support your child effectively.",
                            "Use simple language and visuals to improve communication.",
                            "Mindfulness calms emotions and reduces stress.",
                            "Fun exercises like dancing improve mood and health.",
                            "Connect with parents for advice and emotional support.",
                            "Create sensory-friendly spaces with calming tools.",
                            "Visual schedules ease transitions and daily routines.",
                            "Learn rights, attend workshops, and advocate for your child."
                        )
                        // Generate a "daily index" based on system day
                        val dayOfYear = remember {
                            java.time.LocalDate.now().dayOfYear
                        }

                        // Shuffle tips in a stable way (seeded by dayOfYear)
                        val shuffledTips = remember(dayOfYear) {
                            tips.shuffled(Random(dayOfYear))
                        }
                        val todayTip = shuffledTips.first()


                        Text(
                            text = todayTip,
                            fontSize = 15.sp,
                            color = Color.DarkGray,
                            fontFamily = com.example.smartband.screens.bodyFont,
                            letterSpacing = 0.03.em,
                            lineHeight = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(14.dp)
                        )

                    }
                }
            }
        }
    }
}


@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
@Composable
fun LatestEmotionDisplay(sensorData: List<SensorReading>) {
    val latestEmotion = sensorData.lastOrNull()?.emotion ?: "Unknown"
    Text(
        text = "${latestEmotion ?: "Unknown"}",
        fontSize = 25.sp,
        letterSpacing = 0.02.em,
        fontFamily = headlineFont
        )

    val context = LocalContext.current

    LaunchedEffect(latestEmotion) {
        NotificationHelper.createChannel(context)
        if (latestEmotion == "Calm") {
            NotificationHelper.sendNotification(
                context,
                "Neuroband Alert",
                "Stress detected! Take a deep breath."
            )
        }
    }

}

@Composable
fun LatestMotionDisplay(sensorData: List<SensorReading>) {
    val latestMotion = sensorData.lastOrNull()?.status ?: "Unknown"
    Text(
        text = " ${latestMotion ?: "Unknown"}",
        fontSize = 25.sp,
        letterSpacing = 0.02.em,
        fontFamily = headlineFont
    )
}


@Composable
fun EmotionDonutChart(viewModel: InfluxViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val emotionMap by viewModel.emotionData.collectAsState()

    if (emotionMap.isNotEmpty()) {
        val labels = emotionMap.keys.toList()
        val values = emotionMap.values.map { it.toFloat() }

        val colors = listOf(
            Color(0xFF8E44AD),
            Color(0xFF3498DB),
            Color(0xFFE67E22),
        )

        DonutChartWithLegend(
            values = values,
            colors = colors.take(labels.size),
            labels = labels
        )
    } else {
        Text(
            text = "Loading emotion data...",
            color = Color.Gray,
            modifier = Modifier.padding(16.dp)
        )
    }
}


@Composable
fun DonutChartWithLegend(
    values: List<Float>,
    colors: List<Color>,
    labels: List<String>,
    size: Dp = 80.dp,
    strokeWidth: Dp = 18.dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 🔹 Legend (on the left)
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            labels.forEachIndexed { index, label ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8
                                .dp)
                            .clip(CircleShape)
                            .background(colors[index])
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = label,
                        fontSize = 14.sp,
                        fontFamily = com.example.smartband.screens.bodyFont,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(3.dp)
                    )
                }
            }
        }

        // 🔸 Donut Chart (on the right)
        Canvas(modifier = Modifier.size(size)) {
            val total = values.sum()
            val sweepAngles = values.map { 360 * it / total }

            var startAngle = -90f
            for (i in values.indices) {
                drawArc(
                    color = colors[i],
                    startAngle = startAngle,
                    sweepAngle = sweepAngles[i],
                    useCenter = false,
                    style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
                )
                startAngle += sweepAngles[i]
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashboardGrid(sensorData: List<SensorReading>) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        // 🟪 Left tall card
        DashboardCard(
            title = "Motion",
            value = sensorData.lastOrNull()?.status ?: "--",
            icon = Icons.Default.DirectionsWalk,
            backgroundColor = Color(0xFFFFFFFF),
            modifier = Modifier
                .weight(1f)
                .height(220.dp) // total height of two stacked cards
        )

        // 🟨 Right column with two smaller cards
        Column(
            verticalArrangement = Arrangement.spacedBy(17.dp),
            modifier = Modifier.weight(1f)
        ) {
            DashboardCard(
                title = "Heart Rate",
                value = sensorData.lastOrNull()?.heartRate?.toInt()?.toString() ?: "--",
                icon = Icons.Outlined.Favorite,
                backgroundColor = Color(0xFFFFFFFF),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            DashboardCard(
                title = "Temperature",
                value = sensorData.lastOrNull()?.temp?.toInt()?.toString() ?: "--",
                icon = Icons.Default.DeviceThermostat,
                backgroundColor = Color(0xFFFFFFFF),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
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
                    fontSize = 14.sp
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
                fontSize = 25.sp,
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

@Composable
fun ChildNameView(uid: String, db: FirebaseFirestore) {
    val childName = remember { mutableStateOf("Loading...") }
    val childSurname = remember { mutableStateOf("..") }

    LaunchedEffect(uid) {
        if (uid.isNotEmpty()) {
            db.collection("users").document(uid)
                .collection("children")
                .limit(1) // only first child
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val childDoc = querySnapshot.documents[0]
                        childName.value = childDoc.getString("childFirstName") ?: "Unknown"
                        childSurname.value = childDoc.getString("childSurname") ?: "Child"
                    } else {
                        childName.value = "No child found"
                    }
                }
                .addOnFailureListener {
                    childName.value = "Error loading child"
                }
        }

    }
        Text( text = childName.value + " "+ childSurname.value,
            fontSize = 20.sp,
            fontFamily = headlineFont2,
            letterSpacing = 0.02.em,
            modifier = Modifier.padding(horizontal = 16.dp)
        )


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedDrawerExample(
    navController: NavController,
    uid: String?,
    db: FirebaseFirestore,
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        modifier = Modifier
            .background(Color(0xFFECDFF5)),

        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(270.dp)
                    .background(colorResource(R.color.purple)),
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

                    ChildNameView(uid = uid ?: "UnknownUser", db = db)

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
                        }},
                        colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = Color.Transparent,
                        unselectedContainerColor = Color.Transparent)
                    )

                    NavigationDrawerItem(
                        label = { Text("Reports",fontFamily = bodyFont, fontSize = 15.sp) },
                        selected = false,
                        icon = { Icon(Icons.Outlined.Analytics, contentDescription = null) },
                        badge = { Text("") }, // Placeholder
                        onClick = { navController.navigate(Screen.Reports.route) },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color.Transparent,
                            unselectedContainerColor = Color.Transparent)
                    )

                    NavigationDrawerItem(
                        label = { Text("Location",fontFamily = bodyFont, fontSize = 15.sp) },
                        selected = false,
                        icon = { Icon(Icons.Outlined.Map, contentDescription = null) },
                        badge = { Text("") }, // Placeholder
                        onClick = { navController.navigate(Screen.Map.route)},
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color.Transparent,
                            unselectedContainerColor = Color.Transparent)
                    )

//                    NavigationDrawerItem(
//                        label = { Text("Settings",fontFamily = bodyFont, fontSize = 15.sp)},
//                        selected = false,
//                        icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
//                        badge = { Text("20") }, // Placeholder
//                        onClick = { /* Handle click */ },
//                        colors = NavigationDrawerItemDefaults.colors(
//                            selectedContainerColor = Color.Transparent,
//                            unselectedContainerColor = Color.Transparent)
//                    )

                    NavigationDrawerItem(
                        label = { Text("Help and feedback",fontFamily = bodyFont, fontSize = 15.sp)},
                        selected = false,
                        icon = { Icon(Icons.AutoMirrored.Outlined.Help, contentDescription = null) },
                        onClick = { /* Handle click */ },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color.Transparent,
                            unselectedContainerColor = Color.Transparent)
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
                        containerColor = colorResource(R.color.yellow),
                    )
                )
            },
            modifier = Modifier.fillMaxSize(),
            containerColor = colorResource(R.color.yellow),
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}

