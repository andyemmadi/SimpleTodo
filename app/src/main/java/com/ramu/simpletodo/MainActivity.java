package com.ramu.simpletodo;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;


// linke for  http://tutorialsbuzz.com/2016/12/android-swipe-recyclerview-items-using-itemtouchhelper.html

public class MainActivity extends AppCompatActivity implements CreateTodoFragment.CallMainActivity {

    RecyclerView recyclerView;
    DatabaseHandler dataBasehandler;
    RecylerItemAdapter recyclerAdapter;
    CreateTodoFragment createTodoFragment;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        dataBasehandler = new DatabaseHandler(this);
        recyclerAdapter = new RecylerItemAdapter(this,dataBasehandler);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFragmentCreateTodo(-1);
            }
        });
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.hasFixedSize();
        recyclerAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

   // @Override
  //  public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.menu_create:
//                callFragmentCreateTodo(-1);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
    //}


    public void callFragmentCreateTodo(int position) {
        showFabButton(false);
        if(position==-1)
        {

            //Toast.makeText(this,"callFragmentCreateTodo....",Toast.LENGTH_LONG).show();
            createTodoFragment = new CreateTodoFragment();
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.add(R.id.activity_main,createTodoFragment,"todo_fragment");
            trans.addToBackStack("todo_fragment");
            Log.e("todo_fragment","created");
            trans.commit();
        }
        else{
            //Toast.makeText(this,"callFragmentCreateTodo....",Toast.LENGTH_LONG).show();
            createTodoFragment = new CreateTodoFragment();
            TodoItemModel itemModel = recyclerAdapter.mtodoItemModelList.get(position);

            Bundle bundle = new Bundle();
            bundle.putParcelable("item",itemModel);
            createTodoFragment.setArguments(bundle);

            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.add(R.id.activity_main,createTodoFragment,"todo_fragment");
            trans.addToBackStack("todo_fragment");
            Log.e("todo_fragment","created");
            trans.commit();
        }
    }





    // interface
    @Override
    public void saveToDataBase(TodoItemModel itemModel,String mode) {
       int return_value=-4;
        List<TodoItemModel> list = dataBasehandler.getAllTodoItems();
        if(mode.equals("new"))
        {
            dataBasehandler.addTodoItem(itemModel);
        }
        else if(mode.equals("update"))
        {
            return_value = dataBasehandler.updateTodoItem(itemModel);
            Log.e("return value",String.valueOf(return_value));
        }
        recyclerAdapter.refreshItems();
    }

    @Override
    public void deleteItem(int id) {
        dataBasehandler.deleteTodoItem(id);
        recyclerAdapter.refreshItems();
    }

    @Override
    public void showFabButton(boolean view) {
        if(view == true)
            fab.show();
        else
            fab.hide();
    }

    @Override
    public void onBackPressed() {
        //   super.onBackPressed();
        Log.e("onBackPressed","pop backstack");
        showFabButton(true);
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();

        } else {
            super.onBackPressed();
        }
    }
}
