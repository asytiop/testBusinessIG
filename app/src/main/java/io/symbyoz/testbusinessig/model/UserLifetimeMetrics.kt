package io.symbyoz.testbusinessig.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("UserLifetimeMetrics")
class UserLifetimeMetrics: ParseObject()
{
    var lifetimeMetrics: String by stringAttribute()
}