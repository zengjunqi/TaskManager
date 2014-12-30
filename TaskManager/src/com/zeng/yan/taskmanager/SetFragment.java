package com.zeng.yan.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zeng.yan.taskmanager.ui.SettingClickView;

public class SetFragment extends Fragment {
	private SettingClickView backRestore;
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
	}

}
