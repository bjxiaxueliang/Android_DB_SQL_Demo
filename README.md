##Android SQLite基本使用


###SQLite的数据类型

+ INTEGER – 整数，对应Java 的byte、short、int 和long
+ TEXT/VARCHAR – 字符串文本，相当于java中的String
+ REAL – 浮点数字，相当于java中的float/double
+ BLOB – 二进制对象，相当于java中的byte数组，可用于存放图片
注：sql中不区分大小写

###Sqlite中的约束
约束就是限定数据库字段的条件，可对表里的结构和字段进行约束限定，将约束条件放在需要约束的字段之后

+ NOT NULL 非空
+ UNIQUE 唯一
+ PRIMARY 主键
+ CHECK 条件检查
+ DEFAULT 默认

###SQL DML 和 DDL
可以把 SQL 分为两个部分：**数据操作语言 (DML) 和 数据定义语言 (DDL)**。 

####数据操作语言 (DML) 

+ SELECT - 从数据库表中获取数据
+ UPDATE - 更新数据库表中的数据
+ DELETE - 从数据库表中删除数据
+ INSERT INTO - 向数据库表中插入数据

####数据定义语言 (DDL)

+ CREATE TABLE - 创建新表
+ DROP TABLE - 删除表
+ EATE DATABASE - 创建新数据库
+ ALTER DATABASE - 修改数据库
+ ALTER TABLE - 变更（改变）数据库表
+ CREATE INDEX - 创建索引（搜索键）
+ DROP INDEX - 删除索引

###举例

以此学生成绩表 **student_tb** 举例
| name | sid | age | gender | score |
| :-------- | --------:| :--: |
| 123 | 1 | 7 | 0 | 90 |
| 456 | 2 | 8 | 0 | 80 |
| 789 | 3 | 9 | 0 | 70 |

####创建表
```sql
CREATE TABLE IF NOT EXISTS student_tb (_id integer primary key autoincrement,name varchar(255),sid INTEGER,age INTEGER,gender INTEGER,score REAL);
```
####插入数据

语法：
```sql
INSERT INTO 表名称 VALUES (值1, 值2,....)
INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)
```
举例：
```sql
//
INSERT INTO student_tb VALUES ('123', 1, 7, 0, 90.0);
//
INSERT INTO student_tb (name,sid,age,gender,score) VALUES ('123', 1, 7, 0, 90.0);
```

####查找数据

语法：

```sql
SELECT 列名称 FROM 表名称 WHERE 列 运算符 值 ORDER BY 列名称 DESC
```
举例：

```sql
//
SELECT * from student_tb ORDER BY sid DESC;
//
SELECT name,sid,age,gender,score from student_tb where score>=80.0 and age<=10 ORDER BY sid DESC;
```

####更新数据
语法：
```sql
UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值新某一行中的一个列
```
举例：
```sql
UPDATE student_tb SET score=80.0 WHERE name='789';
```

####删除数据
语法：
```sql
DELETE FROM 表名 WHERE 列名=值
```
举例：
```sql
DELETE FROM student_tb where score<=70.0;
```
