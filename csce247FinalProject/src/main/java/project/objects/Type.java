package project.objects;

/**
 * Defines the type of event
 */
public enum Type {
    MOVIE, CONCERT, PLAY, NONE;

    Type() {
        int value = ordinal();
    }
}
