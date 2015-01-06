package com.zeng.yan.taskmanager;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.R.raw;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

import com.zeng.yan.taskmanager.bean.DatePeroid;
import com.zeng.yan.taskmanager.db.TaskDBHelper;
import com.zeng.yan.taskmanager.db.TaskDBOperator;
import com.zeng.yan.taskmanager.utils.CalendarUtils;

public class MainFragment extends Fragment {

	public interface Mylistener {
		public void setChange(int datePeriod);
	}

	private ListView lv;
	private List<DatePeroid> list;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private Mylistener listener;
	private List<String> listDatas;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_fragment, container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		listener = (Mylistener) activity;
		super.onAttach(activity);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		lv = (ListView) getActivity().findViewById(R.id.main_lv);
		initList();

		

		lv.setAdapter(new BaseAdapter() {

			@Override
			public View getView(final int position, View convertView,
					ViewGroup parent) {

				convertView = View.inflate(getActivity(),
						R.layout.main_lv_item, null);
				TextView tvname = (TextView) convertView
						.findViewById(R.id.tv_main_name);
				TextView tvperoid = (TextView) convertView
						.findViewById(R.id.tv_main_period);
				ImageView ivImage = (ImageView) convertView
						.findViewById(R.id.iv_main_icon);
				ImageView ivPick = (ImageView) convertView
						.findViewById(R.id.iv_pick);
				ivImage.setImageDrawable(list.get(position).getIco());
				tvname.setText(list.get(position).getDate());
				tvperoid.setText(list.get(position).getPeroid());

				ivPick.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						listener.setChange(position);
					}
				});
				return convertView;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				if (list != null) {
					return list.get(position);
				}
				return null;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				if (list != null) {
					return list.size();
				}
				return 0;
			}
		});
	}

	private void initList() {
		String date = dateFormat.format(new Date());
		String weekPeriod = CalendarUtils.getDatePeriod(date, 1);
		String[] week = weekPeriod.split(";");
		String monthPeriod = CalendarUtils.getDatePeriod(date, 2);
		String[] month = monthPeriod.split(";");
		list = new ArrayList<DatePeroid>();
		for (int i = 0; i < 3; i++) {
			DatePeroid dPeroid = new DatePeroid();
			if (i == 0) {
				dPeroid.setDate("今天");
				dPeroid.setPeroid(CalendarUtils.getDateFormate(date, true));
				dPeroid.setIco(getResources().getDrawable(
						R.drawable.ic_launcher));
			} else if (i == 1) {
				dPeroid.setDate("本周");
				dPeroid.setPeroid(CalendarUtils.getDateFormate(week[0], false)
						+ "-" + CalendarUtils.getDateFormate(week[1], false));
				dPeroid.setIco(getResources().getDrawable(
						R.drawable.ic_launcher));
			} else {
				dPeroid.setDate("本月");
				dPeroid.setPeroid(CalendarUtils.getDateFormate(month[0], false)
						+ "-" + CalendarUtils.getDateFormate(month[1], false));
				dPeroid.setIco(getResources().getDrawable(
						R.drawable.ic_launcher));
			}

			list.add(dPeroid);
		}

	}

	
}
