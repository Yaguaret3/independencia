// Call the dataTables jQuery plugin
$(document).ready(function() {
});

async function cargarRecursos(){

    alert("Anda")

    let datos = {};
    datos.ciudad = "Montevideo";

    const request = await fetch('recursos/' + datos.ciudad, {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(datos)
      });
    const recursos = await request.json();
}

async function aumentarIndustria(){

    let datos = {};
    datos.ciudad = "Mendoza";

    const request = await fetch('aumentarIndustria/' + datos.ciudad, {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(datos)
      });
    const recursos = await request.json();
}

async function contratarOficiales(){

    let datos = {};
    datos.ciudad = "Salta";

    const request = await fetch('contratarOficiales/'+datos.ciudad, {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
    body: JSON.stringify(datos)
    });
    const oficiales = await request.json();
}

async function prueba(){

    let datos = {};
    datos.ciudad = "Buenos Aires";
    datos.caballos = 1;
    datos.vacas = 2;
    datos.hierro = 3;
    datos.vino = 4;
    datos.yerba = 5;

    const request = await fetch('prueba', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
    body: JSON.stringify(datos)
    });
    const oficiales = await request.json();
}