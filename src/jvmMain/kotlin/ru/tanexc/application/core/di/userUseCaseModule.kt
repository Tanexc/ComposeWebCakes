package ru.tanexc.application.core.di

import org.koin.dsl.module
import ru.tanexc.application.domain.use_cases.user_use_cases.*

val userUseCaseModule = module {
    single {
        UserSignUpUseCase()
    }

    single {
        UserSignInUseCase()
    }

    single {
        UserGetByIdUseCase()
    }

    single {
        UserGetByTokenUseCase()
    }

    single {
        UserUpdatePasswordUseCase()
    }

    single {
        UserUpdateTokenUseCase()
    }

    single {
        UserUpdateChatIdsUseCase()
    }
}