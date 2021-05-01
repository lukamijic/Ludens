package com.ludens.shared.core

sealed class BuildVariant(
    open val platform: String,
    open val appVersion: String
) {

    val igdbHost = "api.igdb.com"
    val igdbClientId = "iewemqb9yp2p74lahsiz02ld1xwog9"
    val igdbToken = "9m71nh5rvlh9bswjr9eir89aa3kixj"

    data class Develop(override val platform: String, override val appVersion: String) : BuildVariant(platform, appVersion)
    data class Release(override val platform: String, override val appVersion: String) : BuildVariant(platform, appVersion)
}
