/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View,
  TouchableOpacity
  
} from 'react-native';

import ImageCropper from "react-native-android-image-cropper";

type Props = {};

const options = {
  title:'Crop Image',
    cropMenuCropButtonTitle:'Done',
    requestedSizeHeight:400,
    requestedSizeWidth:400,
    allowCounterRotation:false,
    allowFlipping:false,
    aspectRatio:[1,1]
}
export default class App extends Component<Props> {

  callback = (data) =>{
    alert('callback-'+data);
    console.log(data);
  }

  selectImage = ()=>{
    ImageCropper.selectImage(options,this.callback);
  }
 
  render() {
    
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <TouchableOpacity onPress={this.selectImage}>
            <Text>Select Image</Text>
        </TouchableOpacity>
        
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
