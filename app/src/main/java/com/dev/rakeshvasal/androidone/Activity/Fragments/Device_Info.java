package com.dev.rakeshvasal.androidone.Activity.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.rakeshvasal.androidone.Activity.BaseFragment;
import com.dev.rakeshvasal.androidone.Activity.Utilities.Utils;
import com.dev.rakeshvasal.androidone.R;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class Device_Info extends BaseFragment {


    public Device_Info() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_device__info, container, false);

        String[] TABLE_HEADERS = { "Feature", "Value"};
        TableView<String[]> tableView = view.findViewById(R.id.tableView);
        tableView.setColumnCount(2);
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(getActivity(), TABLE_HEADERS));
        TableColumnWeightModel columnModel = new TableColumnWeightModel(2);
        columnModel.setColumnWeight(1, 1);
        columnModel.setColumnWeight(2, 1);
        tableView.setColumnModel(columnModel);

        setData(tableView);
        return view;
    }

    private void setData(TableView<String[]> tableView) {

        String manufacturer = Build.MANUFACTURER;
        String brand = Build.BRAND;
        String model = Build.MODEL;
        int androidSDK = Build.VERSION.SDK_INT;
        String androidRelease = Build.VERSION.RELEASE;

        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        float avilbaleStorage = (statFs.getAvailableBlocks() * statFs.getBlockSize()) / 1048576f * -1;
        String availableStorage = avilbaleStorage + " MB.";
        String availableRam = Utils.getMemoryAndCpuData(getActivity());
        String availableHeap = Runtime.getRuntime().maxMemory() + " bytes";


        String ipaddv4 = Utils.getIPAddress(true);
        String ipaddv6 = Utils.getIPAddress(false);

        String[][] DEVICE_DATA = {{"Manufacturer", manufacturer},
                {"Brand", brand},
                {"Model", model},
                {"Android", "" + androidSDK},
                {"Release", androidRelease},
                {"IPV4", ipaddv4},
                {"IPV6", ipaddv6},
                {"Storage Available", availableStorage},
                {"Storage RAM", availableRam},
                {"Heap Available", availableHeap}};

        tableView.setDataAdapter(new SimpleTableDataAdapter(getActivity(), DEVICE_DATA));

    }

}
