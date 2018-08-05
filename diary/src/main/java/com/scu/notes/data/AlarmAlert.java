package com.scu.notes.data;

import com.scu.notes.AllDiaList;
import com.scu.notes.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;

/**
 *
 */
public class AlarmAlert extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		// 提示信息
		String remindMsg = bundle.getString("remindMsg");
		if (bundle.getBoolean("ring")) {
			// 播放音乐
			AllDiaList.mediaPlayer = MediaPlayer.create(this, R.raw.ring);
			try {
				AllDiaList.mediaPlayer.setLooping(true);
				AllDiaList.mediaPlayer.prepare();
			} catch (Exception e) {
				setTitle(e.getMessage());
			}
			AllDiaList.mediaPlayer.start();// 开始播放
		}
		if (bundle.getBoolean("shake")) {
			AllDiaList.vibrator = (Vibrator) getApplication().getSystemService(
					Service.VIBRATOR_SERVICE);
			AllDiaList.vibrator.vibrate(new long[] { 1000, 100, 100, 1000 }, -1);
		}
		new AlertDialog.Builder(AlarmAlert.this)
				.setIcon(R.drawable.icon)
				.setTitle("提醒")
				.setMessage(remindMsg)
				.setPositiveButton("关 闭",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
												int whichButton) {
								AlarmAlert.this.finish();
								// 关闭音乐播放器
								if (AllDiaList.mediaPlayer != null)
									AllDiaList.mediaPlayer.stop();
								if (AllDiaList.vibrator != null)
									AllDiaList.vibrator.cancel();
							}
						}).show();

	}
}
