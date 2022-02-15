package com.example.mainscreenlayout.ui.home

import com.example.mainscreenlayout.model.ExerciseRepository

interface IExerciseRepository {

    fun getExercises(): List<ExerciseRepository>
}