package adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import techline.carsapp.R;
import model.City_model;

/**
 * Created by Rajesh on 2017-07-28.
 */

public class City_adapter extends RecyclerView.Adapter<City_adapter.MyViewHolder> {

    private List<City_model> modelList;
    private List<City_model> mFilteredList;

    private Context context;

    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public LinearLayout ll_city;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_city_name);
            ll_city = (LinearLayout) view.findViewById(R.id.ll_city_row);

            ll_city.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            selectedItems.clear();
            notifyDataSetChanged();

            // Save the selected positions to the SparseBooleanArray
            if (selectedItems.get(position, false)) {
                selectedItems.delete(position);
                ll_city.setSelected(false);
            } else {
                selectedItems.put(position, true);
                ll_city.setSelected(true);
            }

            Intent updates = new Intent("Carondeal_city");
            updates.putExtra("type", "update_city");
            updates.putExtra("city_id", modelList.get(position).getCity_id());
            updates.putExtra("city_name", modelList.get(position).getCity_name());
            updates.putExtra("currency", modelList.get(position).getCurrency());
            context.sendBroadcast(updates);

        }
    }

    public City_adapter(List<City_model> modelList) {
        this.modelList = modelList;
        this.mFilteredList = modelList;
    }

    @Override
    public City_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_city_list, parent, false);

        context = parent.getContext();

        return new City_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(City_adapter.MyViewHolder holder, int position) {
        City_model mList = modelList.get(position);

        holder.title.setText(mList.getCity_name());

        // Set the selected state of the row depending on the position
        holder.ll_city.setSelected(selectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void filter(List<City_model> models, String query) {

        query = query.toLowerCase();

        final ArrayList<City_model> filteredModelList = new ArrayList<>();
        for (City_model model : models) {
            final String text = model.getCity_name().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
                Log.e("City: ", model.getCity_name());
            }
        }

        this.mFilteredList = filteredModelList;
        this.modelList = filteredModelList;
        notifyDataSetChanged();
    }

}
