package com.example.mainscreenlayout.utils

object QueryUtils {
    val idToName = mapOf(Pair("thought_journal", "Журнал мыслей"),
        Pair("hot_thought", "Горячие мысли"),
        Pair("visualization", "Визулизация"),
        Pair("abdominal_breathing", "Абдоминальное дыхание"),
        Pair("breathe_control", "Техника контроля над дыханием"),
    Pair("cognitive_restructurization", "Когнитивная реструктуризация"),
    Pair("default", "Разговор"),
    Pair("diving", "Погружение"),
        Pair("fast_relaxation", "Быстрая релаксация"),
    Pair("hot_thought", "Горячие мысли"),
    Pair("imagery_coping", "Преодоление в воображении"),
    Pair("inner_desensitization", "Внутренняя десенсибилизация"),
    Pair("problem_solving", " Решение проблем"),
    Pair("risk_estimation", "Оценка риска"),
    Pair("signal_relaxation", "Сигнальная релаксация"),
    Pair("stress_shot", "Прививка от стресса"),
    Pair("thought_fixation", "Фиксация мыслей"),
    Pair("thought_journal", "Ведение журнала мыслей"),
    Pair("visualization", "Визуализация"))

    val packToExercise = mapOf(Pair(
        "depression", listOf("thought_journal", "hot_thought", "problem_solving")),
    Pair("anxiety", listOf("abdominal_breathing", "fast_relaxation", "signal_relaxation",
    "visualization", "problem_solving")),
    Pair("avoiding", listOf("abdominal_breathing", "fast_relaxation", "signal_relaxation",
        "visualization", "imagery_coping")),
    Pair("low_self_esteem", listOf("thought_journal", "hot_thought", "thought_fixation")),
    Pair("panic", listOf("breathe_control", "stress_shot")),
    Pair("perfectionism", listOf("thought_journal", "hot_thought", "stress_shot")),
    Pair("procrastination", listOf("thought_journal", "hot_thought")))
}
