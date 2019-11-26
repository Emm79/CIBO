package imagen.emm.cibo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificacionAdapter extends RecyclerView.Adapter<NotificacionAdapter.ViewHolder> {

     int resource; //private
     private ArrayList<Notificacion> notificacionList;

     public NotificacionAdapter (ArrayList<Notificacion> notificacionList, int resource){
            this.notificacionList = notificacionList;
            this.resource = resource;
     }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

         Notificacion notificacion = notificacionList.get(position);
         holder.textNots.setText(notificacion.getContenido());

    }

    @Override
    public int getItemCount() {
        return notificacionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textNots;
        public View view;

        public ViewHolder(View view){
            super(view);

            this.view = view;
            this.textNots = (TextView) view.findViewById(R.id.text_not);
        }

    }
}
