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

		CategorySeries dataset = buildCategoryDataset(j == values.length,"��ͼ", values);

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
				renderer);// ��״ͼ
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

	protected CategorySeries buildCategoryDataset(Boolean noData,String title, double[] values) {
		CategorySeries series = new CategorySeries(title);
		if (noData) {
			series.add("������",1);
			return series;
		}
		
		double total = 0;
		for (int i = 0; i < values.length; i++) {
			total += values[i];

		}
		series.add("����", values[0] / total);
		series.add("��ͥ", values[1] / total);
		series.add("����", values[2] / total);
		series.add("����", values[3] / total);
		series.add("����", values[4] / total);
		series.add("ѧϰ", values[5] / total);
		series.add("��Ȥ", values[6] / total);
		return series;
	}

	protected DefaultRenderer buildCategoryRenderer(Boolean noData, int[] colors) {
		DefaultRenderer renderer = new DefaultRenderer();
		if (noData == true) {
			renderer.setZoomEnabled(false);// ���ò������Ŵ���С.
			renderer.setLabelsTextSize(30);// ��ͼ�ϱ�����ֵ������С
			renderer.setLabelsColor(Color.parseColor("#33B6EA"));// ��ͼ�ϱ�����ֵ���ɫ33B6EA
			renderer.setPanEnabled(false);// �����Ƿ����ƽ��
			renderer.setChartTitleTextSize(40);// ����ͼ����������ִ�С
			renderer.setChartTitle("ͳ�Ʊ��½��");// ����ͼ���ı��� Ĭ���Ǿ��ж�����ʾ
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setChartValuesFormat(NumberFormat.getPercentInstance());//
			r.setColor(Color.parseColor("#33B6EA"));
			renderer.addSeriesRenderer(r);
		} else {

			renderer.setChartTitleTextSize(40);// ����ͼ����������ִ�С
			renderer.setChartTitle("ͳ�Ʊ��½��");// ����ͼ���ı��� Ĭ���Ǿ��ж�����ʾ
			renderer.setLegendTextSize(30);// �������½Ǳ�ע�����ִ�С
			// renderer.setZoomButtonsVisible(true);//������ʾ�Ŵ���С��ť
			renderer.setZoomEnabled(false);// ���ò������Ŵ���С.
			renderer.setLabelsTextSize(30);// ��ͼ�ϱ�����ֵ������С
			renderer.setLabelsColor(Color.parseColor("#33B6EA"));// ��ͼ�ϱ�����ֵ���ɫ33B6EA
			renderer.setPanEnabled(false);// �����Ƿ����ƽ��
			renderer.setDisplayValues(true);// �Ƿ���ʾֵ
			renderer.setFitLegend(true);//
			renderer.setClickEnabled(true);// �����Ƿ���Ա����
			renderer.setMargins(new int[] { 20, 30, 15, 0 });
			for (int color : colors) {
				SimpleSeriesRenderer r = new SimpleSeriesRenderer();
				r.setChartValuesFormat(NumberFormat.getPercentInstance());//
				r.setColor(color);
				renderer.addSeriesRenderer(r);
			}
		}
		// margins - an array containing the margin size values, in this order:
		// top, left, bottom, right
		return renderer;
	}
}