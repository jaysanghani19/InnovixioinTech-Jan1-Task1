package com.jay.quiz.quizactivity.quizfragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.quiz.databinding.QuizFragmentBinding
import com.jay.quiz.R
import com.jay.quiz.adapters.QuestionAdapter
import com.jay.quiz.adapters.QuizAdapter
import com.jay.quiz.databinding.AddQuestionFragmentBinding
import com.jay.quiz.quizactivity.QuizActivity
import com.jay.quiz.quizactivity.playfragment.PlayFragment
import com.jay.quiz.roomdatabase.QuestionTable
import com.jay.quiz.roomdatabase.QuestionTableDao
import com.jay.quiz.roomdatabase.QuizDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizFragment : Fragment() {

    private lateinit var binding: QuizFragmentBinding

    private lateinit var questionDao: QuestionTableDao

    private var isQuestionsAreSet = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.quiz_fragment, container, false)
        init()
        return binding.root
    }

    private fun init() {
        val quizName = arguments?.getString(QuizActivity.QUIZ_NAME_KEY)
        questionDao = QuizDatabase.getInstance(requireContext())?.getQuestionDao()!!

        setQuestionRecyclerView(quizName!!)


        binding.title.text = quizName
        binding.addButton.setOnClickListener {
            clickListenerOfAddButton(quizName!!)
        }

        binding.playButton.setOnClickListener {
            clickListenerOfPlayButton(quizName!!)
        }
    }

    private fun setQuestionRecyclerView(quizName: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val questions = questionDao.getQuestionOfTheQuiz(quizName)
                withContext(Dispatchers.Main) {
                    binding.questionsRecyclerView.layoutManager =
                        LinearLayoutManager(requireContext())
                    questions.observe(viewLifecycleOwner) { it ->
                        if (it.size>0){
                            isQuestionsAreSet = true
                        }
                        binding.questionsRecyclerView.adapter = QuestionAdapter(it)
                    }
                }
            }
        }

    }

    private fun clickListenerOfAddButton(quizName: String) {
        showAddDialog(quizName)
    }

    private fun clickListenerOfPlayButton(quizName: String) {
        if (isQuestionsAreSet) {
            var playFragment = PlayFragment()
            val bundle = Bundle()
            bundle.apply {
                bundle.putString(QuizActivity.QUIZ_NAME_KEY, quizName)
            }
            playFragment.arguments = bundle
            replaceFragment(playFragment)
        } else{
            Toast.makeText(requireContext(), "Add Questions", Toast.LENGTH_LONG).show()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.quiz_activity_fragment_frame, fragment).commit()
    }

    private fun showAddDialog(quizName: String) {
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.add_question_fragment, null)
        builder.setView(dialogView)
        val dialog = builder.create()
        val questionET = dialogView.findViewById<EditText>(R.id.questionEditText)
        val option1ET = dialogView.findViewById<EditText>(R.id.option1EditText)
        val option2ET = dialogView.findViewById<EditText>(R.id.option2EditText)
        val option3ET = dialogView.findViewById<EditText>(R.id.option3EditText)
        val option4ET = dialogView.findViewById<EditText>(R.id.option4EditText)

        val optionsRadioGroup = dialogView.findViewById<RadioGroup>(R.id.optionsRadioGroup)

        val submitButton = dialogView.findViewById<Button>(R.id.submitButton)

        submitButton.setOnClickListener {
            if (TextUtils.isEmpty(questionET.text.toString())) {
                questionET.error = "Enter the Question"
            } else if (TextUtils.isEmpty(option1ET.text.toString())) {
                option1ET.error = "Enter the option"
            } else if (TextUtils.isEmpty(option2ET.text.toString())) {
                option2ET.error = "Enter the option"
            } else if (TextUtils.isEmpty(option3ET.text.toString())) {
                option3ET.error = "Enter the option"
            } else if (TextUtils.isEmpty(option4ET.text.toString())) {
                option4ET.error = "Enter the option"
            } else {
                val selectedOption = optionsRadioGroup.checkedRadioButtonId

                if (selectedOption == -1) {
                    Toast.makeText(
                        requireContext(),
                        "Select the Right Answer. From Options",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val answerOption = when (selectedOption) {
                        R.id.option1_radio_button -> 1
                        R.id.option2_radio_button -> 2
                        R.id.option3_radio_button -> 3
                        else -> 4
                    }

                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            val question = QuestionTable(
                                0,
                                quizName,
                                questionET.text.toString(),
                                option1ET.text.toString(),
                                option2ET.text.toString(),
                                option3ET.text.toString(),
                                option4ET.text.toString(),
                                answerOption
                            )
                            questionDao.insertQuestion(question)
                            withContext(Dispatchers.Main) {
                                dialog.dismiss()
                            }
                        }
                    }
                }
            }
        }
        dialog.show()

    }
}