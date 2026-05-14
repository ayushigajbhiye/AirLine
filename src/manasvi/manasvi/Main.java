
package manasvi;
import java.sql.*;
import java.util.*;

public class Main {

    static final String JDBC_URL = "jdbc:mysql://localhost:3306/airline";
    static final String User = "root";
    static final String password = "root";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, User, password);
                Scanner sc = new Scanner(System.in)) {

            int mainChoice = 0;

            while (mainChoice != 3) {
                // Main menu
                System.out.println("Welcome to Flight go!");
                System.out.println("Menu:\n1.User\n2.Admin\n3.Exit");
                System.out.print("Enter choice to be selected: ");
                mainChoice = sc.nextInt();

                switch (mainChoice) {
                    case 1: // User menu
                        int userChoice = 0;
                        while (userChoice != 3) {
                            System.out.println("\nUser Menu:\n1.Signup\n2.Login\n3.Exit");
                            System.out.print("Enter choice: ");
                            userChoice = sc.nextInt();

                            switch (userChoice) {
                                case 1:
                                    Signup(conn, sc); // Signup method
                                    break;

                                case 2:
                                    login(conn, sc); // Login method
                                    int loggedInChoice = 0;
                                    while (loggedInChoice != 11) {
                                        // Logged-in user options
                                        System.out.println(
                                                "\nUser Options:\n1.Search Flights\n2.Book Flight\n3.Payment\n4.View bookings\n5.Display total no of flights available\n6.Get Your booking statistics\n7.Get Flights Above Avg 100\n8.Get available flights by location\n9.Get Flights By Price \n10.Get Unique Location\n11.Exit");
                                        System.out.print("Enter choice: ");
                                        loggedInChoice = sc.nextInt();

                                        switch (loggedInChoice) {
                                            case 1:
                                                System.out.println("\nSearch Menu:");
                                                System.out.println("1. Search by Flight No");
                                                System.out.println("2. Search by Arrival Location");
                                                System.out.println("3. Search by Departure Location");
                                                System.out.print("Enter choice: ");
                                                int searchChoice = sc.nextInt();

                                                switch (searchChoice) {
                                                    case 1:
                                                        searchFlight_byNo(conn, sc); // Search by flight number
                                                        break;

                                                    case 2:
                                                        searchByArrival(conn, sc); // Search by arrival location
                                                        break;

                                                    case 3:
                                                        searchByDeparture(conn, sc); // Search by departure location
                                                        break;

                                                    default:
                                                        System.out.println(
                                                                "Invalid choice! Please select between 1 and 3.");
                                                }
                                                break;

                                            case 2:
                                                bookFlight(conn, sc); // Book a flight
                                                break;

                                            case 3:
                                                payment(conn, sc); // Payment
                                                break;

                                            case 4:
                                                viewBookings(conn, sc); // View bookings
                                                break;

                                            case 5:
                                                flightcount(conn); // Total flights available
                                                break;

                                            case 6:
                                                getUserBookingStatistics(conn, sc); // User booking statistics
                                                break;

                                            case 7:
                                                getFlightsAboveAvg100(conn);
                                                break;

                                            case 8:
                                                int availableFlights = getAvailableFlightsByLocation(sc);
                                                System.out.println("Number of available flights: " + availableFlights);
                                                break;

                                            case 9:
                                                getFlightsByPrice(conn, sc);
                                                break;

                                            case 10:
                                                getUniqueLocations(conn);
                                                break;

                                            case 11:
                                                System.out.println("Exiting user menu!");
                                                break;

                                            default:
                                                System.out.println("Invalid choice! Please select between 1 and 7.");
                                        }
                                    }
                                    break;

                                case 3:
                                    System.out.println("Exiting user menu!");
                                    break;

                                default:
                                    System.out.println("Invalid choice! Please select 1, 2, or 3.");
                            }
                        }
                        break;

                    case 2: // Admin menu
                        int adminChoice = 0;
                        while (adminChoice != 12) {
                            System.out.println(
                                    "\nAdmin Menu:\n1.Add a flight\n2.Remove a flight\n3.Update flight\n4.View Flights\n5.View Flights based on price\n6.View Bookings\n7.View Cancelled Flights\n8.View flight info with total bookings\n9.Get total revenue for a flight\n10.Get Most Oldest Three User\n11.Find User With Bookings\n12.Exit");
                            System.out.print("Enter choice: ");
                            adminChoice = sc.nextInt();

                            switch (adminChoice) {
                                case 1:
                                    addflight(conn, sc); // Add a flight
                                    break;

                                case 2:
                                    removeflight(conn, sc); // Remove a flight
                                    break;

                                case 3:
                                    updateflight(conn, sc); // Update flight info
                                    break;

                                case 4:
                                    viewflight(conn); // View all flights
                                    break;

                                case 5:
                                    viewflightbyorderofprice(conn); // View flights by price
                                    break;

                                case 6:
                                    viewBookings(conn, sc); // View all bookings
                                    break;

                                case 7:
                                    displayCanceledFlights(conn); // View canceled flights
                                    break;

                                case 8:
                                    displayFlightBookings(conn); // Display flight bookings
                                    break;

                                case 9:
                                    getTotalRevenueForFlight(conn, sc); // Get revenue for a flight
                                    break;

                                case 10:
                                    GetMostOldestThreeUser(conn); // Get three oldest users
                                    break;

                                case 11:
                                    findUserWithBookings(conn, sc); // finding user with their booking info
                                    break;

                                case 12:
                                    System.out.println("Exiting admin menu!");
                                    break;

                                default:
                                    System.out.println("Invalid choice! Please select between 1 and 10.");
                            }
                        }
                        break;

                    case 3:
                        System.out.println("Exiting main menu!");
                        break;

                    default:
                        System.out.println("Invalid choice! Please select 1, 2, or 3.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewflightbyorderofprice(Connection conn) throws SQLException {

        String query = "select * from flight order by flight_price";

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {

                System.out.println("Flight no : " + rs.getInt("flight_no"));
                System.out.println("Arrival location : " + rs.getString("arrival_location"));
                System.out.println("Departure location : " + rs.getString("departure_location"));
                System.out.println("Arrival time :  " + rs.getString("arrival_time"));
                System.out.println("Departure time : " + rs.getString("departure_time"));
                System.out.println("Capacity : " + rs.getInt("capacity"));
                System.out.println("Arrival date : " + rs.getString("arrival_date"));
                System.out.println("Departure date : " + rs.getString("departure_date"));
                System.out.println("Price: " + rs.getInt("flight_price"));
                System.out.println("\n------------------------------------------------------------------\n");

            }

        }

    }

    public static void addflight(Connection conn, Scanner sc) throws SQLException {

        System.out.println("Enter Flight No : ");
        int flight_no = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter arrival location :");
        String arrival_location = sc.nextLine();
        System.out.println("Enter departure location :");
        String departure_location = sc.nextLine();
        System.out.println("Enter arrival time :");
        String arrival_time = sc.nextLine();
        System.out.println("Enter departure time :");
        String departure_time = sc.nextLine();
        System.out.println("Enter capacity:");
        int capacity = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter arrival date :");
        String arrival_date = sc.nextLine();
        System.out.println("Enter departure date :");
        String departure_date = sc.nextLine();
        System.out.println("Enter price :");
        int flight_price = sc.nextInt();

        String query = "insert into flight(flight_no,arrival_location,departure_location,arrival_time,departure_time,capacity,arrival_date,departure_date,flight_price) values (?,?,?,?,?,?,?,?,?);";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, flight_no);
            pstmt.setString(2, arrival_location);
            pstmt.setString(3, departure_location);
            pstmt.setString(4, arrival_time);
            pstmt.setString(5, departure_time);
            pstmt.setInt(6, capacity);
            pstmt.setString(7, arrival_date);
            pstmt.setString(8, departure_date);
            pstmt.setInt(9, flight_price);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Flight added successfully !");
            } else {
                System.out.println("Error addding flight !");
            }

        }

    }

    private static void searchFlight_byNo(Connection conn, Scanner sc) throws SQLException {
        sc.nextLine();
        System.out.print("Enter Flight Number to search: ");
        int flightNumber = sc.nextInt();
        sc.nextLine();

        String sql = "SELECT * FROM flight WHERE flight_no = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, flightNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Flight Number: " + rs.getInt("flight_no"));
                    System.out.println("Arrival Location: " + rs.getString("arrival_location"));
                    System.out.println("Departure Location: " + rs.getString("departure_location"));
                    System.out.println("Arrival Date: " + rs.getString("arrival_date"));
                    System.out.println("Departure Date: " + rs.getString("departure_date"));
                    System.out.println("Arrival Time: " + rs.getString("arrival_time"));
                    System.out.println("Departure Time: " + rs.getString("departure_time"));
                    System.out.println("Capacity: " + rs.getInt("capacity"));
                    System.out.println("Price: " + rs.getInt("flight_price"));

                } else {
                    System.out.println("Flight not found.");
                }
            }
        }
    }

    private static void searchByArrival(Connection conn, Scanner sc) throws SQLException {
        sc.nextLine();
        System.out.print("Enter Arrival Location to search: ");
        String arrivalLocation = sc.nextLine();

        String sql = "SELECT * FROM flight WHERE arrival_location = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, arrivalLocation);
            try (ResultSet rs = stmt.executeQuery()) {
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.println("Flight Number: " + rs.getInt("flight_no"));
                    System.out.println("Arrival Location: " + rs.getString("arrival_location"));
                    System.out.println("Departure Location: " + rs.getString("departure_location"));
                    System.out.println("Arrival Date: " + rs.getString("arrival_date"));
                    System.out.println("Departure Date: " + rs.getString("departure_date"));
                    System.out.println("Arrival Time: " + rs.getString("arrival_time"));
                    System.out.println("Departure Time: " + rs.getString("departure_time"));
                    System.out.println("Capacity: " + rs.getInt("capacity"));
                    System.out.println("Price: " + rs.getInt("flight_price"));
                    System.out.println("-----------------------------");
                }
                if (!found) {
                    System.out.println("No flights found with the specified arrival location.");
                }
            }
        }
    }

    private static void searchByDeparture(Connection conn, Scanner sc) throws SQLException {
        sc.nextLine();
        System.out.print("Enter Departure Location to search: ");
        String departureLocation = sc.nextLine();

        String sql = "SELECT * FROM flight WHERE departure_location = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, departureLocation);
            try (ResultSet rs = stmt.executeQuery()) {
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.println("Flight Number: " + rs.getInt("flight_no"));
                    System.out.println("Arrival Location: " + rs.getString("arrival_location"));
                    System.out.println("Departure Location: " + rs.getString("departure_location"));
                    System.out.println("Arrival Date: " + rs.getString("arrival_date"));
                    System.out.println("Departure Date: " + rs.getString("departure_date"));
                    System.out.println("Arrival Time: " + rs.getString("arrival_time"));
                    System.out.println("Departure Time: " + rs.getString("departure_time"));
                    System.out.println("Capacity: " + rs.getInt("capacity"));
                    System.out.println("Price: " + rs.getInt("flight_price"));
                    System.out.println("-----------------------------");
                }
                if (!found) {
                    System.out.println("No flights found with the specified departure location.");
                }
            }
        }
    }

    public static void removeflight(Connection conn, Scanner sc) throws SQLException {

        System.out.println("Enter flight no to be deleted :");
        int flightno = sc.nextInt();
        String query = "delete from flight where flight_no=?; ";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, flightno);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {

                System.out.println("Flight deleted successfully !");
            } else {
                System.out.println("Error deleteing the flight !");
            }

        }

    }

    public static void updateflight(Connection conn, Scanner sc) throws SQLException {

        System.out.println("Enter flight no be updated :");
        int flightno = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter arrival location :");
        String arrival_location = sc.nextLine();
        System.out.println("Enter departure location :");
        String departure_location = sc.nextLine();
        System.out.println("Enter arrival time :");
        String arrival_time = sc.nextLine();
        System.out.println("Enter departure time :");
        String departure_time = sc.nextLine();
        System.out.println("Enter capacity:");
        int capacity = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter arrival date :");
        String arrival_date = sc.nextLine();
        System.out.println("Enter departure date :");
        String departure_date = sc.nextLine();
        System.out.println("Enter price :");
        int flight_price = sc.nextInt();

        String query = "update flight set arrival_location=?,departure_location=?,arrival_time=?,departure_time=?,capacity=?,arrival_date=?,departure_date=?,flight_price=? where flight_no=?; ";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, arrival_location);
            pstmt.setString(2, departure_location);
            pstmt.setString(3, arrival_time);
            pstmt.setString(4, departure_time);
            pstmt.setInt(5, capacity);
            pstmt.setString(6, arrival_date);
            pstmt.setString(7, departure_date);
            pstmt.setInt(9, flightno);
            pstmt.setInt(8, flight_price);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Flight updated successfully !");
            } else {
                System.out.println("error updating the flight !");
            }
        }

    }

    public static void viewflight(Connection conn) throws SQLException {

        String query = "select * from flight;";
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {

                System.out.println("Flight no : " + rs.getInt("flight_no"));
                System.out.println("Arrival location : " + rs.getString("arrival_location"));
                System.out.println("Departure location : " + rs.getString("departure_location"));
                System.out.println("Arrival time :  " + rs.getString("arrival_time"));
                System.out.println("Departure time : " + rs.getString("departure_time"));
                System.out.println("Capacity : " + rs.getInt("capacity"));
                System.out.println("Arrival date : " + rs.getString("arrival_date"));
                System.out.println("Departure date : " + rs.getString("departure_date"));
                System.out.println("Price: " + rs.getInt("flight_price"));
                System.out.println("\n------------------------------------------------------------------\n");

            }

        }

    }

    public static void bookFlight(Connection conn, Scanner sc) throws SQLException {
        sc.nextLine();
        System.out.println("Enter your Username:");
        String username = sc.nextLine(); // Accept username instead of user_id
        sc.nextLine();
        System.out.println("Enter Flight Number to book:");
        int flightNo = sc.nextInt();

        System.out.println("Enter number of tickets to book:");
        int tickets = sc.nextInt();
        sc.nextLine(); // Consume newline

        // Check availability
        String checkCapacityQuery = "SELECT capacity, flight_price FROM flight WHERE flight_no = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkCapacityQuery)) {
            checkStmt.setInt(1, flightNo);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    int availableSeats = rs.getInt("capacity");
                    double flightPrice = rs.getDouble("flight_price"); // Get the flight price
                    if (availableSeats >= tickets) {
                        // Calculate total amount for booking
                        double totalAmount = flightPrice * tickets;

                        // Book the flight
                        String bookFlightQuery = "INSERT INTO booking(username, flight_no, tickets, total_amount) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement bookStmt = conn.prepareStatement(bookFlightQuery)) {
                            bookStmt.setString(1, username);
                            bookStmt.setInt(2, flightNo);
                            bookStmt.setInt(3, tickets);
                            bookStmt.setDouble(4, totalAmount); // Insert total amount

                            int rows = bookStmt.executeUpdate();
                            if (rows > 0) {
                                System.out.println("Flight booked successfully!");

                                // Update flight capacity
                                String updateCapacityQuery = "UPDATE flight SET capacity = capacity - ? WHERE flight_no = ?";
                                try (PreparedStatement updateStmt = conn.prepareStatement(updateCapacityQuery)) {
                                    updateStmt.setInt(1, tickets);
                                    updateStmt.setInt(2, flightNo);
                                    updateStmt.executeUpdate();
                                    System.out.println("Capacity updated successfully.");
                                }
                            } else {
                                System.out.println("Error booking flight.");
                            }
                        }
                    } else {
                        System.out.println("Not enough seats available.");
                    }
                } else {
                    System.out.println("Flight not found.");
                }
            }
        }
    }

    private static void viewBookings(Connection conn, Scanner sc) {
        sc.nextLine();
        System.out.print("Enter Username to view bookings: ");
        String username = sc.nextLine();

        try {
            String query = "SELECT * FROM booking WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("No bookings found for Username: " + username);
                    } else {
                        do {
                            System.out.println("\nBooking ID: " + rs.getInt("booking_id"));
                            System.out.println("Flight Number: " + rs.getInt("flight_no"));
                            System.out.println("Tickets: " + rs.getInt("tickets"));
                            System.out.println("Booking Date: " + rs.getString("booking_date"));
                            System.out.println("------------------------------------");
                        } while (rs.next());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching bookings: " + e.getMessage());
        }
    }

    public static void payment(Connection conn, Scanner sc) {
        sc.nextLine(); // Consume the newline character from previous input
        System.out.println("Enter your Username: ");
        String username = sc.nextLine();

        System.out.println("Enter your Booking ID: ");
        int bookingId = sc.nextInt();

        // Query to get the booking details, including number of tickets and flight
        // price
        String checkBookingQuery = "SELECT b.tickets, f.flight_price FROM booking b JOIN flight f ON b.flight_no = f.flight_no WHERE b.booking_id = ? AND b.username = ?";
        String insertPaymentQuery = "INSERT INTO payments (booking_id, username, payment_method, total_amount, payment_status, payment_date) "
                + "VALUES (?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement stmt = conn.prepareStatement(checkBookingQuery)) {
            stmt.setInt(1, bookingId);
            stmt.setString(2, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int tickets = rs.getInt("tickets");
                double flightPrice = rs.getDouble("flight_price");

                // Debugging: Print values to check what's fetched from the database
                System.out.println("Fetched Tickets: " + tickets);
                System.out.println("Fetched Flight Price: " + flightPrice);

                // Calculate total price for the booking
                double displayprice = tickets * flightPrice;

                System.out.println("Proceed to pay Rs " + displayprice);

                System.out.println("Enter the amount to pay: ");
                double amount = sc.nextDouble();

                if (amount < displayprice) {
                    System.out.println("Insufficient amount. Please pay the full amount.");
                } else {
                    // Insert payment details into the payments table
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertPaymentQuery)) {
                        insertStmt.setInt(1, bookingId);
                        insertStmt.setString(2, username);
                        insertStmt.setString(3, "Credit Card"); // Assuming payment method is 'Credit Card'
                        insertStmt.setDouble(4, amount);
                        insertStmt.setString(5, "Completed");

                        int paymentRows = insertStmt.executeUpdate();
                        if (paymentRows > 0) {
                            System.out.println("Payment successful! Booking confirmed.");

                            // Optionally, you can update the booking status to 'Paid' here
                            String updateBookingQuery = "UPDATE booking SET status = 'Paid' WHERE booking_id = ?";
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateBookingQuery)) {
                                updateStmt.setInt(1, bookingId);
                                updateStmt.executeUpdate();
                                System.out.println("Booking status updated to Paid.");
                            }
                        } else {
                            System.out.println("Failed to record payment. Try again.");
                        }
                    }
                }
            } else {
                System.out.println("No booking found for the provided details.");
            }
        } catch (SQLException e) {
            System.out.println("Error processing payment: " + e.getMessage());
        }
    }

    public static void Signup(Connection conn, Scanner sc) throws SQLException {

        sc.nextLine();
        System.out.print("Enter Username: ");
        String username = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        String query = "insert into user(username,password) values(?,?);";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Signup successful !");

            }

            else {

                System.out.println("Error in singup !");
            }

        }
    }

    public static void login(Connection conn, Scanner sc) throws SQLException {
        sc.nextLine();
        System.out.print("Enter Username: ");
        String usn = sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        String query = "{ CALL LoginUser(?, ?, ?) }";
        try (CallableStatement cstmt = conn.prepareCall(query)) {
            cstmt.setString(1, usn);
            cstmt.setString(2, pass);
            cstmt.registerOutParameter(3, java.sql.Types.INTEGER);

            cstmt.execute();

            int result = cstmt.getInt(3);
            if (result == 1) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Please signup first!");
            }
        }
    }

    public static void displayCanceledFlights(Connection conn) throws SQLException {

        String selectSQL = "SELECT * FROM canceled_flights";

        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(selectSQL)) {

            System.out.println("Canceled Flights:");
            System.out.println("---------------------------------------------------------------------");

            while (rs.next()) {
                int flightNo = rs.getInt("flight_no");
                String arrivalLocation = rs.getString("arrival_location");
                String departureLocation = rs.getString("departure_location");
                String arrivalTime = rs.getString("arrival_time");
                String departureTime = rs.getString("departure_time");
                int capacity = rs.getInt("capacity");
                String arrivalDate = rs.getString("arrival_date");
                String departureDate = rs.getString("departure_date");
                int flightPrice = rs.getInt("flight_price");
                String cancellationReason = rs.getString("cancellation_reason");

                System.out.printf("Flight No: %d\n, Arrival Location: %s\n, Departure Location: %s\n, " +
                        "Arrival Time: %s\n Departure Time: %s,\n Capacity: %d,\n " +
                        "Arrival Date: %s,\n Departure Date: %s,\n Price: %d,\n Reason: %s%n",
                        flightNo, arrivalLocation, departureLocation,
                        arrivalTime, departureTime, capacity,
                        arrivalDate, departureDate, flightPrice, cancellationReason);
            }
            System.out.println("---------------------------------------------------------------------");

        }
    }

    public static void flightcount(Connection conn) throws SQLException {
        // Query to get total flights grouped by departure and arrival locations
        String query = "SELECT departure_location, arrival_location, COUNT(*) AS total_flights " +
                "FROM flight " +
                "GROUP BY departure_location, arrival_location;";

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            // Check if the query returns any results
            if (!rs.isBeforeFirst()) {
                System.out.println("No flights found.");
                return;
            }

            // Loop through the results and display them
            while (rs.next()) {
                String departureLocation = rs.getString("departure_location");
                String arrivalLocation = rs.getString("arrival_location");
                int totalFlights = rs.getInt("total_flights"); // This should correctly show the number of flights

                // Display the result
                System.out.println("Departure Location: " + departureLocation +
                        " | Arrival Location: " + arrivalLocation +
                        " | Total Flights: " + totalFlights);
            }
        }
    }

    public static void displayFlightBookings(Connection conn) throws SQLException {
        String query = "SELECT * FROM flight_bookings"; // Your SQL query to fetch data from the view

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            // Print the headers for the columns with proper spacing and alignment
            System.out.printf("%-12s | %-20s | %-20s | %-15s | %-15s | %-8s | %-12s | %-15s%n",
                    "Flight No", "Departure Location", "Arrival Location",
                    "Departure Time", "Arrival Time", "Capacity",
                    "Flight Price", "Total Bookings");
            System.out.println(
                    "-----------------------------------------------------------------------------------------------");

            // Iterate through the result set and display the flight booking information
            while (rs.next()) {
                int flightNo = rs.getInt("flight_no");
                String departureLocation = rs.getString("departure_location");
                String arrivalLocation = rs.getString("arrival_location");
                Time departureTime = rs.getTime("departure_time");
                Time arrivalTime = rs.getTime("arrival_time");
                int capacity = rs.getInt("capacity");
                double flightPrice = rs.getDouble("flight_price");
                int totalBookings = rs.getInt("total_bookings");

                // Format the time values (if you want to display them as HH:MM:SS)
                String formattedDepartureTime = (departureTime != null) ? departureTime.toString() : "N/A";
                String formattedArrivalTime = (arrivalTime != null) ? arrivalTime.toString() : "N/A";

                // Print the retrieved data with proper alignment using printf
                System.out.printf("%-12d | %-20s | %-20s | %-15s | %-15s | %-8d | %-12.2f | %-15d%n",
                        flightNo, departureLocation, arrivalLocation,
                        formattedDepartureTime, formattedArrivalTime,
                        capacity, flightPrice, totalBookings);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getUserBookingStatistics(Connection conn, Scanner sc) throws SQLException {
        sc.nextLine();
        System.out.print("Enter your username: ");
        String username = sc.nextLine();

        // Query to fetch total bookings and the last booking date for the given user
        String query = "SELECT COUNT(*) AS total_bookings, MAX(booking_date) AS last_booking_date " +
                "FROM booking " +
                "WHERE username = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int totalBookings = rs.getInt("total_bookings");
                    Timestamp lastBookingDate = rs.getTimestamp("last_booking_date");

                    // Display the statistics
                    System.out.println("Total Bookings: " + totalBookings);
                    if (lastBookingDate != null) {
                        System.out.println("Last Booking Date: " + lastBookingDate);
                    } else {
                        System.out.println("No bookings found.");
                    }
                } else {
                    System.out.println("User not found or no bookings made.");
                }
            }
        }
    }

    public static void getTotalRevenueForFlight(Connection conn, Scanner sc) throws SQLException {
        System.out.println("Enter flight no: ");
        int flightNo = sc.nextInt();

        // Query to calculate total revenue based on flight price and number of bookings
        String query = "SELECT SUM(f.flight_price) AS total_revenue " +
                "FROM booking b " +
                "JOIN flight f ON b.flight_no = f.flight_no " +
                "WHERE b.flight_no = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, flightNo);

            // Execute query and handle the result
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    double totalRevenue = rs.getDouble("total_revenue");

                    // If the total revenue is 0, it might mean no bookings have been made yet
                    if (totalRevenue == 0.0) {
                        System.out.println("No revenue generated for flight " + flightNo + " yet.");
                    } else {
                        System.out.println("Total revenue for flight " + flightNo + " is: " + totalRevenue);
                    }
                } else {
                    System.out.println("No bookings found for flight " + flightNo);
                }
            }
        }
    }

    private static void getFlightsAboveAvg100(Connection conn) {
        String query = "SELECT avg_price.flight_no, avg_price.avg_price " +
                "FROM (SELECT flight_no, AVG(flight_price) AS avg_price " +
                "FROM flight " +
                "GROUP BY flight_no) AS avg_price " +
                "WHERE avg_price.avg_price > 100";

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int flightno = rs.getInt("flight_no");
                double avgPrice = rs.getDouble("avg_price");
                System.out.println("Flight: " + flightno + ", Avg Price: " + avgPrice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getAvailableFlightsByLocation(Scanner sc) {
        sc.nextLine();
        System.out.println("Enter departure loaction :");
        String departurelocation = sc.nextLine();
        int availableFlights = 0;

        try (Connection conn = DriverManager.getConnection(JDBC_URL, User, password)) {

            // Call stored function to get available flights
            CallableStatement stmt = conn.prepareCall("{? = call available_flights_by_location(?)}");
            stmt.registerOutParameter(1, Types.INTEGER); // Register the output parameter
            stmt.setString(2, departurelocation); // Set departure_location parameter

            stmt.execute(); // Execute the stored function

            // Get the result (number of available flights)
            availableFlights = stmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableFlights;
    }

    public static void getFlightsByPrice(Connection conn, Scanner sc) throws SQLException {

        // Take database credentials from user

        // Take flight search parameters from user
        System.out.print("Enter minimum flight price: ");
        int minPrice = sc.nextInt();

        System.out.print("Enter maximum flight price: ");
        int maxPrice = sc.nextInt();
        sc.nextLine(); // Consume newline

        String query = "SELECT * FROM flight WHERE flight_price BETWEEN ? AND ? ;";

        try (
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, minPrice);
            pstmt.setInt(2, maxPrice);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int flightNo = rs.getInt("flight_no");
                String arrivalLoc = rs.getString("arrival_location");
                String departureLocation = rs.getString("departure_location");
                String arrivalTime = rs.getString("arrival_time");
                String departureTime = rs.getString("departure_time");
                int capacity = rs.getInt("capacity");
                String arrivalDate = rs.getString("arrival_date");
                String departureDate = rs.getString("departure_date");
                int flightPrice = rs.getInt("flight_price");

                System.out.println("Flight No: " + flightNo);
                System.out.println("Arrival Location: " + arrivalLoc);
                System.out.println("Departure Location: " + departureLocation);
                System.out.println("Arrival Time: " + arrivalTime);
                System.out.println("Departure Time: " + departureTime);
                System.out.println("Capacity: " + capacity);
                System.out.println("Arrival Date: " + arrivalDate);
                System.out.println("Departure Date: " + departureDate);
                System.out.println("Flight Price: " + flightPrice);
                System.out.println("---------------");
            }

        }
    }

    public static void GetMostOldestThreeUser(Connection conn) throws SQLException {

        String query = "select * from user limit 3;";
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)

        ) {

            while (rs.next()) {
                System.out.println("Username : " + rs.getString("username"));
                System.out.println("Password: " + rs.getString("password"));
            }

        }

    }

    public static void findUserWithBookings(Connection conn, Scanner sc) {

        System.out.println("Enter username to search : ");
        String usernamePattern = sc.nextLine();

        // SQL query using JOIN and LIKE
        String sql = "SELECT u.username, u.password, b.booking_id, b.flight_no, b.tickets, b.booking_date, b.total_amount, b.status "
                +
                "FROM user u " +
                "JOIN booking b ON u.username = b.username " +
                "WHERE u.username LIKE ?";

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + usernamePattern + "%");

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Process the result set
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                int bookingId = rs.getInt("booking_id");
                int flightNo = rs.getInt("flight_no");
                int tickets = rs.getInt("tickets");
                String bookingDate = rs.getString("booking_date");
                double totalAmount = rs.getDouble("total_amount");
                String status = rs.getString("status");

                System.out.println("Username: " + username);
                System.out.println("Password: " + password);
                System.out.println("Booking ID: " + bookingId);
                System.out.println("Flight No: " + flightNo);
                System.out.println("Tickets: " + tickets);
                System.out.println("Booking Date: " + bookingDate);
                System.out.println("Total Amount: " + totalAmount);
                System.out.println("Status: " + status);
                System.out.println("-------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getUniqueLocations(Connection conn) {

        // SQL query
        String sql = "SELECT DISTINCT arrival_location AS location FROM flight " +
                "UNION " +
                "SELECT DISTINCT departure_location AS location FROM flight;";

        try (
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("Locations (Arrival or Departure):");
            while (resultSet.next()) {
                String location = resultSet.getString("location");
                System.out.println(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


