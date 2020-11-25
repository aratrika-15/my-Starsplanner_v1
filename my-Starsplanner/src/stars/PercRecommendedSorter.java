package stars;

import java.util.Comparator;

/**
 * Comparator class used in sorting of Courses by percentage of students recommending the course
 */
public class PercRecommendedSorter implements Comparator<Course> {
    /**
     * Sort courses by percentage recommended.
     * @param c1 first course to be compared
     * @param c2 second course to be compared
     * @return int
     *
     */

    public int compare(Course c1, Course c2) {
        double d = c2.getPercRecommended() - c1.getPercRecommended();
        if (d >0) {return 1;}
        else if (d<0) {return -1;}
        else {return 0;}
    }
}