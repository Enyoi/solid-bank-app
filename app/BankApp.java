package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Account;
import model.Client;
import services.AccountService;
import services.TransferService;

public class BankApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Client> clients = new ArrayList<>();
    private static final TransferService transferService = new TransferService();
    private static final AccountService accountService = new AccountService();

    public static void main(String[] args) {
        seedData();

        while (true) {
            System.out.println("Welcome to the Bank App!");
            System.out.println("1. Listar clientes");
            System.out.println("2. Ver cuentas de un cliente");
            System.out.println("3. Realizar transferencia");
            System.out.println("4. Ver historial de cuenta");
            System.out.println("5. Registrar cliente");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1 -> listClients();
                case 2 -> viewClientAccounts();
                case 3 -> performTransfer();
                case 4 -> viewAccountHistory();
                case 5 -> registerClient();
                case 0 -> {
                    System.out.println("Saliendo de la aplicación. ¡Gracias por usar el Banco App!");
                    System.exit(0);
                }
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void registerClient() {
        System.out.print("Ingrese el nombre del cliente: ");
        String name = scanner.next();
        Client newClient = new Client(name);

        System.out.print("Cuantas cuentas desea agregar al cliente?");
        int count = scanner.nextInt();

        for (int i = 0; i < count; i++) {
            System.out.print("Ingrese el número de cuenta: ");
            String accountNumber = scanner.next();
            System.out.print("Ingrese el tipo de cuenta (1. Ahorros, 2. Corriente): ");
            int type = scanner.nextInt();
            Account account;
            if (type == 1) {
                account = new model.SavingsAccount(accountNumber);
            } else if (type == 2) {
                account = new model.CheckingAccount(accountNumber);
            } else {
                System.out.println("Tipo de cuenta no válido. Cuenta no creada.");
                continue;
            }

            System.out.print("Ingrese el monto inicial: ");
            double initialBalance = scanner.nextDouble();
            account.deposit(initialBalance);

            newClient.addAccount(account);       
        }
        clients.add(newClient);
        System.out.println("Cliente registrado con éxito: " + newClient.getName());
    }

    private static void viewAccountHistory() {
        System.out.print("Ingrese el número de cuenta: ");
        String accountNumber = scanner.next();
        Account account = findAccount(accountNumber);
        if (account != null) {
            accountService.printHistory(account);
        } else {
            System.out.println("Cuenta no encontrada.");
        }
    }

    private static void performTransfer() {
        System.out.print("Ingrese el número de cuenta origen: ");
        String from = scanner.next();
        System.out.print("Ingrese el número de cuenta destino: ");
        String to = scanner.next();
        System.out.print("Ingrese el monto a transferir: ");
        double amount = scanner.nextDouble();

        Account fromAccount = findAccount(from);
        Account toAccount = findAccount(to);

        if (fromAccount != null && toAccount != null) {
            try {
                transferService.transfer(fromAccount, toAccount, amount);
                System.out.println("Transferencia realizada con éxito.");
            } catch (Exception e) {
                System.out.println("Error al realizar la transferencia: " + e.getMessage());
            }
        } else {
            System.out.println("Error: Una o ambas cuentas no existen.");
        }

    }

    private static Account findAccount(String number) {
        for (Client client : clients) {
            Account account = client.findAccount(number);
            if (account != null) {
                return account;
            }
        }
        System.out.println("Cuenta no encontrada.");
        return null;
    }

    private static void seedData(){
        Client maria = new Client("María");
        maria.addAccount(new model.CheckingAccount("1234567890"));
        maria.addAccount(new model.SavingsAccount("0987654321"));
        maria.getAccounts().get(0).deposit(1000);
        maria.getAccounts().get(1).deposit(500);

        Client juan = new Client("Juan");
        juan.addAccount(new model.CheckingAccount("1122334455"));
        juan.getAccounts().get(0).deposit(2000);

        clients.add(maria);
        clients.add(juan);
    }

    private static void listClients() {
        System.out.println("Lista de clientes:");
        for (int i = 0; i < clients.size(); i++) {
            System.out.printf("%d. %s\n", i + 1,  clients.get(i).getName());
        }
    }

    private static Client selectClient(){
        listClients();
        System.out.print("Seleccione un cliente por número: ");
        int index = scanner.nextInt() - 1;
        if(index >= 0 && index < clients.size()) return clients.get(index);
        System.out.println("Cliente no encontrado.");
        return null;
    }

    private static void viewClientAccounts() {
        Client client = selectClient();
        if (client != null) {
            System.out.println("Cuentas de " + client.getName() + ":");
            for (Account account : client.getAccounts()) {
                System.out.printf("Cuenta %s - Saldo: %.2f\n", account.getNumber(), account.getBalance());
            }
        }
      
    }

}
