package id.lukasdylan.jelajahindonesia.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import id.lukasdylan.jelajahindonesia.intention.HomeActionResult
import id.lukasdylan.jelajahindonesia.intention.HomeViewAction
import id.lukasdylan.jelajahindonesia.intention.HomeViewState
import id.lukasdylan.jelajahindonesia.model.Coordinates
import id.lukasdylan.jelajahindonesia.model.contract.TourismPlace
import id.lukasdylan.jelajahindonesia.model.section.HomeNearbyViewSection
import id.lukasdylan.jelajahindonesia.model.section.HomePopularDestinationViewSection
import id.lukasdylan.jelajahindonesia.model.section.ViewSection
import id.lukasdylan.jelajahindonesia.usecase.CheckLocationPermissionUseCase
import id.lukasdylan.jelajahindonesia.usecase.GetNearbyTourismPlaceUseCase
import id.lukasdylan.jelajahindonesia.usecase.GetPopularDestinationUseCase
import id.lukasdylan.jelajahindonesia.usecase.SetAlreadyShowLocationPermissionUseCase
import id.lukasdylan.jelajahindonesia.utils.Content
import id.lukasdylan.jelajahindonesia.utils.StringWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

/**
 * Created by Lukas Dylan on 29/10/20.
 */
class HomeViewModel @ViewModelInject constructor(
    private val getNearbyTourismPlaceUseCase: GetNearbyTourismPlaceUseCase,
    private val getPopularDestinationUseCase: GetPopularDestinationUseCase,
    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase,
    private val setAlreadyShowLocationPermissionUseCase: SetAlreadyShowLocationPermissionUseCase,
    @Assisted savedStateHandle: SavedStateHandle
) : BaseViewModel<HomeViewState, HomeViewAction, HomeActionResult>(
    initialState = HomeViewState(), savedStateHandle = savedStateHandle
) {

    override fun HomeActionResult.updateViewState(): HomeViewState = when (this) {
        is HomeActionResult.GetNearbyTourismPlaceResult -> this.useCaseResult.mapResult()
        HomeActionResult.RefreshViewState -> currentViewState().copy(
            nearbyTourismPlacesSection = Content(HomeNearbyViewSection()),
            popularDestinationSection = Content(HomePopularDestinationViewSection())
        )
        HomeActionResult.ShowLocationPermission -> currentViewState().copy(
            isNeedShowLocationPermission = Content(true)
        )
        is HomeActionResult.GetPopularDestinationResult -> this.useCaseResult.mapResult()
    }

    override fun HomeViewAction.handleAction(): Flow<HomeActionResult> = channelFlow {
        when (this@handleAction) {
            HomeViewAction.LoadData -> {
                if (checkLocationPermissionUseCase.getResult().needShow) {
                    delay(1500)
                    send(HomeActionResult.ShowLocationPermission)
                } else {
                    launch {
                        val useCaseResult = getNearbyTourismPlaceUseCase.getResult(
                            param = GetNearbyTourismPlaceUseCase.Param(10)
                        )
                        send(HomeActionResult.GetNearbyTourismPlaceResult(useCaseResult))
                    }
                    launch {
                        val popularUseCaseResult = getPopularDestinationUseCase.getResult()
                        send(HomeActionResult.GetPopularDestinationResult(popularUseCaseResult))
                    }
                }
            }
            HomeViewAction.RefreshData -> {
                send(HomeActionResult.RefreshViewState)
                delay(200)
                onAction(HomeViewAction.LoadData)
            }
            HomeViewAction.AlreadyShowLocationPermission -> {
                setAlreadyShowLocationPermissionUseCase.getResult()
                onAction(HomeViewAction.RefreshData)
            }
        }
    }

    private fun GetNearbyTourismPlaceUseCase.UseCaseResult.mapResult(): HomeViewState =
        when (this) {
            is GetNearbyTourismPlaceUseCase.UseCaseResult.Success -> updateNearbyTourismPlacesSection(
                data = this.data,
                coordinates = this.userCoordinate,
                errorType = ViewSection.ErrorType.NONE
            )
            is GetNearbyTourismPlaceUseCase.UseCaseResult.Failed -> updateNearbyTourismPlacesSection(
                errorType = ViewSection.ErrorType.GENERAL
            )
            GetNearbyTourismPlaceUseCase.UseCaseResult.LocationPermissionNotGranted -> updateNearbyTourismPlacesSection(
                errorType = ViewSection.ErrorType.NO_PERMISSION
            )
            GetNearbyTourismPlaceUseCase.UseCaseResult.Empty -> updateNearbyTourismPlacesSection(
                errorType = ViewSection.ErrorType.EMPTY
            )
        }

    private fun updateNearbyTourismPlacesSection(
        data: List<TourismPlace> = emptyList(),
        coordinates: Coordinates = Coordinates(0.0, 0.0),
        errorType: ViewSection.ErrorType
    ): HomeViewState {
        return currentViewState().copy(
            nearbyTourismPlacesSection = Content(
                HomeNearbyViewSection(
                    data = data,
                    userCoordinates = coordinates,
                    isLoading = false,
                    errorType = errorType
                )
            )
        )
    }

    private fun GetPopularDestinationUseCase.UseCaseResult.mapResult(): HomeViewState =
        when (this) {
            is GetPopularDestinationUseCase.UseCaseResult.Success -> {
                currentViewState().copy(
                    popularDestinationSection = Content(
                        HomePopularDestinationViewSection(
                            title = StringWrapper.wrap(this.title),
                            data = this.data,
                            errorType = ViewSection.ErrorType.NONE,
                            isLoading = false
                        )
                    )
                )
            }
            is GetPopularDestinationUseCase.UseCaseResult.Failed -> {
                currentViewState().copy(
                    popularDestinationSection = Content(
                        HomePopularDestinationViewSection(
                            title = StringWrapper.wrap(""),
                            data = emptyList(),
                            errorType = ViewSection.ErrorType.GENERAL,
                            isLoading = false
                        )
                    )
                )
            }
        }
}