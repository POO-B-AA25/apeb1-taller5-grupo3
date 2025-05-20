import java.util.Scanner;
import java.util.Random;

public class Problema1_EjecutorSistemaTienda {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CarritoCompras carrito = new CarritoCompras();
        Random random = new Random();

        String[] nombresProductos = {"Laptop", "Celular", "Tablet", "Audifonos",
            "Cargador", "Smartwatch"};

        while (true) {
            System.out.println("--- Sistema Tienda ---");
            System.out.println("1. Agregar producto al carrito");
            System.out.println("2. Mostrar detalle del carrito");
            System.out.println("3. Calcular total");
            System.out.println("4. Realizar pago");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    String nombreAleatorio = nombresProductos[random.nextInt(nombresProductos.length)];
                    double precioAleatorio = 50 + (2000 - 50) * random.nextDouble();
                    int cantidadAleatoria = 1 + random.nextInt(5);

                    Producto productoEnTienda = carrito.buscarProductoEnTienda(nombreAleatorio);

                    if (productoEnTienda != null) {
                        if (carrito.agregarProducto(productoEnTienda, cantidadAleatoria)) {
                            System.out.println("Se agregaron " + cantidadAleatoria +
                                    " unidades de " + nombreAleatorio + " (Precio: $" +
                                    String.format("%.2f", precioAleatorio) + ") al carrito.");
                        }
                    } else {
                        System.out.println("El producto generado no esta disponible en la tienda.");
                    }
                    break;
                case 2:
                    carrito.mostrarDetalleCompra();
                    break;
                case 3:
                    System.out.println("El total actual del carrito es: $" +
                            String.format("%.2f", carrito.calcularTotal()));
                    break;
                case 4:
                    System.out.print("Ingrese el monto con el que desea pagar: ");
                    double montoPago = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println(carrito.realizarPago(montoPago));
                    break;
                case 5:
                    System.out.println("Gracias por su compra.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opcion no valida, intente de nuevo.");
            }
        }
    }
}

class Producto {
    private String nombre;
    private double precio;
    private int cantidad;

    public Producto() {}

    public Producto(String nombre, double precio, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                '}';
    }
}

class CarritoCompras {
    private Producto[] productos;
    private int[] cantidades;
    private int contadorProductos;
    private final int capacidadMaxima = 100;

    public CarritoCompras() {
        this.productos = new Producto[capacidadMaxima];
        this.cantidades = new int[capacidadMaxima];
        this.contadorProductos = 0;
    }

    public boolean agregarProducto(Producto producto, int cantidad) {
        if (contadorProductos >= capacidadMaxima) {
            System.out.println("Carrito lleno. No se puede agregar mas productos.");
            return false;
        }

        if (producto != null && producto.getCantidad() >= cantidad) {
            Producto productoParaCarrito = new Producto(producto.getNombre(), producto.getPrecio(), 0);
            productos[contadorProductos] = productoParaCarrito;
            cantidades[contadorProductos] = cantidad;
            contadorProductos++;
            producto.setCantidad(producto.getCantidad() - cantidad);
            System.out.println(cantidad + " unidades de \"" + producto.getNombre() +
                    "\" agregadas al carrito.");
            return true;
        } else if (producto == null) {
            System.out.println("El producto no existe en la tienda.");
            return false;
        } else {
            System.out.println("No hay suficiente stock de \"" + producto.getNombre()
                    + "\". Stock disponible: " + producto.getCantidad());
            return false;
        }
    }

    public double calcularTotal() {
        double total = 0;
        for (int i = 0; i < contadorProductos; i++) {
            total += productos[i].getPrecio() * cantidades[i];
        }
        return total;
    }

    public String realizarPago(double montoPagado) {
        double totalCompra = calcularTotal();
        double descuento = 0;
        double montoPromocional = 1000;
        double porcentajeDescuento = 0.10;

        if (totalCompra > montoPromocional) {
            descuento = totalCompra * porcentajeDescuento;
            totalCompra -= descuento;
            System.out.println("Felicidades! Se aplico un descuento del " +
                    (porcentajeDescuento * 100) + "% por superar $" + montoPromocional + ".");
        }

        if (montoPagado >= totalCompra) {
            return "Gracias por su compra. Su cambio es: $" +
                    String.format("%.2f", (montoPagado - totalCompra));
        } else {
            return "Saldo insuficiente. Faltan: $" +
                    String.format("%.2f", (totalCompra - montoPagado));
        }
    }

    public void mostrarDetalleCompra() {
        System.out.println("\n--- Detalle de su compra ---");
        if (contadorProductos == 0) {
            System.out.println("El carrito esta vacio.");
        } else {
            for (int i = 0; i < contadorProductos; i++) {
                System.out.println(productos[i].getNombre() + " x " + cantidades[i] +
                        " = $" + String.format("%.2f", (productos[i].getPrecio() * cantidades[i])));
            }
            System.out.println("--------------------------");
            System.out.println("Total: $" + String.format("%.2f", calcularTotal()));
        }
    }

    public Producto buscarProductoEnTienda(String nombre) {
        if (nombre.equalsIgnoreCase("Laptop")) {
            return new Producto("Laptop", 899.99, 10);
        } else if (nombre.equalsIgnoreCase("Celular")) {
            return new Producto("Celular", 499.99, 15);
        } else if (nombre.equalsIgnoreCase("Tablet")) {
            return new Producto("Tablet", 299.99, 5);
        } else if (nombre.equalsIgnoreCase("Audifonos")) {
            return new Producto("Audifonos", 75.50, 30);
        } else if (nombre.equalsIgnoreCase("Cargador")) {
            return new Producto("Cargador", 25.00, 50);
        } else if (nombre.equalsIgnoreCase("Smartwatch")) {
            return new Producto("Smartwatch", 199.99, 12);
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CarritoCompras {\n");
        for (int i = 0; i < contadorProductos; i++) {
            sb.append("\t").append(productos[i].getNombre()).append(": ")
              .append(cantidades[i]).append(" unidades, Precio unitario: $")
              .append(String.format("%.2f", productos[i].getPrecio())).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
