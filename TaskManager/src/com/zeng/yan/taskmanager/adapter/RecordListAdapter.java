package com.zeng.yan.taskmanager.adapter;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.zeng.yan.taskmanager.R;
import com.zeng.yan.taskmanager.bean.TaskDetails;

public class RecordListAdapter extends BaseAdapter {
	private Context context;
	private List<TaskDetails> list;

	public RecordListAdapter(Context context, List<TaskDetails> list) {
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (list==null) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
//			convertView = LayoutInflater.from(context).inflate(
//					R.layout.item_time_line, parent, false);
			
			convertView = View.inflate(context,
					R.layout.item_time_line, null);
			holder.tl_tv_date_time = (TextView) convertView
					.findViewById(R.id.tl_tv_date_time);
			holder.tl_tv_content = (TextView) convertView
					.findViewById(R.id.tl_tv_content);
			
			holder.tl_task_time = (TextView) convertView
					.findViewById(R.id.tl_task_time);
			holder.tl_alarm_time = (TextView) convertView
					.findViewById(R.id.tl_alarm_time);
		//	holder.line = (View) convertView.findViewById(R.id.v_line);
			holder.tl_ll_content = (LinearLayout) convertView.findViewById(R.id.tl_ll_content);
			holder.tl_iv_timedot = (ImageView) convertView.findViewById(R.id.tl_iv_timedot);
			holder.tl_iv_datedot = (ImageView) convertView.findViewById(R.id.tl_iv_datedot);
			holder.tl_title = (RelativeLayout) convertView.findViewById(R.id.tl_title);
			holder.tl_content = (RelativeLayout) convertView.findViewById(R.id.tl_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		LayoutParams params = (LayoutParams) holder.tl_iv_timedot.getLayoutParams();
		LayoutParams params1 = (LayoutParams) holder.tl_iv_datedot.getLayoutParams();
		if (position == 0) {
			params1.addRule(RelativeLayout.ALIGN_TOP, R.id.tl_title);
			params1.addRule(RelativeLayout.ALIGN_BOTTOM,
					R.id.tl_title);
			params.addRule(RelativeLayout.ALIGN_TOP, R.id.tl_ll_contenter);
			params.addRule(RelativeLayout.ALIGN_BOTTOM,
					R.id.tl_ll_contenter);
			
			holder.tl_tv_date_time.setText(list.get(position).getDate());
			
		} else {
			if (list.get(position).getDate()
					.equals(list.get(position - 1).getDate())) {
				holder.tl_title.setVisibility(View.GONE);
				params.addRule(RelativeLayout.ALIGN_TOP, R.id.tl_ll_contenter);
				params.addRule(RelativeLayout.ALIGN_BOTTOM,
						R.id.tl_ll_contenter);
			
			} else {
				holder.tl_tv_date_time.setText(list.get(position).getDate());
				params1.addRule(RelativeLayout.ALIGN_TOP, R.id.tl_title);
				params1.addRule(RelativeLayout.ALIGN_BOTTOM,R.id.tl_title);
				params.addRule(RelativeLayout.ALIGN_TOP, R.id.tl_ll_contenter);
				params.addRule(RelativeLayout.ALIGN_BOTTOM,
						R.id.tl_ll_contenter);
			}
		}
		holder.tl_ll_content.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				AlertDialog.Builder builder2=new Builder(context);
				builder2.setTitle("请选择操作");
				final String [] arr=new String[]{"查看或修改事项","删除"};
				builder2.setItems(arr, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					if (which==0) {
						Toast.makeText(context, ""+which, 0).show();
					}else {
						
					}
							
						
						
					}
				});
				
				builder2.create().show();
				return true;
			}
		});
				
	
		holder.tl_tv_content.setText(list.get(position).getContent());
		holder.tl_task_time.setText(list.get(position).getStartTime()+"-"+list.get(position).getEndTime());
		holder.tl_alarm_time.setText("20:30");
	
		holder.tl_iv_timedot.setLayoutParams(params);
		holder.tl_iv_datedot.setLayoutParams(params1);
		return convertView;
	}
	

	public static class ViewHolder{
		TextView tl_tv_date_time,tl_tv_content,tl_task_time,tl_alarm_time;
		View line;
		LinearLayout tl_ll_content;
		ImageView tl_iv_timedot,tl_iv_datedot;
		RelativeLayout tl_title,tl_content;
	}

}
