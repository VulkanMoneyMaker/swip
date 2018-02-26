package tut.mawrqns.jol;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import tut.mawrqns.jol.slotmania.G;

public class DialogSchema extends DialogFragment {

    public static final String TEXT_DIALOG = "text_dialog";
    public static final String TITLE_DIALOG = "title_dialog";
    public static final String IS_VIDEO_CONTENT = "isVideoContent";

    public interface DialogSchemaOnClick {
        void onClickPlay();
    }

    private DialogSchemaOnClick dialogSchemaOnClick;

    public static DialogSchema newInstance(String text, String title, boolean isVideoContent) {
        DialogSchema dialogSchema = new DialogSchema();
        Bundle bundle = new Bundle();
        bundle.putString(TEXT_DIALOG, text);
        bundle.putString(TITLE_DIALOG, title);
        bundle.putBoolean(IS_VIDEO_CONTENT, isVideoContent);
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
        String title = getArguments().getString(TITLE_DIALOG);
        boolean isVideoContent = getArguments().getBoolean(IS_VIDEO_CONTENT);
        TextView textView = view.findViewById(R.id.tv_text);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        ImageView imageTitle = view.findViewById(R.id.imageTitle);
        ScrollView scrollView = view.findViewById(R.id.scrollView);
        VideoView videoView = view.findViewById(R.id.video_player);
        tvTitle.setText(title);
        textView.setText(text);
        Button btnOk = view.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(__ -> getDialog().dismiss());
        Button btnPlay = view.findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(__ -> {
            getDialog().dismiss();
            dialogSchemaOnClick.onClickPlay();
        });
        if (isVideoContent) {
            scrollView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            String path = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.video_file;
            videoView.setVideoPath(path);
            videoView.start();
        } else {
            scrollView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);
        }
        Drawable drawable = getTitleImage(title);
        if (drawable != null) {
            imageTitle.setVisibility(View.VISIBLE);
            imageTitle.setImageDrawable(drawable);
        } else {
            imageTitle.setVisibility(View.GONE);
        }

    }

    private Drawable getTitleImage(String title) {
        if (title.equals(getString(R.string.schema_title_1))) {
            return getActivity().getResources().getDrawable(R.drawable.monkey);
        }
        if (title.equals(getString(R.string.schema_title_2))) {
            return getActivity().getResources().getDrawable(R.drawable.book_of_ra);
        }
        if (title.equals(getString(R.string.schema_title_3))) {
            return getActivity().getResources().getDrawable(R.drawable.fruit);
        }

        return null;
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
