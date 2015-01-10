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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zeng.yan.taskmanager.db.TaskDBOperator;
import com.zeng.yan.taskmanager.utils.CalendarUtils;

public class ChatFragment extends Fragment {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
	GraphicalView graphicalView;
	TaskDBOperator helper;
	String conditonPeriod;
	CategorySeries series;
	int[] colors = new int[7];
	double[] values = new double[7];
	DefaultRenderer renderer;
	private boolean updateFlag = false;
	private ImageView ivLeft, ivRight;
	private TextView tvMonth;
	String[] period;
	String conditionMonth;
	String conditionDate;
	public boolean isUpdateFlag() {

		return updateFlag;
	}

	public void setUpdateFlag(boolean updateFlag) {
		this.updateFlag = updateFlag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.chat_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		helper = new TaskDBOperator(getActivity());
		conditionDate=dateFormat.format(new Date());//当前日
		
		conditonPeriod = CalendarUtils.getDatePeriod(
				dateFormat.format(new Date()), 2);
		ivLeft = (ImageView) getActivity().findViewById(R.id.chat_iv1);
		ivRight = (ImageView) getActivity().findViewById(R.id.chat_iv2);
		tvMonth = (TextView) getActivity().findViewById(R.id.chat_month);
		String[] datef = conditionDate.split("-");
		tvMonth.setText(datef[0]+"年-"+datef[1]+"月");
		initChat();
		ivLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				conditionDate = CalendarUtils.calculateEndDate(period[0],
						-1);
				changeMonthChatData();
			}
		});
		ivRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				conditionDate = CalendarUtils.calculateEndDate(period[1],
						+1);
				changeMonthChatData();
			}
		});
	}
	private void changeMonthChatData() {
		//System.out.println(conditionMonth+"==="+conditionDate);
		//conditionMonth=monthFormat.format(conditionDate);
		String[] datef = conditionDate.split("-");
		tvMonth.setText(datef[0]+"年-"+datef[1]+"月");
		conditonPeriod = CalendarUtils.getDatePeriod(
				conditionDate, 2);
		changeItemData();
	}

	@Override
	public void onResume() {
		updateChat();
		super.onResume();
	}

	private void initChat() {
		// int[] colors = {
		// Color.parseColor(getResources().getString(R.string.health)),
		// Color.parseColor(getResources().getString(R.string.family)),
		// Color.parseColor(getResources().getString(R.string.friend)),
		// Color.parseColor(getResources().getString(R.string.love)),
		// Color.parseColor(getResources().getString(R.string.work)),
		// Color.parseColor(getResources().getString(R.string.study)),
		// Color.parseColor(getResources().getString(R.string.interest)) };
		initColor();
		Boolean noData = getData();
		series = new CategorySeries("饼图");
		series = buildCategoryDataset(values, noData);
		renderer = new DefaultRenderer();

		renderer = buildCategoryRenderer(noData, colors);
		graphicalView = ChartFactory.getPieChartView(getActivity(), series,
				renderer);// 饼状图
		RelativeLayout layout = (RelativeLayout) getActivity().findViewById(
				R.id.container);
		layout.removeAllViews();

		RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);// (650, 650);
		lp1.addRule(RelativeLayout.BELOW, R.id.container);
		// lp1.addRule(RelativeLayout.CENTER_VERTICAL,
		// RelativeLayout.CENTER_VERTICAL);
		lp1.topMargin = 10;
		layout.addView(graphicalView, lp1);

	}

	private void initColor() {
		colors[0] = Color.parseColor(getResources().getString(R.string.health));
		colors[1] = Color.parseColor(getResources().getString(R.string.family));
		colors[2] = Color.parseColor(getResources().getString(R.string.friend));
		colors[4] = Color.parseColor(getResources().getString(R.string.work));
		colors[5] = Color.parseColor(getResources().getString(R.string.study));
		colors[6] = Color.parseColor(getResources()
				.getString(R.string.interest));
		colors[3] = Color.parseColor(getResources().getString(R.string.love));
	}

	protected CategorySeries buildCategoryDataset(double[] values,
			boolean noData) {

		if (noData) {
			series.add("无数据", 1);
			return series;
		}

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

	private void updateChat() {
		if (updateFlag) {
			series.clear();
			Boolean noData = getData();
			series = buildCategoryDataset(values, noData);
			renderer = buildCategoryRenderer(noData, colors);
			graphicalView.repaint();
			graphicalView.invalidate();
			//System.out.println("重画了.");
			updateFlag = false;
		}
	}

	public void changeItemData() {
		updateFlag = true;
		updateChat();
	}

	private boolean getData() {
		 period = conditonPeriod.split(";");//
		double[] valuestemp = helper.find(period[0], period[1]);
		int j = 0;
		for (int i = 0; i < valuestemp.length; i++) {
			if (valuestemp[i] == 0) {
				j++;
			}
			values[i] = valuestemp[i];
		}
		return j == valuestemp.length;
	}

	protected DefaultRenderer buildCategoryRenderer(Boolean noData, int[] colors) {
		// renderer.removeSeriesRenderer(simpleSeriesRendererNoData);
		renderer.removeAllRenderers();
		if (noData == true) {
			renderer.setZoomEnabled(false);// 设置不允许放大缩小.
			renderer.setLabelsTextSize(30);// 饼图上标记文字的字体大小
			renderer.setLabelsColor(Color.parseColor("#33B6EA"));// 饼图上标记文字的颜色33B6EA
			renderer.setPanEnabled(false);// 设置是否可以平移
			//renderer.setChartTitleTextSize(40);// 设置图表标题的文字大小
			//renderer.setChartTitle("按月统计结果");// 设置图表的标题 默认是居中顶部显示
			SimpleSeriesRenderer simpleSeriesRendererNoData = new SimpleSeriesRenderer();
			simpleSeriesRendererNoData.setChartValuesFormat(NumberFormat
					.getPercentInstance());//
			simpleSeriesRendererNoData.setColor(Color.parseColor("#33B6EA"));
			renderer.addSeriesRenderer(simpleSeriesRendererNoData);
		} else {

			//renderer.setChartTitleTextSize(40);// 设置图表标题的文字大小
			//renderer.setChartTitle("按月统计结果");// 设置图表的标题 默认是居中顶部显示
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
			for (int color : colors) {
				SimpleSeriesRenderer simpleSeriesRenderer = new SimpleSeriesRenderer();
				simpleSeriesRenderer.setChartValuesFormat(NumberFormat
						.getPercentInstance());//
				simpleSeriesRenderer.setColor(color);
				renderer.addSeriesRenderer(simpleSeriesRenderer);
			}
		}
		// margins - an array containing the margin size values, in this order:
		// top, left, bottom, right
		return renderer;
	}
}
