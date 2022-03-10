package com.fitin.ui.login
import java.io.Serializable
data class LoginResponse (
    val metadata: MetaData?,
    val data: LoginData?
)

data class MetaData(
    val code: Int,
    val msg: String
)


data class LoginData (
    val activity_level: Any,
    val email: String,
    val goal: Any,
    val height: String,
    val idusers: String,
    val password: Any,
    val sex: String,
    val username: String,
    val weight: String
)