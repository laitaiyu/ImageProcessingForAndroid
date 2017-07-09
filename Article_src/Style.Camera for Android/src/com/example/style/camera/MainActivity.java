package com.example.style.camera;


import com.example.style.camera.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.*;
import android.widget.*;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.widget.RadioGroup;
import android.widget.RadioButton;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements SurfaceHolder.Callback  {

	SurfaceView mSurfaceView ;
	Button btn_Capture;		
	Camera mCamera;	
	PictureCallback mPictureCB;
	AutoFocusCallback mAutoFocusCB;
	ImageView ImgView;
	TextView txtView;
	Bitmap bitmapClone;
	RadioGroup rdg_Main;
	RadioButton rdb_Gray;
	RadioButton rdb_Comic;
	int iImageProcessingId; 
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set the format of window, as per the PixelFormat types. 
		// This overrides the default format that is selected by 
		// the Window based on its window decorations.
		// System chooses a format that supports translucency (many alpha bits). 
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		// Enable extended window features. 
		// Flag for the "no title" feature, turning off the title at the top of the screen.
	   	requestWindowFeature(Window.FEATURE_NO_TITLE);
	   	// Set the flags of the window, as per the WindowManager.LayoutParams flags.
	   	// Window flag: Hide all screen decorations (e.g. status bar). 
	   	// while this window is displayed. This allows the window to use 
	   	// the entire display space for itself -- the status bar will be 
	   	// hidden when an app window with this flag set is on the top layer. 
	   	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	   	WindowManager.LayoutParams.FLAG_FULLSCREEN);      	 
        // Set the activity content from a layout resource. 
	   	// The resource will be inflated, adding all top-level views to the activity.
	   	setContentView(R.layout.activity_main);
		// Change the desired orientation of this activity. 
	   	// If the activity is currently in the foreground or 
	   	// otherwise impacting the screen orientation, 
	   	// the screen will immediately be changed 
	   	// (possibly causing the activity to be restarted). 
	   	// Otherwise, this will be used the next time the activity is visible.
        // Constant corresponding to portrait in the android.R.attr.screenOrientation attribute.
   	 	this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
   	 	// -- (Start)
   	 	ImgView = (ImageView)this.findViewById(R.id.ImgView);
   	 	txtView = (TextView)this.findViewById(R.id.txtView);
   	 	btn_Capture = (Button)this.findViewById(R.id.btn_Capture);
   	 	mSurfaceView  = (SurfaceView)this.findViewById(R.id.surView_Camera); 
   	 	rdg_Main = (RadioGroup) findViewById (R.id.rdg_Main);
   	 	rdb_Gray = (RadioButton) findViewById (R.id.rdb_Gray);
   	 	rdb_Comic = (RadioButton) findViewById (R.id.rdb_Comic);
   	 	// -- (End)
   	 	// Set and get SurfaceHolder
   	 	// Abstract interface to someone holding a display surface. 
   	 	// Allows you to control the surface size and format, 
   	 	// edit the pixels in the surface, and monitor changes to the surface. 
   	 	// This interface is typically available through the SurfaceView class. 
        // When using this interface from a thread other than the one running 
   	 	// its SurfaceView, you will want to carefully read the methods 
   	 	// lockCanvas and Callback.surfaceCreated().
   	 	SurfaceHolder mSurfaceHolder = mSurfaceView.getHolder(); 
   	 	mSurfaceHolder.addCallback(this);
   	 	mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
   	 	// To record the choice of image processing by the user 
   	 	iImageProcessingId = 0;
   	 	
        // To listen choice of the user and record it.
   	 	// There are two choices, including Gray and Comic.
   	 	rdg_Main.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				// Gray
				if ( checkedId == rdb_Gray.getId() )
				{
					iImageProcessingId = 0;
				}
				// Comic
				else if ( checkedId == rdb_Comic.getId() )
				{
					iImageProcessingId = 1;
				}
			}
		});
   	 	
        // To establish Camera.takePicture callback function.
   	    mPictureCB = new PictureCallback(){
        // Image processing.
   	    // Overwrite onPictureTake function.
   	    @Override
   		public void onPictureTaken(byte[] data, Camera camera){
   	   	    // We use the BitmapFactory to decode become raw data to bitmap format.
   			Bitmap mBitmap = BitmapFactory.decodeByteArray(data, 0 , data.length);
   			bitmapClone = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), mBitmap.getConfig());
   			bitmapClone.copy(mBitmap.getConfig(), true);
   			// For debug of switch.
   			if ( true )
   			{
   				int iY = 0;
   				int iX = 0;
   				int iPixel = 0;
   				int iRed = 0;
   				int iGreen = 0;
   				int iBlue = 0;
   				int iRGBAvg = 0;
   				// Gray of image processing.
   				if ( iImageProcessingId == 0 )
   				{
   					// The height of the image
   	       			for ( iY = 0; iY < bitmapClone.getHeight(); iY++ )
   	       			{
   	       				// The width of the image
   	       				for ( iX = 0; iX < bitmapClone.getWidth(); iX++ )
   	       				{
   	       					// To get pixel.
   	       					iPixel = mBitmap.getPixel(iX, iY);
   	       					// To get value of the red channel.
   	       					iRed = Color.red(iPixel);
   	       					// To get value of the green channel.
   	       					iGreen = Color.green(iPixel);
   	       					// To get value of the blue channel.
   	       					iBlue = Color.blue(iPixel);
   	       					// Compute value of gray.
   	       					iRGBAvg = ( iRed + iGreen + iBlue ) / 3;
   	       					// Set pixel of gray. 
   	   						bitmapClone.setPixel(iX, iY, Color.rgb(iRGBAvg, iRGBAvg, iRGBAvg));
   	       				}
   	       			}
   				}
   				// Comic
   				else if ( iImageProcessingId == 1 )
   				{
   					// The horizontal of Sobel matrix
   					int iSobel1 [][]= { { -1, -2, -1 }, { 0, 0, 0 }, { 1, 2, 1 }};
   					// The vertical of Sobel matrix
   					int iSobel2 [][]= { { -1, 0, -1 }, { -2, 0, 2 }, { 1, 0, 1 }};
   					// The ordered dither of the matrix
   					int iOrderDither [][] = { { 28, 255, 57 }, { 142, 113, 227 }, { 170, 198, 85 } };
   					float fYofYUV = 0.0f;
   					int iYofYUV = 0;
   					int iX1 = 0;
   					int iY1 = 0;
   					int iR = 1;
   					int iValue1 = 0;
   					int iValue2 = 0;
   					int iValue = 0;
   					// For horizontal index of Sobel matrix
   					int iX2 = 0;
   					// For vertical index of Sobel matrix
   					int iY2 = 0;
   					// Sobel filter of image processing
   					// The height of the image
   	       			for ( iY = 1; iY < bitmapClone.getHeight() - 1; iY++ )
   	       			{
   	   					// The width of the image
   	       				for ( iX = 1; iX < bitmapClone.getWidth() - 1; iX++ )
   	       				{
   	       					iY2= 0;
   	       					iValue1 = 0;
   	       					iValue2 = 0;
   	       					// The height of sobel matrix
   	       					for ( iY1 = iY - iR; iY1 <= iY + iR; iY1++ )
   	       					{
   	       						iX2 = 0;
   	   	       					// The width of sobel matrix
   	       						for ( iX1 = iX - iR; iX1 <= iX + iR; iX1++ )
   	       						{
   	       							// Get value of pixel
   	    	       					iPixel = mBitmap.getPixel(iX1, iY1);
   	    	       					// To get value of the red channel.
   	    	       					iRed = Color.red(iPixel);
   	    	       					// To get value of the green channel.
   	    	       					iGreen = Color.green(iPixel);
   	    	       					// To get value of the blue channel.
   	    	       					iBlue = Color.blue(iPixel);
   	    	       					// To compute value of gray.
   	    	       					fYofYUV = ( 0.299f * iRed ) + ( 0.587f * iGreen ) + ( 0.114f * iBlue );
   	    	       					// To compute sobel matrix, we transform float to integer.
   	    	       					iYofYUV = (int) fYofYUV;
   	    	       					// Convolution computing horizontal of sobel matrix.  
   	    	       					iValue1 += iYofYUV * iSobel1[iX2][iY2];
   	    	       					// Convolution computing vertical of sobel matrix.
   	    	       					iValue2 += iYofYUV * iSobel2[iX2][iY2];
   	    	       					iX2++;
   	       						}
   	       						iY2++;
   	       						iX2 = 0;
   	       					}
   	       					// Choice maximum value.
   	       					iValue = Math.max(iValue1, iValue2);
   	       					// The twenty-four is a magic number.
   	       					if ( iValue > 24 )
   	       					{
   	       						// Set the pixel is black.
   	       						bitmapClone.setPixel(iX, iY, Color.rgb(0, 0, 0));
   	       					}
   	       					else
   	       					{
   	       						// Set the pixel is white.
   	       						bitmapClone.setPixel(iX, iY, Color.rgb(255, 255, 255));
   	       					}
   	       				}
   	       			}
   	       	        // The height of the image. But we are stepping to three once.
   	       			for ( iY = 0; iY < bitmapClone.getHeight() - 3; iY+=3 )
   	       			{
   	       		        // The width of the image. But we are stepping to three once.
   	       				for ( iX = 0; iX < bitmapClone.getWidth() - 3; iX+=3 )
   	       				{
   	       					iY2 = 0;
   	       			        // The height of the matrix.
   	       					for ( iY1 = iY; iY1 <= iY + 2 ; iY1++ )
   	       					{
   	   	       					iX2 = 0;
   	   	       		            // The width of the matrix.
   	       						for ( iX1 = iX; iX1 <= iX + 2; iX1++ )
   	       						{
        	       					// Get value of pixel
   	    	       					iPixel = mBitmap.getPixel(iX1, iY1);
   	    	       			        // To get value of the red channel.
   	    	       					iRed = Color.red(iPixel);
        	    	       			// To get value of the green channel.
   	    	       					iGreen = Color.green(iPixel);
   	    	       			        // To get value of the blue channel.
   	    	       					iBlue = Color.blue(iPixel);
   	    	       			        // Compute value of gray.
   	    	       					fYofYUV = ( 0.299f * iRed ) + ( 0.587f * iGreen ) + ( 0.114f * iBlue );
        	    	       			// To compute sobel matrix, we transform float to integer.
   	    	       					iYofYUV = (int) fYofYUV;
   	    	       			        // If the gray depth more than the matrix, it should be white.
   	    	       					if ( iYofYUV >= iOrderDither[iX2][iY2] )
   	    	       					{
   	    	       						bitmapClone.setPixel(iX, iY, Color.rgb(255, 255, 255));
   	    	       					}
   	    	       			        // Otherwise, it should be black.
   	    	       					else
   	    	       					{
   	    	       						bitmapClone.setPixel(iX, iY, Color.rgb(0, 0, 0));
   	    	       					}
   	    	       					iX2++;
   	       						}
   	       						iY2++;
   	       						iX2 = 0;
   	       					}
   	       				}
   	       			}
   				}
   			}
   			// Set processed image to display.
   			ImgView.setImageBitmap(bitmapClone);
   			// Show the height of the image.
   			String strInfo = "";
   			strInfo = String.valueOf(mBitmap.getHeight());
   			txtView.setText(strInfo);
			// Restart camera to preview.
			camera.startPreview();
			// To disable auto focus of callback function. 
			camera.autoFocus(null);
   		}
   	 };
   	 
   	 // To establish Camera.AutoFocusCallback
   	 mAutoFocusCB = new AutoFocusCallback(){
    		@Override
    		public void onAutoFocus(boolean success, Camera camera){
    			// When auto focus is done and then we will take a picture.
    			if ( success == true )
    			{
					// Into take a picture of callback function.
					camera.takePicture(null, null, mPictureCB);					
    			}
    		}
   	 };
   	 
   	 // While a user press the take a picture button, when it starts auto focus.
   	 btn_Capture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					// To make sure the camera is open.
					if(mCamera != null){
						// Create a thread.
						new Thread(new Runnable() {
						    public void run() {
						    	// To execute the auto focus.
							    mCamera.autoFocus(mAutoFocusCB);
						    }
						  }).start();
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});    	 
   	 	
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
    	// Get parameters of the camera.
    	Camera.Parameters parameters = mCamera.getParameters();
		// Set size of the picture.
    	parameters.setPictureSize(640, 480);
    	// Set size of preview.
    	parameters.setPreviewSize(width, height);
    	// Set auto focus.
    	parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
    	// Set parameters of the camera.
		mCamera.setParameters(parameters);
		// Start preview.
		mCamera.startPreview();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
    	// If the camera is initially successful and then to open camera.
    	if ( mCamera == null )
    	{
    		mCamera = Camera.open();
    	}
    	try {
    		// Set SurfaceHolder.
    		mCamera.setPreviewDisplay(holder);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
    	// Stop preview.
    	mCamera.stopPreview();    	
    	// Release Camera.
    	mCamera.release();
	}

}
