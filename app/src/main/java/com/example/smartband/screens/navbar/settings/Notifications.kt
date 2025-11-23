package com.example.smartband.screens.navbar.settings

import android.Manifest
import android.graphics.Paint
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.smartband.navigation.Screen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView
import com.example.smartband.R
import com.example.smartband.screens.navbar.bodyFont
import com.example.smartband.screens.navbar.headlineFont2
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun Notifications(navController: NavController) {
    Spacer(modifier = Modifier.height(23.dp))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
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

        Spacer(modifier = Modifier.height(3.dp))

        // --- Bar Graph ---
        Text(
            text = "Notifications Overview",
            fontSize = 20.sp,
            fontFamily = headlineFont2,
            modifier = Modifier.padding( 4.dp).offset(x = 14.dp)
        )

        val categories = listOf("Stressed", "Calm", "Amused")
        val values = listOf(5f, 3f, 7f) // static numbers

        Spacer(modifier = Modifier.height(10.dp))


        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
        ){
            androidx.compose.material.Text(
                text = "Your notification summary is here, view how notifications have been sent to you this week.",
                fontSize = 15.sp,
                fontFamily = bodyFont,
                letterSpacing = 0.02.em,
                lineHeight = 22.sp,
                color = Color.Gray,
                modifier = Modifier.padding(4.dp).offset(x = (12).dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))


        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding( 10.dp),
            factory = { context ->
                BarChart(context).apply {
                    val entries = values.mapIndexed { index, value ->
                        BarEntry(
                            index.toFloat(),
                            value
                        )
                    }
                    val dataSet = BarDataSet(entries, "Notifications").apply {
                        colors = listOf(
                            Color(0xFFFF9800).toArgb(), // red
                            Color(0xFF2ECC71).toArgb(), // green
                            Color(0xFF3498DB).toArgb()  // blue
                        )
                        valueTextSize = 12f
                    }
                    val barData = BarData(dataSet)
                    data = barData

                    xAxis.apply {
                        valueFormatter = IndexAxisValueFormatter(categories)
                        position = XAxis.XAxisPosition.BOTTOM
                        granularity = 1f
                        setDrawGridLines(false)
                        textColor = android.graphics.Color.BLACK
                    }

                    axisLeft.textColor = android.graphics.Color.BLACK
                    axisRight.isEnabled = false
                    description.isEnabled = false
                    legend.isEnabled = false
                    animateY(1000)
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
        ){
            androidx.compose.material.Text(
                text = "Latest Notifications",
                fontSize = 16.sp,
                fontFamily = bodyFont,
                letterSpacing = 0.02.em,
                lineHeight = 22.sp,
                color = Color.Black,
                modifier = Modifier.padding(4.dp).offset(x = (12).dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))


        val messages = listOf(
            "Stressed notification sent" to "13:15 PM",
            "Stressed notification sent" to "12:24 PM",
            "Calm notification sent" to "12:21 PM",
            "Amused notification sent" to "Yesterday 5:30 PM",
        )


        Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.purple), shape= RoundedCornerShape(16.dp))
                    .padding(15.dp)
            ) {
                messages.forEachIndexed { index, pair ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = pair.first, fontSize = 14.sp)
                        Text(text = pair.second, fontSize = 12.sp, color = Color.Gray)
                    }

                    // Add divider except for the last item
                    if (index < messages.size - 1) {
                        Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.fillMaxWidth(0.6f))
                    }
                }
            }

    }
}

@Composable
fun NotificationCard(label: String, count: Int, color: Color) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .padding(4.dp)
            .border(1.dp, Color.Black, shape = RoundedCornerShape(16.dp))
            .height(90.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = color
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = "$label\n$count",
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontFamily = headlineFont2
            )
            Spacer(modifier = Modifier.height(4.dp))

        }
    }
}
