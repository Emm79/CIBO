package imagen.emm.cibo;

//import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Filtro_Productos extends Activity {

    Button btn_ModifProd;
    Button btn_Cancelar;
    DatabaseReference databaseAlmacen;
    private ListView listView;
    private ArrayList<String> names;
    private ArrayList<String> caducidades;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro__productos);
        listView = (ListView) findViewById(R.id.listview);
        btn_ModifProd = (Button) findViewById(R.id.btn_ModificarP);
        btn_Cancelar = (Button) findViewById(R.id.btn_Cancelar);


        btn_ModifProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ModificarProducto.class));

            }
        });

        btn_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        names = new ArrayList<String>();
        caducidades = new ArrayList<String>();
        if (acct != null) {

            String personId = acct.getId();

            databaseAlmacen = FirebaseDatabase.getInstance().getReference("Almacen").child(personId);
            databaseAlmacen.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot almacenSnapshot : dataSnapshot.getChildren()){
                        String productoNombre = almacenSnapshot.child("nombreAlimento").getValue().toString();
                        String productoCad = almacenSnapshot.child("caducidad").getValue().toString();
                        names.add(productoNombre);
                        caducidades.add(productoCad);
                        Toast.makeText(Filtro_Productos.this, productoNombre , Toast.LENGTH_LONG).show();
                      //  Toast.makeText(Filtro_Productos.this, productoCad , Toast.LENGTH_LONG).show();

                    }
                    ArrayAdapter namesAdapter = new ArrayAdapter(Filtro_Productos.this,android.R.layout.simple_list_item_1,names);
                    listView.setAdapter(namesAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



            //si esto jala con esto podríamos meter estos datos a la base de datos.
//            text_producto.setText(personName);
        }



    }
}
