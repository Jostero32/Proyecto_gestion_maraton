package maraton;

import java.util.Scanner;

public class Menu {

    Gestormaraton gm;

    public Menu() {
        this.gm = new Gestormaraton();
    }

    public void ejecutarMenu() {
        int opcion = -1;
        do {
            opcion = this.menuMaraton();
        } while (opcion != 0 && !this.gm.getMaraton().isMaratonAbierto());
        opcion = -1;
        this.menuCompeticion();
    }

    public void menuCompeticion() {
        int opcion = -1;
        do {

            Scanner scanner = new Scanner(System.in);
            System.out.println("\n=== Menú Competicion ===");
            System.out.println("1. Registrar llegada participante");
            System.out.println("2. Listado de participantes");
            System.out.println("3. Registrar ausencia de participante");
            System.out.println("4. Cerrar Maraton");
            System.out.println("5. Mostrar Reportes");
            System.out.println("0. Salir");

            System.out.print("Ingrese su opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
            }

            switch (opcion) {
                case 1:
                    gm.registrarLlegada();
                    break;
                case 2:
                    gm.listarParticipantes();
                    break;
                case 3:
                    gm.registrarausencia();
                    break;
                case 4:
                    this.gm.getMaraton().cerrarMaraton();
                    break;
                case 5:
                    this.menuReportes();
                    break;
                case 0:
                    System.out.println("Saliendo del programa. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        } while (opcion != 0);
    }

    public void menuReportes() {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n=== Menú de Reportes ===");
            System.out.println("1. Listado de participantes por auspiciante");
            System.out.println("2. Listado de participantes por categoría en orden de llegada");
            System.out.println("3. Listado de inscritos que no participaron");
            System.out.println("4. Listado de quienes no completaron la carrera");
            System.out.println("5. Listado de inscritos");
            System.out.println("0. Regresar");

            System.out.print("Ingrese su opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    gm.listadoporpatrocinador();
                    break;

                case 2:
                    gm.listadoporcategoriallegada();
                    break;
                case 3:
                    gm.listadoinscritosnoparticipar();
                    break;
                case 4:
                    gm.listadoinscritosnoterminar();
                    break;
                case 5:
                    gm.listarParticipantes();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }

        } while (opcion != 0);
    }

    public int menuMaraton() {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;
        System.out.println("\n=== Menú Maratón ===");
        System.out.println("1. Registrar participante");
        System.out.println("2. Modificar Participante");
        System.out.println("3. Eliminar participante");
        System.out.println("4. Listado de participantes");
        System.out.println("5. Iniciar Maraton");
        System.out.println("0. Salir");

        System.out.print("Ingrese su opción: ");
        try {
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
        }

        switch (opcion) {
            case 1:
                Participante p = gm.crearParticipante();
                if (p != null) {
                    this.gm.getMaraton().registrarParticipante(p);
                    System.out.println("Participante registrado exitosamente.");
                } else {
                    System.out.println("Participante no registrado");
                }
                break;
            case 2:
                System.out.println("Cedula de Participante a modificar:");
                String cedulaM = scanner.nextLine();
                if (gm.eliminarParticipante(cedulaM)) {
                    Participante pM = gm.crearParticipante();
                    if (pM != null) {
                        this.gm.getMaraton().registrarParticipante(pM);
                        System.out.println("Participante modificado exitosamente.");
                    } else {
                        System.out.println("Participante no modificado");
                    }
                } else {
                    System.out.println("Participante no existe");
                }
                break;
            case 3:
                System.out.println("Cedula de Participante a eliminar:");
                String cedula = scanner.nextLine();
                if (gm.eliminarParticipante(cedula)) {
                    System.out.println("Participante eliminado");
                } else {
                    System.out.println("Participante no existe");
                }
                break;
            case 4:
                gm.listarParticipantes();
                break;
            case 5:
                this.gm.getMaraton().iniciarMaraton();
                break;
            case 0:
                System.out.println("Saliendo del programa. ¡Hasta luego!");
                break;
            default:
                System.out.println("Opción no válida. Intente de nuevo.");
                break;
        }
        return opcion;
    }

}
