package com.companyname.asset.ui;

import xxx

public class MainActivity extends AppCompatActivity ... {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_asset);
        ...
        //新建Asset数据库对象；完成了数据库及表的创建工作
        assetDatabase = new SyncAssetDatabase(getApplicationContext());
    }

    ...
  

    public void function() {

        assetDatabase.insertAssetListData(MainActivity.this, data);
    }

    ...

}