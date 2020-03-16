package com.example.etbc.Admin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.etbc.Model.User;
import com.example.etbc.R;

import java.util.List;

public class User_list_Admin extends ArrayAdapter <User> {
    private Activity context;
    private List<User> userList;
    public User_list_Admin(Activity context, List<User>userList){
        super(context, R.layout.user_list_admin,userList);
        this.context=context;
    this.userList=userList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View userview=inflater.inflate(R.layout.user_list_admin,null,true);
        CardView card= userview.findViewById(R.id.li_card);
        TextView Name= userview.findViewById(R.id.name_admin_list);
      //  TextView userid=(TextView)userview.findViewById(R.id.id_admin_list);
        TextView Email= userview.findViewById(R.id.Email_admin_list);
        TextView Phone= userview.findViewById(R.id.Phone_admin_list);
        TextView bdate= userview.findViewById(R.id.bdate_admin_list);
        TextView gender= userview.findViewById(R.id.Gender_admin_list);
        Button udate= userview.findViewById(R.id.btnUpdate);
        Button delete= userview.findViewById(R.id.btnDelete);
        User user =userList.get(position);
        Name.setText("User Name:"+user.getName());
        //userid.setText("UserID:"+user.getId());
        Email.setText("Email:\t"+user.getEmail());
        Phone.setText("Phone :\t"+user.getPhone());
        bdate.setText("Date Of Birth:\t "+user.getBdate());
        gender.setText("Gender:\t"+user.getGender());
        return userview;
    }
}
