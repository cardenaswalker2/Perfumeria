package com.profumi.profumi.config;

import com.profumi.profumi.models.Perfume;
import com.profumi.profumi.repositories.PerfumeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner initData(PerfumeRepository perfumeRepository, 
                                     com.profumi.profumi.repositories.UsuarioRepository usuarioRepository,
                                     PasswordEncoder passwordEncoder) {

        return args -> {
            System.out.println("============================================");
            System.out.println("EJECUTANDO DATA SEEDER...");
            System.out.println("============================================");
            
            List<Perfume> list = new ArrayList<>();
            if (perfumeRepository.count() == 0) {
                System.out.println("Base de datos de perfumes vacía. Cargando datos iniciales...");

            // 1
            Perfume p1 = new Perfume();
            p1.setNombre("Good Girl Tradicional");
            p1.setMarca("Carolina Herrera");
            p1.setFamiliaOlfativa("Ámbar floral");
            p1.setNotasSalida("Almendra, café, bergamota y limón");
            p1.setNotasCorazon("Jazmín sambac, nardo, raíz de lirio, flor de azahar y rosa búlgara");
            p1.setNotasFondo("Haba tonka, cacao, vainilla, praliné, sándalo, almizcle, ámbar, canela, cachemira y pachulí");
            p1.setPerfilOlfativo("Un aroma intenso, elegante y muy femenino, donde las notas dulces se mezclan con un fondo cálido y sensual de excelente duración.");
            p1.setIdealPara("Citas, cenas elegantes, fiestas nocturnas o cualquier momento en el que quieras destacar con una fragancia inolvidable.");
            p1.setGenero("Mujer");
            p1.setPrecio(1000000.0);
            p1.setStock(10);
            p1.setEstadoDisponibilidad("Disponible");
            p1.setImagenUrl("Sin imagen");
            p1.setSku("CH-GG-01");
            p1.setNotasOlfativas(Arrays.asList("Almendra", "Café", "Vainilla", "Cacao"));
            list.add(p1);

            // 2
            Perfume p2 = new Perfume();
            p2.setNombre("Good Girl Blush Elixir");
            p2.setMarca("Carolina Herrera");
            p2.setFamiliaOlfativa("Ámbar floral");
            p2.setNotasSalida("Bergamota y mandarina");
            p2.setNotasCorazon("Ylang-ylang y rosa");
            p2.setNotasFondo("Vainilla, pachulí y haba tonka");
            p2.setPerfilOlfativo("Dulce, cremoso y sofisticado, con un equilibrio entre delicadeza y sensualidad que envuelve desde la primera aplicación.");
            p2.setIdealPara("Noches especiales, eventos elegantes o para quienes buscan un perfume femenino con mucha personalidad.");
            p2.setGenero("Mujer");
            p2.setPrecio(1000000.0);
            p2.setStock(10);
            p2.setEstadoDisponibilidad("Disponible");
            p2.setImagenUrl("Sin imagen");
            p2.setSku("CH-GGBE-02");
            p2.setNotasOlfativas(Arrays.asList("Bergamota", "Rosa", "Vainilla"));
            list.add(p2);

            // 3
            Perfume p3 = new Perfume();
            p3.setNombre("212 VIP Rosé");
            p3.setMarca("Carolina Herrera");
            p3.setFamiliaOlfativa("Floral frutal");
            p3.setNotasSalida("Champagne rosado y pimienta rosa");
            p3.setNotasCorazon("Flor de durazno y rosa");
            p3.setNotasFondo("Almizcle blanco y notas amaderadas");
            p3.setPerfilOlfativo("Fresco, chispeante y elegante, con un aire juvenil que transmite alegría y sofisticación.");
            p3.setIdealPara("Reuniones sociales, brunch, salidas de fin de semana y celebraciones durante el día.");
            p3.setGenero("Mujer");
            p3.setPrecio(1000000.0);
            p3.setStock(10);
            p3.setEstadoDisponibilidad("Disponible");
            p3.setImagenUrl("Sin imagen");
            p3.setSku("CH-212VR-03");
            p3.setNotasOlfativas(Arrays.asList("Champagne", "Pimienta Rosa", "Flor de durazno"));
            list.add(p3);

            // 4
            Perfume p4 = new Perfume();
            p4.setNombre("212 Men NYC");
            p4.setMarca("Carolina Herrera");
            p4.setFamiliaOlfativa("Almizcle amaderado floral");
            p4.setNotasSalida("Hojas verdes, pomelo, bergamota, lavanda y especias");
            p4.setNotasCorazon("Jengibre, gardenia, violeta y salvia");
            p4.setNotasFondo("Sándalo, incienso, vetiver, almizcle, madera de gaiac y ládano");
            p4.setPerfilOlfativo("Moderno, limpio y masculino, con un equilibrio entre frescura, especias y maderas.");
            p4.setIdealPara("Oficina, universidad, reuniones de trabajo o como fragancia de uso diario.");
            p4.setGenero("Hombre");
            p4.setPrecio(1000000.0);
            p4.setStock(10);
            p4.setEstadoDisponibilidad("Disponible");
            p4.setImagenUrl("Sin imagen");
            p4.setSku("CH-212M-04");
            p4.setNotasOlfativas(Arrays.asList("Hojas verdes", "Pomelo", "Sándalo"));
            list.add(p4);

            // 5
            Perfume p5 = new Perfume();
            p5.setNombre("212 VIP Black Men");
            p5.setMarca("Carolina Herrera");
            p5.setFamiliaOlfativa("Aromática fougère");
            p5.setNotasSalida("Ajenjo, anís e hinojo");
            p5.setNotasCorazon("Lavanda");
            p5.setNotasFondo("Vainilla negra y almizcle");
            p5.setPerfilOlfativo("Seductor, intenso y ligeramente dulce, con una personalidad moderna y muy llamativa.");
            p5.setIdealPara("Vida nocturna, fiestas, bares y ocasiones donde quieras proyectar seguridad.");
            p5.setGenero("Hombre");
            p5.setPrecio(1000000.0);
            p5.setStock(10);
            p5.setEstadoDisponibilidad("Disponible");
            p5.setImagenUrl("Sin imagen");
            p5.setSku("CH-212VB-05");
            p5.setNotasOlfativas(Arrays.asList("Anís", "Lavanda", "Vainilla"));
            list.add(p5);

            // 6
            Perfume p6 = new Perfume();
            p6.setNombre("Invictus Tradicional");
            p6.setMarca("Paco Rabanne");
            p6.setFamiliaOlfativa("Amaderada acuática");
            p6.setNotasSalida("Toronja, notas marinas y mandarina");
            p6.setNotasCorazon("Hoja de laurel y jazmín");
            p6.setNotasFondo("Ámbar gris, madera de gaiac, pachulí y musgo de roble");
            p6.setPerfilOlfativo("Fresco, limpio y energético, con una combinación deportiva y elegante que nunca pasa de moda.");
            p6.setIdealPara("Climas cálidos, actividades al aire libre, gimnasio o uso diario.");
            p6.setGenero("Hombre");
            p6.setPrecio(1000000.0);
            p6.setStock(10);
            p6.setEstadoDisponibilidad("Disponible");
            p6.setImagenUrl("Sin imagen");
            p6.setSku("PR-INV-06");
            p6.setNotasOlfativas(Arrays.asList("Toronja", "Notas marinas", "Ámbar"));
            list.add(p6);

            // 7
            Perfume p7 = new Perfume();
            p7.setNombre("Fame The Couture");
            p7.setMarca("Paco Rabanne");
            p7.setFamiliaOlfativa("Floral amaderada almizclada");
            p7.setNotasSalida("Mango, agua de coco y bergamota");
            p7.setNotasCorazon("Jazmín, incienso y raíz de lirio");
            p7.setNotasFondo("Sándalo, vainilla y almizcle");
            p7.setPerfilOlfativo("Exótico, cremoso y sofisticado, con un carácter moderno que mezcla dulzura y elegancia.");
            p7.setIdealPara("Eventos importantes, cenas, celebraciones o cuando buscas un aroma exclusivo.");
            p7.setGenero("Mujer");
            p7.setPrecio(1000000.0);
            p7.setStock(10);
            p7.setEstadoDisponibilidad("Disponible");
            p7.setImagenUrl("Sin imagen");
            p7.setSku("PR-FAM-07");
            p7.setNotasOlfativas(Arrays.asList("Mango", "Jazmín", "Sándalo"));
            list.add(p7);

            // 8
            Perfume p8 = new Perfume();
            p8.setNombre("Versace Bright Crystal");
            p8.setMarca("Versace");
            p8.setFamiliaOlfativa("Floral frutal");
            p8.setNotasSalida("Yuzu, granada y notas acuáticas");
            p8.setNotasCorazon("Peonía, magnolia y flor de loto");
            p8.setNotasFondo("Almizcle, caoba y ámbar");
            p8.setPerfilOlfativo("Ligero, fresco y delicadamente femenino, perfecto para quienes prefieren aromas limpios y luminosos.");
            p8.setIdealPara("Universidad, oficina, días soleados o una rutina diaria con un toque de elegancia.");
            p8.setGenero("Mujer");
            p8.setPrecio(1000000.0);
            p8.setStock(10);
            p8.setEstadoDisponibilidad("Disponible");
            p8.setImagenUrl("Sin imagen");
            p8.setSku("VR-BC-08");
            p8.setNotasOlfativas(Arrays.asList("Yuzu", "Peonía", "Almizcle"));
            list.add(p8);

            // 9
            Perfume p9 = new Perfume();
            p9.setNombre("Versace Eros Flame");
            p9.setMarca("Versace");
            p9.setFamiliaOlfativa("Amaderada especiada");
            p9.setNotasSalida("Mandarina, naranja amarga, limón, pimienta negra y romero");
            p9.setNotasCorazon("Geranio, rosa y pimienta");
            p9.setNotasFondo("Vainilla, haba tonka, sándalo, cedro, pachulí y musgo");
            p9.setPerfilOlfativo("Potente, cálido y apasionado, con un contraste entre cítricos vibrantes y un fondo dulce y amaderado.");
            p9.setIdealPara("Noches frescas, citas, eventos exclusivos y temporadas de otoño o invierno.");
            p9.setGenero("Hombre");
            p9.setPrecio(1000000.0);
            p9.setStock(10);
            p9.setEstadoDisponibilidad("Disponible");
            p9.setImagenUrl("Sin imagen");
            p9.setSku("VR-EF-09");
            p9.setNotasOlfativas(Arrays.asList("Mandarina", "Rosa", "Vainilla"));
            list.add(p9);

            // 10
            Perfume p10 = new Perfume();
            p10.setNombre("Lacoste Rouge Men");
            p10.setMarca("Lacoste");
            p10.setFamiliaOlfativa("Amaderada especiada");
            p10.setNotasSalida("Té rooibos, mandarina y mango");
            p10.setNotasCorazon("Pimienta negra, jengibre y cardamomo");
            p10.setNotasFondo("Benjuí, acacia y notas amaderadas");
            p10.setPerfilOlfativo("Cálido, moderno y dinámico, con un toque especiado que transmite confianza sin ser excesivo.");
            p10.setIdealPara("Salidas informales, encuentros con amigos o un día lleno de actividades.");
            p10.setGenero("Hombre");
            p10.setPrecio(1000000.0);
            p10.setStock(10);
            p10.setEstadoDisponibilidad("Disponible");
            p10.setImagenUrl("Sin imagen");
            p10.setSku("LC-R-10");
            p10.setNotasOlfativas(Arrays.asList("Mango", "Jengibre", "Maderas"));
            list.add(p10);

            // 11
            Perfume p11 = new Perfume();
            p11.setNombre("Scandal Dama");
            p11.setMarca("Jean Paul Gaultier");
            p11.setFamiliaOlfativa("Chipre floral");
            p11.setNotasSalida("Naranja sanguina y mandarina");
            p11.setNotasCorazon("Miel, gardenia, flor de azahar, jazmín y durazno");
            p11.setNotasFondo("Pachulí, cera de abeja, regaliz y caramelo");
            p11.setPerfilOlfativo("Dulce, adictivo y extremadamente sensual, con una mezcla de miel y flores blancas que deja una estela intensa y muy femenina.");
            p11.setIdealPara("Fiestas, citas románticas, noches de celebración o para quienes disfrutan ser el centro de atención con un aroma inolvidable.");
            p11.setGenero("Mujer");
            p11.setPrecio(1000000.0);
            p11.setStock(10);
            p11.setEstadoDisponibilidad("Disponible");
            p11.setImagenUrl("Sin imagen");
            p11.setSku("JPG-SC-11");
            p11.setNotasOlfativas(Arrays.asList("Miel", "Gardenia", "Pachulí"));
            list.add(p11);

            // 12
            Perfume p12 = new Perfume();
            p12.setNombre("Toy Bubble Gum Dama");
            p12.setMarca("Moschino");
            p12.setFamiliaOlfativa("Floral frutal");
            p12.setNotasSalida("Limón italiano, naranja amarga y cereza confitada");
            p12.setNotasCorazon("Chicle de fresa, rosa búlgara, flor de durazno, arándanos y jengibre");
            p12.setNotasFondo("Almizcle, ambroxan y cedro");
            p12.setPerfilOlfativo("Divertido, dulce y juvenil, con un inconfundible aroma a chicle que se mezcla con frutas y flores para crear una fragancia muy llamativa.");
            p12.setIdealPara("Salidas con amigas, universidad, tardes de compras o para quienes disfrutan perfumes dulces con un estilo fresco y diferente.");
            p12.setGenero("Mujer");
            p12.setPrecio(1000000.0);
            p12.setStock(10);
            p12.setEstadoDisponibilidad("Disponible");
            p12.setImagenUrl("Sin imagen");
            p12.setSku("MS-TBG-12");
            p12.setNotasOlfativas(Arrays.asList("Limón", "Chicle de fresa", "Almizcle"));
            list.add(p12);

            // 13
            Perfume p13 = new Perfume();
            p13.setNombre("Toy Boy Men");
            p13.setMarca("Moschino");
            p13.setFamiliaOlfativa("Amaderada especiada");
            p13.setNotasSalida("Pimienta rosa, pera, nuez moscada, elemí y bergamota");
            p13.setNotasCorazon("Rosa, lino, clavo de olor y magnolia");
            p13.setNotasFondo("Vetiver de Haití, cachemira, sándalo, ámbar y Sylkolide");
            p13.setPerfilOlfativo("Elegante, atrevido y moderno. La rosa masculina se combina con especias y maderas para crear un aroma muy original.");
            p13.setIdealPara("Cenas, eventos importantes o para hombres que buscan un perfume fuera de lo unconventional.");
            p13.setGenero("Hombre");
            p13.setPrecio(1000000.0);
            p13.setStock(10);
            p13.setEstadoDisponibilidad("Disponible");
            p13.setImagenUrl("Sin imagen");
            p13.setSku("MS-TB-13");
            p13.setNotasOlfativas(Arrays.asList("Pimienta rosa", "Rosa", "Sándalo"));
            list.add(p13);

            // 14
            Perfume p14 = new Perfume();
            p14.setNombre("Sauvage Elixir Men");
            p14.setMarca("Dior");
            p14.setFamiliaOlfativa("Aromática");
            p14.setNotasSalida("Canela, nuez moscada, cardamomo y toronja");
            p14.setNotasCorazon("Lavanda");
            p14.setNotasFondo("Regaliz, sándalo, ámbar, pachulí y vetiver");
            p14.setPerfilOlfativo("Muy intenso, refinado y de gran proyección. Un aroma especiado con un fondo amaderado que transmite elegancia y autoridad.");
            p14.setIdealPara("Noches especiales, reuniones formales, eventos exclusivos o climas frescos donde pueda lucirse al máximo.");
            p14.setGenero("Hombre");
            p14.setPrecio(1000000.0);
            p14.setStock(10);
            p14.setEstadoDisponibilidad("Disponible");
            p14.setImagenUrl("Sin imagen");
            p14.setSku("DI-SE-14");
            p14.setNotasOlfativas(Arrays.asList("Canela", "Lavanda", "Sándalo"));
            list.add(p14);

            // 15
            Perfume p15 = new Perfume();
            p15.setNombre("J’adore Dama");
            p15.setMarca("Dior");
            p15.setFamiliaOlfativa("Floral");
            p15.setNotasSalida("Bergamota, pera, melón, durazno y magnolia");
            p15.setNotasCorazon("Jazmín, orquídea, nardos, rosa, violeta y lirio del valle");
            p15.setNotasFondo("Cedro, vainilla, almizcle y mora");
            p15.setPerfilOlfativo("Sofisticado, femenino y luminoso, con un elegante bouquet floral que transmite lujo y delicadeza.");
            p15.setIdealPara("Oficina, reuniones elegantes, celebraciones familiares o cualquier ocasión donde quieras proyectar clase y feminidad.");
            p15.setGenero("Mujer");
            p15.setPrecio(1000000.0);
            p15.setStock(10);
            p15.setEstadoDisponibilidad("Disponible");
            p15.setImagenUrl("Sin imagen");
            p15.setSku("DI-JA-15");
            p15.setNotasOlfativas(Arrays.asList("Bergamota", "Jazmín", "Vainilla"));
            list.add(p15);

            // 16
            Perfume p16 = new Perfume();
            p16.setNombre("Miss Dior");
            p16.setMarca("Dior");
            p16.setFamiliaOlfativa("Floral");
            p16.setNotasSalida("Iris, peonía y lirio del valle");
            p16.setNotasCorazon("Rosa, chabacano y durazno");
            p16.setNotasFondo("Vainilla, almizcle, haba tonka, benjuí y sándalo");
            p16.setPerfilOlfativo("Romántico, delicado y refinado, con flores suaves envueltas en un fondo cremoso y ligeramente dulce.");
            p16.setIdealPara("Citas románticas, almuerzos, eventos de día o para quienes prefieren perfumes elegantes sin ser intensos.");
            p16.setGenero("Mujer");
            p16.setPrecio(1000000.0);
            p16.setStock(10);
            p16.setEstadoDisponibilidad("Disponible");
            p16.setImagenUrl("Sin imagen");
            p16.setSku("DI-MD-16");
            p16.setNotasOlfativas(Arrays.asList("Iris", "Rosa", "Vainilla"));
            list.add(p16);

            // 17
            Perfume p17 = new Perfume();
            p17.setNombre("Light Blue Dama");
            p17.setMarca("Dolce & Gabbana");
            p17.setFamiliaOlfativa("Floral frutal");
            p17.setNotasSalida("Limón siciliano, manzana verde, cedro y campanilla");
            p17.setNotasCorazon("Jazmín, bambú y rosa blanca");
            p17.setNotasFondo("Cedro, almizcle y ámbar");
            p17.setPerfilOlfativo("Refrescante, limpio y vibrante, perfecto para transmitir una sensación de frescura durante todo el día.");
            p17.setIdealPara("Días calurosos, vacaciones, playa, oficina o uso diario gracias a su frescura natural.");
            p17.setGenero("Mujer");
            p17.setPrecio(1000000.0);
            p17.setStock(10);
            p17.setEstadoDisponibilidad("Disponible");
            p17.setImagenUrl("Sin imagen");
            p17.setSku("DG-LB-17");
            p17.setNotasOlfativas(Arrays.asList("Limón", "Bambú", "Cedro"));
            list.add(p17);

            // 18
            Perfume p18 = new Perfume();
            p18.setNombre("Bvlgari Omnia Amethyste");
            p18.setMarca("Bvlgari");
            p18.setFamiliaOlfativa("Floral amaderada almizclada");
            p18.setNotasSalida("Toronja rosada y notas verdes");
            p18.setNotasCorazon("Iris y rosa búlgara");
            p18.setNotasFondo("Heliotropo y notas amaderadas");
            p18.setPerfilOlfativo("Elegante, delicado y muy limpio, con una sensación empolvada y sofisticada que resulta sumamente femenina.");
            p18.setIdealPara("Ambientes profesionales, reuniones importantes o para quienes buscan un perfume discreto pero distinguido.");
            p18.setGenero("Mujer");
            p18.setPrecio(1000000.0);
            p18.setStock(10);
            p18.setEstadoDisponibilidad("Disponible");
            p18.setImagenUrl("Sin imagen");
            p18.setSku("BV-OA-18");
            p18.setNotasOlfativas(Arrays.asList("Toronja", "Iris", "Heliotropo"));
            list.add(p18);

            // 19
            Perfume p19 = new Perfume();
            p19.setNombre("Bvlgari Omnia Coral Dama");
            p19.setMarca("Bvlgari");
            p19.setFamiliaOlfativa("Floral frutal");
            p19.setNotasSalida("Bergamota y bayas de goji");
            p19.setNotasCorazon("Hibisco, nenúfar y granada");
            p19.setNotasFondo("Cedro y almizcle");
            p19.setPerfilOlfativo("Alegre, luminoso y femenino, con frutas jugosas y flores frescas que evocan un día de verano.");
            p19.setIdealPara("Paseos al aire libre, viajes, fines de semana o cualquier momento donde quieras irradiar frescura.");
            p19.setGenero("Mujer");
            p19.setPrecio(1000000.0);
            p19.setStock(10);
            p19.setEstadoDisponibilidad("Disponible");
            p19.setImagenUrl("Sin imagen");
            p19.setSku("BV-OC-19");
            p19.setNotasOlfativas(Arrays.asList("Bergamota", "Hibisco", "Cedro"));
            list.add(p19);

            // 20
            Perfume p20 = new Perfume();
            p20.setNombre("Bvlgari Omnia Crystalline Dama");
            p20.setMarca("Bvlgari");
            p20.setFamiliaOlfativa("Floral acuática");
            p20.setNotasSalida("Bambú y pera asiática");
            p20.setNotasCorazon("Flor de loto, hojas de té y casia");
            p20.setNotasFondo("Almizcle, madera de gaiac y musgo de roble");
            p20.setPerfilOlfativo("Suave, cristalino y elegante, con una frescura acuática muy femenina y delicada.");
            p20.setIdealPara("Jornadas laborales, universidad, reuniones de día o para quienes aman los aromas limpios y sutiles.");
            p20.setGenero("Mujer");
            p20.setPrecio(1000000.0);
            p20.setStock(10);
            p20.setEstadoDisponibilidad("Disponible");
            p20.setImagenUrl("Sin imagen");
            p20.setSku("BV-OCR-20");
            p20.setNotasOlfativas(Arrays.asList("Bambú", "Flor de loto", "Almizcle"));
            list.add(p20);

            // 21
            Perfume p21 = new Perfume();
            p21.setNombre("Bleu de Chanel Men");
            p21.setMarca("Chanel");
            p21.setFamiliaOlfativa("Amaderada aromática");
            p21.setNotasSalida("Toronja, limón, menta y pimienta rosa");
            p21.setNotasCorazon("Jengibre, nuez moscada, jazmín y melón");
            p21.setNotasFondo("Incienso, sándalo, cedro, pachulí, vetiver y ládano");
            p21.setPerfilOlfativo("Elegante, versátil y sofisticado, con un equilibrio perfecto entre frescura cítrica, especias y maderas.");
            p21.setIdealPara("Ejecutivos, reuniones de negocios, cenas, eventos sociales o como perfume insignia para cualquier ocasión.");
            p21.setGenero("Hombre");
            p21.setPrecio(1000000.0);
            p21.setStock(10);
            p21.setEstadoDisponibilidad("Disponible");
            p21.setImagenUrl("Sin imagen");
            p21.setSku("CH-BC-21");
            p21.setNotasOlfativas(Arrays.asList("Toronja", "Jengibre", "Incienso"));
            list.add(p21);

            // 22
            Perfume p22 = new Perfume();
            p22.setNombre("Coco Chanel (Coco Mademoiselle)");
            p22.setMarca("Chanel");
            p22.setFamiliaOlfativa("Ámbar floral");
            p22.setNotasSalida("Naranja, mandarina, bergamota y flor de azahar");
            p22.setNotasCorazon("Rosa turca, jazmín, mimosa y ylang-ylang");
            p22.setNotasFondo("Pachulí, vainilla, haba tonka, almizcle blanco, vetiver y opopónaco");
            p22.setPerfilOlfativo("Chic, elegante y sensual, con una combinación perfecta entre cítricos brillantes, flores refinadas y un fondo cálido de larga duración.");
            p22.setIdealPara("Mujeres que desean un perfume versátil que acompañe desde una jornada laboral hasta una cena elegante sin perder sofisticación.");
            p22.setGenero("Mujer");
            p22.setPrecio(1000000.0);
            p22.setStock(10);
            p22.setEstadoDisponibilidad("Disponible");
            p22.setImagenUrl("Sin imagen");
            p22.setSku("CH-CM-22");
            p22.setNotasOlfativas(Arrays.asList("Naranja", "Rosa", "Pachulí"));
            list.add(p22);

            // 23
            Perfume p23 = new Perfume();
            p23.setNombre("Chanel Chance Eau Tendre");
            p23.setMarca("Chanel");
            p23.setFamiliaOlfativa("Floral frutal");
            p23.setNotasSalida("Toronja y membrillo");
            p23.setNotasCorazon("Jazmín y jacinto");
            p23.setNotasFondo("Almizcle blanco, iris, cedro y ámbar");
            p23.setPerfilOlfativo("Fresco, delicado y romántico, con un equilibrio entre frutas jugosas y flores suaves que transmite juventud y elegancia.");
            p23.setIdealPara("Primeras citas, días de primavera, universidad, oficina o para quienes buscan una fragancia ligera que reciba cumplidos donde vaya.");
            p23.setGenero("Mujer");
            p23.setPrecio(1000000.0);
            p23.setStock(10);
            p23.setEstadoDisponibilidad("Disponible");
            p23.setImagenUrl("Sin imagen");
            p23.setSku("CH-CCT-23");
            p23.setNotasOlfativas(Arrays.asList("Toronja", "Jazmín", "Almizcle"));
            list.add(p23);

            // 24
            Perfume p24 = new Perfume();
            p24.setNombre("Diesel Plus Plus");
            p24.setMarca("Diesel");
            p24.setFamiliaOlfativa("Oriental floral");
            p24.setNotasSalida("Piña, naranja amarga, lirio de los valles, azucena, frutas y bergamota");
            p24.setNotasCorazon("Jazmín, violeta, lirio, rosa, orquídea y notas florales");
            p24.setNotasFondo("Vainilla, almizcle, sándalo, ámbar, cedro y vetiver");
            p24.setPerfilOlfativo("Dulce, cremoso y envolvente, con un carácter clásico que combina flores blancas y vainilla para un aroma muy femenino.");
            p24.setIdealPara("Uso diario, tardes frescas o para quienes disfrutan fragancias dulces con un toque elegante.");
            p24.setGenero("Mujer");
            p24.setPrecio(1000000.0);
            p24.setStock(10);
            p24.setEstadoDisponibilidad("Disponible");
            p24.setImagenUrl("Sin imagen");
            p24.setSku("DS-PP-24");
            p24.setNotasOlfativas(Arrays.asList("Piña", "Jazmín", "Vainilla"));
            list.add(p24);

            // 25
            Perfume p25 = new Perfume();
            p25.setNombre("Love Me Eau de Parfum");
            p25.setMarca("Tous");
            p25.setFamiliaOlfativa("Floral afrutada");
            p25.setNotasSalida("Lichi, toronja rosada y pimienta rosa");
            p25.setNotasCorazon("Peonía, rosa y jazmín");
            p25.setNotasFondo("Cedro, cachemira y almizcle");
            p25.setPerfilOlfativo("Radiante, romántico y sofisticado, con una mezcla de frutas jugosas y flores delicadas.");
            p25.setIdealPara("Citas, reuniones familiares, almuerzos especiales o cualquier ocasión donde quieras proyectar dulzura y elegancia.");
            p25.setGenero("Mujer");
            p25.setPrecio(1000000.0);
            p25.setStock(10);
            p25.setEstadoDisponibilidad("Disponible");
            p25.setImagenUrl("Sin imagen");
            p25.setSku("TS-LM-25");
            p25.setNotasOlfativas(Arrays.asList("Lichi", "Peonía", "Cedro"));
            list.add(p25);

            // 26
            Perfume p26 = new Perfume();
            p26.setNombre("Blue Seduction Men");
            p26.setMarca("Antonio Banderas");
            p26.setFamiliaOlfativa("Aromática acuática");
            p26.setNotasSalida("Bergamota, menta, grosella negra y melón");
            p26.setNotasCorazon("Cardamomo, nuez moscada, manzana verde y agua de mar");
            p26.setNotasFondo("Ámbar, almizcle, maderas y capuchino");
            p26.setPerfilOlfativo("Fresco, limpio y juvenil, con un equilibrio entre notas acuáticas y un fondo ligeramente dulce.");
            p26.setIdealPara("Climas cálidos, universidad, oficina o como compañero perfecto para el día a día.");
            p26.setGenero("Hombre");
            p26.setPrecio(1000000.0);
            p26.setStock(10);
            p26.setEstadoDisponibilidad("Disponible");
            p26.setImagenUrl("Sin imagen");
            p26.setSku("AB-BS-26");
            p26.setNotasOlfativas(Arrays.asList("Menta", "Cardamomo", "Ámbar"));
            list.add(p26);

            // 27
            Perfume p27 = new Perfume();
            p27.setNombre("Valentino Uomo Born in Roma Intense");
            p27.setMarca("Valentino");
            p27.setFamiliaOlfativa("Ámbar vainilla");
            p27.setNotasSalida("Vainilla");
            p27.setNotasCorazon("Lavandín");
            p27.setNotasFondo("Vetiver");
            p27.setPerfilOlfativo("Elegante, seductor y moderno. La vainilla intensa se equilibra con un fondo amaderado que transmite lujo y masculinidad.");
            p27.setIdealPara("Noches especiales, eventos de etiqueta, cenas románticas o cuando quieres dejar una impresión memorable.");
            p27.setGenero("Hombre");
            p27.setPrecio(1000000.0);
            p27.setStock(10);
            p27.setEstadoDisponibilidad("Disponible");
            p27.setImagenUrl("Sin imagen");
            p27.setSku("VL-UBR-27");
            p27.setNotasOlfativas(Arrays.asList("Vainilla", "Lavandín", "Vetiver"));
            list.add(p27);

            // 28
            Perfume p28 = new Perfume();
            p28.setNombre("Valentino Donna");
            p28.setMarca("Valentino");
            p28.setFamiliaOlfativa("Ámbar floral");
            p28.setNotasSalida("Bergamota");
            p28.setNotasCorazon("Jazmín sambac");
            p28.setNotasFondo("Vainilla bourbon");
            p28.setPerfilOlfativo("Refinado, femenino y envolvente, con una vainilla elegante que aporta calidez sin resultar excesiva.");
            p28.setIdealPara("Celebraciones, cenas elegantes, reuniones importantes o momentos donde la sofisticación sea protagonista.");
            p28.setGenero("Mujer");
            p28.setPrecio(1000000.0);
            p28.setStock(10);
            p28.setEstadoDisponibilidad("Disponible");
            p28.setImagenUrl("Sin imagen");
            p28.setSku("VL-VD-28");
            p28.setNotasOlfativas(Arrays.asList("Bergamota", "Jazmín", "Vainilla"));
            list.add(p28);

            // 29
            Perfume p29 = new Perfume();
            p29.setNombre("Yara Rosada Tradicional");
            p29.setMarca("Lattafa");
            p29.setFamiliaOlfativa("Ámbar vainilla");
            p29.setNotasSalida("Orquídea, heliotropo y mandarina");
            p29.setNotasCorazon("Acorde gourmand y frutas tropicales");
            p29.setNotasFondo("Vainilla, almizcle y sándalo");
            p29.setPerfilOlfativo("Dulce, cremoso y muy femenino, con un toque tropical que resulta adictivo y acogedor.");
            p29.setIdealPara("Salidas casuales, tardes de compras, universidad o para quienes aman los perfumes dulces de larga duración.");
            p29.setGenero("Mujer");
            p29.setPrecio(1000000.0);
            p29.setStock(10);
            p29.setEstadoDisponibilidad("Disponible");
            p29.setImagenUrl("Sin imagen");
            p29.setSku("LT-YR-29");
            p29.setNotasOlfativas(Arrays.asList("Orquídea", "Frutas tropicales", "Vainilla"));
            list.add(p29);

            // 30
            Perfume p30 = new Perfume();
            p30.setNombre("Yara Elixir");
            p30.setMarca("Lattafa");
            p30.setFamiliaOlfativa("Floral frutal gourmand");
            p30.setNotasSalida("Mandarina, pera y pimienta rosa");
            p30.setNotasCorazon("Jazmín, flor de azahar y rosa");
            p30.setNotasFondo("Vainilla, ámbar, almizcle y maderas");
            p30.setPerfilOlfativo("Más intenso y sofisticado que el Yara tradicional, con una dulzura cremosa y una estela muy envolvente.");
            p30.setIdealPara("Noches, de celebración, reuniones especiales o cuando buscas un perfume que atraiga cumplidos.");
            p30.setGenero("Mujer");
            p30.setPrecio(1000000.0);
            p30.setStock(10);
            p30.setEstadoDisponibilidad("Disponible");
            p30.setImagenUrl("Sin imagen");
            p30.setSku("LT-YE-30");
            p30.setNotasOlfativas(Arrays.asList("Mandarina", "Jazmín", "Vainilla"));
            list.add(p30);

            // 31
            Perfume p31 = new Perfume();
            p31.setNombre("Thank U, Next Tradicional");
            p31.setMarca("Ariana Grande");
            p31.setFamiliaOlfativa("Floral frutal gourmand");
            p31.setNotasSalida("Pera y frambuesa");
            p31.setNotasCorazon("Coco y rosa rosada");
            p31.setNotasFondo("Macarons, almizcle y madera de cachemira");
            p31.setPerfilOlfativo("Dulce, juvenil y divertido, con un delicioso toque de coco y postres que lo hace muy llamativo.");
            p31.setIdealPara("Salidas con amigas, conciertos, fines de semana o para quienes disfrutan aromas alegres y modernos.");
            p31.setGenero("Mujer");
            p31.setPrecio(1000000.0);
            p31.setStock(10);
            p31.setEstadoDisponibilidad("Disponible");
            p31.setImagenUrl("Sin imagen");
            p31.setSku("AG-TUN-31");
            p31.setNotasOlfativas(Arrays.asList("Pera", "Coco", "Macarons"));
            list.add(p31);

            // 32
            Perfume p32 = new Perfume();
            p32.setNombre("Paris Hilton Dama");
            p32.setMarca("Paris Hilton");
            p32.setFamiliaOlfativa("Floral frutal");
            p32.setNotasSalida("Manzana, melón y durazno");
            p32.setNotasCorazon("Fresia, jazmín, lirio y mimosa");
            p32.setNotasFondo("Almizcle, ylang-ylang, sándalo y musgo de roble");
            p32.setPerfilOlfativo("Fresco, femenino y juvenil, con una mezcla de frutas y flores que transmite energía y naturalidad.");
            p32.setIdealPara("Uso diario, universidad, paseos al aire libre o cualquier momento donde busques una fragancia ligera y versátil.");
            p32.setGenero("Mujer");
            p32.setPrecio(1000000.0);
            p32.setStock(10);
            p32.setEstadoDisponibilidad("Disponible");
            p32.setImagenUrl("Sin imagen");
            p32.setSku("PH-PH-32");
            p32.setNotasOlfativas(Arrays.asList("Manzana", "Fresia", "Almizcle"));
            list.add(p32);

            // 33
            Perfume p33 = new Perfume();
            p33.setNombre("Can Can Tradicional");
            p33.setMarca("Paris Hilton");
            p33.setFamiliaOlfativa("Floral frutal");
            p33.setNotasSalida("Clementina, grosella negra y nectarina");
            p33.setNotasCorazon("Flor de azahar y orquídea silvestre");
            p33.setNotasFondo("Ámbar, almizcle y notas amaderadas");
            p33.setPerfilOlfativo("Coqueto, sensual y femenino, con una dulzura elegante que resulta muy fácil de llevar.");
            p33.setIdealPara("Citas, reuniones sociales, cenas informales o para añadir un toque glamuroso a cualquier ocasión.");
            p33.setGenero("Mujer");
            p33.setPrecio(1000000.0);
            p33.setStock(10);
            p33.setEstadoDisponibilidad("Disponible");
            p33.setImagenUrl("Sin imagen");
            p33.setSku("PH-CC-33");
            p33.setNotasOlfativas(Arrays.asList("Clementina", "Flor de azahar", "Ámbar"));
            list.add(p33);

            // 34
            Perfume p34 = new Perfume();
            p34.setNombre("Amber Oud Gold Edition");
            p34.setMarca("Al Haramain");
            p34.setFamiliaOlfativa("Ámbar vainilla");
            p34.setNotasSalida("Bergamota y notas verdes");
            p34.setNotasCorazon("Melón, piña, ámbar y acordes dulces");
            p34.setNotasFondo("Vainilla, almizcle y notas amaderadas");
            p34.setPerfilOlfativo("Exuberante, dulce y extremadamente duradero. Su combinación de frutas tropicales, vainilla y ámbar crea una estela lujosa que no pasa desapercibida.");
            p34.setIdealPara("Eventos exclusivos, noches de gala, celebraciones importantes o para quienes buscan un perfume de gran presencia y excelente fijación.");
            p34.setGenero("Uniséx");
            p34.setPrecio(1000000.0);
            p34.setStock(10);
            p34.setEstadoDisponibilidad("Disponible");
            p34.setImagenUrl("Sin imagen");
            p34.setSku("AH-AO-34");
            p34.setNotasOlfativas(Arrays.asList("Bergamota", "Melón", "Vainilla"));
            list.add(p34);

            // KITS DE DESCUBRIMIENTO REALES
            // 35: Kit Elegancia Femenina
            Perfume k1 = new Perfume();
            k1.setNombre("Kit Elegancia Femenina");
            k1.setMarca("Profumi Selection");
            k1.setFamiliaOlfativa("Varios (Floral, Ámbar)");
            k1.setNotasSalida("Selección de salida");
            k1.setNotasCorazon("Selección de corazón");
            k1.setNotasFondo("Selección de fondo");
            k1.setPerfilOlfativo("Una colección exclusiva de fragancias sumamente elegantes y sofisticadas: Good Girl, Coco Mademoiselle, Miss Dior, J'adore y Valentino Donna.");
            k1.setIdealPara("Mujeres elegantes, citas románticas, uso diario sofisticado");
            k1.setGenero("Mujer");
            k1.setPrecio(180000.0);
            k1.setStock(50);
            k1.setEstadoDisponibilidad("Disponible");
            k1.setImagenUrl("https://images.unsplash.com/photo-1541643600914-78b084683601?q=80&w=800&auto=format&fit=crop");
            k1.setSku("KIT-FEM-ELEG");
            k1.setNotasOlfativas(Arrays.asList("Elegancia", "Sofisticación", "Lujo"));
            list.add(k1);

            // 36: Kit Dulces y Gourmand
            Perfume k2 = new Perfume();
            k2.setNombre("Kit Dulces y Gourmand");
            k2.setMarca("Profumi Selection");
            k2.setFamiliaOlfativa("Dulce Gourmand");
            k2.setNotasSalida("Notas dulces");
            k2.setNotasCorazon("Notas cremosas");
            k2.setNotasFondo("Notas de postres");
            k2.setPerfilOlfativo("Para quienes adoran los aromas irresistibles y golosos. Incluye Yara Tradicional, Yara Elixir, Scandal, Thank U, Next y Toy Bubble Gum.");
            k2.setIdealPara("Amantes del dulce, salidas divertidas, personalidad alegre");
            k2.setGenero("Mujer");
            k2.setPrecio(160000.0);
            k2.setStock(50);
            k2.setEstadoDisponibilidad("Disponible");
            k2.setImagenUrl("https://images.unsplash.com/photo-1592945403244-b3fbafd7f539?q=80&w=800&auto=format&fit=crop");
            k2.setSku("KIT-DUL-GOUR");
            k2.setNotasOlfativas(Arrays.asList("Vainilla", "Coco", "Caramelo"));
            list.add(k2);

            // 37: Kit Frescura Diaria
            Perfume k3 = new Perfume();
            k3.setNombre("Kit Frescura Diaria");
            k3.setMarca("Profumi Selection");
            k3.setFamiliaOlfativa("Cítrico Floral Fresco");
            k3.setNotasSalida("Notas frescas cítricas");
            k3.setNotasCorazon("Flores suaves");
            k3.setNotasFondo("Almizcles limpios");
            k3.setPerfilOlfativo("La selección perfecta para el día a día. Fresca, ligera y limpia: Versace Bright Crystal, Light Blue, Omnia Crystalline, Omnia Coral y Chanel Chance Eau Tendre.");
            k3.setIdealPara("Uso diario, oficina, climas cálidos");
            k3.setGenero("Mujer");
            k3.setPrecio(170000.0);
            k3.setStock(50);
            k3.setEstadoDisponibilidad("Disponible");
            k3.setImagenUrl("https://images.unsplash.com/photo-1594035910387-fea47794261f?q=80&w=800&auto=format&fit=crop");
            k3.setSku("KIT-FRE-DIAR");
            k3.setNotasOlfativas(Arrays.asList("Fresco", "Día a día", "Limpio"));
            list.add(k3);

            // 38: Kit Noches Inolvidables
            Perfume k4 = new Perfume();
            k4.setNombre("Kit Noches Inolvidables");
            k4.setMarca("Profumi Selection");
            k4.setFamiliaOlfativa("Ámbar Oriental");
            k4.setNotasSalida("Especias finas");
            k4.setNotasCorazon("Flores exóticas");
            k4.setNotasFondo("Cálido y duradero");
            k4.setPerfilOlfativo("Aromas intensos y misteriosos para destacar en la noche: Good Girl Blush Elixir, Fame The Couture, Amber Oud Gold Edition, Valentino Donna y Coco Mademoiselle.");
            k4.setIdealPara("Noche, eventos de gala, salidas especiales");
            k4.setGenero("Uniséx");
            k4.setPrecio(190000.0);
            k4.setStock(50);
            k4.setEstadoDisponibilidad("Disponible");
            k4.setImagenUrl("https://images.unsplash.com/photo-1595425970377-c9703cf48b6d?q=80&w=800&auto=format&fit=crop");
            k4.setSku("KIT-NOC-INOL");
            k4.setNotasOlfativas(Arrays.asList("Noche", "Intenso", "Estela"));
            list.add(k4);

            // 39: Kit Hombre Elegante
            Perfume k5 = new Perfume();
            k5.setNombre("Kit Hombre Elegante");
            k5.setMarca("Profumi Selection");
            k5.setFamiliaOlfativa("Amaderado Especiado");
            k5.setNotasSalida("Cítricos y menta");
            k5.setNotasCorazon("Especias masculinas");
            k5.setNotasFondo("Maderas nobles");
            k5.setPerfilOlfativo("Sofisticación y masculinidad clásica y moderna: Bleu de Chanel, Sauvage Elixir, Valentino Uomo Born in Roma Intense, Toy Boy y 212 Men NYC.");
            k5.setIdealPara("Oficina, reuniones ejecutivas, ocasiones formales");
            k5.setGenero("Hombre");
            k5.setPrecio(180000.0);
            k5.setStock(50);
            k5.setEstadoDisponibilidad("Disponible");
            k5.setImagenUrl("https://images.unsplash.com/photo-1523293182086-7651a899d37f?q=80&w=800&auto=format&fit=crop");
            k5.setSku("KIT-HOM-ELEG");
            k5.setNotasOlfativas(Arrays.asList("Elegancia", "Maderas", "Masculino"));
            list.add(k5);

            // 40: Kit Hombre para Fiesta
            Perfume k6 = new Perfume();
            k6.setNombre("Kit Hombre para Fiesta");
            k6.setMarca("Profumi Selection");
            k6.setFamiliaOlfativa("Aromática Especiada Dulce");
            k6.setNotasSalida("Menta, manzana y limón");
            k6.setNotasCorazon("Haba tonka y geranio");
            k6.setNotasFondo("Vainilla y maderas");
            k6.setPerfilOlfativo("Fragancias vibrantes y proyectoras para la noche de fiesta: Versace Eros Flame, 212 VIP Black, Invictus, Lacoste Rouge y Blue Seduction.");
            k6.setIdealPara("Fiestas, bares, salidas nocturnas");
            k6.setGenero("Hombre");
            k6.setPrecio(175000.0);
            k6.setStock(50);
            k6.setEstadoDisponibilidad("Disponible");
            k6.setImagenUrl("https://images.unsplash.com/photo-1547887537-6158d64c35b3?q=80&w=800&auto=format&fit=crop");
            k6.setSku("KIT-HOM-FIES");
            k6.setNotasOlfativas(Arrays.asList("Fiesta", "Seducción", "Proyección"));
            list.add(k6);

            // 41: Kit Más Vendidos
            Perfume k7 = new Perfume();
            k7.setNombre("Kit Más Vendidos");
            k7.setMarca("Profumi Selection");
            k7.setFamiliaOlfativa("Varios (Favoritos)");
            k7.setNotasSalida("Notas más populares");
            k7.setNotasCorazon("Acordes preferidos");
            k7.setNotasFondo("Estela legendaria");
            k7.setPerfilOlfativo("Nuestros best sellers absolutos reunidos en una sola caja de descubrimiento: Sauvage, Baccarat Rouge, Good Girl, Bleu de Chanel y Club de Nuit.");
            k7.setIdealPara("Regalos, principiantes en perfumería, ir a la segura");
            k7.setGenero("Uniséx");
            k7.setPrecio(185000.0);
            k7.setStock(50);
            k7.setEstadoDisponibilidad("Disponible");
            k7.setImagenUrl("https://images.unsplash.com/photo-1592945403244-b3fbafd7f539?q=80&w=800&auto=format&fit=crop");
            k7.setSku("KIT-MAS-VEND");
            k7.setNotasOlfativas(Arrays.asList("Best Seller", "Popular", "Favoritos"));
            list.add(k7);

                perfumeRepository.saveAll(list);
                System.out.println("Base de datos de perfumes inicializada con exactamente 34 perfumes de la hoja.");
            } else {
                System.out.println("Base de datos de perfumes ya contiene datos. Omitiendo sembrado.");
            }

            if (usuarioRepository.findByEmail("admin@profumi.com").isEmpty()) {
                com.profumi.profumi.models.Usuario admin = new com.profumi.profumi.models.Usuario(
                    "admin@profumi.com", 
                    "Administrador Principal", 
                    passwordEncoder.encode("admin123"), 
                    com.profumi.profumi.models.Rol.ADMIN, 
                    "SISTEMA"
                );
                usuarioRepository.save(admin);
                System.out.println("Admin principal creado.");
            }

            if (usuarioRepository.findByEmail("asesor@profumi.com").isEmpty()) {
                com.profumi.profumi.models.Usuario asesor = new com.profumi.profumi.models.Usuario(
                    "asesor@profumi.com", 
                    "Asesor Experto", 
                    passwordEncoder.encode("asesor123"), 
                    com.profumi.profumi.models.Rol.ASESOR, 
                    "SISTEMA"
                );
                usuarioRepository.save(asesor);
                System.out.println("Asesor experto creado.");
            }
        };
    }
}