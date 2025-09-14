package com.example.smartband.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyTipCard() {
    // List of short parent tips
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

//        // Longer parent tips
//        val tips = listOf(
//            "Consistent daily routines helps children feel safe, reduces anxiety, and makes transitions between activities smoother.",
//            "Caring for your own physical and emotional needs gives you the energy and patience to better support your child.",
//            "Using clear language and gestures can improve your child’s understanding and strengthen your bond as you communicate.",
//            "Short breathing exercises or meditation help you and your child relax, reduce stress, and refocus on the moment together.",
//            "Fun exercises like dancing, walking, or swimming boost mood, improve focus, and promote overall health for both of you.",
//            "Connecting with other parents or support groups provides emotional strength, and reduces feelings of isolation in parenting.",
//            "Creating sensory-friendly spaces with calming lights, textures, or sounds can help your child feel more comfortable at home.",
//            "Visual schedules and social stories make transitions easier and daily routines more predictable for children.",
//            "Staying informed about autism resources empowers you to secure better opportunities for your child’s future."
//        )
    // Generate a "daily index" based on system day
    val dayOfYear = remember {
        java.time.LocalDate.now().dayOfYear
    }

    // Shuffle tips in a stable way (seeded by dayOfYear)
    val shuffledTips = remember(dayOfYear) {
        tips.shuffled(Random(dayOfYear))
    }

    // Pick today's tip (first one from shuffled list)
    val todayTip = shuffledTips.first()

    // UI card
    Card(
        modifier = Modifier
            .padding(horizontal = 13.dp, vertical = 1.dp)
            .fillMaxWidth()
            .height(88.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(1.dp), // subtle shadow
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF9F9F9)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = todayTip,
                fontSize = 15.sp,
                color = Color.DarkGray,
                fontFamily = bodyFont,
                letterSpacing = 0.02.em,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp)
            )
        }
    }



}

