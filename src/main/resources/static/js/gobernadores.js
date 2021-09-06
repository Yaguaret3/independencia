// Call the dataTables jQuery plugin
$(document).ready(function() {
});

async function cargarRecursos(){

    alert("Anda")


    const request = await fetch('api/recursos', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem("token")
        },
      });
    const recursos = await request.json();
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

    const request = await fetch('api/contratarOficiales/'+datos.ciudad, {
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