package com.mkrworld.stories.utils.libutils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.utils.Tracer;

/**
 * Class to handle firebase operation
 */
public class FirebaseUtils {
    public static final String APP_CONFIG = "app_config";
    public static final String STORY_MAX_COUNT = "story_max_count";
    public static final String STORY_MIN_COUNT = "story_min_count";
    public static final String STORY_PAGE_COUNT = "story_page_count";
    public static final String APP_VER = "app_ver";
    public static final String STORY_LIST = "story_list";
    public static final String TITLE = "title";
    public static final String STORY = "story";
    private static FirebaseUtils mFirebaseUtils;
    private static final String TAG = BuildConfig.BASE_TAG + ".FirebaseUtils";

    /**
     * Method to get the Instance of this class
     *
     * @return
     */
    public static FirebaseUtils getInstance() {
        Tracer.debug(TAG, "getInstance()");
        if (mFirebaseUtils == null) {
            mFirebaseUtils = new FirebaseUtils();
        }
        return mFirebaseUtils;
    }

    /**
     * Method to save the Story
     *
     * @param id
     * @param title
     * @param story
     */
    public void saveStory(String id, String title, String story) {
        Tracer.debug(TAG, "saveStory()");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference(STORY_LIST);
        databaseReference.child(id).child(TITLE).setValue(title);
        databaseReference.child(id).child(STORY).setValue(story);
    }

    /**
     * Method to fetch the title of the story
     *
     * @param storyId            Unique Id of the Story
     * @param onFirebaseListener Listen the callback
     */
    public void fetchStoryTitle(final String storyId, final OnFirebaseListener onFirebaseListener) {
        Tracer.debug(TAG, "fetchStoryTitle()");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference(STORY_LIST).child(storyId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tracer.debug(TAG, "fetchStoryTitle().onDataChange() " + dataSnapshot.getValue());
                try {
                    String title = (String) dataSnapshot.child(TITLE).getValue();
                } catch (Exception e) {
                    Tracer.error(TAG, "fetchStoryTitle().onDataChange()" + e.getMessage());
                    if (onFirebaseListener != null) {
                        onFirebaseListener.onFirebaseStoryFetchTitleFailed(storyId, e.getMessage());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Tracer.debug(TAG, "fetchStoryTitle().onCancelled() " + databaseError.getMessage());
                if (onFirebaseListener != null) {
                    onFirebaseListener.onFirebaseStoryFetchTitleFailed(storyId, databaseError.getMessage());
                }
            }
        });
    }

    /**
     * Method to fetch the story
     *
     * @param storyId            Unique Id of the Story
     * @param onFirebaseListener Listen the callback
     */
    public void fetchStory(final String storyId, final OnFirebaseListener onFirebaseListener) {
        Tracer.debug(TAG, "fetchStory()");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference(STORY_LIST).child(storyId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tracer.debug(TAG, "fetchStory().onDataChange() " + dataSnapshot.getValue());
                try {
                    String title = (String) dataSnapshot.child(TITLE).getValue();
                } catch (Exception e) {
                    Tracer.error(TAG, "fetchStory().onDataChange()" + e.getMessage());
                    if (onFirebaseListener != null) {
                        onFirebaseListener.onFirebaseStoryFetchTitleFailed(storyId, e.getMessage());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Tracer.debug(TAG, "fetchStory().onCancelled() " + databaseError.getMessage());
                if (onFirebaseListener != null) {
                    onFirebaseListener.onFirebaseStoryFetchTitleFailed(storyId, databaseError.getMessage());
                }
            }
        });
    }

    /**
     * Method to get the app version from play store
     *
     * @param onFirebaseListener
     */
    public void getAppConfig(final OnFirebaseListener onFirebaseListener) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(APP_CONFIG);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tracer.debug(TAG, "getAppConfig().onDataChange() ");
                if (onFirebaseListener != null) {
                    onFirebaseListener.onFirebaseConfigFetchSuccess(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Tracer.debug(TAG, "getAppConfig().onCancelled() " + databaseError.getMessage());
                if (onFirebaseListener != null) {
                    onFirebaseListener.onFirebaseConfigFetchFailed(databaseError.getMessage());
                }
            }
        });
    }

    /**
     * Callback for notify when data is fetched from fire base
     */
    public interface OnFirebaseListener {
        /**
         * Method to notify that Story title fetched from the server
         *
         * @param id    Unique story ID
         * @param dataSnapshot Title of story
         */
        public void onFirebaseStoryFetchTitleSuccess(String id, DataSnapshot dataSnapshot);

        /**
         * Method to notify that fetching story title failed
         *
         * @param id Unique Id of the Story
         */
        public void onFirebaseStoryFetchTitleFailed(String id, String error);

        /**
         * Method to notify that config fetch successfully
         */
        public void onFirebaseConfigFetchSuccess(DataSnapshot dataSnapshot);

        /**
         * Method to notify that config fetch Failed
         */
        public void onFirebaseConfigFetchFailed(String error);
    }
}
