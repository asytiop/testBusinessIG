package io.symbyoz.testbusinessig.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("UserData")
class UserData : ParseObject() {
    var user_id: String by stringAttribute()
    var name: String by stringAttribute()
    var username: String by stringAttribute()
    var biography: String by stringAttribute()
    var followers_count: String by stringAttribute()
    var follow_count: String by stringAttribute()
    var media_count: String by stringAttribute()
    var profile_picture_url: String by stringAttribute()
}