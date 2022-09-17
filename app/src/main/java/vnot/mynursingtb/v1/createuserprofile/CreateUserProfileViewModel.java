package vnot.mynursingtb.v1.createuserprofile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import vnot.mynursingtb.v1.data.UserProfile;
import vnot.mynursingtb.v1.data.UserProfileRepository;

public class CreateUserProfileViewModel extends AndroidViewModel {
    private UserProfileRepository userProfileRepository;

    public CreateUserProfileViewModel(@NonNull Application application) {
        super(application);
        userProfileRepository = new UserProfileRepository(application);
    }

    public void insertUserProfile(UserProfile alarm) {
        userProfileRepository.insert(alarm);
    }

    public void updateUserProfile(UserProfile alarm) {
        userProfileRepository.update(alarm);
    }

    public void findId(String userUID) {
        userProfileRepository.findId(userUID);
//        userProfileRepository.loadUserAndBookNames(userID);
    }

    public LiveData<UserProfile> getOneUIDAndEmail1(String userUID) {
//        userProfileRepository.getOneUIDAndEmail(userUID);
//        return userProfileRepository.getOneUIDAndEmail1(userUID);
        return userProfileRepository.getOneUIDAndEmail1(userUID);
    }

    public void getOneUIDAndEmail2(String userUID) {
//        userProfileRepository.getOneUIDAndEmail(userUID);
//        return userProfileRepository.getOneUIDAndEmail1(userUID);
        userProfileRepository.getOneUIDAndEmail2(userUID);
    }

//    UserProfile findById(String userID);

}
