package com.ramu.simpletodo;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTodoFragment extends Fragment{

    Switch dateSwitch;
    DatePicker datePicker;
    TimePicker timePicker;
    LinearLayout picker_llayout;
    TextView datetext;
    TextView timetext;
    Context mcontext;
    Spinner statuSpinner;
    CallMainActivity callMainActivity;
    Spinner prioritySpinner;
    EditText titleEt;
    EditText notesEt;
    String mode="new";
    TodoItemModel currentItem = null;
    public CreateTodoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        callMainActivity = (CallMainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_create_todo, container, false);
        //dateSwitch = (Switch) view.findViewById(R.id.date_switch);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        timePicker = (TimePicker) view.findViewById(R.id.timePicker);
        //picker_llayout = (LinearLayout) view.findViewById(R.id.picker_llayout);

        datetext = (TextView) view.findViewById(R.id.date_text);
        timetext = (TextView) view.findViewById(R.id.time_text);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        timePicker = (TimePicker) view.findViewById(R.id.timePicker);
        statuSpinner = (Spinner) view.findViewById(R.id.status_spinner);
        prioritySpinner = (Spinner) view.findViewById(R.id.priority_spinner);
        titleEt = (EditText) view.findViewById(R.id.title_et);
        notesEt = (EditText) view.findViewById(R.id.et_notes);

        mode = "new";
        mcontext = getContext();
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        int hours = c.get(Calendar.HOUR);
        int minutes = c.get(Calendar.MINUTE);
        int am = c.get(Calendar.AM_PM);

        Bundle bundle = this.getArguments();

        if(bundle!=null){
            currentItem = bundle.getParcelable("item");
        }
        //timePicker.setCurrentHour(hours);
        //timePicker.setCurrentMinute(minutes);

        String time_value;

        if(minutes<10)
            time_value = String.valueOf(hours)+":0"+String.valueOf(minutes);
        else
            time_value = String.valueOf(hours)+":"+String.valueOf(minutes);

        if(am == 0)
            time_value = time_value+" AM";
        else
            time_value = time_value+" PM";



        timetext.setText(time_value);

        String date_value;

        int t_month = month;
        t_month++;
        if(t_month<10)
            date_value = "0"+String.valueOf(t_month)+"/";
        else
            date_value = String.valueOf(month)+"/";
        if(day<10)
            date_value = date_value+"0"+String.valueOf(day)+"/";
        else
            date_value = date_value+String.valueOf(day)+"/";

        date_value = date_value+String.valueOf(year);

        datetext.setText(date_value);


//        picker_llayout.setVisibility(View.GONE);
//        dateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                //displayDatePicker();
//            }
//        });

        datetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerVisibility("date");
            }
        });
        timetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerVisibility("time");
            }
        });


        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(datePicker.getVisibility()==View.VISIBLE)
                    datePicker.setVisibility(View.GONE);
            }
        });

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                String month;
                String day;
                if(monthOfYear<10){
                    month = "0"+monthOfYear;
                }
                else
                    month = String.valueOf(monthOfYear);
                if(dayOfMonth<10){
                    day = "0"+dayOfMonth;
                }
                else
                    day = String.valueOf(dayOfMonth);

                datetext.setText(month+"/"+day+"/"+year);
            }
        });

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timePicker.getVisibility()==View.VISIBLE)
                    timePicker.setVisibility(View.GONE);
            }
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                String hours_str="";
                String minutes_str="";
                String AMPM = "";
                if(hourOfDay<13)
                {
                    AMPM = "AM";
                    hours_str = String.valueOf(hourOfDay);
                }
                else{
                    AMPM = "PM";
                    hourOfDay = hourOfDay-12;
                    hours_str = String.valueOf(hourOfDay);
                }

                if(minute<10)
                {
                    minutes_str = "0"+String.valueOf(minute);
                }
                else
                    minutes_str = String.valueOf(minute);

                timetext.setText(hours_str+":"+minutes_str+" "+AMPM);
            }
        });
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> priority_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.priority_level, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        priority_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        prioritySpinner.setAdapter(priority_adapter);


        ArrayAdapter<CharSequence> status_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.status_level, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        status_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        statuSpinner.setAdapter(status_adapter);



        if(currentItem!=null){
            mode = "update";
            titleEt.setText(currentItem.getTitle());
            notesEt.setText(currentItem.getNote());
            datetext.setText(currentItem.getDate());
            timetext.setText(currentItem.getTime());
            //prioritySpinner.getSelectedItem().toString();


            for (int position = 0; position < status_adapter.getCount(); position++) {
                if(status_adapter.getItem(position).equals(currentItem.getStatus())) {
                    statuSpinner.setSelection(position);
                }
            }

            for (int position = 0; position < priority_adapter.getCount(); position++) {
                if(priority_adapter.getItem(position).equals(currentItem.getPriority())) {
                    prioritySpinner.setSelection(position);
                }
            }

            //String status = statuSpinner.getSelectedItem().toString();
        }

        return view;
    }

    private void pickerVisibility(String type){
        if(type.equals("date"))
        {
            if(timePicker.getVisibility() == View.VISIBLE)
                timePicker.setVisibility(View.GONE);

            if(datePicker.getVisibility() == View.VISIBLE)
                datePicker.setVisibility(View.GONE);
            else
                datePicker.setVisibility(View.VISIBLE);
        }
        else{
            if(datePicker.getVisibility() == View.VISIBLE)
                datePicker.setVisibility(View.GONE);

            if(timePicker.getVisibility() == View.VISIBLE)
                timePicker.setVisibility(View.GONE);
            else
                timePicker.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // super.onCreateOptionsMenu(menu, inflater);

//        if(menu!=null)
//        {
//            menu.findItem(R.id.menu_create).setVisible(false);
//        }
        inflater.inflate(R.menu.fragment_todo_menu,menu);
        if(mode.equals("new")){
          menu.findItem(R.id.frag_delete).setVisible(false);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.frag_save:
                //Toast.makeText(mcontext,"Save clicked!!",Toast.LENGTH_LONG).show();
                saveToDatabase();
                break;
            case R.id.frag_close:
               // Toast.makeText(mcontext,"Close clicked!!",Toast.LENGTH_LONG).show();
                closeFragment();
                break;
            case R.id.frag_delete:
                showDeleteWarning();
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void deleteFromDataBase() {
        if(mode.equals("update"))
            callMainActivity.deleteItem(currentItem.getId());
    }

    private void closeFragment() {

        getFragmentManager().beginTransaction().remove(this).commit();
        getFragmentManager().popBackStack();
        callMainActivity.showFabButton(true);
    }

    private void saveToDatabase() {

            String title = titleEt.getText().toString();
            String notes = notesEt.getText().toString();
            String date = datetext.getText().toString();
            String time = timetext.getText().toString();
            String priority = prioritySpinner.getSelectedItem().toString();
            String status = statuSpinner.getSelectedItem().toString();

            TodoItemModel todoItem = new TodoItemModel();
            todoItem.setTitle(title); todoItem.setTime(time);   todoItem.setDate(date);
            todoItem.setNote(notes); todoItem.setPriority(priority); todoItem.setStatus(status);

            if(mode.equals("update"))
            {
                todoItem.setId(currentItem.getId());
            }
            callMainActivity.saveToDataBase(todoItem,mode);

            closeFragment();
    }


    public void showDeleteWarning(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        // Add the buttons
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteFromDataBase();
                dialog.dismiss();
                closeFragment();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.dismiss();
            }
        });
        builder.setTitle("Delete");
        builder.setMessage("Are you sure, want to delete the item?");

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    public interface CallMainActivity{
         void saveToDataBase(TodoItemModel itemModel, String m_mode);
         void deleteItem(int id);
         void showFabButton(boolean view);
    }


}
