package no.realitylab.arface.internetService;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import no.realitylab.arface.R;
import no.realitylab.arface.Slider;

public class SliderAdapter1 extends RecyclerView.Adapter<SliderAdapter1.SliderViewHolder> {

    private List<Slider> sliders;
    private ViewPager2 viewPager2;

    public SliderAdapter1(List<Slider> sliders, ViewPager2 viewPager2) {
        this.sliders = sliders;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slider_item_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImage(sliders.get(position));
    }

    @Override
    public int getItemCount() {
        return sliders.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView imageView;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
        }
        void setImage(Slider slider){

            imageView.setImageBitmap(slider.getImage());


        }
    }
}
