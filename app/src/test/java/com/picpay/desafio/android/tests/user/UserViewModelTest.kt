package com.picpay.desafio.android.tests.user

import com.picpay.desafio.android.coroutines.runBlockingUnitTest
import com.picpay.desafio.android.extensions.assertLiveDataEquals
import com.picpay.desafio.android.extensions.assertLiveDataError
import com.picpay.desafio.android.fixture.userList
import com.picpay.desafio.android.model.PicPayRepository
import com.picpay.desafio.android.model.data.User
import com.picpay.desafio.android.model.resource.Resource
import com.picpay.desafio.android.templates.BaseTest
import com.picpay.desafio.android.view.user.UserViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class UserViewModelTest : BaseTest() {

    private lateinit var repository: PicPayRepository
    private lateinit var viewModel: UserViewModel

    @Before
    fun setup() {
        repository = mockk()
        viewModel = UserViewModel(testDispatchersProvider, repository)
    }

    @Test
    fun `WHEN user enter in screen THEN should show loading info`() = runBlockingTest {
        mockAPIRequest(Resource.loading())
        viewModel.resourceLiveData.assertLiveDataEquals(null)
    }

    @Test
    fun `WHEN user enter the screens and have success THEN should see list of users`() =
        runBlockingTest {
            mockAPIRequest()
            viewModel.resourceLiveData.assertLiveDataEquals(userList)
        }

    @Test
    fun `WHEN  user have problems with network response THEN should see error info`() =
        runBlockingTest {
            mockAPIRequest(Resource.error())
            viewModel.resourceLiveData.assertLiveDataError()
        }


    private fun mockAPIRequest(response: Resource<List<User>> = Resource.success(userList)) {
        coroutineTestRule.runBlockingUnitTest {
            coEvery { repository.retrieveListUsers() } returns response
        }
    }
}