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
  guideLines:"on-touch",
  cropShape:"rectangle",
  title:'MY EXAMPLE',
    // cropMenuCropButtonTitle:'Done',
    // requestedSizeHeight:400,
    // requestedSizeWidth:400,
    // allowCounterRotation:true,
    // allowFlipping:true,
    // aspectRatio:[1,1],
    // transferFileToExternalDir:true,
    // externalDirectoryName:'MyExample',
    // autoZoomEnabled:true,
    // maxZoom:9,
    // fixAspectRatio:true,
    // initialCropWindowPaddingRatio:0.4, //10% - Set to 0 for initial crop window to fully cover the cropping image. Max 0.5
    // borderCornerThickness:10,//dp - Set to 0 to remove.
    // borderCornerOffset:10, //dp - Set to 0 place on top of the border lines.
    // borderCornerLength:10, //dp
    // guidelinesThickness:5, //dp
    // snapRadius:5, //dp - Set to 0 to disable snapping.
    // showCropOverlay:true,
    // // showProgressBar:true
    // minCropWindowWidthHeight:[40,40], //dp - min 10 dp,
    // flipHorizontally:true,
    // flipVertically:true,

}
export default class App extends Component<Props> {

  constructor(props){
    super(props);
    this.state = {
      imageUri:undefined
    }
  }


  selectImage = ()=>{
    ImageCropper.selectImage(options,(response)=>{
      
      console.log(response);
    
      //error throwns with response.error

      if(response&&response.uri){
        this.setState({imageUri:response.uri})
      }
    });
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
            style={{width: 200, height: 200}}
            source={{uri: this.state.imageUri}}
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
