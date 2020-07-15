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
public class SignInFragment extends Fragment {

    public SignInFragment() {
        // Required empty public constructor
    }

    private TextView dontHaveAnAccount;
    private FrameLayout parentFrameLayout;
    private EditText emailAddress;
    private EditText password;
    private ImageButton closebtn;
    private Button signInBtn;
    private FirebaseAuth firebaseAuth;
    private String emailPattern="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        dontHaveAnAccount = view.findViewById(R.id.tv_if_not_sign_up);
        parentFrameLayout=getActivity().findViewById(R.id.register_framelayout);
        emailAddress=view.findViewById(R.id.sign_in_email);
        password=view.findViewById(R.id.sign_in_password);
        firebaseAuth=FirebaseAuth.getInstance();
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment() );

            }
        });
         closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainIntent();
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
               checkInputs();
           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });
       signInBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                checkEmailAndPassword();
           }
       });
        }

    private void setFragment(Fragment fragment){
            FragmentTransaction fragmentTransaction= getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_left);
            fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
            fragmentTransaction.commit();
    }
    private void checkInputs()  {
        if(TextUtils.isEmpty(emailAddress.getText())){
            if(TextUtils.isEmpty(password.getText())){
                signInBtn.setEnabled(true);
                signInBtn.setTextColor(Color.rgb(255, 255, 255));
            }else{
                signInBtn.setEnabled(false);
                signInBtn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        }else{
            signInBtn.setEnabled(false);
            signInBtn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }
    private void checkEmailAndPassword(){
     if(emailAddress.getText().toString().matches(emailPattern )){
if(password.length()>=8){
firebaseAuth.signInWithEmailAndPassword(emailAddress.getText().toString(),password.getText().toString())
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                 if(task.isSuccessful()){
                     mainIntent();
                 }else{
String error= task.getException().getMessage();
Toast.makeText(getActivity() ,"error", Toast.LENGTH_SHORT).show();
                 }
            }
        });
}else{
    Toast.makeText(getActivity(),"incorrect emailAddress or password", Toast.LENGTH_SHORT).show();
}
     }else{
         Toast.makeText(getActivity() , "incorrect emailAddress or password", Toast.LENGTH_SHORT).show();

     }
    }
    private void mainIntent(){

        Intent mainIntent= new Intent(getActivity(), MainActivity.class);
        startActivity(mainIntent);
        getActivity().finish();
    }
}
