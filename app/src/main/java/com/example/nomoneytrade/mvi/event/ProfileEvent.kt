package com.example.nomoneytrade.mvi.event

import com.example.nomoneytrade.entity.User

sealed class ProfileEvent {
    class Success(val user: User): ProfileEvent() //Loaded products + profile info into state!
    object Error: ProfileEvent()
    object Loading: ProfileEvent()
    object LogOut: ProfileEvent()
}
