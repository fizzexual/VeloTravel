package com.velotravel.update

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

data class UpdateInfo(
    val version: String,
    val downloadUrl: String,
    val releaseNotes: String,
    val publishedAt: String
)

class UpdateChecker(private val context: Context) {
    
    companion object {
        private const val GITHUB_API_URL = "https://api.github.com/repos/fizzexual/VeloTravel/releases/latest"
        private const val CURRENT_VERSION = "2.2.2" // 100 achievements, improved achievement system for multi-activity tracking
    }
    
    suspend fun checkForUpdates(): UpdateInfo? = withContext(Dispatchers.IO) {
        try {
            val url = URL(GITHUB_API_URL)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json")
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            
            if (connection.responseCode == 200) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(response)
                
                val latestVersion = json.getString("tag_name").removePrefix("v")
                val releaseNotes = json.optString("body", "")
                val publishedAt = json.getString("published_at")
                
                // Find APK asset
                val assets = json.getJSONArray("assets")
                var apkUrl: String? = null
                
                for (i in 0 until assets.length()) {
                    val asset = assets.getJSONObject(i)
                    val name = asset.getString("name")
                    if (name.endsWith(".apk")) {
                        apkUrl = asset.getString("browser_download_url")
                        break
                    }
                }
                
                if (apkUrl != null && isNewerVersion(latestVersion, CURRENT_VERSION)) {
                    UpdateInfo(
                        version = latestVersion,
                        downloadUrl = apkUrl,
                        releaseNotes = releaseNotes,
                        publishedAt = publishedAt
                    )
                } else {
                    null
                }
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    private fun isNewerVersion(latest: String, current: String): Boolean {
        val latestParts = latest.split(".").map { it.toIntOrNull() ?: 0 }
        val currentParts = current.split(".").map { it.toIntOrNull() ?: 0 }
        
        for (i in 0 until maxOf(latestParts.size, currentParts.size)) {
            val latestPart = latestParts.getOrNull(i) ?: 0
            val currentPart = currentParts.getOrNull(i) ?: 0
            
            if (latestPart > currentPart) return true
            if (latestPart < currentPart) return false
        }
        
        return false
    }
    
    fun downloadAndInstallUpdate(updateInfo: UpdateInfo) {
        val request = DownloadManager.Request(Uri.parse(updateInfo.downloadUrl))
            .setTitle("VeloTravel Update")
            .setDescription("Downloading version ${updateInfo.version}")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "VeloTravel-${updateInfo.version}.apk"
            )
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(false)
        
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }
    
    fun installApk(apkFile: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        val apkUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            apkFile
        )
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
        context.startActivity(intent)
    }
}
