package domain.interfaces


interface StringResources {
    val strings: Map<Int, String>
    operator fun invoke(id: Int): String = strings[id]?: "$id"

    operator fun invoke(): Int
}