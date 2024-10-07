package com.example.cookieclicker

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ScoreboardViewModel : ViewModel(){
    var scores = mutableStateListOf<Pair<String,Int>>()

    fun addScore(name: String, score: Int){
        scores.add(Pair(name, score))
        scores.sortByDescending{ it.second }
    }
}