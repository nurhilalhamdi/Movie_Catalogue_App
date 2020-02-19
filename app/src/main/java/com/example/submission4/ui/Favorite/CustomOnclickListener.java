package com.example.submission4.ui.Favorite;

import android.view.View;

public class CustomOnclickListener implements View.OnClickListener {
    private int position;
    private OnItemClickCallback onItemClickCallback;
    public CustomOnclickListener(int position, OnItemClickCallback onItemClickCallback) {
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }
    @Override
    public void onClick(View view) {
        onItemClickCallback.onItemClicked(view, position);
    }
    public interface OnItemClickCallback {
        void onItemClicked(View view, int position);
    }
}