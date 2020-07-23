package io.symbyoz.testbusinessig.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.booleanAttribute
import com.parse.ktx.delegates.intAttribute
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("MediaMetrics")
class MediaMetrics: ParseObject() {
    var media_id: String by stringAttribute()
    var username: String by stringAttribute()
    var caption: String by stringAttribute()
    var comments_count: Int by intAttribute()
    var is_comment_enabled: Boolean by booleanAttribute()
    var like_count: Int by intAttribute()
    var media_type: String by stringAttribute()
    var media_url: String by stringAttribute()
    var permalink: String by stringAttribute()
    var shortcode: String by stringAttribute()
    var timestamp: String by stringAttribute()
}