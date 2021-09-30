$(document).ready(function() {
    cargarRecursosCiudades();
    cargarEjercitosCiudades();
    cargarActoresPoliticos();
    cargarCongresos();
});

async function cargarRecursosCiudades(){

    const request = await fetch('api/control/listarRecursos', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
      });
    const recursosCiudades = await request.json();
    localStorage.ciudades = JSON.stringify(recursosCiudades);

    let listadoHtml = "";

    for (let recurso of recursosCiudades) {

        let recursoHtml = '<tr><td><a href="#" onclick="mostrarModalCiudad(`'+recurso.ciudad+'`)" data-toggle="modal" data-target="#editarCiudadModal">'+recurso.ciudad+
                        '</a></td><td>'+recurso.estatus+'</td><td>'+recurso.caballos+'</td><td>'+recurso.vacas+'</td><td>'+recurso.hierro+
                        '</td><td>'+recurso.vino+'</td><td>'+recurso.yerba+'</td><td>'+recurso.nivel_industria+'</td><td>'+recurso.improductividad+
                        '</td><td>'+recurso.congreso+'</td></tr>';
        listadoHtml += recursoHtml;
      }
      document.querySelector('#recursosCiudades tbody').outerHTML = listadoHtml;
}

async function cargarEjercitosCiudades(){

    const request = await fetch('api/control/listarEjercitos', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
      });
    const ejercitosCiudades = await request.json();
    localStorage.ejercitos = JSON.stringify(ejercitosCiudades);

    let listadoHtmlEjercito = "";
    let listadoHtmlMovimientos = "";

    for (let ejercito of ejercitosCiudades) {

        let ejercitoHtml = '<tr><td><a href="#" onclick="mostrarModalEjercito(`'+ejercito.ciudad+'`)" data-toggle="modal" data-target="#editarEjercitoModal">'+ejercito.ciudad+
                        '</a></td><td>'+ejercito.unidades_agrupadas+'</td><td>'+ejercito.oficial_a+'</td><td>'+ejercito.oficial_b+'</td><td>'+ejercito.oficial_c+
                        '</td><td>'+ejercito.oficial_d+'</td><td>'+ejercito.oficial_e+'</td><td>'+ejercito.nivel_mision_comercial+'</td></tr>';
        listadoHtmlEjercito += ejercitoHtml;

        let movimientoHtml = '<tr><td><a href="#" onclick="mostrarModalEjercito(`'+ejercito.ciudad+'`)" data-toggle="modal" data-target="#editarEjercitoModal">'+ejercito.ciudad+
                             '</a></td><td>'+ejercito.movimiento+'</td><td>'+ejercito.destino_1+'</td></tr>';
        listadoHtmlMovimientos += movimientoHtml;
      }
      document.querySelector('#ejercitosCiudades tbody').outerHTML = listadoHtmlEjercito;
      document.querySelector('#movimientosMilitares tbody').outerHTML = listadoHtmlMovimientos;
}

async function cargarActoresPoliticos(){

    const request = await fetch('api/control/listarActoresPoliticos', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
      });
    const actoresPoliticos = await request.json();
    localStorage.actores = JSON.stringify(actoresPoliticos);

    let listadoHtml = "";

    for (let actor of actoresPoliticos) {

        let actorHtml = '<tr><td><a href="#" onclick="mostrarModalActor(`'+actor.actor+'`)" data-toggle="modal" data-target="#editarActorPoliticoModal">'+actor.actor+
                        '</a><td>'+actor.valor+'</td></tr>';
        listadoHtml += actorHtml;
      }
      document.querySelector('#tablaActoresPoliticos tbody').outerHTML = listadoHtml;
}

async function cargarCongresos(){

    const request = await fetch('api/control/listarCongresos', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
      });
    const congresos = await request.json();
    localStorage.congresos = JSON.stringify(congresos);
}

async function pausar(){

  const request = await fetch('api/control/pausar', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("token")
      },
    });
}

async function despausar(){

  const request = await fetch('api/control/despausar', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("token")
      },
    });
}

function mostrarModalCiudad(ciudad){

    pausar();
    $("#ciudad_modal").val(ciudad);

    let ciudades = JSON.parse(localStorage.ciudades);

    for (let encontrada of ciudades){

        if(ciudad == encontrada.ciudad){
            $("#estatus_modal").val(encontrada.estatus);
            $("#caballos_modal").val(encontrada.caballos);
            $("#vacas_modal").val(encontrada.vacas);
            $("#hierro_modal").val(encontrada.hierro);
            $("#vino_modal").val(encontrada.vino);
            $("#yerba_modal").val(encontrada.yerba);
            $("#nivel_industria_modal").val(encontrada.nivel_industria);
            $("#improduc_ciudad_modal").val(encontrada.improductividad);
            $("#congreso_ciudad_modal").val(encontrada.congreso);
        }
    }
}

function mostrarModalEjercito(ciudad){
    pausar();
    $("#ciudad_ej_modal").val(ciudad);

    let ejercitos = JSON.parse(localStorage.ejercitos);

    for (let encontrada of ejercitos){

        if(ciudad == encontrada.ciudad){
             $("#oficial_a_modal").val(encontrada.oficial_a);
             $("#oficial_b_modal").val(encontrada.oficial_b);
             $("#oficial_c_modal").val(encontrada.oficial_c);
             $("#oficial_d_modal").val(encontrada.oficial_d);
             $("#oficial_e_modal").val(encontrada.oficial_e);
             $("#nivel_mision_comercial_modal").val(encontrada.nivel_mision_comercial);
             $("#ubicacion_comercial_modal").val(encontrada.ubicacion_comercial);
             $("#unidades_a_asignar_modal").val(encontrada.unidades_a_asignar);
             $("#unidades_agrupadas_modal").val(encontrada.unidades_agrupadas);
        }
    }
}

function mostrarModalActor(actor){
    pausar();
    $("#actor_politico_modal").val(actor);

    let actores = JSON.parse(localStorage.actores);

    for (let encontrada of actores){

        if(actor == encontrada.actor){
            $("#valor_actor_politico_modal").val(encontrada.valor);
        }
    }
}

// Botón enviar en el modal
async function actualizarCiudad(){

    // Armar el JSON a enviar
    let datos = {};
    datos.ciudad = $("#ciudad_modal").val();
    datos.estatus= $("#estatus_modal").val();
    datos.caballos= $("#caballos_modal").val();
    datos.vacas= $("#vacas_modal").val();
    datos.hierro= $("#hierro_modal").val();
    datos.vino= $("#vino_modal").val();
    datos.yerba= $("#yerba_modal").val();
    datos.nivel_industria= $("#nivel_industria_modal").val();
    datos.improductividad= $("#improduc_ciudad_modal").val();

    // Enviar
    const request = await fetch('api/control/editarCiudad', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
    body: JSON.stringify(datos)
    });

    //Despausar y cargar recursos de nuevo
    despausar();
    cargarRecursosCiudades();

    // Cerrar el modal
    $("#editarCiudadModal").modal("hide");
}

async function actualizarEjercito(){

    // Armar el JSON a enviar
    let datos = {};
    datos.ciudad = $("#ciudad_ej_modal").val();
    datos.oficial_a= $("#oficial_a_modal").val();
    datos.oficial_b= $("#oficial_b_modal").val();
    datos.oficial_c= $("#oficial_c_modal").val();
    datos.oficial_d= $("#oficial_d_modal").val();
    datos.oficial_e= $("#oficial_e_modal").val();
    datos.nivel_mision_comercial= $("#nivel_mision_comercial_modal").val();
    datos.ubicacion_comercial= $("#ubicacion_comercial_modal").val();
    datos.unidades_a_asignar= $("#unidades_a_asignar_modal").val();
    datos.unidades_agrupadas= $("#unidades_agrupadas_modal").val();

    // Enviar
    const request = await fetch('api/control/editarEjercito', {
       method: 'POST',
       headers: {
       'Accept': 'application/json',
       'Content-Type': 'application/json',
       'Authorization': localStorage.getItem("token")
       },
       body: JSON.stringify(datos)
    });

    // Despausar y recargar ejercitos
    despausar();
    cargarEjercitosCiudades();

    // Cerrar el modal
    $("#editarEjercitoModal").modal("hide");
}

async function actualizarActor(){

    // Armar el JSON a enviar
    let datos = {};
    datos.actor = $("#actor_politico_modal").val();
    datos.valor= $("#valor_actor_politico_modal").val();

    // Enviar
    const request = await fetch('api/control/editarActorPolitico', {
       method: 'POST',
       headers: {
       'Accept': 'application/json',
       'Content-Type': 'application/json',
       'Authorization': localStorage.getItem("token")
       },
       body: JSON.stringify(datos)
    });

    // Despausar y recargar actores
    despausar();
    cargarActoresPoliticos();

    // Cerrar el Modal
    $("#editarActorPoliticoModal").modal("hide");
}

async function improductividad(){

    // Armar el JSON a enviar
    let datos = {};
    datos.actor = $("#actor_politico_modal").val();

    // Enviar
    const request = await fetch('api/control/improductividad', {
       method: 'POST',
       headers: {
       'Accept': 'application/json',
       'Content-Type': 'application/json',
       'Authorization': localStorage.getItem("token")
       },
       body: JSON.stringify(datos)
    });
}

function mostrarModalCongreso(id){
    let congresos = JSON.parse(localStorage.congresos);
    for (let encontrada of congresos){
        if(id == encontrada.id){
            $("#id_congreso_modal").val(encontrada.id);
            $("#capital_congreso_modal").val(encontrada.capital);
            $("#sistema_politico_modal").val(encontrada.sistema_de_gobierno);
            $("#cuantos_congreso_modal").val(encontrada.cuantos);
            $("#jefe_ejecutivo_modal").val(encontrada.presidente);
            $("#sistema_economico_modal").val(encontrada.sistema_economico);
        }
    }
}

async function actualizarCongreso(){

    // Armar el JSON a enviar
    let datos = {};
    datos.id = $("#id_congreso_modal").val();
    datos.sistema_de_gobierno = $("#sistema_politico_modal").val();
    datos.cuantos = $("#cuantos_congreso_modal").val();
    datos.presidente = $("#jefe_ejecutivo_modal").val();
    datos.sistema_economico = $("#sistema_economico_modal").val();
    datos.capital = $("#capital_congreso_modal").val();

    // Enviar
    const request = await fetch('api/control/editarCongreso', {
       method: 'POST',
       headers: {
       'Accept': 'application/json',
       'Content-Type': 'application/json',
       'Authorization': localStorage.getItem("token")
       },
       body: JSON.stringify(datos)
    });

    cargarCongresos();
}

async function siguienteTurno(){

    const request = await fetch('api/control/avanzarTurno', {
       method: 'POST',
       headers: {
       'Accept': 'application/json',
       'Content-Type': 'application/json',
       'Authorization': localStorage.getItem("token")
       },
    });
}

async function nuevaFase(){

    // Armar el JSON a enviar
    let datos = {};
    datos.accion = "fase_militar";
    datos.valor = $("#faseMilitarSelect").val();

    // Enviar
    const request = await fetch('api/control/seleccionarFase', {
       method: 'POST',
       headers: {
       'Accept': 'application/json',
       'Content-Type': 'application/json',
       'Authorization': localStorage.getItem("token")
       },
       body: JSON.stringify(datos)
    });

}

async function permitirActualizar(){

    // Armar el JSON a enviar
    let datos = {};
    datos.accion = "actualizar_capitanes";
    datos.valor = $("#permitirActualizarSelect").val()

    // Enviar
    const request = await fetch('api/control/permitirActualizarListaCapitanes', {
       method: 'POST',
       headers: {
       'Accept': 'application/json',
       'Content-Type': 'application/json',
       'Authorization': localStorage.getItem("token")
       },
       body: JSON.stringify(datos)
    });
}