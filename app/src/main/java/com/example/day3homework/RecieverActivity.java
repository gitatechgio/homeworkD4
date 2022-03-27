package com.example.day3homework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.net.Uri;
//ესაა მეორე მიმღები კოდი რომელიც იღებს ინფორმაციას მთავარი აქტივობიდან და გამოაქვს ფანჯარაზე.
public class RecieverActivity extends AppCompatActivity {
    TextView txt1, txt2, txt3, txt4, txt5;//ტექსტები;
    Button con;//დადასტურების ღილაკი;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciever);
        txt1 = findViewById(R.id.textRecive1);
        txt2 = findViewById(R.id.textRecive2);
        txt3 = findViewById(R.id.textRecive3);
        txt4 = findViewById(R.id.textRecive4);
        txt5 = findViewById(R.id.textRecive5);
        con=findViewById(R.id.conbutton);

        //ეს კოდი უზრუნველჰყოფს ინფორმაციის გამოსახვას მთავარი აქტივობიდან;
        Intent reciveInt = getIntent();
        String recieveValue1 = reciveInt.getStringExtra("KEY_Sender1");
        String recieveValue2 = reciveInt.getStringExtra("KEY_Sender2");
        String recieveValue3 = reciveInt.getStringExtra("KEY_Sender3");
        String recieveValue4 = reciveInt.getStringExtra("KEY_Sender4");
        String recieveValue5 = reciveInt.getStringExtra("KEY_Sender5");
        txt1.setText(recieveValue1);
        txt2.setText(recieveValue2);
        txt3.setText(recieveValue3);
        txt4.setText(recieveValue4);
        txt5.setText(recieveValue5);

        con.setOnClickListener(new View.OnClickListener() {
            @Override
            //ესაა კოდის ღილაკი საიდანაც გამოისახება ტექსტი ინფორმაციის გაგზვნის შესახებ;
            public void onClick(View view) {
                String s1 = txt1.getText().toString();
                String s2 = txt2.getText().toString();
                String s3 = txt3.getText().toString();
                String s4 = txt4.getText().toString();
                String s5 = txt5.getText().toString();

                Toast.makeText(RecieverActivity.this, s1+" /"+s2+" /" +s5+" /" + " /"+s3+" /"+s4, Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    //კუთხეში არსებული მენიუს გამომსახველი კოდი;
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    //ეს კოდი უზრუნველჰყოფს მენიუში არსებული ღილაკების მუშაობას;
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
        if (item_id == R.id.help){
            String url = "https://giphy.com/gifs/rock-coding-programming-MdA16VIoXKKxNE8Stk?fbclid=IwAR1sSJz_5AGFh3LYd-dLjHlqn4yObiJvfZ990pzwE-WR9ILSuf7OwHG1uc0";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }else if (item_id == R.id.Exit){
            //ეს კოდი დაკავშშირებულია მესამე ფანჯარასთან საიდანაც გამოდის შეტყობინება გვერდის დატოვების შესახებ;
            AlertDialog.Builder exit = new AlertDialog.Builder(this);
            exit.setTitle("Exit")
                    .setMessage("Do you want to leave this page?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .show();
        }
        return true;
    }
}