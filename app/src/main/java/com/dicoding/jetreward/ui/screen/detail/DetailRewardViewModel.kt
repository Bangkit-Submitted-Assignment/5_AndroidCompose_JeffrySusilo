package com.dicoding.jetreward.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetreward.R
import com.dicoding.jetreward.data.RewardRepository
import com.dicoding.jetreward.model.OrderReward
import com.dicoding.jetreward.model.Reward
import com.dicoding.jetreward.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailRewardViewModel(
    private val repository: RewardRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderReward>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderReward>>
        get() = _uiState

    fun getRewardById(rewardId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderRewardById(rewardId))
        }
    }

    fun addToCart(reward: Reward, count: Int) {
        viewModelScope.launch {
            repository.updateOrderReward(reward.id, count)
        }
    }

    private val rewardDescriptions = mapOf(
        // Add more mappings as needed
        R.drawable.nasi_goreng to R.string.nasi_goreng_description,
        R.drawable.nasi_bebek to  R.string.nasi_bebek_description,
        R.drawable.nasi_ayam to  R.string.nasi_ayam_description,
        R.drawable.nasi_kambing to  R.string.nasi_kambing_description,
        R.drawable.nasi_sapi to  R.string.nasi_sapi_description,
        R.drawable.sot_sapi to R.string.soto_sapi_description,
        R.drawable.soto_ayam to  R.string.soto_ayam_description,
        R.drawable.steak_wagyu to  R.string.steak_wagyu_description,
        R.drawable.roti_bakar to R.string.roti_bakar_description,
        R.drawable.nasi_ikan to R.string.nasi_ikan_description
    )

    fun getDescriptionResourceId(rewardId: Long): Int {
        return rewardDescriptions.getOrElse(rewardId.toInt()) { R.string.default_description }
    }
}