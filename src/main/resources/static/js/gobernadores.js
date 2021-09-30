// Call the dataTables jQuery plugin
$(document).ready(function() {
  cargarRecursos();
  cargarActoresPoliticos();
});

async function cargarActoresPoliticos(){

  const request = await fetch('api/gobernadores/listarActoresPoliticos', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("token")
      },
    });
  const actoresPoliticos = await request.json();

  let listadoHtml = "";

  for (let actor of actoresPoliticos) {

      let actorHtml = '<tr><td>'+actor.actor+'</td><td>'+actor.valor+'</td></tr>';
      listadoHtml += actorHtml;
    }
    document.querySelector('#tablaActoresPoliticos tbody').outerHTML = listadoHtml;
}


async function cargarRecursos(){
    const request = await fetch('api/gobernadores/cargarRecursos', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
      });
    const respuesta = await request.json();

    // Cargar nombre de ciudad
    $("#nombreCiudad").html(respuesta.ciudad);

    // Cargar recursos
    $("#caballos_base").html(respuesta.caballos);
    $("#vacas_base").html(respuesta.vacas);
    $("#hierro_base").html(respuesta.hierro);
    $("#vino_base").html(respuesta.vino);
    $("#yerba_base").html(respuesta.yerba);
    $("#estatus").html(respuesta.estatus);
    $("#nivel_industria").html(respuesta.nivel_industria);
    $("#unidades_reclutadas_no_enviadas").html(respuesta.unidades);
    $("#unidades_totales_enviadas").html(respuesta.unidades_enviadas);
    $("#oficiales_contratados").html(respuesta.oficiales);

    // Cargar historial de comercio
    $('#historialComercial').html(respuesta.historial_comercial);

    // Cargar nivel de mision comercial
    cargarNivelMisionComercial();
}

async function cargarNivelMisionComercial(){

    const request = await fetch('api/gobernadores/cargarNivelMisionComercial', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
      });
    const respuesta = await request.json();
    $("#nivel_mision_comercial").html(respuesta.nivel_mision_comercial);
}

async function aumentarIndustria(){

    let datos = {};
    datos.caballos= 1;
    datos.vacas= 1;
    datos.hierro= 1;
    datos.vino= 1;
    datos.yerba= 1;

    const request = await fetch('api/aumentarIndustria', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
        body: JSON.stringify(datos)
      });
    const industria = await request.json();

    cargarRecursos();
}

async function aumentarMisionComercial(){

    let datos = {};
        datos.caballos= 1;
        datos.vacas= 1;
        datos.hierro= 1;
        datos.vino= 1;
        datos.yerba= 1;

    const request = await fetch('api/aumentarMisionComercial', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
        body: JSON.stringify(datos)
      });
    const misionComercial = await request.json();

    cargarRecursos();
}

async function contratarOficiales(){

    let datos = {};
        datos.caballos= 1;
        datos.vacas= 1;
        datos.hierro= 1;
        datos.vino= 1;
        datos.yerba= 1;

    const request = await fetch('api/contratarOficiales/', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
    body: JSON.stringify(datos)
    });
    const oficiales = await request.json();

    cargarRecursos();
}

async function enviarUnidadesAlCapitan(){

    const request = await fetch('api/enviarUnidades/', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
    });

    cargarRecursos();
}