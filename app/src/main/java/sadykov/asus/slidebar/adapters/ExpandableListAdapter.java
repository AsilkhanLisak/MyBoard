package sadykov.asus.slidebar.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import sadykov.asus.slidebar.R;

/**
 * Created by Asus on 08.06.2016.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String[]> _listDataHeader; //header titles
    private HashMap<String, List<String[]>> _listDataChild;//child data in format of header title, child title

    public ExpandableListAdapter(Context context, List<String[]> listDataHeader, HashMap<String,List<String[]>> listChildData){
        this._context = context;
        this._listDataChild = listChildData;
        this._listDataHeader = listDataHeader;
    }
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)[0]).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)[0]).get(childPosition);
        //return null;
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //String headerTitle = (String) getGroup(groupPosition);
        String[] headerTitle = (String[]) getGroup(groupPosition);

        if (convertView == null){
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandable_list_group,null);
        }
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.mylbListHeader);
        TextView lblListHeaderSum = (TextView) convertView.findViewById(R.id.mylbListHeaderSum);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle[0]);
        lblListHeaderSum.setText(headerTitle[1]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String[] childText = (String[]) getChild(groupPosition,childPosition);
        if (convertView == null){
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandable_list_item,null);
        }
        TextView txtListChild = (TextView) convertView.findViewById(R.id.mylblListItem);
        TextView txtListChildSum = (TextView) convertView.findViewById(R.id.mylblListItemSum);
        txtListChild.setText(childText[0]);
        txtListChildSum.setText(childText[1]);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

























