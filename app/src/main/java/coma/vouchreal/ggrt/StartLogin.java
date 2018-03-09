package coma.vouchreal.ggrt;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;

public class StartLogin extends Activity {

    private EditText passwordEdit;
    private EditText usernameEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Button signInBtn = findViewById(R.id.sing_in_button);
        usernameEdit = findViewById(R.id.edit_text_email);
        passwordEdit = findViewById(R.id.edit_text_password);

        signInBtn.setOnClickListener(__->signIn());

    }

    private void signIn() {
        if (passwordEdit.getText().toString().isEmpty()
                || usernameEdit.getText().toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Warning")
                    .setMessage("Fill in all the fields")
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            (dialog, id) -> {
                                dialog.cancel();
                            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Warning")
                    .setMessage("We apologize on the server are working, try another time.")
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            (dialog, id) -> {
                                dialog.cancel();
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}