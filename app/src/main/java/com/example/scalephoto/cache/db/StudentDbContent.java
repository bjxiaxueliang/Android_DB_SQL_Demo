package com.example.scalephoto.cache.db;

public class StudentDbContent {

    /**
     * 数据库名称
     */
    public static final String DATABASE_NAME = "student.db";

    /**
     * 数据库版本
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * @author
     */
    public static class StudentTable {
        /**
         * 表名
         */
        public static final String TABLE_NAME = "student_tb";
        //string	姓名
        public static final String NAME = "name";
        //Long	学号
        public static final String SID = "sid";
        //int	年龄
        public static final String AGE = "age";
        //int   性别
        public static final String GENDER = "gender";
        //float	分数
        public static final String SCORE = "score";

    }
}
