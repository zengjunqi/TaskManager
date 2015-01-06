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
		.setTitle("����")//���ñ���          
		.setMessage("ʱ�䵽�ˣ���"+content+"��!")//��������         
		.setPositiveButton("֪����", new OnClickListener(){//���ð�ť              
			public void onClick(DialogInterface dialog, int which) {    
				player.stop();
				AlarmActivity.this.finish();//�ر�Activity              
			}      
		});
		 AlertDialog alertDialog = builder.create();
		 alertDialog.setCancelable(false);
		 alertDialog.show();
	
		
	}
}