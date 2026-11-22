package app.fences

import android.app.Activity
import android.os.Bundle

class LaunchActivity : Activity() {
    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        intent.getStringExtra("pkg")?.let { packageManager.getLaunchIntentForPackage(it) }?.let { startActivity(it) }
        finish()
    }
}
