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
    const industria = await request.json();
}

async function aumentarMisionComercial(){

    let datos = {};
    datos.ciudad = localStorage.getItem("ciudad");
    datos.caballos = 1;
    datos.yerba = 1;

    const request = await fetch('aumentarMisionComercial', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(datos)
      });
    const misionComercial = await request.json();
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

async function pruebaDameHashDeCiudad(){

    const request = await fetch('pruebaDameHashDeCiudad', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
    });
    //Los otros reciben jsons. Acá para guardar en token recibimos texts
    const respuesta = await request.text();

    localStorage.setItem("ciudad", respuesta);

}

async function pruebaUsandoHashDeCiudad(){

    let datos = {};
    datos.ciudad = localStorage.getItem("ciudad");

    const request = await fetch('pruebaUsandoHashDeCiudad', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
    body: JSON.stringify(datos)
    });
    const respuesta = await request.text();
    alert(respuesta);
}