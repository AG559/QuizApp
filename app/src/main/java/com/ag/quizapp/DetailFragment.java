package com.ag.quizapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ag.quizapp.Models.QuizListModel;
import com.ag.quizapp.ViewModels.QuizListViewModel;
import com.bumptech.glide.Glide;

import java.util.List;


public class DetailFragment extends Fragment implements View.OnClickListener {
    private NavController navController;
    private QuizListViewModel quizListViewModel;
    private ImageView detailImage;
    private TextView detail_title, detail_desc, detail_difficulty, detail_total_quest, detail_your_score;
    private int position;
    private Button startQuizBtn;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        position = getArguments().getInt("position");
        navController = Navigation.findNavController(view);

        //Initialization Ui Element
        detailImage = view.findViewById(R.id.detail_image);
        detail_title = view.findViewById(R.id.detail_title);
        detail_desc = view.findViewById(R.id.detail_desc);
        detail_difficulty = view.findViewById(R.id.detail_difficulty_text);
        detail_total_quest = view.findViewById(R.id.detail_total_quest_text);
        detail_your_score = view.findViewById(R.id.detail_your_score_text);
        startQuizBtn = view.findViewById(R.id.detail_start_btn);
        startQuizBtn.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        quizListViewModel = new ViewModelProvider(requireActivity()).get(QuizListViewModel.class);
        quizListViewModel.getQuizListModelData().observe(getViewLifecycleOwner(), new Observer<List<QuizListModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(List<QuizListModel> quizListModelList) {
                Glide.with(requireContext())
                        .load(quizListModelList.get(position).getImage())
                        .centerCrop()
                        .placeholder(R.drawable.placeholder_image)
                        .into(detailImage);
                detail_title.setText(quizListModelList.get(position).getName());
                detail_desc.setText(quizListModelList.get(position).getDesc());
                detail_difficulty.setText(quizListModelList.get(position).getLevel());
                detail_total_quest.setText(quizListModelList.get(position).getQuestions() + "");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_start_btn:
                DetailFragmentDirections.ActionDetailFragmentToQuizFragment action = DetailFragmentDirections.actionDetailFragmentToQuizFragment();
                action.setPosition(position);
                navController.navigate(action);
        }
    }
}
