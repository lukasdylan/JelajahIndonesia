package id.lukasdylan.jelajahindonesia.intention

import id.lukasdylan.jelajahindonesia.model.section.HomeNearbyViewSection
import id.lukasdylan.jelajahindonesia.model.section.HomePopularDestinationViewSection
import id.lukasdylan.jelajahindonesia.usecase.GetNearbyTourismPlaceUseCase
import id.lukasdylan.jelajahindonesia.usecase.GetPopularDestinationUseCase
import id.lukasdylan.jelajahindonesia.utils.Content

/**
 * Created by Lukas Dylan on 29/10/20.
 */
data class HomeViewState(
    val isNeedShowLocationPermission: Content<Boolean> = Content(false),
    val nearbyTourismPlacesSection: Content<HomeNearbyViewSection> = Content(HomeNearbyViewSection()),
    val popularDestinationSection: Content<HomePopularDestinationViewSection> = Content(
        HomePopularDestinationViewSection()
    )
) : ViewState

sealed class HomeViewAction : ViewAction {
    object LoadData : HomeViewAction()
    object RefreshData : HomeViewAction()
    object AlreadyShowLocationPermission : HomeViewAction()
}

sealed class HomeActionResult : ActionResult {
    object RefreshViewState : HomeActionResult()
    object ShowLocationPermission : HomeActionResult()
    data class GetNearbyTourismPlaceResult(val useCaseResult: GetNearbyTourismPlaceUseCase.UseCaseResult) :
        HomeActionResult()

    data class GetPopularDestinationResult(val useCaseResult: GetPopularDestinationUseCase.UseCaseResult) :
        HomeActionResult()
}