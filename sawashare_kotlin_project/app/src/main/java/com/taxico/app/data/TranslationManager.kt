package com.taxico.app.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object TranslationManager {
    var currentLanguage by mutableStateOf("en")

    private val translations = mapOf(
        "en" to mapOf(
            "appName" to "SawaSHare",
            "slogan" to "Safe • Smart • Shared",
            "welcome" to "Welcome",
            "rider" to "Rider",
            "driver" to "Driver",
            "phoneLogin" to "Phone Number",
            "emailLogin" to "Email & Password",
            "sendOtp" to "Send OTP",
            "verify" to "Access SawaSHare",
            "back" to "Back",
            "syncing" to "SYNCING..."
        ),
        "ar" to mapOf(
            "appName" to "SawaSHare",
            "slogan" to "آمن • ذكي • مشارك",
            "welcome" to "أهلاً بك",
            "rider" to "راكب",
            "driver" to "سائق",
            "phoneLogin" to "رقم الهاتف",
            "emailLogin" to "البريد وكلمة المرور",
            "sendOtp" to "إرسال الكود",
            "verify" to "دخول SawaSHare",
            "back" to "رجوع",
            "syncing" to "جاري المزامنة..."
        )
    )

    fun getString(key: String): String {
        return translations[currentLanguage]?.get(key) ?: key
    }
}
