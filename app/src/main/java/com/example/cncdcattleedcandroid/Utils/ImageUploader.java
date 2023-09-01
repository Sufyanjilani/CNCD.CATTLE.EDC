package com.example.cncdcattleedcandroid.Utils;

import com.example.cncdcattleedcandroid.OfflineDb.Models.ImageInfo;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageUploader {

    public List<MultipartBody.Part> prepareImages(List<ImageInfo> imageInfoList) {
        List<MultipartBody.Part> parts = new ArrayList<>();

        for (ImageInfo imageInfo : imageInfoList) {
            File file = new File(imageInfo.getImagePath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData(
                    "images", // Replace with your desired field name
                    imageInfo.getImageName(),
                    requestBody
            );
            parts.add(part);
        }

        return parts;
    }
}
