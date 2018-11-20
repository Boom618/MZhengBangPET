package com.ty.zbpet.ui.widght;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;


import java.util.ArrayList;

/**
 * @author TY on 2018/11/19.
 * <p>
 * 供应商 选择 Dialog
 */
public class SelectDialogFg extends DialogFragment implements DialogInterface.OnClickListener {


    public static final String DATA = "items";

    public static final String SELECTED = "selected";

    public static final String TITLE = "dialogTitle";

    // 使用方法

//    FragmentManager fragmentManager = getSupportFragmentManager();
//
//    SelectDialogFg selectDialogFg = new SelectDialogFg();
//
//    Bundle bundle = new Bundle();
//    bundle.putStringArrayList(SelectDialogFg.DATA, getItems());
//    bundle.putInt(SelectDialogFg.SELECTED, -1);
//    bundle.putString(SelectDialogFg.TITLE, "供应商选择");
//    selectDialogFg.setArguments(bundle);
//    selectDialogFg.show(fragmentManager, "Dialog");


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle bundle = getArguments();
        ArrayList<String> list = bundle.getStringArrayList(DATA);
        int position = bundle.getInt(SELECTED);
        String title = bundle.getString(TITLE);

        CharSequence[] charSequences = list.toArray(new CharSequence[list.size()]);

        builder.setTitle(title)
                .setSingleChoiceItems(charSequences, position, this);


        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {


    }

}
