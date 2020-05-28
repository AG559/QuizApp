package com.ag.quizapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ag.quizapp.Helpers.QuestionViewModelFactory;
import com.ag.quizapp.Models.QuestionModel;
import com.ag.quizapp.ViewModels.QuestionViewModel;

import java.util.ArrayList;
import java.util.List;


public class QuizFragment extends Fragment implements View.OnClickListener {
    private TextView quizTitleTextView, questionTimeTextView,
            questionNumberTextView, questionTextView, questionFeedbackTextView;
    private Button optionOneBtn, optionTwoBtn, optionThreeBtn, nextBtn;
    private ProgressBar questionTimerProgress;


    //Firebase Data
    private List<QuestionModel> allQuestionList;
    private List<QuestionModel> questionsToAnswerList = new ArrayList<>();
    private Long questionToAnswerCount;

    private CountDownTimer countDownTimer;
    private boolean canAnswer = false;
    private int currentQuestion = 0;
    private int correctAnswers = 0;
    private int wrongAnswers = 0;
    private int notAnswers = 0;
    private String quizId;

    public QuizFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //UI Element
        quizTitleTextView = view.findViewById(R.id.quiz_title);
        optionOneBtn = view.findViewById(R.id.quiz_option_one_btn);
        optionTwoBtn = view.findViewById(R.id.quiz_option_two_btn);
        optionThreeBtn = view.findViewById(R.id.quiz_option_three_btn);
        questionTimeTextView = view.findViewById(R.id.quiz_question_time);
        questionNumberTextView = view.findViewById(R.id.quiz_question_number);
        questionTextView = view.findViewById(R.id.quiz_question);
        questionFeedbackTextView = view.findViewById(R.id.quiz_question_feedback);
        nextBtn = view.findViewById(R.id.quiz_next_btn);
        questionTimerProgress = view.findViewById(R.id.quiz_question_progess);

        //Args from Detail
        quizId = getArguments().getString("quizId");
        questionToAnswerCount = getArguments().getLong("questions");
    }


    private void loadUi() {
        //Quiz data load, Load UI
        quizTitleTextView.setText("Quiz Data Loaded");
        questionTextView.setText("Load First Question");

        //Enable Options
        enableOptions();

        //Load First Question
        loadQuestion(1);
    }

    private void loadQuestion(int questionNumber) {
        questionNumberTextView.setText(questionNumber + "");
        //Load Question Text
        questionTextView.setText(questionsToAnswerList.get(questionNumber - 1).getQuestion());

        //Load Options
        optionOneBtn.setText(questionsToAnswerList.get(questionNumber - 1).getOption_a());
        optionTwoBtn.setText(questionsToAnswerList.get(questionNumber - 1).getOption_b());
        optionThreeBtn.setText(questionsToAnswerList.get(questionNumber - 1).getOption_c());

        //set Timer
        startTimer(questionNumber);

        //Question loaded,set Can answer
        canAnswer = true;
        currentQuestion = questionNumber;
    }

    @SuppressLint("SetTextI18n")
    private void startTimer(final int questionNumber) {
        questionTimerProgress.setVisibility(View.VISIBLE);
        Long timeToAnswer = questionsToAnswerList.get(questionNumber - 1).getTimer();
        questionTimeTextView.setText(timeToAnswer.toString());
        countDownTimer = new CountDownTimer(timeToAnswer * 1000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                questionTimeTextView.setText(millisUntilFinished / 1000 + "");
                long progress = millisUntilFinished / (questionToAnswerCount * 10);
                questionTimerProgress.setProgress((int) progress);
            }

            @Override
            public void onFinish() {
                canAnswer = false;

                questionFeedbackTextView.setText("Time Up!No Answer was submitted.");
                questionFeedbackTextView.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                notAnswers++;
                showNextBtn();
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        QuestionViewModel questionviewModel = new ViewModelProvider(requireActivity(), new QuestionViewModelFactory(quizId)).get(QuestionViewModel.class);
        questionviewModel.getQuestionLiveModel().observe(getViewLifecycleOwner(), new Observer<List<QuestionModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(List<QuestionModel> questionModels) {
                if (questionModels.get(0).getThrowable() != null) {
                    quizTitleTextView.setText("Error Loading Data");
                }
                quizTitleTextView.setText("Loading Quiz...");
                allQuestionList = questionModels;
                pickQuestions();
                loadUi();
            }
        });

        //Set Button Click Listener
        optionOneBtn.setOnClickListener(this);
        optionTwoBtn.setOnClickListener(this);
        optionThreeBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
    }

    private void pickQuestions() {

        for (int i = 0; i < questionToAnswerCount; i++) {

            int random = getRandomNumber(allQuestionList.size(), 0);
            questionsToAnswerList.add(allQuestionList.get(random));
            allQuestionList.remove(random);
//            Log.d("Quizz", i + " : " + allQuestionList.get(random).getAnswer() + " After");
            Log.d("Quizz", i + " : " + questionsToAnswerList.get(i).getAnswer() + " answer to que");
        }
    }

    private int getRandomNumber(int maximum, int minimum) {
        return ((int) (Math.random() * (maximum - minimum))) + minimum;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quiz_option_one_btn:
                verifyAnswer(optionOneBtn);
                break;
            case R.id.quiz_option_two_btn:
                verifyAnswer(optionTwoBtn);
                break;
            case R.id.quiz_option_three_btn:
                verifyAnswer(optionThreeBtn);
                break;
            case R.id.quiz_next_btn:
                if (currentQuestion == questionToAnswerCount) {
                    submitResult();
                } else {
                    currentQuestion++;
                    loadQuestion(currentQuestion);
                    resetOptions();
                }
                break;
        }
    }

    private void submitResult() {
    }

    @SuppressLint("SetTextI18n")
    private void verifyAnswer(Button clickedAnswerBtn) {
        //Check Answer
        if (canAnswer) {
            clickedAnswerBtn.setTextColor(getResources().getColor(R.color.colorDark, null));
            if (questionsToAnswerList.get(currentQuestion - 1).getAnswer().contentEquals(clickedAnswerBtn.getText().toString())) {
                correctAnswers++;
                clickedAnswerBtn.setBackground(getResources().getDrawable(R.drawable.correct_answer_btn_bg, null));

                //Correct Answer
                questionFeedbackTextView.setText("Correct Answer");
                questionFeedbackTextView.setTextColor(getResources().getColor(R.color.colorPrimary, null));
            } else {
                wrongAnswers++;
                clickedAnswerBtn.setBackground(getResources().getDrawable(R.drawable.wrong_answer_btn_bg, null));
                questionFeedbackTextView.setText("Wrong Answer \n Correct Answer : " + questionsToAnswerList.get(currentQuestion - 1).getAnswer());
                questionFeedbackTextView.setTextColor(getResources().getColor(R.color.colorAccent, null));
            }
            canAnswer = false;
            countDownTimer.cancel();
            showNextBtn();
        }
    }

    private void showNextBtn() {
        if (currentQuestion == questionToAnswerCount) {
            nextBtn.setText("Submit Results");
        }
        nextBtn.setVisibility(View.VISIBLE);
        questionFeedbackTextView.setVisibility(View.VISIBLE);
        nextBtn.setEnabled(true);
    }

    private void enableOptions() {
        optionOneBtn.setVisibility(View.VISIBLE);
        optionTwoBtn.setVisibility(View.VISIBLE);
        optionThreeBtn.setVisibility(View.VISIBLE);

        //Enable Options Button
        optionOneBtn.setEnabled(true);
        optionTwoBtn.setEnabled(true);
        optionThreeBtn.setEnabled(true);

        //Hide Feedback and next Button
        questionFeedbackTextView.setVisibility(View.INVISIBLE);
        nextBtn.setVisibility(View.INVISIBLE);
        nextBtn.setEnabled(false);
    }

    private void resetOptions() {
        optionOneBtn.setBackground(getResources().getDrawable(R.drawable.outline_light_btn_bg, null));
        optionTwoBtn.setBackground(getResources().getDrawable(R.drawable.outline_light_btn_bg, null));
        optionThreeBtn.setBackground(getResources().getDrawable(R.drawable.outline_light_btn_bg, null));

        optionOneBtn.setTextColor(getResources().getColor(R.color.colorLightText, null));
        optionTwoBtn.setTextColor(getResources().getColor(R.color.colorLightText, null));
        optionThreeBtn.setTextColor(getResources().getColor(R.color.colorLightText, null));

        questionFeedbackTextView.setVisibility(View.INVISIBLE);
        nextBtn.setVisibility(View.INVISIBLE);
        nextBtn.setEnabled(false);
    }
}
