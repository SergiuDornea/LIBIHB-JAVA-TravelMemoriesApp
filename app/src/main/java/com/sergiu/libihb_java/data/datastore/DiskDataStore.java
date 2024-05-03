package com.sergiu.libihb_java.data.datastore;

import static com.sergiu.libihb_java.presentation.utils.Constants.BASE_DISCOVER_TILE_COUNT;
import static com.sergiu.libihb_java.presentation.utils.Constants.NO_EMERGENCY_CONTACT;

import android.util.Pair;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.rxjava3.RxDataStore;

import com.sergiu.libihb_java.presentation.utils.JsonConversionUtil;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DiskDataStore {
    private static final int DAYS_UNTIL_CACHED_DATA_EXPIRES = 10;
    private static final Preferences.Key<Long> DISCOVER_EXPIRE_DATE = PreferencesKeys.longKey("cached_mountain_expire_date");
    private static final Preferences.Key<String> EMERGENCY_CONTACT = PreferencesKeys.stringKey("emergency_contact");
    private static final Preferences.Key<Integer> NUMBER_OF_DISCOVER_TILES = PreferencesKeys.intKey("number_of_explore_tiles");
    private static final Preferences.Key<String> UNIT_OF_MEASUREMENT = PreferencesKeys.stringKey("unit_of_measurement");
    private static final Preferences.Key<Boolean> LOGGED_IN = PreferencesKeys.booleanKey("logged_in");
    private final String baseUnit = "metric";
    private final RxDataStore<Preferences> dataStore;
    private final JsonConversionUtil jsonConversionUtil;

    @Inject
    public DiskDataStore(RxDataStore<Preferences> preferencesDataStore, JsonConversionUtil jsonConversionUtil) {
        this.dataStore = preferencesDataStore;
        this.jsonConversionUtil = jsonConversionUtil;
    }

    public Completable writeEmergencyContact(String name, String phone) {
        Single<Preferences> single = dataStore.updateDataAsync(preferences -> {
            MutablePreferences mutablePreferences = preferences.toMutablePreferences();
            Pair<String, String> contact = new Pair<>(name, phone);
            mutablePreferences.set(EMERGENCY_CONTACT, jsonConversionUtil.fromPairToString(contact));
            return Single.just(mutablePreferences);
        });
        return Completable.fromSingle(single);
    }

    public Flowable<Pair<String, String>> getEmergencyContact() {
        return dataStore.data().map(preferences -> {
            String contactString = preferences.get(EMERGENCY_CONTACT);
            Pair<String, String> contact = jsonConversionUtil.fromStringToPairOfStrings(contactString);
            return contact != null ? contact : new Pair<>(NO_EMERGENCY_CONTACT, NO_EMERGENCY_CONTACT);
        });
    }

    public Completable writeDiscoverTitleSetting(int numberOfTiles) {
        Single<Preferences> updateSingle = dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(NUMBER_OF_DISCOVER_TILES, numberOfTiles);
            return Single.just(mutablePreferences);
        });

        return Completable.fromSingle(updateSingle);
    }

    public Flowable<Integer> getDiscoverTitleSetting() {
        return dataStore.data().map(preferences -> {
            Integer numberOfTilesInteger = preferences.get(NUMBER_OF_DISCOVER_TILES);
            return numberOfTilesInteger != null ? numberOfTilesInteger : BASE_DISCOVER_TILE_COUNT;
        });
    }

    public Completable writeUnitOfMeasurementSetting(String unit) {
        Single<Preferences> updateSingle = dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(UNIT_OF_MEASUREMENT, unit);
            return Single.just(mutablePreferences);
        });

        return Completable.fromSingle(updateSingle);
    }

    public Flowable<String> getUnitOfMeasurementSetting() {
        return dataStore.data().map(preferences -> {
            String unitOfMeasurement = preferences.get(UNIT_OF_MEASUREMENT);
            return unitOfMeasurement != null ? unitOfMeasurement : baseUnit;
        });
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

    public Completable writeIsLoggedIn(boolean isLoggedIn) {
        Single<Preferences> single = dataStore.updateDataAsync(preferences -> {
            MutablePreferences mutablePreferences = preferences.toMutablePreferences();
            mutablePreferences.set(LOGGED_IN, isLoggedIn);
            return Single.just(mutablePreferences);
        });
        return Completable.fromSingle(single);
    }

    public Flowable<Boolean> getIsLoggedIn() {
        return dataStore.data().map(preferences -> {
            Boolean isLoggedIn = preferences.get(LOGGED_IN);
            return isLoggedIn != null ? isLoggedIn : false;
        });
    }

    private Long getNextDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, DAYS_UNTIL_CACHED_DATA_EXPIRES);
        return calendar.getTime().getTime();
    }
}
