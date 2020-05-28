package com.ag.quizapp.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ag.quizapp.Databases.FirebaseRepository;
import com.ag.quizapp.Models.QuizListModel;

import java.util.List;

public class QuizListViewModel extends ViewModel implements FirebaseRepository.MyFireStoreTaskComplete {

    private MutableLiveData<List<QuizListModel>> quizListModelData = new MutableLiveData<>();

    public MutableLiveData<List<QuizListModel>> getQuizListModelData() {
        return quizListModelData;
    }

    public QuizListViewModel() {
        FirebaseRepository firebaseRepository = new FirebaseRepository(this);
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
