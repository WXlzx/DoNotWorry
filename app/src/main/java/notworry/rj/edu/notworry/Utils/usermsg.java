package notworry.rj.edu.notworry.Utils;

import notworry.rj.edu.notworry.Entity.Groups;
import notworry.rj.edu.notworry.Entity.Users;

/**
 * Created by www15 on 2018/5/30.
 */

public class usermsg {
    public static Users user;
    public static Groups groups;

    public static Users getUser() {
        return user;
    }

    public static void setUser(Users user) {
        usermsg.user = user;
    }

    public static Groups getGroups() {
        return groups;
    }

    public static void setGroups(Groups groups) {
        usermsg.groups = groups;
    }
}
