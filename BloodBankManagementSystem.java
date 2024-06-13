import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

class User {
    private String username;
    private String password;
    private java.util.List<String> donations;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.donations = new java.util.ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void donateBlood(String bloodType, int quantity) {
        donations.add(bloodType + ": " + quantity + " units");
    }

    public java.util.List<String> getDonations() {
        return donations;
    }
}

class BloodBank {
    private Map<String, User> users;
    private Map<String, Integer> bloodInventory;

    public BloodBank() {
        users = new HashMap<>();
        bloodInventory = new HashMap<>();
    }

    public String createUser(String username, String password) {
        if (users.containsKey(username)) {
            return "User already exists.";
        }
        users.put(username, new User(username, password));
        return "User created successfully.";
    }

    public User loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            return user;
        }
        return null;
    }

    public void donateBlood(User user, String bloodType, int quantity) {
        user.donateBlood(bloodType, quantity);
        bloodInventory.put(bloodType, bloodInventory.getOrDefault(bloodType, 0) + quantity);
    }

    public boolean collectBlood(String bloodType, int quantity) {
        int currentQuantity = bloodInventory.getOrDefault(bloodType, 0);
        if (currentQuantity >= quantity) {
            bloodInventory.put(bloodType, currentQuantity - quantity);
            return true;
        }
        return false;
    }

    public Map<String, Integer> showInventory() {
        return bloodInventory;
    }
}

public class BloodBankManagementSystem extends Frame {
    private BloodBank bloodBank;
    private User currentUser;
    
    public BloodBankManagementSystem() {
        bloodBank = new BloodBank();
        currentUser = null;

        setLayout(new FlowLayout());
        
        Label label = new Label("Blood Bank Management System");
        add(label);

        Button createUserButton = new Button("Create User");
        add(createUserButton);
        createUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createUser();
            }
        });

        Button loginUserButton = new Button("Login User");
        add(loginUserButton);
        loginUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });

        Button donateBloodButton = new Button("Donate Blood");
        add(donateBloodButton);
        donateBloodButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                donateBlood();
            }
        });

        Button collectBloodButton = new Button("Collect Blood");
        add(collectBloodButton);
        collectBloodButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                collectBlood();
            }
        });

        Button showInventoryButton = new Button("Show Inventory");
        add(showInventoryButton);
        showInventoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showInventory();
            }
        });

        Button logoutButton = new Button("Logout");
        add(logoutButton);
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        Button exitButton = new Button("Exit");
        add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setSize(300, 400);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }

    private void createUser() {
        Dialog d = new Dialog(this, "Create User", true);
        d.setLayout(new FlowLayout());
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField(20);
        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField(20);
        passwordField.setEchoChar('*');
        Button createButton = new Button("Create");

        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String result = bloodBank.createUser(username, password);
                Dialog messageDialog = new Dialog(d, "Message", true);
                messageDialog.setLayout(new FlowLayout());
                messageDialog.add(new Label(result));
                Button okButton = new Button("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        messageDialog.setVisible(false);
                        messageDialog.dispose();
                    }
                });
                messageDialog.add(okButton);
                messageDialog.setSize(200, 100);
                messageDialog.setVisible(true);
                d.setVisible(false);
                d.dispose();
            }
        });

        d.add(usernameLabel);
        d.add(usernameField);
        d.add(passwordLabel);
        d.add(passwordField);
        d.add(createButton);
        d.setSize(300, 200);

        // Window close event
        d.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                d.dispose();
            }
        });

        d.setVisible(true);
    }

    private void loginUser() {
        Dialog d = new Dialog(this, "Login User", true);
        d.setLayout(new FlowLayout());
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField(20);
        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField(20);
        passwordField.setEchoChar('*');
        Button loginButton = new Button("Login");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                User user = bloodBank.loginUser(username, password);
                Dialog messageDialog = new Dialog(d, "Message", true);
                messageDialog.setLayout(new FlowLayout());
                if (user != null) {
                    currentUser = user;
                    messageDialog.add(new Label("Login successful."));
                } else {
                    messageDialog.add(new Label("Invalid username or password."));
                }
                Button okButton = new Button("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        messageDialog.setVisible(false);
                        messageDialog.dispose();
                    }
                });
                messageDialog.add(okButton);
                messageDialog.setSize(200, 100);
                messageDialog.setVisible(true);
                d.setVisible(false);
                d.dispose();
            }
        });

        d.add(usernameLabel);
        d.add(usernameField);
        d.add(passwordLabel);
        d.add(passwordField);
        d.add(loginButton);
        d.setSize(300, 200);

        // Window close event
        d.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                d.dispose();
            }
        });

        d.setVisible(true);
    }

    private void donateBlood() {
        if (currentUser == null) {
            showMessage("Please login first.");
            return;
        }

        Dialog d = new Dialog(this, "Donate Blood", true);
        d.setLayout(new FlowLayout());
        Label bloodTypeLabel = new Label("Blood Type:");
        TextField bloodTypeField = new TextField(20);
        Label quantityLabel = new Label("Quantity:");
        TextField quantityField = new TextField(20);
        Button donateButton = new Button("Donate");

        donateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String bloodType = bloodTypeField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                bloodBank.donateBlood(currentUser, bloodType, quantity);
                showMessage("Blood donated successfully.");
                d.setVisible(false);
                d.dispose();
            }
        });

        d.add(bloodTypeLabel);
        d.add(bloodTypeField);
        d.add(quantityLabel);
        d.add(quantityField);
        d.add(donateButton);
        d.setSize(300, 200);

        // Window close event
        d.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                d.dispose();
            }
        });

        d.setVisible(true);
    }

    private void collectBlood() {
        Dialog d = new Dialog(this, "Collect Blood", true);
        d.setLayout(new FlowLayout());
        Label bloodTypeLabel = new Label("Blood Type:");
        TextField bloodTypeField = new TextField(20);
        Label quantityLabel = new Label("Quantity:");
        TextField quantityField = new TextField(20);
        Button collectButton = new Button("Collect");

        collectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String bloodType = bloodTypeField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                if (bloodBank.collectBlood(bloodType, quantity)) {
                    showMessage("Blood collected successfully.");
                } else {
                    showMessage("Insufficient blood in inventory.");
                }
                d.setVisible(false);
                d.dispose();
            }
        });

        d.add(bloodTypeLabel);
        d.add(bloodTypeField);
        d.add(quantityLabel);
        d.add(quantityField);
        d.add(collectButton);
        d.setSize(300, 200);

        // Window close event
        d.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                d.dispose();
            }
        });

        d.setVisible(true);
    }

    private void showInventory() {
        Dialog d = new Dialog(this, "Blood Inventory", true);
        d.setLayout(new FlowLayout());
        Map<String, Integer> inventory = bloodBank.showInventory();
        if (inventory.isEmpty()) {
            d.add(new Label("No blood in inventory."));
        } else {
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                d.add(new Label(entry.getKey() + ": " + entry.getValue() + " units"));
            }
        }
        Button okButton = new Button("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                d.setVisible(false);
                d.dispose();
            }
        });
        d.add(okButton);
        d.setSize(300, 200);

        // Window close event
        d.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                d.dispose();
            }
        });

        d.setVisible(true);
    }

    private void logout() {
        currentUser = null;
        showMessage("Logged out successfully.");
    }

    private void showMessage(String message) {
        Dialog d = new Dialog(this, "Message", true);
        d.setLayout(new FlowLayout());
        d.add(new Label(message));
        Button okButton = new Button("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                d.setVisible(false);
                d.dispose();
            }
        });
        d.add(okButton);
        d.setSize(200, 100);

        // Window close event
        d.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                d.dispose();
            }
        });

        d.setVisible(true);
    }

    public static void main(String[] args) {
        new BloodBankManagementSystem();
    }
}

