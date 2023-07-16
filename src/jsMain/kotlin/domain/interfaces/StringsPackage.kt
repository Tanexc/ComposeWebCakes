package domain.interfaces


interface StringsPackage {
    val strings: Map<Int, String>

    operator fun invoke(id: Int): String = strings[id]?: "$id"
}