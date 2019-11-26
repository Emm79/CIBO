package imagen.emm.cibo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

//import android.support.v7.app.AppCompatActivity;

public class Tomar_Fotografia extends Activity {

    private Camera mCamera;
    private CameraPreview mPreview;
    IamOptions options = new IamOptions.Builder()
            .apiKey("6FaIIh3r-2UNFg1vRzcMHypzz4-ZCGYC-Pk-GcCtbs8X")
            .build();

    VisualRecognition visualRecognition = new VisualRecognition("2018-03-19", options);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomar__fotografia);

        mCamera = getCameraInstance();

        mPreview = new CameraPreview(this, mCamera);
        //FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        //preview.addView(mPreview);

        Button captura = (Button) findViewById(R.id.btn_TomarFoto);
        captura.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCamera.takePicture(null, null, mPicture);
                    }
                }
        );

    }

    /** Revisamos las camaras disponibles en el dispositivo */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // Hay camara
            return true;
        } else {
            // no camera
            return false;
        }
    }

    /** Obtenemos una instancia de la camara. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // Intentamos abrir la camara
        }
        catch (Exception e){
            // No se puede conectar con la camara
        }
        return c;
    }

    private void reconocerProducto(File imagen){
        String nombre = imagen.getName();
        try {
            InputStream imagesStream = new FileInputStream("./" + nombre);
            ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                    .imagesFile(imagesStream)
                    .imagesFilename(nombre)
                    .threshold((float) 0.6)
                    .classifierIds(Arrays.asList("ModeloComida_460086802"))
                    .build();

            ClassifiedImages result = this.visualRecognition.classify(classifyOptions).execute();
            Toast.makeText(Tomar_Fotografia.this,""+result,Toast.LENGTH_LONG).show();
        } catch (Exception e){}
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                //Log.d(TAG, "Error creating media file, check storage permissions")
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                reconocerProducto(pictureFile);
            } catch (FileNotFoundException e) {
               // Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
               // Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
    };

    // Método provisto por la documentación de Google
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }





}
