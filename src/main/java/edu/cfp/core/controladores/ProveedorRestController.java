package edu.cfp.core.controladores;

import edu.cfp.core.dominio.Proveedor;
import edu.cfp.core.exceptions.BadRequestException;
import edu.cfp.core.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/proveedores")
public class ProveedorRestController {

    private List<Proveedor> proveedores = new ArrayList<>(Arrays.asList(
            new Proveedor(1, "LA ANONIMA", "123456", "54213321", "LAANONIMA@asd.com", "Argentina", "Chubut", "Esquel", "ASD", "asd", "SAD")
    ));

    @GetMapping
    public ResponseEntity<?> getProveedor() {
        return ResponseEntity.ok(proveedores);
    }
    @GetMapping("/{proveedor}")
    public ResponseEntity<?> getProveedor(@PathVariable String proveedor) {

        if (proveedor.length() != 3) {
            throw new BadRequestException("El usuario no es valido");
        }



        return proveedores.stream()
                .filter(
                        pr -> pr.getNombreEntidad().equalsIgnoreCase(proveedor))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente  No encontrado"));

    }

    @PostMapping
    public ResponseEntity<?> altaProveedor(@RequestBody Proveedor proveedor) {
        // 1. Simular la generación de un ID único si tu lista en memoria no lo tiene
        int nuevoId = proveedores.size() + 1;
        proveedor.setId(nuevoId);

        // 2. Guardar el nuevo cliente en la lista en memoria
        proveedores.add(proveedor);

        // 3. Construir la URI de ubicación correcta (Ej: http://localhost:8080/tienda/api/v1/clientes/Juan)
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{nombre}") // CORRECCIÓN: Usar llaves {} para definir la variable en la URL
                .buildAndExpand(proveedor.getNombreEntidad()) // Reemplaza {nombre} por el valor real
                .toUri();

        // 4. Retornar el código 201 Created con la URL y el objeto insertado
        return ResponseEntity.created(location).body(proveedor);
    }

    @PutMapping
    public ResponseEntity<?> modficacionProveedor(@RequestBody Proveedor proveedor) {
        Proveedor proveedorEncontrado = proveedores.stream().filter(
                        p -> p.getId().equals(proveedor.getId())) // Filtro por ID numérico
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + proveedor.getId()));
        proveedorEncontrado.setNombreEntidad(proveedor.getNombreEntidad());
        proveedorEncontrado.setCuitcuilEntidad(proveedor.getCuitcuilEntidad());
        proveedorEncontrado.setEmailEntidad(proveedor.getEmailEntidad());
        proveedorEncontrado.setTelefonoEntidad(proveedor.getTelefonoEntidad());
        proveedorEncontrado.setPais(proveedor.getPais());
        proveedorEncontrado.setProvincia(proveedor.getProvincia());
        proveedorEncontrado.setCiudad(proveedor.getCiudad());
        return ResponseEntity.ok(proveedorEncontrado);
    }
    @DeleteMapping // Sin "/{nombre}" aquí
    public ResponseEntity<Void> eliminarProveedor(@RequestParam("nombre") String nombre) {
        Proveedor clienteEncontrado = proveedores.stream().filter(
                        c -> c.getNombreEntidad().equalsIgnoreCase(nombre)).
                findFirst().orElseThrow();
        proveedores.remove(clienteEncontrado);
        return ResponseEntity.noContent().build();

    }

}

