import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Problema3_EjecutorSistemaDepartamento {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Random random = new Random();
        
        Empresa empresa = new Empresa("Corporacion Favorita", "11031875001", 
                "Av. General Enríquez Vía Cotogchoa");
        
        String[] nombresDepartamentos = {"Area Comercial", "Area Industrial", 
            "Area Inmobiliaria", "Area de Responsabilidad Social", "Gobierno Corporativo",
            "Comunicación Externa"};
        
        System.out.println("--- Empresa ---");
        System.out.println(empresa);
        char opcion  = 's';

        while (opcion == 's' || opcion == 'S') {
            
            String nombreDep = nombresDepartamentos[random.nextInt(nombresDepartamentos.length)];
            int empleados = 10 + random.nextInt(201);
            double produccion = 500000 + (random.nextDouble() * 2000000);

            Departamento departamento = new Departamento(nombreDep, empleados, produccion);
            departamento.determinarCategoria();
            empresa.agregarDepartamento(departamento);

            System.out.println(departamento);

            System.out.print("Desea agregar otro departamento? (s/n): ");
            opcion = scanner.next().charAt(0);
        }

    }
}

class Departamento {

    public String nombre;
    public int numeroEmpleados;
    public double produccionAnual;
    public char categoria;

    public Departamento(String nombre, int numeroEmpleados, double produccionAnual) {
        this.nombre = nombre;
        this.numeroEmpleados = numeroEmpleados;
        this.produccionAnual = produccionAnual;

    }

    public char determinarCategoria() {
        if (numeroEmpleados > 20 && produccionAnual > 1000000) {
            categoria = 'A';
            return categoria;
        } else if (numeroEmpleados == 20 && produccionAnual == 1000000) {
            categoria = 'B';
            return categoria;
        } else if (numeroEmpleados == 10 && produccionAnual == 500000) {
            categoria = 'C';
            return categoria;
        } else {
            categoria = 'C';
            return categoria;
        }
    }

    @Override
    public String toString() {
        return "Departamento\n" + "nombre = " + nombre + "\nnumeroEmpleados = " 
                + numeroEmpleados + "\nproduccionAnual = " + produccionAnual 
                + "\ncategoria = " + categoria;
    }

}

class Empresa {

    public String nombre;
    public String ruc;
    public String direccion;
    public ArrayList<Departamento> departamentos;

    public Empresa(String nombre, String ruc, String direccion) {
        this.nombre = nombre;
        this.ruc = ruc;
        this.direccion = direccion;
        this.departamentos = new ArrayList();
    }

    public void agregarDepartamento(Departamento departamento) {
        departamentos.add(departamento);
    }

    @Override
    public String toString() {
        return "Empresa" + "\nnombre = " + nombre + "\nruc = " + ruc + 
                "\ndireccion = " + direccion + "\ndepartamentos = " + departamentos;
    }

}
