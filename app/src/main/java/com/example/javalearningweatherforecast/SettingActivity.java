package com.example.javalearningweatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    // создание полей
    private Button buttonSetting; // поле кнопки обработки данных
    private EditText editCity; // поле выбора города
    private EditText editKey; // поле выбора api key
    private SharedPreferences settings; // поле настроек приложения
    private SharedPreferences.Editor editor; // поле для добавления новых данных в настройки
    private Intent intent; // поле намерения переключения активностей
    private final String APP_WEATHER = "Weather"; // константа названия настроек
    private final String CITY = "City"; // константа названия переменной города
    private final String API_KEY = "API Key"; // константа названия API Key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // присваивание id полям
        buttonSetting = findViewById(R.id.buttonSetting);
        editCity = findViewById(R.id.editCity);
        editKey = findViewById(R.id.editKey);

        // создание объекта работы с настройками приложения
        settings = getSharedPreferences(APP_WEATHER, MODE_PRIVATE);

        // считывание настроек выбранного города, данной переменной назначается NoCity если данной настройки нет
        String choiceCity = settings.getString(CITY, "NoCity");
        String choiceKey = settings.getString(API_KEY, "NoKey");

        // вывод на экран информации
        editCity.setText(choiceCity);
        editKey.setText(choiceKey);

        // обработка нажатия кнопки
        buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Считывание вводимого значения города
                String nameCity = editCity.getText().toString();
                String apiKey = editKey.getText().toString();

                Boolean isSave = false;

                // если пользователь ничего не ввёл, то остаёмся в этой же активности
                if (nameCity.equals("")) {
                    // сообщение пользователю об отсутствии введённого города
                    Toast toast = Toast.makeText(getApplicationContext(), "Вы не ввели название населённого пункта", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (apiKey.equals("")) {
                    // сообщение пользователю об отсутствии введённого города
                    Toast toast = Toast.makeText(getApplicationContext(), "Вы не ввели API Key", Toast.LENGTH_SHORT);
                    toast.show();
                } else { // иначе сохраняем (пересохраняем) эти данные и переключаемся в активность прогноза погоды
                    isSave = true;
                }

                if (isSave){
                    // запись новой настройки
                    editor = settings.edit(); // создание объекта для доступа к изменению настроек
                    editor.putString(CITY, nameCity); // запись настроек
                    editor.putString(API_KEY, apiKey); // запись настроек
                    editor.apply(); // сохранение настроек
                    // переключение на активность просмотра погоды
                    intent = new Intent(getApplicationContext(), SecondActivity.class);
                    startActivity(intent);
                }

            }
        });

    }
}