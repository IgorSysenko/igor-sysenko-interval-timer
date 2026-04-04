package com.ivos.data.utils

inline fun <T> safeApiRequest(block: () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (e: Exception) {
        Result.failure(e)
    }
}
