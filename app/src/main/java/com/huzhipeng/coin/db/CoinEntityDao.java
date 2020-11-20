package com.huzhipeng.coin.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COIN_ENTITY".
*/
public class CoinEntityDao extends AbstractDao<CoinEntity, Long> {

    public static final String TABLENAME = "COIN_ENTITY";

    /**
     * Properties of entity CoinEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Symbol = new Property(1, String.class, "symbol", false, "SYMBOL");
        public final static Property CoinType = new Property(2, int.class, "coinType", false, "COIN_TYPE");
        public final static Property UnIngnoreTime = new Property(3, long.class, "unIngnoreTime", false, "UN_INGNORE_TIME");
        public final static Property InIgnore = new Property(4, boolean.class, "inIgnore", false, "IN_IGNORE");
        public final static Property Decimal = new Property(5, int.class, "decimal", false, "DECIMAL");
        public final static Property Amount = new Property(6, float.class, "amount", false, "AMOUNT");
        public final static Property HighPrice = new Property(7, float.class, "highPrice", false, "HIGH_PRICE");
        public final static Property LowPrice = new Property(8, float.class, "lowPrice", false, "LOW_PRICE");
    }


    public CoinEntityDao(DaoConfig config) {
        super(config);
    }
    
    public CoinEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COIN_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"SYMBOL\" TEXT," + // 1: symbol
                "\"COIN_TYPE\" INTEGER NOT NULL ," + // 2: coinType
                "\"UN_INGNORE_TIME\" INTEGER NOT NULL ," + // 3: unIngnoreTime
                "\"IN_IGNORE\" INTEGER NOT NULL ," + // 4: inIgnore
                "\"DECIMAL\" INTEGER NOT NULL ," + // 5: decimal
                "\"AMOUNT\" REAL NOT NULL ," + // 6: amount
                "\"HIGH_PRICE\" REAL NOT NULL ," + // 7: highPrice
                "\"LOW_PRICE\" REAL NOT NULL );"); // 8: lowPrice
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COIN_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CoinEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String symbol = entity.getSymbol();
        if (symbol != null) {
            stmt.bindString(2, symbol);
        }
        stmt.bindLong(3, entity.getCoinType());
        stmt.bindLong(4, entity.getUnIngnoreTime());
        stmt.bindLong(5, entity.getInIgnore() ? 1L: 0L);
        stmt.bindLong(6, entity.getDecimal());
        stmt.bindDouble(7, entity.getAmount());
        stmt.bindDouble(8, entity.getHighPrice());
        stmt.bindDouble(9, entity.getLowPrice());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CoinEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String symbol = entity.getSymbol();
        if (symbol != null) {
            stmt.bindString(2, symbol);
        }
        stmt.bindLong(3, entity.getCoinType());
        stmt.bindLong(4, entity.getUnIngnoreTime());
        stmt.bindLong(5, entity.getInIgnore() ? 1L: 0L);
        stmt.bindLong(6, entity.getDecimal());
        stmt.bindDouble(7, entity.getAmount());
        stmt.bindDouble(8, entity.getHighPrice());
        stmt.bindDouble(9, entity.getLowPrice());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CoinEntity readEntity(Cursor cursor, int offset) {
        CoinEntity entity = new CoinEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // symbol
            cursor.getInt(offset + 2), // coinType
            cursor.getLong(offset + 3), // unIngnoreTime
            cursor.getShort(offset + 4) != 0, // inIgnore
            cursor.getInt(offset + 5), // decimal
            cursor.getFloat(offset + 6), // amount
            cursor.getFloat(offset + 7), // highPrice
            cursor.getFloat(offset + 8) // lowPrice
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CoinEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSymbol(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCoinType(cursor.getInt(offset + 2));
        entity.setUnIngnoreTime(cursor.getLong(offset + 3));
        entity.setInIgnore(cursor.getShort(offset + 4) != 0);
        entity.setDecimal(cursor.getInt(offset + 5));
        entity.setAmount(cursor.getFloat(offset + 6));
        entity.setHighPrice(cursor.getFloat(offset + 7));
        entity.setLowPrice(cursor.getFloat(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CoinEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CoinEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CoinEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
