package app.fences

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.widget.*

class ConfigActivity : Activity() {
    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        val id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        val result = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id)
        setResult(RESULT_CANCELED, result)
        if (id == AppWidgetManager.INVALID_APPWIDGET_ID) return finish()

        val pm = packageManager
        val apps = pm.queryIntentActivities(Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER), 0)
            .map { it.activityInfo.packageName to it.loadLabel(pm).toString() }
            .distinctBy { it.first }.filter { it.first != packageName }.sortedBy { it.second.lowercase() }
        val selected = Store.apps(this, id)

        val title = EditText(this).apply { hint = "Titre (optionnel)"; setText(Store.title(this@ConfigActivity, id)) }
        val list = ListView(this).apply {
            choiceMode = ListView.CHOICE_MODE_MULTIPLE
            adapter = ArrayAdapter(this@ConfigActivity, android.R.layout.simple_list_item_multiple_choice, apps.map { it.second })
            apps.forEachIndexed { i, a -> if (a.first in selected) setItemChecked(i, true) }
        }
        val save = Button(this).apply {
            text = "Enregistrer"
            setOnClickListener {
                val checked = list.checkedItemPositions
                // Keep the previous order, then append new picks alphabetically
                val picked = apps.indices.filter { checked[it] }.map { apps[it].first }
                val order = selected.filter { it in picked } + picked.filter { it !in selected }
                Store.save(this@ConfigActivity, id, title.text.toString().trim(), order)
                FolderWidget.update(this@ConfigActivity, AppWidgetManager.getInstance(this@ConfigActivity), id)
                setResult(RESULT_OK, result); finish()
            }
        }
        setContentView(LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL; setPadding(32, 32, 32, 32)
            addView(title); addView(list, LinearLayout.LayoutParams(-1, 0, 1f)); addView(save)
        })
    }
}
