import java.io.IOException;
import java.io.File;
import java.util.*;
import java.io.FileWriter;
import java.io.FileNotFoundException;

public class WestminsterShoppingManager implements ShoppingManager {

    private ArrayList<Product> prodList = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private String prodId;
    private int option;
    private int prodType;

    @Override
    public ArrayList<Product> getProdList() {
        return prodList;
    }

    public static void main(String[] args) {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        manager.consoleMenu();
    }

    public void consoleMenu() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("1. Add a new product");
            System.out.println("2. Delete a product");
            System.out.println("3. Print the list of the products");
            System.out.println("4. Save in a file");
            System.out.println("5. Open GUI");
            System.out.println("6. Exit from the system");

            boolean validInput = false;
            while (!validInput) {
                try {
                    System.out.println("Choose an option:-");
                    if (scanner.hasNextInt()) {
                        option = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                        if (option < 1 || option > 6) {
                            System.out.println("Invalid input. Please select a number between 1 and 6.");
                        } else {
                            validInput = true;
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a number from the displayed options.");
                        scanner.nextLine(); // Consume the invalid input
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input" + e);
                    scanner.nextLine(); // Consume the invalid input
                }
            }

            switch (option) {
                case 1:
                    prodIdGet();
                    addProd(prodId);
                    break;

                case 2:
                    removeProd(prodId);
                    break;

                case 3:
                    printProdList();
                    break;

                case 4:
                    saveFiles();
                    break;

                case 5:
                    UserInfoHandle userHandler = new UserInfoHandle();
                    userHandler.loadDataFromUserDetails();
                    LoginPageGUI loginPage = new LoginPageGUI(userHandler.getUserInfo());
                    isRunning = false;
                    break;

                case 6:
                    isRunning = false;
                    break;
            }
        }
    }


    public String prodIdGet() {
        boolean isValid = true;
        while (isValid) {
            try {
                System.out.println("Enter the product ID:-");
                prodId = scanner.next();
                if (prodId.matches(".*[0-9].*") && prodId.matches(".*[a-zA-Z].*")) {
                    isValid = false;
                }else {
                    System.out.println("Invalid Product ID. It must contain both letters and numbers.");
                }
            } catch (NullPointerException e) {
                System.out.println("Invalid ProductId" + e);
                scanner.nextLine();
            }
        }
        return prodId;
    }

    public void addProd(String productId) {
        System.out.println("Select the product type \n 1.Electronic \n 2.Clothing");
        boolean isValidInput = false;
        int prodType = 0;

        while (!isValidInput) {
            try {
                System.out.println("Enter the Product type 1 or 2 :-");
                if (scanner.hasNextInt()) {
                    prodType = scanner.nextInt();
                    if (prodType == 1 || prodType == 2) {
                        isValidInput = true;
                    } else {
                        System.out.println("Invalid input. Please enter 1 or 2.");
                    }
                }else {
                    System.out.println("Invalid input. Please enter a number (1 or 2).");
                    scanner.next();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input");
                scanner.nextLine();
            }
        }

        String prodName = null;
        while (prodName == null || prodName.contains(" ")) {
            System.out.println("Enter the Product Name:-");
            prodName = scanner.next();

        }

        int numberOfProducts = 0;
        boolean validQuantityInput = false;
        scanner.nextLine(); // Consume the newline
        while (!validQuantityInput) {
            try {
                System.out.println("Enter the number of products:");
                numberOfProducts = scanner.nextInt();
                if (numberOfProducts < 1) {
                    System.out.println("Invalid input. Please enter a positive number.");
                } else {
                    validQuantityInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                scanner.nextLine();
            }
        }

        double prodPrice = 0.0;
        boolean validPriceInput = false;
        while (!validPriceInput) {
            try {
                System.out.println("Enter the product price:");
                prodPrice = scanner.nextDouble();
                if (prodPrice < 1) {
                    System.out.println("Invalid input. Please enter a positive price.");
                } else {
                    validPriceInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                scanner.nextLine();
            }
        }

        switch (prodType) {
            case 1:
                String prodBrand = null;
                boolean validBrandInput = false;
                while (!validBrandInput) {
                    try {
                        System.out.println("Enter the electronic item brand:-");
                        prodBrand = scanner.next();
                        validBrandInput = true;
                    } catch (NoSuchElementException | IllegalStateException e) {
                        System.out.println("Invalid input");
                    }
                }

                String prodWarranty = null;
                boolean validWarrantyInput = false;
                while (!validWarrantyInput) {
                    try {
                        System.out.println("Enter the item warranty period in weeks:-");
                        int productWarrantyWeeks = scanner.nextInt();
                        prodWarranty = productWarrantyWeeks + " " + "weeks warranty";
                        validWarrantyInput = true;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input");
                        scanner.nextLine();
                    }
                }

                Electronic electronic = new Electronic(productId, prodName, numberOfProducts, prodPrice, prodBrand, prodWarranty);
                prodList.add(electronic);
                System.out.println("Product has been added to the system");
                break;

            case 2:
                String clothSize = null;
                boolean validSizeInput = false;
                while (!validSizeInput) {
                    try {
                        System.out.println("Enter the size of the clothe:-");
                        clothSize = scanner.next();
                        validSizeInput = true;
                    } catch (NoSuchElementException | IllegalStateException e) {
                        System.out.println("Invalid input");
                    }
                }

                String clothColour = null;
                boolean validColourInput = false;
                while (!validColourInput) {
                    try {
                        System.out.println("Enter the colour of the clothe:-");
                        clothColour = scanner.next();
                        validColourInput = true;
                    } catch (NoSuchElementException | IllegalStateException e) {
                        System.out.println("Invalid input");
                    }
                }
                Clothing clothes = new Clothing(this.prodId, prodName, numberOfProducts, prodPrice, clothSize, clothColour);
                prodList.add(clothes);
                System.out.println("Product has been added to the system");
                break;
        }
    }

    public void removeProd(String removingItem) {
        boolean isIdFound = false;
        while (!isIdFound) {
            prodIdGet();
            for (Product product : prodList) {
                if (product.getProdId().equals(prodId)) {
                    System.out.println(product);
                    prodList.remove(product);
                    System.out.println("Product has been removed successfully");
                    System.out.println("Available items in the list :- " + prodList.size());
                    try {
                        FileWriter fileWriter = new FileWriter("shoppingManager.txt");
                    } catch (IOException e) {
                        System.out.println(e);
                        break;
                    }
                    saveFiles();

                    isIdFound = true;
                    break;
                }
            }
            if (!isIdFound) {
                System.out.println("Product ID does not match with the database");
            }
        }
    }

    public void printProdList() {
        // Sort the product list by product name
        Collections.sort(prodList, Comparator.comparing(Product::getProdName));

        System.out.println("List of Available Products:");
        System.out.println("----------------------------");
        for (Product product : prodList) {
            System.out.println(product);
        }
        System.out.println("----------------------------");
        System.out.println("Total number of products: " + prodList.size());
    }

    public void saveFiles() {
        String filePath = "shoppingManager.txt";
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            for (Product product : prodList) {
                if (product instanceof Electronic) {
                    fileWriter.write(String.join(",",
                            "Electronic",
                            product.getProdId(),
                            product.getProdName(),
                            String.valueOf(product.getNumberOfAvailableItems()),
                            String.valueOf(product.getPrice()),
                            ((Electronic) product).getBrand(),
                            String.valueOf(((Electronic) product).getWarrantyPeriod()) + '\n'
                    ));
                } else if (product instanceof Clothing) {
                    fileWriter.write(String.join(",",
                            "Clothing",
                            product.getProdId(),
                            product.getProdName(),
                            String.valueOf(product.getNumberOfAvailableItems()),
                            String.valueOf(product.getPrice()),
                            ((Clothing) product).getSize(),
                            ((Clothing) product).getColor() + '\n'
                    ));
                }
            }
            System.out.println("Product information has been saved to the file.");
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    public void loadFiles() {
        try {
            String filePath = "shoppingManager.txt";
            File shoppingManager = new File(filePath);
            Scanner fileReader = new Scanner(shoppingManager);
            while (fileReader.hasNextLine()) {
                String[] temp = fileReader.nextLine().split(",");
                if (temp[0].equals("Electronic")) {
                    Electronic electronic = new Electronic(temp[1], temp[2], Integer.parseInt(temp[3]), Double.parseDouble(temp[4]), temp[5], temp[6]);
                    prodList.add(electronic);
                } else if (temp[0].equals("Clothing")) {
                    Clothing clothing = new Clothing(temp[1], temp[2], Integer.parseInt(temp[3]), Double.parseDouble(temp[4]), temp[5], temp[6]);
                    prodList.add(clothing);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }
}
