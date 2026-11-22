package app.fences

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.RemoteViews

class FolderWidget : AppWidgetProvider() {
    override fun onUpdate(c: Context, m: AppWidgetManager, ids: IntArray) = ids.forEach { update(c, m, it) }
    override fun onDeleted(c: Context, ids: IntArray) = ids.forEach { Store.delete(c, it) }

    companion object {
        fun update(c: Context, m: AppWidgetManager, id: Int) {
            val v = RemoteViews(c.packageName, R.layout.widget)
            val title = Store.title(c, id)
            v.setTextViewText(R.id.title, title)
            v.setViewVisibility(R.id.title, if (title.isEmpty()) View.GONE else View.VISIBLE)
            val svc = Intent(c, FolderService::class.java)
                .putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id)
                .setData(Uri.parse("fences://w/$id"))
            v.setRemoteAdapter(R.id.grid, svc)
            v.setEmptyView(R.id.grid, R.id.empty)
            val tpl = PendingIntent.getActivity(c, id, Intent(c, LaunchActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
            v.setPendingIntentTemplate(R.id.grid, tpl)
            // Tap on the title or empty area -> edit the widget
            val cfg = PendingIntent.getActivity(c, -id - 1,
                Intent(c, ConfigActivity::class.java).putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id)
                    .setData(Uri.parse("fences://cfg/$id")),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            v.setOnClickPendingIntent(R.id.title, cfg)
            v.setOnClickPendingIntent(R.id.empty, cfg)
            m.updateAppWidget(id, v)
            m.notifyAppWidgetViewDataChanged(id, R.id.grid)
        }
    }
}
