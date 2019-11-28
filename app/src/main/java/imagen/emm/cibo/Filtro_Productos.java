package imagen.emm.cibo;

//import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
    DatabaseReference databaseAlimento;

    private ArrayList<String> caducidades;
    Button btn_EliminarP;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro__productos);
        listView = (ListView) findViewById(R.id.listview);
        btn_ModifProd = (Button) findViewById(R.id.btn_ModificarP);
        btn_Cancelar = (Button) findViewById(R.id.btn_Cancelar);
        btn_EliminarP = (Button) findViewById(R.id.btn_EliminarP);

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
                    }
                    ArrayAdapter namesAdapter = new ArrayAdapter(Filtro_Productos.this,android.R.layout.simple_list_item_1,names);
                  listView.setAdapter(namesAdapter);



                    btn_EliminarP.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            listView.setOnItemClickListener(new OnItemClickListener() {
                                public void onItemClick(AdapterView<?> myAdapter, View myView, int pos, long mylng) {
                                    String selectedFromList =(listView.getItemAtPosition(pos).toString());
                                    names.remove(selectedFromList);
                                    ArrayAdapter namesAdapter = new ArrayAdapter(Filtro_Productos.this,android.R.layout.simple_list_item_1,names);
                                    listView.setAdapter(namesAdapter);
                                    for(DataSnapshot almacenSnapshot : dataSnapshot.getChildren()){
                                        String productoNombre = almacenSnapshot.child("nombreAlimento").getValue().toString();
                                        if(productoNombre.equals(selectedFromList)){
                                            String productoID = almacenSnapshot.child("alimentoId").getValue().toString();
                                            databaseAlimento = FirebaseDatabase.getInstance().getReference("Almacen").child(personId).child(productoID);
                                            databaseAlimento.removeValue();
                                        }

                                    }

                                }
                            });

                        }
                    });

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
