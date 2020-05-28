package com.ag.quizapp.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ag.quizapp.Databases.FirebaseQuestionRepository;
import com.ag.quizapp.Models.QuestionModel;

import java.util.ArrayList;
import java.util.List;

public class QuestionViewModel extends ViewModel implements FirebaseQuestionRepository.MyQuestionTaskComplete {

    private MutableLiveData<List<QuestionModel>> questionLiveModel = new MutableLiveData<>();

    public QuestionViewModel(String quizId) {
        FirebaseQuestionRepository firebaseQuestionRepository = new FirebaseQuestionRepository(this);
        firebaseQuestionRepository.getQuestionData(quizId);
    }

    public MutableLiveData<List<QuestionModel>> getQuestionLiveModel() {
        return questionLiveModel;
    }


    @Override
    public void quizListDataAdded(List<QuestionModel> questionModelList) {
        questionLiveModel.setValue(questionModelList);
    }

    @Override
    public void onError(Exception e) {
        ArrayList<QuestionModel> list = new ArrayList<>();
        list.add(new QuestionModel(e));
        questionLiveModel.setValue(list);
    }
}
