package vnot.mynursingtb.v1.alarmslist;

//import android.content.res.Resources;
import android.view.View;
//import android.widget.CompoundButton;
import android.widget.ImageView;
//import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

//import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Locale;

import vnot.mynursingtb.v1.data.Alarm;
import vnot.mynursingtb.v1.R;

public class AlarmViewHolder extends RecyclerView.ViewHolder {
    private TextView alarmTime;
    private ImageView alarmRecurring;
    private TextView alarmRecurringDays;
    private TextView alarmTitle;

    SwitchCompat alarmStarted;

    private OnToggleAlarmListener listener;

    public AlarmViewHolder(@NonNull View itemView, OnToggleAlarmListener listener) {
        super(itemView);

        alarmTime = itemView.findViewById(R.id.item_alarm_time);
        alarmStarted = itemView.findViewById(R.id.item_alarm_started);
        alarmRecurring = itemView.findViewById(R.id.item_alarm_recurring);
        alarmRecurringDays = itemView.findViewById(R.id.item_alarm_recurringDays);
        alarmTitle = itemView.findViewById(R.id.item_alarm_title);

        this.listener = listener;
    }

    public void bind(Alarm alarm) {
        String alarmText = String.format(Locale.getDefault(),"%02d:%02d",
                alarm.getHour(), alarm.getMinute());

        alarmTime.setText(alarmText);
        alarmStarted.setChecked(alarm.isStarted());

        if (alarm.isRecurring()) {
            alarmRecurring.setImageResource(R.drawable.ic_repeat_black_24dp);
            alarmRecurringDays.setText(alarm.getRecurringDaysText());
        } else {
            alarmRecurring.setImageResource(R.drawable.ic_looks_one_black_24dp);
            alarmRecurringDays.setText(R.string.text_once_off);
        }

        if (alarm.getTitle().length() != 0) {
            alarmTitle.setText(String.format(Locale.getDefault(),"%s",
                    alarm.getTitle()));
        } else {
            alarmTitle.setText(String.format(Locale.getDefault(),"%s",
                    R.string.text_alarm));
        }

        alarmStarted.setOnCheckedChangeListener((buttonView, isChecked) -> listener.onToggle(alarm));
    }
}
