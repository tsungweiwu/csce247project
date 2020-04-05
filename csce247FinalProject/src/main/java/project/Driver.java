package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import project.databases.ReviewDatabase;
import project.databases.UserDatabase;
import project.databases.VenueDatabase;
import project.objects.Admin;
import project.objects.Event;
import project.objects.Genre;
import project.objects.RegisteredUser;
import project.objects.Status;
import project.objects.Theater;
import project.objects.Type;
import project.objects.User;
import project.objects.Venue;

public class Driver {

    private VenueDatabase venues;
    private UserDatabase users = new UserDatabase();
    private ReviewDatabase reviews = new ReviewDatabase();
    private User currentUser;
    private Venue selectedVenue = null;
    private Theater selectedTheater = null;
    private Event selectedEvent = null;
    private BufferedReader bufferedReader;

    public Driver() {
        currentUser = new User("default");
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        venues = new VenueDatabase(reviews);
    }

    public static void main(String[] args) throws IOException {
        boolean quit = false;
        Driver d = new Driver();
        d.currentUser = new User("default");
        System.out.println("Welcome to Paradise Showtimes! \n");
        while (!quit) {
            d.options();
            int option = Integer.parseInt(d.bufferedReader.readLine());
            quit = d.command(option);
        }
    }

    public void selectAndView() throws IOException {
        System.out.println("Do you want to purchase ticket(1) or view seats(2)? Enter a number");
        int input = Integer.parseInt(bufferedReader.readLine());

        System.out.println("Please select a venue:");
        int count = 1;
        for (Venue venue : venues.getVenues()) {
            System.out.println(count + ". " + venue.getName() + " - " + venue.getLocation());
            count++;
        }
        int option = Integer.parseInt(bufferedReader.readLine());
        selectedVenue = venues.getVenues().get(option - 1);
        printAllEventsFromVenue(selectedVenue, input);
    }

    private void printAllEventsFromVenue(Venue selectedVenue, int input) throws IOException {
        int count = 1;
        for (Theater theater : selectedVenue.getTheaters()) {
            for (Event event : theater.getEvents()) {
                System.out.println(
                    count + ". " + event.getTitle() + " - " + event.getDate() + " - Room: "
                        + theater.getRoom());
                count++;
            }
        }
        int option = Integer.parseInt(bufferedReader.readLine());
        count = 1;
        outerLoop:
        for (Theater theater : selectedVenue.getTheaters()) {
            for (Event event : theater.getEvents()) {
                if (count == option) {
                    selectedTheater = theater;
                    selectedEvent = event;
                    break outerLoop;
                }
                count++;
            }
        }

        if (input == 1) {
            purchaseTicket();
        } else {
            viewSeating();
        }
    }

    public void options() {
        if (currentUser instanceof RegisteredUser) {
            System.out.println(
                "\nUser: " + currentUser.getUsername() + "\n0. Quit" + "\n1. Search"
                    + "\n2. View All Events" + "\n3. View Seats/Purchase Ticket"
                    + "\n4. Write Review" + "\n5. Sign Out");
        } else if (currentUser instanceof Admin) {
            System.out.println(
                "\nAdmin: " + currentUser.getUsername() + "\n0. Quit" + "\n1. Search"
                    + "\n2. View All Events" + "\n3. View Seats/Purchase Ticket" + "\n4. Add Admin"
                    + "\n5. Add Event" + "\n6. Remove Event" + "\n7. Sign Out");
        } else {
            System.out.println(
                "\nMenu:" + " \n0. Quit" + "\n1. Search"
                    + "\n2. View All Events" + "\n3. View Seats/Purchase Ticket" + "\n4. Register"
                    + "\n5. Sign In");
        }
    }

    public boolean command(int option) throws IOException {
        if (option == 0) {
            return true;
        } else if (option == 1) {
            search();
        } else if (option == 2) {
            viewAllEvents();
        } else if (option == 3) {
            selectAndView();
        } else {
            if (currentUser instanceof RegisteredUser) {
                if (option == 4) {
                    System.out.println("Enter title of event: ");
                    String title = bufferedReader.readLine();
                    System.out.println("Enter short review: ");
                    String review = bufferedReader.readLine();
                    System.out.println("Enter whole number rating from 1-5: ");
                    int rating = Integer.parseInt(bufferedReader.readLine());
                    writeReview(rating, review, title);
                } else if (option == 5) {
                    signOut();
                } else {
                    return true;
                }
            } else if (currentUser instanceof Admin) {
                if (option == 4) {
                    addAdmin();
                } else if (option == 5) {
                    addEvent();
                } else if (option == 6) {
                    System.out.println("Enter the title of event to remove");
                    String title = bufferedReader.readLine();
                    removeEvent(title);
                } else if (option == 7) {
                    signOut();
                } else {
                    return true;
                }
            } else {
                if (option == 4) {
                    register();
                } else if (option == 5) {
                    signIn();
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public void signOut() {
        currentUser = new User("default");
    }

    public void signIn() throws IOException {
        HashSet<RegisteredUser> userList = users.getUsers();
        HashSet<Admin> adminList = users.getAdmins();

        System.out.println("Enter username: ");
        String username = bufferedReader.readLine();
        System.out.println("Enter password: ");
        String password = bufferedReader.readLine();

        if (users.login(username, password, adminList, Admin.class)) {
            currentUser = users.getUser(username, adminList);
            System.out.println("Login successful as Admin");
        } else if (users.login(username, password, userList, RegisteredUser.class)) {
            currentUser = users.getUser(username, userList);
            System.out.println("Login successful as User");
        } else {
            System.out.println("Invalid username/password");
        }
    }

    public void register() throws IOException {
        System.out.println("To register, enter username: ");
        String username = bufferedReader.readLine();

        if (users.isUser(username, users.getUsers())) {
            System.out.println("Username is already taken");
            return;
        }

        System.out.println("Now set a password: ");
        String password = bufferedReader.readLine();

        System.out.println("Enter the number corresponding to your status: "
            + "\n1. Military\n2. Employee\n3. Teacher\n4. Student\n5. Senior\n6. None");
        int stat = Integer.parseInt(bufferedReader.readLine());
        Status status = null;
        switch (stat) {
            case 1:
                status = Status.MILITARY;
                break;
            case 2:
                status = Status.EMPLOYEE;
                break;
            case 3:
                status = Status.TEACHER;
                break;
            case 4:
                status = Status.STUDENT;
                break;
            case 5:
                status = Status.SENIOR;
                break;
            default:
                status = Status.NONE;
                break;
        }
        RegisteredUser user = new RegisteredUser(username, password, status);
        users.addUser(username, password, status);
        currentUser = user;
        System.out.println("Great! You're registered, and you are also signed in.");
    }

    public void addAdmin() throws IOException {
        System.out.println("Enter username for admin: ");
        String username = bufferedReader.readLine();

        if (users.isUser(username, users.getAdmins())) {
            System.out.println("Username is already taken");
            return;
        }

        System.out.println("Now set a password: ");
        String password = bufferedReader.readLine();

        Admin admin = new Admin(username, password);
        users.add(admin);
        System.out.println("Admin created");
    }

    public void addEvent() throws IOException {
        System.out.println("Enter location of venue (Address, City, State)");
        String venueLoc = bufferedReader.readLine();
        System.out.println("Enter name of venue");
        String venueName = bufferedReader.readLine();

        System.out.println("Enter Theater room number");
        int tRoom = Integer.parseInt(bufferedReader.readLine());
        System.out.println("Is this theater handicap supported? Enter 1(Yes) or 0(No)");
        int input = Integer.parseInt(bufferedReader.readLine());
        boolean tHandicap = (input == 1) ? true : (input == 0 ? false : null);

        System.out.println("Enter Date of event (MMMM DD, YYYY 0:00 AM/PM)");
        String eDate = bufferedReader.readLine();
        System.out.println("Enter Title of event");
        String eTitle = bufferedReader.readLine();
        System.out
            .println(
                "Choose a genre for the event: " + "\n1. Comedy" + "\n2. Horror" + "\n3. Thriller"
                    + "\n4. Romance" + "\n5. Indie" + "\n6. Family" + "\n7. Action" + "\n8. Rap"
                    + "\n9. Rock" + "\n10. Pop"
                    + "\n11. Country" + "\n12. Latin" + "\n13. EDM" + "\n14. RNB" + "\n15. Classic"
                    + "\n16. Opera");
        Genre eGenre;
        switch (Integer.parseInt(bufferedReader.readLine())) {
            case 1:
                eGenre = Genre.COMEDY;
                break;
            case 2:
                eGenre = Genre.HORROR;
                break;
            case 3:
                eGenre = Genre.THRILLER;
                break;
            case 4:
                eGenre = Genre.ROMANCE;
                break;
            case 5:
                eGenre = Genre.INDIE;
                break;
            case 6:
                eGenre = Genre.FAMILY;
                break;
            case 7:
                eGenre = Genre.ACTION;
                break;
            case 8:
                eGenre = Genre.RAP;
                break;
            case 9:
                eGenre = Genre.ROCK;
                break;
            case 10:
                eGenre = Genre.POP;
                break;
            case 11:
                eGenre = Genre.COUNTRY;
                break;
            case 12:
                eGenre = Genre.LATIN;
                break;
            case 13:
                eGenre = Genre.EDM;
                break;
            case 14:
                eGenre = Genre.RNB;
                break;
            case 15:
                eGenre = Genre.CLASSIC;
                break;
            case 16:
                eGenre = Genre.OPERA;
                break;
            default:
                eGenre = Genre.NONE;
                break;
        }
        System.out.println("Enter Description of event");
        String eDesc = bufferedReader.readLine();
        System.out.println("Is this event explicit? Enter 1(Yes) or 0(No)");
        input = Integer.parseInt(bufferedReader.readLine());
        boolean eExplicit = (input == 1) ? true : (input == 0 ? false : null);
        System.out.println(
            "Choose an event type: " + "\n1. Movie" + "\n2. Concert" + "\n3. Play");
        Type eType;
        switch (Integer.parseInt(bufferedReader.readLine())) {
            case 1:
                eType = Type.MOVIE;
                break;
            case 2:
                eType = Type.CONCERT;
                break;
            case 3:
                eType = Type.PLAY;
                break;
            default:
                eType = Type.NONE;
                break;
        }
        System.out.println("Enter price of ticket $(##.##) JUST THE NUMBER");
        double ePrice = Double.parseDouble(bufferedReader.readLine());

        Venue venue = new Venue(venueLoc, venueName);
        Theater theater = new Theater(tRoom, tHandicap);
        Event event = new Event(eDate, eTitle, eGenre, eDesc, eExplicit, eType, ePrice);

        theater.addEvent(event);
        venue.addTheater(theater);
        venues.add(venue);
    }

    public void removeEvent(String title) {
        System.out.println(
            venues.removeByTitle(title) ? "Event Removed Successfully" : "Event Remove Failed");
    }

    public void search() throws IOException {
        System.out
            .println(
                "Enter a number to search for events: " + "\n1.Search title" + "\n2.Filter by Genre"
                    + "\n3.Filter by Venue" + "\n4.Filter by Rating" + "\n5.Filter by Event Type");
        int option = Integer.parseInt(bufferedReader.readLine());
        switch (option) {
            case 1:
                System.out.println("Enter title: ");
                String title = bufferedReader.readLine();
                venues.search(title);
                break;
            case 2:
                Genre genre = chooseGenre();
                venues.filterByGenre(genre);
                break;
            case 3:
                System.out.println("Enter name of venue: ");
                String name = bufferedReader.readLine();
                venues.filterByVenue(name);
                break;
            case 4:
                System.out.println("Enter a whole number rating from 1-5: ");
                int rating = Integer.parseInt(bufferedReader.readLine());
                venues.filterByRating(rating);
                break;
            case 5:
                Type type = chooseType();
                venues.filterByEventType(type);
                break;

        }
    }

    private Type chooseType() throws IOException {
        System.out.println(
            "Choose an event type to filter by: " + "\n1. Movie" + "\n2. Concert" + "\n3. Play");
        int option = Integer.parseInt(bufferedReader.readLine());
        switch (option) {
            case 1:
                return Type.MOVIE;
            case 2:
                return Type.CONCERT;
            case 3:
                return Type.PLAY;
            default:
                return Type.NONE;
        }
    }

    private Genre chooseGenre() throws IOException {
        System.out
            .println(
                "Choose a genre to filter by: " + "\n1. Comedy" + "\n2. Horror" + "\n3. Thriller"
                    + "\n4. Romance" + "\n5. Indie" + "\n6. Family" + "\n7. Action" + "\n8. Rap"
                    + "\n9. Rock" + "\n10. Pop"
                    + "\n11. Country" + "\n12. Latin" + "\n13. EDM" + "\n14. RNB" + "\n15. Classic"
                    + "\n16. Opera");
        int option = Integer.parseInt(bufferedReader.readLine());
        switch (option) {
            case 1:
                return Genre.COMEDY;
            case 2:
                return Genre.HORROR;
            case 3:
                return Genre.THRILLER;
            case 4:
                return Genre.ROMANCE;
            case 5:
                return Genre.INDIE;
            case 6:
                return Genre.FAMILY;
            case 7:
                return Genre.ACTION;
            case 8:
                return Genre.RAP;
            case 9:
                return Genre.ROCK;
            case 10:
                return Genre.POP;
            case 11:
                return Genre.COUNTRY;
            case 12:
                return Genre.LATIN;
            case 13:
                return Genre.EDM;
            case 14:
                return Genre.RNB;
            case 15:
                return Genre.CLASSIC;
            case 16:
                return Genre.OPERA;
            default:
                return Genre.NONE;
        }

    }

    public void viewAllEvents() {
        venues.printAll();
    }

    public void viewSeating() {
        String seats[][] = selectedEvent.getSeats();

        char col = '@';
        System.out.println(col + "\t0\t1\t2\t3\t4\t5\t6\t7\t8\t9");
        for (int i = 0; i < seats.length; i++) {
            col++;
            System.out.print(col + "\t");
            for (int j = 0; j < seats[i].length; j++) {
                System.out.print(seats[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public void purchaseTicket() throws IOException {
        viewSeating();

        System.out
            .println("X means occupied, O means available\nChoose the column of the seat (A-J)");
        char line = bufferedReader.readLine().charAt(0);

        int row;
        if (line == 'A') {
            row = 0;
        } else if (line == 'B') {
            row = 1;
        } else if (line == 'C') {
            row = 2;
        } else if (line == 'D') {
            row = 3;
        } else if (line == 'E') {
            row = 4;
        } else if (line == 'F') {
            row = 5;
        } else if (line == 'G') {
            row = 6;
        } else if (line == 'H') {
            row = 7;
        } else if (line == 'I') {
            row = 8;
        } else if (line == 'J') {
            row = 9;
        } else {
            row = 0;
        }

        System.out.println("Choose the row of the seat (0-9)");
        int col = Integer.parseInt(bufferedReader.readLine());

        if (selectedEvent.getSeats()[row][col].equals("X")) {
            System.out.println("BISH SEAT IS TAKEN\n");
            return;
        }

        System.out.println("Are you sure you want to buy a ticket for (Y/N): ");
        venues.printTicketFormat(selectedEvent);
        String ans = bufferedReader.readLine();
        if (ans.equals("N")) {
            return;
        }

        selectedEvent.setSeats(row, col);
        venues.saveVenues();
        orderTicket(line, col);
    }

    public void orderTicket(char line, int col) throws IOException {
        System.out.println(
            "Great! Ticket purchased. Would you like: " + "\n1. Physical ticket"
                + "\n2. Online ticket"
                + "\n3. Both");
        int option = Integer.parseInt(bufferedReader.readLine());
        switch (option) {
            case 1:
                generatePhysicalTicket(selectedVenue, selectedTheater, selectedEvent, line, col);
                break;
            case 2:
                generateOnlineTicket(selectedVenue, selectedTheater, selectedEvent, line, col);
                break;
            case 3:
                generatePhysicalTicket(selectedVenue, selectedTheater, selectedEvent, line, col);
                generateOnlineTicket(selectedVenue, selectedTheater, selectedEvent, line, col);
                break;
        }
    }

    public void generatePhysicalTicket(Venue venue, Theater theater, Event event, char line,
        int col) {
        try {
            PrintWriter file = new PrintWriter(event.getTitle() + "_ticket_receipt.txt", "UTF-8");
            file.print(venues.printReceipt(venue, theater, event, line, col));
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateOnlineTicket(Venue venue, Theater theater, Event event, char line,
        int col) {
        System.out.println(
            "Here is your online receipt. Reminder, receipts save automatically in history for registered users.");
        System.out.println(venues.printReceipt(venue, theater, event, line, col));
        if (currentUser.getClass().equals(RegisteredUser.class)) {
            ((RegisteredUser) currentUser)
                .addTicket(venues.printReceipt(venue, theater, event, line, col));
            users.saveUsers();
        }

    }

    public void writeReview(int rating, String review, String title) {
        reviews.writeReview(venues.getEvent(title), review, rating);
    }
}
