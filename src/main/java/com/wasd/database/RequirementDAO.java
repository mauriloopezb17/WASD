package com.wasd.database;

import java.sql.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.wasd.database.ConnectionDB;

import com.wasd.models.Requirement;
import com.wasd.models.Windows;
import com.wasd.models.Linux;
import com.wasd.models.Mac;
import com.wasd.models.SystemComponent;

public class RequirementDAO {

    // Funcion read para SQL en el caso de Requirement (Este nos devuelve un arraylist con cada clase hija creada y sus respectivos valores)
    public ArrayList<Requirement> searchRequirement(int idGame) throws SQLException {

        String sql = "SELECT r.idRequirement, r.descripcion, os.name AS osName, c.idComponent, c.name AS componentName, ct.typeName AS componentType " +
                    "FROM REQUIREMENTS r JOIN OPERATIVE_SYSTEM os ON r.idSo = os.idSo JOIN COMPONENTS c ON r.idComponent = c.idComponent JOIN COMPONENT_TYPES ct   ON c.idType = ct.idType " +
                    "WHERE r.idGame = ? ORDER BY os.name, ct.typeName";

        Map<String, Requirement> map = new HashMap<>();

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idGame);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String os = rs.getString("osName");
                String type = rs.getString("componentType");
                String name = rs.getString("componentName");

                Requirement req = map.get(os);
                if (req == null) {
                    req = switch (os) {
                        case "Windows" -> new Windows();
                        case "Linux"   -> new Linux();
                        case "Mac"     -> new Mac();
                        default        -> null;
                    };
                    if (req == null) continue;

                    req.setDescripcion(rs.getString("descripcion"));
                    map.put(os, req);
                }

                SystemComponent comp = new SystemComponent(rs.getInt("idComponent"), name, type);
                req.addComponent(comp);

                switch (type.toLowerCase()) {
                    case "ram" -> req.setMemory(name);
                    case "processor" -> req.setProcessor(name);
                    case "graphics" -> req.setGraphics(name);
                    case "storage" -> req.setStorage(name);
                    case "osversion" -> {
                        if (req instanceof Windows w) {
                            w.setOsVersion(name);
                        }
                    }
                    case "distro" -> {
                        if (req instanceof Linux l) {
                            l.setDistro(name);
                        }
                    }
                    case "kernel" -> {
                        if (req instanceof Linux l) {
                            l.setKernelVersion(name);
                        }
                    }
                    case "architecture" -> {
                        if (req instanceof Mac m) {
                            m.setArchitecture(name);
                        }
                    }
                    case "macosversion" -> {
                        if (req instanceof Mac m) {
                            m.setMacOsVersion(name);
                        }
                    }
                }
            }
        }

        return new ArrayList<>(map.values());
    }

    // Funcion para insertar los requerimientos de un juego
    public void insertRequirements(int idGame, Requirement req) throws SQLException {

        String sqlRequirement = "INSERT INTO REQUIREMENTS(idGame, idSo, idComponent, descripcion) VALUES (?, ?, ?, ?)";

        ComponentDAO componentDAO = new ComponentDAO();
        int idSo = searchName(req.getPlatform());     

        if (idSo == -1) {
            System.err.println("Sistema operativo no reconocido: " + req.getPlatform());
            return;
        }

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sqlRequirement)) {

            for (SystemComponent comp : req.getComponents()) {
                // asegura que el componente exista en COMPONENTS
                int idComponent = componentDAO.searchComponent(comp.getName(), comp.getTypeName());

                // insertamos el requerimiento
                stmt.setInt(1, idGame);
                stmt.setInt(2, idSo);
                stmt.setInt(3, idComponent);
                stmt.setString(4, req.getDescripcion());
                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }

    // Busca el id del sistema operativo por su nombre
    private int searchName(String osName) {
        String sql = "SELECT idSo FROM OPERATIVE_SYSTEM WHERE name = ?";
        
        int idSo = -1;

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, osName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                idSo = rs.getInt("idSo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idSo;
    }


}

