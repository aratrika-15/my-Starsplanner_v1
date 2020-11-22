package stars;

import java.util.Comparator;

public class PercRecommendedSorter implements Comparator<Course> {
    /**
     * Sort courses by percentage recommended.
     * @param c1
     * @param c2
     * @return int
     */

    public int compare(Course c1, Course c2) {
        double d = c2.getPercRecommended() - c1.getPercRecommended();
        if (d >0) {return 1;}
        else if (d<0) {return -1;}
        else {return 0;}
    }
}