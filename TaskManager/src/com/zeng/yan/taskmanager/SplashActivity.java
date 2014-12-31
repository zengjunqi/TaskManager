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
			case SHOW_UPDATE_DIALOG:// 显示升级的对话框
				Log.i(TAG, "显示升级的对话框");
				showDailog();
				break;
			case ENTER_HOME:// 进入主页面
				enterHome();
				break;

			case URL_ERROR:// URL错误
				enterHome();
				Toast.makeText(getApplicationContext(), "URL错误", 0).show();

				break;

			case NETWORK_ERROR:// 网络异常
				enterHome();
				Toast.makeText(SplashActivity.this, "网络异常", 0).show();
				break;

			case JSON_ERROR:// JSON解析出错
				enterHome();
				Toast.makeText(SplashActivity.this, "JSON解析出错", 0).show();
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
		tvVersion.setText("版本：" + currentVersion);
		downProcess = (TextView) findViewById(R.id.tv_downprocess);
		tvVersion.setVisibility(View.VISIBLE);
		// isNeedUpdate();
		sp = getSharedPreferences("config", MODE_PRIVATE);
		boolean update = sp.getBoolean("update", false);
		if (update) {
			// 检查升级
			checkUpdate();
		} else {
			// 自动升级已经关闭
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// 进入主页面
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
	 * 检查是否有新版本，如果有就升级
	 */
	private void checkUpdate() {

		new Thread() {
			public void run() {

				Message mes = Message.obtain();
				long startTime = System.currentTimeMillis();
				try {

					URL url = new URL(getString(R.string.updateweburl));
					// 联网
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(4000);
					int code = conn.getResponseCode();
					if (code == 200) {
						// 联网成功
						InputStream is = conn.getInputStream();
						// 把流转成String
						String result = StreamTools.readFromStream(is);
						Log.i(TAG, "联网成功了" + result);
						// json解析
						JSONObject jsonObject = new JSONObject(result);
						// 得到服务器的版本信息
						versionInfo.setVersion(jsonObject.getString("version"));
						versionInfo.setDescription(jsonObject
								.getString("description"));
						versionInfo.setApkurl(jsonObject.getString("apkurl"));
						// 校验是否有新版本
						if (currentVersion.equals(versionInfo.getVersion())) {
							// 版本一致，没有新版本，进入主页面
							mes.what = ENTER_HOME;
						} else {
							// 有新版本，弹出一升级对话框
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
					// 我们花了多少时间
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
		builder.setTitle("升级提醒");
		builder.setCancelable(false);
		builder.setMessage(versionInfo.getDescription());
		builder.setNegativeButton("下次在说", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				// 下载软件并安装
				enterHome();
			}
		});
		builder.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				// 下载软件并安装
				File file = new File(Environment.getExternalStorageDirectory()
						+ File.separator + "new.apk");
				if (file.exists()) {
					file.delete();
				}

				HttpUtils http = new HttpUtils();
				HttpHandler httpHandler = http.download(
						versionInfo.getApkurl(),
						Environment.getExternalStorageDirectory()
								+ File.separator + "new.apk", true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
						true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
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
								downProcess.setText("下载进度为:" + current * 100
										/ total + "/%");
							}

							@Override
							public void onSuccess(
									ResponseInfo<File> responseInfo) {
								// testTextView.setText("downloaded:" +
								// responseInfo.result.getPath());
								Log.i(TAG, "下载成功进入安装");
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
										"下载失败进入主界面\n"
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
	 * @return 获取当前的版本
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
