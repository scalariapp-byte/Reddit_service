package app.fences

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.widget.RemoteViews
import android.widget.RemoteViewsService

class FolderService : RemoteViewsService() {
    override fun onGetViewFactory(i: Intent): RemoteViewsFactory =
        Factory(applicationContext, i.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0))

    class Factory(private val c: Context, private val id: Int) : RemoteViewsFactory {
        private class App(val pkg: String, val label: String, val icon: Bitmap)
        private var apps = emptyList<App>()

        override fun onDataSetChanged() {
            val pm = c.packageManager
            apps = Store.apps(c, id).mapNotNull { pkg ->
                runCatching {
                    val ai = pm.getApplicationInfo(pkg, 0)
                    val d = pm.getApplicationIcon(ai)
                    val b = Bitmap.createBitmap(128, 128, Bitmap.Config.ARGB_8888)
                    d.setBounds(0, 0, 128, 128); d.draw(Canvas(b))
                    App(pkg, pm.getApplicationLabel(ai).toString(), b)
                }.getOrNull()
            }
        }

        override fun getViewAt(pos: Int): RemoteViews {
            val a = apps[pos]
            return RemoteViews(c.packageName, R.layout.item).apply {
                setImageViewBitmap(R.id.icon, a.icon)
                setTextViewText(R.id.label, a.label)
                setOnClickFillInIntent(R.id.item, Intent().putExtra("pkg", a.pkg))
            }
        }

        override fun getCount() = apps.size
        override fun getItemId(pos: Int) = pos.toLong()
        override fun hasStableIds() = true
        override fun getViewTypeCount() = 1
        override fun getLoadingView(): RemoteViews? = null
        override fun onCreate() {}
        override fun onDestroy() {}
    }
}
