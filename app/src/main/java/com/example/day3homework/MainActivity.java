package com.example.day3homework;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
{ // აქაა მოცემული საწყისი აქტივობის ძირითადი კოდი;
    private DatePickerDialog datePickerDialog1, datePickerDialog2; //კალენდარი
    EditText perInfo1, perInfo2; //სახელი, მობილური.
    private Button dateButton1, timeButton, savebt; //კალენდარი და ინფორმაციის გამშვები ღილაკიsavebt;
    int tHour, tMinute;
    MediaPlayer mp;
    CheckBox box; //მონიშვნის გრაფა
    RadioButton rab1, rab2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatePicker();
        box = findViewById(R.id.checkBox);
        dateButton1 = findViewById(R.id.datePickerButton1);
        timeButton = findViewById(R.id.timeButton2);
        dateButton1.setText(getTodaysDate());
        perInfo1 = findViewById(R.id.PersonName1);
        perInfo2 = findViewById(R.id.PersonName2);
        rab1 = findViewById(R.id.radioButton1);
        rab2 = findViewById(R.id.radioButton2);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        mp=MediaPlayer.create(this,R.raw.error);
        savebt = findViewById(R.id.button);
        savebt.setOnClickListener(new View.OnClickListener() {
            @Override
            //ეს კოდი უზრუნველყოფს მონიშვნის გრაფის მუშაობას.
            public void onClick(View view) {
                if (box.isChecked()) {//თუ გრაფა მონიშნულია გადადის შემდეგ ფანჯარაში;
                    sendActivity(); //შემდეგი ფანჯარის გამხსნელი აქტივობა;

                }else {  //თუ არა გაძლევს შეტყობინებას რომ მონიშნო გრაფა;
                    mp.start();
                    builder.setTitle("Alert!")
                            .setMessage("Please check the Agree box")
                            .setCancelable(true)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                    .show();
                }
            }
        });
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //საათის გამშვები კოდი
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                //24 საათიანი კოდი;
                                tHour = hourOfDay;
                                tMinute = minute;
                                String time = tHour + ":" +tMinute;
                                SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                //12 საათიანი კოდი;
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    timeButton.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },12,0,false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//გამჭვირვალე ფონი
                timePickerDialog.updateTime(tHour,tMinute);//დროის განახლება
                timePickerDialog.show();//კოდის ჩვენება
            }
        });
    }
    public void sendActivity(){ //ინფორმაციის გამშვები აქტივობის კოდი;
        Intent sendIntent = new Intent(this, RecieverActivity.class);//შემდეგი აქტივობის დამაკავშირებელი ბრძანება;
        sendIntent.putExtra("KEY_Sender1", dateButton1.getText().toString());//პირველი კალენდარი;
        sendIntent.putExtra("KEY_Sender2", timeButton.getText().toString());//მეორე კალენდარი;
        sendIntent.putExtra("KEY_Sender3", perInfo1.getText().toString());//სახელი;
        sendIntent.putExtra("KEY_Sender4", perInfo2.getText().toString());//ნომერი;
        sendIntent.putExtra("KEY_Sender5", rab1.getText().toString());
        sendIntent.putExtra("KEY_Sender5", rab2.getText().toString());
        startActivity(sendIntent);
    }
    private String getTodaysDate() //კალენდარის კოდი, ეს კოდი უზრუნველყობს შევსების მომენტში არსებული თარიღის ჩვენებას;
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton1.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog1 = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) //თვის აღმნიშვნელი კოდი;
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog1.show();
    } ///პირველი კალენდრის გამშვები კოდი

}