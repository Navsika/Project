package com.example.experiment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.experiment.AddNewTask;
import com.example.experiment.MainActivity;
import com.example.experiment.Model.ToDoModel;
import com.example.experiment.NewTaskFragment;
import com.example.experiment.R;
import com.example.experiment.TimerActivity;
import com.example.experiment.Utils.OpenHelper;
import com.example.experiment.Utils.OpenHelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoModel> todoList;
    private OpenHelper db;
    private MainActivity activity;
    private Context mContext;


    public ToDoAdapter(OpenHelper db, MainActivity activity, Context context) {
        this.db = db;
        this.activity = activity;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        db.openDatabase();

        final ToDoModel item = todoList.get(position);
        holder.textOfTask.setText(item.getTask());
        //holder.textOfDescription.setText(item.getDescription());
        holder.checkBox.setChecked(toBoolean(item.getStatus()));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    db.updateStatus(item.getId(), 1);
                } else {
                    db.updateStatus(item.getId(), 0);
                }
            }
        });

       // holder.parentLayout.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        Intent intent = new Intent(mContext, TimerActivity.class);
         //      mContext.startActivity(intent);
         //   }
      // });
    }

    private boolean toBoolean(int n) {
        return n != 0;
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public Context getContext() {
        return activity;
    }

    public void setTasks(List<ToDoModel> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        ToDoModel item = todoList.get(position);
        db.deleteTask(item.getId());
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position) {
        ToDoModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
       // bundle.putString("description", item.getDescription());
           AddNewTask fragment = new AddNewTask();
           fragment.setArguments(bundle);
          fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
        //NewTaskFragment fragment = new NewTaskFragment();
        //FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction().add(R.id.container_fragment, fragment);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textOfTask; //, textOfDescription;
        CheckBox checkBox;
        RelativeLayout parentLayout;
        Button startTimer;
        Context context;



        ViewHolder(View view) {
            super(view);
          checkBox = view.findViewById(R.id.todoCheckBox);
          parentLayout = view.findViewById(R.id.parent_layout);
          startTimer = view.findViewById(R.id.start_timer);
          textOfTask = view.findViewById(R.id.text_task);
         // textOfDescription = view.findViewById(R.id.text_description);
          context = view.getContext();

            startTimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(view.getContext(), TimerActivity.class);
                    context.startActivity(intent);
                }
            });





        }
    }
}

