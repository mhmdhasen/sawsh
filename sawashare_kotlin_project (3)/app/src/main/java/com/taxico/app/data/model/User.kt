package com.taxico.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val uid: String,
    @SerialName("serial_number") val serialNumber: String? = null,
    val phone: String,
    val name: String,
    val email: String,
    val gender: String,
    val role: String,
    @SerialName("vehicle_type") val vehicleType: String? = null,
    val rating: Double = 5.0,
    @SerialName("rating_count") val ratingCount: Int? = null,
    @SerialName("rating_sum") val ratingSum: Double? = null,
    @SerialName("account_status") val accountStatus: String = "active",
    @SerialName("created_at") val createdAt: Long,
    @SerialName("is_online") val isOnline: Boolean? = null,
    @SerialName("driver_range_radius") val driverRangeRadius: Double? = 5.0,
    val lat: Double? = null,
    val lng: Double? = null,
    @SerialName("credit_balance") val creditBalance: Double? = 0.0,
    @SerialName("cumulative_credit") val cumulativeCredit: Double? = 0.0,
    @SerialName("car_manufacturer") val carManufacturer: String? = null,
    @SerialName("car_name") val carName: String? = null,
    @SerialName("car_model") val carModel: String? = null,
    @SerialName("car_plate") val carPlate: String? = null,
    @SerialName("driver_photo_url") val driverPhotoURL: String? = null,
    @SerialName("car_photo_url") val carPhotoURL: String? = null,
    @SerialName("fcm_token") val fcmToken: String? = null,
    @SerialName("last_credit_update") val lastCreditUpdate: Long? = null,
    @SerialName("was_online_today") val wasOnlineToday: Boolean? = false
)
