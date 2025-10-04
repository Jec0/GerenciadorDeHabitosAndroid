package com.example.gerenciadorhabitos.data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.gerenciadorhabitos.model.Habit;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class HabitDao_Impl implements HabitDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Habit> __insertionAdapterOfHabit;

  private final EntityDeletionOrUpdateAdapter<Habit> __deletionAdapterOfHabit;

  private final EntityDeletionOrUpdateAdapter<Habit> __updateAdapterOfHabit;

  public HabitDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHabit = new EntityInsertionAdapter<Habit>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `habits` (`id`,`profileId`,`name`,`frequency`,`goal`,`notify`,`completedToday`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Habit entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.profileId);
        if (entity.name == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.name);
        }
        if (entity.frequency == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.frequency);
        }
        if (entity.goal == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.goal);
        }
        final int _tmp = entity.notify ? 1 : 0;
        statement.bindLong(6, _tmp);
        final int _tmp_1 = entity.completedToday ? 1 : 0;
        statement.bindLong(7, _tmp_1);
      }
    };
    this.__deletionAdapterOfHabit = new EntityDeletionOrUpdateAdapter<Habit>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `habits` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Habit entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfHabit = new EntityDeletionOrUpdateAdapter<Habit>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `habits` SET `id` = ?,`profileId` = ?,`name` = ?,`frequency` = ?,`goal` = ?,`notify` = ?,`completedToday` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Habit entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.profileId);
        if (entity.name == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.name);
        }
        if (entity.frequency == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.frequency);
        }
        if (entity.goal == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.goal);
        }
        final int _tmp = entity.notify ? 1 : 0;
        statement.bindLong(6, _tmp);
        final int _tmp_1 = entity.completedToday ? 1 : 0;
        statement.bindLong(7, _tmp_1);
        statement.bindLong(8, entity.id);
      }
    };
  }

  @Override
  public void insert(final Habit habit) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfHabit.insert(habit);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Habit habit) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfHabit.handle(habit);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Habit habit) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfHabit.handle(habit);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Habit> getHabitsForProfile(final int profileId) {
    final String _sql = "SELECT * FROM habits WHERE profileId = ? ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, profileId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfProfileId = CursorUtil.getColumnIndexOrThrow(_cursor, "profileId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "frequency");
      final int _cursorIndexOfGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "goal");
      final int _cursorIndexOfNotify = CursorUtil.getColumnIndexOrThrow(_cursor, "notify");
      final int _cursorIndexOfCompletedToday = CursorUtil.getColumnIndexOrThrow(_cursor, "completedToday");
      final List<Habit> _result = new ArrayList<Habit>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Habit _item;
        final int _tmpProfileId;
        _tmpProfileId = _cursor.getInt(_cursorIndexOfProfileId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpFrequency;
        if (_cursor.isNull(_cursorIndexOfFrequency)) {
          _tmpFrequency = null;
        } else {
          _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
        }
        final String _tmpGoal;
        if (_cursor.isNull(_cursorIndexOfGoal)) {
          _tmpGoal = null;
        } else {
          _tmpGoal = _cursor.getString(_cursorIndexOfGoal);
        }
        final boolean _tmpNotify;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfNotify);
        _tmpNotify = _tmp != 0;
        _item = new Habit(_tmpProfileId,_tmpName,_tmpFrequency,_tmpGoal,_tmpNotify);
        _item.id = _cursor.getInt(_cursorIndexOfId);
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfCompletedToday);
        _item.completedToday = _tmp_1 != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
