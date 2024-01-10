package com.jay.quiz.quizactivity.playfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jay.quiz.R
import com.jay.quiz.databinding.PlayFragmentBinding

class PlayFragment : Fragment() {

    private lateinit var binding:PlayFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.play_fragment,container,false)
        return binding.root
    }
}