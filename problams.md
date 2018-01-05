# problems

> has done 

## SQLite table do not exist

数据库经建立后，就不会再执行oncreate方法了，数据库已经建立，然后把数据库表名改了，所以不存在该表；

尝试方法：
- 删除手机上的SQLite db；无法Root；不可行；
- 模拟器上删除SQLite db；未尝试；
- 写删除数据库的方法；暂时把上传按钮做成删除库功能；可行。



## com.google.gson.JsonSyntaxException: java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 59 path $.result

{"code":0,"message":"","time":null,"token":null,"result":{"totalCount":null,"pageNum":1,"pageSize":10,"pageCount":150,"items":[{"CZZ029":1514563200000,"ID":29

错误原因：
ResultBean<PageBean<Asset>> resultBean = AppContext.createGson().fromJson(responseString, new TypeToken<ResultBean<PageBean<Asset>>>() {}.getType());

PageBean不能写成List，List不是对象；这里必须用对象；


## android.database.sqlite.SQLiteException: no such table: asset (code 1): , while compiling: insert into asset(assetCode, assetName, assetStatus) values(?, ?, ?)

-解决方法：统一新建数据库表

```
//建语句只执行一次，如果后来需要加数据库表或者改结构，需要删除数据库，重新运行
db.execSQL(CREATE_ASSET_TABLE_USER);
db.execSQL(CREATE_ASSET_TABLES_ASSET);
```


## 拼接sql时注意对应、空格

- 对应不上

```
update czz003 set czz004=?,czz018=?, =? where id=?
```

- where左右的 空格

注意添加空格


## SQLite count 总是返回1

/**
 * [SQLite 计数功能代码块]
 * @param  String where         [查询条件]
 * @return {[int]}        [数量]
 */
public int count(String where) {
    SQLiteDatabase sqlite = sqLiteHelper4UserInfo.getReadableDatabase();
    int count;
    String sql = "select count(*) as count from " + CreateDatabaseAndTables.assetTablesNames[1] + " " + where;
    Cursor cursor = sqlite.rawQuery(sql, null);
    //注意下面两行
    cursor.moveToFirst();
    count = cursor.getInt(0);
    if (!cursor.isClosed()) {
        cursor.close();
    }
    return count;
} 


====== has done end ===== 


> todo 

## Error reading /data/anr/traces.txt
    
    java.io.FileNotFoundException: /data/anr/traces.txt: open failed: ENOENT (No such file or directory)

- 原因分析：

可能是死锁；写之前已经有写操作了；

可尝试 事务控制；

下载接口调通；本地存储也可以； 
