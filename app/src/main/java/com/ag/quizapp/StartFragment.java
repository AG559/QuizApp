package com.ag.quizapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class StartFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private TextView startFeedback;
    private ProgressBar startProgress;
    private NavController navController;

    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        startFeedback = view.findViewById(R.id.start_feedback);
        startProgress = view.findViewById(R.id.start_progress_bar);
        navController = Navigation.findNavController(view);
        startFeedback.setText("Checking user Account...");
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            startFeedback.setText("Creating an Account...");
            firebaseAuth.signInAnonymously().addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startFeedback.setText("Account Created...");
                        navController.navigate(R.id.action_startFragment_to_listFragment);
                    } else {
                        Log.d("Quizz", "Anonymous login Error " + task.getException());
                    }
                }
            });
        } else {
            startFeedback.setText("Logged in...");
            navController.navigate(R.id.action_startFragment_to_listFragment);
        }
    }
}
