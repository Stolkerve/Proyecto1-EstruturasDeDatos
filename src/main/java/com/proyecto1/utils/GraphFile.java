package com.proyecto1.utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

enum FileState {
    Init,
    BeginWearhouse,
    OnLoadProducts,
    OnGraph
};

public class GraphFile {
    public static void loadFileDialog() {
        var fileDialog = new JFileChooser("./", FileSystemView.getFileSystemView());
        fileDialog.setAcceptAllFileFilterUsed(false);
        fileDialog.setDialogTitle("Selectciona el archivo de guardado de almacenes");
        fileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Solo archivos .txt", "txt"));
        var res = fileDialog.showOpenDialog(null);

        if (res != JFileChooser.CANCEL_OPTION) {
            try {
                var scanner = new Scanner(fileDialog.getSelectedFile());
                var state = FileState.Init;

                var wearhouseIdPattern = Pattern.compile("(Almacen) ([a-zA-Z0-9])+(:)");
                var wearhouseProductPattern = Pattern.compile("([a-zA-Z0-9]+),([0-9]+)(;?)");
                var graphRoutePattern = Pattern.compile("([a-zA-Z0-9]+),([a-zA-Z0-9]+),([0-9]+)");

                while (scanner.hasNextLine()) {
                    var line = scanner.nextLine();
                    switch (state) {
                        case Init -> {
                            if (!line.equals("Almacenes;")) {
                                JOptionPane.showMessageDialog(null, "No se puede leer el archivo. Ingrese un archivo valido", "ERROR", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            state = FileState.BeginWearhouse;
                        }
                        case BeginWearhouse -> {
                            // Ver si la linea es un almacen o son los grafos
                            // La posicion 2 del match tiene el ID del almacen
                            var match = wearhouseIdPattern.matcher(line);
                            if (match.matches()) {
                                var sID = match.group(2);
                                try {
                                    var id = Integer.parseInt(sID, 16);
                                } catch (NumberFormatException e) {
                                }
                            } else {
                                // No son grafos?
                                if (!line.equals("Rutas;")) {
                                    JOptionPane.showMessageDialog(null, "Error leyendo el archivo. Nombre del almacen invalido", "ERROR", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                state = FileState.OnGraph;
                                continue;
                            }
                            state = FileState.OnLoadProducts;
                        }
                        case OnLoadProducts -> {
                            // Obtener los productos
                            var match = wearhouseProductPattern.matcher(line);
                            if (match.matches()) {
                                var name = match.group(1);
                                var amount = Integer.parseInt(match.group(2));
                                var endChar = match.group(3);

                                if (endChar.length() != 0) {
                                    state = FileState.BeginWearhouse;
                                }
                            }
                        }
                        case OnGraph -> {
                            var match = graphRoutePattern.matcher(line);
                            if (match.matches()) {
                                var graphNode1 = match.group(1);
                                var graphNode2 = match.group(2);
                                var distante = match.group(3);
                            }
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + state);
                    }
                }
                // Ir al menu principal
                scanner.close();
            } catch (FileNotFoundException e) {
            }
        }
    }

    public static void saveFileDialog() {
    }
}
