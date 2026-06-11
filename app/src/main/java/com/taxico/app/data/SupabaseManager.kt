package com.taxico.app.data

import io.github.jan.tennert.supabase.SupabaseClient
import io.github.jan.tennert.supabase.createSupabaseClient
import io.github.jan.tennert.supabase.gotrue.GoTrue
import io.github.jan.tennert.supabase.postgrest.Postgrest
import io.github.jan.tennert.supabase.realtime.Realtime
import io.github.jan.tennert.supabase.storage.Storage

object SupabaseManager {
    private const val SUPABASE_URL = "https://aldmsjaszkdspdrmpblu.supabase.co"
    private const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFsZG1zamFzemtkc3Bkcm1wYmx1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzQxMDEyNjEsImV4cCI6MjA4OTY3NzI2MX0.LiGeKoR_zbV1700Fb1GIJACMtWPyVSKDdl1G8x2iAIg"

    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_ANON_KEY
    ) {
        install(Postgrest)
        install(GoTrue)
        install(Realtime)
        install(Storage)
    }
}
