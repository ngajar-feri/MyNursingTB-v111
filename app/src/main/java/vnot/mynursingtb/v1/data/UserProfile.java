package vnot.mynursingtb.v1.data;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import vnot.mynursingtb.v1.R;
import vnot.mynursingtb.v1.broadcastreceiver.AlarmBroadcastReceiver;
import vnot.mynursingtb.v1.createalarm.DayUtil;

import java.sql.Date;
import java.util.Calendar;
import java.util.Locale;

@Entity(tableName = "user_table")
public class UserProfile {
//    @PrimaryKey(autoGenerate = true)
//    int id;
    @PrimaryKey()
    @NonNull
    private String userUID;
    private String userPhotoUrl;
    private String userEmail;
    private String userName;
    private String userToken;
    private String userID;
    private String created_at;
    public UserProfile(String userUID,
                       String userPhotoUrl,
                       String userEmail,
                       String userName,
                       String userToken,
                       String userID,
                       String created_at) {
        this.userUID = userUID;
        this.userPhotoUrl = userPhotoUrl;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userToken = userToken;
        this.userID = userID;
        this.created_at = created_at;
//        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    //    public String getUserPhoneNumber() {
//        return userPhoneNumber;
//    }
//
//    public void setUserPhoneNumber(String userPhoneNumber) {
//        this.userPhoneNumber = userPhoneNumber;
//    }
//    @BindingAdapter({"bind:imageUrl"})
    @BindingAdapter({"userProfile"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl).apply(new RequestOptions().circleCrop())
                .into(view);
    }
}
