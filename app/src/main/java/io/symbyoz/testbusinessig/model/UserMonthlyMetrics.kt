package io.symbyoz.testbusinessig.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("UserMonthlyMetrics")
class UserMonthlyMetrics: ParseObject("UserMonthlyMetrics")
{
    var monthlyMetrics: String by stringAttribute()
}