package util

sealed class State<T>(
    val data: T? = null,
    val addition: Any? = null,
    val message: String
) {

    class Success<T>(
        data: T? = null,
        addition: Any? = null,
        message: String = "success"
    ) :
        State<T>(
            data = data,
            message = message,
            addition = addition
        )

    class Error<T>(
        data: T? = null,
        message: String = "error"
    ) :
        State<T>(
            data = data,
            message = message
        )

    class Processing<T>(
        data: T? = null,
        message: String = "processing"
    ) :
        State<T>(
            data = data,
            message = message
        )
}