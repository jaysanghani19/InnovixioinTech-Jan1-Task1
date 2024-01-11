package com.jay.quiz.quizactivity.playfragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jay.quiz.roomdatabase.QuestionTable
import com.jay.quiz.roomdatabase.QuestionTableDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlayFragmentViewModel(val dao : QuestionTableDao) : ViewModel() {

    private val _currentQuestion = MutableLiveData<QuestionTable>()
    val currentQuestion : LiveData<QuestionTable>
        get() = _currentQuestion

    private val _score = MutableLiveData<Int>()
    val score : LiveData<Int>
        get() = _score

    private val _isGameFinished = MutableLiveData<Boolean>()
    val isGameFinished : LiveData<Boolean>
        get() = _isGameFinished

    var totalQuestion = 0
    var ques : MutableList<QuestionTable>? = mutableListOf()


    init {
        Log.i("Play", "init: Init in ViewModel")

        _score.value = 0
        _isGameFinished.value = false
    }
    fun setQuestions(quizName:String){
        Log.i("Play", "init: Set Questions")

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val temp = dao.getQuestionOfTheQuiz(quizName)

                withContext(Dispatchers.Main){
                    temp.observeForever {
                        if (it!!.isEmpty()){
                            setQuestions(quizName)
                        }
                        it?.toMutableList().also { ques = it }
                    }
                    if (ques!!.isEmpty()){
                        setQuestions(quizName)
                    }
                    setNextQuestion()
                    totalQuestion = ques?.size!!
                }
            }
        }
    }

    fun skipButtonClick(){
        setNextQuestion()
    }

    fun nextButtonClick(answer:Int){
        if (_currentQuestion.value?.answer == answer) {
            _score.value = (_score.value)?.plus(1)
        }
        setNextQuestion()
    }

    fun setNextQuestion(){
        if (ques!!.isEmpty()){
            _isGameFinished.value = true
        }
        else {
            _currentQuestion.value = ques?.removeAt(0)
        }
    }
}