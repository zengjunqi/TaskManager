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

		// **********新建饼图
		final GraphicalView graphicalView;
		TaskDBOperator helper=new TaskDBOperator(getActivity());
		String conditonPeriod = CalendarUtils.getDatePeriod(dateFormat.format(new Date()), 2);
		String[] period = conditonPeriod.split(";");// 
		double [] values=helper.find(period[0], period[1]);
		//double[] values={ 200.0, 300.0, 400.0, 600, 800, 900, 800 };
		CategorySeries dataset = buildCategoryDataset("测试饼图", values);

		int[] colors = {Color.parseColor(getResources().getString(R.string.health)),
				Color.parseColor(getResources().getString(R.string.family)),
				Color.parseColor(getResources().getString(R.string.friend)),
				Color.parseColor(getResources().getString(R.string.love)),
				Color.parseColor(getResources().getString(R.string.work)),
				Color.parseColor(getResources().getString(R.string.study)),
				Color.parseColor(getResources().getString(R.string.interest))};//getResources().getStringArray(R.array.colors);
		DefaultRenderer renderer = buildCategoryRenderer(colors);

		graphicalView = ChartFactory.getPieChartView(getActivity(), dataset,
				renderer);// 饼状图
		RelativeLayout layout = (RelativeLayout) getActivity().findViewById(
				R.id.container);
		layout.removeAllViews();
		// layout.setBackgroundColor(Color.BLACK);

		RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);// (650, 650);
		lp1.addRule(RelativeLayout.CENTER_HORIZONTAL,
				RelativeLayout.CENTER_HORIZONTAL);
		lp1.topMargin = 10;
		layout.addView(graphicalView, lp1);
	
		/*
		 * graphicalView.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub SeriesSelection seriesSelection =
		 * graphicalView.getCurrentSeriesAndPoint(); if (seriesSelection ==
		 * null) { Toast.makeText(getActivity(), "No",
		 * Toast.LENGTH_SHORT).show(); }else { Toast.makeText(getActivity(),
		 * "Yes", Toast.LENGTH_SHORT).show(); } } });
		 */
		// layout.addView(lv);
		// **饼图*//

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

	protected CategorySeries buildCategoryDataset(String title, double[] values) {
		CategorySeries series = new CategorySeries(title);
		double total = 0;
		for (int i = 0; i < values.length; i++) {
			total += values[i];

		}
		series.add("健康", values[0] / total);
		series.add("家庭", values[1] / total);
		series.add("友谊", values[2] / total);
		series.add("爱情", values[3] / total);
		series.add("工作", values[4] / total);
		series.add("学习", values[5] / total);
		series.add("兴趣", values[6] / total);
		return series;
	}

	protected DefaultRenderer buildCategoryRenderer(int[] colors) {
		DefaultRenderer renderer = new DefaultRenderer();

		renderer.setLegendTextSize(30);// 设置左下角表注的文字大小
		// renderer.setZoomButtonsVisible(true);//设置显示放大缩小按钮
		renderer.setZoomEnabled(false);// 设置不允许放大缩小.
		renderer.setChartTitleTextSize(40);// 设置图表标题的文字大小
		renderer.setChartTitle("统计本月结果");// 设置图表的标题 默认是居中顶部显示
		renderer.setLabelsTextSize(30);// 饼图上标记文字的字体大小
		renderer.setLabelsColor(Color.parseColor("#33B6EA"));// 饼图上标记文字的颜色
		renderer.setPanEnabled(false);// 设置是否可以平移
		renderer.setDisplayValues(true);// 是否显示值
		renderer.setFitLegend(true);//
		renderer.setClickEnabled(true);// 设置是否可以被点击
		renderer.setMargins(new int[] { 20, 30, 15, 0 });

		// margins - an array containing the margin size values, in this order:
		// top, left, bottom, right
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setChartValuesFormat(NumberFormat.getPercentInstance());//
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}
}
