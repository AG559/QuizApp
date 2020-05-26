package com.ag.quizapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ag.quizapp.Models.QuizListModel;
import com.ag.quizapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {
    private List<QuizListModel> quizListModels;
    private OnQuizListItemClicked onQuizListItemClicked;

    public ListViewAdapter(OnQuizListItemClicked onQuizListItemClicked) {
        this.onQuizListItemClicked = onQuizListItemClicked;
    }

    public void setQuizListModels(List<QuizListModel> quizListModels) {
        this.quizListModels = quizListModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.listTitle.setText(quizListModels.get(position).getName());
        String listDescription = quizListModels.get(position).getDesc();
        if (listDescription.length() > 150) {
            listDescription = listDescription.substring(0, 150) + "...";
        }
        holder.listDesc.setText(listDescription);
        holder.listLevel.setText(quizListModels.get(position).getLevel());
        String imageUrl = quizListModels.get(position).getImage();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .into(holder.listImage);

    }

    @Override
    public int getItemCount() {
        if (quizListModels == null) {
            return 0;
        } else {
            return quizListModels.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView listImage;
        private TextView listTitle, listDesc, listLevel;
        private Button listBtn;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            listImage = itemView.findViewById(R.id.list_image);
            listTitle = itemView.findViewById(R.id.list_title);
            listDesc = itemView.findViewById(R.id.list_desc);
            listLevel = itemView.findViewById(R.id.list_difficulty);
            listBtn = itemView.findViewById(R.id.list_btn);
            listBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onQuizListItemClicked.onItemClicked(getAdapterPosition());
        }
    }

    public interface OnQuizListItemClicked {
        void onItemClicked(int position);
    }


}
