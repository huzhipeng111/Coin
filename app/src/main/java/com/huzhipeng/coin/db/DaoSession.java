package com.huzhipeng.coin.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.huzhipeng.coin.db.AlarmRecord;
import com.huzhipeng.coin.db.CoinEntity;
import com.huzhipeng.coin.db.TickerDb;

import com.huzhipeng.coin.db.AlarmRecordDao;
import com.huzhipeng.coin.db.CoinEntityDao;
import com.huzhipeng.coin.db.TickerDbDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig alarmRecordDaoConfig;
    private final DaoConfig coinEntityDaoConfig;
    private final DaoConfig tickerDbDaoConfig;

    private final AlarmRecordDao alarmRecordDao;
    private final CoinEntityDao coinEntityDao;
    private final TickerDbDao tickerDbDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        alarmRecordDaoConfig = daoConfigMap.get(AlarmRecordDao.class).clone();
        alarmRecordDaoConfig.initIdentityScope(type);

        coinEntityDaoConfig = daoConfigMap.get(CoinEntityDao.class).clone();
        coinEntityDaoConfig.initIdentityScope(type);

        tickerDbDaoConfig = daoConfigMap.get(TickerDbDao.class).clone();
        tickerDbDaoConfig.initIdentityScope(type);

        alarmRecordDao = new AlarmRecordDao(alarmRecordDaoConfig, this);
        coinEntityDao = new CoinEntityDao(coinEntityDaoConfig, this);
        tickerDbDao = new TickerDbDao(tickerDbDaoConfig, this);

        registerDao(AlarmRecord.class, alarmRecordDao);
        registerDao(CoinEntity.class, coinEntityDao);
        registerDao(TickerDb.class, tickerDbDao);
    }
    
    public void clear() {
        alarmRecordDaoConfig.clearIdentityScope();
        coinEntityDaoConfig.clearIdentityScope();
        tickerDbDaoConfig.clearIdentityScope();
    }

    public AlarmRecordDao getAlarmRecordDao() {
        return alarmRecordDao;
    }

    public CoinEntityDao getCoinEntityDao() {
        return coinEntityDao;
    }

    public TickerDbDao getTickerDbDao() {
        return tickerDbDao;
    }

}
