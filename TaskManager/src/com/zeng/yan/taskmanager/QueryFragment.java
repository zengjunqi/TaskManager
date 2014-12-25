package com.zeng.yan.taskmanager;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zeng.yan.taskmanager.bean.TaskDetails;
import com.zeng.yan.taskmanager.db.TaskDBOperator;

public class QueryFragment extends Fragment implements OnClickListener {
	private String[] condition = { "今日", "这周", "这月", "今年" };
	private ListView lv;
	private TaskDBOperator dbOperator;
	private MyListAdapter dapter;
	List<TaskDetails> list;
	private int offset;
	private int maxno;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.query_fragment, container, false);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Spinner spCondition = (Spinner) getActivity().findViewById(
				R.id.sp_condition);
		ArrayAdapter adpt = new ArrayAdapter(getActivity(),
				android.R.layout.simple_spinner_item, condition);
		adpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spCondition.setAdapter(adpt);

		ImageView ivleft = (ImageView) getActivity().findViewById(
				R.id.query_iv1);
		ImageView ivright = (ImageView) getActivity().findViewById(
				R.id.query_iv2);

		ivleft.setOnClickListener(this);
		ivright.setOnClickListener(this);
		dbOperator = new TaskDBOperator(getActivity());
		lv = (ListView) getActivity().findViewById(R.id.query_lv);
		offset = 0;
		maxno = 20;
		list = dbOperator.findPart(offset, maxno,"2014-12-20","2014-12-31");
		dapter = new MyListAdapter(getActivity(), list);
		lv.setAdapter(dapter);
		lv.setDividerHeight(0);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.query_iv1:
			Toast.makeText(getActivity(), "左边点击.", Toast.LENGTH_SHORT).show();
			break;
		case R.id.query_iv2:
			Toast.makeText(getActivity(), "右边点击.", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		fillListViewData();
		super.onActivityResult(requestCode, resultCode, data);
	}
@Override
public void onResume() {
	fillListViewData();
	super.onResume();
}
	private void fillListViewData() {

		// if (list == null) {
		list.clear();
		list.addAll(dbOperator.findPart(offset, maxno,"2014-12-20","2014-12-31"));//list的引用改变则不能用notifyDataSetChanged来刷新界面了
		// } else {
		// list.addAll(dbOperator.findPart(offset, maxno));
		// }
		// isibility(View.INVISIBLE);
		if (dapter == null) {
			dapter = new MyListAdapter(getActivity(), list);
			lv.setAdapter(dapter);
		} else {
			dapter.notifyDataSetChanged();
		}

	}

	/**
	 * ListView Adapter
	 * 
	 * @author zeng.yan.ju
	 * 
	 */
	private class MyListAdapter extends BaseAdapter {
		private Context context;
		private List<TaskDetails> list;

		public MyListAdapter(Context context, List<TaskDetails> list) {
			this.context = context;
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (list == null) {
				return 0;
			}
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			if (list == null) {
				return null;
			}
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				// convertView = LayoutInflater.from(context).inflate(
				// R.layout.item_time_line, parent, false);

				convertView = View.inflate(context, R.layout.item_time_line,
						null);
				holder.tl_tv_date_time = (TextView) convertView
						.findViewById(R.id.tl_tv_date_time);
				holder.tl_tv_content = (TextView) convertView
						.findViewById(R.id.tl_tv_content);

				holder.tl_task_time = (TextView) convertView
						.findViewById(R.id.tl_task_time);
				holder.tl_alarm_time = (TextView) convertView
						.findViewById(R.id.tl_alarm_time);
				// holder.line = (View) convertView.findViewById(R.id.v_line);
				holder.tl_ll_content = (LinearLayout) convertView
						.findViewById(R.id.tl_ll_content);
				holder.tl_iv_timedot = (ImageView) convertView
						.findViewById(R.id.tl_iv_timedot);
				holder.tl_iv_datedot = (ImageView) convertView
						.findViewById(R.id.tl_iv_datedot);
				holder.tl_title = (RelativeLayout) convertView
						.findViewById(R.id.tl_title);
				holder.tl_content = (RelativeLayout) convertView
						.findViewById(R.id.tl_content);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			LayoutParams params = (LayoutParams) holder.tl_iv_timedot
					.getLayoutParams();
			LayoutParams params1 = (LayoutParams) holder.tl_iv_datedot
					.getLayoutParams();
			if (position == 0) {
				params1.addRule(RelativeLayout.ALIGN_TOP, R.id.tl_title);
				params1.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.tl_title);
				params.addRule(RelativeLayout.ALIGN_TOP, R.id.tl_ll_contenter);
				params.addRule(RelativeLayout.ALIGN_BOTTOM,
						R.id.tl_ll_contenter);

				holder.tl_tv_date_time.setText(list.get(position).getDate());

			} else {
				if (list.get(position).getDate()
						.equals(list.get(position - 1).getDate())) {
					holder.tl_title.setVisibility(View.GONE);
					params.addRule(RelativeLayout.ALIGN_TOP,
							R.id.tl_ll_contenter);
					params.addRule(RelativeLayout.ALIGN_BOTTOM,
							R.id.tl_ll_contenter);

				} else {
					holder.tl_tv_date_time
							.setText(list.get(position).getDate());
					params1.addRule(RelativeLayout.ALIGN_TOP, R.id.tl_title);
					params1.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.tl_title);
					params.addRule(RelativeLayout.ALIGN_TOP,
							R.id.tl_ll_contenter);
					params.addRule(RelativeLayout.ALIGN_BOTTOM,
							R.id.tl_ll_contenter);
				}
			}
			holder.tl_ll_content
					.setOnLongClickListener(new OnLongClickListener() {

						@Override
						public boolean onLongClick(View v) {
							AlertDialog.Builder builder2 = new Builder(context);
							builder2.setTitle("请选择操作");
							final String[] arr = new String[] { "查看或修改事项", "删除" };
							builder2.setItems(arr,
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											if (which == 0) {
												Intent intent = new Intent(
														context,
														AddTaskActivity.class);
												intent.putExtra("oper",
														"update");
												intent.putExtra("id",
														list.get(position)
																.get_id());
												startActivityForResult(intent,
														5);

											} else {

												AlertDialog.Builder builder = new Builder(
														context);
												builder.setTitle("警告");
												builder.setMessage("确定要删除这条记录么？");
												builder.setPositiveButton(
														"确定",
														new DialogInterface.OnClickListener() {
															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {
																dbOperator
																		.delete(String
																				.valueOf(list
																						.get(position)
																						.get_id()));
																// 更新界面。
																list.remove(position);
																// 通知listview数据适配器更新
																dapter.notifyDataSetChanged();
															}
														});
												builder.setNegativeButton("取消",
														null);
												builder.show();

											}

										}
									});

							builder2.create().show();
							return true;
						}
					});

			holder.tl_tv_content.setText(list.get(position).getContent());
			holder.tl_task_time.setText(list.get(position).getStartTime() + "-"
					+ list.get(position).getEndTime());
			holder.tl_alarm_time.setText("20:30");

			holder.tl_iv_timedot.setLayoutParams(params);
			holder.tl_iv_datedot.setLayoutParams(params1);
			return convertView;
		}
	}

	static class ViewHolder {
		TextView tl_tv_date_time, tl_tv_content, tl_task_time, tl_alarm_time;
		View line;
		LinearLayout tl_ll_content;
		ImageView tl_iv_timedot, tl_iv_datedot;
		RelativeLayout tl_title, tl_content;
	}
}
