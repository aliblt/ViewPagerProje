package no.realitylab.arface.internetService;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import no.realitylab.arface.R;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private RecyclerView parentRecycler;
    Bitmap myBitmap;
    private ArrayList<Bitmap> image = new ArrayList<>();
    private ArrayList<String> nameCategory = new ArrayList<>();

    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<Bitmap> images, ArrayList<String> nameCategories) {

        image= images;
        mContext = context;
        nameCategory = nameCategories;

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parentRecycler = recyclerView;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.imageview.setImageBitmap(myBitmap);
        holder.textView.setText(nameCategory.get(position));

        Glide.with(mContext)
                .asBitmap()
                .load(image.get(position))
                .into(holder.imageview);
    }


    @Override
    public int getItemCount() {
        return image.size();
    }

    public void setLayoutManager(LinearLayoutManager layoutManager) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imageview;
        TextView textView;


        public ViewHolder(View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.image_view);
          //  textView = itemView.findViewById(R.id.nameCat);
        }
    }

}