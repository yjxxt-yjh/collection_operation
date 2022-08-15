package com.yjxxt.service;

import com.yjxxt.pojo.NoteType;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class NoteTypeServiceTest {
    private NoteTypeService noteTypeService;
    private UserService userService;
    @Before
    public void init(){
        noteTypeService = new NoteTypeService();
        userService = new UserService();
    }

    @Test
    public void addNoteType(){
        List<Integer> list = userService.queryAllId();
        noteTypeService.addNoteType(list,new NoteType(4,"yjh",2));
    }

    @Test
    public void listNoteType(){
        noteTypeService.listNoteType(1);
    }

    @Test
    public void updateNoteType(){
        List<Integer> list = userService.queryAllId();
        noteTypeService.updateNoteType(list,new NoteType(1,"yjh",1));
    }




}
