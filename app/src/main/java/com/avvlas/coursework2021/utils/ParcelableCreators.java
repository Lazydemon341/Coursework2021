package com.avvlas.coursework2021.utils;

import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.avvlas.coursework2021.model.Macro;

public class ParcelableCreators {
    @NonNull
    public static Parcelable.Creator<Macro> getMacroCreator() {
        return Macro.CREATOR;
    }
}
