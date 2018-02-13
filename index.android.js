'use strict'

import { NativeModules } from 'react-native'
// name as defined via ReactContextBaseJavaModule's getName
const { ImageCropperManager } = NativeModules;

module.exports = {
    ...ImageCropperManager
}
