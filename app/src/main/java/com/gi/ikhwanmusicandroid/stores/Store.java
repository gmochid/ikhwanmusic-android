package com.gi.ikhwanmusicandroid.stores;

import com.gi.ikhwanmusicandroid.actions.Action;
import com.gi.ikhwanmusicandroid.actions.Dispatcher;

/**
 * Created by gmochid on 2/4/16.
 */
public abstract class Store {

    final Dispatcher dispatcher;

    protected Store(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    void emitStoreChange(StoreChangeEvent event) {
        dispatcher.emitChange(event);
    }

    public abstract void onAction(Action action);

    public interface StoreChangeEvent {}
}