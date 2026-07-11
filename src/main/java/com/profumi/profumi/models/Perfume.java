package com.profumi.profumi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "perfumes")
public class Perfume {

    @Id
    private String id;
    private String nombre;
    private String marca;
    private String descripcion;
    private Double precio;
    private String imagenUrl;
    
    // Filtros y Diagnóstico originales
    private List<String> notasOlfativas;
    private String tipoPielIdeal;
    private String climaRecomendado;
    private String ocasion;
    private String genero;
    private String duracion;
    private String proyeccion;
    private Integer stock;
    private String sku;

    // Campos extendidos para Administrador Avanzado
    private String descripcionCorta;
    private String descripcionLarga;
    private String historia;
    private Double precioAnterior;
    private Double descuento;
    private String skuBarra; // Código de barras
    private String estadoDisponibilidad; // Disponible, Agotado, Preventa
    private List<String> categorias;
    private List<String> colecciones;
    private List<String> etiquetas;
    private String tipoFragancia;
    private String familiaOlfativa;
    private String notasSalida;
    private String notasCorazon;
    private String notasFondo;
    private String concentracion;
    private String paisOrigen;
    private Integer anoLanzamiento;
    private String perfumista;
    private List<String> tamanosDisponibles;
    private Double peso;
    private String dimensiones;
    private String garantia;
    private String politicaDevolucion;
    private List<String> imagenesAdicionales;
    private List<String> videos;

    // Campos de Perfumería Avanzada (CMS)
    private String perfilOlfativo;
    private String idealPara;
    private String intensidad;
    private String temporadaRecomendada;
    private String usoRecomendado;
    private String estilo;
    private String edadRecomendada;
    private Boolean destacado;
    private Boolean topVentas;
    private Boolean nuevo;
    private Boolean oferta;
    private Boolean exclusivo;
    private Boolean edicionLimitada;
    private Boolean envioGratis;
    private String autenticidad;
    private String metaTitle;
    private String metaDescription;
    private String friendlyUrl;
    private String keywords;

    public Perfume() {}

    // Getters y Setters originales
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
    public List<String> getNotasOlfativas() { return notasOlfativas; }
    public void setNotasOlfativas(List<String> notasOlfativas) { this.notasOlfativas = notasOlfativas; }
    public String getTipoPielIdeal() { return tipoPielIdeal; }
    public void setTipoPielIdeal(String tipoPielIdeal) { this.tipoPielIdeal = tipoPielIdeal; }
    public String getClimaRecomendado() { return climaRecomendado; }
    public void setClimaRecomendado(String climaRecomendado) { this.climaRecomendado = climaRecomendado; }
    public String getOcasion() { return ocasion; }
    public void setOcasion(String ocasion) { this.ocasion = ocasion; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }
    public String getProyeccion() { return proyeccion; }
    public void setProyeccion(String proyeccion) { this.proyeccion = proyeccion; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    // Getters y Setters de los campos extendidos
    public String getDescripcionCorta() { return descripcionCorta; }
    public void setDescripcionCorta(String descripcionCorta) { this.descripcionCorta = descripcionCorta; }
    public String getDescripcionLarga() { return descripcionLarga; }
    public void setDescripcionLarga(String descripcionLarga) { this.descripcionLarga = descripcionLarga; }
    public String getHistoria() { return historia; }
    public void setHistoria(String historia) { this.historia = historia; }
    public Double getPrecioAnterior() { return precioAnterior; }
    public void setPrecioAnterior(Double precioAnterior) { this.precioAnterior = precioAnterior; }
    public Double getDescuento() { return descuento; }
    public void setDescuento(Double descuento) { this.descuento = descuento; }
    public String getSkuBarra() { return skuBarra; }
    public void setSkuBarra(String skuBarra) { this.skuBarra = skuBarra; }
    public String getEstadoDisponibilidad() { return estadoDisponibilidad; }
    public void setEstadoDisponibilidad(String estadoDisponibilidad) { this.estadoDisponibilidad = estadoDisponibilidad; }
    public List<String> getCategorias() { return categorias; }
    public void setCategorias(List<String> categorias) { this.categorias = categorias; }
    public List<String> getColecciones() { return colecciones; }
    public void setColecciones(List<String> colecciones) { this.colecciones = colecciones; }
    public List<String> getEtiquetas() { return etiquetas; }
    public void setEtiquetas(List<String> etiquetas) { this.etiquetas = etiquetas; }
    public String getTipoFragancia() { return tipoFragancia; }
    public void setTipoFragancia(String tipoFragancia) { this.tipoFragancia = tipoFragancia; }
    public String getFamiliaOlfativa() { return familiaOlfativa; }
    public void setFamiliaOlfativa(String familiaOlfativa) { this.familiaOlfativa = familiaOlfativa; }
    public String getNotasSalida() { return notasSalida; }
    public void setNotasSalida(String notasSalida) { this.notasSalida = notasSalida; }
    public String getNotasCorazon() { return notasCorazon; }
    public void setNotasCorazon(String notasCorazon) { this.notasCorazon = notasCorazon; }
    public String getNotasFondo() { return notasFondo; }
    public void setNotasFondo(String notasFondo) { this.notasFondo = notasFondo; }
    public String getConcentracion() { return concentracion; }
    public void setConcentracion(String concentracion) { this.concentracion = concentracion; }
    public String getPaisOrigen() { return paisOrigen; }
    public void setPaisOrigen(String paisOrigen) { this.paisOrigen = paisOrigen; }
    public Integer getAnoLanzamiento() { return anoLanzamiento; }
    public void setAnoLanzamiento(Integer anoLanzamiento) { this.anoLanzamiento = anoLanzamiento; }
    public String getPerfumista() { return perfumista; }
    public void setPerfumista(String perfumista) { this.perfumista = perfumista; }
    public List<String> getTamanosDisponibles() { return tamanosDisponibles; }
    public void setTamanosDisponibles(List<String> tamanosDisponibles) { this.tamanosDisponibles = tamanosDisponibles; }
    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }
    public String getDimensiones() { return dimensiones; }
    public void setDimensiones(String dimensiones) { this.dimensiones = dimensiones; }
    public String getGarantia() { return garantia; }
    public void setGarantia(String garantia) { this.garantia = garantia; }
    public String getPoliticaDevolucion() { return politicaDevolucion; }
    public void setPoliticaDevolucion(String politicaDevolucion) { this.politicaDevolucion = politicaDevolucion; }
    public List<String> getImagenesAdicionales() { return imagenesAdicionales; }
    public void setImagenesAdicionales(List<String> imagenesAdicionales) { this.imagenesAdicionales = imagenesAdicionales; }
    public List<String> getVideos() { return videos; }
    public void setVideos(List<String> videos) { this.videos = videos; }

    // Getters y Setters de Perfumería Avanzada (CMS)
    public String getPerfilOlfativo() { return perfilOlfativo; }
    public void setPerfilOlfativo(String perfilOlfativo) { this.perfilOlfativo = perfilOlfativo; }
    public String getIdealPara() { return idealPara; }
    public void setIdealPara(String idealPara) { this.idealPara = idealPara; }
    public String getIntensidad() { return intensidad; }
    public void setIntensidad(String intensidad) { this.intensidad = intensidad; }
    public String getTemporadaRecomendada() { return temporadaRecomendada; }
    public void setTemporadaRecomendada(String temporadaRecomendada) { this.temporadaRecomendada = temporadaRecomendada; }
    public String getUsoRecomendado() { return usoRecomendado; }
    public void setUsoRecomendado(String usoRecomendado) { this.usoRecomendado = usoRecomendado; }
    public String getEstilo() { return estilo; }
    public void setEstilo(String estilo) { this.estilo = estilo; }
    public String getEdadRecomendada() { return edadRecomendada; }
    public void setEdadRecomendada(String edadRecomendada) { this.edadRecomendada = edadRecomendada; }
    public Boolean getDestacado() { return destacado; }
    public void setDestacado(Boolean destacado) { this.destacado = destacado; }
    public Boolean getTopVentas() { return topVentas; }
    public void setTopVentas(Boolean topVentas) { this.topVentas = topVentas; }
    public Boolean getNuevo() { return nuevo; }
    public void setNuevo(Boolean nuevo) { this.nuevo = nuevo; }
    public Boolean getOferta() { return oferta; }
    public void setOferta(Boolean oferta) { this.oferta = oferta; }
    public Boolean getExclusivo() { return exclusivo; }
    public void setExclusivo(Boolean exclusivo) { this.exclusivo = exclusivo; }
    public Boolean getEdicionLimitada() { return edicionLimitada; }
    public void setEdicionLimitada(Boolean edicionLimitada) { this.edicionLimitada = edicionLimitada; }
    public Boolean getEnvioGratis() { return envioGratis; }
    public void setEnvioGratis(Boolean envioGratis) { this.envioGratis = envioGratis; }
    public String getAutenticidad() { return autenticidad; }
    public void setAutenticidad(String autenticidad) { this.autenticidad = autenticidad; }
    public String getMetaTitle() { return metaTitle; }
    public void setMetaTitle(String metaTitle) { this.metaTitle = metaTitle; }
    public String getMetaDescription() { return metaDescription; }
    public void setMetaDescription(String metaDescription) { this.metaDescription = metaDescription; }
    public String getFriendlyUrl() { return friendlyUrl; }
    public void setFriendlyUrl(String friendlyUrl) { this.friendlyUrl = friendlyUrl; }
    public String getKeywords() { return keywords; }
    public void setKeywords(String keywords) { this.keywords = keywords; }
}
