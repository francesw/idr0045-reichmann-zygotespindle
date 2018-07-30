/*
 *------------------------------------------------------------------------------
 *  Copyright (C) 2018 University of Dundee. All rights reserved.
 *
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 *------------------------------------------------------------------------------
 */

package annotations;

import static annotations.CSVTools.*;

/**
 * Generates the filePaths.tsv and annotation.csv from the provided assays.txt file.
 * 
 * @author Dominik Lindner &nbsp;&nbsp;&nbsp;&nbsp; <a
 *         href="mailto:d.lindner@dundee.ac.uk">d.lindner@dundee.ac.uk</a>
 */
public class IDR0045Workflow {

    public static void main(String[] args) throws Exception {
        
        // ====================
        // Parameters
        
        final String path = "/uod/idr/filesets/idr0045-reichmann/20180731-renamed";
        
        final String assayFile = "/Users/dlindner/Repositories/idr0045-reichmann/experimentA/idr0045-experimentA-assays.txt";
        
        final String filePathColumn = "Comment [Image File Path]";
        final String datasetNameColumn = "Assay Name";
        
        final String filePathsFile = "/Users/dlindner/Repositories/idr0045-reichmann/experimentA/idr0045-experimentA-filePaths.tsv";
        final String annotationFile = "/Users/dlindner/Repositories/idr0045-reichmann/experimentA/idr0045-experimentA-annotation.csv";
        final String fileMappingFile = "/Users/dlindner/Repositories/idr0045-reichmann/experimentA/filenameImageMapping.csv";
        
        // =====================
        
        final char TSV = '\t';
        final char CSV = ',';
        
        String input = readFile(assayFile);
        
        /**
         *  create filePath.tsv
         */
        
        // Extract the column with the dataset name and image file paths
        int indexFilePath = getColumnIndex(input, filePathColumn, TSV);
        int indexDataset = getColumnIndex(input, datasetNameColumn, TSV);
        String filePathsContent = extractColumns(input, new int[]{indexDataset, indexFilePath}, TSV);
        
        // have to remove "Reichman-2018/"
        filePathsContent = process(filePathsContent, 1, TSV, content -> {
            return content.substring(content.indexOf('/')+1);
        });
        
        // Prefix the dataset name with the /uod/idr/filesets/... path to get the absolute path
        filePathsContent = prefixColumn(filePathsContent, 0, TSV, "Dataset:name:", null);
        
        // Prefix the (relative) image file paths with the /uod/idr/filesets/... path to get the absolute path
        filePathsContent = prefixColumn(filePathsContent, 1, TSV, path+"/", null);
        
        // remove the header line
        filePathsContent = removeRow(filePathsContent, 0);
        
        // save the filePaths.tsv file
        writeFile(filePathsFile, filePathsContent);
        
        
        /**
         * create annotation.csv
         */
        
        // Convert the tsv assays file to a csv file
        String annotationContent = format(input, TSV, CSV);
        
        // Remove empty columns
        annotationContent = removeEmptyColumns(annotationContent, CSV);
        
        // Rename "Assays" column to "Dataset Name"
        int index = getColumnIndex(annotationContent, datasetNameColumn, CSV);
        annotationContent = renameColumn(annotationContent, index, "Dataset Name", CSV);
        
        // Merge the file -> image mapping to add the Image Name
        String fileMapping = readFile(fileMappingFile);
        annotationContent = mergeColumns(annotationContent, fileMapping, new String[]{"Dataset Name", "Image File"}, CSV);
        
        // There are files which are just imported as they are, fill these in the "Image Name" column
        index = getColumnIndex(annotationContent, "Image Name", CSV);
        int index2 = getColumnIndex(annotationContent, "Image File", CSV);
        annotationContent = copyContent(annotationContent, index2, index, false, CSV);
        
        // Move the image name column to the front
        index = getColumnIndex(annotationContent, "Image Name", CSV);
        annotationContent = swapColumns(annotationContent, index, 1, CSV);
        
        // Move the dataset name column to the front
        index = getColumnIndex(annotationContent, "Dataset Name", CSV);
        annotationContent = swapColumns(annotationContent, index, 0, CSV);
        
        // Make sure that there's only one entry per image
        annotationContent = shrink(annotationContent, new int[] {0,1}, new int[] {14}, CSV);
        
        // Delete the image file column
        index = getColumnIndex(annotationContent, "Image File", CSV);
        annotationContent = removeColumn(annotationContent, index, CSV);
        
        // Make sure only ; is used as separator in the Channels column
        index = getColumnIndex(annotationContent, "Channels", CSV);
        annotationContent = process(annotationContent, index, CSV, content -> {
            return content.replace(',', ';');
        });
        
        // Finally save the annotion.csv file
        writeFile(annotationFile, annotationContent);
        
    }
}
