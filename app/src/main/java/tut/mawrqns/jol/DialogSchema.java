package tut.mawrqns.jol;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DialogSchema extends DialogFragment {

    public static final String TEXT_DIALOG = "text_dialog";

    public interface DialogSchemaOnClick {
        void onClickPlay();
    }

    private DialogSchemaOnClick dialogSchemaOnClick;

    public static DialogSchema newInstance(String text) {
        DialogSchema dialogSchema = new DialogSchema();
        Bundle bundle = new Bundle();
        bundle.putString(TEXT_DIALOG, text);
        dialogSchema.setArguments(bundle);
        return dialogSchema;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.diaglog_schema, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String text = getArguments().getString(TEXT_DIALOG);
        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText(text);
        Button btnOk = view.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(__ -> getDialog().dismiss());
        Button btnPlay = view.findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(__ -> {
            getDialog().dismiss();
            dialogSchemaOnClick.onClickPlay();
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            dialogSchemaOnClick = (DialogSchemaOnClick) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DialogSchemaOnClick");
        }
    }
}
