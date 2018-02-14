React-Native Android Image Cropper
=======

**Powerful** (Zoom, Rotation, Multi-Source), **customizable** (Shape, Limits, Style), **optimized** (Async, Sampling, Matrix) and **simple** image cropping library for React-Native Android Module.

![Crop](https://github.com/zainozzaini/react-native-android-image-cropper/blob/master/rn-android-crop-image.gif?raw=true)

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


## License

Forked from [ArthurHub/Android-Image-Cropper](https://github.com/ArthurHub/Android-Image-Cropper).

Originally forked from [edmodo/cropper](https://github.com/edmodo/cropper).

Copyright 2018 Zainoz Zaini, 2017, Arthur Teplitzki, 2013, Edmodo, Inc.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the   License.
You may obtain a copy of the License in the LICENSE file, or at:

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS   IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.