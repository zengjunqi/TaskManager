package com.zeng.yan.taskmanager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zeng.yan.taskmanager.bean.VersionInfo;
import com.zeng.yan.taskmanager.utils.StreamTools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {
	private TextView tvVersion;
	private PackageManager packageManager;
	private String currentVersion;
	private VersionInfo versionInfo;
	private String TAG = "ZENG";
	private TextView downProcess;
	private RelativeLayout rlLayout;
	private SharedPreferences sp;
	protected static final int SHOW_UPDATE_DIALOG = 0;
	protected static final int ENTER_HOME = 1;
	protected static final int URL_ERROR = 2;
	protected static final int NETWORK_ERROR = 3;
	protected static final int JSON_ERROR = 4;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case SHOW_UPDATE_DIALOG:// ��ʾ�����ĶԻ���
				Log.i(TAG, "��ʾ�����ĶԻ���");
				showDailog();
				break;
			case ENTER_HOME:// ������ҳ��
				enterHome();
				break;

			case URL_ERROR:// URL����
				enterHome();
				Toast.makeText(getApplicationContext(), "URL����", 0).show();

				break;

			case NETWORK_ERROR:// �����쳣
				enterHome();
				Toast.makeText(SplashActivity.this, "�����쳣", 0).show();
				break;

			case JSON_ERROR:// JSON��������
				enterHome();
				Toast.makeText(SplashActivity.this, "JSON��������", 0).show();
				break;

			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);
		versionInfo = new VersionInfo();
		tvVersion = (TextView) findViewById(R.id.tv_version);
		rlLayout = (RelativeLayout) findViewById(R.id.rl_splash);
		currentVersion = getVersion();
		tvVersion.setText("�汾��" + currentVersion);
		downProcess = (TextView) findViewById(R.id.tv_downprocess);
		tvVersion.setVisibility(View.VISIBLE);
		// isNeedUpdate();
		sp = getSharedPreferences("config", MODE_PRIVATE);
		boolean update = sp.getBoolean("update", false);
		if (update) {
			// �������
			checkUpdate();
		} else {
			// �Զ������Ѿ��ر�
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// ������ҳ��
					enterHome();

				}
			}, 2000);

		}
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
		// alphaAnimation.setRepeatCount(1);
		alphaAnimation.setDuration(2000);
		// alphaAnimation.setRepeatMode(Animation.REVERSE);
		alphaAnimation.setFillAfter(true);

		rlLayout.setAnimation(alphaAnimation);
		// installShortCut();

	}

	/**
	 * ����Ƿ����°汾������о�����
	 */
	private void checkUpdate() {

		new Thread() {
			public void run() {

				Message mes = Message.obtain();
				long startTime = System.currentTimeMillis();
				try {

					URL url = new URL(getString(R.string.updateweburl));
					// ����
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(4000);
					int code = conn.getResponseCode();
					if (code == 200) {
						// �����ɹ�
						InputStream is = conn.getInputStream();
						// ����ת��String
						String result = StreamTools.readFromStream(is);
						Log.i(TAG, "�����ɹ���" + result);
						// json����
						JSONObject jsonObject = new JSONObject(result);
						// �õ��������İ汾��Ϣ
						versionInfo.setVersion(jsonObject.getString("version"));
						versionInfo.setDescription(jsonObject
								.getString("description"));
						versionInfo.setApkurl(jsonObject.getString("apkurl"));
						// У���Ƿ����°汾
						if (currentVersion.equals(versionInfo.getVersion())) {
							// �汾һ�£�û���°汾��������ҳ��
							mes.what = ENTER_HOME;
						} else {
							// ���°汾������һ�����Ի���
							mes.what = SHOW_UPDATE_DIALOG;

						}

					}

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					mes.what = URL_ERROR;
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					mes.what = NETWORK_ERROR;
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mes.what = JSON_ERROR;
				} finally {

					long endTime = System.currentTimeMillis();
					// ���ǻ��˶���ʱ��
					long dTime = endTime - startTime;
					// 2000
					if (dTime < 2000) {
						try {
							Thread.sleep(2000 - dTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					handler.sendMessage(mes);
				}

			};
		}.start();

	}

	private void showDailog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("��������");
		builder.setCancelable(false);
		builder.setMessage(versionInfo.getDescription());
		builder.setNegativeButton("�´���˵", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				// ������������װ
				enterHome();
			}
		});
		builder.setPositiveButton("ȷ��", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				// ������������װ
				File file = new File(Environment.getExternalStorageDirectory()
						+ File.separator + "new.apk");
				if (file.exists()) {
					file.delete();
				}

				HttpUtils http = new HttpUtils();
				HttpHandler httpHandler = http.download(
						versionInfo.getApkurl(),
						Environment.getExternalStorageDirectory()
								+ File.separator + "new.apk", true, // ���Ŀ���ļ����ڣ�����δ��ɵĲ��ּ������ء���������֧��RANGEʱ���������ء�
						true, // ��������󷵻���Ϣ�л�ȡ���ļ�����������ɺ��Զ���������
						new RequestCallBack<File>() {

							@Override
							public void onStart() {
								// testTextView.setText("conn...");

							}

							@Override
							public void onLoading(long total, long current,
									boolean isUploading) {
								// testTextView.setText(current + "/" + total);
								downProcess.setVisibility(View.VISIBLE);
								downProcess.setText("���ؽ���Ϊ:" + current * 100
										/ total + "/%");
							}

							@Override
							public void onSuccess(
									ResponseInfo<File> responseInfo) {
								// testTextView.setText("downloaded:" +
								// responseInfo.result.getPath());
								Log.i(TAG, "���سɹ����밲װ");
								Intent installIntent = new Intent();
								installIntent
										.setAction("android.intent.action.VIEW");// android.intent.action.VIEW
								installIntent.setDataAndType(
										Uri.fromFile(responseInfo.result),
										"application/vnd.android.package-archive");
								startActivity(installIntent);

								SplashActivity.this.finish();
							}

							@Override
							public void onFailure(HttpException error,
									String msg) {
								// testTextView.setText(msg);
								Log.i(TAG,
										"����ʧ�ܽ���������\n"
												+ versionInfo.getApkurl()
												+ "\n"
												+ Environment
														.getExternalStorageDirectory()
												+ File.separator + "new.apk");
								enterHome();
							}
						});

			}
		});

		builder.show();
	}

	private void enterHome() {
		// TODO Auto-generated method stub
		this.finish();
		Intent homeIntent = new Intent(this, MainActivity.class);
		startActivity(homeIntent);

	}

	/**
	 * 
	 * @return ��ȡ��ǰ�İ汾
	 */
	private String getVersion() {
		packageManager = getPackageManager();
		try {
			PackageInfo info = packageManager.getPackageInfo(getPackageName(),
					0);
			return info.versionName;

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

}