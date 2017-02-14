package com.ramu.simpletodo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ramu on 2/12/2017.
 */


public class TodoItemModel implements Parcelable {

    int id;
    String title = "";
    String date = "";
    String time = "";
    String priority = "";
    String note = "";
    String status = "";

    public TodoItemModel() {
    }

    public TodoItemModel(int id, String title, String date, String time, String priority, String note, String status) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.priority = priority;
        this.note = note;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    protected TodoItemModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        date = in.readString();
        time = in.readString();
        priority = in.readString();
        note = in.readString();
        status = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(priority);
        dest.writeString(note);
        dest.writeString(status);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TodoItemModel> CREATOR = new Parcelable.Creator<TodoItemModel>() {
        @Override
        public TodoItemModel createFromParcel(Parcel in) {
            return new TodoItemModel(in);
        }

        @Override
        public TodoItemModel[] newArray(int size) {
            return new TodoItemModel[size];
        }
    };

}
/*
public class TodoItemModel{

    int id;
    String title="";
    String date="";
    String time="";
    String priority="";
    String note="";
    String status="";

    public TodoItemModel(){
    }

    public TodoItemModel(int id,String title, String date, String time, String priority, String note, String status) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.priority = priority;
        this.note = note;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
*/