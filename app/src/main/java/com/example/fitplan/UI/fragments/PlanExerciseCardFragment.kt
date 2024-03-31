package com.example.fitplan.UI.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.fitplan.R
import com.example.fitplan.view_models.SharedViewModel
import com.example.fitplan.adapters.PlanExerciseAdapter
import com.example.fitplan.databinding.PlanExerciseCardLayoutBinding
import com.example.fitplan.model.Exercise
import com.google.android.material.bottomnavigation.BottomNavigationView


class PlanExerciseCardFragment : Fragment() {
    private var _binding: PlanExerciseCardLayoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var planExerciseAdapter: PlanExerciseAdapter

    private val sharedViewModel : SharedViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PlanExerciseCardLayoutBinding.inflate(inflater, container, false)

        sharedViewModel.selectedExercise.observe(viewLifecycleOwner) { exercise ->

            Glide.with(requireContext()).asGif().load(exercise.image).into(binding.exerciseImage)
            binding.titleTv.text = exercise.name
            binding.descriptionTv.text = exercise.description

            IncreaseDecreaseUI(exercise)

            binding.addExerciseBtn.setOnClickListener {

                exercise.reps = binding.repsText.text.toString().toIntOrNull() ?: 0

                val minutes = binding.minutesTv.text.toString().split(":").last().trim().toIntOrNull() ?: 0
                val seconds = binding.secondsTv.text.toString().split(":").last().trim().toIntOrNull() ?: 0
                val totalMilliseconds = ((minutes * 60) + seconds) * 1000L
                exercise.time = totalMilliseconds

                exercise.generateId()

                sharedViewModel.addExerciseToPlan(exercise)
                //exercisesToPlan.add(exercise)
                Toast.makeText(requireContext(), "${exercise.name}" + getString(R.string.added_to_your_plan), Toast.LENGTH_SHORT).show()
            }
        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.GONE

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun IncreaseDecreaseUI(exercise: Exercise) {

        var totalMinutes = 0
        var totalSeconds = 0

        binding.minutesTv.text = getString(R.string.minutes)+" ${totalMinutes}"
        binding.secondsTv.text = getString(R.string.seconds)+" ${totalSeconds}"

        binding.increaseMinutesBtn.setOnClickListener {
            binding.minutesTv.text = getString(R.string.minutes)+" ${++totalMinutes}"
        }

        binding.decreaseMinutesBtn.setOnClickListener {
            if (totalMinutes == 0) totalMinutes = 1
            binding.minutesTv.text = getString(R.string.minutes)+" ${--totalMinutes}"
        }

        binding.increaseSecondBtn.setOnClickListener {
            binding.secondsTv.text = getString(R.string.seconds)+" ${++totalSeconds}"
        }

        binding.decreaseSecondBtn.setOnClickListener {
            if (totalSeconds == 0) totalSeconds = 1
            binding.secondsTv.text = getString(R.string.seconds)+" ${--totalSeconds}"
        }

    }

}