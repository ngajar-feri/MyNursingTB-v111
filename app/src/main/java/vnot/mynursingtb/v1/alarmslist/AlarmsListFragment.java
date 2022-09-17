package vnot.mynursingtb.v1.alarmslist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vnot.mynursingtb.v1.data.Alarm;
import vnot.mynursingtb.v1.R;

import java.util.List;
import java.util.Objects;

public class AlarmsListFragment extends Fragment implements OnToggleAlarmListener {
    private AlarmRecyclerViewAdapter alarmRecyclerViewAdapter;
    private AlarmsListViewModel alarmsListViewModel;
    //    String hariMinggu;
//    String hariSenin;
//    String hariSelasa;
//    String hariRabu;
//    String hariKamis;
//    String hariJumat;
//    String hariSabtu;
//    String notLocatedDay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        String hariMinggu= getString(R.string.sunday);
//        String hariSenin= getString(R.string.monday);
//        String hariSelasa= getString(R.string.tuesday);
//        String hariRabu= getString(R.string.thursday);
//        String hariKamis= getString(R.string.wednesday);
//        String hariJumat= getString(R.string.friday);
//        String hariSabtu= getString(R.string.saturday);
//        String notLocatedDay= getString(R.string.not_located_day);
        alarmRecyclerViewAdapter = new AlarmRecyclerViewAdapter(this);
        alarmsListViewModel = ViewModelProviders.of(this).get(AlarmsListViewModel.class);
        alarmsListViewModel.getAlarmsLiveData().observe(this, alarms -> {
            if (alarms != null) {
                alarmRecyclerViewAdapter.setAlarms(alarms);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listalarms, container, false);

        RecyclerView alarmsRecyclerView = view.findViewById(R.id.fragment_listalarms_recylerView);
        alarmsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        alarmsRecyclerView.setAdapter(alarmRecyclerViewAdapter);

        ImageButton backBtn = view.findViewById(R.id.backB);
        backBtn.setOnClickListener(v -> requireActivity().onBackPressed());
        Button addAlarm = view.findViewById(R.id.fragment_listalarms_addAlarm);
        addAlarm.setOnClickListener(v -> Navigation.findNavController(v).navigate(
                R.id.action_alarmsListFragment_to_createAlarmFragment));

        return view;
    }

    @Override
    public void onToggle(Alarm alarm) {
        if (alarm.isStarted()) {
            alarm.cancelAlarm(requireContext());
            alarmsListViewModel.update(alarm);
        } else {
            alarm.schedule(requireContext());
            alarmsListViewModel.update(alarm);
        }
    }
}