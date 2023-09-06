package com.example.projcopy.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projcopy.Model.Comments;
import com.example.projcopy.Model.Users;
import com.example.projcopy.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private Activity context;

    private List<Users>usersList;
    private List<Comments> commentsList;

    public CommentsAdapter(Activity context, List<Comments> commentsList,List <Users> usersList){
        this.context=context;
        this.commentsList=commentsList;
        this.usersList=usersList;

    }
    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.each_comment,parent,false);
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        Comments comments=commentsList.get(position);
        holder.setmComment(comments.getComment());

        Users users=usersList.get(position);
//        holder.setmUserName(users.getName());
//        holder.setCircleImageView(users.getUrl());

    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder{
        TextView mComment,mUserName;
        CircleImageView circleImageView;
        View mview;


        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            mview=itemView;
        }
        public void setmComment(String comment){
            mComment=mview.findViewById(R.id.comment_tv);
            mComment.setText(comment);
        }
//        public void setmUserName(String userName){
//            mUserName=mview.findViewById(R.id.comment_user);
//            mUserName.setText(userName);
//        }
//        public void setCircleImageView(String profilePic){
//            circleImageView=mview.findViewById(R.id.comment_Profile_pic);
//            Glide.with(context).load(profilePic).into(circleImageView);
//        }
    }
}
