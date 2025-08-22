package com.example.smartband.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.smartband.navigation.Screen
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ModalDrawer
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.smartband.R

@Composable
fun ChildInfo(navController: NavController){
    val headlineFont = FontFamily(Font(R.font.playfairdispla_black))
    val headlineFont2 = FontFamily(Font(R.font.playfairdisplay_bold))

    val bodyFont = FontFamily(Font(R.font.helmet_regular))
    var currentStep by remember { mutableIntStateOf(0) }
    val totalSteps = 4
    var parentName by remember { mutableStateOf("") }  // Holds the input text state
    var childName by remember { mutableStateOf("" ) }
    Scaffold(
        topBar = {

        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        )
        {
            Column(
                modifier = Modifier.fillMaxSize().padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                LinearProgressIndicator(
                    progress = { (currentStep + 1) / totalSteps.toFloat() },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(4.dp),
                    color = Color(0xFFA56ABD),
                    trackColor = Color.LightGray,
                    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                )

                when (currentStep) {
                    0 -> {
                        Text(
                            text = "Hey there !",
                            fontFamily = bodyFont,
                            color = Color.LightGray,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(
                                vertical = 9.dp
                            )
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth().offset(x=(-10).dp),
                            horizontalArrangement =Arrangement.Start
                        ){
                            IconButton(
                                onClick = { navController.navigate(Screen.SignIn.route) },
                                modifier = Modifier.size(53.dp) //the clickable area
                            ){
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Back icon",
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceEvenly
                        )
                        {
                            Text(
                                text = "My name \n is",
                                fontFamily = headlineFont2,
                                letterSpacing = 0.02.em,
                                fontSize = 32.sp
                            )

                            OutlinedTextField(
                                value = parentName,
                                onValueChange = { parentName = it },
                                label = { Text(" ") },
                                modifier = Modifier.fillMaxWidth(),
                            )

                            Text(
                                text = "My child's name \n is",
                                fontSize = 32.sp,
                                fontFamily = headlineFont2,
                                letterSpacing = 0.02.em
                            )
                            OutlinedTextField(
                                value = parentName,
                                onValueChange = { parentName = it },
                                label = { Text(" ") },
                                modifier = Modifier.fillMaxWidth(),
                            )

                            Spacer(modifier =Modifier.height(10.dp))

                            if (currentStep < totalSteps) {
                                Button(
                                    onClick = { currentStep++ },
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(Color(0xFFFED711)),
                                    modifier = Modifier.fillMaxWidth().height(42.dp)
                                ) {
                                    Text(
                                        text = "Next ",
                                        fontFamily = headlineFont,
                                        color = Color.Black,
                                        fontSize = 20.sp,
                                        letterSpacing = 0.02.em
                                    )
                                }
                            } else {
                                Button(
                                    onClick = { navController.navigate(Screen.Landing.route) },
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(Color(0xFFFED711)),
                                    modifier = Modifier.fillMaxWidth().height(42.dp)
                                ) {
                                    Text(
                                        text = "Finish ",
                                        fontFamily = headlineFont,
                                        color = Color.Black,
                                        fontSize = 20.sp,
                                        letterSpacing = 0.02.em
                                    )
                                }
                            }
                        }
                    }

                    1 -> {
                        Text(
                            text = "You Got This !",
                            fontFamily = bodyFont,
                            fontSize = 15.sp,
                            color = Color.LightGray,
                            modifier = Modifier.padding(
                                vertical = 9.dp
                            )
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth().offset(x=(-10).dp),
                            horizontalArrangement =Arrangement.Start
                        ){
                            IconButton(
                                onClick = { currentStep-- },
                                modifier = Modifier.size(53.dp) //the clickable area
                            ){
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Back icon",
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceEvenly
                        )
                        {
                            Text(
                                text = "Date of Birth of the Child",
                                fontSize = 32.sp,
                                fontFamily = headlineFont2,
                                letterSpacing = 0.02.em
                            )

                            OutlinedTextField(
                                value = parentName,
                                onValueChange = { parentName = it },
                                label = { Text(" ") },
                                modifier = Modifier.fillMaxWidth(),
                            )

                            Text(
                                text = "Gender",
                                fontSize = 32.sp,
                                fontFamily = headlineFont2,
                                letterSpacing = 0.02.em
                            )

                            GenderSelection()

                            Spacer(modifier =Modifier.height(10.dp))

                            if (currentStep < totalSteps) {
                                Button(
                                    onClick = { currentStep++ },
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(Color(0xFFFED711)),
                                    modifier = Modifier.fillMaxWidth().height(42.dp)
                                ) {
                                    Text(
                                        text = "Next ",
                                        fontFamily = headlineFont,
                                        color = Color.Black,
                                        fontSize = 20.sp,
                                        letterSpacing = 0.02.em
                                    )
                                }
                            } else {
                                Button(
                                    onClick = { navController.navigate(Screen.Landing.route) },
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(Color(0xFFFED711)),
                                    modifier = Modifier.fillMaxWidth().height(42.dp)
                                ) {
                                    Text(
                                        text = "Finish ",
                                        fontFamily = headlineFont,
                                        color = Color.Black,
                                        fontSize = 20.sp,
                                        letterSpacing = 0.02.em
                                    )
                                }
                            }
                        }
                    }

                    2 -> {

                        Text(
                            text = "Almost There...",
                            fontFamily = bodyFont,
                            fontSize = 15.sp,
                            color = Color.LightGray,
                            modifier = Modifier.padding(
                                vertical = 9.dp
                            )
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth().offset(x=(-10).dp),
                            horizontalArrangement =Arrangement.Start
                        ){
                            IconButton(
                                onClick = { currentStep-- },
                                modifier = Modifier.size(53.dp) //the clickable area
                            ){
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Back icon",
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceEvenly
                        )
                        {
                            Text(
                                text = "When was your child clinically diagnosed with Autism Spectrum Disorder?",
                                fontSize = 32.sp,
                                textAlign = TextAlign.Left,
                                fontFamily = headlineFont2,
                                letterSpacing = 0.02.em,
                                lineHeight = 40.sp
                            )

                            DiagnosisYear()

                            Spacer(modifier = Modifier.height(10.dp))

                            if (currentStep < totalSteps) {
                                Button(
                                    onClick = { currentStep++ },
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(Color(0xFFFED711)),
                                    modifier = Modifier.fillMaxWidth().height(42.dp)
                                ) {
                                    Text(
                                        text = "Next ",
                                        fontFamily = headlineFont,
                                        color = Color.Black,
                                        fontSize = 20.sp,
                                        letterSpacing = 0.02.em
                                    )
                                }
                            } else {
                                Button(
                                    onClick = { navController.navigate(Screen.Landing.route) },
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(Color(0xFFFED711)),
                                    modifier = Modifier.fillMaxWidth().height(42.dp)
                                ) {
                                    Text(
                                        text = "Finish ",
                                        fontFamily = headlineFont,
                                        color = Color.Black,
                                        fontSize = 20.sp,
                                        letterSpacing = 0.02.em
                                    )
                                }
                            }
                        }
                    }
                        3 -> {

                            Text(
                                text = "You did it !",
                                fontFamily = bodyFont,
                                fontSize = 15.sp,
                                color = Color.LightGray,
                                modifier = Modifier.padding(
                                    vertical = 9.dp
                                )
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth().offset(x=(-10).dp),
                                horizontalArrangement =Arrangement.Start
                            ){
                                IconButton(
                                    onClick = { currentStep-- },
                                    modifier = Modifier.size(53.dp) //the clickable area
                                ){
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = "Back icon",
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceEvenly
                            )
                            {
                                Text(
                                    text = "What are your top priorities for your child now?",
                                    fontSize = 32.sp,
                                    textAlign = TextAlign.Left,
                                    fontFamily = headlineFont2,
                                    letterSpacing = 0.02.em,
                                    lineHeight = 40.sp
                                )

                                TopPriorities()

                                Spacer(modifier = Modifier.height(10.dp))

                                Button(
                                        onClick = { navController.navigate(Screen.Landing.route) },
                                        shape = RectangleShape,
                                        colors = ButtonDefaults.buttonColors(Color(0xFFFED711)),
                                        modifier = Modifier.fillMaxWidth().height(42.dp)
                                    ) {
                                        Text(
                                            text = "Next ",
                                            fontFamily = headlineFont,
                                            color = Color.Black,
                                            fontSize = 20.sp,
                                            letterSpacing = 0.02.em
                                        )
                                    }
                                }
                            }
                    }
                }
            }
        }
    }

@Composable
fun FilterChipGroup(
    options: List<String>,
    selectedOption: String?,
    onSelectionChanged: (String) -> Unit
) {

    FlowRow (
        //modifier = Modifier.height(300.dp).fillMaxWidth().horizontalScroll(rememberScrollState()).padding(8.dp)
        modifier = Modifier.wrapContentHeight().padding(8.dp),
)
    {
        options.forEach { option ->
            FilterChip(
                selected = option == selectedOption,
                onClick = { onSelectionChanged(option) },
                modifier = Modifier.padding(vertical = 6.dp),
                label = {
                    Text(
                        text= option,
                        modifier = Modifier.padding(horizontal=10.dp, vertical = 8.dp))
                        },
                leadingIcon = if (option == selectedOption) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
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
fun GenderSelection(){
    var selectedGender by remember { mutableStateOf<String?>(null) }

    FilterChipGroup(
        options = listOf("Male","Female","Other"),
        selectedOption = selectedGender,
        onSelectionChanged = {selectedGender = it},

    )
}

@Composable
fun DiagnosisYear(){
    var selectedYear by remember { mutableStateOf<String?>(null) }

    FilterChipGroup(
        options = listOf("2025","2024","2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "Earlier"),
        selectedOption = selectedYear,
        onSelectionChanged = {selectedYear = it},

        )
}

@Composable
fun TopPriorities(){
    var selectedPriority by remember { mutableStateOf<String?>(null) }

    FilterChipGroup(
        options = listOf("Improve Communication","Reduce Meltdowns", "Increase Independence", "Social Interaction", "Other"),
        selectedOption = selectedPriority,
        onSelectionChanged = {selectedPriority = it},

        )
}