package ch.longstone.a18ftiohelper

import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        makeLinksClickable()
        updateCounter()
    }

    private fun makeLinksClickable() {
        val creditTextView = findViewById<View>(R.id.credits_to) as TextView
        creditTextView.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onResume() {
        super.onResume()
        updateCounter()
    }

    private fun updateCounter() {
        val prefs: SharedPreferences =
            getSharedPreferences(MyPreferences.NAME, MODE_PRIVATE)
        val helloTextView: TextView = findViewById(R.id.helped_with_text)
        val counterLinksRedirected = prefs.getInt(MyPreferences.counterLinksRedirected, 0)
        helloTextView.text = getString(
            R.string.redirected_links,
            counterLinksRedirected
        )
        if (counterLinksRedirected < 0) {
            // show helper toast
            Toast.makeText(
                this,
                getString(R.string.hello_world),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}