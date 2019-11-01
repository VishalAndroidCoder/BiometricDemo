package com.vishal.biometricdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkWhetherDeviceSupportBiometric();
    }

    private void checkWhetherDeviceSupportBiometric() {
        BiometricManager manager = BiometricManager.from(this);
        if (manager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS){
            // If your device support Bio Metric It prompt Finger-print pop-up
            BiometricPrompt biometricPrompt = getBiometricPromptInstance();
            biometricPrompt.authenticate(getPromptInformation());

        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Failed");
            builder.setMessage("Your device not support biometric feature");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.create().show();
        }
    }

    private BiometricPrompt getBiometricPromptInstance(){
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Log.i("DEBUG", "Auth Error: "+errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                String success = "Fingerprint authorized successfully";
                Log.i("DEBUG", success);
                Intent i = new Intent(MainActivity.this, TopSecretActivity.class);
                i.putExtra("KEY_S", success);
                startActivity(i);
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.i("DEBUG", "Auth Failed");
            }
        };
        return new BiometricPrompt(this, executor, callback);
    }

    private BiometricPrompt.PromptInfo getPromptInformation(){
        return new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Bio Authentication")
                .setSubtitle("Fingerprint authentication")
                .setDescription("Top secret information given behind this page")
                //.setDeviceCredentialAllowed(true)  It create other options like, Enter Password, Security Pin or Patterns
                .setNegativeButtonText("Cancel")
                .setConfirmationRequired(true)
                .build();
    }

    public void tryAgain(View view){
        checkWhetherDeviceSupportBiometric();
    }
}