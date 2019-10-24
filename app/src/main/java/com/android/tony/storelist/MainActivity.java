package com.android.tony.storelist;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    RadioGroup genderRadioGroup;
    RadioButton radioButton;
    TextInputLayout nameTextInputLayout, passwordTextInputLayout, confirmPasswordTextInputLayout, emailTextInputLayout, numberTextInputLayout, birthTextInputLayout;
    TextInputEditText nameTextInputEditText, passwordTextInputEditText, confirmTextInputEditText, emailTextInputEditText, numberTextInputEditText, birthTextInputEditText;
    Button signButton;
    TextView accountTextView;
    SimpleDateFormat simpleDateFormat;
    Calendar calendar;
    DatePickerDialog.OnDateSetListener dateSetListener;
    String emailPattern;
    Uri uri;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // if user is signed in it will redirect to next activity
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, StoreListActivity.class));
            this.finish();
        }

        nameTextInputLayout = findViewById(R.id.nameTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTtextInputLayout);
        confirmPasswordTextInputLayout = findViewById(R.id.confirmPasswordTextInputLayout);
        emailTextInputLayout = findViewById(R.id.emailTextInputLayout);
        numberTextInputLayout = findViewById(R.id.phoneNumberTextInputLayout);
        birthTextInputLayout = findViewById(R.id.dateOfBirthTextInputLayout);
        nameTextInputEditText = findViewById(R.id.nameTextInputEditText);
        passwordTextInputEditText = findViewById(R.id.passwordTextInputEditText);
        confirmTextInputEditText = findViewById(R.id.confirmPasswordTextInputEditText);
        emailTextInputEditText = findViewById(R.id.emailTextInputEditText);
        numberTextInputEditText = findViewById(R.id.numberTextInputEditText);
        birthTextInputEditText = findViewById(R.id.birthTextInputEditText);
        signButton = findViewById(R.id.signButton);
        accountTextView = findViewById(R.id.accountTextView);
        progressBar = findViewById(R.id.userManagementProgressBar);
        genderRadioGroup = findViewById(R.id.radioGroup);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        calendar = Calendar.getInstance();
        emailPattern = getResources().getString(R.string.email_pattern);
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);
                birthTextInputEditText.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        // making password field
        passwordTextInputEditText.setTransformationMethod(new PasswordTransformationMethod());
        passwordTextInputEditText.setTypeface(Typeface.DEFAULT);
        confirmTextInputEditText.setTransformationMethod(new PasswordTransformationMethod());
        confirmTextInputEditText.setTypeface(Typeface.DEFAULT);
        radioButton = findViewById(R.id.maleRadioButton);
        //Toast.makeText(getApplicationContext(),radioButton.getText(),Toast.LENGTH_SHORT).show();
        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton = radioGroup.findViewById(i);
                // Toast.makeText(getApplicationContext(),radioButton.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        birthTextInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        accountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accountTextView.getText().toString().equals(getResources().getString(R.string.already_have_an_account))) {
                    accountTextView.setText(getResources().getString(R.string.create_account));
                    signButton.setText(getResources().getString(R.string.sign_in));
                    nameTextInputLayout.setVisibility(View.GONE);
                    numberTextInputLayout.setVisibility(View.GONE);
                    birthTextInputLayout.setVisibility(View.GONE);
                    confirmPasswordTextInputLayout.setVisibility(View.GONE);
                    genderRadioGroup.setVisibility(View.GONE);

                    emailTextInputEditText.setError(null);
                    passwordTextInputEditText.setError(null);
                    passwordTextInputLayout.setError("");

                    emailTextInputEditText.setText("");
                    passwordTextInputEditText.setText("");
                } else {
                    accountTextView.setText(getResources().getString(R.string.already_have_an_account));
                    signButton.setText(getResources().getString(R.string.sign_up));
                    genderRadioGroup.setVisibility(View.VISIBLE);
                    nameTextInputLayout.setVisibility(View.VISIBLE);
                    numberTextInputLayout.setVisibility(View.VISIBLE);
                    birthTextInputLayout.setVisibility(View.VISIBLE);
                    confirmPasswordTextInputLayout.setVisibility(View.VISIBLE);

                    nameTextInputEditText.setError(null);
                    emailTextInputEditText.setError(null);
                    numberTextInputEditText.setError(null);
                    birthTextInputEditText.setError(null);
                    passwordTextInputEditText.setError(null);
                    confirmTextInputEditText.setError(null);

                    nameTextInputEditText.setText("");
                    emailTextInputEditText.setText("");
                    numberTextInputEditText.setText("");
                    birthTextInputEditText.setText("");
                    passwordTextInputEditText.setText("");
                    confirmTextInputEditText.setText("");
                }
            }
        });
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                if (radioButton.getText().toString().equals(getResources().getString(R.string.male))) {
                    uri = Uri.parse("android.resource://com.android.tony.aura/drawable/male");
                } else {
                    uri = Uri.parse("android.resource://com.android.tony.aura/drawable/female");
                }
                if (validateForm() && signButton.getText().toString().equals(getResources().getString(R.string.sign_up))) {
                    final String name = nameTextInputEditText.getText().toString().trim(), email = emailTextInputEditText.getText().toString().trim(),
                            phone = numberTextInputEditText.getText().toString().trim(), dob = birthTextInputEditText.getText().toString().trim(),
                            password = passwordTextInputEditText.getText().toString().trim();
                    Log.i("image", "" + uri);
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new UsersDatabase(name, email, dob, phone, radioButton.getText().toString())).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        FirebaseStorage.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profile_picture").putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(getApplicationContext(), "Please Verify your email to continue", Toast.LENGTH_SHORT).show();
                                                                    FirebaseAuth.getInstance().signOut();
                                                                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                                                                } else {
                                                                    progressBar.setVisibility(View.GONE);
                                                                    FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(null);
                                                                    FirebaseAuth.getInstance().getCurrentUser().delete();
                                                                    Toast.makeText(getApplicationContext(), "Something went wrong please try again", Toast.LENGTH_LONG).show();
                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        progressBar.setVisibility(View.GONE);
                                                        FirebaseAuth.getInstance().getCurrentUser().delete();
                                                        Snackbar.make(findViewById(R.id.userMainConstraintLayout), task.getException().getMessage(), Snackbar.LENGTH_SHORT).setActionTextColor(getResources().getColor(R.color.text)).show();
                                                    }

                                                }
                                            });
                                        } else {
                                            progressBar.setVisibility(View.GONE);
                                            FirebaseAuth.getInstance().getCurrentUser().delete();
                                            Snackbar.make(findViewById(R.id.userMainConstraintLayout), task.getException().getMessage() + " Try Again", Snackbar.LENGTH_SHORT).setActionTextColor(getResources().getColor(R.color.text)).show();
                                        }
                                    }
                                });
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Snackbar.make(findViewById(R.id.userMainConstraintLayout), task.getException().getMessage(), Snackbar.LENGTH_SHORT).setActionTextColor(getResources().getColor(R.color.text)).show();
                            }

                        }
                    });

                } else if (validateForm() && signButton.getText().toString().equals(getResources().getString(R.string.sign_in))) {
                    String email = emailTextInputEditText.getText().toString().trim(), password = passwordTextInputEditText.getText().toString().trim();
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                                    startActivity(new Intent(MainActivity.this, StoreListActivity.class));
                                    MainActivity.this.finish();
                                } else {
                                    Snackbar.make(findViewById(R.id.userMainConstraintLayout), "Verify Email Address to continue", Snackbar.LENGTH_SHORT).setAction("Send Email Verifiacation", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
                                            FirebaseAuth.getInstance().signOut();
                                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                                            MainActivity.this.finish();
                                        }
                                    }).show();
                                }
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Snackbar.make(findViewById(R.id.userMainConstraintLayout), task.getException().getMessage(), Snackbar.LENGTH_SHORT).setActionTextColor(getResources().getColor(R.color.text)).show();
                            }
                        }
                    });
                }
            }
        });
    }

    boolean validateForm() {
        boolean flag = true;
        if (signButton.getText().toString().equals(getResources().getString(R.string.sign_up))) {
            if (nameTextInputEditText.getText().toString().trim().isEmpty()) {
                nameTextInputEditText.setError("Enter Valid Name");
                flag = false;
            }

            if (emailTextInputEditText.getText().toString().trim().isEmpty() || !emailTextInputEditText.getText().toString().trim().matches(emailPattern)) {
                emailTextInputEditText.setError("Enter Valid Email");
                flag = false;
            }

            if (numberTextInputEditText.getText().toString().trim().isEmpty() || numberTextInputEditText.getText().toString().trim().length() > 10) {
                numberTextInputEditText.setError("Enter Valid Phone Number");
                flag = false;
            }

            if (birthTextInputEditText.getText().toString().trim().isEmpty()) {
                birthTextInputEditText.setError("Enter Valid Date of Birth");
                flag = false;
            }

            if (passwordTextInputEditText.getText().toString().trim().isEmpty() || passwordTextInputEditText.getText().toString().trim().length() < 6) {
                passwordTextInputEditText.setError("Enter a valid password of 6 charcaters");
                flag = false;
            }

            if (!passwordTextInputEditText.getText().toString().trim().equals(confirmTextInputEditText.getText().toString().trim()) || confirmTextInputEditText.getText().toString().trim().isEmpty()) {
                passwordTextInputEditText.setError("Password do not matches");
                confirmTextInputEditText.setError("Password do not matches");
                flag = false;
            }
        } else {
            if (passwordTextInputEditText.getText().toString().trim().isEmpty() || passwordTextInputEditText.getText().toString().trim().length() < 6) {
                passwordTextInputEditText.setError("Enter valid 6 character password");
                flag = false;
            }
            if (emailTextInputEditText.getText().toString().trim().isEmpty() || !emailTextInputEditText.getText().toString().trim().matches(emailPattern)) {
                emailTextInputEditText.setError("Enter Valid Email");
                flag = false;
            }
        }

        return flag;
    }
}

