package com.vsc.vidasemcancer.Adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vsc.vidasemcancer.Activities.ViewPostDetailsActivity;
import com.vsc.vidasemcancer.Mappers.PostMapper;
import com.vsc.vidasemcancer.R;
import com.vsc.vidasemcancer.Utils.VolleyRequests;
import com.vsc.vidasemcancer.entities.Post;

import java.util.List;

/**
 * The type Post adapter.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Post> objects;
    private PostMapper postMapper;

    /**
     * Instantiates a new Post adapter.
     *
     * @param context the context
     * @param objects the objects
     */
    public PostAdapter(Context context, List<Post> objects) {
        inflater = LayoutInflater.from(context);
        this.objects = objects;
        postMapper = new PostMapper();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;


        View convertView = inflater.inflate(R.layout.post_list_item, parent, false);
        holder = new ViewHolder(convertView);
        holder.textView1 = (TextView) convertView.findViewById(R.id.post_list_item_title);
        holder.textView2 = (TextView) convertView.findViewById(R.id.post_list_item_excerpt);
        holder.imageView1 = (ImageView) convertView.findViewById(R.id.post_image);
        convertView.setTag(holder);


        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textView1.setText(objects.get(position).getTitle().getRendered());
        holder.textView2.setText(android.text.Html.fromHtml(objects.get(position).getExcerpt().getRendered()).toString());

        VolleyRequests volleyRequests = new VolleyRequests();
        holder.imageView1.setImageBitmap(volleyRequests.getImageFromSpanned(Html.fromHtml(objects.get(position).getContent().getRendered())));

    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * The Text view 1.
         */
        TextView textView1;
        /**
         * The Text view 2.
         */
        TextView textView2;

        ImageView imageView1;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Integer taggedPosition = (Integer) ((ViewHolder) v.getTag()).getAdapterPosition();
            Intent intent = new Intent(inflater.getContext(), ViewPostDetailsActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("com.vidasemcancer.post", objects.get(taggedPosition).getContent().getRendered());
            intent.putExtra("com.vidasemcancer.title", objects.get(taggedPosition).getTitle().getRendered());
            v.getContext().startActivity(intent);
        }
    }

}

