package com.example.submission4.ui.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class StackWidgerService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext());
    }
}
