package com.zainoz.theartofdev.edmodo.cropper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.zainoz.theartofdev.edmodo.utils.ReadableMapUtils;
import com.zainoz.theartofdev.edmodo.utils.ResponseHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class ImageCropperModule extends ReactContextBaseJavaModule implements ActivityEventListener{

  private static final String DURATION_SHORT_KEY = "SHORT";
  private static final String DURATION_LONG_KEY = "LONG";



  private static final HashMap<String, Enum> GUIDELINES = new HashMap<String, Enum>();

  private static final String TAG =  "imageCropperModule";
  private ReactApplicationContext reactContext;

  private ReadableMap options;
  protected Callback callback;

  private ResponseHelper responseHelper = new ResponseHelper();



  public ImageCropperModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    reactContext.addActivityEventListener(this);

    GUIDELINES.put("on", CropImageView.Guidelines.ON);
    GUIDELINES.put("off",CropImageView.Guidelines.OFF);
    GUIDELINES.put("on-touch",CropImageView.Guidelines.ON_TOUCH);

  }

  @Override
  public String getName() {
    return "ImageCropperManager";
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put(DURATION_SHORT_KEY, Toast.LENGTH_SHORT);
    constants.put(DURATION_LONG_KEY, Toast.LENGTH_LONG);
    return constants;
  }

  @ReactMethod
  public void selectImage(final ReadableMap options,final Callback callback) {
    Activity currentActivity = getCurrentActivity();

    if (currentActivity == null)
    {
      responseHelper.invokeError(callback, "can't find current Activity");
      return;
    }

    this.callback = callback;
    this.options = options;

    String GUIDELINES_KEY = "guideLines",
            TITLE_KEY="title",
            CROPSHAPE_KEY = "cropShape",
            CROPMENU_BUTTON_TITLE_KEY= "cropMenuCropButtonTitle",
            REQUESTED_SIZE_HEIGHT_KEY = "requestedSizeHeight",
            REQUESTED_SIZE_WIDTH_KEY = "requestedSizeWidth",
            ALLOW_COUNTER_ROTATION_KEY = "allowCounterRotation",
            ALLOW_FLIPPING_KEY = "allowFlipping",
            ASPECT_RATIO_KEY="aspectRatio"; //array [x,y]

    HashMap<String, Object> optionsHashMap = options.toHashMap();
    CropImageView.Guidelines guidelines = ((CropImageView.Guidelines)optionsHashMap.get(GUIDELINES_KEY))!=null ?
            (CropImageView.Guidelines)optionsHashMap.get(GUIDELINES_KEY) : CropImageView.Guidelines.ON;
    CropImageView.CropShape cropShape = ((CropImageView.CropShape)optionsHashMap.get(CROPSHAPE_KEY)) != null ?
            (CropImageView.CropShape)optionsHashMap.get(CROPSHAPE_KEY) : CropImageView.CropShape.RECTANGLE;

    CropImage.activity()
            .setGuidelines(guidelines)
            .setActivityTitle(options.getString(TITLE_KEY))
            .setCropShape(cropShape)
            .setCropMenuCropButtonTitle(options.getString(CROPMENU_BUTTON_TITLE_KEY))
            .setRequestedSize(
                    options.getInt(REQUESTED_SIZE_HEIGHT_KEY),
                    options.getInt(REQUESTED_SIZE_WIDTH_KEY))
            .setAllowCounterRotation(options.getBoolean(ALLOW_COUNTER_ROTATION_KEY))
            .setAllowFlipping(options.getBoolean(ALLOW_FLIPPING_KEY))
            .setAspectRatio(
                    options.getArray(ASPECT_RATIO_KEY).getInt(0)>0?options.getArray(ASPECT_RATIO_KEY).getInt(0):1,
                    options.getArray(ASPECT_RATIO_KEY).getInt(1)>0?options.getArray(ASPECT_RATIO_KEY).getInt(1):1)
            .start(getCurrentActivity());


  }


  @Override
  public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {

    if (requestCode != CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
      return;
    }

    CropImage.ActivityResult result = CropImage.getActivityResult(data);
    if (resultCode == RESULT_OK) {
      Uri resultUri = result.getUri();
      responseHelper.putString("uri",resultUri.getPath());
      responseHelper.putString("originalUri",result.getOriginalUri().getPath());
    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
      Exception error = result.getError();
    }

    responseHelper.invokeResponse(callback);
    callback = null;


//    responseHelper.invokeResponse(callback);
//    callback = null;
//    this.options = null;

  }

  @Override
  public void onNewIntent(Intent intent) {

  }


}