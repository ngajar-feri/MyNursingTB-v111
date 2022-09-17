package vnot.mynursingtb.v1.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

public class UserProfileRepository {
    private UserProfileDao userProfileDao;
//    private LiveData<List<UserProfile>> userProfileLiveData;

    public UserProfileRepository(Application application) {
        AlarmDatabase db = AlarmDatabase.getDatabase(application);
        userProfileDao = db.userProfileDao();
//        userProfileLiveData = userProfileDao.getUserProfile();
    }

    public void insert(UserProfile alarm) {
        AlarmDatabase.databaseWriteExecutor.execute(() -> {
            userProfileDao.insert(alarm);
        });
    }

    public void update(UserProfile userUID) {
        AlarmDatabase.databaseWriteExecutor.execute(() -> {
            userProfileDao.update(userUID);
        });
    }
    public void findId(String userUID) {
        AlarmDatabase.databaseWriteExecutor.execute(() -> {
//            userProfileDao.findById(alarm);
            userProfileDao.loadUIDAndEmail(userUID);
        });
    }
    public LiveData<UserProfile> getOneUIDAndEmail1(String userUID) {
//        userProfileDao.getOneUIDAndEmail(userUID);
        return userProfileDao.getOneUIDAndEmail(userUID);
    }
    public void getOneUIDAndEmail2(String userUID) {
//        userProfileDao.getOneUIDAndEmail(userUID);
        AlarmDatabase.databaseWriteExecutor.execute(() -> {
//            userProfileDao.findById(alarm);
            userProfileDao.getOneUIDAndEmail(userUID);
        });
    }

//    public LiveData<List<UserProfile>> getUserProfileLiveData() {
//        return userProfileLiveData;
//    }
}
