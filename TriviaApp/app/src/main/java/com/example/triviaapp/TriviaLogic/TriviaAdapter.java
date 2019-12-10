package com.example.triviaapp.TriviaLogic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.triviaapp.R;
import com.example.triviaapp.TriviaLogic.Model.TriviaData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class TriviaAdapter extends RecyclerView.Adapter<TriviaAdapter.TriviaViewHolder> {

    private static boolean[] checkedAnsw;
    private Random random;
    private List<String> answers;
    int [] selectedRadioButton;

    public ArrayList<TriviaData> triviaData;
    Context mContext;

    public TriviaAdapter(ArrayList<TriviaData> triviaData, Context context) {
        this.triviaData = triviaData;
        this.mContext = context;
        checkedAnsw = new boolean[triviaData.size()];
        selectedRadioButton = new int[triviaData.size()];
    }

    @NonNull
    @Override
    public TriviaAdapter.TriviaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View mView = inflater.inflate(R.layout.question_item, parent, false);
        TriviaViewHolder viewHolder = new TriviaViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TriviaViewHolder holder, final int position) {
        final int position_holder = holder.getAdapterPosition();
        final TriviaData trivia = triviaData.get(holder.getAdapterPosition());

        random = new Random();
        holder.TVQuestions.setText(trivia.getQuestion());

        answers = trivia.getAnswers_ALL();

        holder.radioButton4.setText(answers.get(0));
        holder.radioButton1.setText(answers.get(1));
        holder.radioButton2.setText(answers.get(2));
        holder.radioButton3.setText(answers.get(3));


        holder.radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRadioButton[position_holder]=holder.radioButton1.getId();
                RadioButton r = (RadioButton) v;
                if (r.getText().toString().equals(trivia.getAnswer())) {
                    checkedAnsw[position_holder] = true;
                } else {
                    checkedAnsw[position_holder] = false;
                }
            }
        });
        holder.radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRadioButton[position_holder]=holder.radioButton1.getId();
                RadioButton r = (RadioButton) v;
                if (r.getText().toString().equals(trivia.getAnswer())) {
                    checkedAnsw[position_holder] = true;
                } else {
                    checkedAnsw[position_holder] = false;
                }
            }
        });
        holder.radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRadioButton[position_holder]=holder.radioButton1.getId();
                RadioButton r = (RadioButton) v;
                if (r.getText().toString().equals(trivia.getAnswer())) {
                    checkedAnsw[position_holder] = true;
                } else {
                    checkedAnsw[position_holder] = false;
                }
            }
        });
        holder.radioButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRadioButton[position_holder]=holder.radioButton1.getId();
                RadioButton r = (RadioButton) v;
                if (r.getText().toString().equals(trivia.getAnswer())) {
                    checkedAnsw[position_holder] = true;
                } else {
                    checkedAnsw[position_holder] = false;
                }
            }
        });

        holder.radioGroup.check(selectedRadioButton[position_holder]);
    }

    public boolean[] getUserInput() {
        return checkedAnsw;
    }

    @Override
    public int getItemCount() {
        return triviaData.size();
    }


    public class TriviaViewHolder extends RecyclerView.ViewHolder {

        TextView TVQuestions;
        RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
        RadioGroup radioGroup;


        public TriviaViewHolder(@NonNull View itemView) {
            super(itemView);

            radioGroup = itemView.findViewById(R.id.RadioGroup);

            radioButton1 = itemView.findViewById(R.id.radio_1);
            radioButton2 = itemView.findViewById(R.id.radio_2);
            radioButton3 = itemView.findViewById(R.id.radio_3);
            radioButton4 = itemView.findViewById(R.id.radio_4);

            TVQuestions = itemView.findViewById(R.id.QuestionTV);
        }
    }
}
