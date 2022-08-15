package com.yjxxt.service;



import com.yjxxt.pojo.NoteType;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 云记类别管理
 *    云记类别遍历
 *    云记类别添加
 *    云记类别更新
 *    云记类别删除
 */
public class NoteTypeService {

    private Map<Integer, NoteType> noteTypeMap;


    public NoteTypeService() {
        noteTypeMap= new HashMap<Integer, NoteType>();
        noteTypeMap.put(1,new NoteType(1,"java",1));
        noteTypeMap.put(2,new NoteType(2,"php",1));
        noteTypeMap.put(3,new NoteType(3,"scala",2));
    }

    public void addNoteType(List<Integer> list,NoteType noteType){
        /**
         * 1.参数校验
         *    类别名 不能为空 用户id 必须存在(UserService->List<User> 必须存在对应用户记录)
         * 2.当前用户下类别名称不可重复
         * 3.执行添加
         */
        if (StringUtils.isBlank(noteType.getTypeName())){
            throw new RuntimeException("类别名不能为空！");
        }

        Set<Map.Entry<Integer, NoteType>> entries = noteTypeMap.entrySet();
        for (Map.Entry<Integer, NoteType> entry : entries) {
            if (entry.getValue().getTypeName().equals(noteType.getTypeName())){
                throw new RuntimeException("类别名已存在！");
            }
        }
        if (null == noteType.getUserId()){
            throw new RuntimeException("用户Id不能为空！");
        }
        if (!(list.contains(noteType.getUserId()))) {
            throw new RuntimeException("用户Id不合法！");
        }
        noteTypeMap.put(noteType.getId(),noteType);

    }

    /**
     * 根据登录用户查询云记类别
     * @param userId
     */
    public void listNoteType(Integer userId){
        if (null == userId){
            throw new RuntimeException("用户未登录！");
        }
        List<String> list = new ArrayList<>();
        Set<Map.Entry<Integer, NoteType>> entries = noteTypeMap.entrySet();
        for (Map.Entry<Integer, NoteType> entry : entries) {
            if (entry.getValue().getUserId() == userId){
                list.add(entry.getValue().getTypeName());
            }
        }
        list.forEach(System.out::println);
    }


    public void updateNoteType(List<Integer> list,NoteType noteType){
        /**
         * 1.参数校验
         *    类别名 不能为空
         *    用户id 必须存在(UserService->List<User> 必须存在对应用户记录)
         *    云记类别id 必须存在
         * 2.当前用户下类别名称不可重复
         * 3.执行更新
         */
        if (StringUtils.isBlank(noteType.getTypeName())){
            throw new RuntimeException("类别名不能为空！");
        }

        Set<Map.Entry<Integer, NoteType>> entries = noteTypeMap.entrySet();
        for (Map.Entry<Integer, NoteType> entry : entries) {
            if (noteType.getId() == null || queryNoteTypeById(noteType.getId()) == null){
                throw new RuntimeException("待更新记录不存在！");
            }
            if (entry.getValue().getTypeName().equals(noteType.getTypeName())){
                if (noteType.getId() != queryNoteTypeIdByName(entry.getValue().getTypeName())){
                    throw new RuntimeException("类别名已存在！");
                }

            }
        }
        if (null == noteType.getUserId()){
            throw new RuntimeException("用户Id不能为空！");
        }
        if (!(list.contains(noteType.getUserId()))) {
            throw new RuntimeException("用户Id不合法！");
        }
        noteTypeMap.replace(noteType.getId(),noteType);
    }

    private Integer queryNoteTypeIdByName(String typeName) {
        for (Map.Entry<Integer, NoteType> entry : noteTypeMap.entrySet()) {
            if (entry.getValue().getTypeName() ==typeName) {
                return entry.getValue().getId();
            }
        }
        return null;
    }

    private NoteType queryNoteTypeById(Integer id) {
        NoteType noteType = null;
        for (Map.Entry<Integer, NoteType> entry : noteTypeMap.entrySet()) {
            if (entry.getValue().getId() == id){
                return entry.getValue();
            }
        }
        return noteType;

    }

    public void delNoteType(Integer noteTypeId){
        /**
         * 1.参数校验
         *    云记类别id 必须存在
         * 2.如果类别下存在云记记录 不允许删除
         * 3.执行删除
         */
        if (null == noteTypeId || null == queryNoteTypeById(noteTypeId)){
            throw new RuntimeException("待删除记录不存在！");
        }

        noteTypeMap.remove(noteTypeId);
    }


}
