package com.oxyzenhomes.grapevine.fxn.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.CancellationSignal;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.oxyzenhomes.grapevine.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 1,
        xi = 2,
        d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0015B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0014\u0010\t\u001a\u00020\n2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u000bJ\b\u0010\f\u001a\u00020\rH\u0016J\u0018\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\rH\u0016J\u0018\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\rH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"},
        d2 = {"Lcom/fxn/adapters/MyAdapterJava;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "list", "Ljava/util/ArrayList;", "", "addImage", "", "", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "Holder", "PixImagePicker-master.app"}
)
public final class MyAdapterJava extends Adapter {
    private final ArrayList list;
    private final Context context;

    public final void addImage(@NotNull List list) {
        Intrinsics.checkNotNullParameter(list, "list");
        this.list.clear();
        this.list.addAll((Collection)list);
        this.notifyDataSetChanged();
    }

    @NotNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image, parent, false);
        Intrinsics.checkNotNullExpressionValue(v, "v");
        return (ViewHolder)(new MyAdapterJava.Holder(v));
    }

    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        final File f = new File((String)this.list.get(position));
        int len = f.getAbsolutePath().length();
        CharSequence extension = f.getAbsolutePath().subSequence(len - 3, len);
        Bitmap bm = null;
        if (!Intrinsics.areEqual(extension, "mp4") && !Intrinsics.areEqual(extension, "mkv")) {
            ((MyAdapterJava.Holder)holder).getPlay().setVisibility(View.VISIBLE);
            bm = BitmapFactory.decodeFile(f.getAbsolutePath());
        } else {
            ((MyAdapterJava.Holder)holder).getPlay().setVisibility(View.VISIBLE);
            try {
                bm = VERSION.SDK_INT >= 29 ? ThumbnailUtils.createVideoThumbnail(f, new Size(500, 500), (CancellationSignal)null) : ThumbnailUtils.createVideoThumbnail(f.getAbsolutePath(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Bitmap bitmap = bm;
        RoundedBitmapDrawable var9 = RoundedBitmapDrawableFactory.create(this.context.getResources(), bitmap);
        Intrinsics.checkNotNullExpressionValue(var9, "RoundedBitmapDrawableFac…ontext.resources, bitmap)");
        RoundedBitmapDrawable roundedBitmapDrawable = var9;
        Intrinsics.checkNotNull(bitmap);
        float roundPx = (float)bitmap.getWidth() * 0.06F;
        roundedBitmapDrawable.setCornerRadius(roundPx);
        ((MyAdapterJava.Holder)holder).getIv().setImageDrawable((Drawable)roundedBitmapDrawable);
        ((MyAdapterJava.Holder)holder).getIv().setOnClickListener((OnClickListener)(new OnClickListener() {
            public final void onClick(View it) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(f.getAbsolutePath()));

                try {
                    if (VERSION.SDK_INT >= 26) {
                        Intrinsics.checkNotNullExpressionValue(intent.setDataAndType(Uri.parse(f.getAbsolutePath()), Files.probeContentType(f.toPath())), "intent.setDataAndType(Ur…eContentType(f.toPath()))");
                    } else {
                        intent.setData(Uri.parse(f.getAbsolutePath()));
                    }
                } catch (IOException var4) {
                    var4.printStackTrace();
                }

                MyAdapterJava.this.context.startActivity(intent);
            }
        }));
    }

    public int getItemCount() {
        return this.list.size();
    }

    public MyAdapterJava(@NotNull Context context) {
        super();
        //Intrinsics.checkNotNullParameter(context, "context");

        this.context = context;
        this.list = new ArrayList();
    }

    @Metadata(
            mv = {1, 1, 16},
            bv = {1, 0, 3},
            k = 1,
            xi = 2,
            d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\n¨\u0006\u000e"},
            d2 = {"Lcom/fxn/adapters/MyAdapterJava$Holder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/fxn/adapters/MyAdapterJava;Landroid/view/View;)V", "iv", "Landroid/widget/ImageView;", "getIv", "()Landroid/widget/ImageView;", "setIv", "(Landroid/widget/ImageView;)V", "play", "getPlay", "setPlay", "PixImagePicker-master.app"}
    )
    public final class Holder extends ViewHolder {
        @NotNull
        private ImageView iv;
        @NotNull
        private ImageView play;

        @NotNull
        public final ImageView getIv() {
            return this.iv;
        }

        public final void setIv(@NotNull ImageView var1) {
            Intrinsics.checkNotNullParameter(var1, "<set-?>");
            this.iv = var1;
        }

        @NotNull
        public final ImageView getPlay() {
            return this.play;
        }

        public final void setPlay(@NotNull ImageView var1) {
            Intrinsics.checkNotNullParameter(var1, "<set-?>");
            this.play = var1;
        }

        public Holder(@NotNull View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            View var10001 = itemView.findViewById(R.id.iv);
            //Intrinsics.checkNotNullExpressionValue(var10001, "itemView.findViewById(R.id.iv)");
            this.iv = (ImageView)var10001;
            var10001 = itemView.findViewById(R.id.play);
            //Intrinsics.checkNotNullExpressionValue(var10001, "itemView.findViewById(R.id.play)");
            this.play = (ImageView)var10001;
        }
    }
}
