package net.mantucon.baracus;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import net.mantucon.baracus.application.ApplicationContext;
import net.mantucon.baracus.model.BankAccount;
import net.mantucon.baracus.model.Customer;
import net.mantucon.baracus.signalling.DataChangeAwareComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * List Adapter Class for displaying accounts
 */
public class AccountExpandListAdapter extends BaseExpandableListAdapter {

    private Activity context;       // we need this one to inflate the detail rows
    private List<Customer> groups;  // customer master rows

    public AccountExpandListAdapter(Activity context, List<Customer> groups) {
        this.context = context;
        this.groups = groups;
    }

    /**
     * @param groupPosition - the customer row index
     * @param childPosition - the account row index
     * @return child object from group x, child y
     */
    public Object getChild(int groupPosition, int childPosition) {
        List<BankAccount> entries = groups.get(groupPosition).getAccounts();
        ArrayList<BankAccount> chList = new ArrayList<BankAccount>(entries);
        return chList.get(childPosition);
    }

    /**
     * @param groupPosition
     * @param childPosition
     * @return child Id which is equal to our position
     */
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * create the child view for a child on a certain position
     *
     * @param groupPosition - the customer row index
     * @param childPosition - the account row index
     * @param isLastChild
     * @param view - the view (the child view, can be null)
     * @param parent - the parent view (the customer)
     * @return the view
     */
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        final BankAccount child = (BankAccount) getChild(groupPosition, childPosition);

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.entries_list_child_item, null);
        }
        final TextView entryName = (TextView) view.findViewById(R.id.entryName);
        entryName.setText(child.getBankName().toString());


        final TextView entryValue = (TextView) view.findViewById(R.id.entryAccount);
        entryValue.setText(child.getIban());

        return view;
    }

    public int getChildrenCount(int groupPosition) {
        ArrayList<BankAccount> chList = new ArrayList<BankAccount>(groups.get(groupPosition).getAccounts());

        return chList.size();

    }

    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    public int getGroupCount() {
        return groups.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * creat the customer view
     * @param groupPosition
     * @param isLastChild
     * @param view
     * @param parent
     * @return the customer view
     */
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {
        Customer group = (Customer) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.entries_list_group_item, null);
        }
        TextView tv = (TextView) view.findViewById(R.id.tvGroup);
        tv.setText(group.getLastName()+", "+group.getFirstName());
        return view;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

}



