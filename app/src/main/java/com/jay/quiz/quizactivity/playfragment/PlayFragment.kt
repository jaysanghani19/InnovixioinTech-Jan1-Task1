package com.jay.quiz.quizactivity.playfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jay.quiz.R
import com.jay.quiz.databinding.PlayFragmentBinding
import com.jay.quiz.quizactivity.QuizActivity
import com.jay.quiz.quizactivity.quizfragment.QuizFragment
import com.jay.quiz.roomdatabase.QuestionTableDao
import com.jay.quiz.roomdatabase.QuizDatabase

class PlayFragment : Fragment() {

    private lateinit var binding:PlayFragmentBinding

    private lateinit var viewModel: PlayFragmentViewModel

    private lateinit var dao: QuestionTableDao

    var quizName = arguments?.getString(QuizActivity.QUIZ_NAME_KEY)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.play_fragment,container,false)
        init()
        viewModel.currentQuestion.observe(viewLifecycleOwner){it->
            if (it!=null) {
                binding.questionTextview.text = it.question

                binding.answer1RadioButton.text = it.option1
                binding.answer2RadioButton.text = it.option2
                binding.answer3RadioButton.text = it.option3
                binding.answer4RadioButton.text = it.option4
            }
            else{
                viewModel.setNextQuestion()
            }
        }

        viewModel.isGameFinished.observe(viewLifecycleOwner){it->
            if (it){
                viewModel.score.observe(viewLifecycleOwner){
                    showAlertDialog(it,viewModel.totalQuestion)

                }
            }
        }
        return binding.root
    }

    private fun init(){
        quizName = arguments?.getString(QuizActivity.QUIZ_NAME_KEY)
        dao = QuizDatabase.getInstance(requireContext())?.getQuestionDao()!!
        val provider = PlayFragmentViewModelProvider(dao)
        viewModel = ViewModelProvider(this,provider).get(PlayFragmentViewModel::class.java)
        viewModel.setQuestions(quizName!!)
        clickListeners()
    }

    private fun clickListeners(){
        binding.nextButton.setOnClickListener {
            clickListenersOfNextButton()
        }

        binding.skipButton.setOnClickListener {
            viewModel.skipButtonClick()
        }
    }

    private fun clickListenersOfNextButton(){
        val answerID = binding.optionsRadioGroup.checkedRadioButtonId
        if (answerID==-1){
            Toast.makeText(requireContext(), "Select The Answer", Toast.LENGTH_SHORT).show()
        }
        val answer = when(answerID){
            R.id.answer1_radio_button -> 1
            R.id.answer2_radio_button -> 2
            R.id.answer3_radio_button -> 3
            else -> 3
        }
        viewModel.nextButtonClick(answer)
    }

    private fun showAlertDialog(score : Int , totalQuestions:Int) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        // Set the title and message for the dialog
        alertDialogBuilder.setTitle("Score")
        alertDialogBuilder.setMessage("You Scored $score/$totalQuestions")

        // Set the positive button and its click listener
        alertDialogBuilder.setPositiveButton("Okay") { dialog, which ->
            val quizFragment = QuizFragment()
            val bundle = Bundle()
            bundle.apply {
                bundle.putString(QuizActivity.QUIZ_NAME_KEY,quizName)
            }
            quizFragment.arguments = bundle
            replaceFragment(quizFragment)
            dialog.dismiss()
        }

        // Set the negative button and its click listener
        alertDialogBuilder.setNegativeButton("Retry") { dialog, which ->
            viewModel.setQuestions(quizName!!)
            dialog.dismiss()
        }
        val alertDialog: AlertDialog = alertDialogBuilder.create()

        // Create and show the AlertDialog
        alertDialog.show()
    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.quiz_activity_fragment_frame, fragment).commit()
    }
}