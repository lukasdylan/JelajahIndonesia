package id.lukasdylan.jelajahindonesia.intention

import id.lukasdylan.jelajahindonesia.model.contract.TourismPlace
import id.lukasdylan.jelajahindonesia.usecase.GetRecommendedCityUseCase
import id.lukasdylan.jelajahindonesia.utils.Content

/**
 * Created by Lukas Dylan on 08/11/20.
 */
data class ExploreViewState(
    val title: Content<String> = Content(""),
    val subtitle: Content<String> = Content(""),
    val recommendedCities: Content<List<TourismPlace>> = Content(emptyList()),
    val isLoading: Content<Boolean> = Content(true)
) : ViewState

sealed class ExploreViewAction : ViewAction {
    object LoadData : ExploreViewAction()
}

sealed class ExploreActionResult : ActionResult {
    data class GetRecommendedCityResult(val useCaseResult: GetRecommendedCityUseCase.UseCaseResult) :
        ExploreActionResult()
}