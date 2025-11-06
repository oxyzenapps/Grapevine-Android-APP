package com.oxyzenhomes.grapevine.appprefrence;




import android.content.Context;
import android.content.SharedPreferences;

import androidx.core.content.SharedPreferencesCompat;


public class AppPreferences  {


    public static AppPreferences mAppPreference;
    private SharedPreferences mPreferences;
    private static SharedPreferences.Editor mEditor;


    /**
     * Enum tha wrap the various key that will be used in app shared preferences
     */
    enum SharedPrefrencesKey {

        Subscriptionid,
        applicant_id,
        isverify,
        StuID,
        SceID,
        SchoolID,
        StatusType,
        StatffID,
        imagePath,
        UserName,
        fid,
        schoolname,
         RegNo,
        FbUserName,
        FbUserEmail,
        FbAppId,
        GoogleAppId,
        GoogleUserName,
        GoogleUserEmail,
        SessionCount,
        UserOTP,
        CurrentURL,
        Latitude,
        Longitude,
        ShareLink,
        FriendFeedChannelID,
        UserFeedChannelID,
        PubnubChannel,
        IniviteUserRecord,
        IniviteName,
        IniviteEmail,
        SpotifyToken,
        FirebaseTokenID,
        UserPresenceState,
        isAccepted,
        ActivityID,
        LocationPermissionDenied,
        UpdateLaterCount,
        IsUPdateLater,
        RequestCode,
        ContactNumberToAdd,
        ContactEmailToAdd,
        ContactNameToAdd,
        LoginUserMobileNo,
        AgentMode,
        FilterOnFeedChannelID,
        CRMActivityChannelID,
        EntityFeedChannelID

    }

    public void setAgentMode(String Value) {
        mEditor.putString(SharedPrefrencesKey.AgentMode.toString(), Value);
        mEditor.commit();
    }

    public String getAgentMode() {
        return mPreferences.getString(SharedPrefrencesKey.AgentMode.toString(), "");
    }

    public void setFilterOnFeedChannelID(String Value) {
        mEditor.putString(SharedPrefrencesKey.FilterOnFeedChannelID.toString(), Value);
        mEditor.commit();
    }

    public String getFilterOnFeedChannelID() {
        return mPreferences.getString(SharedPrefrencesKey.FilterOnFeedChannelID.toString(), "");
    }
    public void setCRMActivityChannelID(String Value) {
        mEditor.putString(SharedPrefrencesKey.CRMActivityChannelID.toString(), Value);
        mEditor.commit();
    }

    public String getCRMActivityChannelID() {
        return mPreferences.getString(SharedPrefrencesKey.CRMActivityChannelID.toString(), "");
    }
    public void setEntityFeedChannelID(String Value) {
        mEditor.putString(SharedPrefrencesKey.EntityFeedChannelID.toString(), Value);
        mEditor.commit();
    }

    public String getEntityFeedChannelID() {
        return mPreferences.getString(SharedPrefrencesKey.EntityFeedChannelID.toString(), "");
    }

    public Integer getRequestCode(){
        return mPreferences.getInt(SharedPrefrencesKey.RequestCode.toString(),0);
    }
    public void setRequestCode(Integer Value) {
        mEditor.putInt(SharedPrefrencesKey.RequestCode.toString(), Value);
        mEditor.commit();
    }
    public Integer getUpdateLaterCount(){
        return mPreferences.getInt(SharedPrefrencesKey.UpdateLaterCount.toString(),0);
    }
    public void setUpdateLaterCount(Integer Value) {
        mEditor.putInt(SharedPrefrencesKey.UpdateLaterCount.toString(), Value);
        mEditor.commit();
    }
    public Boolean getIsUPdateLater(){
        return mPreferences.getBoolean(SharedPrefrencesKey.IsUPdateLater.toString(),false);
    }
    public void setIsUPdateLater(Boolean value){

        mEditor.putBoolean(SharedPrefrencesKey.IsUPdateLater.toString(), value);
        mEditor.commit();

    }
    public Boolean getisAccepted(){
        return mPreferences.getBoolean(SharedPrefrencesKey.isAccepted.toString(),false);
    }
    public void setIsAccepted(Boolean value){

            mEditor.putBoolean(SharedPrefrencesKey.isAccepted.toString(), value);
            mEditor.commit();

    }
    public Boolean getLocationPermissionDenied(){
        return mPreferences.getBoolean(SharedPrefrencesKey.LocationPermissionDenied.toString(),false);
    }
    public void setLocationPermissionDenied(Boolean value){

        mEditor.putBoolean(SharedPrefrencesKey.LocationPermissionDenied.toString(), value);
        mEditor.commit();

    }
    public String getActivityID(){
        return mPreferences.getString(SharedPrefrencesKey.ActivityID.toString(),"");
    }
    public void setActivityID(String Value){
        mEditor.putString(SharedPrefrencesKey.ActivityID.toString(),Value);
        mEditor.commit();
    }
    public String getUserPresenceState(){
        return mPreferences.getString(SharedPrefrencesKey.UserPresenceState.toString(),"");
    }
    public void setUserPresenceState(String Value){
        mEditor.putString(SharedPrefrencesKey.UserPresenceState.toString(),Value);
        mEditor.commit();
    }
    public String getFirebaseTokenID( ){
        return mPreferences.getString(SharedPrefrencesKey.FirebaseTokenID.toString(), "");
    }
    public void setFirebaseTokenID(String Value){
        mEditor.putString(SharedPrefrencesKey.FirebaseTokenID.toString(),Value);
        mEditor.commit();
    }
    public void setSpotifyToken(String Value) {
        mEditor.putString(SharedPrefrencesKey.SpotifyToken.toString(), Value);
        mEditor.commit();
    }
    public String getSpotifyToken() {
        return mPreferences.getString(SharedPrefrencesKey.SpotifyToken.toString(), "");
    }
    public void setIniviteEmail(String Value) {
        mEditor.putString(SharedPrefrencesKey.IniviteEmail.toString(), Value);
        mEditor.commit();
    }

    public String getIniviteEmail() {
        return mPreferences.getString(SharedPrefrencesKey.IniviteEmail.toString(), "");
    }
    public void setIniviteName(String Value) {
        mEditor.putString(SharedPrefrencesKey.IniviteName.toString(), Value);
        mEditor.commit();
    }

    public String getIniviteName() {
        return mPreferences.getString(SharedPrefrencesKey.IniviteName.toString(), "");
    }

    public void setIniviteUserRecord(String Value) {
        mEditor.putString(SharedPrefrencesKey.IniviteUserRecord.toString(), Value);
        mEditor.commit();
    }

    public String getIniviteUserRecord() {
        return mPreferences.getString(SharedPrefrencesKey.IniviteUserRecord.toString(), "");
    }

    public void setFriendFeedChannelID(String Value) {
        mEditor.putString(SharedPrefrencesKey.FriendFeedChannelID.toString(), Value);
        mEditor.commit();
    }

    public String getFriendFeedChannelID() {
        return mPreferences.getString(SharedPrefrencesKey.FriendFeedChannelID.toString(), "");
    }

    public void setContactNumberToAdd(String Value) {
        mEditor.putString(SharedPrefrencesKey.ContactNumberToAdd.toString(), Value);
        mEditor.commit();
    }

    public String getContactNumberToAdd() {
        return mPreferences.getString(SharedPrefrencesKey.ContactNumberToAdd.toString(), "");
    }

    public void setContactEmailToAdd(String Value) {
        mEditor.putString(SharedPrefrencesKey.ContactEmailToAdd.toString(), Value);
        mEditor.commit();
    }

    public String getContactEmailToAdd() {
        return mPreferences.getString(SharedPrefrencesKey.ContactEmailToAdd.toString(), "");
    }
    public void setContactNameToAdd(String Value) {
        mEditor.putString(SharedPrefrencesKey.ContactNameToAdd.toString(), Value);
        mEditor.commit();
    }

    public String getContactNameToAdd() {
        return mPreferences.getString(SharedPrefrencesKey.ContactNameToAdd.toString(), "");
    }

    public void setPubnubChannel(String Value) {
        mEditor.putString(SharedPrefrencesKey.PubnubChannel.toString(), Value);
        mEditor.commit();
    }

    public String getPubnubChannel() {
        return mPreferences.getString(SharedPrefrencesKey.PubnubChannel.toString(), "");
    }



    public void setUserFeedChannelID(String Value) {
        mEditor.putString(SharedPrefrencesKey.UserFeedChannelID.toString(), Value);
        mEditor.commit();
    }

    public String getUserFeedChannelID() {
        return mPreferences.getString(SharedPrefrencesKey.UserFeedChannelID.toString(), "");
    }
    public void setShareLink(String Value) {
        mEditor.putString(SharedPrefrencesKey.ShareLink.toString(), Value);
        mEditor.commit();
    }

    public String getShareLink() {
        return mPreferences.getString(SharedPrefrencesKey.ShareLink.toString(), "");
    }

    public void setLatitude(String UserOTP) {
        mEditor.putString(SharedPrefrencesKey.Latitude.toString(), UserOTP);
        mEditor.commit();
    }

    public String getLatitude() {
        return mPreferences.getString(SharedPrefrencesKey.Latitude.toString(), "");
    }
    public void setLongitude(String UserOTP) {
        mEditor.putString(SharedPrefrencesKey.Longitude.toString(), UserOTP);
        mEditor.commit();
    }

    public String getLongitude() {
        return mPreferences.getString(SharedPrefrencesKey.Longitude.toString(), "");
    }

    public void setCurrentURL(String UserOTP) {
        mEditor.putString(SharedPrefrencesKey.CurrentURL.toString(), UserOTP);
        mEditor.commit();
    }

    public String getCurrentURL() {
        return mPreferences.getString(SharedPrefrencesKey.CurrentURL.toString(), "");
    }
    public void setUserOTP(String UserOTP) {
        mEditor.putString(SharedPrefrencesKey.UserOTP.toString(), UserOTP);
        mEditor.commit();
    }

    public String getUserOTP() {
        return mPreferences.getString(SharedPrefrencesKey.UserOTP.toString(), "");
    }

    public void setSessionCount(String SessionCount) {
        mEditor.putString(SharedPrefrencesKey.SessionCount.toString(), SessionCount);
        mEditor.commit();
    }

    public String getSessionCount() {
        return mPreferences.getString(SharedPrefrencesKey.SessionCount.toString(), "");
    }

    public void setRegNo(String RegNo) {
        mEditor.putString(SharedPrefrencesKey.RegNo.toString(), RegNo);
        mEditor.commit();
    }

    public String getRegNo() {
        return mPreferences.getString(SharedPrefrencesKey.RegNo.toString(), "");
    }



    public void setFbUserName(String FbUserName) {
        mEditor.putString(SharedPrefrencesKey.FbUserName.toString(), FbUserName);
        mEditor.commit();
    }

    public String getFbUserName() {
        return mPreferences.getString(SharedPrefrencesKey.FbUserName.toString(), "");
    }
    public void setFbUserEmail(String FbUserEmail) {
        mEditor.putString(SharedPrefrencesKey.FbUserEmail.toString(), FbUserEmail);
        mEditor.commit();
    }

    public String getFbUserEmail() {
        return mPreferences.getString(SharedPrefrencesKey.FbUserEmail.toString(), "");
    }

    public void setFbAppId(String FbAppId) {
        mEditor.putString(SharedPrefrencesKey.FbAppId.toString(), FbAppId);
        mEditor.commit();
    }

    public String getFbAppId() {
        return mPreferences.getString(SharedPrefrencesKey.FbAppId.toString(), "");
    }

    public void setGoogleUserName(String GoogleUserName) {
        mEditor.putString(SharedPrefrencesKey.GoogleUserName.toString(), GoogleUserName);
        mEditor.commit();
    }

    public String getGoogleUserName() {
        return mPreferences.getString(SharedPrefrencesKey.GoogleUserName.toString(), "");
    }

    public void setGoogleAppId(String GoogleAppId) {
        mEditor.putString(SharedPrefrencesKey.GoogleAppId.toString(), GoogleAppId);
        mEditor.commit();
    }

    public String getGoogleAppId() {
        return mPreferences.getString(SharedPrefrencesKey.GoogleAppId.toString(), "");
    }

    public void setGoogleUserEmail(String GoogleUserEmail) {
        mEditor.putString(SharedPrefrencesKey.GoogleUserEmail.toString(), GoogleUserEmail);
        mEditor.commit();
    }

    public String getGoogleUserEmail() {
        return mPreferences.getString(SharedPrefrencesKey.GoogleUserEmail.toString(), "");
    }


    /////////////
    public void setStuID(String StuID) {
        mEditor.putString(SharedPrefrencesKey.StuID.toString(), StuID);
        mEditor.commit();
    }

    public String getStuID() {
        return mPreferences.getString(SharedPrefrencesKey.StuID.toString(), "");
    }

    public void setfid(String fid) {
        mEditor.putString(SharedPrefrencesKey.fid.toString(), fid);
        mEditor.commit();
    }

    public String getfid() {
        return mPreferences.getString(SharedPrefrencesKey.fid.toString(), "");
    }


    public void setschoolname(String schoolname) {
        mEditor.putString(SharedPrefrencesKey.schoolname.toString(), schoolname);
        mEditor.commit();
    }

    public String getschoolname() {
        return mPreferences.getString(SharedPrefrencesKey.schoolname.toString(), "");
    }

    public void setSceID(String SceID) {
        mEditor.putString(SharedPrefrencesKey.SceID.toString(), SceID);
        mEditor.commit();
    }

    public String getSceID() {
        return mPreferences.getString(SharedPrefrencesKey.SceID.toString(), "");
    }


    public void setSchoolID(String SchoolID) {
        mEditor.putString(SharedPrefrencesKey.SchoolID.toString(), SchoolID);
        mEditor.commit();
    }

    public String getSchoolID() {
        return mPreferences.getString(SharedPrefrencesKey.SchoolID.toString(), "");
    }

    public void setStatusType(String StatusType) {
        mEditor.putString(SharedPrefrencesKey.StatusType.toString(), StatusType);
        mEditor.commit();
    }

    public String getStatusType() {
        return mPreferences.getString(SharedPrefrencesKey.StatusType.toString(), "");
    }


    public void setStatffID(String StatffID) {
        mEditor.putString(SharedPrefrencesKey.StatffID.toString(), StatffID);
        mEditor.commit();
    }

    public String getStatffID() {
        return mPreferences.getString(SharedPrefrencesKey.StatffID.toString(), "");
    }


    public void setimagePath(String imagePath) {
        mEditor.putString(SharedPrefrencesKey.imagePath.toString(), imagePath);
        mEditor.commit();
    }

    public String getimagePath() {
        return mPreferences.getString(SharedPrefrencesKey.imagePath.toString(), "");
    }



    public void setUserName(String UserName) {
        mEditor.putString(SharedPrefrencesKey.UserName.toString(), UserName);
        mEditor.commit();
    }

    public String getUserName() {
        return mPreferences.getString(SharedPrefrencesKey.UserName.toString(), "");
    }


    public void setSubscriptionid(String Subscriptionid) {
        mEditor.putString(SharedPrefrencesKey.Subscriptionid.toString(), Subscriptionid);
        mEditor.commit();
    }

    public String getSubscriptionid() {
        return mPreferences.getString(SharedPrefrencesKey.Subscriptionid.toString(), "");
    }


    public void setApplicant_id(String lName) {
        mEditor.putString(SharedPrefrencesKey.applicant_id.toString(), lName);
        mEditor.commit();
    }

    public String getApplicant_id() {
        return mPreferences.getString(SharedPrefrencesKey.applicant_id.toString(), "");
    }

    public void setLoginUserMobileNo(String lName) {
        mEditor.putString(SharedPrefrencesKey.LoginUserMobileNo.toString(), lName);
        mEditor.commit();
    }

    public String getLoginUserMobileNo() {
        return mPreferences.getString(SharedPrefrencesKey.LoginUserMobileNo.toString(), "");
    }



    public void setIsVerify(Boolean isVerify) {
        mEditor.putBoolean(SharedPrefrencesKey.isverify.toString(), isVerify);
        mEditor.commit();
    }
    public Boolean getIsVerify() {
        return mPreferences.getBoolean(SharedPrefrencesKey.isverify.toString(),false);
    }



    public AppPreferences(Context context) {
        mPreferences = context.getSharedPreferences(PreferenceID.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public static synchronized AppPreferences getInstance(Context context) {
        if (mAppPreference == null) {
            mAppPreference = new AppPreferences(context);
        }
        return mAppPreference;
    }

    public void clearAppPreference() {
        if (mPreferences != null) {
            mEditor.clear().commit();
        }
    }


}
