package com.example.mainscreenlayout.ui.home

import com.example.mainscreenlayout.model.Exercise

interface IExerciseRepository {

    fun getExercises(): List<Exercise>
}