package com.example.labskeletest;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ExpandableListLabInfoAdapater extends BaseExpandableListAdapter {
    private Context context;
    private java.util.List<String> listDataHeader;
    private HashMap<String,java.util.List<String>> listHashMap;
    private Lab lab;

    public ExpandableListLabInfoAdapater(Context content, java.util.List<String> listDataHeader, HashMap<String, List<String>> listHashMap, Lab lab) {
        this.context = content;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
        this.lab = lab;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listHashMap.get(listDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listDataHeader.get(i);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_textview, null);
        }

        TextView listLabel = (TextView) view.findViewById(R.id.lvHeaders);

        listLabel.setTypeface(null , Typeface.BOLD);
        listLabel.setText(headerTitle);
        if(groupPosition == 0){
            view = setUpLabName(view);
            return view;
        }
        else if (groupPosition ==1){
            view = setUpOccupancy(view);
            return view;
        }
        else if (groupPosition ==2){
            view = setUpPrinter(view);
            return view;
        }
        else if (groupPosition ==3){
            view = setUpLabHours(view);
            return view;
        }
        else{
            view = setUpSoftware(view);
            return view;
        }
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listitem, null);
        }
        final TextView childLabel = (TextView) view.findViewById(R.id.tvLabNumber);
        childLabel.setText(childText);
        return view;

//        System.out.println("Child Text" + childText);
//        if(view == null) {
//            if (groupPosition == 0) {
//                LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                view = inflater.inflate(R.layout.listitem_header, null);
//                final TextView childLabel = (TextView) view.findViewById(R.id.tvLabHeader);
//                childLabel.setText(childText);
//
//                return view;
//            } else if (groupPosition == 1) {
//                LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                view = inflater.inflate(R.layout.listitem, null);
//                final TextView childLabel = (TextView) view.findViewById(R.id.tvLabNumber);
//                childLabel.setText(childText);
//                return view;
//            } else if (groupPosition == 2) {
//                LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                view = inflater.inflate(R.layout.listitem_printer, null);
//                final TextView childLabel = (TextView) view.findViewById(R.id.tvLabPrinter);
//                childLabel.setText(childText);
//                return view;
//            } else if (groupPosition == 3) {
//                LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                view = inflater.inflate(R.layout.listitem_hours, null);
//                final TextView childLabel = (TextView) view.findViewById(R.id.tvLabHours);
//                childLabel.setText(childText);
//                return view;
//            } else {
//                LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                view = inflater.inflate(R.layout.listitem_software, null);
//                final TextView childLabel = (TextView) view.findViewById(R.id.tvLabSoftware);
//                childLabel.setText(childText);
//                return view;
//            }
//        }
//        else{
//            return view;
 //       }
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private View setUpLabName(View view){
        view.setBackgroundColor(context.getResources().getColor(R.color.colorGeorgiaSouthernGold));
        return view;
    }
    private View setUpOccupancy(View view){

        int inUse = lab.getInUseComputers();
        int total = lab.getTotalComputers();
        int totalPreset = lab.getPresetTotalComputers();
        int difference = totalPreset - total;
        inUse = inUse + difference;
        double percentInUse = (double) inUse /totalPreset;
        if(percentInUse > .75){
            view.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
        }
        else if(percentInUse > .50){
            view.setBackgroundColor(context.getResources().getColor(R.color.colorYellow));
        }
        else{
            view.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
        }
        return view;
    }
    private View setUpPrinter(View view){
        view.setBackgroundColor(context.getResources().getColor(R.color.colorGeorgiaSouthernGrey));
        return view;
    }
    private View setUpLabHours(View view){
        view.setBackgroundColor(context.getResources().getColor(R.color.colorGeorgiaSouthernGrey));
        return view;
    }
    private View setUpSoftware(View view){
        view.setBackgroundColor(context.getResources().getColor(R.color.colorGeorgiaSouthernGrey));
        return view;
    }
//    private View setUpLabNameChild(View view){
//        return view;
//    }
//
//    private View setUpOccupancyChild(View view){
//        view.setBackgroundColor(context.getResources().getColor(R.color.colorGeorgiaSouthernGrey));
//        return view;
//    }
//    private View setUpPrinterChild(View view){
//        view.setBackgroundColor(context.getResources().getColor(R.color.colorGeorgiaSouthernGrey));
//        return view;
//    }
//    private View setUpLabHoursChild(View view){
//        view.setBackgroundColor(context.getResources().getColor(R.color.colorGeorgiaSouthernGrey));
//        return view;
//    }
//    private View setUpSoftwareChild(View view){
//        return view;
//    }
//

}