package com.example.qrhunter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScanCompleteDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayCodeFragment extends DialogFragment {

    private Bitmap image_bitmap;
    private static final String ARG_PARAM2 = "Message";
    private String message;

    private ImageView qrCodeImage;
    private TextView textView;
    public DisplayCodeFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param message The message to display
     * @return A new instance of fragment ScanCompleteDialog.
     */
    public static DisplayCodeFragment newInstance(String message) {
        DisplayCodeFragment fragment = new DisplayCodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM2, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        image_bitmap = ((PlayerProfile) getActivity()).getMap();
        message = ((PlayerProfile) getActivity()).getMessage();

    }


    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setTitle(message);
        //not working
        dialog.setCanceledOnTouchOutside(true);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
        return dialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if(qrCodeImage != null) {
            qrCodeImage.setImageBitmap(image_bitmap);
        }
        if (textView != null) {
            textView.setText(message);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_code, null);
        qrCodeImage = (ImageView)view.findViewById(R.id.qr_code_profile);
        textView = (TextView) view.findViewById(R.id.message);
        Button exit = (Button) view.findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        getActivity(). getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return view;
    }
}