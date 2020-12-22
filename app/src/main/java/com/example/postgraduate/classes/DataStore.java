package com.example.postgraduate.classes;

import android.content.Context;
import android.content.res.Resources;

import com.example.postgraduate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DataStore {

    public static final String KEY_ID = "ID";

    //Users json file
    public static final String KEY_USERNAME = "USERNAME";
    public static final String KEY_PASSWORD = "PASSWORD";
    public static final String KEY_FULLNAME = "FULLNAME";
    public static final String KEY_STUDENT_OR_PROFESSOR = "STUDENT_OR_PROFESSOR";
    public static final String KEY_LESSONS_IDS = "LESSONS_IDS";
    public static final String KEY_DISSERTATIONS_IDS = "DISSERTATIONS_IDS";

    //Lessons json file
    public static final String KEY_LESSON_NAME = "NAME";
    public static final String KEY_SEMESTER = "SEMESTER";
    public static final String KEY_LECTURE_DATES = "LECTURE_DATES";
    public static final String KEY_DISSERTATION = "DISSERTATION";

    public static final String KEY_POSITION = "POSITION";

    public static boolean STUDENT_OR_PROFESSOR;

    public static final String KEY_LESSON_STUDENT_1 = "LESSON_STUDENT_1";
    public static final String KEY_LESSON_STUDENT_2 = "LESSON_STUDENT_2";
    public static final String KEY_LESSON_STUDENT_3 = "LESSON_STUDENT_3";
    public static final String KEY_LESSON_STUDENT_4 = "LESSON_STUDENT_4";
    public static final String KEY_LESSON_STUDENT_5 = "LESSON_STUDENT_5";
    public static final String KEY_LESSON_STUDENT_6 = "LESSON_STUDENT_6";
    public static final String KEY_LESSON_STUDENT_7 = "LESSON_STUDENT_7";
    public static final String KEY_LESSON_STUDENT_8 = "LESSON_STUDENT_8";
    public static final String KEY_LESSON_PROFESSOR_1 = "LESSON_PROFESSOR_1";
    public static final String KEY_LESSON_PROFESSOR_2 = "LESSON_PROFESSOR_2";
    public static final String KEY_LESSON_PROFESSOR_3 = "LESSON_PROFESSOR_3";
    public static final String KEY_LESSON = "LESSON";

    public static Context AppContext = null;
    public static Resources AppResources = null;
    public static String[] lessonsAsemester = null;
    public static String[] lessonsBsemester = null;
    public static String[] dissertationsCsemester = null;
    public static ArrayList<HashMap<String, Object>> Users = new ArrayList<HashMap<String, Object>>();
    public static ArrayList<HashMap<String, Object>> Lessons;
    public static ArrayList<HashMap<String, Object>> Dissertations;

   public static void Init(Context context) {
       AppContext = context;
       AppResources = AppContext.getResources();

   }

    public static void LoadUsers() {
        DataStore.Users.clear();
        String contents = AssetsUtils.getFileContentsFromAssets(AppContext, "users.json");
        //String urlString = String.format();
        //String contents = NetworkUtils.getFileContentsFromFromUrl(urlString);
        JSONObject json = JsonParser.getJsonObject(contents);
        JSONArray jUsers = json.optJSONArray("Users");
        if (jUsers == null) return;
        int nUsers = jUsers.length();
        for (int i = 0; i < nUsers; i++) {
            JSONObject jCurUser = jUsers.optJSONObject(i);

            int userID = jCurUser.optInt(DataStore.KEY_ID, 0);
            String userUsername = jCurUser.optString(DataStore.KEY_USERNAME);
            String userPassword = jCurUser.optString(DataStore.KEY_PASSWORD);
            String userFullname = jCurUser.optString(DataStore.KEY_FULLNAME);
            boolean userStudent_or_Professor = jCurUser.optBoolean(DataStore.KEY_STUDENT_OR_PROFESSOR);

            JSONArray userLessonsArray = jCurUser.optJSONArray(DataStore.KEY_LESSONS_IDS);
            ArrayList<String> userLessonIDs = new ArrayList<>();
            if (userLessonsArray != null) {
                for (int j = 0; j < userLessonsArray.length(); j++) {
                    try {
                        userLessonIDs.add(userLessonsArray.getString(j));
                    } catch (JSONException e) {
                        System.out.println("Lesson ID does not exist");
                    }
                }
            }

            ArrayList<String> dissertationIDs = new ArrayList<>();
            if (!userStudent_or_Professor) {
                JSONArray dissertationsArray = jCurUser.optJSONArray(DataStore.KEY_DISSERTATIONS_IDS);
                if (dissertationsArray != null) {
                    for (int j = 0; j < dissertationsArray.length(); j++) {
                        try {
                            dissertationIDs.add(dissertationsArray.getString(j));
                        } catch (JSONException e) {
                            System.out.println("Dissertation ID does not exist");
                        }
                    }
                }
            }

            // hold each user in a HashMap (Associative Array)
            HashMap<String, Object> user = new HashMap<String, Object>();
            user.put(DataStore.KEY_ID, userID);
            user.put(DataStore.KEY_USERNAME, userUsername);
            user.put(DataStore.KEY_PASSWORD, userPassword);
            user.put(DataStore.KEY_FULLNAME, userFullname);
            user.put(DataStore.KEY_STUDENT_OR_PROFESSOR, userStudent_or_Professor);
            user.put(DataStore.KEY_LESSONS_IDS, userLessonIDs);
            user.put(DataStore.KEY_DISSERTATIONS_IDS, dissertationIDs);

            DataStore.Users.add(user);
        }
    }

    public static void LoadLessons() {
        Lessons = new ArrayList<HashMap<String, Object>>();
        //Read json from file in Assets
        String contents = AssetsUtils.getFileContentsFromAssets(AppContext, "lessons.json");
        //Read json from Url
        //String urlString = String.format();
        //String contents = NetworkUtils.getFileContentsFromFromUrl(urlString);
        JSONObject json = JsonParser.getJsonObject(contents);
        JSONArray jLessons = json.optJSONArray("Lessons");
        if (jLessons == null) return;
        int nLessons = jLessons.length();
        for (int i = 0; i < nLessons; i++) {
            JSONObject jCurLesson = jLessons.optJSONObject(i);
            String lesson_ID = jCurLesson.optString(DataStore.KEY_ID);
            String lesson_Lesson = jCurLesson.optString(DataStore.KEY_LESSON_NAME);
            String lesson_Semester = jCurLesson.optString(DataStore.KEY_SEMESTER);
            JSONArray LectureDatesArray = jCurLesson.optJSONArray(DataStore.KEY_LECTURE_DATES);
            ArrayList<String> LectureDates = new ArrayList<>();
            if (LectureDatesArray != null) {
                for (int j = 0; j < LectureDatesArray.length(); j++) {
                    try {
                        LectureDates.add(LectureDatesArray.getString(j));
                    } catch (JSONException e) {
                        System.out.println("Lecture date does not exist");
                    }
                }
            }

            // hold each user in a HashMap (Associative Array)
            HashMap<String, Object> lesson = new HashMap<String, Object>();
            lesson.put(DataStore.KEY_ID, lesson_ID);
            lesson.put(DataStore.KEY_LESSON_NAME, lesson_Lesson);
            lesson.put(DataStore.KEY_SEMESTER, lesson_Semester);
            lesson.put(DataStore.KEY_LECTURE_DATES, LectureDates);

            DataStore.Lessons.add(lesson);
        }

        Dissertations = new ArrayList<>();
        JSONArray jDissetrations = json.optJSONArray("Dissertations");
        if (jDissetrations == null) return;
        int nDissertations = jDissetrations.length();
        for (int i = 0; i < nDissertations; i++) {
            JSONObject jCurLesson = jDissetrations.optJSONObject(i);
            String lesson_ID = jCurLesson.optString(DataStore.KEY_ID);
            String lesson_Dissertation = jCurLesson.optString(DataStore.KEY_DISSERTATION);

            // hold each user in a HashMap (Associative Array)
            HashMap<String, Object> dissertation = new HashMap<String, Object>();
            dissertation.put(DataStore.KEY_ID, lesson_ID);
            dissertation.put(DataStore.KEY_DISSERTATION, lesson_Dissertation);

            DataStore.Dissertations.add(dissertation);
        }
    }

}