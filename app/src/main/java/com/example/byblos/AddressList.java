package com.example.byblos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AddressList extends ArrayAdapter<Address> {

    private Activity context;
    List<Address> addresses;

    public AddressList(Activity context, List<Address> addresses){
        super(context, R.layout.activity_address_list, addresses);
        this.context = context;
        this.addresses = addresses;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_address_list, null, true);

        TextView serviceUser = (TextView) listViewItem.findViewById(R.id.addressName);

        Address address = addresses.get(position);
        serviceUser.setText(String.valueOf(address.getAddress()));
        return listViewItem;
    }
}