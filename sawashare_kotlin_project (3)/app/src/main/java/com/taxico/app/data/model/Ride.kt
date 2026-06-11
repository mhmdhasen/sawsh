package com.taxico.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val lat: Double,
    val lng: Double,
    val address: String
)

@Serializable
data class RideRider(
    val uid: String,
    val name: String,
    val phone: String,
    val gender: String,
    val pickup: Location,
    val drop: Location,
    val price: Double,
    val seats: Int,
    @SerialName("is_picked_up") val isPickedUp: Boolean? = false,
    @SerialName("is_dropped_off") val isDroppedOff: Boolean? = false,
    @SerialName("has_arrived_at_pickup") val hasArrivedAtPickup: Boolean? = false,
    @SerialName("has_arrived_at_dropoff") val hasArrivedAtDropoff: Boolean? = false,
    @SerialName("is_onboard") val isOnboard: Boolean? = false,
    @SerialName("has_reached_destination") val hasReachedDestination: Boolean? = false,
    @SerialName("reached_destination_at") val reachedDestinationAt: Long? = null,
    @SerialName("picked_up_at") val pickedUpAt: Long? = null,
    @SerialName("is_rated") val isRated: Boolean? = false,
    @SerialName("is_rated_by_rider") val isRatedByRider: Boolean? = false,
    val color: String? = null
)

@Serializable
data class Ride(
    val id: String,
    @SerialName("driver_id") val driverId: String? = null,
    @SerialName("vehicle_type") val vehicleType: String? = null,
    val riders: List<RideRider>,
    val status: String,
    @SerialName("total_price") val totalPrice: Double,
    @SerialName("is_private") val isPrivate: Boolean,
    @SerialName("gender_grouping") val genderGrouping: String,
    @SerialName("created_at") val createdAt: Long,
    @SerialName("updated_at") val updatedAt: Long? = null,
    @SerialName("skipped_drivers") val skippedDrivers: List<String>? = null,
    @SerialName("target_riders_count") val targetRidersCount: Int? = null,
    @SerialName("trip_number") val tripNumber: String? = null,
    @SerialName("current_phase") val currentPhase: String? = null,
    @SerialName("destination_queue") val destinationQueue: List<Int>? = null,
    @SerialName("accepted_at") val acceptedAt: Long? = null,
    @SerialName("finished_at") val finishedAt: Long? = null
)
