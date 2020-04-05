package project;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi.Attribute;
import com.diogonunes.jcdp.color.api.Ansi.FColor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
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
    private int count = 0;
    private int input = 0;
    private LinkedList<Event> tempEvents = new LinkedList<>();
    private ColoredPrinter cpWarn = new ColoredPrinter.Builder(1, false).foreground(FColor.YELLOW)
        .build();
    private ColoredPrinter cpInfo = new ColoredPrinter.Builder(1, false)
        .foreground(FColor.CYAN)
        .build();
    private ColoredPrinter cpMenu = new ColoredPrinter.Builder(1, false)
        .foreground(FColor.GREEN)
        .build();
    private ColoredPrinter cpError = new ColoredPrinter.Builder(1, false)
        .foreground(FColor.RED)
        .build();

    public Driver() {
        currentUser = new User("default");
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        venues = new VenueDatabase(reviews);
    }

    public static void main(String[] args) throws IOException {
        boolean quit = false;
        Driver d = new Driver();
        d.currentUser = new User("default");
        System.out.println("Welcome to Paradise Showtimes!");
        while (!quit) {
            d.options();
            int option = Integer.parseInt(d.bufferedReader.readLine());
            quit = d.command(option);
        }
    }

    /**
     * Menu for the view seats and purchase ticket function
     *
     * @throws IOException
     */
    public void selectAndView() throws IOException {
        cpInfo.println("Do you want to purchase ticket(1) or view seats(2)? Enter a number");
        getInput();

        select();

        if (input == 1) {
            purchaseTicket();
        } else {
            viewSeating();
        }
    }

    /**
     * Option menu for the user to select
     */
    public void options() {
        ColoredPrinter cpUnderline = new ColoredPrinter.Builder(1, false)
            .attribute(Attribute.UNDERLINE)
            .build();
        ;
        System.out.println();
        if (currentUser instanceof RegisteredUser) {
            cpUnderline.print("User: " + currentUser.getUsername());
            System.out.print("\n0. Quit" + "\n1. Search"
                + "\n2. View All Events" + "\n3. View Seats/Purchase Ticket"
                + "\n4. Write Review" + "\n5. Sign Out\n");
        } else if (currentUser instanceof Admin) {
            cpUnderline.print("Admin: " + currentUser.getUsername());
            System.out.print("\n0. Quit" + "\n1. Search"
                + "\n2. View All Events" + "\n3. View Seats/Purchase Ticket" + "\n4. Add Admin"
                + "\n5. Add Data" + "\n6. Remove Data" + "\n7. Sign Out\n");
        } else {
            cpUnderline.print("Menu:");
            System.out.println(" \n0. Quit" + "\n1. Search"
                + "\n2. View All Events" + "\n3. View Seats/Purchase Ticket" + "\n4. Register"
                + "\n5. Sign In");
        }
    }

    /**
     * Command Menu that corresponds to the function selected
     *
     * @param option
     * @return
     * @throws IOException
     */
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
                    setSelectedEvent();
                    cpInfo.println("Enter short review: ");
                    String review = bufferedReader.readLine();
                    cpInfo.println("Enter whole number rating from 1-5: ");
                    int rating = Integer.parseInt(bufferedReader.readLine());
                    writeReview(rating, review, selectedEvent.getTitle());
                } else if (option == 5) {
                    signOut();
                } else {
                    return true;
                }
            } else if (currentUser instanceof Admin) {
                if (option == 4) {
                    addAdmin();
                } else if (option == 5) {
                    add();
                } else if (option == 6) {
                    remove();
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

    /**
     * selects the current data to be used
     *
     * @throws IOException
     */
    public void select() throws IOException {
        count = 1;
        setSelectedVenue();

        count = 1;
        for (Theater theater : selectedVenue.getTheaters()) {
            for (Event event : theater.getEvents()) {
                System.out.println(
                    count + ". " + event.getTitle() + " - " + event.getDate() + " - Room: "
                        + theater.getRoom());
                count++;
            }
        }

        getInput();

        count = 1;
        outerLoop:
        for (Theater theater : selectedVenue.getTheaters()) {
            for (Event event : theater.getEvents()) {
                if (count == input) {
                    selectedTheater = theater;
                    selectedEvent = event;
                    break outerLoop;
                }
                count++;
            }
        }
    }

    /**
     * Selects the current venue to be used
     *
     * @throws IOException
     */
    public void setSelectedVenue() throws IOException {
        if (venues.getVenues().isEmpty()) {
            cpWarn.println("You need to Create a Venue First!");
            return;
        }
        cpInfo.println("Please select a venue:");
        count = 1;
        for (Venue venue : venues.getVenues()) {
            System.out.println(count + ". " + venue.getName() + " - " + venue.getLocation());
            count++;
        }
        getInput();
        selectedVenue = venues.getVenues().get(input - 1);
    }

    /**
     * Selects the current Theater to be used
     *
     * @throws IOException
     */
    public void setSelectedTheater() throws IOException {
        if (selectedVenue.getTheaters().isEmpty()) {
            cpWarn.println("You need to Create a Theater First!");
            return;
        }
        cpInfo.println("Please select a theater:");
        count = 1;
        for (Theater theater : selectedVenue.getTheaters()) {
            System.out
                .println(count + ". Room #" + theater.getRoom() + " - Handicap: " + theater
                    .isHandicap());
            count++;
        }
        getInput();

        count = 1;
        for (Theater theater : selectedVenue.getTheaters()) {
            if (count == input) {
                selectedTheater = theater;
                break;
            }
            count++;
        }
    }

    /**
     * Selects the current Theater to be used
     *
     * @throws IOException
     */
    public void setSelectedEvent() throws IOException {
        setSelectedVenue();
        cpInfo.println("Please select an event:");
        for (Theater theater : selectedVenue.getTheaters()) {
            for (Event event : theater.getEvents()) {
                printOnce(event);
            }
        }

        count = 1;
        for (Event event : tempEvents) {
            System.out
                .println(count + ". " + event.getTitle());
            count++;
        }
        getInput();

        count = 1;
        for (Event event : tempEvents) {
            if (count == input) {
                selectedEvent = event;
                break;
            }
            count++;
        }
    }

    /**
     * Avoids the duplication of events for writing reviews
     *
     * @param event
     */
    public void printOnce(Event event) {
        for (Event temp : tempEvents) {
            if (temp.getTitle().equals(event.getTitle())) {
                return;
            }
        }
        tempEvents.add(event);
    }

    /**
     * Prompts user for login credentials and verifies it with the JSON to log you in
     *
     * @throws IOException
     */
    public void signIn() throws IOException {
        HashSet<RegisteredUser> userList = users.getUsers();
        HashSet<Admin> adminList = users.getAdmins();

        cpInfo.println("Enter username: ");
        String username = bufferedReader.readLine().trim();
        cpInfo.println("Enter password: ");
        String password = bufferedReader.readLine().trim();

        if (users.login(username, password, adminList, Admin.class)) {
            currentUser = users.getUser(username, adminList);
            cpInfo.println("Login successful as Admin");
        } else if (users.login(username, password, userList, RegisteredUser.class)) {
            currentUser = users.getUser(username, userList);
            cpInfo.println("Login successful as User");
        } else {
            cpError.println("Invalid username/password");
        }
    }

    /**
     * Logs you out by setting you as default user
     */
    public void signOut() {
        currentUser = new User("default");
    }

    /**
     * Registers you and submits the data into the JSON and logs you in while at it
     *
     * @throws IOException
     */
    public void register() throws IOException {
        cpInfo.println("To register, enter username: ");
        String username = bufferedReader.readLine().trim();

        if (users.isUser(username, users.getUsers())) {
            cpError.println("Username is already taken");
            return;
        }

        cpInfo.println("Now set a password: ");
        String password = bufferedReader.readLine().trim();

        cpInfo.println("Enter the number corresponding to your status: "
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
        users.add(user);
        currentUser = user;

        cpInfo.println("Great! You're registered, and you are also signed in.");
    }

    /**
     * Writes a review and sends it to be submitted to the JSON file
     *
     * @param rating
     * @param review
     * @param title
     */
    public void writeReview(int rating, String review, String title) {
        reviews.writeReview(venues.getEvent(title), review, rating);
    }

    /**
     * Allows the creation of another admin only if you are an admin. Submits data into JSON file.
     *
     * @throws IOException
     */
    public void addAdmin() throws IOException {
        cpInfo.println("Enter username for admin: ");
        String username = bufferedReader.readLine();

        if (users.isUser(username, users.getAdmins())) {
            cpError.println("Username is already taken");
            return;
        }

        cpInfo.println("Now set a password: ");
        String password = bufferedReader.readLine();

        Admin admin = new Admin(username, password);
        users.add(admin);
        cpInfo.println("Admin created");
    }

    /**
     * Displays a menu to ask what type of information you want to add
     *
     * @throws IOException
     */
    public void add() throws IOException {
        cpWarn.println(
            "WARNING: If you are adding a Theater or Event to a non existing Venue, you must first CREATE the Venue and or Theater before creating that Event");
        cpInfo
            .println("Do you want to add a(n): Venue(1), Theater(2), Event(3)? Enter a Number");
        getInput();
        if (input == 1) {
            addVenue();
        } else if (input == 2) {
            addTheater();
        } else if (input == 3) {
            addEvent();
        } else {
            cpError.println("Not an option!");
            return;
        }
    }


    /**
     * Adds a new Venue into the list
     *
     * @throws IOException
     */
    public void addVenue() throws IOException {
        cpInfo.println("Enter location of venue (Address, City, State)");
        String venueLoc = bufferedReader.readLine();
        cpInfo.println("Enter name of venue");
        String venueName = bufferedReader.readLine();
        selectedVenue = new Venue(venueLoc, venueName);
        venues.add(selectedVenue);
        venues.saveVenues();
    }

    /**
     * Adds a new Theater into the list
     *
     * @throws IOException
     */
    public void addTheater() throws IOException {
        setSelectedVenue();

        cpInfo.println("Enter Theater room number");
        int tRoom = Integer.parseInt(bufferedReader.readLine());
        cpInfo.println("Is this theater handicap supported? Enter 1(Yes) or 0(No)");
        getInput();
        boolean tHandicap = (input == 1) ? true : (input == 0 ? false : null);

        selectedTheater = new Theater(tRoom, tHandicap);
        selectedVenue.addTheater(selectedTheater);
        venues.saveVenues();
    }

    /**
     * Adds a new event into the list
     *
     * @throws IOException
     */
    public void addEvent() throws IOException {
        setSelectedVenue();
        setSelectedTheater();

        cpInfo.println("Enter Date of event (ex: January 20, 2020 10:00 AM)");
        String eDate = bufferedReader.readLine();
        cpInfo.println("Enter Title of event");
        String eTitle = bufferedReader.readLine();
        cpInfo
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
        cpInfo.println("Enter Description of event");
        String eDesc = bufferedReader.readLine();
        cpInfo.println("Is this event explicit? Enter 1(Yes) or 0(No)");
        boolean eExplicit = Integer.parseInt(bufferedReader.readLine()) == 1;
        cpInfo.println(
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
        cpInfo.println("Enter price of ticket $(##.##) JUST THE NUMBER");
        double ePrice = Double.parseDouble(bufferedReader.readLine());

        Event event = new Event(eDate, eTitle, eGenre, eDesc, eExplicit, eType, ePrice);

        selectedTheater.addEvent(event);
        venues.saveVenues();
    }

    /**
     * Inserts your input into a variable to be used
     *
     * @throws IOException
     */
    public void getInput() throws IOException {
        input = Integer.parseInt(bufferedReader.readLine());
    }

    /**
     * Displays a menu to ask what type of information you want to remove
     *
     * @throws IOException
     */
    private void remove() throws IOException {
        cpInfo
            .println("Do you want to remove a(n): Venue(1), Theater(2), Event(3)? Enter a Number");
        getInput();
        if (input == 1) {
            removeVenue();
        } else if (input == 2) {
            removeTheater();
        } else if (input == 3) {
            removeEvent();
        } else {
            cpError.println("Not an option!");
            return;
        }
    }

    /**
     * Removes a Venue from the list
     *
     * @throws IOException
     */
    public void removeVenue() throws IOException {
        cpInfo.println("Choose which to delete:");
        setSelectedVenue();
        if (venues.remove(selectedVenue)) {
            cpInfo.println("Venue Removed Successfully");
        } else {
            cpError.println("Venue Remove Failed");
        }
        venues.saveVenues();
    }

    /**
     * Removes a theater from the list
     */
    public void removeTheater() throws IOException {
        cpInfo.println("Choose which to delete:");
        setSelectedVenue();
        setSelectedTheater();
        if (selectedVenue.getTheaters().remove(selectedTheater)) {
            cpInfo.println("Theater Removed Successfully");
        } else {
            cpError.println("Theater Remove Failed");
        }
    }

    //TODO

    /**
     * Removes an event from the list
     */
    public void removeEvent() throws IOException {
        System.out.println("Choose which to delete:");
        select();
        if (selectedTheater.getEvents().remove(selectedEvent)) {
            cpInfo.println("Event Removed Successfully");
        } else {
            cpError.println("Event Remove Failed");
        }
        venues.saveVenues();

    }

    /**
     * Searches and filters accordingly to user's choice
     *
     * @throws IOException
     */
    public void search() throws IOException {
        cpInfo
            .println(
                "Enter a number to search for events: " + "\n1.Search title" + "\n2.Filter by Genre"
                    + "\n3.Filter by Venue" + "\n4.Filter by Rating" + "\n5.Filter by Event Type");
        int option = Integer.parseInt(bufferedReader.readLine());
        switch (option) {
            case 1:
                cpInfo.println("Enter title: ");
                String title = bufferedReader.readLine();
                venues.search(title);
                break;
            case 2:
                Genre genre = chooseGenre();
                venues.filterByGenre(genre);
                break;
            case 3:
                cpInfo.println("Enter name of venue: ");
                String name = bufferedReader.readLine();
                venues.filterByVenue(name);
                break;
            case 4:
                cpInfo.println("Enter a whole number rating from 1-5: ");
                int rating = Integer.parseInt(bufferedReader.readLine());
                venues.filterByRating(rating);
                break;
            case 5:
                Type type = chooseType();
                venues.filterByEventType(type);
                break;

        }
    }

    /**
     * Chooses event type
     *
     * @return Type
     * @throws IOException
     */
    private Type chooseType() throws IOException {
        cpInfo.println(
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

    /**
     * Chooses Event Genre
     *
     * @return Genre
     * @throws IOException
     */
    private Genre chooseGenre() throws IOException {
        cpInfo
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

    /**
     * prints all the events being listed
     */
    public void viewAllEvents() {
        venues.printAll();
    }

    /**
     * prints out a mapping of the seats in theater
     */
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

    /**
     * Allows you to choose a seat and purchases the seat
     *
     * @throws IOException
     */
    public void purchaseTicket() throws IOException {
        viewSeating();

        cpInfo
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

        cpInfo.println("Choose the row of the seat (0-9)");
        int col = Integer.parseInt(bufferedReader.readLine());

        if (selectedEvent.getSeats()[row][col].equals("X")) {
            System.out.println("SEAT IS TAKEN!\n");
            return;
        }

        cpInfo.println("Are you sure you want to buy a ticket for (Y/N): ");
        if (currentUser instanceof RegisteredUser) {
            venues.printTicket(selectedVenue, selectedTheater, selectedEvent,
                ((RegisteredUser) currentUser).getStatus());
        } else {
            venues.printTicket(selectedVenue, selectedTheater, selectedEvent);
        }

        String ans = bufferedReader.readLine();
        if (ans.equals("N")) {
            return;
        }

        selectedEvent.setSeats(row, col);
        venues.saveVenues();
        orderTicket(line, col);
    }

    /**
     * Allows user to order the ticket and generate either a physical or digital receipt
     *
     * @param line
     * @param col
     * @throws IOException
     */
    public void orderTicket(char line, int col) throws IOException {
        cpInfo.println(
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

    /**
     * Generates a physical ticket and prints it into a txt file
     *
     * @param venue
     * @param theater
     * @param event
     * @param line
     * @param col
     */
    public void generatePhysicalTicket(Venue venue, Theater theater, Event event, char line,
        int col) {
        try {
            PrintWriter file = new PrintWriter(event.getTitle() + "_ticket_receipt.txt", "UTF-8");
            if (currentUser instanceof RegisteredUser) {
                file.print(venues.printReceipt(venue, theater, event, line, col,
                    ((RegisteredUser) currentUser).getStatus()));
            } else {
                file.print(venues.printReceipt(venue, theater, event, line, col));
            }

            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a digital receipt and stores it into the User's JSON file
     *
     * @param venue
     * @param theater
     * @param event
     * @param line
     * @param col
     */
    public void generateOnlineTicket(Venue venue, Theater theater, Event event, char line,
        int col) {
        cpInfo.println(
            "Here is your online receipt. Reminder, receipts save automatically in history for registered users.");
        if (currentUser instanceof RegisteredUser) {
            System.out.println(venues.printReceipt(venue, theater, event, line, col,
                ((RegisteredUser) currentUser).getStatus()));
            if (currentUser.getClass().equals(RegisteredUser.class)) {
                ((RegisteredUser) currentUser)
                    .addTicket(venues.printReceipt(venue, theater, event, line, col,
                        ((RegisteredUser) currentUser).getStatus()));
                users.saveUsers();
            }
        } else {
            System.out.println(venues.printReceipt(venue, theater, event, line, col));
            if (currentUser.getClass().equals(RegisteredUser.class)) {
                ((RegisteredUser) currentUser)
                    .addTicket(venues.printReceipt(venue, theater, event, line, col));
                users.saveUsers();
            }
        }
    }
}