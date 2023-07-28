package util.exceptions

class Disconnected(override val message: String = "disconnected from websocket"): Exception(message=message)