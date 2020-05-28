package com.ag.quizapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class ResultFragment extends Fragment {

    private ProgressBar resultProgressBar;
    private TextView resultProgressText, resultCorrectText, resultWrongText, resultMissedText;
    private Button resultGoHomeBtn;

    public ResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resultProgressBar = view.findViewById(R.id.result_progress_bar);
        resultProgressText = view.findViewById(R.id.result_progress_text);
        resultCorrectText = view.findViewById(R.id.result_correct_text);
        resultWrongText = view.findViewById(R.id.result_wrong_text);
        resultMissedText = view.findViewById(R.id.result_missed_text);
        resultGoHomeBtn = view.findViewById(R.id.result_go_home_btn);
    }
}
