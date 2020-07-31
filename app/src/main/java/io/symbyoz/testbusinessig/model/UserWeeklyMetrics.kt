package io.symbyoz.testbusinessig.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("UserWeeklyMetrics")
class UserWeeklyMetrics: ParseObject()
{
    var weeklyMetrics: String by stringAttribute()
}