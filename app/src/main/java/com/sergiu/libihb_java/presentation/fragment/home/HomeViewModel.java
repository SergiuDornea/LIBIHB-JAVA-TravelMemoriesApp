package com.sergiu.libihb_java.presentation.fragment.home;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.domain.model.TravelMemory;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    public List<TravelMemory> getDummyMemories(){
        List<TravelMemory> dummyMemories = new ArrayList<>();

        TravelMemory tr1 = new TravelMemory(getImageList(R.drawable.rimetea, R.drawable.negoiu, R.drawable.lespezi, R.drawable.vanatoare), "t1", new LatLng(37.7749, -122.4194), "Date1", "loc1");
        TravelMemory tr2 = new TravelMemory(getImageList(R.drawable.negoiu, R.drawable.rimetea, R.drawable.negoiu, R.drawable.lespezi), "t2", new LatLng(37.7749, -122.4194), "Date2", "loc2");
        TravelMemory tr3 = new TravelMemory(getImageList(R.drawable.lespezi, R.drawable.rimetea, R.drawable.negoiu, R.drawable.lespezi), "t3", new LatLng(37.7749, -122.4194), "Date3", "loc3");
        TravelMemory tr4 = new TravelMemory(getImageList(R.drawable.moldoveanu, R.drawable.rimetea, R.drawable.negoiu, R.drawable.lespezi), "t4", new LatLng(37.7749, -122.4194), "Date4", "loc4");
        TravelMemory tr5 = new TravelMemory(getImageList(R.drawable.vanatoare, R.drawable.rimetea, R.drawable.negoiu, R.drawable.lespezi), "t5", new LatLng(37.7749, -122.4194), "Date5", "loc5");
        TravelMemory tr6 = new TravelMemory(getImageList(R.drawable.vistea, R.drawable.rimetea, R.drawable.negoiu, R.drawable.lespezi), "t6", new LatLng(37.7749, -122.4194), "Date6", "loc6");

        dummyMemories.add(tr1);
        dummyMemories.add(tr2);
        dummyMemories.add(tr3);
        dummyMemories.add(tr4);
        dummyMemories.add(tr5);
        dummyMemories.add(tr6);

        return dummyMemories;
    }

    private List<Integer> getImageList(int... resourceIds) {
        List<Integer> imageList = new ArrayList<>();
        for (int resourceId : resourceIds) {
            imageList.add(resourceId);
        }
        return imageList;
    }
}
