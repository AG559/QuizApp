package com.ag.quizapp.Databases;

import androidx.annotation.NonNull;

import com.ag.quizapp.Models.QuestionModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirebaseQuestionRepository {
    private MyQuestionTaskComplete myQuestionTaskComplete;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference quizRef = firebaseFirestore.collection("quizlists");

    public FirebaseQuestionRepository(MyQuestionTaskComplete myQuestionTaskComplete) {
        this.myQuestionTaskComplete = myQuestionTaskComplete;

    }


    public void getQuestionData(String quizId) {
        quizRef.document(quizId).collection("Questions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    myQuestionTaskComplete.quizListDataAdded(task.getResult().toObjects(QuestionModel.class));
                } else {
                    myQuestionTaskComplete.onError(task.getException());
                }
            }
        });
    }

    public interface MyQuestionTaskComplete {
        void quizListDataAdded(List<QuestionModel> questionModelList);

        void onError(Exception e);
    }
}
