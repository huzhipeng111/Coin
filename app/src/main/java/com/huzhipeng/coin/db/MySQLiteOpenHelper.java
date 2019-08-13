package com.huzhipeng.coin.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;

import org.greenrobot.greendao.database.Database;

/**
 * Created by huzhipeng on 2018/2/10.
 */

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
        public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }
        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {

                @Override
                public void onCreateAllTables(Database db, boolean ifNotExists) {
                    DaoMaster.createAllTables(db, ifNotExists);
                }

                @Override
                public void onDropAllTables(Database db, boolean ifExists) {
                    DaoMaster.dropAllTables(db, ifExists);
                }
            },CoinEntityDao.class, AlarmRecordDao.class);
        }
        public void deleSQL(){
            SQLiteDatabase db = getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            DaoMaster.dropAllTables(daoMaster.getDatabase(),true);
            DaoMaster.createAllTables(daoMaster.getDatabase(),true);

        }
}
