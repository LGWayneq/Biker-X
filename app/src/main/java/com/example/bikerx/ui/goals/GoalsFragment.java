package com.example.bikerx.ui.goals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bikerx.MainActivity;
import com.example.bikerx.entities.Goal;

import com.example.bikerx.databinding.GoalsFragmentBinding;

import java.util.HashMap;


public class GoalsFragment extends Fragment {
    private GoalsFragmentBinding binding;
    private GoalsViewModel viewModel;
    private String userId;

    /**
     * Initialises goals fragment
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = GoalsFragmentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(GoalsViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // initializing our object class variable.
        bindButtons();
        userId = ((MainActivity)getActivity()).getUserId();
        viewModel.getCyclingHistory(userId);
        displayGoalsData();
    }

    /**Helper function to format time (in milliseconds) to Chronometer display.
     * @param monthDuration Time to be formatted.
     * @return Returns a String, representing time formatted as "HHh MMm".
     */
    private String getChronometerDisplay(Long monthDuration) {
        int h = (int) ((monthDuration) / 3600000);
        int m = (int) (((monthDuration) / 60) % 60);

        String mString = m >= 10 ? Integer.toString(m) : "0"+Integer.toString(m);
        return String.format("%dh %sm", h, mString);
    }


    /**
     * This method dictates the logic of the buttons in the fragment.
     */
    private void bindButtons() {
        //update monthlydistance goals to database and reflect the updated value on the UI
        binding.MonthlyDistanceGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String monthlyDistanceInKm = binding.InputMonthlyDistanceGoal.getText().toString();

                //check for valid input
                if (!isValidInput(monthlyDistanceInKm)) {
                    Toast.makeText(getActivity(), "Error: Please provide numeric input.", Toast.LENGTH_LONG).show();
                } else {
                    float inputFloat = Float.parseFloat(monthlyDistanceInKm);
                    int roundedInput = Math.round(inputFloat);

                    binding.MonthlyGoalsProgressBar.setMax(roundedInput);
                    String percentage = calPercentage(binding.MonthlyGoalsProgressBar.getProgress(),binding.MonthlyGoalsProgressBar.getMax());
                    binding.GoalsPercentage.setText(percentage + "%");
                    binding.MonthlyDistanceGoal.setText(String.valueOf(roundedInput));

                    long duration = Long.parseLong(binding.MonthlyTimeGoal.getText().toString())* 3600 * 1000;
                    Goal newGoal = new Goal((double) roundedInput, duration);
                    viewModel.updateGoal(userId, newGoal);
                }
            }
        });

        //update MonthlyTime goals to database and reflect the updated value on the UI
        binding.MonthlyTimeGoalButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String monthlyTimeInHours = binding.InputMonthlyTimeGoal.getText().toString();

                //check for valid input
                if (!isValidInput(monthlyTimeInHours)) {
                    Toast.makeText(getActivity(), "Error: Please provide numeric input.", Toast.LENGTH_LONG).show();
                } else {
                    Float timeFloat = Float.parseFloat(monthlyTimeInHours);
                    int roundedTime = Math.round(timeFloat);

                    binding.MonthlyTimeGoal.setText(String.valueOf(roundedTime));

                    long duration = (long) roundedTime * 3600 * 1000;
                    Goal newGoal = new Goal(Double.parseDouble(binding.MonthlyDistanceGoal.getText().toString()), duration);
                    viewModel.updateGoal(userId, newGoal);
                }
            }
        });
    }

    /**Helper method to check if user input for goals is valid.
     * @param input The user's input in String format.
     * @return Returns a boolean based on whether the input is valid.
     */
    private boolean isValidInput(String input) {
        try {
            Float.parseFloat(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * update and display MonthlyDistanceGoal and MonthlyTimeGoal achieved data on the chronometer display
     * update and display MonthlyDistanceGoal achieved data on the progress bar
     */
    private void displayGoalsData() {
        binding.chronometer.setText(getChronometerDisplay(0L));
        viewModel.fetchGoals(userId);
        viewModel.getGoals().observe(getViewLifecycleOwner(), new Observer<Goal>() {
            @Override
            public void onChanged(Goal goal) {
                if (goal != null) {
                    //
                    binding.MonthlyDistanceGoal.setText(String.format("%d", (int)goal.getDistance()));
                    binding.MonthlyTimeGoal.setText(String.format("%d",(int) (goal.getDuration() / 3600000)));

                    binding.goalsChronometer.setText(getChronometerDisplay(goal.getDuration()));
                    binding.timeProgressBar.setMax((int) (goal.getDuration()/3600000));


                    binding.distanceGoalsFloat.setText(String.format("%.2f", goal.getDistance()));
                    binding.distanceProgressBar.setMax((int) goal.getDistance());
                    binding.MonthlyGoalsProgressBar.setMax((int) goal.getDistance());
                    String percentage = calPercentage(binding.MonthlyGoalsProgressBar.getProgress(),binding.MonthlyGoalsProgressBar.getMax());
                    binding.GoalsPercentage.setText(percentage + "%");
                }
            }
        });

        viewModel.calculateMonthlyData(this).observe(getViewLifecycleOwner(), new Observer<HashMap<String, Object>>() {
            @Override
            public void onChanged(HashMap<String, Object> hashMap) {

                if (hashMap != null) {
                    Double monthDistance = (Double)hashMap.get("monthDistance");
                    binding.distanceDetailsFloat.setText(String.format("%.2f", monthDistance));
                    binding.distanceProgressBar.setProgress(monthDistance.intValue());

                    long monthDuration = (Long) hashMap.get("monthDuration");
                    binding.chronometer.setText(getChronometerDisplay(monthDuration));
                    binding.timeProgressBar.setProgress((int) monthDuration);

                    binding.MonthlyGoalsProgressBar.setProgress(monthDistance.intValue());
                }


            }
        });

    }

    /**
     * Helper function to calculate percentage of goals (for distance) achieved by the user
     * @param progress distance cycled
     * @param max goal (in distance) set by user
     * @return percentage of goals (for distance) achieved by the user
     */
    private String calPercentage(int progress, int max){

        return Float.toString(Math.round(((float)progress/(float)max)*100));

    }
}