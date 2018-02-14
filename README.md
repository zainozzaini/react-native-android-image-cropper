React-Native Android Image Cropper
=======

**Powerful** (Zoom, Rotation, Multi-Source), **customizable** (Shape, Limits, Style), **optimized** (Async, Sampling, Matrix) and **simple** image cropping library for React-Native Android Module.

![Crop](https://github.com/zainozzaini/react-native-android-image-cropper/blob/master/rn-android-crop-image.gif?raw=true)
## Install

`npm i react-native-android-image-cropper@latest --save`

### Automatic Installation

`react-native link`

### Manual Installation

1. Add the following lines to `android/settings.gradle`:
    ```gradle
    include ':react-native-android-image-cropper'
    project(':react-native-android-image-cropper').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-android-image-cropper/android')
    ```

4. Add the compile line to the dependencies in `android/app/build.gradle`:
    ```gradle
    dependencies {
        compile project(':react-native-android-image-cropper')
    }
    ```
6. Add the import and link the package in `MainApplication.java`:
    ```java
    import com.imagepicker.ImagePickerPackage; // <-- add this import

    public class MainApplication extends Application implements ReactApplication {
        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                new MainReactPackage(),
                new ImageCropperPackage() // <-- add this line
              
            );
        }
    }
    ```

## Usage
```javascript
import ImageCropper from "react-native-android-image-cropper";

// More info on all the options is below in the README

var options = {
  guideLines:"on-touch",
  cropShape:"rectangle",
  title:'MY EXAMPLE',
  cropMenuCropButtonTitle:'Done'
 }
 
 /**
 * The first arg is the options object for customization (it can also be null or omitted for default options),
 * The second arg is the callback which sends object: response 
 */
 
  ImageCropper.selectImage(options,(response)=>{
      if(response&&response.uri){
        this.setState({imageUri:response.uri})
      }
    });

```

### Options

Option Name | Default| Option/Info
------ | ---- | ------- 
guideLines (String)|on|off: no guidelines will be displayed. <br>on: guidelines will always be displayed. <br>on-touch: guidelines will be displayed when the crop window is touched. 
cropShape (String)|rectangle|rectangle,oval<br>use FixAspectRatio for Square / Circle.
title (String) |Crop Image|Set window title
cropMenuCropButtonTitle (String)|Done| Set crop button's title
requestedSizeHeight (int)|0|The height to resize the cropped image to
requestedSizeWidth (int)|0|The width to resize the cropped image to
allowCounterRotation (boolean)|false|Allow counter-clockwise rotation during cropping.<br> Note: if rotation is disabled this option has no effect.<br>
allowFlipping (boolean) |false|Allow flipping during cropping
aspectRatio (array [int,int])|[1,1]|X,Y value of the aspect ratio.<br>* Also sets fixes aspect ratio to TRUE.
transferFileToExternalDir (boolean)| false|Move cropped image from cache directory to external directory. Otherwise the image will stored in app cache folder.
externalDirectoryName (String)|CropImage|Name of folder if transferFileToExternalDir is true
autoZoomEnabled (boolean)|true|Auto-zoom functionality is enabled
maxZoom (int)|4|The max zoom allowed during cropping
fixAspectRatio (boolean)|false|Whether the width to height aspect ratio should be maintained or free to change.
initialCropWindowPaddingRatio (double)|0.1| 0% - 100%. The initial crop window padding from image borders in percentage of the cropping image dimensions.<br>Set to 0 for initial crop window to fully cover the cropping image.
borderCornerThickness (double)|2|dp - Thickness of the corner line (in pixels).Set to 0 to remove.
borderCornerOffset (double)|5|dp - The offset of corner line from crop window border (in pixels). Set to 0 to place on top of the border lines.
borderCornerLength (double)|12| dp - The length of the corner line away from the corner (in pixels)
guidelinesThickness (double)|1|dp - The thickness of the guidelines lines (in pixels)
snapRadius (double)|3|dp - An edge of the crop window will snap to the corresponding edge of a specified bounding box when the crop window edge is less than or equal to this distance (in pixels) away from the bounding box edge (in pixels).<br>Set 0 to disable snapping
showCropOverlay (boolean)|true|Show crop overlay UI what contains the crop window UI surrounded by background over the cropping image
minCropWindowWidthHeight (array [int,int])|[40,40]|dp - The min size the crop window is allowed to be (in pixels) minimum width/height 10
flipHorizontally|true|Whether the image should be flipped horizontally
flipVertically|true|Whether the image should be flipped vertically


## License

Forked from [ArthurHub/Android-Image-Cropper](https://github.com/ArthurHub/Android-Image-Cropper).

Originally forked from [edmodo/cropper](https://github.com/edmodo/cropper).

Copyright 2018 Zainoz Zaini, 2017, Arthur Teplitzki, 2013, Edmodo, Inc.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the   License.
You may obtain a copy of the License in the LICENSE file, or at:

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS   IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.