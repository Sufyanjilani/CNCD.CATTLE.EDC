package com.example.cncdcattleedcandroid.UI;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.webkit.WebSettingsCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.cncdcattleedcandroid.Network.RetrofitClientSurvey;
import com.example.cncdcattleedcandroid.OfflineDb.Helper.RealmDatabaseHlper;
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.Utils.Constants;
import com.example.cncdcattleedcandroid.Utils.ImageCompression;
import com.example.cncdcattleedcandroid.Utils.LoadingDialog;
import com.example.cncdcattleedcandroid.ViewModels.WebViewSurveyViewModel;
import com.example.cncdcattleedcandroid.databinding.ActivityFarmerProfileBinding;
import com.example.cncdcattleedcandroid.databinding.ActivityWebViewSurveyFormBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityWebViewSurveyForm extends AppCompatActivity {


    ActivityWebViewSurveyFormBinding webViewSurveyFormBinding;
    private static final int REQUEST_PERMISSIONS_CODE = 123;

    MutableLiveData<Boolean> beginpost;

    SessionManager sessionManager;

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

    private String formId = "";

    private String formdata = "";

    private String entityId = "";


    private String formName;


    LoadingDialog loadingDialog;

    RetrofitClientSurvey retrofitClientSurvey;

    String farmId, farmerId;


    //live data for end Coordinates

    MutableLiveData<Boolean> isEndCoordinatesObtained = new MutableLiveData<>();

    ArrayList<String> locationCoordinates = new ArrayList<>();


    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webViewSurveyFormBinding = ActivityWebViewSurveyFormBinding.inflate(getLayoutInflater());
        setContentView(webViewSurveyFormBinding.getRoot());
        realm.init(this);
        realmDatabaseHlper = new RealmDatabaseHlper(this);
        realm = realmDatabaseHlper.InitializeRealm(this);
        realm = Realm.getDefaultInstance();
        sessionManager = new SessionManager(this);

        constants = new Constants();
        setUpLocation();
        surveyViewModel = new ViewModelProvider(ActivityWebViewSurveyForm.this).get(WebViewSurveyViewModel.class);
        LoadWebViewWithDifferentSettings();


    }


    public void setUpLocation() {

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);


    }


    public void Loadgetjavascript2(String formjson, String function) {

        String javascriptCode =
                "      window['surveyjs-widgets'].inputmask(Survey);\n" +
                        "      window['surveyjs-widgets'].nouislider(Survey);\n" +
                        "const themeJson = {\n" +
                        "  \"cssVariables\": {\n" +
                        "    \"--sjs-general-backcolor\": \"rgba(255, 255, 255, 1)\",\n" +
                        "    \"--sjs-general-backcolor-dark\": \"rgba(248, 248, 248, 1)\",\n" +
                        "    \"--sjs-general-backcolor-dim\": \"rgba(243, 243, 243, 1)\",\n" +
                        "    \"--sjs-general-backcolor-dim-light\": \"rgba(249, 249, 249, 1)\",\n" +
                        "    \"--sjs-general-backcolor-dim-dark\": \"rgba(243, 243, 243, 1)\",\n" +
                        "    \"--sjs-general-forecolor\": \"rgba(0, 0, 0, 0.91)\",\n" +
                        "    \"--sjs-general-forecolor-light\": \"rgba(0, 0, 0, 0.45)\",\n" +
                        "    \"--sjs-general-dim-forecolor\": \"rgba(0, 0, 0, 0.91)\",\n" +
                        "    \"--sjs-general-dim-forecolor-light\": \"rgba(0, 0, 0, 0.45)\",\n" +
                        "    \"--sjs-primary-backcolor\": \"#2772cb\",\n" +
                        "    \"--sjs-primary-backcolor-light\": \"rgba(NaN, NaN, NaN, 0.07)\",\n" +
                        "    \"--sjs-primary-backcolor-dark\": \"rgba(NaN, NaN, NaN, 1)\",\n" +
                        "    \"--sjs-primary-forecolor\": \"rgba(255, 255, 255, 1)\",\n" +
                        "    \"--sjs-primary-forecolor-light\": \"rgba(255, 255, 255, 0.25)\",\n" +
                        "    \"--sjs-base-unit\": \"8px\",\n" +
                        "    \"--sjs-corner-radius\": \"4px\",\n" +
                        "    \"--sjs-secondary-backcolor\": \"rgba(255, 152, 20, 1)\",\n" +
                        "    \"--sjs-secondary-backcolor-light\": \"rgba(255, 152, 20, 0.1)\",\n" +
                        "    \"--sjs-secondary-backcolor-semi-light\": \"rgba(255, 152, 20, 0.25)\",\n" +
                        "    \"--sjs-secondary-forecolor\": \"rgba(255, 255, 255, 1)\",\n" +
                        "    \"--sjs-secondary-forecolor-light\": \"rgba(255, 255, 255, 0.25)\",\n" +
                        "    \"--sjs-shadow-small\": \"0px 1px 2px 0px rgba(0, 0, 0, 0.15)\",\n" +
                        "    \"--sjs-shadow-medium\": \"0px 2px 6px 0px rgba(0, 0, 0, 0.1)\",\n" +
                        "    \"--sjs-shadow-large\": \"0px 8px 16px 0px rgba(0, 0, 0, 0.1)\",\n" +
                        "    \"--sjs-shadow-inner\": \"inset 0px 1px 2px 0px rgba(0, 0, 0, 0.15)\",\n" +
                        "    \"--sjs-border-light\": \"rgba(0, 0, 0, 0.09)\",\n" +
                        "    \"--sjs-border-default\": \"rgba(0, 0, 0, 0.16)\",\n" +
                        "    \"--sjs-border-inside\": \"rgba(0, 0, 0, 0.16)\",\n" +
                        "    \"--sjs-special-red\": \"rgba(229, 10, 62, 1)\",\n" +
                        "    \"--sjs-special-red-light\": \"rgba(229, 10, 62, 0.1)\",\n" +
                        "    \"--sjs-special-red-forecolor\": \"rgba(255, 255, 255, 1)\",\n" +
                        "    \"--sjs-special-green\": \"rgba(25, 179, 148, 1)\",\n" +
                        "    \"--sjs-special-green-light\": \"rgba(25, 179, 148, 0.1)\",\n" +
                        "    \"--sjs-special-green-forecolor\": \"rgba(255, 255, 255, 1)\",\n" +
                        "    \"--sjs-special-blue\": \"rgba(67, 127, 217, 1)\",\n" +
                        "    \"--sjs-special-blue-light\": \"rgba(67, 127, 217, 0.1)\",\n" +
                        "    \"--sjs-special-blue-forecolor\": \"rgba(255, 255, 255, 1)\",\n" +
                        "    \"--sjs-special-yellow\": \"rgba(255, 152, 20, 1)\",\n" +
                        "    \"--sjs-special-yellow-light\": \"rgba(255, 152, 20, 0.1)\",\n" +
                        "    \"--sjs-special-yellow-forecolor\": \"rgba(255, 255, 255, 1)\"\n" +
                        "  },\n" +
                        "  \"themeName\": \"default\",\n" +
                        "  \"colorPalette\": \"light\"\n" +
                        "}" +
                        "\n" +
                        "  const survey = new Survey.Model(" + formjson + ");\n" +
                        "\n" +
                        "  // You can delete the line below if you do not use a customized theme\n" +
                        "  survey.applyTheme(themeJson);\n" +
                        "  survey.onComplete.add((sender, options) => {\n" +
                        "Android.ShowProgressDialog()\n" +
                        "setTimeout(function(){\n" +
                        " const results = JSON.stringify(sender.data);\n" +
                        function + "\n" +
                        "},2000)" +
                        "  });\n" +
                        "\n" +
                        "\n" +
                        "  $(\"#surveyElement\").Survey({ model: survey });";


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

        webViewSurveyFormBinding.surveyWebView.addJavascriptInterface(new JavaScriptInterface(this), "Android");

        webViewSurveyFormBinding.surveyWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (!sessionManager.getthemstate()) {
                    Loadgetjavascript(JsonToInject(getSurveyFormData(formId)));
                    injectCities();

                } else {

                    ForceWebViewToDarkMode(JsonToInject(getSurveyFormData(formId)));
                    InjectDarkMode();
                }
            }
        });

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

        Context context;

        //
        public JavaScriptInterface(Context context) {

            this.context = context;
        }

        @JavascriptInterface
        public String isInternetONJS() {
            String isinternetonJavascript = isInternetOnMessage;
            Log.d(tag, isinternetonJavascript.toString() + "why");
            return isinternetonJavascript;
        }


        @JavascriptInterface
        public void dissmissdialog() {

            loadingDialog.dissmissDialog();
        }

        @JavascriptInterface
        public void showProgressDialog() {
            pd = new ProgressDialog(ActivityWebViewSurveyForm.this);
            pd.setTitle("Please wait");
            pd.setMessage("Submitting your survey");
            pd.show();
        }

        @JavascriptInterface
        public void getSubmittedData(String data) {

            //ArrayList<String> locationarray = getcurrentlocationEnd();


            Log.d(constants.info, "Submit function called");
            loadingDialog.dissmissDialog();
            formdata = data;
            String form_start_time = sessionManager.getStartTimestamp();
            String form_end_time = getTimeStamp("end-");
            String start_latitude = sessionManager.getLatitudeStart();
            String start_longitude = sessionManager.getLongitudeStart();
            String end_latitude = "0.0";
            String end_longitude = "0.0";
            String appversion = "";

            try {
                appversion = getPackageManager()
                        .getPackageInfo(getPackageName(), 0).versionName;
                ;
            } catch (PackageManager.NameNotFoundException nameNotFoundException) {

                Log.d("package", nameNotFoundException.getMessage().toString());
            }

            realmDatabaseHlper.InsertCompletedForm(
                    Integer.parseInt(formId),
                    form_start_time,
                    form_end_time,
                    start_latitude,
                    start_longitude,
                    end_latitude,
                    end_longitude,
                    appversion,
                    data
            );
            Log.d(constants.info, formdata);
            Log.d(constants.info, appversion);
            Log.d(constants.info, formId.toString());


        }

        @JavascriptInterface
        public void ShowProgressDialog() {

            loadingDialog.ShowCustomLoadingDialog();
        }


        @JavascriptInterface
        public void changevalue(Boolean value, String data) {

            beginpost.postValue(value);
            formdata = data;
            loadingDialog.dissmissDialog();

        }


        @JavascriptInterface
        public void PostFirstFormData(String formjson) {


            String questionnaireID = entityId;
            String appVersion = "";


            Log.d(constants.info,"post called");
            loadingDialog.dissmissDialog();


            try {
                appVersion = getPackageManager()
                        .getPackageInfo(getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }


            String locationCoordinate = "23.32,45.32";
            String formJSON = formjson;


            String accessToken = sessionManager.getbearer();
            String interviewtakenAt = getTimeStamp("Interview_taken_at-");
            String interviewTimeStart = sessionManager.getStartTimestamp();
            String interviewTimeEnd = getTimeStamp("end-");


            String locationCoordinatesStart = sessionManager.getLatitudeStart() + "," +
                    sessionManager.getLongitudeStart();


            String locationCoordinatesEnd = "0.0,0.0";


            surveyViewModel.PostFirstFormData(context,
                    questionnaireID,
                    appVersion,
                    locationCoordinate,
                    formJSON,
                    accessToken,
                    interviewtakenAt,
                    interviewTimeStart,
                    interviewTimeEnd,
                    locationCoordinatesStart,
                    locationCoordinatesEnd
            );

        }


        @JavascriptInterface
        public void PostFirstFormDatacattle(String formjson) {


            String questionnaireID = entityId;
            String appVersion = "";


            Log.d(constants.info, "post called");
            loadingDialog.dissmissDialog();


            try {
                appVersion = getPackageManager()
                        .getPackageInfo(getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }


            String locationCoordinate = "23.32,45.32";
            String formJSON = formjson;


            String accessToken = sessionManager.getbearer();
            String interviewtakenAt = getTimeStamp("Interview_taken_at-");
            String interviewTimeStart = sessionManager.getStartTimestamp();
            String interviewTimeEnd = getTimeStamp("end-");


            String locationCoordinatesStart = sessionManager.getLatitudeStart() + "," +
                    sessionManager.getLongitudeStart();


            String locationCoordinatesEnd = "0.0,0.0";


            surveyViewModel.PostFirstCattleFormData(context,
                    questionnaireID,
                    farmId,
                    farmerId,
                    appVersion,
                    locationCoordinate,
                    formJSON,
                    accessToken,
                    interviewtakenAt,
                    interviewTimeStart,
                    interviewTimeEnd,
                    locationCoordinatesStart,
                    locationCoordinatesEnd
            );

        }


        @JavascriptInterface
        public void SubmitSecondForm(String formjson) {
            String questionnaireID = entityId;
            String appVersion = "";


            Log.d(constants.info, "post called");
            loadingDialog.dissmissDialog();


            try {
                appVersion = getPackageManager()
                        .getPackageInfo(getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }


            String locationCoordinate = "23.32,45.32";
            String formJSON = formjson;


            String accessToken = sessionManager.getbearer();
            String interviewtakenAt = getTimeStamp("Interview_taken_at-");
            String interviewTimeStart = sessionManager.getStartTimestamp();
            String interviewTimeEnd = getTimeStamp("end-");


            String locationCoordinatesStart = sessionManager.getLatitudeStart() + "," +
                    sessionManager.getLongitudeStart();


            String locationCoordinatesEnd = "0.0,0.0";


            surveyViewModel.SubmitSecondForm(context,
                    questionnaireID,
                    sessionManager.get_Farm_ID(),
                    sessionManager.get_Farmer_ID(),
                    appVersion,
                    locationCoordinate,
                    formJSON,
                    accessToken,
                    interviewtakenAt,
                    interviewTimeStart,
                    interviewTimeEnd,
                    locationCoordinatesStart,
                    locationCoordinatesEnd,
                    entityId
            );


        }

        @JavascriptInterface
        public void SubmitThirdForm(String formjson) {
            String questionnaireID = entityId;
            String appVersion = "";


            Log.d(constants.info, "post called");
            loadingDialog.dissmissDialog();


            try {
                appVersion = getPackageManager()
                        .getPackageInfo(getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }


            String locationCoordinate = "23.32,45.32";
            String formJSON = formjson;


            String accessToken = sessionManager.getbearer();
            String interviewtakenAt = getTimeStamp("Interview_taken_at-");
            String interviewTimeStart = sessionManager.getStartTimestamp();
            String interviewTimeEnd = getTimeStamp("end-");


            String locationCoordinatesStart = sessionManager.getLatitudeStart() + "," +
                    sessionManager.getLongitudeStart();


            String locationCoordinatesEnd = "0.0,0.0";


            surveyViewModel.SubmitThirdForm(context,
                    questionnaireID,
                    sessionManager.get_Farm_ID(),
                    sessionManager.get_Farmer_ID(),
                    appVersion,
                    locationCoordinate,
                    formJSON,
                    accessToken,
                    interviewtakenAt,
                    interviewTimeStart,
                    interviewTimeEnd,
                    locationCoordinatesStart,
                    locationCoordinatesEnd,
                    entityId
            );


        }


        @JavascriptInterface
        public void SubmitCattleEntityForm(String formjson) {
            String questionnaireID = entityId;
            String appVersion = "";


            Log.d(constants.info, "post called");
            loadingDialog.dissmissDialog();


            try {
                appVersion = getPackageManager()
                        .getPackageInfo(getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }


            String locationCoordinate = "23.32,45.32";
            String formJSON = formjson;


            String accessToken = sessionManager.getbearer();
            String interviewtakenAt = getTimeStamp("Interview_taken_at-");
            String interviewTimeStart = sessionManager.getStartTimestamp();
            String interviewTimeEnd = getTimeStamp("end-");


            String locationCoordinatesStart = sessionManager.getLatitudeStart() + "," +
                    sessionManager.getLongitudeStart();


            String locationCoordinatesEnd = "0.0,0.0";


            surveyViewModel.SubmitSecondFormCattle(context,
                    questionnaireID,
                    appVersion,
                    locationCoordinate,
                    formJSON,
                    accessToken,
                    interviewtakenAt,
                    interviewTimeStart,
                    interviewTimeEnd,
                    locationCoordinatesStart,
                    locationCoordinatesEnd

            );


        }


        @JavascriptInterface
        public void SubmitCattleEntityForm3(String formjson) {
            String questionnaireID = entityId;
            String appVersion = "";


            Log.d(constants.info, "post called");
            loadingDialog.dissmissDialog();


            try {
                appVersion = getPackageManager()
                        .getPackageInfo(getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }


            String locationCoordinate = "23.32,45.32";
            String formJSON = formjson;


            String accessToken = sessionManager.getbearer();
            String interviewtakenAt = getTimeStamp("Interview_taken_at-");
            String interviewTimeStart = sessionManager.getStartTimestamp();
            String interviewTimeEnd = getTimeStamp("end-");


            String locationCoordinatesStart = sessionManager.getLatitudeStart() + "," +
                    sessionManager.getLongitudeStart();


            String locationCoordinatesEnd = "0.0,0.0";


            surveyViewModel.SubmitThirdFormCattle(context,
                    questionnaireID,
                    appVersion,
                    locationCoordinate,
                    formJSON,
                    accessToken,
                    interviewtakenAt,
                    interviewTimeStart,
                    interviewTimeEnd,
                    locationCoordinatesStart,
                    locationCoordinatesEnd

            );


        }

        @JavascriptInterface
        public void SubmitCattleEntityForm4(String formjson) {
            String questionnaireID = entityId;
            String appVersion = "";


            Log.d(constants.info, "post called");
            loadingDialog.dissmissDialog();


            try {
                appVersion = getPackageManager()
                        .getPackageInfo(getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }


            String locationCoordinate = "23.32,45.32";
            String formJSON = formjson;


            String accessToken = sessionManager.getbearer();
            String interviewtakenAt = getTimeStamp("Interview_taken_at-");
            String interviewTimeStart = sessionManager.getStartTimestamp();
            String interviewTimeEnd = getTimeStamp("end-");


            String locationCoordinatesStart = sessionManager.getLatitudeStart() + "," +
                    sessionManager.getLongitudeStart();


            String locationCoordinatesEnd = "0.0,0.0";


            surveyViewModel.SubmitFourthFormCattle(context,
                    questionnaireID,
                    appVersion,
                    locationCoordinate,
                    formJSON,
                    accessToken,
                    interviewtakenAt,
                    interviewTimeStart,
                    interviewTimeEnd,
                    locationCoordinatesStart,
                    locationCoordinatesEnd

            );


        }


    }


    public void injectCities() {

        ArrayList<String> citiesarray = realmDatabaseHlper.readDataCities();
        Log.d(constants.Tag, citiesarray.toString());

        if (citiesarray.size() != 0) {
            String citiesJson = new Gson().toJson(citiesarray.toString());

            String javascript_injection = "var dropdown = survey.getQuestionByName(\"city\");\n" +
                    "var optionsJson = " + citiesJson + ";\n" +
                    "var options = JSON.parse(optionsJson);\n" +
                    "dropdown.choices = options;" +
                    "console.log(optionsJson)";
            webViewSurveyFormBinding.surveyWebView.evaluateJavascript(javascript_injection, null);
        }


    }


    public String getSurveyFormData(String id) {
        String formjson = realmDatabaseHlper.readDataSurvey(id);

        Log.d(constants.info + "function", formjson);
        Log.d(constants.Tag, entityId);

        return formjson;

    }

    public void ForceWebViewToDarkMode(String formjson) {

        String javascriptCode =
                "      window['surveyjs-widgets'].inputmask(Survey);\n" +
                        "      window['surveyjs-widgets'].nouislider(Survey);\n" +
                        "const themeJson = {\n" +
                        "  \"cssVariables\": {\n" +
                        "    \"--sjs-general-backcolor\": \"rgba(48, 48, 48, 1)\",\n" +
                        "    \"--sjs-general-backcolor-dark\": \"rgba(52, 52, 52, 1)\",\n" +
                        "    \"--sjs-general-backcolor-dim\": \"rgba(36, 36, 36, 1)\",\n" +
                        "    \"--sjs-general-backcolor-dim-light\": \"rgba(43, 43, 43, 1)\",\n" +
                        "    \"--sjs-general-backcolor-dim-dark\": \"rgba(46, 46, 46, 1)\",\n" +
                        "    \"--sjs-general-forecolor\": \"rgba(255, 255, 255, 0.78)\",\n" +
                        "    \"--sjs-general-forecolor-light\": \"rgba(255, 255, 255, 0.42)\",\n" +
                        "    \"--sjs-general-dim-forecolor\": \"rgba(255, 255, 255, 0.79)\",\n" +
                        "    \"--sjs-general-dim-forecolor-light\": \"rgba(255, 255, 255, 0.45)\",\n" +
                        "    \"--sjs-primary-backcolor\": \"#6db7fc\",\n" +
                        "    \"--sjs-primary-backcolor-light\": \"rgba(NaN, NaN, NaN, 0.07)\",\n" +
                        "    \"--sjs-primary-backcolor-dark\": \"rgba(NaN, NaN, NaN, 1)\",\n" +
                        "    \"--sjs-primary-forecolor\": \"rgba(32, 32, 32, 1)\",\n" +
                        "    \"--sjs-primary-forecolor-light\": \"rgba(32, 32, 32, 0.25)\",\n" +
                        "    \"--sjs-base-unit\": \"8px\",\n" +
                        "    \"--sjs-corner-radius\": \"4px\",\n" +
                        "    \"--sjs-secondary-backcolor\": \"rgba(255, 152, 20, 1)\",\n" +
                        "    \"--sjs-secondary-backcolor-light\": \"rgba(255, 152, 20, 0.1)\",\n" +
                        "    \"--sjs-secondary-backcolor-semi-light\": \"rgba(255, 152, 20, 0.25)\",\n" +
                        "    \"--sjs-secondary-forecolor\": \"rgba(48, 48, 48, 1)\",\n" +
                        "    \"--sjs-secondary-forecolor-light\": \"rgba(48, 48, 48, 0.25)\",\n" +
                        "    \"--sjs-shadow-small\": \"0px 1px 2px 0px rgba(0, 0, 0, 0.35)\",\n" +
                        "    \"--sjs-shadow-medium\": \"0px 2px 6px 0px rgba(0, 0, 0, 0.2)\",\n" +
                        "    \"--sjs-shadow-large\": \"0px 8px 16px 0px rgba(0, 0, 0, 0.2)\",\n" +
                        "    \"--sjs-shadow-inner\": \"inset 0px 1px 2px 0px rgba(0, 0, 0, 0.2)\",\n" +
                        "    \"--sjs-border-light\": \"rgba(255, 255, 255, 0.08)\",\n" +
                        "    \"--sjs-border-default\": \"rgba(255, 255, 255, 0.12)\",\n" +
                        "    \"--sjs-border-inside\": \"rgba(255, 255, 255, 0.08)\",\n" +
                        "    \"--sjs-special-red\": \"rgba(254, 76, 108, 1)\",\n" +
                        "    \"--sjs-special-red-light\": \"rgba(254, 76, 108, 0.1)\",\n" +
                        "    \"--sjs-special-red-forecolor\": \"rgba(48, 48, 48, 1)\",\n" +
                        "    \"--sjs-special-green\": \"rgba(36, 197, 164, 1)\",\n" +
                        "    \"--sjs-special-green-light\": \"rgba(36, 197, 164, 0.1)\",\n" +
                        "    \"--sjs-special-green-forecolor\": \"rgba(48, 48, 48, 1)\",\n" +
                        "    \"--sjs-special-blue\": \"rgba(91, 151, 242, 1)\",\n" +
                        "    \"--sjs-special-blue-light\": \"rgba(91, 151, 242, 0.1)\",\n" +
                        "    \"--sjs-special-blue-forecolor\": \"rgba(48, 48, 48, 1)\",\n" +
                        "    \"--sjs-special-yellow\": \"rgba(255, 152, 20, 1)\",\n" +
                        "    \"--sjs-special-yellow-light\": \"rgba(255, 152, 20, 0.1)\",\n" +
                        "    \"--sjs-special-yellow-forecolor\": \"rgba(48, 48, 48, 1)\"\n" +
                        "  },\n" +
                        "  \"themeName\": \"default\",\n" +
                        "  \"colorPalette\": \"dark\"\n" +
                        "}" +
                        "\n" +
                        "  const survey = new Survey.Model(" + formjson + ");\n" +
                        "\n" +
                        "  // You can delete the line below if you do not use a customized theme\n" +
                        "  survey.applyTheme(themeJson);\n" +
                        "  survey.onComplete.add((sender, options) => {\n" +
                        "Android.ShowProgressDialog()\n" +
                        "setTimeout(function(){\n" +
                        " const results = JSON.stringify(sender.data);\n" +
                        "  console.log(JSON.stringify(sender.data, null, 3));\n" +
                        "Android.getSubmittedData(results)" +
                        "},2000)" +
                        "  });\n" +
                        "\n" +
                        "\n" +
                        "  $(\"#surveyElement\").Survey({ model: survey });";
        ;

        Log.d(constants.Tag, "darkfunction");

        webViewSurveyFormBinding.surveyWebView.evaluateJavascript(javascriptCode, null);

        WebSettingsCompat.setForceDark(webViewSurveyFormBinding.surveyWebView.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
    }


    public void getcurrentlocationstart() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationProviderClient.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY, new CancellationToken() {
            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }

            @Override
            public boolean isCancellationRequested() {
                return false;
            }


        }).addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {


                    sessionManager.saveStartCoordinatesAndTime(location.getLatitude(), location.getLongitude(), getTimeStamp("start-"));
                    Log.d(constants.Tag, String.valueOf(location.getLatitude()));
                    Log.d(constants.Tag, String.valueOf(location.getLongitude()));


                }
            }
        });
    }


    public ArrayList<String> getcurrentlocationEnd() {


        ArrayList<String> arrayList = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            ActivityCompat.requestPermissions(this, permission, 4);
        }
        locationProviderClient.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY, new CancellationToken() {
            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }

            @Override
            public boolean isCancellationRequested() {
                return false;
            }


        }).addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {


                    arrayList.add(String.valueOf(location.getLatitude()));
                    arrayList.add(String.valueOf(location.getLongitude()));

                    Log.d(constants.Tag, String.valueOf(location.getLatitude()));
                    Log.d(constants.Tag, String.valueOf(location.getLongitude()));


                }
            }
        });

        return arrayList;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        sessionManager.saveStartCoordinatesAndTime(0.0, 0.0, "00/00/0000 00:00");
    }


    public void CheckLocationTurnedOn() {

        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {

            Log.d("error", ex.getMessage());
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {

            Log.d("error", ex.getMessage());

        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            new AlertDialog.Builder(ActivityWebViewSurveyForm.this)
                    .setMessage("Location not turned on please turn on location to connect bluetooth printer")
                    .setPositiveButton("Turn on", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                        }
                    }).setCancelable(false)


                    .show();
        }
    }

    String getTimeStamp(String msg) {

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssa");
        String stamp = sdf.format(date);
        Log.d(tag, msg + stamp);
        return stamp;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        CheckLocationTurnedOn();
    }

    @Override
    public void onBackPressed() {
        goback();

    }


    public void createCompletedJson() {

        ArrayList<String> arrayList = realmDatabaseHlper.readCompletedForm();

        if (arrayList.size() != 0) {
            Gson gson = new Gson();
            String jsonArrayString = gson.toJson(arrayList);
            JsonObject response = new JsonObject();
            response.addProperty("id", arrayList.get(8).toString());
            response.addProperty("start_time", arrayList.get(0));
            response.addProperty("end_time", arrayList.get(1));
            response.addProperty("appversion", arrayList.get(2));
            response.addProperty("FormName", formName);
            response.addProperty("start_coordinates_latitude", arrayList.get(3));
            response.addProperty("start_coordinates_longitude", arrayList.get(4));
            response.addProperty("end_coordinates_latitude", arrayList.get(5));
            response.addProperty("end_coordinates_longitude", arrayList.get(6));
            response.addProperty("form_data", arrayList.get(7));
            Log.d(constants.info, response.toString());
        }

    }


    public void goback() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setMessage(R.string.formcancelmessage);
        alert.setMessage("Do you want to close the form?");
        alert.setTitle("Form Cancellation");


        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                finish();

            }
        });

        alert.show();


    }


    public void InjectDarkMode() {

        String js = "function myFunction() {\n" +
                "  var element = document.body;\n" +
                "  element.classList.toggle(\"dark-mode\");\n" +
                "}\n" +
                "myFunction();";


        webViewSurveyFormBinding.surveyWebView.evaluateJavascript(js, null);

    }


    public String JsonToInject(String json) {


        String title = formName = realmDatabaseHlper.getFormName(formId);
        Log.d(constants.Tag, json);
        JsonObject parsedjson = (JsonObject) new JsonParser().parse(json);

        Log.d(constants.Tag, parsedjson.toString());


        return
                parsedjson.toString();

    }


    public void LoadWebViewWithOldSettings() {


        Bundle extras = getIntent().getExtras();
        formId = extras.getString("formID");

        sessionManager = new SessionManager(this);

        CheckLocationTurnedOn();

        loadingDialog = new LoadingDialog(ActivityWebViewSurveyForm.this, this);


        constants = new Constants();
        setUpLocation();
        getcurrentlocationstart();


        //injectCities();


        //surveyViewModel.getJsonFromAPi("1");

        surveyViewModel.formdata.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (s != null) {

                    // Loadgetjavascript(getSurveyFormData());

                } else {

                    Toast.makeText(ActivityWebViewSurveyForm.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            setUpWebView();
        }
        createCompletedJson();
        formName = realmDatabaseHlper.getFormName(formId);

    }


    public void LoadWebViewWithDifferentSettings() {


        Bundle extras = getIntent().getExtras();
        formId = extras.getString("formID");


        if (formId.equals("general_basic")) {

            sessionManager = new SessionManager(this);

            CheckLocationTurnedOn();


            loadingDialog = new LoadingDialog(ActivityWebViewSurveyForm.this, this);
            //PostFirstFormData("POST");

            constants = new Constants();
            setUpLocation();
            getcurrentlocationstart();


            //injectCities();


            //.getJsonFromAPi("1");

            surveyViewModel.formdata.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {

                    if (s != null) {

                        // Loadgetjavascript(getSurveyFormData());

                    } else {

                        Toast.makeText(ActivityWebViewSurveyForm.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                setUpWebView2("general_basic", "Android.PostFirstFormData(results)");
            }
            createCompletedJson();
            formName = realmDatabaseHlper.getFormName(formId);


            surveyViewModel.isfirstformSent.observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {

                    if (aBoolean) {

                        LoadSecondForm("general_diet", "Android.SubmitSecondForm(results)\n" + "dissmissdialog()\n");

                    }
                }
            });


            surveyViewModel.formMsg.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {

                    if (!s.equals("")) {

                        ShowAllToastMessages(s);

                        if (s.equals("token_expired")) {

                            Toast.makeText(ActivityWebViewSurveyForm.this, s, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ActivityWebViewSurveyForm.this, ActivityLogin.class));
                            sessionManager.SaveCattleID("");
                            sessionManager.savebarearToken("null");
                            sessionManager.Save_Farm_and_Farmer_ID("", "");
                            sessionManager.saveStartCoordinatesAndTime(0, 0, "");
                            finish();
                        }
                    }

                }
            });


            surveyViewModel.is_secondformSent.observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {

                    if (aBoolean) {

                        LoadThirdForm("general_medical", "Android.SubmitThirdForm(results)");
                    }
                }
            });


            surveyViewModel.isthirdformSent.observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {

                    if (aBoolean) {

                        Intent i = new Intent(ActivityWebViewSurveyForm.this, ActivityDashboard.class);
                        startActivity(i);
                        finish();
                    }
                }
            });
        } else if (formId.equals("personal_basic")) {

            Bundle extraspersonalbasic = getIntent().getExtras();
            farmId = extraspersonalbasic.getString("farmID");
            farmerId = extraspersonalbasic.getString("farmerID");

            sessionManager = new SessionManager(this);

            CheckLocationTurnedOn();


            loadingDialog = new LoadingDialog(ActivityWebViewSurveyForm.this, this);
            //PostFirstFormData("POST");

            constants = new Constants();
            setUpLocation();
            getcurrentlocationstart();


            //injectCities();


            //.getJsonFromAPi("1");

            surveyViewModel.formdata.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {

                    if (s != null) {

                        // Loadgetjavascript(getSurveyFormData());

                    } else {

                        Toast.makeText(ActivityWebViewSurveyForm.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                setUpWebView2("personal_basic", "Android.PostFirstFormDatacattle(results)");

            }

            createCompletedJson();
            formName = realmDatabaseHlper.getFormName(formId);


            surveyViewModel.isfirstformSent.observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {

                    if (aBoolean) {

                        LoadSecondForm("personal_milk", "Android.SubmitCattleEntityForm(results)");
                    }
                }
            });


            surveyViewModel.is_secondformSent.observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {

                    if (aBoolean) {

                        LoadThirdForm("personal_medical", "Android.SubmitCattleEntityForm3(results)");
                    }
                }
            });


            surveyViewModel.isthirdformSent.observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {

                    if (aBoolean) {

                        Intent i = new Intent(ActivityWebViewSurveyForm.this, ActivityDashboard.class);
                        startActivity(i);
                        finish();
                    }
                }
            });


            surveyViewModel.isthirdCattleformSubmitted.observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {

                    if (aBoolean) {

                        LoadFourthForm("personal_traits", "Android.SubmitCattleEntityForm4(results)");

                    }
                }
            });


            surveyViewModel.isFourthCattleformSubmitted.observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {

                    if (aBoolean) {
                        Intent i = new Intent(ActivityWebViewSurveyForm.this, ActivityDashboard.class);
                        startActivity(i);
                        finish();
                    }
                }
            });


        } else {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                setUpWebView();
            }

        }
    }


    public JsonObject getEntities() {

        return realmDatabaseHlper.getEntityObject();

    }


    public String getFormEntity(String key) {

        JsonObject object = getEntities();
        String firstformId = object.get(key).getAsString();
        entityId = firstformId;
        Log.d(constants.Tag, entityId);
        return firstformId;


    }


    public void ForceWebViewToDarkMode2(String formjson, String function) {

        String javascriptCode =
                "      window['surveyjs-widgets'].inputmask(Survey);\n" +
                        "      window['surveyjs-widgets'].nouislider(Survey);\n" +
                        "const themeJson = {\n" +
                        "  \"cssVariables\": {\n" +
                        "    \"--sjs-general-backcolor\": \"rgba(48, 48, 48, 1)\",\n" +
                        "    \"--sjs-general-backcolor-dark\": \"rgba(52, 52, 52, 1)\",\n" +
                        "    \"--sjs-general-backcolor-dim\": \"rgba(36, 36, 36, 1)\",\n" +
                        "    \"--sjs-general-backcolor-dim-light\": \"rgba(43, 43, 43, 1)\",\n" +
                        "    \"--sjs-general-backcolor-dim-dark\": \"rgba(46, 46, 46, 1)\",\n" +
                        "    \"--sjs-general-forecolor\": \"rgba(255, 255, 255, 0.78)\",\n" +
                        "    \"--sjs-general-forecolor-light\": \"rgba(255, 255, 255, 0.42)\",\n" +
                        "    \"--sjs-general-dim-forecolor\": \"rgba(255, 255, 255, 0.79)\",\n" +
                        "    \"--sjs-general-dim-forecolor-light\": \"rgba(255, 255, 255, 0.45)\",\n" +
                        "    \"--sjs-primary-backcolor\": \"#6db7fc\",\n" +
                        "    \"--sjs-primary-backcolor-light\": \"rgba(NaN, NaN, NaN, 0.07)\",\n" +
                        "    \"--sjs-primary-backcolor-dark\": \"rgba(NaN, NaN, NaN, 1)\",\n" +
                        "    \"--sjs-primary-forecolor\": \"rgba(32, 32, 32, 1)\",\n" +
                        "    \"--sjs-primary-forecolor-light\": \"rgba(32, 32, 32, 0.25)\",\n" +
                        "    \"--sjs-base-unit\": \"8px\",\n" +
                        "    \"--sjs-corner-radius\": \"4px\",\n" +
                        "    \"--sjs-secondary-backcolor\": \"rgba(255, 152, 20, 1)\",\n" +
                        "    \"--sjs-secondary-backcolor-light\": \"rgba(255, 152, 20, 0.1)\",\n" +
                        "    \"--sjs-secondary-backcolor-semi-light\": \"rgba(255, 152, 20, 0.25)\",\n" +
                        "    \"--sjs-secondary-forecolor\": \"rgba(48, 48, 48, 1)\",\n" +
                        "    \"--sjs-secondary-forecolor-light\": \"rgba(48, 48, 48, 0.25)\",\n" +
                        "    \"--sjs-shadow-small\": \"0px 1px 2px 0px rgba(0, 0, 0, 0.35)\",\n" +
                        "    \"--sjs-shadow-medium\": \"0px 2px 6px 0px rgba(0, 0, 0, 0.2)\",\n" +
                        "    \"--sjs-shadow-large\": \"0px 8px 16px 0px rgba(0, 0, 0, 0.2)\",\n" +
                        "    \"--sjs-shadow-inner\": \"inset 0px 1px 2px 0px rgba(0, 0, 0, 0.2)\",\n" +
                        "    \"--sjs-border-light\": \"rgba(255, 255, 255, 0.08)\",\n" +
                        "    \"--sjs-border-default\": \"rgba(255, 255, 255, 0.12)\",\n" +
                        "    \"--sjs-border-inside\": \"rgba(255, 255, 255, 0.08)\",\n" +
                        "    \"--sjs-special-red\": \"rgba(254, 76, 108, 1)\",\n" +
                        "    \"--sjs-special-red-light\": \"rgba(254, 76, 108, 0.1)\",\n" +
                        "    \"--sjs-special-red-forecolor\": \"rgba(48, 48, 48, 1)\",\n" +
                        "    \"--sjs-special-green\": \"rgba(36, 197, 164, 1)\",\n" +
                        "    \"--sjs-special-green-light\": \"rgba(36, 197, 164, 0.1)\",\n" +
                        "    \"--sjs-special-green-forecolor\": \"rgba(48, 48, 48, 1)\",\n" +
                        "    \"--sjs-special-blue\": \"rgba(91, 151, 242, 1)\",\n" +
                        "    \"--sjs-special-blue-light\": \"rgba(91, 151, 242, 0.1)\",\n" +
                        "    \"--sjs-special-blue-forecolor\": \"rgba(48, 48, 48, 1)\",\n" +
                        "    \"--sjs-special-yellow\": \"rgba(255, 152, 20, 1)\",\n" +
                        "    \"--sjs-special-yellow-light\": \"rgba(255, 152, 20, 0.1)\",\n" +
                        "    \"--sjs-special-yellow-forecolor\": \"rgba(48, 48, 48, 1)\"\n" +
                        "  },\n" +
                        "  \"themeName\": \"default\",\n" +
                        "  \"colorPalette\": \"dark\"\n" +
                        "}" +
                        "\n" +
                        "  const survey = new Survey.Model(" + formjson + ");\n" +
                        "\n" +
                        "  // You can delete the line below if you do not use a customized theme\n" +
                        "  survey.applyTheme(themeJson);\n" +
                        "  survey.onComplete.add((sender, options) => {\n" +
                        "Android.ShowProgressDialog()\n" +
                        "setTimeout(function(){\n" +
                        " const results = JSON.stringify(sender.data);\n" +
                        function + "\n" +
                        "},2000)" +
                        "  });\n" +
                        "\n" +
                        "\n" +
                        "  $(\"#surveyElement\").Survey({ model: survey });";
        ;

        Log.d(constants.Tag, "darkfunction");

        webViewSurveyFormBinding.surveyWebView.evaluateJavascript(javascriptCode, null);

        WebSettingsCompat.setForceDark(webViewSurveyFormBinding.surveyWebView.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
    }


    public void Loadgetjavascript(String formjson) {

        String javascriptCode =
                "      window['surveyjs-widgets'].inputmask(Survey);\n" +
                        "      window['surveyjs-widgets'].nouislider(Survey);\n" +
                        "const themeJson = {\n" +
                        "  \"cssVariables\": {\n" +
                        "    \"--sjs-general-backcolor\": \"rgba(255, 255, 255, 1)\",\n" +
                        "    \"--sjs-general-backcolor-dark\": \"rgba(248, 248, 248, 1)\",\n" +
                        "    \"--sjs-general-backcolor-dim\": \"rgba(243, 243, 243, 1)\",\n" +
                        "    \"--sjs-general-backcolor-dim-light\": \"rgba(249, 249, 249, 1)\",\n" +
                        "    \"--sjs-general-backcolor-dim-dark\": \"rgba(243, 243, 243, 1)\",\n" +
                        "    \"--sjs-general-forecolor\": \"rgba(0, 0, 0, 0.91)\",\n" +
                        "    \"--sjs-general-forecolor-light\": \"rgba(0, 0, 0, 0.45)\",\n" +
                        "    \"--sjs-general-dim-forecolor\": \"rgba(0, 0, 0, 0.91)\",\n" +
                        "    \"--sjs-general-dim-forecolor-light\": \"rgba(0, 0, 0, 0.45)\",\n" +
                        "    \"--sjs-primary-backcolor\": \"#2772cb\",\n" +
                        "    \"--sjs-primary-backcolor-light\": \"rgba(NaN, NaN, NaN, 0.07)\",\n" +
                        "    \"--sjs-primary-backcolor-dark\": \"rgba(NaN, NaN, NaN, 1)\",\n" +
                        "    \"--sjs-primary-forecolor\": \"rgba(255, 255, 255, 1)\",\n" +
                        "    \"--sjs-primary-forecolor-light\": \"rgba(255, 255, 255, 0.25)\",\n" +
                        "    \"--sjs-base-unit\": \"8px\",\n" +
                        "    \"--sjs-corner-radius\": \"4px\",\n" +
                        "    \"--sjs-secondary-backcolor\": \"rgba(255, 152, 20, 1)\",\n" +
                        "    \"--sjs-secondary-backcolor-light\": \"rgba(255, 152, 20, 0.1)\",\n" +
                        "    \"--sjs-secondary-backcolor-semi-light\": \"rgba(255, 152, 20, 0.25)\",\n" +
                        "    \"--sjs-secondary-forecolor\": \"rgba(255, 255, 255, 1)\",\n" +
                        "    \"--sjs-secondary-forecolor-light\": \"rgba(255, 255, 255, 0.25)\",\n" +
                        "    \"--sjs-shadow-small\": \"0px 1px 2px 0px rgba(0, 0, 0, 0.15)\",\n" +
                        "    \"--sjs-shadow-medium\": \"0px 2px 6px 0px rgba(0, 0, 0, 0.1)\",\n" +
                        "    \"--sjs-shadow-large\": \"0px 8px 16px 0px rgba(0, 0, 0, 0.1)\",\n" +
                        "    \"--sjs-shadow-inner\": \"inset 0px 1px 2px 0px rgba(0, 0, 0, 0.15)\",\n" +
                        "    \"--sjs-border-light\": \"rgba(0, 0, 0, 0.09)\",\n" +
                        "    \"--sjs-border-default\": \"rgba(0, 0, 0, 0.16)\",\n" +
                        "    \"--sjs-border-inside\": \"rgba(0, 0, 0, 0.16)\",\n" +
                        "    \"--sjs-special-red\": \"rgba(229, 10, 62, 1)\",\n" +
                        "    \"--sjs-special-red-light\": \"rgba(229, 10, 62, 0.1)\",\n" +
                        "    \"--sjs-special-red-forecolor\": \"rgba(255, 255, 255, 1)\",\n" +
                        "    \"--sjs-special-green\": \"rgba(25, 179, 148, 1)\",\n" +
                        "    \"--sjs-special-green-light\": \"rgba(25, 179, 148, 0.1)\",\n" +
                        "    \"--sjs-special-green-forecolor\": \"rgba(255, 255, 255, 1)\",\n" +
                        "    \"--sjs-special-blue\": \"rgba(67, 127, 217, 1)\",\n" +
                        "    \"--sjs-special-blue-light\": \"rgba(67, 127, 217, 0.1)\",\n" +
                        "    \"--sjs-special-blue-forecolor\": \"rgba(255, 255, 255, 1)\",\n" +
                        "    \"--sjs-special-yellow\": \"rgba(255, 152, 20, 1)\",\n" +
                        "    \"--sjs-special-yellow-light\": \"rgba(255, 152, 20, 0.1)\",\n" +
                        "    \"--sjs-special-yellow-forecolor\": \"rgba(255, 255, 255, 1)\"\n" +
                        "  },\n" +
                        "  \"themeName\": \"default\",\n" +
                        "  \"colorPalette\": \"light\"\n" +
                        "}" +
                        "\n" +
                        "  const survey = new Survey.Model(" + formjson + ");\n" +
                        "\n" +
                        "  // You can delete the line below if you do not use a customized theme\n" +
                        "  survey.applyTheme(themeJson);\n" +
                        "survey.data = JSON.stringify({\"farmName\":\"Tftdb\",\"farmerName\":\"Tfbtf\",\"address\":\"Tft\",\"sector\":\"Tfgfb\",\"province\":\"4\",\"city\":\"8\",\"mobileNo\":\"0312-9578544\",\"alternateMobile\":\"0356-4945549\",\"cnicNo\":\"03584-4595495-7\",\"animalType\":\"cow\",\"numberOfCows\":1,\"cowBreeds\":[\"sahiwal\"],\"reproduceCows\":false,\"choosingCow_breed\":[\"lessSick\"],\"businessModule\":\"dairy\",\"animalSource\":\"self\",\"animalSourceLocation\":\"sindh\",\"milkYeild_dialy\":[\"daily\"],\"dailyMilk_yeild_weight\":2,\"improvementToBreed\":[\"milkProduction\"],\"animalPurchased_age\":\"notPregnant\",\"eliminationReason\":\"lactation\",\"animalEliminated_age\":2,\"highestMilk_yield\":2,\"lowestMilk_yield\":5,\"averageLactation_period\":6});\n" +
                        "  survey.onComplete.add((sender, options) => {\n" +
                        "Android.ShowProgressDialog()\n" +
                        "setTimeout(function(){\n" +
                        " const results = JSON.stringify(sender.data);\n" +
                        "  console.log(JSON.stringify(sender.data, null, 3));\n" +
                        "Android.PostFirstFormData(results)" +
                        "},2000)" +
                        "  });\n" +
                        "\n" +
                        "\n" +
                        "  $(\"#surveyElement\").Survey({ model: survey });";

        webViewSurveyFormBinding.surveyWebView.evaluateJavascript(javascriptCode, null);
        WebSettingsCompat.setForceDark(webViewSurveyFormBinding.surveyWebView.getSettings(), WebSettingsCompat.FORCE_DARK_OFF);


    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void setUpWebView2(String key, String function) {

        webViewSurveyFormBinding.surveyWebView.getSettings().setJavaScriptEnabled(true);
        webViewSurveyFormBinding.surveyWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        webViewSurveyFormBinding.surveyWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webViewSurveyFormBinding.surveyWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webViewSurveyFormBinding.surveyWebView.getSettings().setDomStorageEnabled(true);
        webViewSurveyFormBinding.surveyWebView.getSettings().setAllowFileAccess(true);
        webViewSurveyFormBinding.surveyWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSurveyFormBinding.surveyWebView.getSettings().setDomStorageEnabled(true);
        webViewSurveyFormBinding.surveyWebView.getSettings().setForceDark(WebSettings.FORCE_DARK_ON);

        webViewSurveyFormBinding.surveyWebView.addJavascriptInterface(new JavaScriptInterface(this), "Android");


        webViewSurveyFormBinding.surveyWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (!sessionManager.getthemstate()) {
                    Loadgetjavascript2(JsonToInject(getSurveyFormData(getFormEntity(key))), function);
                    injectCities();

                } else {

                    ForceWebViewToDarkMode2(JsonToInject((JsonToInject(getSurveyFormData(getFormEntity(key))))), function);
                    InjectDarkMode();
                }
            }
        });

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


    public void PostFirstFormData(String formjson) {


        String questionnaireID = entityId;
        String appVersion = "";


        Log.d(constants.info, "post called");
        // loadingDialog.dissmissDialog();


        try {
            appVersion = getPackageManager()
                    .getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }


        String locationCoordinate = "";
        String formJSON = formjson;


        String accessToken = sessionManager.getbearer();
        String interviewtakenAt = getTimeStamp("Interview_taken_at-");
        String interviewTimeStart = sessionManager.getStartTimestamp();
        String interviewTimeEnd = getTimeStamp("end-");


        String locationCoordinatesStart = "Latitude_start " + sessionManager.getLatitudeStart() + "," +
                "Longitude_start " +
                sessionManager.getLongitudeStart();


        String locationCoordinatesEnd = "Latitude_End " + 0 + "," +
                "Longitude_End " +
                0;


        JsonObject payloadObject = new JsonObject();


        JsonObject firstformObject = new JsonObject();
        firstformObject.addProperty("questionnaireID", questionnaireID);
        firstformObject.addProperty("appVersion", appVersion);
        firstformObject.addProperty("locationCoordinates", locationCoordinate);
        firstformObject.addProperty("formJSON", "formJSON");
        firstformObject.addProperty("accessToken", accessToken);
        firstformObject.addProperty("interviewTakenAt", interviewtakenAt);
        firstformObject.addProperty("interviewTimeStart", interviewTimeStart);
        firstformObject.addProperty("interviewTimeEnd", interviewTimeEnd);
        firstformObject.addProperty("locationCoordinatesStart", locationCoordinatesStart);
        firstformObject.addProperty("locationCoordinatesEnd", "lat 0,lon 0");

        firstformObject.add("payload", payloadObject);


        Call<JsonObject> apicall = new RetrofitClientSurvey(this).retrofitclient()
                .Addfarmer(firstformObject);

        apicall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {

                    Log.d(constants.Tag, "Worked Yeay!");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG", "called");
            }
        });


    }

    public void LoadSecondForm(String formname, String method) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            setUpWebView2(formname, method);
        }

    }

    public void LoadThirdForm(String formname, String method) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            setUpWebView2(formname, method);
        }
    }


    public void LoadFourthForm(String formname, String method) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            setUpWebView2(formname, method);
        }
    }

    public void ShowAllToastMessages(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    public class EndLocationAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(constants.Tag, "getting end location");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getcurrentlocationstarEnd();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            if (locationCoordinates.size() != 0) {

                isEndCoordinatesObtained.setValue(true);
            } else {

                isEndCoordinatesObtained.setValue(false);
            }

            Log.d(constants.Tag, "getting end location exited" + isEndCoordinatesObtained.getValue().toString());
        }


        public void getcurrentlocationstarEnd() {


            if (ActivityCompat.checkSelfPermission(ActivityWebViewSurveyForm.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityWebViewSurveyForm.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }


            locationProviderClient.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY, new CancellationToken() {
                @NonNull
                @Override
                public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                    return null;
                }

                @Override
                public boolean isCancellationRequested() {
                    return false;
                }


            }).addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if (location != null) {

                        locationCoordinates.add(String.valueOf(location.getLatitude()));
                        locationCoordinates.add(String.valueOf(location.getLongitude()));


                    }
                }
            });


        }


    }


    public void LocationUpdates() {

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {


                }
            }
        };
    }


    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationProviderClient.requestLocationUpdates(new com.google.android.gms.location.LocationRequest(),
                locationCallback,
                Looper.getMainLooper());
    }




}




