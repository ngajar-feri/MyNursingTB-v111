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
public interface UserProfileDao {
//    private String userUID;
//    String userPhotoUrl,userEmail,userName,userToken,userID;
    @Insert
    void insert(UserProfile userProfile);

    @Query("DELETE FROM user_table")
    void deleteAllUserProfile();

//    @Query("SELECT * FROM user_table ORDER BY created_at ASC")
//    LiveData<List<UserProfile>> getUserProfile();

    @Query("SELECT * FROM user_table WHERE userUID IN (:userUID)")
    List<UserProfile> loadAllUserProfileByIds(String userUID);

    @MapInfo(keyColumn = "userUID", valueColumn = "userEmail")
    @Query("SELECT userUID AS userUID, userEmail AS userEmail FROM user_table " +
            "WHERE userUID = :userUID " +
            "LIMIT 1")
    Map<String, List<String>> loadUIDAndEmail(String userUID);

    @MapInfo(keyColumn = "userUID", valueColumn = "userEmail")
    @Query("SELECT userUID AS userUID, userEmail AS userEmail FROM user_table " +
            "WHERE userUID = :userUID " +
            "LIMIT 1")
    LiveData<UserProfile> getOneUIDAndEmail(String userUID);


    @Query("SELECT * FROM user_table WHERE userUID LIKE :userUID " +
            "LIMIT 1")
    UserProfile findById(String userUID);

    @Update
    void update(UserProfile userProfile);
}
