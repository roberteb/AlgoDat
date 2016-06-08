package com.example.robert.algodat.backend.controller;

import android.app.Activity;
import android.content.Context;

import com.example.robert.algodat.backend.model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

/**
 * Created by chris on 08.06.2016.
 */
public class UserDAO {
   private String filename="User.txt";
   private User user;
   private Context context;


    public UserDAO(Context c){
        this.user=new User();
        context=c;
        File file = new File(context.getFilesDir(), filename);
        if(file.exists()){
            read();
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void createUser(String name){
        if(user==null) {
            this.user = new User();
            user.setName(name);
        }else{
            throw new IllegalArgumentException("Only one User is allowed!");
        }
    }

    public void save(){
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(this);
            os.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    public void read(){
        try {
            FileInputStream fis = context.openFileInput(filename);
            ObjectInputStream is = null;
            is = new ObjectInputStream(fis);
            User user= (User) is.readObject();
            is.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    public void incrementMadeExams(){
        user.incrementMadeExams();
        save();
    }
    public void incrementPractices(){
        user.incrementMadePractices();
        save();
    }
    public void incrementLearned(){
        user.incrementLearned();
        save();
    }
    public void increaseLevel(){
        user.setLvl(user.getLvl()+1);
        user.increaseNxtLvlXp();
        save();
    }
    public void increaseExamXp(int xp){
        user.setExamXp(user.getExamXp()+xp);
        save();
    }
    public void increasePracticeXp(int xp){
        user.setPracticeXp(user.getPracticeXp()+xp);
        save();
    }
    public void increaseLearnXp(int xp){
        user.setLearnXp(user.getLearnXp()+xp);
        save();
    }
    public int getSumXp(){
        return user.getExamXp()+user.getLearnXp()+user.getPracticeXp();
    }
    public int getUserLevel(){
        return user.getLvl();
    }

    public boolean checkLvlUp(){
        boolean leveledUp=false;
        while (getSumXp()>user.getNxtLvlXp()){
            user.setLvl(user.getLvl()+1);
            leveledUp=true;
            }
        save();
        return leveledUp;
    }
}