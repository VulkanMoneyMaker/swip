package tut.mawrqns.jol;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class DialogComment extends DialogFragment {


    public interface DialogCommentListner {
        void onComment();
    }

    public static DialogComment newInstance() {

        return new DialogComment();
    }

    private DialogCommentListner dialogCommentListner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_comment, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText username = view.findViewById(R.id.et_username);
        EditText comment = view.findViewById(R.id.et_comment);
        TextInputLayout inputUserName = view.findViewById(R.id.input_username);
        TextInputLayout inputComment = view.findViewById(R.id.input_comment);
        Button button = view.findViewById(R.id.btn_comment);
        button.setOnClickListener(__->{
            if (!username.getText().toString().isEmpty() &&
                    !comment.getText().toString().isEmpty()) {
               dialogCommentListner.onComment();
               dismiss();
            } else {
                if (username.getText().toString().isEmpty()) {
                    inputUserName.setError("Заполните поле");
                    inputUserName.setErrorEnabled(true);
                } else {
                    inputUserName.setError("");
                    inputUserName.setErrorEnabled(false);
                }
                if (comment.getText().toString().isEmpty()) {
                    inputComment.setError("Заполните поле");
                    inputComment.setErrorEnabled(true);
                } else {
                    inputComment.setError("");
                    inputComment.setErrorEnabled(false);
                }
            }
        });

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            dialogCommentListner = (DialogCommentListner) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DialogSchemaOnClick");
        }
    }
}
