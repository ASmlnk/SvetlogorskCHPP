package com.example.daggerlessons.lessons19_Hilt_EntryPoint

import com.example.daggerlessons.lessons15_Hilt.DatabaseHelper15
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface DatabaseEntryPointActivity {
    fun getDatabaseHelper(): DatabaseHelper15
}
