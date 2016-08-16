package com.example.lvivPolitexStuff;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;



public class LoadMySplash extends Activity {

  protected	Splash myAnimation;
	
  protected Thread runMain;
  // MediaPlayer mp;
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//дозволяэ запускати фулл скрін
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//
		
		myAnimation= new Splash(this);
		setContentView(myAnimation);
	//	mp=MediaPlayer.create(LoadMySplash.this, R.raw.rovenskyyfon);
		//mp.start();
		runMain = new Thread() {

			public void run() {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent i = new Intent(LoadMySplash.this,MainActivity.class);//параметр android:name в маніфесті або (З якого запускають.this,Який запускають.class)
					startActivity(i);
				}
			}

		};
		runMain.start();
	
		
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	//mp.stop();
		finish();
	}
	
	
	
	

}
