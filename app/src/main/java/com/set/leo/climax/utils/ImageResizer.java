package com.set.leo.climax.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileDescriptor;

/**
 * Author    LeoCheung
 * Email     leocheung4ever@gmail.com
 * Description  抽象压缩功能 - 图片缩放器
 * Date          Author          Version          Description
 * ------------------------------------------------------------------
 * 2016/4/23     LeoCheung       1.0              1.0
 * Why & What is modified:
 */
public class ImageResizer {

    private static final String TAG = "ImageResizer";

    public ImageResizer() {
    }

    /**
     * from the resources to decode the sampled bitmap
     *
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodesBounds = true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        //Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        //Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);

    }

    /**
     * from file descriptor to decode sampled bitmap
     *
     * @param fd
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap decodeSampledBitmapFromFileDescriptor(FileDescriptor fd, int reqWidth, int reqHeight) {

        //First decode with inJustDecodesBounds =true to check dimension
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);

        //Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        //Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);

    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }
        // raw width and height of image
        final int outHeight = options.outHeight;
        final int outWidth = options.outWidth;
        Log.d(TAG, "origin,w=" + outWidth + " h=" + outHeight);
        int inSampleSize = 1;

        if (outHeight > reqHeight || outWidth > reqWidth) {
            final int halfHeight = outHeight / 2;
            final int halfWidth = outWidth / 2;

            // Calculate the largest inSampleSize value that is a power of 2
            // and keeps both raw height and width larger than the requested height and width
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        Log.d(TAG, "sampleSize:" + inSampleSize);
        return inSampleSize;
    }
}
