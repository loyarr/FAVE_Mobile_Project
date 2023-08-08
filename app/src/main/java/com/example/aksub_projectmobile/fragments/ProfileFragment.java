package com.example.aksub_projectmobile.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aksub_projectmobile.R;
import com.example.aksub_projectmobile.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    TextView usernameTxt, emailTxt, phoneTxt, signOut;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://mobile-project-da6de-default-rtdb.asia-southeast1.firebasedatabase.app/");
        userRef = firebaseDatabase.getReference("users").child(mAuth.getCurrentUser().getUid());

        usernameTxt = view.findViewById(R.id.tv_username);
        emailTxt = view.findViewById(R.id.tv_email);
        phoneTxt = view.findViewById(R.id.tv_phone);
        signOut = view.findViewById(R.id.tv_signOut);
        signOut.setOnClickListener(v -> {
            mAuth.signOut();
        });

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    User user = snapshot.getValue(User.class);
                    usernameTxt.setText(user.getUsername());
                    emailTxt.setText(user.getEmail());
                    if(user.getPhone() != null && user.getPhone().matches("[0-9]+")){
                        phoneTxt.setText(user.getPhone());
                    }else{
                        phoneTxt.setText("-");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to get user data D:", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}