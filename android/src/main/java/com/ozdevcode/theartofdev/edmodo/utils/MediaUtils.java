package com.ozdevcode.theartofdev.edmodo.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by zainoz on 14/02/2018.
 */

public class MediaUtils {

    public static File transferImageToGallery(Context context,Uri uri,String folderName){
        try{
            File originalFile = new File(uri.getPath());
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),uri);

            FileOutputStream outStream = null;
            File dir = null;
            if(folderName!=null&&!folderName.isEmpty()){
                File sdCard = Environment.getExternalStorageDirectory();
                dir = new File(sdCard.getAbsolutePath() + "/"+folderName);
            }else{
                dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"");
            }
            dir.mkdirs();
            String fileName = String.format("%d.jpg", System.currentTimeMillis());
            File outFile = new File(dir, fileName);
            outStream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(outFile));
            context.sendBroadcast(intent);

            //Delete original file
            if(outFile.exists()&&originalFile.exists()){
                originalFile.delete();
            }


            return outFile;


        }catch (Exception e){

        }

        return null;
    }
}
