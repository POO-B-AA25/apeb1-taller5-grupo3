import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Problema7_EjecutorSistemaFeriaGastronomica {

    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        String[] nombresExpositores = {"Lucinda", "Jose", "Miguel", "Ana"};
        String[] nombresStandsBase = {"BurguerTop", "FastFood", "Delicias", 
            "Postres y mas", "GourmetFit", "Sabor Criollo", "Mariscos Frescos"};

        String[] ingredientesBase1 = {"Cebolla", "Pimiento", "Ajo", "Alino", "Tomate Cherry"};
        String[] ingredientesBase2 = {"Res", "Pollo", "Pescado", "Longaniza", "Camaron"};
        String[] ingredientesBase3 = {"Arroz", "Mote", "Arbeja", "Lentejas", "Papa"};

        ArrayList<Stand> listaStands = new ArrayList<>();
        char opcionMenuPrincipal;

        do {
            System.out.println("\n--- Menu Feria Gastronomica ---");
            System.out.println("1. Registrar Nuevo Stand (con sus platos)");
            System.out.println("2. Listar todos los Stands");
            System.out.println("3. Ver Detalles de un Stand (y sus platos)");
            System.out.println("4. Mostrar Stand con mas Clientes");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");
            opcionMenuPrincipal = scanner.next().charAt(0);
            scanner.nextLine(); 

            switch (opcionMenuPrincipal) {
                case '1': 
                    System.out.println("\n--- Registro de Nuevo Stand ---");
                    ArrayList<Plato> platosDelStand = new ArrayList<>();
                    char opcionPlato = 'S';

                    String nombreStandActual = nombresStandsBase[random.nextInt(nombresStandsBase.length)] + " #" + (listaStands.size() + 1);
                    String expositorActual = nombresExpositores[random.nextInt(nombresExpositores.length)];
                    System.out.println("Configurando Stand: " + nombreStandActual + " (Expositor: " + expositorActual + ")");

                    do {
                        String ingrediente1 = ingredientesBase1[random.nextInt(ingredientesBase1.length)];
                        String ingrediente2 = ingredientesBase2[random.nextInt(ingredientesBase2.length)];
                        String ingrediente3 = ingredientesBase3[random.nextInt(ingredientesBase3.length)];

                        ArrayList<String> ingredientes = new ArrayList<>();
                        ingredientes.add(ingrediente1);
                        ingredientes.add(ingrediente2);
                        ingredientes.add(ingrediente3);

                        String nombreDelPlato = ingrediente2 + " Especial con " + ingrediente1 + " y " + ingrediente3;
                        double precio = 2.50 + (12.50 - 2.50) * random.nextDouble();
                        int disponiblesInicial = 10 + random.nextInt(21);

                        Plato p = new Plato(nombreDelPlato, ingredientes, precio, disponiblesInicial);

                        System.out.println("\nPlato generado para el stand '" + nombreStandActual + "':");
                        System.out.println("  Nombre: " + p.nombrePlato);
                        System.out.println("  Precio: $" + String.format("%.2f", p.precio));
                        System.out.println("  Disponibles: " + p.disponibilidad);

                        int cantidadAComprar;
                        System.out.print("  Cuantos de este plato se vendieron inicialmente? (0 para ninguno): ");
                        cantidadAComprar = scanner.nextInt();
                        scanner.nextLine(); 

                        if (cantidadAComprar < 0) cantidadAComprar = 0;
                        if (cantidadAComprar > p.disponibilidad) cantidadAComprar = p.disponibilidad;

                        if (cantidadAComprar > 0) {
                            p.disponibilidad -= cantidadAComprar;
                            p.vendidos += cantidadAComprar;
                            System.out.println("  " + cantidadAComprar + " unidades de " + p.nombrePlato + " marcadas como vendidas.");
                        }
                        platosDelStand.add(p);

                        System.out.print("Desea agregar otro plato a este Stand? (S/N): ");
                        opcionPlato = scanner.next().charAt(0);
                        scanner.nextLine(); 
                    } while (opcionPlato == 'S' || opcionPlato == 's');

                    Stand stand = new Stand(nombreStandActual, expositorActual, platosDelStand, platosDelStand.size());
                    stand.calcularIngresos();
                    stand.determinarPlatoMasVendido();
                    listaStands.add(stand);
                    System.out.println("\nStand '" + nombreStandActual + "' registrado exitosamente!");
                    break;

                case '2': 
                    if (listaStands.isEmpty()) {
                        System.out.println("\nNo hay Stands registrados aun.");
                    } else {
                        System.out.println("\n--- Lista de Stands Registrados ---");
                        for (int i = 0; i < listaStands.size(); i++) {
                            Stand s = listaStands.get(i);
                            System.out.println((i + 1) + ". " + s.nombreStand + " (Expositor: " + s.expositor + ") - Platos: " + s.platos.size());
                        }
                    }
                    break;

                case '3': 
                    if (listaStands.isEmpty()) {
                        System.out.println("\nNo hay Stands registrados para mostrar detalles.");
                        break;
                    }
                    System.out.println("\n--- Ver Detalles de un Stand ---");
                    System.out.println("Stands disponibles:");
                    for (int i = 0; i < listaStands.size(); i++) {
                        System.out.println((i + 1) + ". " + listaStands.get(i).nombreStand);
                    }
                    System.out.print("Seleccione el numero del Stand para ver sus detalles: ");
                    int indiceStand = scanner.nextInt() - 1;
                    scanner.nextLine(); 

                    if (indiceStand >= 0 && indiceStand < listaStands.size()) {
                        Stand sSeleccionado = listaStands.get(indiceStand);
                        System.out.println("\n--- Detalles del Stand: " + sSeleccionado.nombreStand + " ---");
                        System.out.println(sSeleccionado.toString()); 
                        System.out.println("\n  --- Platos Ofrecidos por " + sSeleccionado.nombreStand + " ---");
                        if (sSeleccionado.platos.isEmpty()){
                            System.out.println("    Este stand no tiene platos registrados aun.");
                        } else {
                            for(Plato plato : sSeleccionado.platos) {
                                System.out.println("    ------------------------------------");
                                System.out.println("    Nombre Plato: " + plato.nombrePlato);
                                System.out.println("    Ingredientes: " + plato.ingredientes);
                                System.out.println("    Precio: $" + String.format("%.2f", plato.precio));
                                System.out.println("    Disponibles: " + plato.disponibilidad);
                                System.out.println("    Vendidos: " + plato.vendidos);
                            }
                            System.out.println("    ------------------------------------");
                        }
                    } else {
                        System.out.println("Seleccion invalida.");
                    }
                    break;

                case '4': 
                    if (listaStands.isEmpty()) {
                        System.out.println("\nNo hay Stands registrados.");
                    } else {
                        Stand standConMasClientes = null;
                        int maxClientes = -1;
                        for (Stand s : listaStands) {
                            if (s.numClientes > maxClientes) { 
                                maxClientes = s.numClientes;
                                standConMasClientes = s;
                            }
                        }
                        System.out.println("\n--- Stand Con mas Clientes (basado en variedad de platos) ---");
                        if (standConMasClientes != null) {
                           System.out.println(standConMasClientes);
                        } else {
                           System.out.println("No se pudo determinar el stand con mas clientes.");
                        }
                    }
                    break;

                case '5':
                    System.out.println("\nSaliendo del sistema de la Feria Gastronomica. Adios!");
                    break;

                default:
                    System.out.println("\nOpcion no valida. Por favor, intente de nuevo.");
                    break;
            }
        } while (opcionMenuPrincipal != '5');

    }
}

class Plato {
    public String nombrePlato;
    public ArrayList<String> ingredientes;
    public double precio;
    public int disponibilidad;
    public int vendidos;

    public Plato() {
        this.ingredientes = new ArrayList<>();
        this.vendidos = 0;
    }

    public Plato(String nombrePlato, ArrayList<String> ingredientes, double precio, int disponibilidadInicial) {
        this.nombrePlato = nombrePlato;
        this.ingredientes = ingredientes;
        this.precio = precio;
        this.disponibilidad = disponibilidadInicial;
        this.vendidos = 0;
    }

    @Override
    public String toString() {
        return String.format("Plato{nombrePlato=%s, ingredientes=%s, precio=%.2f, disponibilidad=%d, vendidos=%d}",
                nombrePlato, ingredientes, precio, disponibilidad, vendidos);
    }
}

class Stand {
    public String nombreStand;
    public String expositor;
    public ArrayList<Plato> platos;
    public double ingresos;
    public Plato platoMasVendido;
    public int numClientes; 

    public Stand() {
        this.platos = new ArrayList<>();
        this.ingresos = 0.0;
        this.numClientes = 0;
    }

    public Stand(String nombreStand, String expositor, ArrayList<Plato> platos, int numClientes) {
        this.nombreStand = nombreStand;
        this.expositor = expositor;
        this.platos = platos;
        this.numClientes = numClientes; 
        this.ingresos = 0.0;
        this.platoMasVendido = null;
    }

    public void añadirPlato(Plato plato) {
        if (this.platos == null) {
            this.platos = new ArrayList<>();
        }
        this.platos.add(plato);
    }

    public double calcularIngresos() {
        double total = 0;
        if (this.platos != null) {
            for (Plato p : this.platos) {
                total += p.vendidos * p.precio;
            }
        }
        this.ingresos = total;
        return this.ingresos;
    }

    public Plato determinarPlatoMasVendido() {
        Plato masVendidoActual = null;
        int maxVentas = -1;
        if (this.platos != null && !this.platos.isEmpty()) {
            for (Plato p : this.platos) {
                if (p.vendidos > maxVentas) {
                    maxVentas = p.vendidos;
                    masVendidoActual = p;
                }
            }
        }
        this.platoMasVendido = masVendidoActual;
        return this.platoMasVendido;
    }

    @Override
    public String toString() {
        StringBuilder platosStr = new StringBuilder();
        if (platos != null && !platos.isEmpty()) {
            for (int i = 0; i < platos.size(); i++) {
                platosStr.append(platos.get(i).nombrePlato);
                if (i < platos.size() - 1) {
                    platosStr.append(", ");
                }
            }
        } else {
            platosStr.append("Ninguno");
        }

        String nombrePlatoMasVendido = (platoMasVendido != null) ? platoMasVendido.nombrePlato : "Ninguno aun";

        return "\n  Nombre Stand: " + nombreStand +
               "\n  Expositor: " + expositor +
               "\n  Platos Ofrecidos: [" + platosStr.toString() + "]" +
               "\n  Ingresos Totales: $" + String.format("%.2f", ingresos) +
               "\n  Plato Mas Vendido: " + nombrePlatoMasVendido +
               "\n  Numero de Tipos de Platos (Clientes proxy): " + numClientes;
    }
}