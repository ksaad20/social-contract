package com.socialcontract.util

import java.util.UUID

object IdGenerator {

    fun generate(): String {
        return UUID.randomUUID().toString()
    }
}
