package id.lukasdylan.jelajahindonesia.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import id.lukasdylan.jelajahindonesia.intention.DetailPlaceActionResult
import id.lukasdylan.jelajahindonesia.intention.DetailPlaceViewAction
import id.lukasdylan.jelajahindonesia.intention.DetailPlaceViewState
import id.lukasdylan.jelajahindonesia.usecase.GetDetailTourismPlaceUseCase
import id.lukasdylan.jelajahindonesia.utils.Content
import id.lukasdylan.jelajahindonesia.utils.StringWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Lukas Dylan on 05/11/20.
 */
class DetailPlaceViewModel @ViewModelInject constructor(
    private val getDetailTourismPlaceUseCase: GetDetailTourismPlaceUseCase,
    @Assisted savedStateHandle: SavedStateHandle
) : BaseViewModel<DetailPlaceViewState, DetailPlaceViewAction, DetailPlaceActionResult>(
    initialState = DetailPlaceViewState(), savedStateHandle = savedStateHandle
) {

    override fun DetailPlaceActionResult.updateViewState(): DetailPlaceViewState = when (this) {
        is DetailPlaceActionResult.SetScreenTitle -> currentViewState().copy(title = Content(this.title))
        is DetailPlaceActionResult.GetDetailPlaceResult -> this.useCaseResult.mapResult()
    }

    override fun DetailPlaceViewAction.handleAction(): Flow<DetailPlaceActionResult> = flow {
        when (this@handleAction) {
            is DetailPlaceViewAction.LoadDetailPlace -> {
                emit(DetailPlaceActionResult.SetScreenTitle(title = this@handleAction.title))
                kotlinx.coroutines.delay(200)
                val useCaseResult = getDetailTourismPlaceUseCase.getResult(
                    param = GetDetailTourismPlaceUseCase.Param(xid = this@handleAction.xid)
                )
                emit(DetailPlaceActionResult.GetDetailPlaceResult(useCaseResult))
            }
        }
    }

    private fun GetDetailTourismPlaceUseCase.UseCaseResult.mapResult(): DetailPlaceViewState {
        return when (this) {
            is GetDetailTourismPlaceUseCase.UseCaseResult.Success -> {
                currentViewState().copy(
                    detailPlace = Content(this.data),
                    isLoading = Content(false)
                )
            }
            is GetDetailTourismPlaceUseCase.UseCaseResult.Failed -> {
                currentViewState().copy(
                    errorLoadDetailPlace = Content(StringWrapper.wrap(this.reason)),
                    isLoading = Content(false)
                )
            }
        }
    }

}