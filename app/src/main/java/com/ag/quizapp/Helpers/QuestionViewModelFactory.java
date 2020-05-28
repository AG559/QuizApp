package com.ag.quizapp.Helpers;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ag.quizapp.ViewModels.QuestionViewModel;

public class QuestionViewModelFactory implements ViewModelProvider.Factory {
    private String quizId;

    public QuestionViewModelFactory(String quizId) {
        this.quizId = quizId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new QuestionViewModel(quizId);
    }
}
