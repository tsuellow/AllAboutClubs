package com.example.android.allaboutclubs.presentation.common_components

sealed class Screen(val route:String) {
    object ClubsListScreen:Screen("club_list")
    object ClubDetailScreen:Screen("club_detail")
}