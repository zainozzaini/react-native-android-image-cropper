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
  TouchableOpacity,
  Image
  
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

  constructor(props){
    super(props);
    this.state = {
      imageUri:undefined
    }
  }

  callback = (image) =>{
    if(image&&image.uri){
      this.setState({imageUri:image.uri})
    }
    
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

        {
          this.state.imageUri !== undefined &&
            <Image
            style={{width: 400, height: 400}}
            source={{uri: 'file:///'+this.state.imageUri}}
          />
        }
        
        
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
