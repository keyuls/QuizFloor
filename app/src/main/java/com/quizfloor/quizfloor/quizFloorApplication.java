package com.quizfloor.quizfloor;

import android.app.Application;
import android.support.annotation.NonNull;

import com.parse.ParseObject;

import org.json.JSONObject;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by keyul on 7/26/2015.
 */
public class quizFloorApplication extends Application {

    // List of friends the user can invite (have not installed the app).
    private List<JSONObject> invitableFriends;
    private  String userId;
    private  String userName;
    private String reciverId;
    private boolean challengeMode=false;
    List<ParseObject> queList;
    List<JSONObject> friendScoreBoardList;
    int fbScore=0;
    private String challengerScore;
    private String challengerName;
    private String selectedSubCatagory;
    String challengeObjId;
    List<videoItems> videoCatList;
    List<videoItems> videoSubCatList;
    List<videoItems> videoWatchList;
    private boolean videoCatMode=false;
    private String VIDEO_ID;

    public String getVIDEO_ID() {
        return VIDEO_ID;
    }

    public void setVIDEO_ID(String VIDEO_ID) {
        this.VIDEO_ID = VIDEO_ID;
    }

    public List<videoItems> getVideoWatchList() {
        return videoWatchList;
    }

    public void setVideoWatchList(List<videoItems> videoWatchList) {
        this.videoWatchList = videoWatchList;
    }

    public boolean isVideoCatMode() {
        return videoCatMode;
    }

    public void setVideoCatMode(boolean videoCatMode) {
        this.videoCatMode = videoCatMode;
    }




    public List<videoItems> getVideoCatList() {
        return videoCatList;
    }

    public quizFloorApplication setVideoCatList(List<videoItems> videoCatList) {
        this.videoCatList = videoCatList;
        return null;
    }

    public List<videoItems> getVideoSubCatList() {
        return videoSubCatList;
    }

    public void setVideoSubCatList(List<videoItems> videoSubCatList) {
        this.videoSubCatList = videoSubCatList;
    }



    public List<JSONObject> getFriendScoreBoardList() {
        return friendScoreBoardList;
    }

    public void setFriendScoreBoardList(List<JSONObject> friendScoreBoardList) {
        this.friendScoreBoardList = friendScoreBoardList;
    }



    public int getFbScore() {
        return fbScore;
    }

    public void setFbScore(int fbScore) {
        this.fbScore = fbScore;
    }


    List<ParseObject> challengeObj=new List<ParseObject>() {
        @Override
        public void add(int location, ParseObject object) {

        }

        @Override
        public boolean add(ParseObject object) {
            return false;
        }

        @Override
        public boolean addAll(int location, Collection<? extends ParseObject> collection) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends ParseObject> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean contains(Object object) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return false;
        }

        @Override
        public ParseObject get(int location) {
            return null;
        }

        @Override
        public int indexOf(Object object) {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @NonNull
        @Override
        public Iterator<ParseObject> iterator() {
            return null;
        }

        @Override
        public int lastIndexOf(Object object) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<ParseObject> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<ParseObject> listIterator(int location) {
            return null;
        }

        @Override
        public ParseObject remove(int location) {
            return null;
        }

        @Override
        public boolean remove(Object object) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return false;
        }

        @Override
        public ParseObject set(int location, ParseObject object) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @NonNull
        @Override
        public List<ParseObject> subList(int start, int end) {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(T[] array) {
            return null;
        }
    };


    public String getChallengeObjId() {
        return challengeObjId;
    }

    public void setChallengeObjId(String challengeObjId) {
        this.challengeObjId = challengeObjId;
    }



    public String getSelectedSubCatagory() {
        return selectedSubCatagory;
    }

    public void setSelectedSubCatagory(String selectedSubCatagory) {
        this.selectedSubCatagory = selectedSubCatagory;
    }




    private List<ParseObject> catagoryObj= new List<ParseObject>() {
        @Override
        public void add(int location, ParseObject object) {

        }

        @Override
        public boolean add(ParseObject object) {
            return false;
        }

        @Override
        public boolean addAll(int location, Collection<? extends ParseObject> collection) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends ParseObject> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean contains(Object object) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return false;
        }

        @Override
        public ParseObject get(int location) {
            return null;
        }

        @Override
        public int indexOf(Object object) {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @NonNull
        @Override
        public Iterator<ParseObject> iterator() {
            return null;
        }

        @Override
        public int lastIndexOf(Object object) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<ParseObject> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<ParseObject> listIterator(int location) {
            return null;
        }

        @Override
        public ParseObject remove(int location) {
            return null;
        }

        @Override
        public boolean remove(Object object) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return false;
        }

        @Override
        public ParseObject set(int location, ParseObject object) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @NonNull
        @Override
        public List<ParseObject> subList(int start, int end) {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(T[] array) {
            return null;
        }
    };
   private  List<ParseObject> subCatagoryObj=new List<ParseObject>() {
       @Override
       public void add(int location, ParseObject object) {

       }

       @Override
       public boolean add(ParseObject object) {
           return false;
       }

       @Override
       public boolean addAll(int location, Collection<? extends ParseObject> collection) {
           return false;
       }

       @Override
       public boolean addAll(Collection<? extends ParseObject> collection) {
           return false;
       }

       @Override
       public void clear() {

       }

       @Override
       public boolean contains(Object object) {
           return false;
       }

       @Override
       public boolean containsAll(Collection<?> collection) {
           return false;
       }

       @Override
       public ParseObject get(int location) {
           return null;
       }

       @Override
       public int indexOf(Object object) {
           return 0;
       }

       @Override
       public boolean isEmpty() {
           return false;
       }

       @NonNull
       @Override
       public Iterator<ParseObject> iterator() {
           return null;
       }

       @Override
       public int lastIndexOf(Object object) {
           return 0;
       }

       @NonNull
       @Override
       public ListIterator<ParseObject> listIterator() {
           return null;
       }

       @NonNull
       @Override
       public ListIterator<ParseObject> listIterator(int location) {
           return null;
       }

       @Override
       public ParseObject remove(int location) {
           return null;
       }

       @Override
       public boolean remove(Object object) {
           return false;
       }

       @Override
       public boolean removeAll(Collection<?> collection) {
           return false;
       }

       @Override
       public boolean retainAll(Collection<?> collection) {
           return false;
       }

       @Override
       public ParseObject set(int location, ParseObject object) {
           return null;
       }

       @Override
       public int size() {
           return 0;
       }

       @NonNull
       @Override
       public List<ParseObject> subList(int start, int end) {
           return null;
       }

       @NonNull
       @Override
       public Object[] toArray() {
           return new Object[0];
       }

       @NonNull
       @Override
       public <T> T[] toArray(T[] array) {
           return null;
       }
   };
    private  String selectedCatagory;

    public String getSelectedCatagory() {
        return selectedCatagory;
    }

    public void setSelectedCatagory(String selectedCatagory) {
        this.selectedCatagory = selectedCatagory;
    }



    public List<ParseObject> getCatagoryObj() {
        return catagoryObj;
    }

    public void setCatagoryObj(List<ParseObject> catagoryObj) {
        this.catagoryObj = catagoryObj;
    }

    public List<ParseObject> getSubCatagoryObj() {
        return subCatagoryObj;
    }

    public void setSubCatagoryObj(List<ParseObject> subCatagoryObj) {
        this.subCatagoryObj = subCatagoryObj;
    }



    public String getChallengerName() {
        return challengerName;
    }

    public void setChallengerName(String challengerName) {
        this.challengerName = challengerName;
    }




    public String getChallengerScore() {
        return challengerScore;
    }

    public void setChallengerScore(String challengerScore) {
        this.challengerScore = challengerScore;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getIndexList() {
        return indexList;
    }

    public void setIndexList(String indexList) {
        this.indexList = indexList;
    }

    private String topic;
    private String indexList;

    public List<ParseObject> getChallengeObj() {
        return challengeObj;
    }

    public void setChallengeObj(List<ParseObject> challengeObj) {
        this.challengeObj = challengeObj;
    }



    public List<ParseObject> getQueList() {
        return queList;
    }
    public void setQueList(List<ParseObject> queList) {
        this.queList = queList;
    }


    public boolean isChallengeMode() {
        return challengeMode;
    }

    public void setChallengeMode(boolean challengeMode) {
        this.challengeMode = challengeMode;
    }



    public String getReciverId() {
        return reciverId;
    }

    public void setReciverId(String reciverId) {
        this.reciverId = reciverId;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }




    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public List<JSONObject> getChallengbleFriends() {
        return ChallengbleFriends;
    }

    public void setChallengbleFriends(List<JSONObject> challengbleFriends) {
        ChallengbleFriends = challengbleFriends;
    }

    private List<JSONObject> ChallengbleFriends;

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    boolean isLoggedIn = false;

    public List<JSONObject> getInvitableFriends() {
        return invitableFriends;
    }

    public void setInvitableFriends(List<JSONObject> invitableFriends) {
        this.invitableFriends = invitableFriends;
    }


}
