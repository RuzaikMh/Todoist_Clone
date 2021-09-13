package ruzaik.mh.todolistapp.adapter;

import ruzaik.mh.todolistapp.model.Task;

public interface OnTodoClickListener {
    void onTodoClick(int adapterPosition, Task task);
    void onTodoRadioButtonClick(Task task);
}
