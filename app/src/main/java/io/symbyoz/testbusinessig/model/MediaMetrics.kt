package io.symbyoz.testbusinessig.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("MediaMetrics")
class MediaMetrics: ParseObject()
{
    var mediaMetrics: String by stringAttribute()
}