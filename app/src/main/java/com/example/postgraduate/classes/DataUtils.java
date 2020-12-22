package com.example.postgraduate.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataUtils {

    public static ArrayList<Map<String, Object>> getSemesterLessons(String semester, List<String> lessonIDs){

        //Keep a list of current user lessons.
        final ArrayList<Map<String, Object>> lessons = new ArrayList<>();
        for (int i = 0; i < lessonIDs.size(); i++) {
            String lessonID = lessonIDs.get(i);
            HashMap<String, Object> lesson = getLesson(lessonID);
            String lessonSemester = String.valueOf(lesson.get(DataStore.KEY_SEMESTER));
            if (semester.equals(lessonSemester)) {
                lessons.add(lesson);
            }
        }
        return lessons;
    }

    // Finds a user (current user etc)
    public static HashMap<String, Object> getUser(String username) {

        for (int i = 0; i < DataStore.Users.size() && username != null; i++) {

            HashMap<String, Object> user = DataStore.Users.get(i);
            String current_username = String.valueOf(user.get(DataStore.KEY_USERNAME));
            if (username.equals(current_username)) {
                return user;
            }
        }
        return new HashMap<String, Object>();
    }

    // Finds and returns a lesson of the given ID
    public static HashMap<String, Object> getLesson(String lessonID) {
        for (int i = 0; i < DataStore.Lessons.size(); i++) {
            HashMap<String, Object> lesson = DataStore.Lessons.get(i);
            String currentLessonID = String.valueOf(lesson.get(DataStore.KEY_ID));
            if (currentLessonID.equals(lessonID)) {
                return lesson;
            }
        }
        return new HashMap<>();
    }

    // Finds the students who have the given lesson
    public static ArrayList<HashMap<String, Object>> getLessonStudents(String lesson_ID) {
        ArrayList<HashMap<String, Object>> lessonStudents = new ArrayList<>();
        //For each of the users
        for (int j = 0; j < DataStore.Users.size(); j++) {
            HashMap<String, Object> student = DataStore.Users.get(j);
            boolean isStudent = (boolean) student.get(DataStore.KEY_STUDENT_OR_PROFESSOR);
            if (isStudent) {
                ArrayList<String> student_lessons_IDs = (ArrayList<String>) student.get(DataStore.KEY_LESSONS_IDS);
                if (student_lessons_IDs.contains(lesson_ID)) {
                    lessonStudents.add(student);
                }
            }
        }
        return lessonStudents;
    }

}
