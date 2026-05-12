package com.empresa.catalogo.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.empresa.catalogo.dto.ProductoRequestDTO;
import com.empresa.catalogo.dto.ProductoResponseDTO;
import com.empresa.catalogo.entity.Producto;
import com.empresa.catalogo.exception.RecursoNoEncontradoException;
import com.empresa.catalogo.factory.ProductoFactory;
import com.empresa.catalogo.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository repo;
    private final ProductoFactory factory;

    public ProductoServiceImpl(ProductoRepository repo, ProductoFactory factory) {
        this.repo = repo;
        this.factory = factory;
    }

    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        Producto p = factory.toEntity(dto);
        return factory.toResponseDTO(repo.save(p));
    }

    public ProductoResponseDTO buscarPorId(Long id) {
        Producto p = repo.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Producto", id));
        return factory.toResponseDTO(p);
    }

    public List<ProductoResponseDTO> listarActivos() {
        return repo.findByActivoTrue().stream()
                .map(factory::toResponseDTO).toList();
    }

    public void eliminar(Long id) {
        buscarPorId(id); // verifica existencia
        repo.deleteById(id);
    }
}
