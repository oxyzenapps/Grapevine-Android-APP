package datanapps.mediapicker.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import datanapps.mediapicker.R;
import datanapps.mediapicker.ui.adapters.CategorizedMediaAdapter;
import datanapps.mediapicker.utils.AppConstants;
import datanapps.mediapicker.utils.Utility;


/*
 /*
 *  Yogendra
 *
 * https://github.com/datanapps
 *
 * 10-01-2019
 *
 * For bucket name
 *
 *
 * Video
 *
 * */

public class AudioFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<String> bucketNames= new ArrayList<>();
    private List<String> bitmapList=new ArrayList<>();
    private List<Integer> FileCounts = new ArrayList<>();
    private final String[] projection = new String[]{
            MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATA };
    private final String[] projection2 = new String[]{MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media.DATA };
    public static List<String> AudiosList=new ArrayList<>();
    public static List<String> AudiosNameList=new ArrayList<>();
    public static List<Boolean> selected=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bucketNames.clear();
        bitmapList.clear();
        AudiosList.clear();
        AudiosNameList.clear();
        FileCounts.clear();
        getAllAudio();
        getAudioBuckets();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_image, container, false);
        recyclerView = v.findViewById(R.id.content_open_gallery_recycler_view);
        populateRecyclerView();
        return v;
    }

    private void populateRecyclerView() {
        CategorizedMediaAdapter categorizedMediaAdapter = new CategorizedMediaAdapter(bucketNames,bitmapList
                ,FileCounts,"Audio",getContext());

        int noOfColumns = Utility.calculateNoOfColumns(getContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),noOfColumns);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(categorizedMediaAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                getAudios(bucketNames.get(position));
                Intent intent=new Intent(getContext(), SubMediaGalleryActivity.class);
                intent.putExtra("FROM","Audios");
                intent.putExtra("FolderName",bucketNames.get(position) );
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        categorizedMediaAdapter.notifyDataSetChanged();
    }

    public void getAudios(String bucket){
        selected.clear();
        Cursor cursor=null;
        if(bucket.contains("All Audios")){
            cursor = getContext().getContentResolver()
                    .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                            null,null,MediaStore.Audio.Media.DATE_ADDED);
        }
        else{
            cursor = getContext().getContentResolver()
                    .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection2,
                            MediaStore.Audio.Media.DISPLAY_NAME+" =?",new String[]{bucket},MediaStore.Audio.Media.DATE_ADDED);
        }

        ArrayList<String> imagesTEMP = new ArrayList<>(cursor.getCount());
        ArrayList<String> imagesName = new ArrayList<>(cursor.getCount());
        HashSet<String> albumSet = new HashSet<>();
        File file;
        if (cursor.moveToLast()) {
            do {
                if (Thread.interrupted()) {
                    return;
                }
                String name = cursor.getString(cursor.getColumnIndex(projection2[0]));
                String path = cursor.getString(cursor.getColumnIndex(projection2[1]));
                file = new File(path);
                if (file.exists() && !albumSet.contains(path)) {

                    imagesTEMP.add(path);
                    imagesName.add(name);
                    albumSet.add(path);
                    selected.add(false);
                }
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        if (imagesTEMP == null) {

            imagesTEMP = new ArrayList<>();
        }
        AudiosNameList.clear();
        AudiosList.clear();
        AudiosNameList.addAll(imagesName);
        AudiosList.addAll(imagesTEMP);
    }

    public void getAllAudio(){
        Cursor cursor = getContext().getContentResolver()
                .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                        null, null, MediaStore.Audio.Media.DATE_ADDED);

        ArrayList<String> bitmapListTEMP = new ArrayList<>(cursor.getCount());
        HashSet<String> albumSet = new HashSet<>();
        File file;
        int Count=0;

        if(cursor.moveToLast()){
            String image = cursor.getString(cursor.getColumnIndex(projection[1]));
            file = new File(image);
            if (file.exists() ) {

                bitmapListTEMP.add(image);
            }
        }
        Count=cursor.getCount();
        cursor.close();

        bucketNames.clear();
        bitmapList.clear();
        FileCounts.clear();

        FileCounts.add(Count);
        bucketNames.add("All Audios");
        bitmapList.addAll(bitmapListTEMP);
    }

    public void getAudioBuckets(){
        Cursor cursor = getContext().getContentResolver()
                .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection,
                        null, null, MediaStore.Audio.Media.DATE_ADDED);
        ArrayList<String> bucketNamesTEMP = new ArrayList<>(cursor.getCount());
        ArrayList<String> bitmapListTEMP = new ArrayList<>(cursor.getCount());
        ArrayList<Integer> bucketFileCount = new ArrayList<>(cursor.getCount());
        HashSet<String> albumSet = new HashSet<>();
        File file;
        if (cursor.moveToLast()) {
            do {
                if (Thread.interrupted()) {
                    return;
                }
                String album = cursor.getString(cursor.getColumnIndex(projection[0]));
                String image = cursor.getString(cursor.getColumnIndex(projection[1]));
                file = new File(image);
                if (file.exists() && !albumSet.contains(album)) {
                    Cursor cr= getContext().getContentResolver()
                            .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, AppConstants.projectionImageName,
                                    MediaStore.Audio.Media.DISPLAY_NAME + " =?",
                                    new String[]{album}, MediaStore.Audio.Media.DATE_ADDED);
                    bucketFileCount.add(cr.getCount());
                    bucketNamesTEMP.add(album);
                    bitmapListTEMP.add(image);
                    albumSet.add(album);
                }
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        if (bucketNamesTEMP == null) {
            bucketNames = new ArrayList<>();
        }
        bucketNames.clear();
        bitmapList.clear();
        FileCounts.addAll(bucketFileCount);
        bucketNames.addAll(bucketNamesTEMP);
        bitmapList.addAll(bitmapListTEMP);
    }

    public interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }
}