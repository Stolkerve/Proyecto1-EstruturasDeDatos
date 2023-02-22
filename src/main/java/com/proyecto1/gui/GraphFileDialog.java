package com.proyecto1.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import com.proyecto1.containers.Grafo;
import com.proyecto1.containers.Vector;
import com.proyecto1.models.Edge;
import com.proyecto1.models.Product;
import com.proyecto1.models.Wearhouse;

/**
 * Estado de carga del arhcivo de grafos
 * 
 * @author sebas
 */
enum FileState {
    Init,
    BeginWearhouse,
    OnLoadProducts,
    OnGraph
};

/**
 * Clase con metodos estaticos para cargar y guardar el archivo de grafos y
 * alamacenes
 * 
 * @author sebas
 */
public class GraphFileDialog {

    /**
     * Metodo estatico para cargar el archivo de grafos y almacenes
     */
    public static void loadFileDialog() {
        JFileChooser fileDialog = new JFileChooser("./", FileSystemView.getFileSystemView());
        fileDialog.setAcceptAllFileFilterUsed(false);
        fileDialog.setDialogTitle("Selectciona el archivo de guardado de almacenes");
        fileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Solo archivos .txt", "txt"));
        int res = fileDialog.showOpenDialog(null);

        if (res != JFileChooser.CANCEL_OPTION) {
            try {
                Scanner scanner = new Scanner(fileDialog.getSelectedFile());
                FileState state = FileState.Init;

                Pattern wearhouseIdPattern = Pattern.compile("(Almacen) ([a-zA-Z0-9])+(:)");
                Pattern wearhouseProductPattern = Pattern.compile("([a-zA-Z0-9]+),([0-9]+)(;?)");
                Pattern graphRoutePattern = Pattern.compile("([a-zA-Z0-9]+),([a-zA-Z0-9]+),([0-9]+)");

                Vector<Wearhouse> wearhouses = new Vector<>();
                Wearhouse wearhouse = null;

                int productIdCounter = 0;

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    switch (state) {
                        case Init: {
                            if (!line.equals("Almacenes;")) {
                                JOptionPane.showMessageDialog(fileDialog,
                                        "No se puede leer el archivo. Ingrese un archivo valido", "ERROR",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            state = FileState.BeginWearhouse;
                            break;
                        }
                        case BeginWearhouse: {
                            // Ver si la linea es un almacen o son los grafos
                            // La posicion 2 del match tiene el nombre del almacen
                            Matcher match = wearhouseIdPattern.matcher(line);
                            if (match.matches()) {
                                String name = match.group(2);
                                for (Wearhouse w : wearhouses) {
                                    if (w.name.equals(name)) {
                                        JOptionPane.showMessageDialog(fileDialog,
                                            String.format("Ya existe un almacen con nombre %. Se procede a ignoralo", name), "ERROR",
                                            JOptionPane.ERROR_MESSAGE);
                                        continue;
                                    }
                                }
                                wearhouse = new Wearhouse(name);
                                wearhouses.pushBack(wearhouse);
                                productIdCounter = 0;
                            } else {
                                // No son grafos?
                                if (!line.equals("Rutas;")) {
                                    JOptionPane.showMessageDialog(fileDialog,
                                            "Error leyendo el archivo. Nombre del almacen invalido", "ERROR",
                                            JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                state = FileState.OnGraph;
                                continue;
                            }
                            state = FileState.OnLoadProducts;
                            break;
                        }
                        case OnLoadProducts: {
                            // Obtener los productos
                            Matcher match = wearhouseProductPattern.matcher(line);
                            if (match.matches()) {
                                String name = match.group(1);
                                int stock = Integer.parseInt(match.group(2));
                                wearhouse.products.pushBack(new Product(name, productIdCounter, stock));

                                productIdCounter++;
                                String endChar = match.group(3);
                                if (endChar.length() != 0) {
                                    state = FileState.BeginWearhouse;
                                }
                            }
                            break;
                        }
                        case OnGraph: {
                            Matcher match = graphRoutePattern.matcher(line);
                            if (match.matches()) {
                                String originNodeName = match.group(1);
                                String destinationNodeName = match.group(2);
                                int distance = Integer.parseInt(match.group(3));

                                for (Wearhouse w : wearhouses) {
                                    if (w.name.equals(originNodeName)) {
                                        for (Wearhouse w2 : wearhouses) {
                                            if (w2.name.equals(destinationNodeName)) {
                                                w.edges.pushBack(new Edge(w, w2, distance));
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
                scanner.close();
                // Si se llego aqui significa que se abrio el archivo y no salto ningun error de
                // carga

                Grafo instace = Grafo.getInstance();

                if (!instace.iniciado) {
                    instace.iniciado = true;
                    instace.almacenes.reserve(wearhouses.capacity());
                }
                else {
                    instace.almacenes.clear();
                }
                for (Wearhouse w : wearhouses)
                    instace.almacenes.pushBack(w);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(fileDialog,
                        "No se pudo abrir archivo", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

    public static boolean saveFileDialog() {
        JFileChooser fileDialog = new JFileChooser("./", FileSystemView.getFileSystemView());
        fileDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int res = fileDialog.showOpenDialog(null);

        if (res != JFileChooser.CANCEL_OPTION) {
            String msg = "Nombre del archivo";
            String name = JOptionPane.showInputDialog(fileDialog, msg);

            if (name == null)
                return false;

            while (name.length() == 0)
                name = JOptionPane.showInputDialog(fileDialog, msg);

            File outputFile = new File(name + ".txt");
            if (outputFile.exists()) {
                int r = JOptionPane.showConfirmDialog(fileDialog, "El archivo ya existe, deseas sobre escribirlo?");
                if (r != JOptionPane.OK_OPTION) return false;
            }
            try (FileWriter output = new FileWriter(outputFile)) {
                output.write("Almacenes;\n");
                String rutas = "";
                for (Wearhouse w : Grafo.getInstance().almacenes) {
                    output.write(String.format("Almacen %s:\n", w.name));
                    int i = 0;
                    for (Product p : w.products) {
                        if (i == w.products.size() - 1) {
                            output.write(String.format("%s,%d;\n", p.name, p.stock));
                            continue;
                        }
                        output.write(String.format("%s,%d\n", p.name, p.stock));
                        i++;
                    }
                    for (Edge e : w.edges)
                        rutas += String.format("%s,%s,%d\n", e.almacen.name, e.almacenVecino.name, e.distancia);
                }
                output.write("Rutas;\n" + rutas);
                output.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(fileDialog,
                        "No se pudo crear el archivo", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }
}
