package com.ag.quizapp.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ag.quizapp.Helpers.FirebaseRepository;
import com.ag.quizapp.Models.QuizListModel;

import java.util.List;

public class QuizListViewModel extends ViewModel implements FirebaseRepository.MyFireStoreTaskComplete {

    private MutableLiveData<List<QuizListModel>> quizListModelData = new MutableLiveData<>();
    private FirebaseRepository firebaseRepository = new FirebaseRepository(this);

    public MutableLiveData<List<QuizListModel>> getQuizListModelData() {
        return quizListModelData;
    }

    public QuizListViewModel() {
        firebaseRepository.getQuizData();
    }

    @Override
    public void quizListDataAdded(List<QuizListModel> quizListModelList) {
        quizListModelData.setValue(quizListModelList);
    }

    @Override
    public void onError(Exception e) {

    }
}
