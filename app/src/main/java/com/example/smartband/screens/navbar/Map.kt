package com.example.smartband.screens.navbar

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.example.smartband.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.json.JSONObject
import android.app.AlertDialog
import android.content.DialogInterface
import android.widget.Toast  // Only if you use Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import com.example.smartband.influx.InfluxLocationModel


@Composable
fun Map(
    navController: NavController, viewModel : InfluxLocationModel
) {
    val context = LocalContext.current
    var parentLocation by remember { mutableStateOf(LatLng(0.0, 0.0)) }

    var permissionGranted by remember { mutableStateOf(false) }
    

    val childLocation by viewModel.childLocation.collectAsState()

    // Request location permission
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionGranted = granted
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            permissionGranted = true
        }
    }


    val apiKey = context.packageManager
        .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        .metaData.getString("com.google.android.geo.API_KEY") ?: ""

    val scope = rememberCoroutineScope()

    var path by remember { mutableStateOf<List<LatLng>>(emptyList()) }
    // Pick the last valid coordinates (non-zero)


    LaunchedEffect(permissionGranted) {
        if (permissionGranted) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            val location = fusedLocationClient.lastLocation.await()
            location?.let {
                parentLocation = LatLng(it.latitude, it.longitude)
            }
        }
    }


    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(childLocation?: parentLocation ?:LatLng(0.0, 0.0), 14f)
        }
    ) {
        // Child marker
        childLocation?.let { childLatLng ->
            val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.orphanage)
            val smallMarker = Bitmap.createScaledBitmap(bitmap, 130, 130, false)
            val markerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker)

            Marker(
                state = MarkerState(position = childLatLng),
                title = "Child Location",
                icon = markerIcon
            )
        }

        // Parent marker (phone)
        parentLocation?.let { parentLatLng ->
            Marker(
                state = MarkerState(position = parentLatLng),
                title = "Parent Location",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
            )
        }

        // Optional: Draw path polyline if available
        if (path.isNotEmpty()) {
            Polyline(
                points = path,
                color = Color.Black,
                width = 8f
            )
        }
    }
 }


