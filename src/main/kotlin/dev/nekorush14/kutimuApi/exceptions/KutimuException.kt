package dev.nekorush14.kutimuApi.exceptions

open class KutimuException(
    override val message: String = "Internal Server Error"
) : Exception(message) {
    open val statusCode: Int = 500
}