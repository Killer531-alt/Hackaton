package hackaton.parte.pkg1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Cita {
    int ID;
    String paciente;
    String tipoServicio;
    double precio;
    String hora;

    // Constructor
    public Cita(int ID, String paciente, String tipoServicio, double precio, String hora) {
        this.ID = ID;
        this.paciente = paciente;
        this.tipoServicio = tipoServicio;
        this.precio = precio;
        this.hora = hora;
    }

    @Override
    public String toString() {
        return ID + " " + paciente + " " + tipoServicio + " " + precio + " " + hora;
    }
}

class BaseDatosCitas {
    List<Cita> citas;

    public BaseDatosCitas() {
        citas = new ArrayList<>();
    }

    public void agendarCita(int ID, String paciente, String tipoServicio, double precio, String hora) {
        if (verificarExistencia(ID)) {
            System.out.println("ERROR");
            return;
        }

        citas.add(new Cita(ID, paciente, tipoServicio, precio, hora));
    }

    public void actualizarCita(int ID, String paciente, String tipoServicio, double precio, String hora) {
        if (!verificarExistencia(ID)) {
            System.out.println("ERROR");
            return;
        }

        Cita citaActualizada = new Cita(ID, paciente, tipoServicio, precio, hora);
        int index = -1;
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).ID == ID) {
                index = i;
                break;
            }
        }

        citas.set(index, citaActualizada);
    }

    public void cancelarCita(int ID) {
        if (!verificarExistencia(ID)) {
            System.out.println("ERROR");
            return;
        }

        Cita citaEliminada = null;
        for (Cita cita : citas) {
            if (cita.ID == ID) {
                citaEliminada = cita;
                break;
            }
        }

        citas.remove(citaEliminada);
    }

    public Cita buscarCita(int ID, String paciente, String tipoServicio, double precio, String hora) {
        for (Cita cita : citas) {
            if (cita.ID == ID && cita.paciente.equals(paciente) && cita.tipoServicio.equals(tipoServicio)
                    && cita.precio == precio && cita.hora.equals(hora)) {
                return cita;
            }
        }

        return null;
    }

  public List<Cita> ordenarCitas(String campo) {
        List<Cita> citasOrdenadas = new ArrayList<>(citas);

        switch (campo) {
            case "ID":
                citasOrdenadas.sort(Comparator.comparingInt(c -> c.ID));
                break;
            case "paciente":
                citasOrdenadas.sort(Comparator.comparing(c -> c.paciente));
                break;
            case "tipoServicio":
                citasOrdenadas.sort(Comparator.comparing(c -> c.tipoServicio));
                break;
            case "precio":
                citasOrdenadas.sort(Comparator.comparingDouble(c -> c.precio));
                break;
            case "hora":
                citasOrdenadas.sort(Comparator.comparing(c -> c.hora));
                break;
            default:
                System.out.println("Campo de ordenación inválido.");
                return citasOrdenadas;
        }

        return citasOrdenadas;
    }

    public Cita buscarCitaPorTipoServicio(String tipoServicio) {
        for (Cita cita : citas) {
            if (cita.tipoServicio.equals(tipoServicio)) {
                return cita;
            }
        }
        return null;
    }

    public void eliminarCita(int ID) {
        Cita citaEliminada = null;
        for (Cita cita : citas) {
            if (cita.ID == ID) {
                citaEliminada = cita;
                break;
            }
        }
        if (citaEliminada != null) {
            citas.remove(citaEliminada);
        }
    }



    public String generarInforme() {
        if (citas.isEmpty()) {
            return "No hay citas en la base de datos.";
        }

        double sumaPrecios = 0.0;
        String pacienteMasCostoso = "";
        String pacienteMenosCostoso = "";
        double precioMasCostoso = Double.MIN_VALUE;
        double precioMenosCostoso = Double.MAX_VALUE;
        int cantidadDecimales = 1;

        for (Cita cita : citas) {
            sumaPrecios += cita.precio;

            if (cita.precio > precioMasCostoso) {
                precioMasCostoso = cita.precio;
                pacienteMasCostoso = cita.paciente;
            }

            if (cita.precio < precioMenosCostoso) {
                precioMenosCostoso = cita.precio;
                pacienteMenosCostoso = cita.paciente;
            }
        }

        double promedioPrecios = sumaPrecios / citas.size();

        BigDecimal bigDecimal = new BigDecimal(String.valueOf(promedioPrecios));

        // Establecer la escala para reducir los decimales (2 en este ejemplo)
        int numeroDecimalesDeseados = 1;
        BigDecimal resultado = bigDecimal.setScale(numeroDecimalesDeseados, BigDecimal.ROUND_DOWN);

        return pacienteMasCostoso + " " + pacienteMenosCostoso + " " + resultado;
    }

    public boolean verificarExistencia(int ID) {
        for (Cita cita : citas) {
            if (cita.ID == ID) {
                return true;
            }
        }

        return false;
    }
}

public class ClinicaUniversitaria {
    public static void main(String[] args) {
        BaseDatosCitas baseDatos = new BaseDatosCitas();
        baseDatos.agendarCita(1, "Maria", "General", 100000.0, "10:00");
        baseDatos.agendarCita(2, "Juan", "Odontologia", 280000.0, "11:00");
        baseDatos.agendarCita(3, "Andres", "Psicologia", 120000.0, "12:00");
        baseDatos.agendarCita(4, "Valentina", "Nutricion", 78000.0, "13:00");
        baseDatos.agendarCita(5, "Sergio", "General", 100000.0, "14:00");
        baseDatos.agendarCita(6, "Laura", "General", 100000.0, "15:00");
        baseDatos.agendarCita(7, "Carlos", "Odontologia", 200000.0, "16:00");
        baseDatos.agendarCita(8, "Sofia", "Psicologia", 120000.0, "17:00");
        baseDatos.agendarCita(9, "Fernando", "Nutricion", 80000.0, "18:00");
        baseDatos.agendarCita(10, "Pedro", "Odontologia", 200000.0, "19:00");
        Scanner scanner = new Scanner(System.in);

        //System.out.println("Ingrese la operación a realizar (AGENDAR, ACTUALIZAR, CANCELAR, BUSQUEDA, ORDENAR):");
        String operacion = scanner.nextLine();

        if (operacion.equals("AGENDAR") || operacion.equals("ACTUALIZAR")) {
            //System.out.println("Ingrese los datos de la cita (ID Paciente TipoServicio Precio Hora):");
            String[] datosCita = scanner.nextLine().split(" ");
            int ID = Integer.parseInt(datosCita[0]);
            String paciente = datosCita[1];
            String tipoServicio = datosCita[2];
            double precio = Double.parseDouble(datosCita[3]);
            String hora = datosCita[4];

            if (operacion.equals("AGENDAR")) {
                baseDatos.agendarCita(ID, paciente, tipoServicio, precio, hora);
            } else {
                baseDatos.actualizarCita(ID, paciente, tipoServicio, precio, hora);
            }
             System.out.println(baseDatos.generarInforme());
        } else if (operacion.equals("CANCELAR")) {
            //System.out.println("Ingrese el ID de la cita a cancelar:");
            String[] datosCita = scanner.nextLine().split(" ");
            int ID = Integer.parseInt(datosCita[0]);
            String paciente = datosCita[1];
            String tipoServicio = datosCita[2];
            double precio = Double.parseDouble(datosCita[3]);
            String hora = datosCita[4];
            baseDatos.cancelarCita(ID);
            System.out.println(baseDatos.generarInforme());
        } else if (operacion.equals("BUSQUEDA")) {
            //System.out.println("Ingrese los datos de la cita a buscar (ID Paciente TipoServicio Precio Hora):");
            String[] datosCita = scanner.nextLine().split(" ");
            int ID = Integer.parseInt(datosCita[0]);
            String paciente = datosCita[1];
            String tipoServicio = datosCita[2];
            double precio = Double.parseDouble(datosCita[3]);
            String hora = datosCita[4];

            Cita citaBuscada = baseDatos.buscarCita(ID, paciente, tipoServicio, precio, hora);
            if (citaBuscada != null) {
                System.out.println(citaBuscada.ID + " " + citaBuscada.paciente + " " + citaBuscada.tipoServicio + " " + citaBuscada.hora);
            } else {
                System.out.println("Cita no encontrada.");
            }
          } else if (operacion.equals("ORDENAR")) {
            String campo = "tipoServicio"; // Ordenar por el campo "tipoServicio"
            String[] datosCita = scanner.nextLine().split(" ");
            int ID = Integer.parseInt(datosCita[0]);
            String paciente = datosCita[1];
            String tipoServicio = datosCita[2];
            double precio = Double.parseDouble(datosCita[3]);
            String hora = datosCita[4];

            Cita citaEntrada = baseDatos.buscarCitaPorTipoServicio(tipoServicio);

            if (citaEntrada != null) {
                System.out.println(citaEntrada);
                baseDatos.eliminarCita(citaEntrada.ID);
            }

            List<Cita> citasOrdenadas = baseDatos.ordenarCitas(campo);
            for (Cita cita : citasOrdenadas) {
                if (cita.tipoServicio.equals(tipoServicio) && cita.ID != citaEntrada.ID) {
                    System.out.println(cita);
                }
            }
        } else {
            System.out.println("Operación inválida. Intente nuevamente.");
        }

        scanner.close();
    }
}