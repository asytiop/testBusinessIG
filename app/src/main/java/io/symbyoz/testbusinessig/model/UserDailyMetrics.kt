package io.symbyoz.testbusinessig.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("UserDailyMetrics")
class UserDailyMetrics: ParseObject()
{
    var dailyMetrics: String by stringAttribute()
}