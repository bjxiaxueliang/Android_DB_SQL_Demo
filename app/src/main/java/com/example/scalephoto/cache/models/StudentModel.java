package com.example.scalephoto.cache.models;

import java.io.Serializable;

/**
 * @author xiaxueliang
 */
public class StudentModel implements Serializable {
    private static final long serialVersionUID = 1L;

    public String name;        //string	姓名
    public Long sid;        //Long	学号
    public int age;            //int	年龄
    public int gender;        //int   性别
    public float score;        //float	分数



    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("name: ");
        sb.append(name);
        sb.append(" sid: ");
        sb.append(sid);
        sb.append(" age: ");
        sb.append(age);
        sb.append(" gender: ");
        sb.append(gender);
        sb.append(" score: ");
        sb.append(score);
        return sb.toString();
    }
}
