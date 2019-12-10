package com.example.triviaapp.ScoreTable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.triviaapp.R;
import com.example.triviaapp.ScoreTable.Model.Score;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {

    private List<Score> scores;

    public ScoreAdapter(List<Score> allScores) {
        this.scores = allScores;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tVScore, tvName, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tVScore = itemView.findViewById(R.id.tvScoreAdapter);
            tvDate = itemView.findViewById(R.id.dateTV);
        }
    }

    @NonNull
    @Override
    public ScoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreAdapter.ViewHolder holder, int position) {
        Score score = scores.get(position);

        holder.tvName.setText(score.getName());
        holder.tvDate.setText(score.getDate());
        holder.tVScore.setText(score.getScore());
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    public void addScore(Score score) {
        scores.add(score);
        notifyItemInserted(0);
    }
}
