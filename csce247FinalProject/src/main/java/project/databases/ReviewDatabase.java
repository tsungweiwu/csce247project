package project.databases;

import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import project.objects.Event;
import project.objects.EventReview;
import project.objects.Review;

public class ReviewDatabase extends DataManager {

    private HashSet<EventReview> eventReviews = new HashSet<>();

    public ReviewDatabase() {
        loadReviews();
    }

    public void writeReview(Event event, String comment, int rating) {
        if (!containsEventReview(event)) {
            EventReview eventReview = new EventReview();
            eventReview.setTitle(event.getTitle());
            eventReview.getReviews().add(new Review(rating, comment));
            getEventReviews().add(eventReview);
        } else {
            EventReview existingEventReview = findEventReview(event);
            assert existingEventReview != null;
            existingEventReview.getReviews().add(new Review(rating, comment));
            getEventReviews().add(existingEventReview);
        }
        saveReviews();
    }

    private boolean containsEventReview(Event e) {
        for (EventReview eventReview : getEventReviews()) {
            if (eventReview.getTitle().equalsIgnoreCase(e.getTitle())) {
                return true;
            }
        }
        return false;
    }

    public EventReview findEventReview(Event e) {
        for (EventReview eventReview : getEventReviews()) {
            if (eventReview.getTitle().trim().equalsIgnoreCase(e.getTitle().trim())) {
                return eventReview;
            }
        }
        EventReview eventReview = new EventReview();
        eventReview.setTitle(e.getTitle());
        getEventReviews().add(eventReview);
        return eventReview;
    }

    public void loadReviews() {
        if (!new File(REVIEW_FILE_NAME).exists()) {
            try {
                Files.createDirectories(Paths.get(REVIEW_FILE_NAME).getParent());
                Files.createFile(Paths.get(REVIEW_FILE_NAME));
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveReviews();
        }
        setEventReviews(read(REVIEW_FILE_NAME, new TypeToken<HashSet<EventReview>>() {
        }.getType()));
    }

    public void saveReviews() {
        write(getEventReviews(), REVIEW_FILE_NAME);
    }

    public HashSet<EventReview> getEventReviews() {
        return eventReviews;
    }

    public void setEventReviews(HashSet<EventReview> eventReviews) {
        this.eventReviews = eventReviews;
    }
}
