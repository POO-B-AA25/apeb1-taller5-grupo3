import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.util.Random;
public class Problema5_EjecutorSistemaConflictos {
    public static void main(String[] args) {
        Random rand = new Random();
        Scanner tcl = new Scanner(System.in);
        char opcion = 'S';
        String nombresEventos [] = {"Batalla", "Tratado de paz", "Reunion Diplomatica"};
        while (opcion == 'S'){
            ArrayList<Pais> paises1 = new ArrayList<>(
                    Arrays.asList(new Pais("Peru", false, 100, 10),
                                new Pais("Ecuador", false, 100, 10),
                                new Pais("Colombia", false, 100, 10)));
            ArrayList<Pais> paises2 = new ArrayList<>(
                    Arrays.asList(new Pais("Rusia", true, 100, 60),
                                new Pais("Ucrania", false, 100, 10),
                                new Pais("China", true, 100, 10)));
            ArrayList<Evento> eventos = new ArrayList<>(
                    Arrays.asList(new Evento(nombresEventos[rand.nextInt(nombresEventos.length)],
                                        LocalDate.of(2025, 5, 15),
                                        "Europa", "Problemas de territorio", false, paises1),
                                  new Evento(nombresEventos[rand.nextInt(nombresEventos.length)],
                                        LocalDate.of(2024, 5, 15),
                                        "Europa", "Problemas politicos", true, paises2)));
            Conflicto conflicto = new Conflicto("Reseteo", LocalDate.of(2025, 5, 15),100, eventos);
            conflicto.actualizarEstado();
            System.out.println(conflicto);
            System.out.print("SIMULAR MAS: ");
            opcion = tcl.next().charAt(0);
        }
    }
    
}
class Conflicto{
    public String nombre;
    public LocalDate fechaInicio;
    public String estadoActual;
    public int totalPaisesMundo;
    public ArrayList<Evento> eventos;
    public Conflicto(String nombre, LocalDate fechaInicio, int totalPaisesMundo, ArrayList<Evento> eventos) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.totalPaisesMundo = totalPaisesMundo;
        this.eventos = eventos;
    }
    public void actualizarEstado(){
        int numeroPaisesBatalla = 0;
        for(Evento evento : eventos){
            numeroPaisesBatalla += (evento.nombre.equals("Batalla")) ? 1 : 0;
        }
        if (((numeroPaisesBatalla/100)*totalPaisesMundo)  > 50)
            this.estadoActual = "GUERRA MUNDIAL";
        else if (((numeroPaisesBatalla/100)*totalPaisesMundo)  > 30 && 
                ((numeroPaisesBatalla/100)*totalPaisesMundo)  <= 50)
            this.estadoActual = "CONVOCAR ONU URGENTE";
        for(Evento evento : eventos){
            for (Pais pais : evento.paises) {
                if (pais.estadoDesarrollo && evento.usoArmasNucleares){
                    this.estadoActual = "GUERRA MUNDIAL";
                    break;
                }
            }
        }
        for(Evento evento : eventos){
            for (Pais pais : evento.paises) {
                if (((pais.numeroBajas/100)*pais.totalHabitantes)  > 50 ) {
                    this.estadoActual = "CONVOCAR ONU URGENTE";
                    break;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Conflicto\n" + ""
                + "nombre = " + nombre + "\nfechaInicio = " + fechaInicio + 
                "\nestadoActual = " + estadoActual + "\ntotalPaisesMundo = " + 
                totalPaisesMundo + "\neventos = " + eventos;
    }
    
}
class Evento{
    public String nombre; 
    public LocalDate fecha;
    public String ubicacion;
    public String descripcion;
    public boolean usoArmasNucleares; 
    public ArrayList<Pais> paises;
    public Evento(String nombre, LocalDate fecha, String ubicacion, String descripcion, boolean usoArmasNucleares, ArrayList<Pais> paises) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.usoArmasNucleares = usoArmasNucleares;
        this.paises = paises;
    }
    public String toString() {
        return "\nEvento" + "\n nombre = " + nombre + "\n fecha = " + fecha + 
                "\n ubicacion = " + ubicacion + "\n descripcion = " + descripcion
                + "\n paises = " + paises + "\n usoArmasNucleares = " + usoArmasNucleares;
    }
}
class Pais{
    public String nombre;
    public boolean estadoDesarrollo; 
    public int totalHabitantes;
    public int numeroBajas;
    public Pais(String nombre, boolean estadoDesarrollo, int totalHabitantes, int numeroBajas) {
        this.nombre = nombre;
        this.estadoDesarrollo = estadoDesarrollo;
        this.totalHabitantes = totalHabitantes;
        this.numeroBajas = numeroBajas;
    }
    public String toString() {
        return "\n\t- Pais" + "\n\tnombre = " + nombre + "\n\testadoDesarrollo = " 
                + estadoDesarrollo + "\n\ttotalHabitantes = " + totalHabitantes 
                + "\n\tumeroBajas=" + numeroBajas;
    }
}
