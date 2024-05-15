package maraton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Gestormaraton {

    Scanner scanner = new Scanner(System.in);
    private Maraton maraton;

    public Gestormaraton() {
        this.maraton = new Maraton();
    }

    public boolean eliminarParticipante(String cedula) {
        Participante p = this.buscarParticipantePorCedula(cedula);
        return this.maraton.eliminarParticipante(p);
    }

    public Participante crearParticipante() {

        System.out.print("Ingrese el número de cédula: ");
        String numeroCedula = scanner.nextLine();

        if (buscarParticipantePorCedula(numeroCedula) != null) {
            System.out.println("El participante con la cédula " + numeroCedula + " ya está registrado.");
            return null;
        }

        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese el apellido: ");
        String apellido = scanner.nextLine();
        int edad = 0;
        try {
            System.out.print("Ingrese la edad: ");
            edad = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            return null;
        }

        System.out.print("Ingrese el sexo: ");
        String sexo = scanner.nextLine();

        Participante nuevoParticipante = new Participante(numeroCedula, nombre, apellido, edad, sexo);

        System.out.print("¿Desea agregar patrocinadores? (S/N): ");
        String respuesta = scanner.nextLine();

        while (respuesta.equalsIgnoreCase("S")) {
            System.out.print("Ingrese el nombre del patrocinador: ");
            String nombrePatrocinador = scanner.nextLine();

            System.out.print("Ingrese el tipo de patrocinador: ");
            String tipoPatrocinador = scanner.nextLine();

            Patrocinador patrocinador = new Patrocinador(nombrePatrocinador, tipoPatrocinador);
            nuevoParticipante.agregarPatrocinador(patrocinador);

            System.out.print("¿Desea agregar otro patrocinador? (S/N): ");
            respuesta = scanner.nextLine();
        }

        return nuevoParticipante;
    }

    public boolean registrarLlegada() {
        if (this.maraton.isMaratonAbierto()) {
            System.out.println("Cedula de Participante a modificar:");
            String cedula = scanner.nextLine();
            Participante p = this.buscarParticipantePorCedula(cedula);
            if (p != null && !p.isAusencia()) {
                p.setHoraLlegada(this.maraton.obtenerHoraActual());
                return true;
            }
            System.out.println("Participante no esta en la competencia");
        } else {
            System.out.println("Competencia Cerrada");
        }
        return false;
        
    }

    public boolean registrarausencia() {
        System.out.println("Cedula del participante: ");
        String cedula = scanner.nextLine();
        Participante p = this.buscarParticipantePorCedula(cedula);
        if (p != null) {
            p.setAusencia(true);
            return true;
        }
        System.out.println("Participante no Existe");
        return false;
    }

    public void listarParticipantes() {
        System.out.println("Reportes de la maraton:");
        for (int i = 1; i <= 3; i++) {
            System.out.println("Categoría " + i + ":");
            ArrayList<Participante> participantesCategoria = obtenerParticipantesCategoria(i);
            for (Participante participante : participantesCategoria) {
                System.out.println(" - " + participante.getNombre() + " " + participante.getApellido());
            }
        }

    }

    public ArrayList<Participante> obtenerParticipantesCategoria(int categoria) {
        switch (categoria) {
            case 1:
                return this.maraton.getParticipantesCategoria1();
            case 2:
                return this.maraton.getParticipantesCategoria2();
            case 3:
                return this.maraton.getParticipantesCategoria3();
            default:
                return null;
        }
    }

    public Participante buscarParticipantePorCedula(String numeroCedula) {
        for (int i = 1; i <= 3; i++) {
            ArrayList<Participante> participantesCategoria;
            switch (i) {
                case 1:
                    participantesCategoria = this.maraton.getParticipantesCategoria1();
                    break;
                case 2:
                    participantesCategoria = this.maraton.getParticipantesCategoria2();
                    break;
                default:
                    participantesCategoria = this.maraton.getParticipantesCategoria3();
                    break;
            }
            if (participantesCategoria != null) {
                for (Participante participante : participantesCategoria) {
                    if (participante.getNumeroCedula().equals(numeroCedula)) {
                        return participante;
                    }
                }
            }
        }
        return null;
    }

    public ArrayList<Participante> obtenerParticipantes() {
        ArrayList<Participante> arraysParticipantes = new ArrayList<>();

        arraysParticipantes.addAll(this.maraton.getParticipantesCategoria1());
        arraysParticipantes.addAll(this.maraton.getParticipantesCategoria2());
        arraysParticipantes.addAll(this.maraton.getParticipantesCategoria3());

        return arraysParticipantes;
    }

    public void listadoporpatrocinador() {
        ArrayList<Participante> participantes = obtenerParticipantes();
        if (participantes != null && !participantes.isEmpty()) {
            System.out.println("Listado de participantes por patrocinador:");

            for (Participante p : participantes) {
                System.out.println("Número de Cédula: " + p.getNumeroCedula());
                System.out.println("Nombre: " + p.getNombre());
                System.out.println("Apellido: " + p.getApellido());
                System.out.println("Edad: " + p.getEdad());
                System.out.println("Sexo: " + p.getSexo());

                ArrayList<Patrocinador> patrocinadores = p.getPatrocinadores();

                if (patrocinadores != null && !patrocinadores.isEmpty()) {
                    System.out.println("Patrocinadores:");

                    for (Patrocinador patrocinador : patrocinadores) {
                        if (patrocinador != null) {
                            System.out.println(" - Nombre: " + patrocinador.getNombre());
                            System.out.println(" - Tipo: " + patrocinador.getTipo());
                        }
                    }
                }
                System.out.println("----------------------------------");
            }
        } else {
            System.out.println("No hay participantes registrados.");
        }
    }

    public void listadoporcategoriallegada() {
        System.out.println("Reportes de la maraton:");
        for (int i = 1; i <= 3; i++) {
            ArrayList<Participante> participantesCategoria = obtenerParticipantesCategoria(i);
            Collections.sort(participantesCategoria);
            System.out.println("Categoria " + i);
            for (Participante participante : participantesCategoria) {
                if (participante.getHoraLlegada() != null) {
                    System.out.println(" - " + participante.getNombre() + " " + participante.getApellido()
                            + " " + participante.getHoraLlegada());
                }
            }
        }
    }

    public ArrayList<Participante> obtenerinscritos() {
        ArrayList<Participante> arraysParticipantes = new ArrayList<>();

        arraysParticipantes.addAll(this.maraton.getParticipantesCategoria1());
        arraysParticipantes.addAll(this.maraton.getParticipantesCategoria2());
        arraysParticipantes.addAll(this.maraton.getParticipantesCategoria3());

        return arraysParticipantes;
    }

    public void listadoinscritosnoparticipar() {
        System.out.println("-----inscritos pero no participaron----");
        ArrayList<Participante> participantesCategoria = obtenerParticipantes();
        for (Participante participante : participantesCategoria) {
            if (participante.isAusencia()) {
                System.out.println(" - " + participante.getNombre() + " " + participante.getApellido());
            }
        }
    }

    public void listadoinscritosnoterminar() {
        System.out.println("-----inscritos que no terminaron----");
        ArrayList<Participante> participantesCategoria = obtenerParticipantes();
        for (Participante participante : participantesCategoria) {
            if (!participante.isAusencia() && participante.getHoraLlegada() == null) {
                System.out.println(" - " + participante.getNombre() + " " + participante.getApellido());
            }
        }
    }

    public Maraton getMaraton() {
        return maraton;
    }

    public void setMaraton(Maraton maraton) {
        this.maraton = maraton;
    }

}
