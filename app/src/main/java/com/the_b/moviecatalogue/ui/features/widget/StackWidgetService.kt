package com.the_b.moviecatalogue.ui.features.widget

import android.content.Intent
import android.widget.RemoteViewsService

class StackWidgetService: RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return RemoteViewsFactory(this.applicationContext)
    }
}