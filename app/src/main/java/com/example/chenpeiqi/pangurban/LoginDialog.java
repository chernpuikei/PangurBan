package com.example.chenpeiqi.pangurban;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.chenpeiqi.pangurban.Helper.MyTool;

import static com.example.chenpeiqi.pangurban.Helper.MyLog.i;

/**
 * Created on 2018/11/7.
 */
public class LoginDialog extends DialogFragment {

    private static final boolean SELECTING_LOGIN = true, SELECTING_REGISTER = false;
    boolean currentOperation = SELECTING_LOGIN;
    boolean passwordConfirmed = false;
    EditText inputUserId, inputPassword, confirmPassword;
    TextView tabLogin, tabRegister, errorHint;
    Button button;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View dialog = getActivity().getLayoutInflater().inflate(R.layout.login_register,null);
        confirmPassword = dialog.findViewById(R.id.editText_confirm_password);
        inputUserId = dialog.findViewById(R.id.editText_input_user_id);
        inputPassword = dialog.findViewById(R.id.editText_input_password);
        tabLogin = dialog.findViewById(R.id.textView_select_login);
        errorHint = dialog.findViewById(R.id.error_hint);
        tabRegister = dialog.findViewById(R.id.textView_select_register);
        button = dialog.findViewById(R.id.button_send_request);

        IntentFilter accountFilter = new IntentFilter("login");
        accountFilter.addAction("register");
        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(new AccountStatus(),accountFilter);

        tabLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                i("tab_login>>");
                confirmPassword.setVisibility(View.GONE);
                currentOperation = SELECTING_LOGIN;
            }
        });
        tabRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                i("tab_register>>");
                currentOperation = SELECTING_REGISTER;
                confirmPassword.setVisibility(View.VISIBLE);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                i("onClick>>");
                if (inputUserId.getText().toString().equals("") ||
                        inputPassword.getText().toString().equals("") ||
                        (!currentOperation && confirmPassword.getText().toString().equals(""))) {
                    errorHint.setText(R.string.incomplete_info);
                    i("incomplete info,event invalid");
                    return;
                }
                if (currentOperation ||
                        inputPassword.getText().toString().equals(confirmPassword.getText().toString())) {
                    i("about to send request");
                    Intent intent = new Intent(currentOperation? "login": "register");
                    i("intent.getAction?",intent.getAction());
                    intent.putExtra("id",inputUserId.getText().toString())
                            .putExtra("password",inputPassword.getText().toString());
                    NetworkTransaction.enqueueWork(getContext(),NetworkTransaction.class,NetworkTransaction.jobId_login_register,intent);
                } else {
                    i("password error");
                    errorHint.setText(R.string.error_password_not_the_same);
                }

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialog);
        return builder.create();
    }

    private class AccountStatus extends BroadcastReceiver {

        @Override
        public void onReceive(Context context,Intent intent) {
            i("Dialog receives broadcast",intent);
            String action = intent.getAction();
            boolean result = intent.getBooleanExtra("result",false);
            if (result) {
                MyTool.getSP(context).edit().putString("id",intent.getStringExtra("id"));
                LoginDialog.this.dismiss();
            } else {
                switch (action) {
                    case "login":
                        errorHint.setText(R.string.error_id_password_incorrect);
                        break;
                    case "register":
                        errorHint.setText(R.string.error_id_exist);
                }
            }
        }
    }

}
