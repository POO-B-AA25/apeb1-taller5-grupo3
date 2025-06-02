import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Problema6_EjecutorSistemaDeportivo {
    public static void main(String[] args) {
        Random rand = new Random();
        Scanner scanner = new Scanner(System.in);
        boolean continuarSimulando = true;

        while (continuarSimulando) {
            System.out.println("\nMenu de Simulacion Deportiva");
            System.out.println("------------------------------------");
            System.out.println("1. Simulacion de Evento");
            System.out.println("2. Salir del Simulador");
            System.out.print("Seleccione una opcion: ");
            
            int opcionUsuario = -1;
            if (scanner.hasNextInt()) {
                opcionUsuario = scanner.nextInt();
            }
            scanner.nextLine(); 

            switch (opcionUsuario) {
                case 1:
                    EventoDeportivo evento = new EventoDeportivo("Campeonato "
                            + "Simulado Internacional");
                    System.out.println(" --- Sistema Deportivo --- ");
                    

                    String[] nombresBase = {"Carlos", "Lucia", "Miguel", "Sofia",
                        "David", "Elena", "Javier", "Paula", "Andres", "Valentina"};
                    String[] apellidosBase = {"Garcia", "Rodriguez", "Martinez", 
                        "Lopez", "Perez", "Gomez", "Sanchez", "Diaz", "Torres", "Ramirez"};
                    String[] disciplinasBase = {"Atletismo", "Salto Alto", 
                        "Natacion", "Gimnasia ", "Lanzamiento de Disco"};
                    String[] nombresPruebasBase = {"Clasificatoria", "Ronda Inicial",
                        "Cuartos de Final", "Fase de Grupos", "Semifinal", "Final", "Prueba Individual"};

                    int numeroDeParticipantesAGenerar = 4 + rand.nextInt(5);

                    for (int i = 0; i < numeroDeParticipantesAGenerar; i++) {
                        String nombreCompleto = nombresBase[rand.nextInt(nombresBase.length)] + " " + apellidosBase[rand.nextInt(apellidosBase.length)];
                        String cedula = "CED-SIM-" + String.format("%04d", (evento.hashCode() % 1000) + i + 1000); 
                        String disciplina = disciplinasBase[rand.nextInt(disciplinasBase.length)];

                        Participante nuevoParticipante = new Participante(nombreCompleto, cedula, disciplina);
                        evento.agregarParticipante(nuevoParticipante);
                        System.out.println("-> Participante Registrado: " + nombreCompleto + " (Cedula: " + cedula + ") en " + disciplina);

                        int numeroDePuntajes = 2 + rand.nextInt(3);
                        for (int j = 0; j < numeroDePuntajes; j++) {
                            int puntajeObtenido = 1 + rand.nextInt(10);
                            String nombrePrueba = nombresPruebasBase[rand.nextInt(nombresPruebasBase.length)] + " [" + (j + 1) + "]";
                            evento.registrarPuntaje(cedula, puntajeObtenido, nombrePrueba);
                        }
                        System.out.println("   Se registraron " + numeroDePuntajes + " puntajes|| " + nombreCompleto + ".");
                    }

                    int umbralParaReporte = 6 + rand.nextInt(2);
                    evento.generarReporteGeneral(umbralParaReporte);
                    break;
                case 2:
                    continuarSimulando = false;
                    System.out.println("Gracias por usar el sistema");
                    break;
                default:
                    System.out.println("Opcion no valida.");
                    break;
            }
        }
        scanner.close();
    }
}

class Participante {
    public String nombreCompleto;
    public String cedulaIdentidad;
    public String disciplinaDeportiva;
    public ArrayList<Integer> listaPuntajes;
    public ArrayList<String> nombresPruebas;

    public Participante(String nombre, String cedula, String disciplina) {
        this.nombreCompleto = nombre;
        this.cedulaIdentidad = cedula;
        this.disciplinaDeportiva = disciplina;
        this.listaPuntajes = new ArrayList<>();
        this.nombresPruebas = new ArrayList<>();
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getCedulaIdentidad() {
        return cedulaIdentidad;
    }

    public String getDisciplinaDeportiva() {
        return disciplinaDeportiva;
    }
    
    public ArrayList<Integer> getListaPuntajes() {
        return listaPuntajes;
    }

    public ArrayList<String> getNombresPruebas() {
        return nombresPruebas;
    }

    public void agregarPuntaje(int puntaje, String nombrePrueba) {
        listaPuntajes.add(puntaje);
        nombresPruebas.add(nombrePrueba);
    }

    public double calcularRendimientoPromedio() {
        if (listaPuntajes.isEmpty()) {
            return 0.0;
        }
        double suma = 0;
        for (int p : listaPuntajes) {
            suma += p;
        }
        return suma / listaPuntajes.size();
    }

    public int contarPruebasAprobadas(int umbralMinimo) {
        int aprobadas = 0;
        for (int p : listaPuntajes) {
            if (p >= umbralMinimo) {
                aprobadas++;
            }
        }
        return aprobadas;
    }
}

class EventoDeportivo {
    private String nombreDelEvento;
    public ArrayList<Participante> listaDeParticipantes;

    public EventoDeportivo(String nombreEvento) {
        this.nombreDelEvento = nombreEvento;
        this.listaDeParticipantes = new ArrayList<>();
    }

    public String getNombreEvento() {
        return nombreDelEvento;
    }

    public void agregarParticipante(Participante p) {
        for (Participante existente : listaDeParticipantes) {
            if (existente.getCedulaIdentidad().equals(p.getCedulaIdentidad())) {
                return;
            }
        }
        listaDeParticipantes.add(p);
    }
    
    private Participante buscarParticipantePorCedula(String cedula) {
        for (Participante p : listaDeParticipantes) {
            if (p.getCedulaIdentidad().equals(cedula)) {
                return p;
            }
        }
        return null;
    }

    public void registrarPuntaje(String cedulaParticipante, int puntaje, String nombrePrueba) {
        Participante participante = buscarParticipantePorCedula(cedulaParticipante);
        if (participante != null) {
            participante.agregarPuntaje(puntaje, nombrePrueba);
        }
    }
    
    public void mostrarInformacionParticipante(String cedula) {
        Participante participante = buscarParticipantePorCedula(cedula);
        if (participante != null) {
            System.out.println("\n--- Informacion Detallada del Participante ---");
            System.out.println("Nombre: " + participante.getNombreCompleto());
            System.out.println("Cedula: " + participante.getCedulaIdentidad());
            System.out.println("Disciplina: " + participante.getDisciplinaDeportiva());
            System.out.println("Puntajes Obtenidos (escala 1-10):");
            if (participante.getListaPuntajes().isEmpty()) {
                System.out.println("  Aun no tiene puntajes registrados.");
            } else {
                for (int i = 0; i < participante.getListaPuntajes().size(); i++) {
                    System.out.println("  - Prueba: " + participante.getNombresPruebas().get(i) + ", Puntaje: " + participante.getListaPuntajes().get(i));
                }
            }
            System.out.println("Promedio General: " + String.format("%.2f", participante.calcularRendimientoPromedio()));
        } else {
            System.out.println("Participante con cedula " + cedula + " no encontrado.");
        }
    }

    public void generarReporteGeneral(int umbralAprobacion) {
        System.out.println("\n--- Reporte General de Desempeno del Evento: " + nombreDelEvento + " ---");
        System.out.println("Total de participantes registrados: " + listaDeParticipantes.size());
        System.out.println("Umbral de aprobacion para este reporte (escala 1-10): " + umbralAprobacion + " puntos.");
        if (listaDeParticipantes.isEmpty()) {
            System.out.println("Aun no hay participantes para mostrar en el reporte.");
            return;
        }
        for (Participante p : listaDeParticipantes) {
            System.out.println("--------------------------------------------------");
            System.out.println("Participante: " + p.getNombreCompleto() + " (Cedula: " + p.getCedulaIdentidad() + ")");
            System.out.println("Disciplina: " + p.getDisciplinaDeportiva());
            System.out.println("Promedio de rendimiento (escala 1-10): " + String.format("%.2f", p.calcularRendimientoPromedio()));
            System.out.println("Numero de pruebas aprobadas (umbral " + umbralAprobacion + "): " + p.contarPruebasAprobadas(umbralAprobacion));
            if (!p.getListaPuntajes().isEmpty()) {
                System.out.println("Detalle de puntajes (escala 1-10):");
                for(int i = 0; i < p.getListaPuntajes().size(); i++) {
                     System.out.println("  - " + p.getNombresPruebas().get(i) + ": " + p.getListaPuntajes().get(i));
                }
            } else {
                System.out.println("  Sin puntajes registrados.");
            }
        }
        
    }
}