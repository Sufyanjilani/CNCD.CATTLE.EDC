package com.example.cncdcattleedcandroid.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageCompression {

    private Context context;
    private ImageCompressionListener listener;

    public ImageCompression(Context context) {
        this.context = context;
    }

    // Interface to provide compression result back to the caller
    public interface ImageCompressionListener {
        void onImageCompressionSuccess(File compressedImage);
        void onImageCompressionFailure(String errorMessage);
    }

    // Compress the image file in the background
    public void compressImageFile(File imageFile, ImageCompressionListener listener) {
        this.listener = listener;
        new ImageCompressionTask().execute(imageFile);
    }

    private class ImageCompressionTask extends AsyncTask<File, Void, File> {

        @Override
        protected File doInBackground(File... files) {
            File imageFile = files[0];
            File compressedImageFile = null;

            try {
                // Create a bitmap from the image file
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = calculateInSampleSize(imageFile);

                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

                // Compress the bitmap into a ByteArrayOutputStream
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int quality = 80; // You can adjust the quality as per your requirement
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);

                // Save the compressed bitmap to a new file
                compressedImageFile = createCompressedImageFile(imageFile);
                FileOutputStream fileOutputStream = new FileOutputStream(compressedImageFile);
                fileOutputStream.write(byteArrayOutputStream.toByteArray());
                fileOutputStream.close();

                // Recycle the bitmap to release memory
                bitmap.recycle();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            return compressedImageFile;
        }

        @Override
        protected void onPostExecute(File compressedImageFile) {
            if (compressedImageFile != null) {
                listener.onImageCompressionSuccess(compressedImageFile);
            } else {
                listener.onImageCompressionFailure("Image compression failed.");
            }
        }
    }

    // Helper method to calculate inSampleSize to reduce memory usage while decoding
    private int calculateInSampleSize(File imageFile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

        final int reqWidth = 800; // The desired maximum width for the compressed image
        final int reqHeight = 600; // The desired maximum height for the compressed image

        int inSampleSize = 1;
        if (options.outHeight > reqHeight || options.outWidth > reqWidth) {
            final int halfHeight = options.outHeight / 2;
            final int halfWidth = options.outWidth / 2;

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    // Helper method to create a new file for compressed image
    private File createCompressedImageFile(File originalImageFile) throws IOException {
        File storageDir = context.getExternalFilesDir("CompressedImages");
        if (storageDir != null && !storageDir.exists()) {
            storageDir.mkdirs();
        }

        String compressedFileName = "COMP_" + originalImageFile.getName();
        return new File(storageDir, compressedFileName);
    }
}
