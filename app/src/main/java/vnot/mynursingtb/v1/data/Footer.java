package vnot.mynursingtb.v1.data;

import android.content.Context;

import java.util.Calendar;

import vnot.mynursingtb.v1.R;
import vnot.mynursingtb.v1.databinding.ActivityDashboardBinding;
import vnot.mynursingtb.v1.databinding.ActivityLoginBinding;

public class Footer {
    String TAG = Footer.class.getSimpleName();
    public void footerDashboard(ActivityDashboardBinding binding, Context current){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String txtAppName=current.getResources().getString(R.string.app_name);
//        Log.e(TAG,txtAppName);
        String txtCopyRight=current.getResources().getString(R.string.txt_copyright,String.valueOf(year),txtAppName);
//        Log.e(TAG,txtCopyRight);
//        TextView copyright = findViewById(R.id.tvCopyRight);
        binding.tvCopyRight.setText(txtCopyRight);
    }
    public void footerLogin(ActivityLoginBinding binding, Context current){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String txtAppName=current.getResources().getString(R.string.app_name);
//        Log.e(TAG,txtAppName);
        String txtCopyRight=current.getResources().getString(R.string.txt_copyright,String.valueOf(year),txtAppName);
//        Log.e(TAG,txtCopyRight);
//        TextView copyright = findViewById(R.id.tvCopyRight);
        binding.tvCopyRight2.setText(txtCopyRight);
    }

}
