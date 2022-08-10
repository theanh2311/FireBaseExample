package com.example.demofirebase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button btn;
    EditText edt1, edt2;
    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btnRegister);
        edt1 = findViewById(R.id.editText1);
        edt2 = findViewById(R.id.editText2);
        tvResult = findViewById(R.id.tvResult);
        auth = FirebaseAuth.getInstance();
    }

    public void registerFB(View view) {
        String u = edt1.getText().toString();
        String p = edt2.getText().toString();
        auth.createUserWithEmailAndPassword(u,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    tvResult.setText("Thanh cong");
                }
            }
        });
    }

    public void loginFB(View view) {
        String u = edt1.getText().toString();
        String p = edt2.getText().toString();
        auth.signInWithEmailAndPassword(u,p)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            tvResult.setText("Thanh cong");
                            //startActivity(intent);
                            //finish();
                        }
                    }
                });
    }
    public void insertArray(View view)
    {
        String[] ar = new String[]{"Item","Item","Item"};
        Task<Void> df = FirebaseDatabase.getInstance().getReference()
                .child("ticket").setValue(Arrays.asList(ar));
    }
    public void insertAppend(View view)
    {
        FirebaseDatabase.getInstance().getReference("ticket").child("Item")
                .runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        String lastKey="-1";
                        for(MutableData c : currentData.getChildren())
                        {
                            lastKey = c.getKey();//lay lastkey
                        }
                        int nextKey = Integer.parseInt(lastKey)+1;
                        currentData.child(""+nextKey).setValue("gia tri can them");
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                        tvResult.setText("Insert Thanh cong");
                    }
                });
    }
//
//    DBFirebaseHelper helper;
//    public void createUser(View view) {
//        helper = new DBFirebaseHelper();
//        String name=edt1.getText().toString();
//        String email = edt2.getText().toString();
//        helper.getD
//        helper.createUser(name,email,tvResult);
//    }
//
//    public void updateUser(View view) {
//        String name=edt1.getText().toString();
//        String email = edt2.getText().toString();
//        helper.getDB();
//        helper.updateUser(name,email,tvResult);
//    }


    DBFirebaseHelper fh;
    public void createUrs(View view)
    {
        fh = new DBFirebaseHelper();
        String name=edt1.getText().toString();
        String email = edt2.getText().toString();
        fh.createUser(name,email,tvResult);
    }
    public void updateUrs(View view)
    {
        String name=edt1.getText().toString();
        String email = edt2.getText().toString();
        fh.updateUser(name,email,tvResult);
    }
}