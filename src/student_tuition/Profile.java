package student_tuition;

/**
 * Class that stores a student profile which
 * includes the student's name and major.
 *
 * @author Andy Li, Henry Lin
 */
public class Profile {
    private String name;
    private Major major; //5 majors and 2-charater each: CS, IT, BA, EE, ME

    /**
     * constructor method that stores the students name and major
     *
     * @param name student name
     * @param major student major
     */
    public Profile(String name, Major major){
        this.name = name;
        this.major = major;
    }

    /**
     * compares two profiles to see if they are the same
     *
     * @param obj the object (profile) to be compared to
     * @return whether or not the to profiles are the same
     */
    @Override
    public boolean equals(Object obj) {;
        if(obj instanceof Profile){
            Profile comp = (Profile) obj;
            return this.name.equals(comp.name) && this.major.equals(comp.major);
        }
        return false;
    }

    /**
     * Converts a profile to string
     *
     * @return the string form of the profile
     */
    @Override
    public String toString() {
        String profile = this.name + ":" + this.major.toString();
        return profile;
    }

    /**
     * gets the name of the student
     *
     * @return string of the student's name
     */
    public String getName(){
        return this.name.toString();
    }
}
