package com.example.museumhelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MuseumDescriptionActivity extends AppCompatActivity {

    TextView titleView;
    TextView textView;
    TextToSpeech textToSpeechEN, textToSpeechFR, textToSpeechDE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum_description);
        showFragment(new DescriptionFragment());
        textView = (TextView) findViewById(R.id.museumDescriptionTextView);
        titleView = (TextView) findViewById(R.id.museumTitleTextView);

        //Museum museum = (Museum)getIntent().getSerializableExtra("museumDescription");
        //String description = museum.toString();
        String name = getIntent().getStringExtra("museumName");
        String description = getIntent().getStringExtra("museumDescription");

        titleView.setText(name);
        textView.setText(description);

        textToSpeechEN = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeechEN.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        //textToSpeech.setEnabled(true);
                        Log.e("TTS", "Language ok");
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        textToSpeechFR = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeechFR.setLanguage(Locale.FRENCH);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        //textToSpeech.setEnabled(true);
                        Log.e("TTS", "Language ok");
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        textToSpeechDE = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeechDE.setLanguage(Locale.GERMAN);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        //textToSpeech.setEnabled(true);
                        Log.e("TTS", "Language ok");
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
    }

    public void showFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.descriptionFragment,fragment).commit();
    }

    public void speakEN(View v) {
        String text = titleView.getText().toString() + ". " + textView.getText().toString();
        textToSpeechEN.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void speakFR(View v) {
        String text = titleView.getText().toString() + ". " + textView.getText().toString();
        textToSpeechFR.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void speakDE(View v) {
        String text = titleView.getText().toString() + ". " + textView.getText().toString();
        textToSpeechDE.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
    @Override
    protected void onDestroy() {
        if (textToSpeechEN != null) {
            textToSpeechEN.stop();
            textToSpeechEN.shutdown();
        }
        if (textToSpeechFR != null) {
            textToSpeechFR.stop();
            textToSpeechFR.shutdown();
        }
        if (textToSpeechDE != null) {
            textToSpeechDE.stop();
            textToSpeechDE.shutdown();
        }
        super.onDestroy();
    }
}