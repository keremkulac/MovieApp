package com.keremkulac.movieapp.repository.model

import java.io.Serializable

data class User(
    var email : String? = null,
    var firstname : String? = null,
    var lastname : String? = null,
    ): Serializable
