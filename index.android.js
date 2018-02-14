'use strict'

import { NativeModules } from 'react-native';
// name as defined via ReactContextBaseJavaModule's getName
const { ImageCropperManager } = NativeModules;
const DEFAULT_OPTIONS = {
    title:'Crop Image',
    cropMenuCropButtonTitle:'Done',
    requestedSizeHeight:400,
    requestedSizeWidth:400,
    allowCounterRotation:false,
    allowFlipping:false,
    aspectRatio:[1,1]
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
