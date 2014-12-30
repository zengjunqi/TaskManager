package com.zeng.yan.taskmanager;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zeng.yan.taskmanager.db.TaskDBOperator;
import com.zeng.yan.taskmanager.utils.CalendarUtils;

public class ChatFragment extends Fragment {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	GraphicalView graphicalView;
	TaskDBOperator helper;
	String conditonPeriod;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		helper = new TaskDBOperator(getActivity());
		conditonPeriod = CalendarUtils.getDatePeriod(
				dateFormat.format(new Date()), 2);

	}

	@Override
	public void onResume() {
		initChat();
		super.onResume();
	}

	private void initChat() {
		String[] period = conditonPeriod.split(";");//
		double[] values = helper.find(period[0], period[1]);
		// double[] values={ 200.0, 300.0, 400.0, 600, 800, 900, 800 };
		int j = 0;
		for (int i = 0; i < values.length; i++) {
			if (values[i] == 0) {
				j++;
			}
		}

		CategorySeries dataset = buildCategoryDataset("测试饼图", values);

		int[] colors = {
				Color.parseColor(getResources().getString(R.string.health)),
				Color.parseColor(getResources().getString(R.string.family)),
				Color.parseColor(getResources().getString(R.string.friend)),
				Color.parseColor(getResources().getString(R.string.love)),
				Color.parseColor(getResources().getString(R.string.work)),
				Color.parseColor(getResources().getString(R.string.study)),
				Color.parseColor(getResources().getString(R.string.interest)) };// getResources().getStringArray(R.array.colors);
		DefaultRenderer renderer = buildCategoryRenderer(j == values.length,
				colors);

		graphicalView = ChartFactory.getPieChartView(getActivity(), dataset,
				renderer);// 饼状图
		RelativeLayout layout = (RelativeLayout) getActivity().findViewById(
				R.id.container);
		layout.removeAllViews();

		RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);// (650, 650);
		lp1.addRule(RelativeLayout.CENTER_HORIZONTAL,
				RelativeLayout.CENTER_HORIZONTAL);
		// lp1.addRule(RelativeLayout.CENTER_IN_PARENT,
		// RelativeLayout.CENTER_IN_PARENT);
		lp1.topMargin = 10;
		layout.addView(graphicalView, lp1);
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

	protected DefaultRenderer buildCategoryRenderer(Boolean noData, int[] colors) {
		DefaultRenderer renderer = new DefaultRenderer();
		if (noData == true) {
			renderer.setChartTitleTextSize(40);// 设置图表标题的文字大小
			renderer.setChartTitle("本月还没有数据");// 设置图表的标题 默认是居中顶部显示

		} else {

			renderer.setChartTitleTextSize(40);// 设置图表标题的文字大小
			renderer.setChartTitle("统计本月结果");// 设置图表的标题 默认是居中顶部显示
			renderer.setLegendTextSize(30);// 设置左下角表注的文字大小
			// renderer.setZoomButtonsVisible(true);//设置显示放大缩小按钮
			renderer.setZoomEnabled(false);// 设置不允许放大缩小.
			renderer.setLabelsTextSize(30);// 饼图上标记文字的字体大小
			renderer.setLabelsColor(Color.parseColor("#33B6EA"));// 饼图上标记文字的颜色33B6EA
			renderer.setPanEnabled(false);// 设置是否可以平移
			renderer.setDisplayValues(true);// 是否显示值
			renderer.setFitLegend(true);//
			renderer.setClickEnabled(true);// 设置是否可以被点击
			renderer.setMargins(new int[] { 20, 30, 15, 0 });
		}
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
