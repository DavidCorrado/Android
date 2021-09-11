package com.corradodev.todo.extension

import com.corradodev.todo.data.ViewState
import com.corradodev.todo.data.successData
import com.dropbox.android.external.store4.ResponseOrigin
import com.dropbox.android.external.store4.StoreResponse
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class FlowStoreResultExtensionTest {
    @Test
    fun storeResponseLoadingToViewStateLoading() = runBlockingTest {
        val loadingState =
            flow<StoreResponse<Unit>> { emit(StoreResponse.Loading(origin = ResponseOrigin.Cache)) }.toResult()
                .first()
        assertThat(loadingState).isEqualTo(ViewState.Loading)
    }

    @Test
    fun storeResponseErrorExceptionToViewStateError() = runBlockingTest {
        val exception = Exception("")
        val errorState = flow<StoreResponse<Unit>> {
            emit(
                StoreResponse.Error.Exception(
                    exception,
                    origin = ResponseOrigin.Cache
                )
            )
        }.toResult().first()
        assertThat(errorState).isEqualTo(ViewState.Error(exception))
    }

    @Test
    fun storeResponseErrorMessageToViewStateError() = runBlockingTest {
        val error = "Message"
        val errorState = flow<StoreResponse<Unit>> {
            emit(
                StoreResponse.Error.Message(
                    error,
                    origin = ResponseOrigin.Cache
                )
            )
        }.toResult().first() as ViewState.Error
        assertThat(errorState.throwable.message).isEqualTo(error)
    }

    @Test
    fun storeResponseSuccessToViewStateSuccess() = runBlockingTest {
        val success = "Message"
        val successData = flow<StoreResponse<String>> {
            emit(
                StoreResponse.Data(
                    success,
                    origin = ResponseOrigin.Cache
                )
            )
        }.toResult().first().successData
        assertThat(successData).isEqualTo(success)
    }
}
