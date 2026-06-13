package com.taxico.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.taxico.app.data.TranslationManager

@Composable
fun DriverDashboard() {
    var isOnline by remember { mutableStateOf(false) }
    var rangeRadius by remember { mutableStateOf(5f) }
    
    val sudan = LatLng(15.5007, 32.5599)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(sudan, 12f)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Map Background
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = true),
            uiSettings = MapUiSettings(zoomControlsEnabled = false)
        )

        // Top Status Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter)
                .clip(RoundedCornerShape(24.dp))
                .background(if (isOnline) Color.Black else Color.White.copy(alpha = 0.9f))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = if (isOnline) TranslationManager.getString("systemActive") else TranslationManager.getString("systemOffline"),
                    color = if (isOnline) Color.White else Color.Black,
                    fontWeight = FontWeight.Black,
                    fontSize = 14.sp
                )
                if (isOnline) {
                    Text(
                        text = TranslationManager.getString("scanningLive"),
                        color = Color(0xFFFFD600),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Switch(
                checked = isOnline,
                onCheckedChange = { isOnline = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFFFFD600),
                    uncheckedThumbColor = Color.Gray,
                    uncheckedTrackColor = Color.LightGray
                )
            )
        }

        // Bottom Control Panel
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            color = Color.White,
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .navigationBarsPadding()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = TranslationManager.getString("driverConsole"),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black
                    )
                    IconButton(onClick = { /* Refresh */ }) {
                        Icon(Icons.Default.Refresh, contentDescription = null)
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Range Radius: ${rangeRadius.toInt()} km",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                
                Slider(
                    value = rangeRadius,
                    onValueChange = { rangeRadius = it },
                    valueRange = 1f..50f,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFFFFD600),
                        activeTrackColor = Color(0xFFFFD600)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* Accept Ride Logic */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(24.dp),
                    enabled = isOnline
                ) {
                    Text(
                        text = TranslationManager.getString("acceptRide"),
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
