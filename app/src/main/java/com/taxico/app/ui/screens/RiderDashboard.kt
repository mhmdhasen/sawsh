package com.taxico.app.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
fun RiderDashboard() {
    var pickup by remember { mutableStateOf<String?>(null) }
    var drop by remember { mutableStateOf<String?>(null) }
    var isSelectingPickup by remember { mutableStateOf(true) }
    var selectedOption by remember { mutableStateOf("shared-1") }
    
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

        // Top Search Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White.copy(alpha = 0.95f))
                .padding(12.dp)
        ) {
            SearchItem(
                icon = Icons.Default.LocationOn,
                iconColor = Color.Green,
                value = pickup ?: TranslationManager.getString("whereFrom"),
                isActive = isSelectingPickup,
                onClick = { isSelectingPickup = true }
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray.copy(alpha = 0.5f))
            SearchItem(
                icon = Icons.Default.Place,
                iconColor = Color.Red,
                value = drop ?: TranslationManager.getString("whereTo"),
                isActive = !isSelectingPickup,
                onClick = { isSelectingPickup = false }
            )
        }

        // Bottom Sheet (Ride Options)
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
                Text(
                    text = TranslationManager.getString("vehicleType"),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    RideOptionItem("shared-1", TranslationManager.getString("shared1"), selectedOption == "shared-1") { selectedOption = "shared-1" }
                    RideOptionItem("shared-2", TranslationManager.getString("shared2"), selectedOption == "shared-2") { selectedOption = "shared-2" }
                    RideOptionItem("private", TranslationManager.getString("privateRide"), selectedOption == "private") { selectedOption = "private" }
                    RideOptionItem("raksha", TranslationManager.getString("raksha"), selectedOption == "raksha") { selectedOption = "raksha" }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { /* Handle Confirmation */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD600)),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = TranslationManager.getString("confirm"),
                        color = Color.Black,
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun SearchItem(icon: androidx.compose.ui.graphics.vector.ImageVector, iconColor: Color, value: String, isActive: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
            color = if (isActive) Color.Black else Color.Gray
        )
    }
}

@Composable
fun RideOptionItem(id: String, label: String, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) Color(0xFFFFD600) else Color(0xFFF5F5F5))
            .clickable(onClick = onClick)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Placeholder for Icon
        Box(modifier = Modifier.size(32.dp).background(Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(8.dp)))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}
