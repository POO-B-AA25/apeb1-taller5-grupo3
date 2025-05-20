import java.util.Random;
import java.util.Scanner;

public class Problema4_EjecutorSistemaFiscalia {

    public static void main(String[] args) {
        Random random = new Random();
        char opcion;
        Scanner tcl = new Scanner(System.in);

        String nombres[] = {"Juan", "Maria", "Luis", "Carlos", "Sonia", "Karla"};
        String apellidos[] = {"Rodriguez", "Pardo", "Valdeon", "Soto", "Torres", "Abad"};
        String implicacion[] = {"Acusado", "Testigo", "Victima"};
        boolean colaboraInfo[] = {true, false};
        System.out.println("--- Sistema FISCALIA ----\n");
        opcion = 'S';
        while (opcion == 'S') {
            String nombreImplicado = nombres[random.nextInt(nombres.length)] + " "
                    + apellidos[random.nextInt(apellidos.length)];

            PersonaImplicada personaImplicada = new PersonaImplicada(
                    nombreImplicado,
                    implicacion[random.nextInt(implicacion.length)],
                    random.nextInt(24),
                    colaboraInfo[random.nextInt(colaboraInfo.length)]);

            System.out.println(personaImplicada);

            CasoCorrupcion casoCorrupcion = new CasoCorrupcion("Matastaxis", 3,
                    personaImplicada, random.nextDouble() * 1000);

            System.out.println(casoCorrupcion);
            casoCorrupcion.actualizarEstadoCaso(15);
            casoCorrupcion.actualizarPena(5);
            System.out.println(casoCorrupcion.pagarFianza(100));
            System.out.println(casoCorrupcion);

            System.out.println("Generar mas? (S/N)?");
            opcion = tcl.next().toUpperCase().charAt(0); 
        }
    }
}

class CasoCorrupcion {
    public String nombre;
    public int diaInicio;
    public String estadoCaso;
    public PersonaImplicada personaImplicada;
    public double montoDanio;

    public CasoCorrupcion(String nombre, int diaInicio, PersonaImplicada personaImplicada, double montoDanio) {
        this.nombre = nombre;
        this.diaInicio = diaInicio;
        this.personaImplicada = personaImplicada;
        this.montoDanio = montoDanio;
        this.estadoCaso = "Iniciado";
    }

    public void actualizarEstadoCaso(int diaActual) {
        if ((diaActual - diaInicio) > 7 && (diaActual - diaInicio) < 15)
            estadoCaso = "Alerta";
        else if ((diaActual - diaInicio) > 15)
            estadoCaso = "Urgente";
    }

    public void actualizarPena(int reduccionPena) {
        if ((personaImplicada.implicacion.equals("Acusado")) &&
                personaImplicada.colaboraInfo) {
            personaImplicada.penaMeses -= reduccionPena;
        }
    }

    public String pagarFianza(double fianza) {
        String estadoFianza = "No se acepta la fianza por que no es acusado o no colabora con informacion";

        if ((personaImplicada.implicacion.equals("Acusado")) &&
                personaImplicada.colaboraInfo) {
            if (fianza < (montoDanio * 0.5)) {
                personaImplicada.penaMeses = 0;
                estadoFianza = "Se acepta la fianza de " + fianza;
            } else {
                estadoFianza = "No se acepta la fianza de " + fianza + " por que excede el 50% del danio";
            }
        }
        return estadoFianza;
    }

    public String toString() {
        return "CasoCorrupcion\n" + "nombre = " + nombre + "\ndiaInicio = " + 
                diaInicio + "\nestadoCaso = " + estadoCaso + "\npersonaImplicada = " 
                + personaImplicada + "\nmontoDanio = " + montoDanio;
    }
}

class PersonaImplicada {
    public String nombre;
    public String implicacion;
    public int penaMeses;
    public boolean colaboraInfo;

    public PersonaImplicada(String nombre, String implicacion, int penaMeses, boolean colaboraInfo) {
        this.nombre = nombre;
        this.implicacion = implicacion;
        this.penaMeses = penaMeses;
        this.colaboraInfo = colaboraInfo;
    }

    public String toString() {
        return "PersonaImplicada\n" + "nombre = " + nombre + "\nimplicacion = " 
                + implicacion + "\npenaMeses = " + penaMeses + "\ncolaboraInfo = " 
                + colaboraInfo;
    }
}

