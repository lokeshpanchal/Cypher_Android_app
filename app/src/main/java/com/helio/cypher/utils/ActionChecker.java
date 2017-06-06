package com.helio.cypher.utils;

public class ActionChecker {

  //  private static int[] points = {3, 6, 15, 20, 30, 50, 70, 100, 150, 200, 225, 250, 300};

    public static boolean checker(int count)
    {
      /*  for (int i : points)
            if (count == i)*/
                return true;
       // return false;
    }

    /*public static void checkTheDailyCommentsCount() {
        ParseUser user = ParseUser.getCurrentUser();



        if (user == null)
            return;
        try {
            if (user.get(Constants.USER_COMMENT_DATE) == null) {
                user.put(Constants.USER_COMMENT_DATE, Calendar.getInstance().getTime());
                //user.put(Constants.USER_COMMENT_COUNT, Constants.USER_COMMENTS_LIMIT);
                user.saveInBackground();
            } else {
                if (System.currentTimeMillis() - user.getDate(Constants.USER_COMMENT_DATE).getTime() > Constants.ONE_DAY) {
                    user.put(Constants.USER_COMMENT_DATE, Calendar.getInstance().getTime());
                  //  user.put(Constants.USER_COMMENT_COUNT, Constants.USER_COMMENTS_LIMIT);
                    user.saveInBackground();
                    HelpController.updateHelpUsersArray(null);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean checkDailyHelp() {
        ParseUser user = ParseUser.getCurrentUser();

        if (user == null)
            return false;

        if (user.get(Constants.USER_HELP_DATE) == null) {
            return true;
        } else if (System.currentTimeMillis() - user.getDate(Constants.USER_HELP_DATE).getTime() > Constants.ONE_DAY) {
            return true;
        }

        return false;
    }*/
}
