package project.objects;

/**
 * Defines the type of User which determines the discount
 */
public enum Status {
    MILITARY, EMPLOYEE, TEACHER, STUDENT, SENIOR, NONE;

    Status() {
        int value = ordinal();
    }
}
