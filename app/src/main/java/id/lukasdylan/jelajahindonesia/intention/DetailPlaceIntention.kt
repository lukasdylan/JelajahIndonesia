package id.lukasdylan.jelajahindonesia.intention

import id.lukasdylan.jelajahindonesia.model.contract.DetailTourismPlace
import id.lukasdylan.jelajahindonesia.usecase.GetDetailTourismPlaceUseCase
import id.lukasdylan.jelajahindonesia.utils.Content
import id.lukasdylan.jelajahindonesia.utils.StringWrapper

/**
 * Created by Lukas Dylan on 29/10/20.
 */
data class DetailPlaceViewState(
    val detailPlace: Content<DetailTourismPlace?> = Content(null),
    val isLoading: Content<Boolean> = Content(true),
    val title: Content<String> = Content(""),
    val errorLoadDetailPlace: Content<StringWrapper> = Content(StringWrapper.StringType(""))
) : ViewState

sealed class DetailPlaceViewAction : ViewAction {
    data class LoadDetailPlace(val xid: String, val title: String) : DetailPlaceViewAction()
}

sealed class DetailPlaceActionResult : ActionResult {
    data class SetScreenTitle(val title: String) : DetailPlaceActionResult()
    data class GetDetailPlaceResult(val useCaseResult: GetDetailTourismPlaceUseCase.UseCaseResult) :
        DetailPlaceActionResult()
}