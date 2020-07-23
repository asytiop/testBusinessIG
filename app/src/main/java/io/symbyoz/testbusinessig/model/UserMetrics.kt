package io.symbyoz.testbusinessig.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.intAttribute
import com.parse.ktx.delegates.jsonArrayAttribute
import org.json.JSONArray

@ParseClassName("UserMetrics")
class UserMetrics: ParseObject() {
    var audience_city: JSONArray? by jsonArrayAttribute()
    var audience_country: JSONArray? by jsonArrayAttribute()
    var audience_gender_age: JSONArray? by jsonArrayAttribute()
    var audience_locale: JSONArray? by jsonArrayAttribute()
    var email_contacts: Int by intAttribute()
    var follower_count: Int by intAttribute()
    var get_directions_clicks: Int by intAttribute()
    var impressions: Int by intAttribute()
    var online_followers: Int by intAttribute()
    var phone_call_clicks: Int by intAttribute()
    var profile_views: Int by intAttribute()
    var reach: Int by intAttribute()
    var text_message_clicks: Int by intAttribute()
    var website_clicks: Int by intAttribute()
}