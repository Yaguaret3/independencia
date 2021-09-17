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

    // Cargar historial de comercio
    document.querySelector('#historialComercial').innerHTML = respuesta.historial_comercial;

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
}