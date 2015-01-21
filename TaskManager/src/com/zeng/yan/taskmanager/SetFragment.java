package com.zeng.yan.taskmanager;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.zeng.yan.taskmanager.ui.SettingClickView;
import com.zeng.yan.taskmanager.ui.SettingItem;

public class SetFragment extends Fragment {
	private SettingClickView backRestore, share, helper, setring;
	private SettingItem update;
	private SharedPreferences sp;
	MediaPlayer player;
	int which;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.set_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		sp = getActivity().getSharedPreferences("config",
				getActivity().MODE_PRIVATE);
		backRestore = (SettingClickView) getActivity().findViewById(
				R.id.v_backup_restore);
		backRestore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						BackRestoreActivity.class);
				startActivity(intent);
			}
		});

		share = (SettingClickView) getActivity().findViewById(R.id.v_share);
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

		helper = (SettingClickView) getActivity().findViewById(R.id.v_reback);
		helper.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), ReplyActivity.class);
				startActivity(intent);
			}
		});
		setring = (SettingClickView) getActivity().findViewById(R.id.v_setring);
		final String[] items = { "铃声1", "铃声2", "铃声3", "铃声4", "铃声5", "铃声6","铃声7", "铃声8", "铃声9", "铃声10", "铃声11" };
		final int[] itemsId = { R.raw.app, R.raw.kaishi, R.raw.pingfan,
				R.raw.shebude, R.raw.tianliang, R.raw.xinxiao, R.raw.haoting,
				R.raw.jita, R.raw.niao, R.raw.nusheng, R.raw.miaohuat };
		which = sp.getInt("which", 0);
		System.out.println("which" + which);
		if (which == -1) {
			which = 0;
		}
		// player = MediaPlayer.create(getActivity(), itemsId[which]);
		// player.setLooping(true);//
		// player.setVolume(1.0f, 1.0f);

		setring.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 弹出一个对话框
				AlertDialog.Builder builder = new Builder(getActivity());
				builder.setTitle("闹钟铃声设置");
				builder.setSingleChoiceItems(items, which,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int positon) {
								if (player != null && player.isPlaying()) {

									player.stop();
									player = null;
								}

								// 保存选择参数
								player = MediaPlayer.create(getActivity(),
										itemsId[positon]);
								player.setLooping(true);//
								player.setVolume(1.0f, 1.0f);
								player.start();

								which = positon;
								// 取消对话框
								// dialog.dismiss();
							}
						});
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int positon) {
								// TODO Auto-generated method stub
								System.out.println("onClick which:" + which);
								Editor editor = sp.edit();
								editor.putInt("which", which);
								editor.commit();
								player.stop();
								dialog.dismiss();
							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								if (player.isPlaying()) {
									player.stop();
								}
							}
						});
				AlertDialog dialog = builder.create();
				dialog.setCancelable(false);
				dialog.show();

			}
		});
		update = (SettingItem) getActivity().findViewById(R.id.v_update);

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
