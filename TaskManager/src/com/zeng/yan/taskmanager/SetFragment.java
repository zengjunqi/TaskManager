package com.zeng.yan.taskmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zeng.yan.taskmanager.ui.SettingClickView;
import com.zeng.yan.taskmanager.ui.SettingItem;

public class SetFragment extends Fragment {
	private SettingClickView backRestore,share,helper;
	private SettingItem update;
	private SharedPreferences sp;
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	return inflater.inflate(R.layout.set_fragment, container,false);
}

@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		backRestore=(SettingClickView) getActivity().findViewById(R.id.v_backup_restore);
		backRestore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),BackRestoreActivity.class);
				startActivity(intent);
			}
		});
		
		share=(SettingClickView) getActivity().findViewById(R.id.v_share);
		share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction("android.intent.action.SEND");
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_TEXT,
						"推荐您使用一款软件名称为:时间管理,助你实现更好自我时间管理,天天向上.");
				startActivity(intent);
			}
		});
		
		helper=(SettingClickView) getActivity().findViewById(R.id.v_reback);
		helper.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),ReplyActivity.class);
				startActivity(intent);
			}
		});
		
		update=(SettingItem) getActivity().findViewById(R.id.v_update);
		sp =getActivity().getSharedPreferences("config",getActivity().MODE_PRIVATE);

		boolean updateConfig = sp.getBoolean("update", false);
		if (updateConfig) {
			// 自动升级已经开启
			update.setChecked(true);
		} else {
			// 自动升级已经关闭
			update.setChecked(false);
		}
		update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Editor editor = sp.edit();
				// 判断是否有选中
				// 已经打开自动升级了
				if (update.isChecked()) {
					update.setChecked(false);
					editor.putBoolean("update", false);

				} else {
					// 没有打开自动升级
					update.setChecked(true);
					editor.putBoolean("update", true);
				}
				editor.commit();
			}
		});
	}

}
