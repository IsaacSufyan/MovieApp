package com.isaacsufyan.androidarchitecture.mvvm.movieapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.MovieRepository
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddViewModelTest {

    private lateinit var addViewModel: AddViewModel

    @Mock
    lateinit var repository: MovieRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        addViewModel = AddViewModel(repository)
    }

    @Test
    fun validateCanSaveMovie_emptyTitleEmptyDate_returnFalse() {
        addViewModel.title.set("")
        addViewModel.releaseDate.set("")
        val canSaveMovie = addViewModel.validateCanSaveMovie()
        assertEquals(false, canSaveMovie)
    }

    @Test
    fun validateCanSaveMovie_emptyTitle_returnFalse() {
        addViewModel.title.set("")
        addViewModel.releaseDate.set("2022")
        val canSaveMovie = addViewModel.validateCanSaveMovie()
        MatcherAssert.assertThat(canSaveMovie, `is`(false))
    }

    @Test
    fun validateCanSaveMovie_emptyDate_returnFalse() {
        addViewModel.title.set("Awesome Movie I")
        addViewModel.releaseDate.set("")
        val canSaveMovie = addViewModel.validateCanSaveMovie()
        MatcherAssert.assertThat(canSaveMovie, `is`(false))
    }

    @Test
    fun validateCanSaveMovie_nullTitleNullDate_returnFalse() {
        addViewModel.title.set(null)
        addViewModel.releaseDate.set(null)
        val canSaveMovie = addViewModel.validateCanSaveMovie()
        MatcherAssert.assertThat(canSaveMovie, `is`(false))
    }

    @Test
    fun saveMovie_validData_returnTrue() {
        addViewModel.title.set("Awesome Movie II")
        addViewModel.releaseDate.set("1994")
        addViewModel.saveMovie()
        MatcherAssert.assertThat(addViewModel.saveMovie.value, `is`(true))
    }

    @Test
    fun validateCanSearchMovie_emptyTitle_returnFalse() {
        addViewModel.title.set("")
        MatcherAssert.assertThat(addViewModel.validateCanSaveSearch(), `is`(false) )
    }

    @Test
    fun validateCanSearchMovie_nullTitle_returnFalse() {
        addViewModel.title.set(null)
        MatcherAssert.assertThat(addViewModel.validateCanSaveSearch(), `is`(false) )
    }

    @Test
    fun openSearchScreen_valid_returnNotNull() {
        addViewModel.title.set("Awesome Movie II")
        addViewModel.openSearchScreen()
        MatcherAssert.assertThat(addViewModel.searchMovie.value, not(nullValue()))
        MatcherAssert.assertThat(addViewModel.searchMovie.value, `is`("Awesome Movie II"))
    }
}