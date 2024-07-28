package com.mdvorscak.ladderhelper

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UnPaywallActivity : AppCompatActivity() {

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remove_paywall)

        handleIntent()
    }

    private fun extractUrl(text: String): String? {
        val urlPattern = "https?://[\\w-]+(\\.[\\w-]+)+(/\\S*)?"
        val regex = Regex(urlPattern)
        val matchResult = regex.find(text)
        return matchResult?.value
    }

    private fun handleIntent() {
        // Get the intent that started this activity
        val intent: Intent = intent
        val action: String? = intent.action
        val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        val url = sharedText?.let { extractUrl(it) }
        // Check if the intent is a "send" action and there is a URL in the data
        if (Intent.ACTION_SEND == action && url != null) {
            // Add "https://12ft.io/" to the front of the URL
            val urlWithoutPaywall = "https://12ft.io/$url"
            // Open the URL in a browser
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(urlWithoutPaywall))
            val prefs: SharedPreferences =
                getSharedPreferences(MyPreferences.NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(
                MyPreferences.counterLinksRedirected,
                (prefs.getInt(MyPreferences.counterLinksRedirected, 0) + 1)
            )
            editor.apply()
            startActivity(browserIntent)
        } else {
            Toast.makeText(
                this,
                "You should share a link via the App to get the 12ft.io link",
                Toast.LENGTH_LONG
            ).show()
        }

        finish()
    }
}