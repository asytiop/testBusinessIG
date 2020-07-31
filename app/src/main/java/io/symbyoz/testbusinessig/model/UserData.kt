package io.symbyoz.testbusinessig.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("UserData")
class UserData : ParseObject()
{
    var userData: String by stringAttribute()
}