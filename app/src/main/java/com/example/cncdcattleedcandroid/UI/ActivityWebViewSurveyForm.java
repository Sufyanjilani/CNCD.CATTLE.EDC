package com.example.cncdcattleedcandroid.UI;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.JsonReader;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.cncdcattleedcandroid.Network.ApiEndPoints;
import com.example.cncdcattleedcandroid.Network.RetrofitClientSurvey;
import com.example.cncdcattleedcandroid.OfflineDb.Helper.RealmDatabaseHlper;
import com.example.cncdcattleedcandroid.R;
import com.example.cncdcattleedcandroid.Utils.Constants;
import com.example.cncdcattleedcandroid.Utils.ImageCompression;
import com.example.cncdcattleedcandroid.ViewModels.WebViewSurveyViewModel;
import com.example.cncdcattleedcandroid.databinding.ActivityWebViewSurveyFormBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityWebViewSurveyForm extends AppCompatActivity {


    ActivityWebViewSurveyFormBinding webViewSurveyFormBinding;
    private static final int REQUEST_PERMISSIONS_CODE = 123;

    private FusedLocationProviderClient locationProviderClient;
    private PermissionRequest myRequest;
    private String mCM;
    private ValueCallback<Uri> mUM;
    private ValueCallback<Uri[]> mUMA;

    private final static int FCR = 1;

    String isInternetOnMessage;

    ProgressDialog pd;

    String tag = "tag";

    private ValueCallback<Uri> mUploadMessage;

    private static final int FILECHOOSER_RESULTCODE = 5173;

    WebViewSurveyViewModel surveyViewModel;

    Constants constants;

    RealmDatabaseHlper realmDatabaseHlper;

    Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webViewSurveyFormBinding = ActivityWebViewSurveyFormBinding.inflate(getLayoutInflater());
        setContentView(webViewSurveyFormBinding.getRoot());
        realm.init(this);
        realmDatabaseHlper = new RealmDatabaseHlper(this);
        realm = realmDatabaseHlper.InitializeRealm(this);
        realm = Realm.getDefaultInstance();



        surveyViewModel = new ViewModelProvider(ActivityWebViewSurveyForm.this).get(WebViewSurveyViewModel.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            setUpWebView();
        }

        constants = new Constants();
        setUpLocation();
        getDataforInjection();


        surveyViewModel.getJsonFromAPi("1");

        surveyViewModel.formdata.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (s != null) {

                    Loadgetjavascript(s);

                } else {

                    Toast.makeText(ActivityWebViewSurveyForm.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    public void setUpLocation() {

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);


    }


    public void Loadgetjavascript(String formjson) {

        String javascriptCode =
                "const survey = new Survey.Model(" + formjson + ");\n" +
                        "survey.onComplete.add((sender, options) => {\n" +
                        "    console.log(JSON.stringify(sender.data, null, 3));\n" +
                        "    Android.showProgressDialog()\n" +
                        "    console.log(sender.data)\n" +
                        "       const results = JSON.stringify(sender.data);\n" +
                        "Android.createJsonObject(results)\n" +
                        "Android.onFormSubmission(results);\n" +
                        "});\n" +
                        "\n" +
                        "$(\"#surveyElement\").Survey({ model: survey });";

        webViewSurveyFormBinding.surveyWebView.evaluateJavascript(javascriptCode, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            // Check if all permissions were granted
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted) {
                // All permissions granted, proceed with your actions
            } else {
                // Handle the scenario when some permissions were denied
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (Build.VERSION.SDK_INT >= 21) {
            Uri[] results = null;
            //Check if response is positive
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == FCR) {
                    if (null == mUMA) {
                        return;
                    }
                    if (intent == null) {
                        //Capture Photo if no image available
                        if (mCM != null) {
                            results = new Uri[]{Uri.parse(mCM)};
                        }
                    } else if (intent != null) {
                        if (mCM != null) {
                            results = new Uri[]{Uri.parse(mCM)};
                        }
                    } else {
                        String dataString = intent.getDataString();
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};

                        }
                    }
                }
            }
            Log.d("tag", results[0].getPath().toString());

            if (results != null) {
                final File file = new File(results[0].getPath());
                //getLocation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Utility.ImageCompression imageCompression = new Utility().new ImageCompression(ActivitySurvey.this);
                        //imageCompression.execute(file.getAbsolutePath(), Utility.imageFolderPath(project.getProjectName()), file.getName());
                        ImageCompression imageCompression = new ImageCompression(ActivityWebViewSurveyForm.this);
                        imageCompression.compressImageFile(file, new ImageCompression.ImageCompressionListener() {
                            @Override
                            public void onImageCompressionSuccess(File compressedImage) {
                                // Handle the compressed image file here
                                // For example, you can display it in an ImageView or upload it to a server.
                            }

                            @Override
                            public void onImageCompressionFailure(String errorMessage) {
                                // Handle the error when image compression fails.
                                Log.e("ImageCompression", errorMessage);
                            }
                        });
                        Log.d("Tag", "Image-Save");
                    }
                }, 1000);
                mUMA.onReceiveValue(results);
                mUMA = null;
            } else {
                mUMA.onReceiveValue(results);
                mUMA = null;
            }
        } else {
            if (requestCode == FCR) {
                if (null == mUM) return;
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUM.onReceiveValue(result);
                mUM = null;
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void setUpWebView() {

        webViewSurveyFormBinding.surveyWebView.getSettings().setJavaScriptEnabled(true);
        webViewSurveyFormBinding.surveyWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        webViewSurveyFormBinding.surveyWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webViewSurveyFormBinding.surveyWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webViewSurveyFormBinding.surveyWebView.getSettings().setDomStorageEnabled(true);
        webViewSurveyFormBinding.surveyWebView.getSettings().setAllowFileAccess(true);
        webViewSurveyFormBinding.surveyWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSurveyFormBinding.surveyWebView.getSettings().setDomStorageEnabled(true);
        webViewSurveyFormBinding.surveyWebView.getSettings().setForceDark(WebSettings.FORCE_DARK_ON);

        webViewSurveyFormBinding.surveyWebView.addJavascriptInterface(new JavaScriptInterface(), "Android");

        webViewSurveyFormBinding.surveyWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Logger.i("onPermissionRequest");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Logger.i("Grant", request.toString());
                    Logger.i("getOrigin", request.getOrigin().toString());
                    request.grant(request.getResources());
                }
                if (request.getOrigin().toString().equals("file:///")) {
                    Logger.i("GRANTED", "GRANTED");
                } else {
                    Log.d("DENIED", "DENIED");
                    request.deny();
                }
                myRequest = request;
            }


            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.i("WebView Log: ", consoleMessage.message() + " -- From line "
                        + consoleMessage.lineNumber() + " of "
                        + consoleMessage.sourceId());
                Logger.i("WebView Log: ", consoleMessage.message());
                Logger.i("WebView Log:", consoleMessage.message() + " -- From line "
                        + consoleMessage.lineNumber() + " of "
                        + consoleMessage.sourceId());
                return true;
            }

            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                this.openFileChooser(uploadMsg, "*/*");
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                this.openFileChooser(uploadMsg, acceptType, null);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(Intent.createChooser(i, "File Browser"),
                        FILECHOOSER_RESULTCODE);
            }

            public File createImageFile() throws IOException {
                // Create an image file name based on the current timestamp
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "IMG_" + timeStamp + ".jpg";
                Logger.i("1️⃣1️⃣1️⃣1️⃣1️⃣1️⃣1️⃣1️⃣1️⃣1️⃣1️⃣1️⃣1️⃣1️⃣1️⃣1️⃣1️⃣1️⃣1️⃣1️⃣1️⃣");
                // Get the directory where the image file will be stored (e.g., Pictures directory)
                File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DCIM), "CNCDImages");
                Logger.i("StorageDir 🤚🤚🤚🤚🤚🤚🤚🤚🤚", storageDir);
                // If the directory doesn't exist, create it
                if (!storageDir.exists()) {
                    Logger.i("2️2️⃣2️⃣2️⃣2️⃣2️⃣2️⃣2️⃣2️⃣2️⃣2️⃣2️⃣2️⃣2️⃣2️⃣2️⃣2️⃣2️⃣2️⃣2️⃣⃣");
                    storageDir.mkdirs();
                }

                // Create the image file
                File imageFile = new File(storageDir, imageFileName);
                return imageFile;
            }

            public boolean onShowFileChooser(
                    WebView webView, ValueCallback<Uri[]> filePathCallback,
                    FileChooserParams fileChooserParams) {

                if (mUMA != null) {
                    mUMA.onReceiveValue(null);
                }
                mUMA = filePathCallback;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(ActivityWebViewSurveyForm.this.getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        //photoFile = 'Utility.createImageFile()';
                        photoFile = createImageFile();
                        Logger.i("Image-File-Path", photoFile.getAbsolutePath());
                        Logger.i("filechooserparams", String.valueOf(fileChooserParams));
                        takePictureIntent.putExtra("PhotoPath", mCM);
                    } catch (Exception ex) {
                        Log.e("TAG", "Image file creation failed", ex);
                        Logger.i("Image-File-creation-failed", ex.toString());
                    }
                    if (photoFile != null) {
                        mCM = "file:" + photoFile.getAbsolutePath();
                        Logger.i("Image-File-Path", photoFile.getAbsolutePath());
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    } else {
                        takePictureIntent = null;
                    }
                }

                Intent contentSelectionIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("*/*");
                Intent[] intentArray;
                if (takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                } else {
                    intentArray = new Intent[0];
                }

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                startActivityForResult(chooserIntent, FCR);
                return true;
            }


        });

        webViewSurveyFormBinding.surveyWebView.loadUrl("file:///android_asset/html/index.html");
        //webViewSurveyFormBinding.surveyWebView.loadUrl("https://www.google.com");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            webViewSurveyFormBinding.surveyWebView.setForceDarkAllowed(true);

        }
    }

    class JavaScriptInterface {

//        @JavascriptInterface
//        public void showdialog(){
//            Toast.makeText(ActivityWebViewSurveyForm.this,"Hello Earth",Toast.LENGTH_SHORT).show();
//        }
//


//        @JavascriptInterface
//        public void onFormSubmission(String jsonData) {
//
//            getcurrentlocationEnd();
//            Log.d("TAG", "got json back");
//            //Toast.makeText(ActivityWebViewSurveyForm.this, "got json back" + jsonData.toString(), Toast.LENGTH_SHORT).show();
//            getTimeStamp("end-");
//            formdata = jsonData.replace("\\\"", "\"");
//
//
//        }

//        @JavascriptInterface
//        public void createJsonObject(String formdata) throws JSONException {
//            // if (latitudestart != 0 && longitudestart != 0 && latitudeEnd != 0 && longitideEnd != 0) {
//
//            JSONObject object = new JSONObject();
//            JSONObject formresponse = new JSONObject();
//            JSONObject participantdata = new JSONObject();
//
//            participantdata.put("name", "john");
//            participantdata.put("age", "25");
//            participantdata.put("gender", "male");
//
//            formresponse.put("participantData", participantdata);
//            formresponse.put("formData", formdata);
//
//
//            object.put("studyID", "");
//            object.put("coordinates_Start_Latitude", latitudestart);
//            object.put("coordinates_Start_Longitude", longitudestart);
//            object.put("coordinates_End_Latitude", latitudeEnd);
//            object.put("coordinates_End_Longitude", longitideEnd);
//            object.put("survey_StartTime", startTime);
//            object.put("survey_EndTime", getTimeStamp("end-"));
//            object.put("PgrForms_ID", "");
//            object.put("diseaseForms_ID", "");
//            object.put("user_ID", 0);
//            object.put("centerID", 0);
//            object.put("formData", formdata);
//
//
//            Log.d(tag, object.toString());
////                Intent i = new Intent(ActivityWebViewSurveyForm.this, Form2Activity.class);
////                i.putExtra("object", object.toString());
////                startActivity(i);
//
////            } else {
////
////                dissmissdialog();
////            }
//
//        }

        @JavascriptInterface
        public String isInternetONJS() {
            String isinternetonJavascript = isInternetOnMessage;
            Log.d(tag, isinternetonJavascript.toString() + "why");
            return isinternetonJavascript;
        }


        @JavascriptInterface
        public void dissmissdialog() {

            pd.dismiss();
        }

        @JavascriptInterface
        public void showProgressDialog() {
            pd = new ProgressDialog(ActivityWebViewSurveyForm.this);
            pd.setTitle("Please wait");
            pd.setMessage("Submitting your survey");
            pd.show();
        }

//        @JavascriptInterface
//        public String getSurveyJson(){
//            return json.toString();
//        }

    }


    public void getDataforInjection() {

        Call<JsonObject> apireader = new RetrofitClientSurvey().retrofitclient().getcities();
        apireader.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {

                    JsonObject citiesObject = response.body();
                    Log.d(constants.Tag, citiesObject.toString());

                    JsonArray data = citiesObject.get("data").getAsJsonArray();

                    JsonObject obj1 = data.get(0).getAsJsonObject();
                    String country = obj1.get("country").getAsString();
                    String countryInitials = obj1.get("countryInitials").getAsString();
                    String countrycode = obj1.get("countryCode").toString();
                    JsonArray statesAndCities = obj1.get("statesAndCities").getAsJsonArray();
                    JsonObject  statesAndCitiesObject1 = statesAndCities.get(0).getAsJsonObject();
                    String statename = statesAndCitiesObject1.get("stateName").getAsString();
                    JsonArray cities= statesAndCitiesObject1.get("cities").getAsJsonArray().getAsJsonArray();



                    realmDatabaseHlper.insertCities(country,countryInitials,countrycode,statename,cities.toString());




                    Log.d(constants.Tag, country);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Log.d(constants.Tag, t.getMessage().toString());
            }
        });


    }
}