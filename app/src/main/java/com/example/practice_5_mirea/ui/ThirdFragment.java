package com.example.practice_5_mirea.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice_5_mirea.R;
import com.example.practice_5_mirea.data.GoodRepository;
import com.example.practice_5_mirea.data.GoodRepositoryImpl;
import com.example.practice_5_mirea.viewModels.OrderViewModel;

import java.util.ArrayList;
import java.util.List;

public class ThirdFragment extends Fragment {
    public static class SecondFragmentRecyclerViewAdapter extends
            RecyclerView.Adapter <SecondFragmentRecyclerViewAdapter.ViewHolder>{
        private final LayoutInflater inflater;
        private final List<GoodRepository> items;

        //
        private OnItemClicked onClick;

        public interface OnItemClicked {
            void onItemClick(int position);
        }
        //

        SecondFragmentRecyclerViewAdapter(Context context, List<GoodRepository>
                items) {
            this.items = items;
            this.inflater = LayoutInflater.from(context);
        }
        @Override
        public SecondFragmentRecyclerViewAdapter.ViewHolder
        onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.list_item, parent,
                    false);
            return new ViewHolder(view);
        }
        @Override
        public void
        onBindViewHolder(SecondFragmentRecyclerViewAdapter.ViewHolder
                                 holder, int position) {
            GoodRepository item = items.get(position);
            holder.textView1.setText(item.getGoodName());
            holder.textView2.setText(item.getGoodAmount());
            String number = Integer.toString(position + 1);
            holder.textView3.setText(number);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onItemClick(holder.getAdapterPosition());
                }
            });
        }
        @Override
        public int getItemCount() {
            return items.size();
        }
        public class ViewHolder extends
                RecyclerView.ViewHolder {
            final TextView textView1;
            final TextView textView2;
            final TextView textView3;
            ViewHolder(View view){
                super(view);
                textView1 = view.findViewById(R.id.first_fragment_list_view_item_good_name);
                textView2 = view.findViewById(R.id.first_fragment_list_view_item_good_amount);
                textView3 = view.findViewById(R.id.first_fragment_list_view_item_number);
            }
        }
        //
        public void setOnClick(OnItemClicked onClick){
            this.onClick=onClick;
        }
        //
    }

    Button addMoreGoodsButton;

    public ThirdFragment() {
        super(R.layout.fragment_third);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        addMoreGoodsButton = (Button) getActivity().findViewById(R.id.third_fragment_button);

        RecyclerView itemsList = getActivity().findViewById(R.id.third_fragment_recycler_view);

        OrderViewModel orderViewModel = new ViewModelProvider(getActivity()).get(OrderViewModel.class);

        orderViewModel.getUiState().observe(getViewLifecycleOwner(), uiState -> {
            List<GoodRepository> items = uiState.getOrderedPositions();

            if (items == null || items.size() == 0) {
                itemsList.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "There is no purchased goods", Toast.LENGTH_SHORT).show();
            }
            else {
                if (itemsList.getVisibility() == View.GONE)
                    itemsList.setVisibility(View.VISIBLE);

                SecondFragmentRecyclerViewAdapter adapter = new SecondFragmentRecyclerViewAdapter(this.getContext(), items);
                LinearLayoutManager layoutManager = new
                        LinearLayoutManager(this.getContext().getApplicationContext());
                itemsList.setLayoutManager(layoutManager);

                adapter.setOnClick(new SecondFragmentRecyclerViewAdapter.OnItemClicked() {
                    @Override
                    public void onItemClick(int position) {
                        if (orderViewModel.getUiState().getValue() != null) {
                            GoodRepositoryImpl good = (GoodRepositoryImpl) orderViewModel.getUiState().getValue().getGood(position);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Good", good);
                            navController.navigate(R.id.action_thirdFragment_to_fourthFragment, bundle);
                        }
                    }
                });

                itemsList.setAdapter(adapter);
            }
        });

        addMoreGoodsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_thirdFragment_to_firstFragment);
            }
        });
    }
}
