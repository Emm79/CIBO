package imagen.emm.cibo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.sangcomz.fishbun.define.Define;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class tomaFotografia extends AppCompatActivity {

    IamOptions options = new IamOptions.Builder()
            .apiKey("Odq7VnI56NIxfAU-I9RejABcuoshsDSY68yL7FqFBku2")
            .build();

    //IamAuthenticator options = new IamAuthenticator();

    VisualRecognition mVisualRecognition;
    CameraHelper mCameraHelper;
    GestorProductos gestor = new GestorProductos();
    ArrayList<Uri> path = new ArrayList<>();
    ArrayList<String> nombres = new ArrayList<>();
    DatabaseReference databaseAlmacen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomar__fotografia);



        //mVisualRecognition = new VisualRecognition("2018-03-19",options);
        //mVisualRecognition.setApiKey("6FaIIh3r-2UNFg1vRzcMHypzz4-ZCGYC-Pk-GcCtbs8X");

       /* mCameraHelper = new CameraHelper(this);
        btn_salir_fotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });*/
    }

    public void tomarFotografia(View view){
        mCameraHelper.dispatchTakePictureIntent();
    }

    /*@Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CameraHelper.REQUEST_IMAGE_CAPTURE) {
            final Bitmap photo = mCameraHelper.getBitmap(resultCode);
            final File photoFile = mCameraHelper.getFile(resultCode);
            ImageView preview = findViewById(R.id.imageViewFotoPreview);
            preview.setImageBitmap(photo);
        }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                VisualClassification response =
                        vrClient.classify(
                                new ClassifyImagesOptions.Builder()
                                        .images(photoFile)
                                        .build()
                        ).execute();

                ImageClassification classification =
                        response.getImages().get(0);

                VisualClassifier classifier =
                        classification.getClassifiers().get(0);
            }
        });
    }*/



    public void pickFromGallery(View view){
        FishBun.with(tomaFotografia.this)
                .setImageAdapter(new GlideAdapter())
                .setPickerCount(50)
                .setPickerSpanCount(4)
                .setActionBarColor(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"), true)
                .setActionBarTitleColor(Color.parseColor("#000000"))
                .setSelectedImages(path)
                .setAlbumSpanCount(1, 2)
                .setButtonInAlbumActivity(true)
                .setCamera(false)
                .exceptGif(true)
                .setReachLimitAutomaticClose(false)
                .setHomeAsUpIndicatorDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp))
                //.setOkButtonDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp))
                .setAllViewTitle("Selecciona tus fotos")
                .setActionBarTitle("Galería")
                .textOnImagesSelectionLimitReached("No es posible seleccionar más fotos")
                .textOnNothingSelected("¡Debes seleccionar una foto!")
                .startAlbum();
    }

    public void verNombres(View view){
        String cad = "";
        for(String n : nombres){cad+=n;}
        Toast.makeText(tomaFotografia.this,cad,Toast.LENGTH_LONG).show();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personId = acct.getId();
            databaseAlmacen = FirebaseDatabase.getInstance().getReference("Almacen").child(personId);
            String nombreAlimento = "manzana";
            String Caducidad = "5 días";
            String id = databaseAlmacen.push().getKey();
            AlmacenFirebase almacenFirebase = new AlmacenFirebase(id,nombreAlimento,Caducidad);
            databaseAlmacen.child(id).setValue(almacenFirebase);
            Toast.makeText(this,"Guardado en almacen.",Toast.LENGTH_LONG).show();
        }
    }

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =  cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if (requestCode == CameraHelper.REQUEST_IMAGE_CAPTURE) {
          //  final Bitmap photo = mCameraHelper.getBitmap(resultCode);
            //final File photoFile = mCameraHelper.getFile(resultCode);
            //ImageView preview = findViewById(R.id.imageViewFotoPreview);
            //Picasso.load(photo).fit().centerCrop().into(preview);
            //preview.setImageBitmap(photo);
            switch (requestCode) {
                case Define.ALBUM_REQUEST_CODE:
                    if (resultCode == RESULT_OK) {
                        // path = imageData.getStringArrayListExtra(Define.INTENT_PATH);
                        // you can get an image path(ArrayList<String>) on <0.6.2
                        System.out.println("WENAS ");
                        path = data.getParcelableArrayListExtra(Define.INTENT_PATH);
                        System.out.println("JEJE ");
                        // you can get an image path(ArrayList<Uri>) on 0.6.2 and later
                        break;
                    }
            }
            /*for(Uri p :path){
                File photo = new File(getPath(p));
                System.out.println("URI "+photo.getName());
            }*/

                    AsyncTask.execute(new Runnable(){
                        @Override
                        public void run() {
                            for(Uri p:path){
                                File photoFile = new File(getPath(p));
                                InputStream imagesStream = null;
                                try {
                                    imagesStream = new FileInputStream(photoFile);
                                    System.out.println(photoFile.getName());
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    mVisualRecognition = new VisualRecognition("2018-03-19",options);
                                    mVisualRecognition.setSkipAuthentication(true);
                                    ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                                            .imagesFile(imagesStream)
                                            .imagesFilename(photoFile.getName())
                                            .threshold((float) 0.8)
                                            .classifierIds(Arrays.asList("DefaultCustomModel_443087349"))
                                            //.owners(Arrays.asList("me"))
                                            .build();
                                    ClassifiedImages result = mVisualRecognition.classify(classifyOptions).execute();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(result);
                                    String name = null;

                                    System.out.println(result);
                                    try {
                                        JSONObject jsonObject = new JSONObject(json);
                                        JSONArray jsonArray = jsonObject.getJSONArray("images");
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                        JSONArray jsonArray1 = jsonObject1.getJSONArray("classifiers");
                                        JSONObject jsonObject2 = jsonArray1.getJSONObject(0);
                                        JSONArray jsonArray2 = jsonObject2.getJSONArray("classes");
                                        JSONObject jsonObject3 = jsonArray2.getJSONObject(0);
                                        name = jsonObject3.getString("class");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    final String finalName = name;
                                    nombres.add(name);
                                    System.out.println(name);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                //Toast.makeText(tomaFotografia.this,"llegado hasta aca",Toast.LENGTH_LONG).show();

                            }

                        }
                    });

        //}
        System.out.println("nombres "+nombres);
    }




    /*
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        JSONArray jsonArray = jsonObject.getJSONArray("images");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        JSONArray jsonArray1 = jsonObject1.getJSONArray("classifiers");
                        JSONObject jsonObject2 = jsonArray1.getJSONObject(0);
                        JSONArray jsonArray2 = jsonObject2.getJSONArray("classes");
                        JSONObject jsonObject3 = jsonArray2.getJSONObject(0);
                        name = jsonObject3.getString("class");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final String finalName = name;
                    Toast.makeText(tomaFotografia.this,finalName,Toast.LENGTH_LONG).show();*/
                    /*runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //TextView detectedObjects = findViewById(R.id.textViewFoto);
                            //detectedObjects.setText(finalName);
                            detectedObjects.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(tomaFotografia.this, Perfil_Usuario.class));
                                }
                            });
                        }
                    });*/
}
