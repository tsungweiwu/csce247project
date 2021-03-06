package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private LinkedList<Event> tempEvents = new LinkedList<>();

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
        System.out.println("Do you want to purchase ticket(1) or view seats(2)? Enter a number");
        int input = Integer.parseInt(bufferedReader.readLine());

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
        if (currentUser instanceof RegisteredUser) {
            System.out.println(
                "User: " + currentUser.getUsername() + "\n0. Quit" + "\n1. Search"
                    + "\n2. View All Events" + "\n3. View Seats/Purchase Ticket"
                    + "\n4. Write Review" + "\n5. Sign Out");
        } else if (currentUser instanceof Admin) {
            System.out.println(
                "Admin: " + currentUser.getUsername() + "\n0. Quit" + "\n1. Search"
                    + "\n2. View All Events" + "\n3. View Seats/Purchase Ticket" + "\n4. Add Admin"
                    + "\n5. Add Data" + "\n6. Remove Data" + "\n7. Sign Out");
        } else {
            System.out.println(
                "Menu:" + " \n0. Quit" + "\n1. Search"
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
                    System.out.println("Enter short review: ");
                    String review = bufferedReader.readLine();
                    System.out.println("Enter whole number rating from 1-5: ");
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
        int count = 1;
        setSelectedVenue();

        count = 1;
        for (Theater theater : selectedVenue.getTheaters()) {
            for (Event event : theater.getEvents()) {
                System.out.println(
                    count + ". " + event.getTitle() + " - " + event.getDate() + " - Room: "
                        + theater.getRoom() + " - Price: $" + event.getPrice());
                count++;
            }
        }

        int input = Integer.parseInt(bufferedReader.readLine());

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
            System.out.println("You need to Create a Venue First!");
            return;
        }
        System.out.println("Please select a venue:");
        int count = 1;
        for (Venue venue : venues.getVenues()) {
            System.out.println(count + ". " + venue.getName() + " - " + venue.getLocation());
            count++;
        }
        int input = Integer.parseInt(bufferedReader.readLine());
        selectedVenue = venues.getVenues().get(input - 1);
    }

    /**
     * Selects the current Theater to be used
     *
     * @throws IOException
     */
    public void setSelectedTheater() throws IOException {
        if (selectedVenue.getTheaters().isEmpty()) {
            System.out.println("You need to Create a Theater First!");
            return;
        }
        System.out.println("Please select a theater:");
        int count = 1;
        for (Theater theater : selectedVenue.getTheaters()) {
            System.out
                .println(count + ". Room #" + theater.getRoom() + " - Handicap: " + theater
                    .isHandicap());
            count++;
        }
        int input = Integer.parseInt(bufferedReader.readLine());

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
        System.out.println("Please select an event:");
        for (Theater theater : selectedVenue.getTheaters()) {
            for (Event event : theater.getEvents()) {
                printOnce(event);
            }
        }

        int count = 1;
        for (Event event : tempEvents) {
            System.out
                .println(count + ". " + event.getTitle());
            count++;
        }
        int input = Integer.parseInt(bufferedReader.readLine());

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

        System.out.println("Enter username: ");
        String username = bufferedReader.readLine();
        System.out.println("Enter password: ");
        String password = bufferedReader.readLine();

        if (users.login(username, password, adminList, Admin.class)) {
            currentUser = users.getUser(username, adminList);
            System.out.println("Login successful as Admin\n");
        } else if (users.login(username, password, userList, RegisteredUser.class)) {
            currentUser = users.getUser(username, userList);
            System.out.println("Login successful as User\n");
        } else {
            System.out.println("Invalid username/password");
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

        Status status = Status.values()[Integer.parseInt(bufferedReader.readLine()) - 1];

        RegisteredUser user = new RegisteredUser(username, password, status);
        users.add(user);
        currentUser = user;
        System.out.println("Great! You're registered, and you are also signed in.");
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

    /**
     * Displays a menu to ask what type of information you want to add
     *
     * @throws IOException
     */
    public void add() throws IOException {
        System.out.println(
            "WARNING: If you are adding a Theater or Event to a non existing Venue, you must first CREATE the Venue and or Theater before creating that Event"
        );
        System.out
            .println("Do you want to add a(n): Venue(1), Theater(2), Event(3)? Enter a Number");
        int input = Integer.parseInt(bufferedReader.readLine());

        if (input == 1) {
            addVenue();
        } else if (input == 2) {
            addTheater();
        } else if (input == 3) {
            addEvent();
        } else {
            System.out.println("Not an option!");
            return;
        }
    }


    /**
     * Adds a new Venue into the list
     *
     * @throws IOException
     */
    public void addVenue() throws IOException {
        System.out.println("Enter location of venue (Address, City, State)");
        String venueLoc = bufferedReader.readLine();
        System.out.println("Enter name of venue");
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

        System.out.println("Enter Theater room number");
        int tRoom = Integer.parseInt(bufferedReader.readLine());
        System.out.println("Is this theater handicap supported? Enter 1(Yes) or 0(No)");
        int input = Integer.parseInt(bufferedReader.readLine());
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

        Genre eGenre = Genre.values()[Integer.parseInt(bufferedReader.readLine()) - 1];

        System.out.println("Enter Description of event");
        String eDesc = bufferedReader.readLine();
        System.out.println("Is this event explicit? Enter 1(Yes) or 0(No)");
        boolean eExplicit = Integer.parseInt(bufferedReader.readLine()) == 1;
        System.out.println(
            "Choose an event type: " + "\n1. Movie" + "\n2. Concert" + "\n3. Play");

        Type eType = Type.values()[Integer.parseInt(bufferedReader.readLine())];

        System.out.println("Enter price of ticket $(##.##) JUST THE NUMBER");
        double ePrice = Double.parseDouble(bufferedReader.readLine());

        Event event = new Event(eDate, eTitle, eGenre, eDesc, eExplicit, eType, ePrice);

        selectedTheater.addEvent(event);
        venues.saveVenues();
    }

    /**
     * Displays a menu to ask what type of information you want to remove
     *
     * @throws IOException
     */
    private void remove() throws IOException {
        System.out
            .println("Do you want to remove a(n): Venue(1), Theater(2), Event(3)? Enter a Number");
        int input = Integer.parseInt(bufferedReader.readLine());
        if (input == 1) {
            removeVenue();
        } else if (input == 2) {
            removeTheater();
        } else if (input == 3) {
            removeEvent();
        } else {
            System.out.println("Not an option!");
            return;
        }
    }

    /**
     * Removes a Venue from the list
     *
     * @throws IOException
     */
    public void removeVenue() throws IOException {
        System.out.println("Choose which to delete:");
        setSelectedVenue();
        System.out.println(
            venues.remove(selectedVenue) ? "Venue Removed Successfully"
                : "Venue Remove Failed");
        venues.saveVenues();
    }

    /**
     * Removes a theater from the list
     */
    public void removeTheater() throws IOException {
        System.out.println("Choose which to delete:");
        setSelectedVenue();
        setSelectedTheater();
        System.out.println(
            selectedVenue.getTheaters().remove(selectedTheater) ? "Theater Removed Successfully"
                : "Theater Remove Failed");
        venues.saveVenues();
    }

    /**
     * Removes an event from the list
     */
    public void removeEvent() throws IOException {
        System.out.println("Choose which to delete:");
        select();
        System.out.println(
            selectedTheater.getEvents().remove(selectedEvent) ? "Event Removed Successfully"
                : "Event Remove Failed");
        venues.saveVenues();

    }

    /**
     * Searches and filters accordingly to user's choice
     *
     * @throws IOException
     */
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

    /**
     * Chooses event type
     *
     * @return Type
     * @throws IOException
     */
    private Type chooseType() throws IOException {
        System.out.println(
            "Choose an event type to filter by: " + "\n1. Movie" + "\n2. Concert" + "\n3. Play");

        return Type.values()[Integer.parseInt(bufferedReader.readLine()) - 1];
    }

    /**
     * Chooses Event Genre
     *
     * @return Genre
     * @throws IOException
     */
    private Genre chooseGenre() throws IOException {
        System.out
            .println(
                "Choose a genre to filter by: " + "\n1. Comedy" + "\n2. Horror" + "\n3. Thriller"
                    + "\n4. Romance" + "\n5. Indie" + "\n6. Family" + "\n7. Action" + "\n8. Rap"
                    + "\n9. Rock" + "\n10. Pop"
                    + "\n11. Country" + "\n12. Latin" + "\n13. EDM" + "\n14. RNB" + "\n15. Classic"
                    + "\n16. Opera");

        return Genre.values()[Integer.parseInt(bufferedReader.readLine()) - 1];
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
        System.out.println("Seats Available: " + selectedEvent.getAvailable());
    }

    public int convertToNum(char line) {
        if (line == 'A') {
            return 0;
        } else if (line == 'B') {
            return 1;
        } else if (line == 'C') {
            return 2;
        } else if (line == 'D') {
            return 3;
        } else if (line == 'E') {
            return 4;
        } else if (line == 'F') {
            return 5;
        } else if (line == 'G') {
            return 6;
        } else if (line == 'H') {
            return 7;
        } else if (line == 'I') {
            return 8;
        } else if (line == 'J') {
            return 9;
        }
        return 0;
    }

    /**
     * Checks to see if seat was previously inputted
     *
     * @param row
     * @param col
     * @param x
     * @param y
     * @return
     */
    public boolean isSeatTaken(LinkedList<Integer> row, LinkedList<Integer> col, int x, int y) {
        if (row.isEmpty() || col.isEmpty()) {
            return false;
        }

        for (int i = 0; i < row.size(); i++) {
            if (row.get(i) == x && col.get(i) == y) {
                return true;
            }
        }
        return false;
    }


    /**
     * Allows you to choose a seat and purchases the seat
     *
     * @throws IOException
     */
    public void purchaseTicket() throws IOException {
        viewSeating();

        System.out.println("How many tickets do you want? (No more than what is available!)");
        int quantity = Integer.parseInt(bufferedReader.readLine());

        if (quantity > selectedEvent.getAvailable()) {
            return;
        }

        System.out.println("X means occupied, O means available");

        LinkedList<Integer> row = new LinkedList<>();
        LinkedList<Integer> col = new LinkedList<>();
        char[] rowChar = new char[quantity];
        char temp1;
        int temp2;
        int temp3;

        for (int i = 0; i < rowChar.length; i++) {
            System.out.println("Choose the row of the seat (A-J)");
            temp1 = bufferedReader.readLine().charAt(0);

            temp2 = convertToNum(temp1);

            System.out.println("Choose the column of the seat (0-9)");
            temp3 = Integer.parseInt(bufferedReader.readLine());

            while (selectedEvent.getSeats()[temp2][temp3].equals("X") || isSeatTaken(
                row, col, temp2, temp3)) {
                System.out.println("Seat is taken please try again!");
                System.out.println("Choose the column of the seat (A-J)");
                temp1 = bufferedReader.readLine().charAt(0);

                temp2 = convertToNum(temp1);

                System.out.println("Choose the row of the seat (0-9)");
                temp3 = Integer.parseInt(bufferedReader.readLine());
            }
            rowChar[i] = temp1;
            row.add(temp2);
            col.add(temp3);
        }

        System.out.println("Are you sure you want to buy a ticket for (Y/N): ");
        if (currentUser instanceof RegisteredUser) {
            venues.printTicket(selectedVenue, selectedTheater, selectedEvent,
                ((RegisteredUser) currentUser).getStatus(), quantity);
        } else {
            venues.printTicket(selectedVenue, selectedTheater, selectedEvent, quantity);
        }

        String ans = bufferedReader.readLine();
        if (ans.equals("N")) {
            return;
        }

        for (int i = 0; i < row.size(); i++) {
            selectedEvent.setSeats(row.get(i), col.get(i));
        }
        venues.saveVenues();
        orderTicket(rowChar, col);
    }

    /**
     * Allows user to order the ticket and generate either a physical or digital receipt
     *
     * @param line
     * @param col
     * @throws IOException
     */
    public void orderTicket(char[] line, LinkedList<Integer> col) throws IOException {
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

    /**
     * Generates a physical ticket and prints it into a txt file
     *
     * @param venue
     * @param theater
     * @param event
     * @param line
     * @param col
     */
    public void generatePhysicalTicket(Venue venue, Theater theater, Event event, char[] line,
        LinkedList<Integer> col) {
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
    public void generateOnlineTicket(Venue venue, Theater theater, Event event, char[] line,
        LinkedList<Integer> col) {
        System.out.println(
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