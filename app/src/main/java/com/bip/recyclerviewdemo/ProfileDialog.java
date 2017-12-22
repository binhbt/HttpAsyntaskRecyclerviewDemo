package com.bip.recyclerviewdemo;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bip.recyclerviewdemo.model.Student;
import com.squareup.picasso.Picasso;

/**
 * Created by t430 on 12/15/2017.
 */

public class ProfileDialog extends Dialog{
    //private Student student;
    private TextView txtName;
    private ImageView imgAvatar;
    public ProfileDialog(@NonNull Context context, Student student) {
        super(context);
        setTitle("Student profile");
        //this.student = student;
        setContentView(R.layout.dialog_detail);
        txtName = (TextView) findViewById(R.id.txt_name);
        txtName.setText(student.getId()+ " - " +student.getName());
        imgAvatar = (ImageView) findViewById(R.id.img_avatar);
        Picasso.with(getContext())
                .load(student.getAvatar()).into(imgAvatar);
        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
