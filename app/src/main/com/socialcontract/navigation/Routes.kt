package com.socialcontract.navigation

object Routes {

    const val HOME = "home"

    const val CONTRACT_LIST = "contracts"

    const val CREATE_CONTRACT = "contracts/create"

    const val CONTRACT_DETAIL = "contracts/{contractId}"

    fun contractDetail(contractId: String): String {
        return "contracts/$contractId"
    }
}
