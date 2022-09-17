package vnot.mynursingtb.v1.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.MapInfo;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.Map;

@Dao
public interface AlarmDao {
    @Insert
    void insert(Alarm alarm);

    @Query("DELETE FROM alarm_table")
    void deleteAll();

    @Query("SELECT * FROM alarm_table ORDER BY created ASC")
    LiveData<List<Alarm>> getAlarms();

    @Query("SELECT * FROM alarm_table WHERE alarmId IN (:alarmId)")
    List<Alarm> loadAllAlarmByIds(int[] alarmId);

    @Query("SELECT * FROM alarm_table WHERE title LIKE :title " +
            "LIMIT 1")
    Alarm findByName(String title);

    @Update
    void update(Alarm alarm);
}
