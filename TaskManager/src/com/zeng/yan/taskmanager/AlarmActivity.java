package com.zeng.yan.taskmanager;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.os.Bundle;

public class AlarmActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent=getIntent();
		String content=intent.getStringExtra("content");
		final MediaPlayer player = MediaPlayer.create(this, R.raw.als);
		player.setLooping(true);//
		player.setVolume(1.0f, 1.0f);
		player.start();
		 Builder builder =new AlertDialog.Builder(AlarmActivity.this)          
		.setTitle("闹钟")//设置标题          
		.setMessage("时间到了！该"+content+"了!")//设置内容         
		.setPositiveButton("知道了", new OnClickListener(){//设置按钮              
			public void onClick(DialogInterface dialog, int which) {    
				player.stop();
				AlarmActivity.this.finish();//关闭Activity              
			}      
		});
		 AlertDialog alertDialog = builder.create();
		 alertDialog.setCancelable(false);
		 alertDialog.show();
	
		
	}
}
