package com.taxico.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taxico.app.data.SupabaseManager
import com.taxico.app.data.model.User
import io.github.jan.tennert.supabase.gotrue.auth
import io.github.jan.tennert.supabase.gotrue.providers.builtin.Email
import io.github.jan.tennert.supabase.postgrest.postgrest
// CRITICAL IMPORTS: This exposes the `eq` function inside the filter block
import io.github.jan.tennert.supabase.postgrest.query.filter.PostgrestFilterBuilder
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
                // In Supabase Kotlin, phone login usually involves OTP
                // This is a simplified version matching the web app's flow
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
