package com.example.android.allaboutclubs.domain.use_case

import com.example.android.allaboutclubs.data.repository.FakeRepository
import com.example.android.allaboutclubs.domain.model.Club
import com.google.common.truth.*
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetClubsListUseCaseTest{
    private lateinit var getClubsListUseCase: GetClubsListUseCase
    private lateinit var fakeRepo:FakeRepository

    // Create Club objects
    val club1 = Club(
        id = "cc97b53a-3213-42bf-90c7-85c276347405",
        country = "England",
        european_titles = 3,
        image = "https://public.allaboutapps.at/hiring/images/manu.png",
        name = "Manchester United",
        stadiumName = "Old Trafford",
        value = 801
    )

    val club2 = Club(
        id = "88e02a96-3e73-41e6-84bc-0c61aada0dc3",
        country = "England",
        european_titles = 0,
        image = "https://public.allaboutapps.at/hiring/images/arsenal.png",
        name = "Arsenal FC",
        stadiumName = "Emirates Stadium",
        value = 522
    )

    val club3 = Club(
        id = "f354b48e-2405-4793-99bc-613b1ebd91c9",
        country = "England",
        european_titles = 1,
        image = "https://public.allaboutapps.at/hiring/images/chelsea.png",
        name = "FC Chelsea",
        stadiumName = "Stamford Bridge",
        value = 792
    )

    // Create a list of Club objects
    val clubList = listOf(club1, club2, club3)

    @Before
    fun setUp(){
        fakeRepo= FakeRepository()
        fakeRepo.setClubList(clubList)
        getClubsListUseCase= GetClubsListUseCase(fakeRepo)
    }

    @Test
    fun `Order clubs by valuation correctly`() = runBlocking{
        val orderedClubs=getClubsListUseCase(Sorting.ByValueDesc).first()
        for (i in 0..orderedClubs.size-2){
            assertThat(orderedClubs[i].value).isGreaterThan(orderedClubs[i+1].value)
        }
    }

    @Test
    fun `Order clubs alphabetically correctly`() = runBlocking{
        val orderedClubs=getClubsListUseCase(Sorting.ByValueDesc).first()
        for (i in 0..orderedClubs.size-2){
            assertThat(orderedClubs[i].name).isGreaterThan(orderedClubs[i+1].name)
        }
    }
}