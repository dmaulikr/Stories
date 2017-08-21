package com.mkrworld.stories.utils.libutils;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mkrworld.sexstories.BuildConfig;
import com.mkrworld.sexstories.utils.PreferenceDataUtils;
import com.mkrworld.sexstories.utils.Tracer;

/**
 * Class to handle firebase operation
 */
public class FirebaseUtils {
    private static FirebaseUtils mFirebaseUtils;
    private static final String TAG = BuildConfig.BASE_TAG + ".FirebaseUtils";
    private static final String APP_VER = "app_ver";
    private static final String APP_CONFIG = "app_config";
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
     * Method to fetch user score in local DB. Fetch current score from Server and saved in local db
     *
     * @param onFriendsScoreUpdate Listen the callback
     */
    public void fetchStory(final String story_group_id, final OnFriendsScoreUpdate onFriendsScoreUpdate) {
        Tracer.debug(TAG, "fetchUserScore()");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference(STORY_LIST).child(story_group_id);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tracer.debug(TAG, "fetchUserScore().onDataChange() " + dataSnapshot.getValue());
                try {
                    String title = (String) dataSnapshot.child(TITLE).getValue();
                    String story = (String) dataSnapshot.child(STORY).getValue();
                    if (onFriendsScoreUpdate != null) {
                    }
                } catch (Exception e) {
                    Tracer.error(TAG, "fetchUserScore().onDataChange()" + e.getMessage());
                    if (onFriendsScoreUpdate != null) {
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Tracer.debug(TAG, "fetchUserScore().onCancelled() " + databaseError.getMessage());
                if (onFriendsScoreUpdate != null) {
                }
            }
        });
    }

    /**
     * Method to get the app version from play store
     *
     * @param context
     * @return
     */
    public void getAppVersion(final Context context) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(APP_CONFIG);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tracer.debug(TAG, "getAppVersion().onDataChange() " + dataSnapshot.getValue());
                try {
                    long appVer = (Long) dataSnapshot.child(APP_VER).getValue();
                    PreferenceDataUtils.setLatestVersion(context, (int) appVer);
                } catch (Exception e) {
                    Tracer.error(TAG, "getAppVersion().onDataChange()" + e.getMessage());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Tracer.debug(TAG, "getAppVersion().onCancelled() " + databaseError.getMessage());
            }
        });
    }

    /**
     * Callback for notify that user friends score is updated
     */
    public interface OnFriendsScoreUpdate {

    }
}
