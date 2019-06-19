package com.mobilepolice.office;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mobilepolice.office.db.DatabaseOpenHelper;

public class FavoriteInfoProvider extends ContentProvider {

    DatabaseOpenHelper helper;

    private String auth  = "com.access.favorite.info";

    @Override
    public boolean onCreate() {
        helper = new DatabaseOpenHelper(getContext(), 1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return helper.getWritableDatabase().query("favorite_item", projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long favorite_item = helper.getWritableDatabase().insert("favorite_item", null, values);
        Log.e("insert: ", uri.toString()+"/"+favorite_item);
        return Uri.parse(uri.toString()+"/"+favorite_item);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int favorite_item = helper.getWritableDatabase().delete("favorite_item", selection, selectionArgs);
        return favorite_item;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int favorite_item = helper.getWritableDatabase().update("favorite_item", values, selection, selectionArgs);
        return favorite_item;
    }
}
