package constants

object Api {
    // Message api
    const val GET_MESSAGES = "api/message/get/chat" // get messages from chat with client id
    const val GET_MESSAGE = "api/message/get" // get one message by id

    // Chat api
    const val GET_CHAT = "api/chat/get" // get chat by client id if exist
    const val GET_NEW_CHAT = "api/chat/get/new" // get new chat with client id of not exist
    const val POST_MESSAGE_INTO_CHAT = "api/chat/post/message" // post message into chat

    // User api
    const val GET_USER = "api/user/get/id" // get chat by user id
    const val GET_USER_BY_TOKEN = "api/user/get/token"  // get chat by token
    const val SIGN_IN_USER = "api/user/get/sign-in" // sign in user if exist
    const val SIGN_UP_USER = "api/user/get/sign-up" // sign up user
    const val UPDATE_CHATS_USER = "api/user/post/chat" // add chat id to user's chats
    const val UPDATE_PASSWORD = "api/user/post/password" // updates password to newPassword

}