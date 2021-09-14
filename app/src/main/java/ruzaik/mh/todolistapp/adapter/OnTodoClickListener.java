package ruzaik.mh.todolistapp.adapter;

import ruzaik.mh.todolistapp.model.Task;

public interface OnTodoClickListener {
    void onTodoClick(Task task);
    void onTodoRadioButtonClick(Task task);
}
