import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Book {
    int id;
    String title;
    String author;
    boolean available;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = true;
    }
}

class Member {
    static int memberCount = 0; // Counter for generating unique member IDs
    int id;
    String name;

    public Member(String name) {
        this.id = ++memberCount; // Auto-generate member ID
        this.name = name;
    }
}

public class LibraryManagementSystem {
    ArrayList<Book> books = new ArrayList<>();
    ArrayList<Member> members = new ArrayList<>();
    Queue<Integer> bookQueue = new LinkedList<>();

    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();
        library.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Library Management System Menu:");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. View Books"); // New option to view books
            System.out.println("6. View Members"); // New option to view members
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    addMember(scanner);
                    break;
                case 3:
                    borrowBook(scanner);
                    break;
                case 4:
                    returnBook(scanner);
                    break;
                case 5:
                    displayBooks(); // Call the method to view books
                    break;
                case 6:
                    displayMembers(); // Call the method to view members
                    break;
                case 7:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private int bookIdCounter = 0;

    private void addBook(Scanner scanner) {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        // Generate the next book ID
        int bookId = ++bookIdCounter;

        Book book = new Book(bookId, title, author);
        books.add(book);

        System.out.println("Book added successfully with ID: B" + String.format("%04d", bookId));
    }

    private void addMember(Scanner scanner) {
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();

        Member member = new Member(name);
        members.add(member);

        System.out.println("Member added successfully with ID: " + member.id);
    }

    private void displayMembers() {
        System.out.println("List of Members:");
        for (Member member : members) {
            System.out.println("ID: " + member.id + ", Name: " + member.name);
        }
    }

    private void displayBooks() {
        System.out.println("List of Books:");
        for (Book book : books) {
            System.out.println("ID: " + book.id + ", Title: " + book.title + ", Author: " + book.author
                    + ", Available: " + (book.available ? "Yes" : "No"));
        }
    }

    private void borrowBook(Scanner scanner) {
        if (members.isEmpty() || books.isEmpty()) {
            System.out.println("No members or books available.");
            return;
        }

        System.out.print("Enter member ID: ");
        int memberId = scanner.nextInt();

        if (memberId < 1 || memberId > members.size()) {
            System.out.println("Invalid member ID.");
            return;
        }

        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();

        if (bookId < 1 || bookId > books.size()) {
            System.out.println("Invalid book ID.");
            return;
        }

        if (!books.get(bookId - 1).available) {
            System.out.println("This book is not available.");
            return;
        }
        books.get(bookId - 1).available = false;
        bookQueue.add(bookId);

        System.out.println("Book borrowed successfully.");
    }

    private void returnBook(Scanner scanner) {
        System.out.print("Enter the ID of the book you want to return: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        boolean bookFound = false;

        for (Book book : books) {
            if (book.id == bookId && !book.available) {
                book.available = true;
                bookQueue.remove(book.id);
                bookFound = true;
                System.out.println("Book with ID " + bookId + " has been successfully returned.");
                break; // Exit the loop once the book is found and returned
            }
        }

        if (!bookFound) {
            System.out.println("Book with ID " + bookId + " not found or already returned.");
        }
    }
}