package ar.edu.utn.frba.dds.modelo;

import ar.edu.utn.frba.dds.interpretes.InterpreteEmpresa;
import ar.edu.utn.frba.dds.lectores.Lector;


import java.util.List;

public class CargadorMasivoDeEmpresas {

    private InterpreteEmpresa interprete;
    private Lector lector;

    public List<Empresa> cargaMasivaDeEmpresas(String rutaArchivo) {
        return lector.leerArchivo(rutaArchivo).stream().
                        map(r -> interprete.interpretarEmpresa(r)).toList();
    }

    public CargadorMasivoDeEmpresas(Lector lector, InterpreteEmpresa interprete){
        this.interprete=interprete;
        this.lector = lector;
    }
}
