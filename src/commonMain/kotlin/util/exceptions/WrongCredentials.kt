package util.exceptions

class WrongCredentials(val value: String?): Exception("Wrong credentials ${value?:""}")