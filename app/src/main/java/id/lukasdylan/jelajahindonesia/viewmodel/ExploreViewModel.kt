package id.lukasdylan.jelajahindonesia.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import id.lukasdylan.jelajahindonesia.intention.ExploreActionResult
import id.lukasdylan.jelajahindonesia.intention.ExploreViewAction
import id.lukasdylan.jelajahindonesia.intention.ExploreViewState
import id.lukasdylan.jelajahindonesia.usecase.GetRecommendedCityUseCase
import id.lukasdylan.jelajahindonesia.utils.Content
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Lukas Dylan on 08/11/20.
 */
class ExploreViewModel @ViewModelInject constructor(
    private val getRecommendedCityUseCase: GetRecommendedCityUseCase,
    @Assisted savedStateHandle: SavedStateHandle
) : BaseViewModel<ExploreViewState, ExploreViewAction, ExploreActionResult>(
    initialState = ExploreViewState(), savedStateHandle = savedStateHandle
) {

    override fun ExploreActionResult.updateViewState(): ExploreViewState = when (this) {
        is ExploreActionResult.GetRecommendedCityResult -> this.useCaseResult.mapResult()
    }

    override fun ExploreViewAction.handleAction(): Flow<ExploreActionResult> = flow {
        when (this@handleAction) {
            ExploreViewAction.LoadData -> {
                kotlinx.coroutines.delay(250)
                val result = getRecommendedCityUseCase.getResult()
                emit(ExploreActionResult.GetRecommendedCityResult(result))
            }
        }
    }

    private fun GetRecommendedCityUseCase.UseCaseResult.mapResult(): ExploreViewState {
        return when (this) {
            is GetRecommendedCityUseCase.UseCaseResult.Success -> {
                currentViewState().copy(
                    title = Content(this.title),
                    subtitle = Content(this.subtitle),
                    recommendedCities = Content(this.data),
                    isLoading = Content(false)
                )
            }
            is GetRecommendedCityUseCase.UseCaseResult.Failed -> {
                currentViewState().copy(isLoading = Content(false))
            }
        }
    }

}