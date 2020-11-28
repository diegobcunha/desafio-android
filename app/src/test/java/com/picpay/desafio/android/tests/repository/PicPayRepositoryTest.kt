package com.picpay.desafio.android.tests.repository

import com.picpay.desafio.android.fixture.userList
import com.picpay.desafio.android.model.PicPayRepository
import com.picpay.desafio.android.model.resource.Resource
import com.picpay.desafio.android.model.retrofit.PicPayService
import com.picpay.desafio.android.templates.BaseTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class PicPayRepositoryTest : BaseTest() {

    private lateinit var apiService: PicPayService
    private lateinit var repository: PicPayRepository

    @Before
    fun setup() {
        apiService = mockk()
        repository = PicPayRepository(apiService)
    }

    @Test
    fun `WHEN a network call is make THEN should return success`() = runBlockingTest {
        coEvery { apiService.getUsers() } returns Resource.success(userList)
        Assert.assertEquals(Resource.success(userList), apiService.getUsers())
    }
}