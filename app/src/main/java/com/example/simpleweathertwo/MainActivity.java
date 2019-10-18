package com.example.simpleweathertwo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

        Button btnActNew;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            btnActNew = (Button) findViewById(R.id.btnActNew);
            btnActNew.setOnClickListener(this);

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new WeatherFragment())
                        .commit();
            }

            ImageView oblako = findViewById(R.id.oblako);
            ImageView imageView = (ImageView) findViewById(R.id.imageView);

            imageView.setBackgroundResource(R.drawable.sunpic);
            // Анимация для восхода солнца
            Animation sunRiseAnimation = AnimationUtils.loadAnimation(this, R.anim.sun_rise);
            // Подключаем анимацию к нужному View
            imageView.startAnimation(sunRiseAnimation);

            //вращение солнца
            ObjectAnimator animation = ObjectAnimator.ofFloat(imageView, "rotation", 0.0f, 360f);
            animation.setDuration(15000);
            animation.setRepeatCount(ObjectAnimator.INFINITE);
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
            animation.start();

            //появление облака
            TranslateAnimation anim = new TranslateAnimation(-400, 1200, 200, 200);
            anim.setDuration(10000);
            anim.setRepeatCount(ObjectAnimator.INFINITE);
            oblako.setAlpha (0.8f);
            oblako.startAnimation(anim);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnActNew:
                    Intent intent = new Intent((MainActivity.this), ActivityTwo.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }

        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                if(item.getItemId() == R.id.change_city){
                    showInputDialog();
                }
                return false;
            }

            private void showInputDialog(){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Change city");
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeCity(input.getText().toString());
                    }
                });
                builder.show();
            }

            public void changeCity(String city){
                WeatherFragment wf = (WeatherFragment)getSupportFragmentManager()
                        .findFragmentById(R.id.container);
                wf.changeCity(city);
                new CityPreference(this).setCity(city);
            }
    }
