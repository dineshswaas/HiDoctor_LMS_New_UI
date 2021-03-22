package com.swaas.kangle.LPCourse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.swaas.kangle.R;

import java.util.ArrayList;
import java.util.Random;

public class HangmanGame extends AppCompatActivity {

    // Java Keywords
    public static final String[] WORDS = {
            "ABSTRACT", "ASSERT", "BOOLEAN", "BREAK", "BYTE",
            "CASE", "CATCH", "CHAR", "CLASS", "CONST",
            "CONTINUE", "DEFAULT", "DOUBLE", "DO", "ELSE",
            "ENUM", "EXTENDS", "FALSE", "FINAL", "FINALLY",
            "FLOAT", "FOR", "GOTO", "IF", "IMPLEMENTS",
            "IMPORT", "INSTANCEOF", "INT", "INTERFACE",
            "LONG", "NATIVE", "NEW", "NULL", "PACKAGE",
            "PRIVATE", "PROTECTED", "PUBLIC", "RETURN",
            "SHORT", "STATIC", "STRICTFP", "SUPER", "SWITCH",
            "SYNCHRONIZED", "THIS", "THROW", "THROWS",
            "TRANSIENT", "TRUE", "TRY", "VOID", "VOLATILE", "WHILE"
    };

    public static final Random RANDOM = new Random();

    // Max errors before user lose
    public static final int MAX_ERRORS = 7;
    // Word to find
    private String wordToFind;
    // Word found stored in a char array to show progression of user
    private char[] wordFound;
    private int nbErrors;
    // letters already entered by user
    private ArrayList < String > letters = new ArrayList < > ();
    private ImageView img;
    private TextView wordTv;
    private TextView wordToFindTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hangmangame);
        img = findViewById(R.id.img);
        wordTv = findViewById(R.id.wordTv);
        wordToFindTv = findViewById(R.id.wordToFindTv);
        newGame();
    }



    // Method returning randomly next word to find
    private String nextWordToFind() {
        return WORDS[RANDOM.nextInt(WORDS.length)];
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    // Method for starting a new game
    public void newGame() {
        nbErrors = 1;
        letters.clear();
        wordToFind = nextWordToFind();

        // word found initialization
        wordFound = new char[wordToFind.length()];

        for (int i = 0; i < wordFound.length; i++) {
            wordFound[i] = '_';
        }

        updateImg(nbErrors);
        wordTv.setText(wordFoundContent());
        wordToFindTv.setText("You Won");
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
                Toast.makeText(this, "Try another", Toast.LENGTH_SHORT).show();
            }

            // c is now a letter entered
            letters.add(c);
        } else {
            Toast.makeText(this, "Letter already Entered", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "You Won", Toast.LENGTH_SHORT).
                        show();
                wordToFindTv.setText("You Won");
            } else {
                if (nbErrors >= MAX_ERRORS) {
                    Toast.makeText(this, "You Lost", Toast.LENGTH_SHORT).show();
                    wordToFindTv.setText("Word to find was: " + wordToFind);
                }
            }
        } else {
            Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
        }
    }
}
