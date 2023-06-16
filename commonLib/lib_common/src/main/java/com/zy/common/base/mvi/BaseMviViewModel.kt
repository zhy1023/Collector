package com.zy.common.base.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zy.common.app.GlobalApp
import com.zy.common.http.BaseData
import com.zy.common.http.ReqState

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * @Author: zhy
 * @Date: 2023/4/7
 * @Desc: BaseMviViewModel MVI ViewModel基类
 *
 * @param UiState 重复性事件，View层可以多次接收并刷新
 * @param SingleUiState 一次性事件，View层不支持多次消费 如弹Toast，导航Activity等
 */
abstract class BaseMviViewModel<UiState : IUiState, SingleUiState : ISingleUiState> : ViewModel() {
    /**
     * 可以重复消费的事件
     */
    private val _uiStateFlow by lazy { MutableStateFlow(initUiState()) }

    val uiStateFlow: StateFlow<UiState> = _uiStateFlow

    /**
     * 一次性事件 且 一对一的订阅关系
     * 例如：弹Toast、导航Fragment等
     * Channel特点
     * 1.每个消息只有一个订阅者可以收到，用于一对一的通信
     * 2.第一个订阅者可以收到 collect 之前的事件
     */
    private val _sUiStateFlow: Channel<SingleUiState> = Channel()
    val sUiStateFlow: Flow<SingleUiState> = _sUiStateFlow.receiveAsFlow()

    private val _loadUiStateFlow: Channel<LoadUiState> = Channel()
    val loadUiStateFlow: Flow<LoadUiState> = _loadUiStateFlow.receiveAsFlow()

    protected abstract fun initUiState(): UiState

    protected fun sendUiState(copy: UiState.() -> UiState) =
        _uiStateFlow.update { _uiStateFlow.value.copy() }

    protected fun sendSingleUiState(sUiState: SingleUiState) =
        viewModelScope.launch { _sUiStateFlow.send(sUiState) }

    /**
     * 发送当前加载状态： Loading、Error、Normal
     */
    fun sendLoadUiState(loadState: LoadUiState) =
        viewModelScope.launch { _loadUiStateFlow.send(loadState) }


    /**
     * @param showLoading 是否展示loading
     * @param request 请求数据
     * @param successCallback 请求成功回调
     * @param errorCallback 请求失败回调，处理异常逻辑
     */
    protected fun <T : Any> requestDataWithFlow(
        //默认展示loading
        showLoading: Boolean = true,
        request: suspend () -> BaseData<T>,
        successCallback: (T) -> Unit,
        errorCallback: suspend (String) -> Unit = { errMsg ->
            //默认异常处理，子类可以进行覆写
            sendLoadUiState(LoadUiState.Error(errMsg))
        },
    ) {
        viewModelScope.launch {
            if (showLoading) {
                sendLoadUiState(LoadUiState.Loading(true))
            }
            val baseData: BaseData<T>
            try {
                baseData = request()
                when (baseData.state) {
                    ReqState.SUCCESS -> {
                        sendLoadUiState(LoadUiState.ShowMainView)
                        baseData.data?.let { successCallback(it) }
                    }
                    ReqState.ERROR -> {
//                        sendLoadUiState(LoadUiState.Error(GlobalApp.getString(R.string.imi_network_error)))
                    }
                }
            } catch (e: Exception) {
                e.message?.let { errorCallback(it) }
            } finally {
                if (showLoading) {
                    sendLoadUiState(LoadUiState.Loading(false))
                }
            }
        }
    }
}