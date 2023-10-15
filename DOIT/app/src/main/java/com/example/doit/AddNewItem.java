package com.example.doit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.doit.Model.Item;
import com.example.doit.Utils.DataBaseHelper;

import java.time.LocalDateTime;
import java.util.Objects;

public class AddNewItem extends DialogFragment {

    public static final String TAG = "AddNewTask";

    private Button saveButton;
    private EditText editSubject;
    private EditText editNote;

    private DataBaseHelper database;

    public static AddNewItem newInstance() {
        return new AddNewItem();
    }

    @NonNull
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.item_popup, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceStatus) {
        super.onViewCreated(view, savedInstanceStatus);

        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setEnabled(false);
        Button cancelButton = view.findViewById(R.id.cancelButton);
        editSubject = view.findViewById(R.id.editTextSubject);
        editNote = view.findViewById(R.id.editTextNote);

        database = new DataBaseHelper(getActivity());

        boolean isUpdated = false;

        final Bundle bundle = getArguments();

        if (bundle != null) {
            isUpdated = true;
            String subject = Objects.requireNonNull(bundle.get("subject")).toString();
            String note = Objects.requireNonNull(bundle.get("note")).toString();
            editSubject.setText(subject);
            editNote.setText(note);
        }

        final boolean finalIsUpdate = isUpdated;

        if(isUpdated) {
            saveButton.setEnabled(true);
        }

        editSubject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().equals("")) {
                    saveButton.setEnabled(false);
                } else {
                    saveButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = editSubject.getText().toString();
                String note = editNote.getText().toString();

                if (finalIsUpdate){
                    database.updateTask(bundle.getInt("id") , subject, note);
                }else{
                    Item item = new Item();
                    item.setSubject(subject);
                    item.setNote(note);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        item.setCreationDateTime(LocalDateTime.now());
                    }
                    item.setCompleted(false);
                    database.insertTask(item);
                }
                dismiss();

            }
        });

        cancelButton.setOnClickListener(view1 -> dismiss());

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof OnDialogCloseListner){
            ((OnDialogCloseListner)activity).onDialogClose(dialog);
        }

    }
}
