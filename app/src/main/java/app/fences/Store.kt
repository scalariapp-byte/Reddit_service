package app.fences

import android.content.Context

object Store {
    private fun p(c: Context) = c.getSharedPreferences("w", Context.MODE_PRIVATE)
    fun apps(c: Context, id: Int) = p(c).getString("a$id", "")!!.split(',').filter { it.isNotEmpty() }
    fun title(c: Context, id: Int) = p(c).getString("t$id", "")!!
    fun save(c: Context, id: Int, title: String, apps: List<String>) =
        p(c).edit().putString("t$id", title).putString("a$id", apps.joinToString(",")).apply()
    fun delete(c: Context, id: Int) = p(c).edit().remove("t$id").remove("a$id").apply()
}
