package com.example.mainscreenlayout.domain.utils

import android.content.Context
import java.io.File
import kotlin.reflect.KClass

class ProjectClassScanner(context: Context, private val file: File): ClassScanner(context) {

	private val classes = setOf("ExerciseRepository", "GamificationSystem", "Recommendation", "MoodGraph", "AlarmReceiver", "EveryDayNotificationManager", "ClassScanner", "JsonConverters", "PermissionRequestManager", "PersonalDataManager", "ProjectClassScanner", "QueryUtils", "FirestoreDatabase", "FirestoreRepository", "IMessageRepository", "MessageRepositoryImpl", "PersonalDatabase", "PersonalDataDao", "RoomRepository", "Answer", "FirebaseResponse", "HistoryItem", "MarkableItem", "MarkedItem", "Message", "Question", "Record", "MainActivity", "PasswordFragment", "SettingsActivity", "ChatAdapter", "CommandAdapter", "ExerciseAdapter", "HistoryAdapter", "MarkedItemAdapter", "MarketAdapter", "MonthCalendar", "RecordAdapter", "RoundedRectangleItemAdapter", "RoundItemAdapter", "AnswerFragment", "AnswerViewModel", "ChatFragment", "ChatViewModel", "ChatViewModelFactory", "DashboardFragment", "DashboardViewModel", "ExerciseListFragment", "ExerciseListViewModel", "FavoritesFragment", "FavoritesViewModel", "HistoryFragment", "HistoryViewModel", "HomeFragment", "HomeViewModel", "HomeViewModelFactory", "MarketActivity", "MarketFragment", "MarketViewModel", "MarketViewModelFactory", "NicknameFragment", "NicknameViewModel", "QuestionActivity", "QuestionFragment", "QuestionViewModel", "RecordActivity", "RecordFragment", "RecordViewModel", "RecordViewModelFactory", "SplashFragment", "SplashViewModel", "StartActivity")

    override fun isTargetClassName(className: String?): Boolean {
        return className?.startsWith("com.example") == true
    }

    override fun isTargetClass(clazz: KClass<*>?): Boolean {
        if (clazz == null) {
            return false
        }
        return classes.contains(clazz.simpleName)
    }

    private val params = listOf("Имя", "Модификатор доступа", "Тип", "Аргументы")

    override fun onScanResult(clazz: KClass<*>?) {
        if (clazz == null) {
            return
        }
        if (clazz.simpleName == null) {
            return
        }
        file.appendText(clazz.simpleName!!)
        file.appendText("\n")
        val members = clazz.members
        for (member in members) {
            val params = member.parameters.map { it.name + ": " + it.type }
            val values = listOf(member.name, member.visibility,
                member.returnType, params)
            file.appendText(values.joinToString(separator=";"))
            file.appendText("\n")
        }
    }
}