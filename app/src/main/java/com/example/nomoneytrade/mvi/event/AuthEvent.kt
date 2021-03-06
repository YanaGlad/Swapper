package com.example.nomoneytrade.mvi.event

import android.graphics.Bitmap
import com.example.nomoneytrade.mvi.effect.AuthEffect
import com.example.nomoneytrade.mvi.state.AuthState

//TODO асбтракция над Event, всегда принимает State и Effect (Абстракции над state и effect)

sealed class AuthEvent {
    class Success(
        val state: AuthState,
        val effect: AuthEffect,
    ): AuthEvent()
    object Loading: AuthEvent()
    object FailedToLogin: AuthEvent()
    object Error: AuthEvent()
    object None: AuthEvent()
    class UpdatedPhoto(val bitmap: Bitmap): AuthEvent()
}