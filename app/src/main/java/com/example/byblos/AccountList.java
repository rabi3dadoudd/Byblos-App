package com.example.byblos;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AccountList extends ArrayAdapter<Account> {
    private Activity context;
    List<Account> accounts;

    public AccountList(Activity context, List<Account> accounts){
        super(context, R.layout.activity_account_list, accounts);
        this.context = context;
        this.accounts = accounts;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_account_list, null, true);

        TextView accountUser = (TextView) listViewItem.findViewById(R.id.accountusername);

        Account account = accounts.get(position);
        accountUser.setText(String.valueOf(account.getUsername()));
        return listViewItem;
    }
}