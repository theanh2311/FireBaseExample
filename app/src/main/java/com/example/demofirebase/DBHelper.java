package com.example.demofirebase;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DBHelper {
    FirebaseDatabase f_install;
    DatabaseReference f_db;
    String USER_ID;
    //tao
    public void  createUser(String u,String e)
    {
        f_install = FirebaseDatabase.getInstance();
        f_db = f_install.getReference("user");//tenDB
        if(TextUtils.isEmpty(USER_ID))
        {
            USER_ID = f_db.push().getKey();//tao ID ngau nhien
        }
        User user = new User(u,e);
        f_db.child(USER_ID).setValue(user);
        //Cap nhat vao database
        update_UserToDB();
    }
    //cap nhat
    public void updateUser(String name,String email)
    {
        f_install = FirebaseDatabase.getInstance();
        f_db = f_install.getReference("user");//tenDB
        if(!TextUtils.isEmpty(name))
        {
            f_db.child(USER_ID).child("name").setValue(name);
        }
        if(!TextUtils.isEmpty(email))
        {
            f_db.child(USER_ID).child("email").setValue(email);
        }
        update_UserToDB();//thuc su update vao firebase
    }




    private void update_UserToDB()//ham rieng: update vao database
    {
        f_db.child(USER_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);//current user
                List<User> list = new ArrayList<>();
                for(DataSnapshot ds: snapshot.getChildren())//get all user
                {
                    User u1 = snapshot.getValue(User.class);
                    list.add(u1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
