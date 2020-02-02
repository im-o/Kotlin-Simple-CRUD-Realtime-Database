package com.stimednp.crudrealtimedb.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * Created by rivaldy on 12/25/2019.
 */

object Const {
    val PATH_COLLECTION = "users"
    val PATH_AGE = "intAge"
    val PATH_NAME = "strName"

    fun setTimeStamp(): Long {
        val time = (-1 * System.currentTimeMillis())
        return time
    }
}

fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}