package com.example.nomoneytrade.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nomoneytrade.CURRENT_USER_ID
import com.example.nomoneytrade.api.Api
import com.example.nomoneytrade.api.UserId
import com.example.nomoneytrade.entity.User
import com.example.nomoneytrade.mvi.event.ProfileEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val api: Api) : ViewModel() {

    val event = MutableStateFlow<ProfileEvent>(ProfileEvent.Loading)

    init {
        this.viewModelScope.launch {
            getCurrentUserInfo()
        }
    }

    fun clickLogOut() {
        this.viewModelScope.launch {
            logOut()
        }
    }

    private suspend fun logOut() {
        val response = api.signOut()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            CURRENT_USER_ID = -1L
            event.value = ProfileEvent.LogOut
        } else {
            event.value = ProfileEvent.Error
        }
    }

    private suspend fun getCurrentUserInfo() {
        val response = api.getUserById(UserId(CURRENT_USER_ID))
        val body = response.body()
        if (response.isSuccessful && body != null) {
            event.value = ProfileEvent.Success(User(
                id = CURRENT_USER_ID,
                username = body.username,
                fio = "Гладких Яна Сергеевна",
                email = body.email,
                iconUrl = body.imagePath,
                phoneNumber = body.phoneNumber,
            )
            )
        } else {
            event.value = ProfileEvent.Error
        }
    }

    suspend fun getUserProducts() {
        // api.getUserOffers(CURRENT_USER_ID)
    }
}