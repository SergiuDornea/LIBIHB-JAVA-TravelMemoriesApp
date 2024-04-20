package com.sergiu.libihb_java.data.datastore;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.rxjava3.RxDataStore;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DiskDataStore {
    private static final int DAYS_UNTIL_CACHED_DATA_EXPIRES = 10;
    private static final Preferences.Key<Long> DISCOVER_EXPIRE_DATE = PreferencesKeys.longKey("cached_mountain_expire_date");
    private final RxDataStore<Preferences> dataStore;

    @Inject
    public DiskDataStore(RxDataStore<Preferences> preferencesDataStore) {
        this.dataStore = preferencesDataStore;
    }

    public Completable writeDiscoverExpireDate() {
        Single<Preferences> single = dataStore.updateDataAsync(preferences -> {
            MutablePreferences mutablePreferences = preferences.toMutablePreferences();
            mutablePreferences.set(DISCOVER_EXPIRE_DATE, getNextDate());
            return Single.just(mutablePreferences);
        });
        return Completable.fromSingle(single);
    }

    public Date getDiscoverExpireDate() {
        return dataStore.data().map(preferences -> {
                    Long date = preferences.get(DISCOVER_EXPIRE_DATE);
                    return date == null ? new Date() : new Date(date);
                }).subscribeOn(Schedulers.io())
                .blockingFirst();
    }

    private Long getNextDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, DAYS_UNTIL_CACHED_DATA_EXPIRES);
        return calendar.getTime().getTime();
    }
}
