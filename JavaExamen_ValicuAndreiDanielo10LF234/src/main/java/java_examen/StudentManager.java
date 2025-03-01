package java_examen;
import java.util.*;

public class StudentManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMeniu:");
            System.out.println("1. Afiseaza studentii dintr-o grupa");
            System.out.println("2. Afiseaza un student dupa nume");
            System.out.println("3. Exit");
            System.out.print("Alegeti o optiune: ");
            String optiune = scanner.nextLine();

            if (optiune.equals("1")) {
                System.out.print("Introduceti numele grupei: ");
                String grupaNume = scanner.nextLine();
                List<Student> studenti = StudentDAO.getStudentiByGrupa(grupaNume);
                if (studenti.isEmpty()) {
                    System.out.println("Grupa nu exista sau nu are studenti.");
                } else {
                    for (Student student : studenti) {
                        if (student.areRestanta()) {
                            System.out.println(student.getNume() + " - RESTANTA");
                        } else {
                            System.out.println(student.getNume() + " - Media: " + String.format("%.2f", student.getMedia()));
                        }
                    }
                }
            } else if (optiune.equals("2")) {
                System.out.print("Introduceti numele studentului: ");
                String studentNume = scanner.nextLine();
                Student student = StudentDAO.getStudentByName(studentNume);
                if (student != null) {
                    if (student.areRestanta()) {
                        System.out.println(student.getNume() + " - RESTANTA");
                    } else {
                        System.out.println(student.getNume() + " - Media: " + String.format("%.2f", student.getMedia()));
                    }
                } else {
                    System.out.println("Studentul nu a fost gasit.");
                }
            } else if (optiune.equals("3")) {
                System.out.println("Aplicatia se inchide...");
                System.out.println("Conexiunea cu baza de date se inchide...");
                DatabaseConnection.closeConnection();
                break;
            } else {
                System.out.println("Opțiune invalida, incercati din nou.");
            }
        }
        scanner.close();
    }
}

