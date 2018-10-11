package com.eden.orchid.netlifycms.model

import com.eden.orchid.api.OrchidContext
import com.eden.orchid.api.compilers.TemplateTag
import com.eden.orchid.api.tasks.TaskService
import com.eden.orchid.api.theme.assets.AssetHolder
import com.eden.orchid.api.theme.components.OrchidComponent
import com.eden.orchid.api.theme.menus.menuItem.OrchidMenuItem
import com.eden.orchid.netlifycms.util.toNetlifyCmsField
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@JvmSuppressWildcards
class NetlifyCmsModel
@Inject
constructor(
    val context: OrchidContext,
    val templateTags: Set<TemplateTag>,
    val components: Set<OrchidComponent>,
    val menuItems: Set<OrchidMenuItem>
) {

    var useNetlifyIdentityWidget: Boolean = false

    fun getTemplateFieldsFromTag(tag: TemplateTag): JSONArray {
        val fields = JSONArray()

        tag.describeOptions(context).optionsDescriptions.forEach {
            fields.put(it.toNetlifyCmsField())
        }

        if (tag.type == TemplateTag.Type.Content) {
            val filtersField = JSONObject()
            filtersField.put("label", "Content Filters")
            filtersField.put("name", "filters")
            filtersField.put("widget", "string")
            fields.put(filtersField)

            val bodyField = JSONObject()
            bodyField.put("label", "Tag Body")
            bodyField.put("name", "body")
            bodyField.put("widget", "markdown")
            fields.put(bodyField)
        }

        return fields
    }

    fun getTemplateFieldsFromComponent(tag: OrchidComponent): JSONArray {
        val fields = JSONArray()

        tag.describeOptions(context).optionsDescriptions.forEach {
            fields.put(it.toNetlifyCmsField())
        }

        return fields
    }

    fun getTemplateFieldsFromMenuItem(tag: OrchidMenuItem): JSONArray {
        val fields = JSONArray()

        tag.describeOptions(context).optionsDescriptions.forEach {
            fields.put(it.toNetlifyCmsField())
        }

        return fields
    }

    fun getTagProps(tag: TemplateTag): String {
        var json = JSONArray()
        tag.describeOptions(context).optionsDescriptions.forEach { optionsDescription ->
            json.put(optionsDescription.key)
        }
        return json.toString()
    }

    fun isRunningLocally(): Boolean {
        return context.taskType == TaskService.TaskType.SERVE
    }

    fun loadAssets(holder: AssetHolder) {
        holder.addJs("https://unpkg.com/netlify-cms@^2.0.0/dist/netlify-cms.js")

        if (isRunningLocally()) {
            holder.addCss("assets/css/fs-backend.min.css")
            holder.addJs("assets/js/fs-backend.min.js")
            holder.addJs("inline:fs-cms-registration.js:CMS.registerBackend(\"orchid-server\", FileSystemBackendClass)")
        }
        else if (useNetlifyIdentityWidget) {
            holder.addJs("https://identity.netlify.com/v1/netlify-identity-widget.js")
        }
    }

}