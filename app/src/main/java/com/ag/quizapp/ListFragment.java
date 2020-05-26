package com.ag.quizapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ag.quizapp.Adapters.ListViewAdapter;
import com.ag.quizapp.Models.QuizListModel;
import com.ag.quizapp.ViewModels.QuizListViewModel;

import java.util.List;


public class ListFragment extends Fragment implements ListViewAdapter.OnQuizListItemClicked {
    private QuizListViewModel quizListViewModel;
    private RecyclerView listView;
    private ListViewAdapter adapter;
    private NavController navController;
    private ProgressBar listProgressBar;
    private Animation fadeIn, fadeOut;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.list_recycler);
        adapter = new ListViewAdapter(this);
        navController = Navigation.findNavController(view);
        listProgressBar = view.findViewById(R.id.list_progressbar);
        fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        listView.setLayoutManager(new LinearLayoutManager(requireContext()));
        listView.setHasFixedSize(true);
        listView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        quizListViewModel = new ViewModelProvider(requireActivity()).get(QuizListViewModel.class);
        quizListViewModel.getQuizListModelData().observe(getViewLifecycleOwner(), new Observer<List<QuizListModel>>() {
            @Override
            public void onChanged(List<QuizListModel> quizListModelList) {
                listView.startAnimation(fadeIn);
                listProgressBar.startAnimation(fadeOut);
                adapter.setQuizListModels(quizListModelList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClicked(int position) {
        ListFragmentDirections.ActionListFragmentToDetailFragment action = ListFragmentDirections.actionListFragmentToDetailFragment();
        action.setPosition(position);
        navController.navigate(action);
    }
}
