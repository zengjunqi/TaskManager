package com.zeng.yan.taskmanager;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zeng.yan.taskmanager.QueryFragment.ViewHolder;
import com.zeng.yan.taskmanager.bean.FileInfo;
import com.zeng.yan.taskmanager.ui.TitleBar;
import com.zeng.yan.taskmanager.ui.TitleBar.titleBarClickListener;
import com.zeng.yan.taskmanager.utils.BackupRestoreUtils;
import com.zeng.yan.taskmanager.utils.BackupRestoreUtils.BackUpCallBack;
import com.zeng.yan.taskmanager.utils.BackupRestoreUtils.RestoreBack;
import com.zeng.yan.taskmanager.utils.FileUtils;

public class BackRestoreActivity extends Activity implements
		OnItemLongClickListener {
	private TitleBar titleBar;
	private ListView lv;
	private Button btn;
	ProgressDialog pbBar;
	List<FileInfo> fileInfos;
	private BaseAdapter dapter;
	private TextView msgtv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.back_restore);
		lv = (ListView) findViewById(R.id.br_lv);
		lv.setOnItemLongClickListener(this);
		btn = (Button) findViewById(R.id.btn_backup);
		titleBar = (TitleBar) findViewById(R.id.myTitleBar);
		msgtv = (TextView) findViewById(R.id.tv_msg);
		titleBar.setOnTitleBarClickListener(new titleBarClickListener() {

			@Override
			public void rightClick() {
				// TODO Auto-generated method stub

			}

			@Override
			public void leftClick() {
				// TODO Auto-generated method stub
				finish();
			}
		});

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tasksBackup();
				
				
			}
		});
		refreshData();
	}

	private void refreshData() {
		fileInfos = FileUtils.scanDir(Environment.getExternalStorageDirectory()
				+ File.separator + "TaskManager" + File.separator + "Backup");
		if (fileInfos==null) {
			System.out.println("null");
		}else {
			System.out.println("===aa"+fileInfos.size());
			
		}
		if (fileInfos.size() > 0) {
			msgtv.setVisibility(View.GONE);
			lv.setVisibility(View.VISIBLE);
		} else {
			msgtv.setVisibility(View.VISIBLE);
			lv.setVisibility(View.GONE);
		}
			
		dapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder holder = null;
				if (convertView == null) {
					holder = new ViewHolder();
					convertView = View.inflate(getApplicationContext(),
							R.layout.backup_lv_item, null);
					holder.tvname = (TextView) convertView
							.findViewById(R.id.tv_filename);
					holder.tvsize = (TextView) convertView
							.findViewById(R.id.tv_filesize);
					holder.tvdate = (TextView) convertView
							.findViewById(R.id.tv_filedate);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				holder.tvdate.setText(fileInfos.get(position).getCreateDate());
				holder.tvname.setText(fileInfos.get(position).getName());
				holder.tvsize.setText(fileInfos.get(position).getSize());

				return convertView;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public Object getItem(int position) {
				if (fileInfos != null) {
					return fileInfos.get(position);
				}
				return null;
			}

			@Override
			public int getCount() {
				if (fileInfos != null) {
					return fileInfos.size();
				}
				return 0;
			}
		};
		lv.setAdapter(dapter);
	}

	private static class ViewHolder {
		public TextView tvname, tvdate, tvsize;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			final int position, long id) {
		AlertDialog.Builder builder = new Builder(BackRestoreActivity.this);
		builder.setTitle("���ݹ���");
		builder.setItems(new String[] { "�ָ�", "ɾ��" },
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (which == 0) {
							tasksRestore(fileInfos.get(position).getPath());
						} else  {

							AlertDialog.Builder builder = new Builder(
									BackRestoreActivity.this);
							builder.setTitle("����");
							builder.setMessage("ȷ��Ҫɾ������ļ�������");
							builder.setPositiveButton("ȷ��",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											FileUtils.deleteFile(fileInfos.get(
													position).getPath());
											// ���½��档
											fileInfos.remove(position);
											// ֪ͨlistview��������������
											dapter.notifyDataSetChanged();
											if (fileInfos.size() > 0) {
												msgtv.setVisibility(View.GONE);
												lv.setVisibility(View.VISIBLE);
											} else {
												msgtv.setVisibility(View.VISIBLE);
												lv.setVisibility(View.GONE);
											}
										}
									});
							builder.setNegativeButton("ȡ��", null);
							builder.show();

						}
					}
				});
		
		builder.setPositiveButton("ȡ��", null);
		builder.create().show();
		return true;
	}

	private void tasksBackup() {

		pbBar = new ProgressDialog(this);
		pbBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pbBar.setMessage("���ڱ�����...");
		pbBar.show();
		new Thread() {

			public void run() {
				try {
					BackupRestoreUtils.backupTasks(BackRestoreActivity.this,
							new BackUpCallBack() {

								@Override
								public void onBackup(int process) {
									// TODO Auto-generated method stub
									pbBar.setProgress(process);
								}

								@Override
								public void beforeBackup(int max) {
									// TODO Auto-generated method stub
									pbBar.setMax(max);
								}
							});
					runOnUiThread(new Runnable() {
						public void run() {
							refreshData();
							Toast.makeText(BackRestoreActivity.this, "���ݳɹ�", 0)
									.show();
						}
					});

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				///	System.out.println(e.getMessage());
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(BackRestoreActivity.this, "����ʧ��", 0)
									.show();
						}
					});
				} finally {
					pbBar.dismiss();
				}
			}

		}.start();

	}

	private void tasksRestore(final String filePath) {

		pbBar = new ProgressDialog(this);
		pbBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pbBar.setMessage("���ڱ�����...");
		pbBar.show();
		new Thread() {

			public void run() {
				try {
					BackupRestoreUtils.restoreTasks(BackRestoreActivity.this,
							filePath, new RestoreBack() {

								@Override
								public void onRestore(int process) {
									// TODO Auto-generated method stub
									pbBar.setProgress(process);
								}

								@Override
								public void beforeRestore(int max) {
									// TODO Auto-generated method stub
									pbBar.setMax(max);
								}
							});
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(BackRestoreActivity.this, "��ԭ�ɹ�", 0)
									.show();
						}
					});

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println(e.getMessage());
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(BackRestoreActivity.this, "��ԭʧ��", 0)
									.show();
						}
					});
				} finally {
					pbBar.dismiss();
				}
			}

		}.start();

	}

}