package com.kedarnath.zipperlockscreen.Apputils;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kedarnath.zipperlockscreen.Activity.KZL_MainActivity;
import com.kedarnath.zipperlockscreen.R;


public class PasswordDialog extends Dialog {
    public static boolean OK_CLICKED;
    static SharedPreferences spf;
    Context context;
    TextView enterPassText;
    ImageView okBtn;
    EditText passwordEdit1;
    EditText passwordEdit2;
    ImageView passwordVisibilityBtn;
    boolean passwordVisible;
    TextView reenterPassText;
    SharedPreferences.Editor spfEdit;


    public PasswordDialog(Context context2) {
        super(context2);
        context = context2;
        spf = context2.getSharedPreferences(String.valueOf(context2.getPackageName()), 0);
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.dialog_password);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        passwordVisible = false;
        spfEdit = spf.edit();
        OK_CLICKED = false;
        passwordEdit1 = findViewById(R.id.password_edit_1);
        getWindow().setSoftInputMode(4);
        if (KZL_MainActivity.CURRENT_PASSWORD.equals("")) {
            passwordEdit1.setText("0000");
        } else {
            passwordEdit1.setText(KZL_MainActivity.CURRENT_PASSWORD);
        }
        passwordEdit2 = findViewById(R.id.password_edit_2);
        if (KZL_MainActivity.CURRENT_PASSWORD.equals("")) {
            passwordEdit2.setText("0000");
        } else {
            passwordEdit2.setText(KZL_MainActivity.CURRENT_PASSWORD);
        }
        enterPassText = findViewById(R.id.dialog_enter_password_text);
        reenterPassText = findViewById(R.id.dialog_reenter_password_text);
        okBtn = findViewById(R.id.dialog_password_ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (passwordEdit1.getText().toString().length() != 4 || passwordEdit2.getText().toString().length() != 4) {
                    Toast.makeText(context, R.string.enter_4_digit_pass, Toast.LENGTH_SHORT).show();
                } else if (passwordEdit1.getText().toString().equals(passwordEdit2.getText().toString())) {
                    KZL_MainActivity.CURRENT_PASSWORD = passwordEdit1.getText().toString();
                    spfEdit.putString(context.getString(R.string.PIN_PREF_KEY), passwordEdit1.getText().toString());
                    spfEdit.commit();
                    PasswordDialog.OK_CLICKED = true;
                    dismiss();
                } else {
                    Toast.makeText(context, R.string.pass_no_match, Toast.LENGTH_SHORT).show();
                }
            }
        });
        passwordVisibilityBtn = (ImageView) findViewById(R.id.password_visibility_btn);
        passwordVisibilityBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (passwordVisible) {
                    passwordVisible = false;
                } else {
                    passwordVisible = true;
                }
                setInputType(passwordVisible);
            }
        });
    }


    public void setInputType(boolean z) {
        if (z) {
            passwordEdit1.setTransformationMethod((TransformationMethod) null);
            passwordEdit2.setTransformationMethod((TransformationMethod) null);
            passwordVisibilityBtn.setImageResource(R.drawable.btn_no_see);
            passwordEdit1.invalidate();
            passwordEdit2.invalidate();
            if (passwordEdit1.hasFocus()) {
                passwordEdit1.setSelection(0, passwordEdit1.getText().length());
            } else if (passwordEdit2.hasFocus()) {
                passwordEdit2.setSelection(0, passwordEdit2.getText().length());
            } else {
                passwordEdit1.requestFocus();
                passwordEdit1.setSelection(0, passwordEdit1.getText().length());
            }
        } else {
            passwordEdit1.setTransformationMethod(new PasswordTransformationMethod());
            passwordEdit2.setTransformationMethod(new PasswordTransformationMethod());
            passwordVisibilityBtn.setImageResource(R.drawable.btn_see);
            passwordEdit1.invalidate();
            passwordEdit2.invalidate();
            if (passwordEdit1.hasFocus()) {
                passwordEdit1.setSelection(0, passwordEdit1.getText().length());
            } else if (passwordEdit2.hasFocus()) {
                passwordEdit2.setSelection(0, passwordEdit2.getText().length());
            } else {
                passwordEdit1.requestFocus();
                passwordEdit1.setSelection(0, passwordEdit1.getText().length());
            }
        }
    }
}
