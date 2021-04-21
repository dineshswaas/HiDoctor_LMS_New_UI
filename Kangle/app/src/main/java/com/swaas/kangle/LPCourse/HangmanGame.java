package com.swaas.kangle.LPCourse;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.koushikdutta.ion.Ion;
import com.swaas.kangle.LPCourse.model.HangmanResponse;
import com.swaas.kangle.LPCourse.model.Lstattempt;
import com.swaas.kangle.LPCourse.model.Lsthint;
import com.swaas.kangle.LPCourse.model.Lstresponse;
import com.swaas.kangle.LPCourse.model.Lstword;
import com.swaas.kangle.R;
import com.swaas.kangle.db.RetrofitAPIBuilder;
import com.swaas.kangle.playerPart.DigitalAssets;
import com.swaas.kangle.preferences.PreferenceUtils;
import com.swaas.kangle.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static com.swaas.kangle.LPCourse.CateoryGameAdapter.gameCategoryWords;

public class HangmanGame extends AppCompatActivity {



    // Max errors before user lose
    public static final int MAX_ERRORS = 7;
    // Word to find
    private String wordToFind;
    // Word found stored in a char array to show progression of user
    private char[] wordFound;
    private int nbErrors;
    int next = gameCategoryWords.getLstwords().size();
    int i = -1;
    // letters already entered by user
    private ArrayList < String > letters = new ArrayList < > ();
    private ImageView img,idea,question;
    private TextView wordTv;
    int life = 6;
    int hintsize = 0;
    int hint = 0 ;
    private TextView wordToFindTv,lifetext;
    private Lstword lstwords;
    ArrayList<Lsthint> lsthint = new ArrayList<>();
    public HangmanResponse hangmanResponse = new HangmanResponse();
    public Lstattempt lstattempt = new Lstattempt();
    public List<Lstattempt> lstattemptList = new ArrayList();
    public List<Lstresponse> lstresponselist = new ArrayList();

    public Lstresponse lstresponse = new Lstresponse();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hangmangame);
        img = findViewById(R.id.img);
        wordTv = findViewById(R.id.wordTv);
        wordToFindTv = findViewById(R.id.wordToFindTv);
        lifetext = findViewById(R.id.lifetext);
        idea = findViewById(R.id.ideahangman);
        question = findViewById(R.id.questionhangman);
        lstattempt =  new Lstattempt();
        newGame();
        lsthint.clear();
        for (int j = 0 ; j < gameCategoryWords.getLsthints().size(); j++)
        {
            if(gameCategoryWords.getLsthints().get(j).getQuestionId().equals(gameCategoryWords.getLstwords().get(i).getQuestionId())) {
                lsthint.add(gameCategoryWords.getLsthints().get(j));
            }
        }
        hintsize = lsthint.size();
        idea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    hint = 0;
                    if(lsthint.size() > 1 && hintsize > 0) {
                        showgamepopup("HINT", lsthint.get(0).getHintDescription()
                                , "", "Next Hint");
                    }
                    else if(lsthint.size() == 1 ) {
                        showgamepopup("HINT", lsthint.get(0).getHintDescription()
                                , "", "OK");
                    }
                    else
                    {
                        showgamepopup("HINT","NO HINT AVAILABLE","","OK");
                    }

            }
        });
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( gameCategoryWords.getLstwords().get(i).getQuestionImageURL()!= null) {
                    showgamepopup("QUESTION", gameCategoryWords.getLstwords().get(i).getQuestionDescription()
                            ,gameCategoryWords.getLstwords().get(i).getQuestionImageURL().toString(), "OK");
                }
                else
                {
                    showgamepopup("QUESTION", gameCategoryWords.getLstwords().get(i).getQuestionDescription()
                            , "", "OK");
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd_HHmmss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        lstattempt.setGameEndTime(null);
        lstattemptList.add(lstattempt);
        hangmanResponse.setLstattempts(lstattemptList);
        insertresponse(hangmanResponse);
        this.finish();
    }
    public void insertresponse( HangmanResponse hangmanResponse)  {
        if(NetworkUtils.checkIfNetworkAvailable(getApplicationContext())){
            Retrofit retrofitAPI = RetrofitAPIBuilder.getInstance();
            LPCourseService lpService = retrofitAPI.create(LPCourseService.class);

            Call call = lpService.posthangman(PreferenceUtils.getCompnayId(getApplicationContext()),hangmanResponse);
            call.enqueue(new Callback<String>() {

                @Override
                public void onResponse(Response<String> response, Retrofit retrofit) {

                }
                @Override
                public void onFailure(Throwable t) {
                    Log.d(t.toString(),"Error");
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "no network", Toast.LENGTH_SHORT).show();
        }
    }
    // Method for starting a new game
    public void newGame() {
        nbErrors = 1;

        letters.clear();
        hintsize = 0;
        life = 6;
        lifetext.setText(String.valueOf(life));
        next = next - 1;
        if(next > -1){
            //wordToFind = nextWordToFind();
            i = i + 1;
            lstattempt.setCategoryId(gameCategoryWords.getLstwords().get(0).getCategoryId());
            lstattempt.setAttemptNo(gameCategoryWords.getAttemptNumber() + 1);
            lstattempt.setUserId(PreferenceUtils.getUserId(getApplicationContext()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());
            lstattempt.setGameStartTime(null);

            lstresponse = new Lstresponse();
            lstresponse.setQuestionId(gameCategoryWords.getLstwords().get(i).getQuestionId());
            lstresponse.setUserId(PreferenceUtils.getUserId(getApplicationContext()));
            lsthint.clear();
            for (int j = 0 ; j < gameCategoryWords.getLsthints().size(); j++)
            {
                if(gameCategoryWords.getLsthints().get(j).getQuestionId().equals(gameCategoryWords.getLstwords().get(i).getQuestionId())) {
                    lsthint.add(gameCategoryWords.getLsthints().get(j));
                }
            }
            lstwords = gameCategoryWords.getLstwords().get(i);
            wordToFind = lstwords.getQuestionText().trim();
            wordToFind = wordToFind.replace(" ","");
            // word found initialization
            wordFound = new char[wordToFind.length()];

            for (int i = 0; i < wordFound.length; i++) {
                wordFound[i] = '_';
            }

            updateImg(nbErrors);
            wordTv.setText(wordFoundContent());
            wordToFindTv.setText("You Won");
            wordToFindTv.setVisibility(View.GONE);
        }
        else
        {
            showgamepopup("Category Completed", "Well done !!\nPlay next Category.", "", "Play");
        }
    }

    // Method returning trus if word is found by user
    public boolean wordFound() {
        return wordToFind.contentEquals(new String(wordFound));
    }

    // Method updating the word found after user entered a character
    private void enter(String c) {
        // we update only if c has not already been entered
        if (!letters.contains(c)) {
            // we check if word to find contains c
            if (wordToFind.contains(c)) {
                // if so, we replace _ by the character c
                int index = wordToFind.indexOf(c);

                while (index >= 0) {
                    wordFound[index] = c.charAt(0);
                    index = wordToFind.indexOf(c, index + 1);
                }
            } else {
                // c not in the word => error
                nbErrors++;
                life = life - 1;
                lifetext.setText(String.valueOf(life));
                showgamepopup("Alert","Try another Letter","","OK");
            }

            // c is now a letter entered
            letters.add(c);
        } else {
            showgamepopup("Alert","Letter already Entered","","OK");
        }
    }

    // Method returning the state of the word found by the user until by now
    private String wordFoundContent() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < wordFound.length; i++) {
            builder.append(wordFound[i]);

            if (i < wordFound.length - 1) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }


    private void updateImg(int play) {
        int resImg = getResources().getIdentifier("hangman" + play, "mipmap",
                getPackageName());
        img.setImageResource(resImg);

    }


    public void touchLetter(View v) {
        if (nbErrors < MAX_ERRORS
                && wordToFindTv.getText().equals("You Won")) {
            String letter = ((Button) v).getText().toString();
            enter(letter);
            wordTv.setText(wordFoundContent());
            updateImg(nbErrors);

            // check if word is found
            if (wordFound()) {
               // Toast.makeText(this, "You Won", Toast.LENGTH_SHORT).
                   //     show();
                wordToFindTv.setVisibility(View.VISIBLE);
                wordToFindTv.setText("You Won");
                wordTv.setText("");
                if(next > 0) {
                    showgamepopup("You WON !!", "Well done !!\nPlay next word?", "", "NEXT WORD");
                    lstresponse.setIsCorrect(1);
                    lstresponse.setNoOfLivesTaken(6 - life);
                    lstresponselist.add(lstresponse);
                    hangmanResponse.setLstresponse(lstresponselist);
                }
                else
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                    String currentDateandTime = sdf.format(new Date());
                    lstattempt.setGameEndTime(null);
                    lstresponse.setIsCorrect(1);
                    lstresponse.setNoOfLivesTaken(6 - life);
                    lstresponselist.add(lstresponse);
                    hangmanResponse.setLstresponse(lstresponselist);
                    lstattempt.setGameEndTime(null);
                    lstattemptList.add(lstattempt);
                    hangmanResponse.setLstattempts(lstattemptList);

                        insertresponse(hangmanResponse);

                    showgamepopup("Category Completed", "Well done !!\nPlay next Category.", "", "Play");
                }
            } else {
                if (nbErrors >= MAX_ERRORS) {
                    wordTv.setText("");
                   // Toast.makeText(this, "You Lost", Toast.LENGTH_SHORT).show();
                    wordToFindTv.setText("Word to find was: " + wordToFind);
                    wordToFindTv.setVisibility(View.VISIBLE);
                    if(next > 0) {
                        showgamepopup("You Lost !!", "Play next word?", "", "NEXT WORD");
                        lstresponse.setIsCorrect(0);
                        lstresponse.setNoOfLivesTaken(6 - life);
                        lstresponselist.add(lstresponse);
                        hangmanResponse.setLstresponse(lstresponselist);
                    }
                    else
                    {
                        lstresponse.setIsCorrect(0);
                        lstresponse.setNoOfLivesTaken(6 - life);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                        String currentDateandTime = sdf.format(new Date());
                        lstattempt.setGameEndTime(null);
                        lstresponselist.add(lstresponse);
                        hangmanResponse.setLstresponse(lstresponselist);
                        lstattempt.setGameEndTime(null);
                        lstattemptList.add(lstattempt);
                        hangmanResponse.setLstattempts(lstattemptList);

                            insertresponse(hangmanResponse);

                        showgamepopup("Category Completed", "Well done !!\nPlay next Category.", "", "Play");
                    }
                }
            }

        } else {
           // Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
            showgamepopup("Category Completed", "Well done !!\nPlay next Category.", "", "Play");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());
            lstattempt.setGameEndTime(null);
            lstattemptList.add(lstattempt);
            hangmanResponse.setLstattempts(lstattemptList);
        }
    }
    public void showgamepopup(String title, String description, String url, final String button) {

        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.gamepopup, null);
        builder.setView(customLayout);
        TextView header = customLayout.findViewById(R.id.title);
        TextView desc = customLayout.findViewById(R.id.description);
        ImageView image = customLayout.findViewById(R.id.image);
        final Button btn = customLayout.findViewById(R.id.button);
        header.setText(title);
        desc.setText(description);

        if(title.equals("HINT"))
        {
            image.setVisibility(View.GONE);
        }
        if(url.isEmpty())
        {
            image.setVisibility(View.GONE);
        }
        else
        {
            Ion.with(image).load(url);
        }

        // add a button

        // create and show the alert dialog
        final AlertDialog dialog = builder.create();
        btn.setText(button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if(button.toUpperCase().equals("NEXT HINT"))
                {
                    hint = hint + 1;
                    if(hint < lsthint.size()) {
                        nexthint();
                    }
                }
                if(button.toUpperCase().equals("NEXT WORD"))
                {
                    newGame();
                }
                if(button.toUpperCase().equals("PLAY"))
                {

                    finish();
                }
            }
        });
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
    }

    private void nexthint() {

        if(lsthint.size() > 0) {
            if(hint < hintsize - 1)
            {
                showgamepopup("HINT", lsthint.get(hint).getHintDescription()
                        , "", "Next Hint");

            }
            else {
                showgamepopup("HINT", lsthint.get(hint).getHintDescription()
                        , "", "OK");
            }
        }
    }
}
