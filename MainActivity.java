package com.afrobrain;
import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends Activity implements SurfaceHolder.Callback {
 Camera camera; SurfaceView preview; SurfaceHolder holder; boolean isTorchOn=false;
 protected void onCreate(Bundle s){super.onCreate(s);setContentView(R.layout.activity_main);
  TextView t=findViewById(R.id.title);t.setText("AfroBRAIN - Bamenda");
  preview=findViewById(R.id.preview);holder=preview.getHolder();holder.addCallback(this);
  Button cap=findViewById(R.id.capture);Button tor=findViewById(R.id.torch);
  SeekBar zoom=findViewById(R.id.zoomBar);zoom.setMax(10);
  zoom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
   public void onProgressChanged(SeekBar sb,int p,boolean b){if(camera!=null){try{Camera.Parameters pa=camera.getParameters();if(pa.isZoomSupported()){pa.setZoom(p*pa.getMaxZoom()/10);camera.setParameters(pa);}}catch(Exception e){}}}
   public void onStartTrackingTouch(SeekBar sb){} public void onStopTrackingTouch(SeekBar sb){}});
  cap.setOnClickListener(v->{if(camera!=null)camera.takePicture(null,null,(data,cam)->{try{File dir=getExternalFilesDir(null);File f=new File(dir,"AfroBRAIN_"+System.currentTimeMillis()+".jpg");FileOutputStream fos=new FileOutputStream(f);fos.write(data);fos.close();Toast.makeText(this,"Saved: "+f.getName(),0).show();camera.startPreview();}catch(Exception e){}});});
  tor.setOnClickListener(v->{if(camera!=null){try{Camera.Parameters p=camera.getParameters();if(isTorchOn){p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);isTorchOn=false;((Button)v).setText("LIGHT ON");}else{p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);isTorchOn=true;((Button)v).setText("LIGHT OFF");}camera.setParameters(p);}catch(Exception e){}}});
 }
 public void surfaceCreated(SurfaceHolder h){try{camera=Camera.open();camera.setPreviewDisplay(h);camera.startPreview();}catch(Exception e){}}
 public void surfaceChanged(SurfaceHolder h,int f,int w,int hh){} 
 public void surfaceDestroyed(SurfaceHolder h){if(camera!=null){camera.stopPreview();camera.release();camera=null;}}
}
