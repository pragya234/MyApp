package com.example.myapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    public SignUpFragment() {
        // Required empty public constructor
    }

    private TextView AlreadyHaveAnAccount;
    private FrameLayout parentFrameLayout;
    private EditText fullname;
    private EditText emailAddress;
    private EditText password;
    private EditText confirmpassword;
    private ImageButton closebtn;
    private Button signUpBtn;
    private FirebaseAuth firebaseAuth;
    private String emailPattern="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
     ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);
        AlreadyHaveAnAccount = view.findViewById(R.id.tv_already_an_account);
        fullname = view.findViewById(R.id.sign_up_name);
        emailAddress = view.findViewById(R.id.sign_up_email);
        password = view.findViewById(R.id.sign_up_pswrd);
        confirmpassword = view.findViewById(R.id.sign_up_cnfrm_pswrd);
        closebtn = view.findViewById(R.id.sign_up_close_btn);
        signUpBtn = view.findViewById(R.id.sign_up_btn);
        firebaseAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AlreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });
        fullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        emailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirmpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void checkInputs() {
        if (!TextUtils.isEmpty(fullname.getText())) {
            if (!TextUtils.isEmpty(emailAddress.getText())) {
                if (!TextUtils.isEmpty(password.getText())) {
                    if (!TextUtils.isEmpty(confirmpassword.getText())) {
                        signUpBtn.setEnabled(true);
                        signUpBtn.setTextColor(Color.rgb(255, 255, 255));
                    } else {
                        signUpBtn.setEnabled(false);
                        signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));

                    }
                } else {
                    signUpBtn.setEnabled(false);
                    signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
                }
            } else {
                signUpBtn.setEnabled(false);
                signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            signUpBtn.setEnabled(false);
            signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }
    private void checkEmailAndPassword(){
        if(emailAddress.getText().toString().matches(emailPattern)){
if(password.getText().toString().equals(confirmpassword.getText().toString() )){
    signUpBtn.setEnabled(false);
    signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
firebaseAuth.createUserWithEmailAndPassword(emailAddress.getText().toString(),password.getText().toString())
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
               Intent mainIntent= new Intent(getActivity(),MainActivity.class);
               startActivity(mainIntent);
               getActivity().finish();
               }else{
                   signUpBtn.setEnabled(true);
                   signUpBtn.setTextColor(Color.rgb(255, 255, 255));
                   String error= task.getException().getMessage();
                   Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();
               }
            }
        });
        }else{
        confirmpassword.setError("Password doesn't match!");
        }
        }else{
            emailAddress.setError("Invalid email!");
        }
        }

        }