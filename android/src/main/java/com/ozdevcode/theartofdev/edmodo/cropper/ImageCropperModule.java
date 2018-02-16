package com.ozdevcode.theartofdev.edmodo.cropper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.ozdevcode.theartofdev.edmodo.utils.ResponseHelper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.ozdevcode.theartofdev.edmodo.utils.MediaUtils.transferImageToGallery;

public class ImageCropperModule extends ReactContextBaseJavaModule implements ActivityEventListener{


  private static final HashMap<String, Enum> GUIDELINES = new HashMap<String, Enum>();
  private static final HashMap<String, Enum> CROPSHAPES = new HashMap<String, Enum>();


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

    CROPSHAPES.put("rectangle",CropImageView.CropShape.RECTANGLE);
    CROPSHAPES.put("oval",CropImageView.CropShape.OVAL);

  }

  @Override
  public String getName() {
    return "ImageCropperManager";
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
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
            ASPECT_RATIO_KEY="aspectRatio", //array [x,y]
            AUTO_ZOOM_KEY="autoZoomEnabled",
            MAX_ZOOM_KEY="maxZoom",
            FIX_RATIO_KEY="fixAspectRatio",
            CROP_WINDOW_PADDING_RATIO_KEY="initialCropWindowPaddingRatio",
            BORDER_CORNER_THICKNESS_KEY="borderCornerThickness",
            BORDER_CORNER_OFFSET_KEY="borderCornerOffset",
            BORDER_CORNER_LENGTH_KEY="borderCornerLength",
            GUIDELINES_THICKNESS_KEY="guidelinesThickness",
            SNAP_RADIUS_KEY="snapRadius",
            SHOW_CROP_OVERLAY_KEY="showCropOverlay",
            MIN_CROP_WINDOW_KEY="minCropWindowWidthHeight",
            FLIP_HORIZONTALLY_KEY="flipHorizontally",
            FLIP_VERTICALLY_KEY="flipVertically";

    CropImageView.Guidelines guidelines = ((CropImageView.Guidelines)GUIDELINES.get(options.getString(GUIDELINES_KEY)))!=null ?
            (CropImageView.Guidelines)GUIDELINES.get(options.getString(GUIDELINES_KEY)) : CropImageView.Guidelines.ON;
    CropImageView.CropShape cropShape = ((CropImageView.CropShape)CROPSHAPES.get(options.getString(CROPSHAPE_KEY))) != null ?
            (CropImageView.CropShape)CROPSHAPES.get(options.getString(CROPSHAPE_KEY)) : CropImageView.CropShape.RECTANGLE;

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
            .setInitialCropWindowPaddingRatio(0)
            .setAspectRatio(
                    options.getArray(ASPECT_RATIO_KEY).getInt(0)>0?options.getArray(ASPECT_RATIO_KEY).getInt(0):1,
                    options.getArray(ASPECT_RATIO_KEY).getInt(1)>0?options.getArray(ASPECT_RATIO_KEY).getInt(1):1)
            .setAutoZoomEnabled(options.getBoolean(AUTO_ZOOM_KEY))
            .setMaxZoom(options.getInt(MAX_ZOOM_KEY))
            .setFixAspectRatio(options.getBoolean(FIX_RATIO_KEY))
            .setInitialCropWindowPaddingRatio((float)options.getDouble(CROP_WINDOW_PADDING_RATIO_KEY))
            .setBorderCornerThickness((float)options.getDouble((BORDER_CORNER_THICKNESS_KEY)))
            .setBorderCornerOffset((float)options.getDouble(BORDER_CORNER_OFFSET_KEY))
            .setBorderCornerLength((float)options.getDouble(BORDER_CORNER_LENGTH_KEY))
            .setGuidelinesThickness((float)options.getDouble(GUIDELINES_THICKNESS_KEY))
            .setSnapRadius((float)options.getDouble(SNAP_RADIUS_KEY))
            .setShowCropOverlay(options.getBoolean(SHOW_CROP_OVERLAY_KEY))
            .setMinCropWindowSize(
                    options.getArray(MIN_CROP_WINDOW_KEY).getInt(0)>10?options.getArray(MIN_CROP_WINDOW_KEY).getInt(0):10,
                    options.getArray(MIN_CROP_WINDOW_KEY).getInt(1)>10?options.getArray(MIN_CROP_WINDOW_KEY).getInt(1):10
            )
            .setFlipHorizontally(options.getBoolean(FLIP_HORIZONTALLY_KEY))
            .setFlipVertically(options.getBoolean(FLIP_VERTICALLY_KEY))
            .start(getCurrentActivity());




  }


  @Override
  public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {

    if (requestCode != CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
      return;
    }


      Boolean transferFileToExternalDir = options.hasKey("transferFileToExternalDir")&&
              options.getBoolean("transferFileToExternalDir");
      String externalDirectoryName = this.options.getString("externalDirectoryName");

      responseHelper.cleanResponse();

      Exception error =  null;
      Uri resultUri= null;

      CropImage.ActivityResult result = CropImage.getActivityResult(data);

      if (resultCode == RESULT_OK) {
        resultUri = result.getUri();

        //do transfer
        if(transferFileToExternalDir){
          try {
            File transImage= transferImageToGallery(getReactApplicationContext(), resultUri, externalDirectoryName);
            if(transImage!=null){
              resultUri = Uri.fromFile(transImage);
            }
          }catch (Exception ex){
            Log.e(TAG,ex.getMessage());
            error = ex;
          }

        }

      } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
        error= result.getError();
      }

    if(error!=null){
      responseHelper.invokeError(callback,error.getMessage());
    }else if(resultUri==null||result.getOriginalUri()==null){
      responseHelper.invokeResponse(callback);
    }else{
      responseHelper.putString("uri",resultUri.toString());
      responseHelper.putString("path",resultUri.getPath());
      responseHelper.putString("originalUri",result.getOriginalUri().toString());
      responseHelper.putString("originalPath",resultUri.getPath());
      responseHelper.invokeResponse(callback);
    }

    callback = null;
    this.options = null;

  }

  @Override
  public void onNewIntent(Intent intent) {

  }


}