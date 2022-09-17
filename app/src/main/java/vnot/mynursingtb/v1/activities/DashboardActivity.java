package vnot.mynursingtb.v1.activities;

import android.accounts.Account;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.vnot2021.mynursing_tb.utils.CustomWebTabHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
//import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import vnot.mynursingtb.v1.R;
import vnot.mynursingtb.v1.createalarm.CreateAlarmViewModel;
import vnot.mynursingtb.v1.createalarm.TimePickerUtil;
import vnot.mynursingtb.v1.createuserprofile.CreateUserProfileViewModel;
import vnot.mynursingtb.v1.data.Alarm;
import vnot.mynursingtb.v1.data.AlarmDatabase;
import vnot.mynursingtb.v1.data.UserProfile;
import vnot.mynursingtb.v1.data.UserProfileDao;
import vnot.mynursingtb.v1.databinding.ActivityDashboardBinding;
import vnot.mynursingtb.v1.data.Footer;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class DashboardActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {
    String TAG = DashboardActivity.class.getSimpleName();
    public String namaUser=null;
    public String emailUser=null;
    public String uidUser=null;

//    @BindView(R.id.tvCopyRight) TextView copyright;
//    @BindView(R.id.cardFaskes) CardView cardFaskes;
//    @BindView(R.id.cardPenkes) CardView cardPenkes;
//    @BindView(R.id.btnProfile) ImageButton btnProfile;
//    @BindView(R.id.btnLogOut) ImageButton btnLogOut;
//    @BindView(R.id.tvUserName) TextView tvUserName;
//    @BindView(R.id.tvEmailUser) TextView tvEmailUser;

    private ActivityDashboardBinding binding;
//    ViewDataBinding binding;
    View view;

    Context spcontext;
    CharSequence text;
    Button btnOkPilFaskes;
    LayoutInflater inflater;
    View dialogView;
    Spinner spinner;
    private String MINDORKS_PUBLICATION;
    private CustomWebTabHelper customTabHelper;

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;
    Dialog dialog;
    Context context;
    OptionalPendingResult<GoogleSignInResult> opr;

    ImageView profileImage;
    String personPhotoUrl,personEmail,personName,personToken,personUID,personID,personPhoneNumber,createdAt;
    TextView userName,userEmail;

    CreateUserProfileViewModel createUserProfileViewModel;
    UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityDashboardBinding.inflate(getLayoutInflater(),container, attachToContainer);
        // Assign variable
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
//        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        view = binding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();
        gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        customTabHelper = new CustomWebTabHelper();
        MINDORKS_PUBLICATION = getResources().getString(R.string.webalamat_homepage);
//        userName=(TextView)findViewById(R.id.tvUserName);
//        userEmail=(TextView)findViewById(R.id.tvEmailUser);
//        userPhoneNumber=(TextView)root.findViewById(R.id.phoneNumber);
//        userId=(TextView)root.findViewById(R.id.userId);
        profileImage=view.findViewById(R.id.ivProfile);

//        MyHandlers handlers = new MyHandlers();
//        binding.btnLogOut.setOnClickListener(handlers);
//        binding.(handlers);
//        handlers.onClickLogOut(view);
//        binding= DataBindingUtil.setContentView(this,R.layout.activity_dashboard);
//        setContentView(R.layout.activity_dashboard);
//        ButterKnife.bind(this);
        if(googleApiClient == null || !googleApiClient.isConnected()){
            try {
                googleApiClient = new GoogleApiClient.Builder(this)
                        .enableAutoManage(this , this )
                        .addOnConnectionFailedListener(this)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();
                hideProgressDialog();
            } catch (Exception e) {
                e.printStackTrace();
                hideProgressDialog();
            }
        }
        Footer footer = new Footer();
        footer.footerDashboard(binding,this);
        createUserProfileViewModel = ViewModelProviders.of(this)
                .get(CreateUserProfileViewModel.class);
    }
//    void footer(){
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        String txtAppName=getResources().getString(R.string.app_name);
////        Log.e(TAG,txtAppName);
//        String txtCopyRight=getResources().getString(R.string.txt_copyright,String.valueOf(year),txtAppName);
////        Log.e(TAG,txtCopyRight);
////        TextView copyright = findViewById(R.id.tvCopyRight);
//        binding.tvCopyRight.setText(txtCopyRight);
//    }

    public void onClickLogOut(View view){
        Log.e(TAG,"onClickLogOut");
        signOut();
    }
    public void onClickFaskes(View view) {
        showCustomDialog();
        Log.e(TAG,"onClickFaskes");
    }
    public void onClickPenkes(View view) {
        Log.e(TAG,"onClickPenkes");
        OpenUrlCustomTabsItents();
    }
    public void onClickReminder(View view) {
        Log.e(TAG,"onClickReminder");
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(namaUser, personName);
        intent.putExtra(emailUser, personEmail);
        intent.putExtra(uidUser, personUID);
        startActivity(intent);

    }
    public void onClickProfile(View view) {
        Log.e(TAG,"onClickProfile");

    }
    public void onClickCopyRight(View view) {
        Log.e(TAG,"onClickCopyRight");

    }

    private void PilihKabKota(){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.lokasi_faskes, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                String selected = parentView.getItemAtPosition(position).toString();
                spcontext = parentView.getContext();
                text = selected;
//                int duration = Toast.LENGTH_SHORT;
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();
//                TextView bk = (TextView) rootView.findViewById(R.id.booktext);
                switch (position){
                    case 0:
                        Log.e(TAG, "On 0 Select\nPosition : "+position+
                                "\nTitle : "+text.toString());
                        break;
                    case 1:
                        Log.e(TAG, "On 1 Select\nPosition : "+position+
                                "\nTitle : "+text.toString());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }
    private void LihatFaskes(String lokasi) {
        String flag1 = null;
        try {
            flag1 = URLEncoder.encode("Fasilitas Pelayanan Kesehatan "+lokasi, "UTF-8");
        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
            Log.e(TAG,"Error LihatFaskes => "+e.toString());
        }
        try {
//        URLEncoder.encode(lokasi, "UTF-8");
            Uri url = Uri.parse("https://www.google.co.id/maps/search/"+
                    flag1+"/@-6.9146666,109.6244375,13z");
            Intent intent = new Intent(Intent.ACTION_VIEW,url);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            dialog.dismiss();
        }catch (ActivityNotFoundException e){
            Uri url = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW,url);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            dialog.dismiss();
        }
    }
    //Function to display the custom dialog.
    void showCustomDialog() {
//        dialog = new Dialog(DashboardActivity.this);
        //We have added a title in the custom layout. So let's disable the default title.
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
//        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
//        dialog.setContentView(R.layout.fragment_faskes);
        dialog = new Dialog(DashboardActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.fragment_faskes, null);
        dialog.setContentView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle(getString(R.string.app_name));
        spinner = dialog.findViewById(R.id.spinner);
        btnOkPilFaskes = dialog.findViewById(R.id.btnOkPilFaskes);
        PilihKabKota();
        btnOkPilFaskes.setOnClickListener(view -> LihatFaskes(text.toString()));
        dialog.show();
    }

    public void OpenUrlCustomTabsItents(){
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        // modify toolbar color
//        builder.setToolbarColor(ContextCompat.getColor(this@PankesActivity, R.color.bg_login))


        CustomTabColorSchemeParams builderTabColor = new CustomTabColorSchemeParams.Builder()
                .setToolbarColor(getResources().getColor(R.color.warna_unuy))
                .build();
        // add share button to overflow menu
        builder.addDefaultShareMenuItem();
        CustomTabsIntent anotherCustomTab = new CustomTabsIntent.Builder().build();
        int requestCode = 100;
        Intent intent = anotherCustomTab.intent;
        intent.setData(Uri.parse(MINDORKS_PUBLICATION));
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // add menu item to oveflow
        builder.addMenuItem("Sample item", pendingIntent);
        builder.setDefaultColorSchemeParams(builderTabColor);
        builder.setUrlBarHidingEnabled(true);
        builder.setShowTitle(true);
        builder.setStartAnimations(this, android.R.anim.fade_in, android.R.anim.fade_out);
        builder.setExitAnimations(this, android.R.anim.fade_in, android.R.anim.fade_out);
//        val customTabsIntent = builder.build()
        // check is chrom available
//        val packageName = customTabHelper.getPackageNameToUse(this, MINDORKS_PUBLICATION)
//        customTabsIntent.intent.setPackage(packageName)
//        customTabsIntent.launchUrl(this, Uri.parse(MINDORKS_PUBLICATION))
//        }
        String packageName = customTabHelper.getPackageNameToUse(this, MINDORKS_PUBLICATION);
        if (packageName != null) {
            // we are checking if the package name is not null
            // if package name is not null then we are calling
            // that custom chrome tab with intent by passing its
            // package name.
//            customTabsIntent.intent.setPackage(packageName)
            // in that custom tab intent we are passing
            // our url which we have to browse.
//            customTabsIntent.launchUrl(activity, uri)
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(this, Uri.parse(MINDORKS_PUBLICATION));
        } else {
            // if the custom tabs fails to load then we are simply
            // redirecting our user to users device default browser.
            this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MINDORKS_PUBLICATION)));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            hideProgressDialog();
            googleApiClient.stopAutoManage((FragmentActivity) context);
            googleApiClient.disconnect();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        hideProgressDialog();
        googleApiClient.stopAutoManage(this);
        googleApiClient.disconnect();
    }

//    @Override
//    public void onResume(){
//        super.onResume();
//        createUserProfileViewModel.insert(userProfile);
//    }

    @Override
    public void onStart() {
        super.onStart();
//        OptionalPendingResult<GoogleSignInResult> opr= Auth.GoogleSignInApi.silentSignIn(googleApiClient);
//        if(opr.isDone()){
//            GoogleSignInResult result=opr.get();
//            handleSignInResult(result);
//        }else{
//            opr.setResultCallback(googleSignInResult -> handleSignInResult(googleSignInResult));
//        }
        opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.e(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            personPhotoUrl = account.getPhotoUrl().toString();
            personEmail = account.getEmail();
            personName = account.getDisplayName();
            personToken = account.getIdToken();
            personID = account.getId();
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            createdAt=date;
//            Account a = account.getAccount();
//            String b = a.name;
//            String c = a.type;
//            int d = account.hashCode();
//            Log.e(TAG,"a.name ="+b+" - a.type ="+c+"- account.hashCode() ="+ d);
            personUID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
//            personPhoneNumber=acc;
//            Log.e(TAG,"personPhoneNumber = "+personPhoneNumber.toString());
//            if(mAuth != null) {
//                personPhoneNumber = mAuth.getCurrentUser().getPhoneNumber();
//            }
//            String userUID,
//            String userPhotoUrl,
//            String userEmail,
//            String userName,
//            String userToken,
//            String userID,
//            String created_at
            userProfile = new UserProfile(
                    personUID, personPhotoUrl, personEmail,
                    personName, personToken, personID,createdAt
            );

            binding.tvUserName.setText(personName);
            binding.tvEmailUser.setText(personEmail);
//            userName.setText(personName);
//            userEmail.setText(personEmail);
//            if (personPhoneNumber!=null){
//                Log.e(TAG,"personPhoneNumber = "+ personPhoneNumber);
////                userPhoneNumber.setText(personPhoneNumber);
//            }else{
//                Log.e(TAG,"personPhoneNumber isEmpty() = "+personPhoneNumber.toString());
//            }
//            userId.setText(personID);
//            userPhoneNumber.setText(personPhoneNumber.toString());
            Log.e(TAG, "= User Profile = "
                    +"\nCreated At: "+createdAt
                    + ", \nName: " + personName
                    + ", \nEmail: " + personEmail
                    + ", \nUID: " + personUID
                    + ", \nID: " + personID
                    + ", \nToken: " + personToken
                    + ", \nImage: " + personPhotoUrl);
            try{
                Glide.with(this)
                        .load(personPhotoUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transition(withCrossFade())
                        .circleCrop()
                        .placeholder(R.drawable.ic_person_white_60)
                        .error(R.drawable.ic_broken_image_white_60)
                        .into(profileImage);
//                binding.btnProfile.setImageURI(Uri.parse(personPhotoUrl));
//                binding.setUser(new UserProfile(personPhotoUrl,personEmail,personName,personToken,personUID,personID));
//                binding.setImageUrl("http://androidwave.com/wp-content/uploads/2019/01/profile_pic.jpg");

            }catch (NullPointerException e){
                Toast.makeText(this,"image not found",Toast.LENGTH_LONG).show();
//                Glide.with(this).load(personPhotoUrl)
//                        .thumbnail(0.5f)
//                        .crossFade()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .centerCrop()
//                        .placeholder(R.drawable.ic_person_white_60)
//                        .error(R.drawable.ic_broken_image_white_60)
//                        .into(profileImage);

//                ImageButton profilePhoto = binding.btnProfile.
                Glide.with(this)
                        .load(personPhotoUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transition(withCrossFade())
                        .circleCrop()
                        .placeholder(R.drawable.ic_person_white_60)
                        .error(R.drawable.ic_broken_image_white_60)
                        .into(profileImage);
            }

//            createUserProfileViewModel.findId(personUID);
//            LiveData<UserProfile> b=createUserProfileViewModel.getOneUIDAndEmail1(personUID);
//            String b=createUserProfileViewModel.getOneUIDAndEmail2(personUID);
//            createUserProfileViewModel.getOneUIDAndEmail2(personUID);
            AlarmDatabase db = AlarmDatabase.getDatabase(context);
            UserProfileDao uDao = db.userProfileDao();
            new Thread(() -> {
                UserProfile userEntityUID = uDao.findById(personUID);
                if (userEntityUID == null) {
                    runOnUiThread(() -> {
                        createUserProfileViewModel.insertUserProfile(userProfile);
                        Log.e(TAG,"==== Data Belum Ada ==== ");
                    });
                }else{
                    String uid = userEntityUID.getUserUID();
                    String nama = userEntityUID.getUserName();
                    String email = userEntityUID.getUserEmail();
                    Log.e(TAG,"==== Data Ditemukan ===="
                            +"\nUID = "+uid
                            +"\nNama = "+nama
                            +"\nEmail = "+email);
                }
            }).start();
//            createUserProfileViewModel.insertUserProfile(userProfile);
            hideProgressDialog();
        }else{
            gotoMainActivity();
        }
    }

    private void gotoMainActivity(){
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
//        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG,"onConnectionFailed => "+connectionResult.toString());
        hideProgressDialog();
    }

    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        gotoMainActivity();
                    }else{
                        Toast.makeText(this,"Session not close", Toast.LENGTH_LONG).show();
                    }
                });
        // [END auth_fui_signout]
    }
}