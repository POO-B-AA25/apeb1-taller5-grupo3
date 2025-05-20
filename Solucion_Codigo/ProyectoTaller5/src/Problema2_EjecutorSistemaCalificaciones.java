import java.util.Random;
import java.util.Scanner;

public class Problema2_EjecutorSistemaCalificaciones {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Random random = new Random();
        String nombres[] = {"Diana", "Carlos","Ivanna", "Ivan", "Jorge", 
                            "Joel", "Dennilson", "Valentina"};
        String apellidos[] = {"Abad", "Jaramillo", "Castillo", "Rios", "Carrion",
                            "Velasquez", "Rojas", "Velez"};
        String materias[] = {"Matematica", "Lengua y Literatura", "Ingles", "Historia",
                            "ECA", "Quimica", "Fisica", "Biologia "};
       
        char opcion = 's';

        while (opcion == 's' || opcion == 'S') {

            String nombreEstudiante = nombres[random.nextInt(nombres.length)] + " "
                    + apellidos[random.nextInt(apellidos.length)];
            int edad = 18 + random.nextInt(7);

            String nombreMateria = materias[random.nextInt(materias.length)];
            double notaACD = Math.floor(Math.random() * 36) / 10.0;
            double notaAPE = Math.floor(Math.random() * 36) / 10.0;
            double notaAA = Math.floor(Math.random() * 31) / 10.0;


            Materia materia = new Materia(nombreMateria, notaACD, notaAPE, notaAA);
            Estudiante estudiante = new Estudiante(nombreEstudiante, edad, materia);

            System.out.println("--- Sistema Calificaciones ---");
            System.out.println(estudiante.nombreEstudiante);
            System.out.println(materia);
            System.out.println("Nota Final: " + materia.calcularNotaFinal());

            if (!estudiante.comprobarAprobacion()) {
                System.out.println("Debe rendir un examen de recuperacion sobre"
                + " 3.5/10 pts. agregado al 60% acumulado de los componentes ACD, APE y AA.");
            } else {
                System.out.println("Aprobado");
            }

            System.out.print("Desea añadir otro estudiante? (s/n): ");
            opcion = scanner.next().charAt(0);

        }
    }

}

class Estudiante {

    public String nombreEstudiante;
    public int edad;
    public Materia materia;
    public boolean aprobado;

    public Estudiante() {
    }

    public Estudiante(String nombreEstudiante, int edad, Materia materia) {
        this.nombreEstudiante = nombreEstudiante;
        this.edad = edad;
        this.materia = materia;
    }

    public boolean comprobarAprobacion() {
        double total = materia.notaACD + materia.notaAPE + materia.notaAA;
        return aprobado = total >= 7;
    }

    @Override
    public String toString() {
        return "Estudiante" + "\nnombreEstudiante = " + nombreEstudiante + 
                "\nedad = " + edad + "\nmateria = " + materia + "\naprobado = " 
                + aprobado;
    }

}

class Materia {

    public String nombreMateria;
    public double notaACD;
    public double notaAPE;
    public double notaAA;
    public double notaFinal;

    public Materia() {
    }

    public Materia(String nombreMateria, double notaACD, double notaAPE, double notaAA) {
        this.nombreMateria = nombreMateria;
        this.notaACD = notaACD;
        this.notaAPE = notaAPE;
        this.notaAA = notaAA;
    }

    public double calcularNotaFinal() {
        this.notaFinal = notaACD + notaAPE + notaAA;
        return this.notaFinal;
    }

    @Override
    public String toString() {
        return "Materia" + "\nnombreMateria = " + nombreMateria + "\nnotaACD=" 
                + notaACD + "\nnotaAPE = " + notaAPE + "\nnotaAA = " + notaAA;
    }

}