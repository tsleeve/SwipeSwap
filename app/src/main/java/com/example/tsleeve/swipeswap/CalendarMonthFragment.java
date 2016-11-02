package com.example.tsleeve.swipeswap;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;

import static android.R.attr.fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarMonthFragment extends Fragment implements CalendarView.OnDateChangeListener {

    CalendarView calendarView;
    private android.support.v4.app.FragmentManager manager = null;
    private Fragment fragment = null;
    public CalendarMonthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calendar_month_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendarView.setMaxDate(calendar.getTimeInMillis());
        calendarView.setMinDate(Calendar.getInstance().getTimeInMillis());
    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Bundle bundle = new Bundle();
        bundle.putLong(CalendarDayFragment.DATE_TO_SHOW, cal.getTimeInMillis());
        if (manager == null) manager = getChildFragmentManager();
        fragment = new CalendarDayFragment();
        fragment.setArguments(bundle);
        manager.beginTransaction().replace(R.id.month_dayfragment_container, fragment).commit();
    }
}
