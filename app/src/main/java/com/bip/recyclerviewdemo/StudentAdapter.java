package com.bip.recyclerviewdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bip.recyclerviewdemo.model.Student;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by t430 on 12/8/2017.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder>{
    private List<Student> listData;
    public StudentAdapter(List<Student> listData){
        this.listData = listData;
    }
    //Tao View holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //Do du lieu len View Holder
    @Override
    public void onBindViewHolder(final ViewHolder holder, int positon) {
        //holder tai vi tri position
        final Student student = listData.get(positon);
        holder.txtName.setText(student.getName());
        Picasso.with(holder.txtName.getContext())
                .load(student.getAvatar()).into(holder.imgAvatar);
        holder.imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(holder.imgAvatar.getContext(),
//                        "click vao sinh vien "+student.getName(),
//                        Toast.LENGTH_LONG).show();
                ProfileDialog profileDialog = new ProfileDialog(view.getContext(), student);
                profileDialog.show();

            }
        });
    }

    //Cho biet so luong phan tu cua list
    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtName;
        ImageView imgAvatar;
        public ViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txt_name);
            imgAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
        }
    }

}
