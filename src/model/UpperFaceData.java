package model;

/**
 * Wrapper class for the upper face data
 * Data consists of raiseBrow, and furrowBrow
 *
 * @author Team 2, SER 516
 * @version 1.0 April 10, 2018
 */
public class UpperFaceData {

    public static final String RAISE_BROW = "Raise Brow";
    public static final String FURROW_BROW = "Furrow Brow";

    private double raiseBrow;
    private double furrowBrow;

    public UpperFaceData() {
        reset();
    }

    /* reset all data to its default value */
    public void reset() {
        raiseBrow = 0;
        furrowBrow = 0;
    }

    public double getRaiseBrow() {
        return raiseBrow;
    }

    public void setRaiseBrow(double raiseBrow) {
        this.raiseBrow = raiseBrow;
    }

    public double getFurrowBrow() {
        return furrowBrow;
    }

    public void setFurrowBrow(double furrowBrow) {
        this.furrowBrow = furrowBrow;
    }
}
