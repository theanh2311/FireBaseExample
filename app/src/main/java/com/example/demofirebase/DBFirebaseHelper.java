package com.example.demofirebase;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DBFirebaseHelper {
    private FirebaseDatabase f_instance;//lay ve instance
    private DatabaseReference f_db;//lay ve db
    private String USER_ID;
    public void createUser(String name, String email, TextView tvResult)
    {
        f_instance = FirebaseDatabase.getInstance();
        f_db = f_instance.getReference("Urs");//ten bang du lieu
        if(TextUtils.isEmpty(USER_ID))//chua co
        {
            USER_ID = f_db.push().getKey();//tao 1 key
        }
        Urs urs = new Urs(name,email);
        f_db.child(USER_ID).setValue(urs);//insert du lieu
        ursChangListener(tvResult);
    }
    public void updateUser(String name, String email,TextView tvResult)
    {
        f_instance = FirebaseDatabase.getInstance();
        f_db = f_instance.getReference("Urs");//ten bang du lieu
        if(!TextUtils.isEmpty(name))
            f_db.child(USER_ID).child("name").setValue(name);//cap nhat du lieu truong name
        if(!TextUtils.isEmpty(email))
            f_db.child(USER_ID).child("email").setValue(email);
        ursChangListener(tvResult);
    }
    public void ursChangListener(final TextView textView)
    {
        f_db.child(USER_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Urs urs = snapshot.getValue(Urs.class);
                textView.setText(urs.getName()+": "+urs.getEmail());
                //lay listUser
                List<Urs> ls = new ArrayList<>();
                for(DataSnapshot d : snapshot.getChildren())
                {
                    Urs urs1 = snapshot.getValue(Urs.class);
                    ls.add(urs1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                textView.setText(error.getMessage());
            }
        });
    }
    public void resetPasswwordFB(String email)
    {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    //Thong bao reset thanh cong
                    //check mail kiem tra
                }
            }
        });
    }

    public void changePassFB(String old_pass, final String new_pass)
    {
        //can thiet thiet ke 2 button: Login, changepass
        //chay chuong trinh vaf login thanh cong
        //tiep theo clink vao change pass
         final FirebaseUser user;
         user = FirebaseAuth.getInstance().getCurrentUser();//lay ve user hien tai
        final String email = user.getEmail();//lay ve email cua user
        AuthCredential credential = EmailAuthProvider.getCredential(email,old_pass);//lay ve credential
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {//tao lai credential
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())//neu thanh cong
                {
                    //update password
                    user.updatePassword(new_pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                //thong bao update password thanh cong
                            }
                        }
                    });
                    //dua ra thong bao lay ve  reauthenticate thanh cong
                }
                else
                {
                    //dua ra thong bao lay ve  reauthenticate that bai
                }
            }
        });
    }
}
