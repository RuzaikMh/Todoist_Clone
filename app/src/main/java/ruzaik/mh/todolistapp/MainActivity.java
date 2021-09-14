package ruzaik.mh.todolistapp;

import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ruzaik.mh.todolistapp.adapter.OnTodoClickListener;
import ruzaik.mh.todolistapp.adapter.RecylerViewAdapter;
import ruzaik.mh.todolistapp.databinding.ActivityMainBinding;
import ruzaik.mh.todolistapp.model.Task;
import ruzaik.mh.todolistapp.model.TaskViewModel;
import ruzaik.mh.todolistapp.model.SharedViewModel;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements OnTodoClickListener {

    private static final String TAG = "main";
    private TaskViewModel taskViewModel;
    private RecyclerView recyclerView;
    private RecylerViewAdapter recylerViewAdapter;
    private int counter;
    BottomSheetFragment bottomSheetFragment;
    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ruzaik.mh.todolistapp.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        counter = 0;

        bottomSheetFragment = new BottomSheetFragment();
        ConstraintLayout constraintLayout = findViewById(R.id.bottomSheet);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskViewModel = new ViewModelProvider.AndroidViewModelFactory(
                MainActivity.this.getApplication())
                .create(TaskViewModel.class);

        sharedViewModel = new ViewModelProvider(this)
                .get(SharedViewModel.class);


        taskViewModel.getAllTasks().observe(this, tasks -> {
            recylerViewAdapter = new RecylerViewAdapter(tasks, this);
            recyclerView.setAdapter(recylerViewAdapter);
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Task task = new Task("Task " + counter++, Priority.MEDIUM, Calendar.getInstance().getTime(),
//                        Calendar.getInstance().getTime(), false);
//
//                TaskViewModel.insert(task);

                showBottomSheetDialog();
            }
        });
    }

    private void showBottomSheetDialog() {
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTodoClick(Task task) {
        sharedViewModel.setSelectItem(task);
        sharedViewModel.setIsEdit(true);
        showBottomSheetDialog();
    }

    @Override
    public void onTodoRadioButtonClick(Task task) {
        Log.d(TAG, "onTodoClick: " + task.getTask());
        TaskViewModel.delete(task);
        recylerViewAdapter.notifyDataSetChanged();
    }
}