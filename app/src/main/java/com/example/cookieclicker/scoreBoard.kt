package com.example.cookieclicker

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ScoreboardViewModel : ViewModel(){
    private lateinit var scoreboardManager :ScoreboardManager

    var scores = mutableStateListOf<Pair<String,Int>>()

    private fun loadScores(){
        scores.clear()
        scores.addAll(scoreboardManager.getScores())
    }

    fun initializeScoreboardManager(context: Context){
        scoreboardManager = ScoreboardManager(context)
        loadScores()
    }

    private fun saveScores(){
        scoreboardManager.saveScores(scores.toList())
    }

    fun addScore(name: String, score: Int){
        scores.add(Pair(name, score))
        scores.sortByDescending{ it.second }
        saveScores()
    }

    fun clearScore(){
        scores.clear()
        saveScores()
    }
}

class ScoreboardManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("score_data", Context.MODE_PRIVATE)

    companion object {
        private const val SCORES_KEY = "scores_list"
    }

    fun saveScores(scores: List<Pair<String, Int>>) {
        if(scores.isNotEmpty()){
            val serializedScores = scores.joinToString(separator = ";") { "${it.first}:${it.second}" }
            sharedPreferences.edit().putString(SCORES_KEY, serializedScores).apply()
        }
        else{
            sharedPreferences.edit().putString(SCORES_KEY, null).apply()
        }
    }

    fun getScores(): List<Pair<String, Int>> {
        val serializedScores = sharedPreferences.getString(SCORES_KEY, null) ?: return emptyList()

        return serializedScores.split(";")
            .map {
                val parts = it.split(":")
                Pair(parts[0], parts[1].toInt())
            }
    }
}