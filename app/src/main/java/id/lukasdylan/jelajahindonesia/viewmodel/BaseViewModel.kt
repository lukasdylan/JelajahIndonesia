package id.lukasdylan.jelajahindonesia.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.lukasdylan.jelajahindonesia.intention.ActionResult
import id.lukasdylan.jelajahindonesia.intention.ViewAction
import id.lukasdylan.jelajahindonesia.intention.ViewState
import kotlinx.coroutines.flow.*
import timber.log.Timber

/**
 * Created by Lukas Dylan on 29/10/20.
 */
abstract class BaseViewModel<State : ViewState, Action : ViewAction, Result: ActionResult>(
    initialState: State,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _viewState = savedStateHandle.asMutableStateFlow(initialState::javaClass.name, initialState)
    val viewState: StateFlow<State> = _viewState

    protected fun currentViewState(): State = viewState.value

    protected abstract fun Result.updateViewState(): State
    protected abstract fun Action.handleAction(): Flow<Result>

    fun onAction(action: Action) {
        action.handleAction()
            .map { it.updateViewState() }
            .onEach { _viewState.value = it }
            .catch { Timber.e(it) }
            .launchIn(viewModelScope)
    }

    private fun <T> SavedStateHandle.asMutableStateFlow(
        key: String,
        defaultValue: T
    ): MutableStateFlow<T> = MutableStateFlow(get<T>(key) ?: defaultValue)
}