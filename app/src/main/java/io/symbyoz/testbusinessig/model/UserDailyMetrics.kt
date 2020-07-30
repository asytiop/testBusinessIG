package io.symbyoz.testbusinessig.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.jsonArrayAttribute
import org.json.JSONArray

@ParseClassName("UserDailyMetrics")
class UserDailyMetrics: ParseObject()
{
    var dailyMetrics: JSONArray? by jsonArrayAttribute()
}