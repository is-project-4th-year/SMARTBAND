package com.example.smartband.screens.auth

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.LocalTextStyle
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.smartband.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.Calendar

@Composable
fun ChildInfo(navController: NavController){
    val headlineFont = FontFamily(Font(R.font.playfairdispla_black))
    val headlineFont2 = FontFamily(Font(R.font.playfairdisplay_bold))

    val bodyFont = FontFamily(Font(R.font.helmet_regular))
    var currentStep by remember { mutableIntStateOf(0) }
    val totalSteps = 4
    var parentFirstName by remember { mutableStateOf("") }  // Holds the input text state
    var parentSurname by remember { mutableStateOf("") }  // Holds the input text state
    var childFirstName by remember { mutableStateOf("" ) }
    var childSurname by remember { mutableStateOf("" ) }
    val childDob by remember { mutableStateOf("")}
    var childGender: String? by remember { mutableStateOf ("")}
    var diagnosisYear by remember { mutableStateOf<String?>(null) }
    var priority: String? by remember { mutableStateOf("")}
    var currentChildDocId by remember { mutableStateOf<String?>(null) }
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val db = FirebaseFirestore.getInstance()

    Scaffold(
        topBar = {

        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding).background(Color.White)
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(x = (-10).dp),
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
                                text = "Setup Profile",
                                fontFamily = headlineFont2,
                                fontSize = 24.sp,
                                color = Color.LightGray,
                            )

                            Text(
                                text = "My name is,",
                                fontFamily = headlineFont2,
                                letterSpacing = 0.02.em,
                                fontSize = 32.sp
                            )

                            MinimalUnderlineTextField(
                                value = parentFirstName,
                                onValueChange = { parentFirstName = it },
                                placeholder = "First Name"
                            )

                            MinimalUnderlineTextField(
                                value = parentSurname,
                                onValueChange = { parentSurname = it },
                                placeholder = "Surname"
                            )

                            Text(
                                text = "My child's name is",
                                fontSize = 32.sp,
                                fontFamily = headlineFont2,
                                letterSpacing = 0.02.em
                            )

                            MinimalUnderlineTextField(
                                value = childFirstName,
                                onValueChange = {childFirstName = it},
                                placeholder = "First Name"
                            )

                            MinimalUnderlineTextField(
                                value = childSurname,
                                onValueChange = {childSurname = it},
                                placeholder = "Surname"
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            val context = LocalContext.current
                            if (currentStep < totalSteps) {
                                Button(
                                    onClick = {
                                    //    currentStep++
                                        if (uid != null) {
                                            val parentData = hashMapOf(
                                                "parentFirstName" to parentFirstName,
                                                "parentSurname" to parentSurname
                                            )

                                            db.collection("users").document(uid)
                                                .set(parentData, SetOptions.merge())

                                            // ✅ Collect child details from your input fields (replace with actual state variables)
                                            val childData = hashMapOf(
                                                "childFirstName" to childFirstName,
                                                "childSurname" to childSurname,
                                            )

                                            if (currentChildDocId == null) { // ✅ First time: create new child document
                                                db.collection("users").document(uid)
                                                    .collection("children")   // subcollection
                                                    .add(childData)
                                                    .addOnSuccessListener { docRef ->
                                                        Log.d("Firestore", "Child created with ID: ${docRef.id}")
                                                        // 🔑 Save this child ID to a variable for later pages
                                                        currentChildDocId = docRef.id
                                                        currentStep++
                                                    }
                                                    .addOnFailureListener { e ->
                                                        Log.e("Firestore", "Error saving child", e)
                                                    }
                                        }
                                        }

                                        else{
                                            Toast.makeText(context,"No uid", Toast.LENGTH_SHORT).show()
                                        }
                                              },
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(Color(0xFFFED711)),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(42.dp)
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
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(42.dp)
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
                        var selectedDay by remember { mutableStateOf(1) }
                        var selectedMonth by remember { mutableStateOf("Jan") }
                        var selectedYear by remember { mutableStateOf(2000) }


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
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(x = (-10).dp),
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
                                text = "Child Profile",
                                fontFamily = headlineFont2,
                                fontSize = 24.sp,
                                color = Color.LightGray,
                            )

                            Text(
                                text = "Date of Birth",
                                fontSize = 32.sp,
                                fontFamily = headlineFont2,
                                letterSpacing = 0.02.em
                            )


                            DateSpinner { day, month, year ->
                                selectedDay = day
                                selectedMonth = month
                                selectedYear = year
                            }

                        // Later, create a formatted string
                            val dobString = "%02d/%s/%04d".format(selectedDay, selectedMonth, selectedYear)

                            Text(
                                text = "Gender",
                                fontSize = 32.sp,
                                fontFamily = headlineFont2,
                                letterSpacing = 0.02.em
                            )

                            GenderSelection( onGenderSelected = { gender ->
                                childGender = gender  // save selected value here
                            })

                            Spacer(modifier =Modifier.height(10.dp))

                            val updateData = hashMapOf(
                                "childDob" to dobString,
                                "childGender" to childGender,
                            )

                            if (currentStep < totalSteps) {
                                Button(
                                    onClick = {
                                      //  currentStep++
                                        if (uid != null && currentChildDocId != null) {
                                        db.collection("users").document(uid)
                                            .collection("children")
                                            .document(currentChildDocId!!)  // use the same doc ID
                                            .set(updateData, SetOptions.merge())  // merge keeps existing fields
                                            .addOnSuccessListener {
                                                currentStep++
                                                Log.d("Firestore", "Child updated successfully")
                                            }
                                            .addOnFailureListener { e ->
                                                Log.e("Firestore", "Error updating child", e)
                                            }
                                    }
                                              },
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(Color(0xFFFED711)),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(42.dp)
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
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(42.dp)
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(x = (-10).dp),
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

                            DiagnosisYear(
                                onYearSelected = { year ->
                                    diagnosisYear = year  // save selected value here
                                }
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            val updateData = hashMapOf(
                                "diagnosisYear" to diagnosisYear,
                            )

                            if (currentStep < totalSteps) {
                                Button(
                                    onClick = {
                                        if (uid != null && currentChildDocId != null) {
                                            db.collection("users").document(uid)
                                                .collection("children")
                                                .document(currentChildDocId!!)  // use the same doc ID
                                                .set(updateData, SetOptions.merge())  // merge keeps existing fields
                                                .addOnSuccessListener {
                                                    Log.d("Firestore", "Child updated successfully")
                                                    currentStep++
                                                }
                                                .addOnFailureListener { e ->
                                                    Log.e("Firestore", "Error updating child", e)
                                                }
                                        }
                                    },
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(Color(0xFFFED711)),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(42.dp)
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
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(42.dp)
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
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .offset(x = (-10).dp),
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

                                TopPriorities(
                                    onPrioritySelected = { selectedPriority ->
                                        priority = selectedPriority
                                    }
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                val updateData = hashMapOf(
                                    "priority" to priority,
                                )

                                Button(
                                        onClick = { navController.navigate(Screen.Landing.route) },
                                        shape = RectangleShape,
                                        colors = ButtonDefaults.buttonColors(Color(0xFFFED711)),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(42.dp)
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
        modifier = Modifier
            .wrapContentHeight()
            .padding(8.dp),
)
    {
        options.forEach { option ->
            FilterChip(
                selected = option == selectedOption,
                onClick = { onSelectionChanged(option) },
                modifier = Modifier.padding(vertical = 6.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFA56ABD).copy(0.25f)
                ),
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
fun GenderSelection(onGenderSelected: (String?) -> Unit){
    var selectedGender by remember { mutableStateOf<String?>(null) }

    FilterChipGroup(
        options = listOf("Male","Female","Other"),
        selectedOption = selectedGender,
        onSelectionChanged = {
            selectedGender = it
            onGenderSelected(it) },

    )
}

@Composable
fun DiagnosisYear(onYearSelected: (String?) -> Unit){
    var selectedYear by remember { mutableStateOf<String?>(null) }

    FilterChipGroup(
        options = listOf("2025","2024","2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "Earlier"),
        selectedOption = selectedYear,
        onSelectionChanged = {
            selectedYear = it
            onYearSelected(it) },

        )
}

@Composable
fun TopPriorities( onPrioritySelected: (String?) -> Unit){
    var selectedPriority by remember { mutableStateOf<String?>(null) }

    FilterChipGroup(
        options = listOf("Improve Communication","Reduce Meltdowns", "Increase Independence", "Social Interaction", "Other"),
        selectedOption = selectedPriority,
        onSelectionChanged = {selectedPriority = it
                             onPrioritySelected(it)},

        )
}

@Composable
fun MinimalUnderlineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Type here…"
) {
    Column(Modifier.fillMaxWidth()) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.onSurface
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),

            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                ) {
                    if (value.isEmpty()) {
                        Text(placeholder,
                            fontSize = 28.sp,
                            color = Color.Gray)
                    }
                    innerTextField()
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .cursorWidth(3.dp)
        )
        // underline
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(1.dp)
                .background(MaterialTheme.colorScheme.outline)
        )
    }
}

fun Modifier.cursorWidth(width: Dp): Modifier = this.then(
    Modifier.drawBehind {
        // this doesn’t override the default cursor directly,
        // but we can fake it if needed by disabling the built-in one
    }
)

@SuppressLint("FrequentlyChangingValue")
@Composable
fun DateSpinner(
    onDateSelected: (day: Int, month: String, year: Int) -> Unit
) {
    val days = (1..31).toList()
    val months = listOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )
    val years = (1900..2100).toList()

    val dayState = rememberLazyListState(initialFirstVisibleItemIndex = 0)
    val monthState = rememberLazyListState(initialFirstVisibleItemIndex = 0)
    val yearState = rememberLazyListState(initialFirstVisibleItemIndex = 100) // e.g. start ~2000

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SpinnerColumn(days.map { it.toString() }, dayState)
        SpinnerColumn(months, monthState)
        SpinnerColumn(years.map { it.toString() }, yearState)
    }

    // Whenever you want the selected values:
    LaunchedEffect(
        dayState.firstVisibleItemIndex,
        monthState.firstVisibleItemIndex,
        yearState.firstVisibleItemIndex
    ) {
        val day = days.getOrNull(dayState.firstVisibleItemIndex) ?: 1
        val month = months.getOrNull(monthState.firstVisibleItemIndex) ?: "Jan"
        val year = years.getOrNull(yearState.firstVisibleItemIndex) ?: 2000
        onDateSelected(day, month, year)
    }
}

@Composable
fun SpinnerColumn(items: List<String>, state: LazyListState) {
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = state)
    Box(
        modifier = Modifier
            .width(80.dp)
            .fillMaxHeight()
            .border(1.dp, Color.Gray)
    ) {
        LazyColumn(
            state = state,
            flingBehavior = flingBehavior,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            items(items.size) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = items[index], fontSize = 20.sp)
                }
            }
        }
        // highlight line for "selected"
        // highlight center row
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(40.dp)
                .background(Color.Transparent)
                .border(2.dp, Color.Gray)
        )

        // faint overlays above & below center
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.5f) // top half
                .background(Color(0xFFA56ABD).copy(alpha = 0.2f))
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.5f) // bottom half
                .background(Color(0xFFA56ABD).copy(alpha = 0.2f))
        )
    }
}
