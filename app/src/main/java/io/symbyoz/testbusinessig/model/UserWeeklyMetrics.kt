package io.symbyoz.testbusinessig.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.jsonArrayAttribute
import org.json.JSONArray

@ParseClassName("UserWeeklyMetrics")
class UserWeeklyMetrics: ParseObject()
{
    var weeklyMetrics: JSONArray? by jsonArrayAttribute()
}