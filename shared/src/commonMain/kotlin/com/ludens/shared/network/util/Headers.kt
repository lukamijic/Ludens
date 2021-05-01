package com.ludens.shared.network.util

const val CLIENT_ID_KEY = "Client-ID"
const val AUTHORIZATION_KEY = "Authorization"

fun bearerToken(token: String) = "Bearer $token"
