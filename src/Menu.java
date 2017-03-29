import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by 47989768s on 29/03/17.
 */
public class Menu {

    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;
    static String reciever = "47989768s@iespoblenou.org";
    static String sender;
    static String password;
    static Scanner sc;

    public static void main (String args[]) throws MessagingException {

        sc = new Scanner(System.in);

        showLogin();

        setupMailProperties();
        getSession();
        showMenu();


    }

    private static void showLogin() {

        System.out.print("Mail (solo gmail): ");
        sender = sc.next();
        System.out.println();

        System.out.print("Password: ");
        password = sc.next();
        System.out.println();

    }

    public static void showMenu() throws MessagingException {

        System.out.println("-------------- BIENVENIDO A CUTREMAIL --------------");
        System.out.println("1.- Enviar email.");
        System.out.println("2.- Consultar emails.");
        System.out.println("3.- Log out.");
        System.out.println("4.- Log out and exit.");
        System.out.println("----------------------------------------------------");

        int option = sc.nextInt();
        getSelection(option);

    }

    public static void getSelection(int option) throws MessagingException {

        switch (option) {

            case 1:
                sendEmail();
                break;
            case 2:
                getEmails();
                break;
            case 3:
                logOut();
                break;
            case 4:
                exit();
                break;
            default:
                System.out.println("Opción no válida.");
                showMenu();
                break;

        }

    }

    private static void exit() {
        System.exit(1);
    }

    private static void logOut() {

        sender = "";
        password = "";
        showLogin();
    }

    private static void getEmails() throws MessagingException {

        showMenu();
    }

    private static void sendEmail() throws MessagingException {

        System.out.print("Receptor: ");
        reciever = sc.next();
        System.out.println();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Sujeto: ");
        String subject = null;
        String body = null;

        try {

            subject = br.readLine();
            System.out.println();

            System.out.print("Cuerpo: ");

            body = br.readLine();
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
        }


        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(reciever));
        generateMailMessage.setSubject(subject);
        generateMailMessage.setContent(body, "text/html");

        for(int i = 0; i < 10; ++i) {
            Transport transport = getMailSession.getTransport("smtp");

            // Enter your correct gmail UserID and Password
            // if you have 2FA enabled then provide App Specific Password
            transport.connect("smtp.gmail.com", sender, password);
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();

            System.out.println("E-mail enviado correctamente.");
        }

        showMenu();
    }

    private static void setupMailProperties() {

        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");

    }

    private static void getSession() {

        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);

    }

}
