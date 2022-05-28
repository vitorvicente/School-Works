package edu.bu.cs411.Registrar;

import edu.bu.cs411.Config.GeneralConfig;
import edu.bu.cs411.Config.RegistrarConfig;

import java.time.Year;
import java.util.Date;

/**
 * Semester Object Class.
 *
 * @author dsullo@bu.edu.
 * @version 1.0.0.
 */
public class Semester {

    /**
     * Semester Term.
     */
    private Term term;
    /**
     * Semester Year.
     */
    private Year year;

    /**
     * Base Constructor for the Semester Object.
     *
     * @param term Semester Term.
     * @param year Semester Year.
     */
    public Semester(Term term, Year year) {
        this.setTerm(term);
        this.setYear(year);
    }

    /**
     * String Constructor for the Semester Object.
     *
     * @param semester String Value for the Semester.
     * @throws InstantiationException Illegal Initiation Parameters.
     */
    public Semester(String semester) throws InstantiationException {
        String[] splitSemester = semester.split(RegistrarConfig.SEMESTER_STRING_SPLITTER);
        if (splitSemester.length != RegistrarConfig.SEMESTER_SPLIT_SIZE)
            throw new InstantiationException(GeneralConfig.ILLEGAL_ARGUMENTS_ERROR);

        this.setTerm(Term.getTerm(splitSemester[RegistrarConfig.SEMESTER_SPLIT_TERM_INDEX]));
        this.setYear(Year.of(Integer.parseInt(splitSemester[RegistrarConfig.SEMESTER_SPLIT_YEAR_INDEX])));
    }

    /**
     * Date Constructor for the Semester Object.
     *
     * @param semester Date Value for the Semester.
     */
    public Semester(Date semester) {
        this.setTerm(semester.getMonth() <= RegistrarConfig.SEMESTER_SPRING_TERM_END ? Term.SPRING :
                (semester.getMonth() <= RegistrarConfig.SEMESTER_SUMMER_TERM_END ? Term.SUMMER : Term.FALL));
        this.setYear(Year.of(semester.getYear()));
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public Semester() throws InstantiationException {
        throw new InstantiationException(GeneralConfig.ILLEGAL_EMPTY_CONSTRUCTOR_ERROR);
    }

    /**
     * Helper Method to move to the Next Semester in the cycle.
     *
     * @return Next Semester in the cycle.
     */
    public Semester next() {
        Term nextTerm = this.getTerm().next();
        Year nextYear = this.getYear();
        if (nextTerm == Term.SPRING)
            nextYear = Year.of(this.getYear().getValue() + RegistrarConfig.SEMESTER_YEAR_JUMP);

        return new Semester(nextTerm, nextYear);
    }

    /**
     * Retrieve the current Semester Term.
     *
     * @return Semester Term.
     */
    public Term getTerm() {
        return this.term;
    }

    /**
     * Set the current Semester Term.
     *
     * @param term Semester Term.
     */
    public void setTerm(Term term) {
        this.term = term;
    }

    /**
     * Retrieve the current Semester Year.
     *
     * @return Semester Year.
     */
    public Year getYear() {
        return this.year;
    }

    /**
     * Set the current Semester Year.
     *
     * @param year Semester Year.
     */
    public void setYear(Year year) {
        this.year = year;
    }

    /**
     * Helper Method to check whether this Semester is equal to another Semester.
     *
     * @param o Object to compare to.
     * @return Whether this Semester is equal to another Semester.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Semester castedObject)
            return (this.getYear().getValue() == castedObject.getYear().getValue())
                    && (this.getTerm() == castedObject.getTerm());

        return false;
    }

    /**
     * Retrieve the String Value for the Semester.
     *
     * @return String Value for the Semester.
     */
    @Override
    public String toString() {
        return this.getTerm().toString() + RegistrarConfig.SEMESTER_STRING_SPLITTER + this.getYear().toString();
    }

}
