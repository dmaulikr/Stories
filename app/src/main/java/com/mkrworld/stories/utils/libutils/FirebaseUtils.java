package com.mkrworld.stories.utils.libutils;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.utils.PreferenceDataUtils;
import com.mkrworld.stories.utils.Tracer;

/**
 * Class to handle firebase operation
 */
public class FirebaseUtils {
    private static FirebaseUtils mFirebaseUtils;
    private static final String TAG = BuildConfig.BASE_TAG + ".FirebaseUtils";
    private static final String APP_CONFIG = "app_config";
    private static final String STORY_MAX_COUNT = "story_max_count";
    private static final String STORY_MIN_COUNT = "story_min_count";
    private static final String STORY_PAGE_COUNT = "story_page_count";
    private static final String APP_VER = "app_ver";
    private static final String STORY_LIST = "story_list";
    private static final String TITLE = "title";
    private static final String STORY = "story";

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
    public void fetchStoryTitle(final String storyId, final OnFirebaseListeer onFirebaseListener) {
        Tracer.debug(TAG, "fetchStoryTitle()");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference(STORY_LIST).child(storyId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tracer.debug(TAG, "fetchStoryTitle().onDataChange() " + dataSnapshot.getValue());
                try {
                    String title = (String) dataSnapshot.child(TITLE).getValue();
                    if (onFirebaseListener != null) {
                        onFirebaseListener.onFirebaseStoryFetchTitleSuccess(storyId, title);
                    }
                } catch (Exception e) {
                    Tracer.error(TAG, "fetchStoryTitle().onDataChange()" + e.getMessage());
                    if (onFirebaseListener != null) {
                        onFirebaseListener.onFirebaseStoryFetchTitleFailed(storyId);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Tracer.debug(TAG, "fetchStoryTitle().onCancelled() " + databaseError.getMessage());
                if (onFirebaseListener != null) {
                    onFirebaseListener.onFirebaseStoryFetchTitleFailed(storyId);
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
    public void fetchStory(final String storyId, final OnFirebaseListeer onFirebaseListener) {
        Tracer.debug(TAG, "fetchStory()");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference(STORY_LIST).child(storyId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tracer.debug(TAG, "fetchStory().onDataChange() " + dataSnapshot.getValue());
                try {
                    String title = (String) dataSnapshot.child(TITLE).getValue();
                    if (onFirebaseListener != null) {
                        onFirebaseListener.onFirebaseStoryFetchTitleSuccess(storyId, title);
                    }
                } catch (Exception e) {
                    Tracer.error(TAG, "fetchStory().onDataChange()" + e.getMessage());
                    if (onFirebaseListener != null) {
                        onFirebaseListener.onFirebaseStoryFetchTitleFailed(storyId);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Tracer.debug(TAG, "fetchStory().onCancelled() " + databaseError.getMessage());
                if (onFirebaseListener != null) {
                    onFirebaseListener.onFirebaseStoryFetchTitleFailed(storyId);
                }
            }
        });
    }

    /**
     * Method to get the app version from play store
     *
     * @param context
     * @param onFirebaseListener
     */
    public void getAppConfig(final Context context, final OnFirebaseListeer onFirebaseListener) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(APP_CONFIG);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tracer.debug(TAG, "getAppConfig().onDataChange() " + dataSnapshot.getValue());
                try {
                    long appVer = (Long) dataSnapshot.child(APP_VER).getValue();
                    PreferenceDataUtils.setLatestVersion(context, (int) appVer);
                } catch (Exception e) {
                    Tracer.error(TAG, "getAppConfig().onDataChange(APP_VER)" + e.getMessage());
                }
                try {
                    long appVer = (Long) dataSnapshot.child(STORY_MAX_COUNT).getValue();
                    PreferenceDataUtils.setStoryMaxCount(context, (int) appVer);
                } catch (Exception e) {
                    Tracer.error(TAG, "getAppConfig().onDataChange(STORY_MAX_COUNT)" + e.getMessage());
                }
                try {
                    long appVer = (Long) dataSnapshot.child(STORY_MIN_COUNT).getValue();
                    PreferenceDataUtils.setStoryMinCount(context, (int) appVer);
                } catch (Exception e) {
                    Tracer.error(TAG, "getAppConfig().onDataChange(STORY_MIN_COUNT)" + e.getMessage());
                }
                try {
                    long appVer = (Long) dataSnapshot.child(STORY_PAGE_COUNT).getValue();
                    PreferenceDataUtils.setStoryPageCount(context, (int) appVer);
                } catch (Exception e) {
                    Tracer.error(TAG, "getAppConfig().onDataChange(STORY_PAGE_COUNT)" + e.getMessage());
                }
                if (onFirebaseListener != null) {
                    onFirebaseListener.onFirebaseConfigFetchSuccess();
                }
                if (onFirebaseListener != null) {
                    onFirebaseListener.onFirebaseConfigFetchSuccess();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Tracer.debug(TAG, "getAppConfig().onCancelled() " + databaseError.getMessage());
                if (onFirebaseListener != null) {
                    onFirebaseListener.onFirebaseConfigFetchFailed();
                }
            }
        });
    }

    /**
     * Callback for notify when data is fetched from fire base
     */
    public interface OnFirebaseListeer {
        /**
         * Method to notify that Story title fetched from the server
         *
         * @param id    Unique story ID
         * @param title Title of story
         */
        public void onFirebaseStoryFetchTitleSuccess(String id, String title);

        /**
         * Method to notify that fetching story title failed
         *
         * @param id Unique Id of the Story
         */
        public void onFirebaseStoryFetchTitleFailed(String id);

        /**
         * Method to notify that config fetch successfully
         */
        public void onFirebaseConfigFetchSuccess();

        /**
         * Method to notify that config fetch Failed
         */
        public void onFirebaseConfigFetchFailed();
    }
}
