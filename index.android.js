'use strict'

import { NativeModules } from 'react-native';
// name as defined via ReactContextBaseJavaModule's getName
const { ImageCropperManager } = NativeModules;
const DEFAULT_OPTIONS = {
    guideLines:"on",
    cropShape:"oval",
    title:'Crop Image',
    cropMenuCropButtonTitle:'Done',
    requestedSizeHeight:0,//remain original size
    requestedSizeWidth:0,//remain original size
    allowCounterRotation:false,
    allowFlipping:false,
    aspectRatio:[1,1],
    transferFileToExternalDir:false,
    externalDirectoryName:'CropImage',
    autoZoomEnabled:true,
    maxZoom:4,
    fixAspectRatio:false,
    initialCropWindowPaddingRatio:0.1, //10% - Set to 0 for initial crop window to fully cover the cropping image.
    borderCornerThickness:2,//dp - Set to 0 to remove.
    borderCornerOffset:5, //dp - Set to 0 place on top of the border lines.
    borderCornerLength:14, //dp
    guidelinesThickness:1, //dp
    snapRadius:3, //dp - Set to 0 to disable snapping.
    showCropOverlay:true,
    // showProgressBar:true
    minCropWindowWidthHeight:[40,40], //dp - min 10 dp,
    flipHorizontally:false,
    flipVertically:false,

}
module.exports = {
    ...ImageCropperManager,
    selectImage:function selectImage(options,callback){
        if (typeof options === 'function') {
            callback = options;
            options = {};
          }
          return ImageCropperManager.selectImage({...DEFAULT_OPTIONS, ...options}, callback)
    }
}
