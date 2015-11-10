package ${YYAndroidPackageName};

import android.util.Log;
import android.app.Activity;
import android.os.Bundle;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.String;

import com.boomvideo.framework.dto.OperationResult;
import com.boomvideo.videotracker.BoomVideoResourceManager;
import com.boomvideo.videotracker.BoomVideoTrackerInf;
import com.boomvideo.framework.common.Logger;
import com.boomvideo.framework.dto.ERROR_TYPE;

import ${YYAndroidPackageName}.R;
import ${YYAndroidPackageName}.RunnerActivity;
import com.yoyogames.runner.RunnerJNILib;

public class GMSBoomVideoExt implements BoomVideoTrackerInf {

	private String BOOMGUID;
	private final String TAG="yoyo";
	
	public void BOOM_init(String GUID) {
		Log.i("yoyo","BOOM_init called");

		//boom.init(GUID);
		BOOMGUID=GUID;
	}
	
	public void BOOM_showPreroll() {
		Log.i("yoyo","BOOM_showPreroll() called");
		final String guid=BOOMGUID;
		RunnerActivity.ViewHandler.post(new Runnable(){
			@Override
			public void run() {
				BoomVideoResourceManager.getInstance().showVideoAds(guid,GMSBoomVideoExt.this,BoomVideoResourceManager.VIDEO_PLAYER_TYPE.PREROLL);
			}
		});
		
	}
	
	public void BOOM_showRewards() {
		Log.i("yoyo","BOOM_showRewards() called");
		final String guid=BOOMGUID;
		RunnerActivity.ViewHandler.post(new Runnable(){
			@Override
			public void run() {
				BoomVideoResourceManager.getInstance().showVideoAds(guid,GMSBoomVideoExt.this,BoomVideoResourceManager.VIDEO_PLAYER_TYPE.REWARDS);
			}
		});
	}
	
	public void BOOM_showOfferList() {
		Log.i("yoyo","BOOM_showOfferList() called");
		final String guid=BOOMGUID;
		RunnerActivity.ViewHandler.post(new Runnable(){
			@Override
			public void run() {
				BoomVideoResourceManager.getInstance().showVideoAds(guid,GMSBoomVideoExt.this,BoomVideoResourceManager.VIDEO_PLAYER_TYPE.OFFERLIST);
			}
		});
	}
	
	@Override
	public void onVideoTrackEvent(OperationResult arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getResultMessages()) {
			case AD_FAILED: {
				/*
				 * setLogs("AD FAILED..." + operationResult.getErrorInfo());*/
				String errorInfo = arg0.getErrorInfo().toString();
				if (errorInfo.equalsIgnoreCase(ERROR_TYPE.INTERNAL_ERROR.toString())) {
					RunnerActivity.ViewHandler.post(new Runnable(){
						@Override
						public void run() {
							Log.i(TAG, "BOOM: INTERNAL ERROR");
						}
					});
					int dsMapIndex=RunnerJNILib.jCreateDsMap(null, null, null);
					RunnerJNILib.DsMapAddString( dsMapIndex, "type", "kInternalError" );
					RunnerJNILib.DsMapAddDouble( dsMapIndex, "value", 1);
					RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex, 70);
					
				} else if (errorInfo.equalsIgnoreCase(ERROR_TYPE.NO_FILL.toString())) {
					RunnerActivity.ViewHandler.post(new Runnable(){
						@Override
						public void run() {
							Log.i(TAG, "BOOM: NO FILL");
						}
					});
					int dsMapIndex=RunnerJNILib.jCreateDsMap(null, null, null);
					RunnerJNILib.DsMapAddString( dsMapIndex, "type", "kNoFill" );
					RunnerJNILib.DsMapAddDouble( dsMapIndex, "value", 1);
					RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex, 70);
				} else {
					RunnerActivity.ViewHandler.post(new Runnable(){
						@Override
						public void run() {
							Log.i(TAG, "BOOM: NO NETWORK AVAILABLE");
						}
					});
					int dsMapIndex=RunnerJNILib.jCreateDsMap(null, null, null);
					RunnerJNILib.DsMapAddString( dsMapIndex, "type", "kNetworkError" );
					RunnerJNILib.DsMapAddDouble( dsMapIndex, "value", 1);
					RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex, 70);
				}
				}
				break;
			case AD_VIEW_CLOSED: {
				RunnerActivity.ViewHandler.post(new Runnable(){
					@Override
					public void run() {
						Log.i(TAG,"BOOM: AD_VIEW_CLOSED");
					}
				});
				int dsMapIndex=RunnerJNILib.jCreateDsMap(null, null, null);
				RunnerJNILib.DsMapAddString( dsMapIndex, "type", "kAdViewClosed" );
				RunnerJNILib.DsMapAddDouble( dsMapIndex, "value", 1);
				RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex, 70);
				
				}
				break;
			case AD_VIEW_LOADED: {
				RunnerActivity.ViewHandler.post(new Runnable(){
					@Override
					public void run() {
						Log.i(TAG,"BOOM: AD_VIEW_LOADED");
					}
				});
				int dsMapIndex=RunnerJNILib.jCreateDsMap(null, null, null);
				RunnerJNILib.DsMapAddString( dsMapIndex, "type", "kAdViewLoaded" );
				RunnerJNILib.DsMapAddDouble( dsMapIndex, "value", 1);
				RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex, 70);
				
				}
				break;
			case POINTS_REVEALED: {
				RunnerActivity.ViewHandler.post(new Runnable(){
					@Override
					public void run() {
						Log.i(TAG,"BOOM: POINTS_REVEALED");
					}
				});
				int dsMapIndex=RunnerJNILib.jCreateDsMap(null, null, null);
				RunnerJNILib.DsMapAddString( dsMapIndex, "type", "kPointsRevealed" );
				RunnerJNILib.DsMapAddDouble( dsMapIndex, "value", arg0.getPoints());
				RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex, 70);
				}
				break;
			case SUCCESSFUL_SHARED_ON_FACEBOOK: {
				Log.i(TAG,"BOOM: SUCCESSFUL_SHARED_ON_FACEBOOK");
				
				int dsMapIndex=RunnerJNILib.jCreateDsMap(null, null, null);
				RunnerJNILib.DsMapAddString( dsMapIndex, "type", "kSuccessfulSharedOnFacebook" );
				RunnerJNILib.DsMapAddDouble( dsMapIndex, "value", 1);
				RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex, 70);
				}
				break;
			case SUCCESSFUL_SHARED_ON_GOOGLEPLUS: {
				Log.i(TAG,"BOOM: SUCCESSFUL_SHARED_ON_GOOGLEPLUS");
				
				int dsMapIndex=RunnerJNILib.jCreateDsMap(null, null, null);
				RunnerJNILib.DsMapAddString( dsMapIndex, "type", "kSuccessfulSharedOnGooglePlus" );
				RunnerJNILib.DsMapAddDouble( dsMapIndex, "value", 1);
				RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex, 70);
				}
				break;
			case SUCCESSFUL_SHARED_ON_TWITTER: {
				Log.i(TAG,"BOOM: SUCCESSFUL_SHARED_ON_TWITTER");
				
				int dsMapIndex=RunnerJNILib.jCreateDsMap(null, null, null);
				RunnerJNILib.DsMapAddString( dsMapIndex, "type", "kSuccessfulSharedOnTwitter" );
				RunnerJNILib.DsMapAddDouble( dsMapIndex, "value", 1);
				RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex, 70);
				}
				break;
			default:
				break;
				
		}
		
	}
	
	@Override
	public Activity getContext() {
		// TODO Auto-generated method stub
		return RunnerActivity.CurrentActivity;
	}
		
	
}
