package com.tom.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GamePlayActivity extends AppCompatActivity implements QuestionsRequest.Callback {
    private int question_number;
    private int max_questions = 10;
    private Question currentQuestion;
    private Bundle questions;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        Intent intent = getIntent();

        // keep track of what question we currently are, at first question this is set to 0
        this.question_number = intent.getIntExtra("question_number", 0);
        this.score = intent.getIntExtra("score", 0);

        // answered all questions, direct to MenuActivity
        if (question_number == max_questions) {
            Intent intent1 = new Intent(this, GameFinishedActivity.class);
            intent1.putExtra("score", score);
            startActivity(intent1);
        }

        // first time on this screen (first turn), get questions from QuestionRequest
        else if (question_number == 0) {
            int category_id = intent.getIntExtra("category_id", 0);
            String difficulty = intent.getStringExtra("difficulty");
            String type = intent.getStringExtra("type");
            QuestionsRequest qr = new QuestionsRequest(this);
            qr.getQuestions(this, max_questions, category_id, difficulty, type);
        }

        // play one turn, answer the question
        else {
            Bundle b = intent.getBundleExtra("bundle");
            this.questions = b;
            Question question = (Question) b.getSerializable("question"+Integer.toString(question_number));
            makeQuestion(question);
        }
    }

    public void onAnswerClick(View view) {
        Button pressed = (Button) view;
        String selectedAnswer = pressed.getText().toString();
        Intent intent1 = new Intent(this, GamePlayActivity.class);
        intent1.putExtra("question_number", question_number+1);
        intent1.putExtra("bundle", questions);

        // if user selected correct answer, add to score
        if (selectedAnswer.contentEquals(currentQuestion.getCorrectAnswer())) {
            this.score += computePointsForQuestion(currentQuestion.getDifficulty(), currentQuestion.getType());
//            Log.d("DAT IS GOED!!!! ----->", Integer.toString(score));
        }
        intent1.putExtra("score", score);
        startActivity(intent1);
    }

    @Override
    // you are not allowed to go back to previous question
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void gotQuestions(ArrayList<Question> questions) {
        // save all questions in bundle
        Bundle b = new Bundle();
        for(int i = question_number; i < questions.size(); i++) {
            b.putSerializable("question"+Integer.toString(i), questions.get(i));
        }
        this.questions = b;

        // show current question
        Question currentQuestion = questions.get(question_number);
        makeQuestion(currentQuestion);
    }

    @Override
    public void gotQuestionError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    // this function sets all Views to display content of passed question
    private void makeQuestion(Question question) {
        currentQuestion = question;
        // set question
        TextView questionTitle = findViewById(R.id.questionView);
        questionTitle.setText(question.getQuestion());

        TextView difficulty = findViewById(R.id.difficultyView);
        difficulty.setText("Difficulty : "+question.getDifficulty());
        TextView cat_view = findViewById(R.id.categoryView);
        cat_view.setText("Category: "+ question.getCategory());
        setTitle("Trivia - Question "+ Integer.toString(question_number+1) + ":");

        TextView scoreView = findViewById(R.id.scoreView);
        scoreView.setText("Score = "+Integer.toString(score));


        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);

        // if question is multiple choise
        if (question.getType().contentEquals("multiple")) {
            button3.setVisibility(View.VISIBLE);
            button4.setVisibility(View.VISIBLE);
            // randomize answers and show them in the buttons
            ArrayList<String> answers = question.getAnswers();
            Collections.shuffle(answers);
            button1.setText(answers.get(0));
            button2.setText(answers.get(1));
            button3.setText(answers.get(2));
            button4.setText(answers.get(3));
        }

        // true or false
        else {
            button3.setVisibility(View.INVISIBLE);
            button4.setVisibility(View.INVISIBLE);
            button1.setText("True");
            button2.setText("False");
        }

    }

    private int computePointsForQuestion(String difficulty, String type) {
        Map<String, Integer> map_diffuculty = new HashMap<String, Integer>();
        map_diffuculty.put("easy", 3);
        map_diffuculty.put("medium", 4);
        map_diffuculty.put("hard", 5);

        Map<String, Integer> map_type = new HashMap<String, Integer>();
        map_type.put("boolean", 2);
        map_type.put("multiple", 3);
        return (map_diffuculty.get(difficulty) * map_type.get(type));

    }
}
