package com.ag.quizapp.Helpers;

import androidx.annotation.NonNull;

import com.ag.quizapp.Models.QuizListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirebaseRepository {
    private MyFireStoreTaskComplete myFireStoreTaskComplete;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference quizRef = firebaseFirestore.collection("quizlists");

    public FirebaseRepository(MyFireStoreTaskComplete myFireStoreTaskComplete) {
        this.myFireStoreTaskComplete = myFireStoreTaskComplete;

    }

    public void getQuizData() {
        quizRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    myFireStoreTaskComplete.quizListDataAdded(task.getResult().toObjects(QuizListModel.class));
                } else {
                    myFireStoreTaskComplete.onError(task.getException());
                }
            }
        });
    }

    public interface MyFireStoreTaskComplete {
        void quizListDataAdded(List<QuizListModel> quizListModelList);

        void onError(Exception e);
    }
}
