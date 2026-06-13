package com.taxico.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taxico.app.data.SupabaseManager
import com.taxico.app.data.model.User
import io.github.jan_tennert.supabase.gotrue.auth
import io.github.jan_tennert.supabase.gotrue.providers.builtin.Email
import io.github.jan_tennert.supabase.postgrest.postgrest
// CRITICAL EXPLICIT IMPORTS: Explicitly forcing the Postgrest lambda filter builders
import io.github.jan_tennert.supabase.postgrest.query.filter.PostgrestFilterBuilder
import io.github.jan_tennert.supabase.postgrest.query.filter.eq
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    var currentUser by mutableStateOf<User?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                SupabaseManager.client.auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }
                fetchUserProfile(onSuccess)
            } catch (e: Exception) {
                error = e.message ?: "Login failed"
                isLoading = false
            }
        }
    }

    fun loginWithPhone(phone: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                // FIXED: Explicitly referencing the filter lambda context to guarantee "eq" matches
                val user = SupabaseManager.client.postgrest["users"]
                    .select {
                        filter {
                            eq("phone", phone)
                        }
                    }.decodeSingleOrNull<User>()
                
                if (user != null) {
                    currentUser = user
                    onSuccess()
                } else {
                    error = "Phone number not registered"
                }
            } catch (e: Exception) {
                error = e.message ?: "Phone login failed"
            } finally {
                isLoading = false
            }
        }
    }

    private suspend fun fetchUserProfile(onSuccess: () -> Unit) {
        val userUid = SupabaseManager.client.auth.currentUserOrNull()?.id
        if (userUid != null) {
            // FIXED: Explicitly referencing the filter lambda context to guarantee "eq" matches
            val user = SupabaseManager.client.postgrest["users"]
                .select {
                    filter {
                        eq("uid", userUid)
                    }
                }.decodeSingle<User>()
            currentUser = user
            onSuccess()
        } else {
            error = "User not found"
        }
        isLoading = false
    }

    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                SupabaseManager.client.auth.signOut()
                currentUser = null
                onSuccess()
            } catch (e: Exception) {
                error = e.message
            }
        }
    }
}
