/*
 * Copyright (c) 2018 Jouke Profijt
 * Licensed under GPLv3. See LICENSE
 */
package nl.bioinf.jprofijt.BirdClassifier.BoneClassification;



import nl.bioinf.jprofijt.BirdClassifier.UserOptionsProcessing.OptionsProvider;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BirdClassifier {
    private final String modelFile = "RandomForest_model.model";
    private String unknownFile;
    private String outputFile;
    public boolean CSV;




    public void start() {
        /**
         * Starts classification with model file & unknown datafile.
         */

        try {
            // try to open model file, should be delivered with application.
            RandomForest fromFile = loadClassifier();
            Instances unknownInstances = loadArff(unknownFile);
            classifyNewInstance(fromFile, unknownInstances);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void classifyNewInstance(RandomForest tree, Instances unknownInstances) throws Exception {
        /**
         * classifies all unknown instances with the loaded tree model.
         *
         * @tree RandomForest model tree
         * @unknownInstances Instances to be classified
         */
        // create copy
        Instances labeled = new Instances(unknownInstances);
        // label instances
        for (int i = 0; i < unknownInstances.numInstances(); i++) {
            double clsLabel = tree.classifyInstance(unknownInstances.instance(i));
            labeled.instance(i).setClassValue(clsLabel);
        }
        //System.out.println("\nNew, labeled = \n" + labeled);

        if (CSV){
            // if the csv option is given output should be written in a csv format
            writeToCSV(labeled);
        } else {
            // else store results as normal. As arff.
            writeToArff(labeled);
        }
    }

    private RandomForest loadClassifier() throws Exception {
        /**
         * loads RandomForest tree from model file.
         * @return RandomForest
         */
        return (RandomForest) weka.core.SerializationHelper.read(modelFile);
    }

    private Instances loadArff(String datafile) throws IOException {
        /**
         * reads in arff file and converts instances to a Instances object.
         * @return Instances
         */
        try {
            DataSource source = new DataSource(datafile);
            Instances data = source.getDataSet();
            // setting class attribute if the data format does not provide this information
            // For example, the XRFF format saves the class attribute information as well
            if (data.classIndex() == -1)
                data.setClassIndex(data.numAttributes() - 1);
            return data;
        } catch (Exception e) {
            throw new IOException("could not read from file");
        }
    }
    public void setUnknownFile(String datafile) {
        /**
         * Set file that needs to be classified
         */
        this.unknownFile = datafile;
        return;

    }

    public void setOutputFile(String datafile) {
        /**
         * Sets output file path
         */
        this.outputFile = datafile;
        return;
    }

    private void writeToArff(Instances labels) throws IOException{
        /**
         * if the default ouput is requested(or a arff file is given),
         * call this function to write the instances to an file of arff format.
         *
         * @labels Instances to be written
         */
        ArffSaver saver = new ArffSaver();
        saver.setInstances(labels);
        saver.setFile(new File(this.outputFile));
        saver.writeBatch();
    }
    private void writeToCSV(Instances labels) throws IOException{
        /**
         * if csv is explisitly requested by program or user,
         * call this function to write to a file of csv format
         *
         * @labels Instances to be written
         */
        CSVSaver saver = new CSVSaver();
        saver.setInstances(labels);
        saver.setFile(new File(this.outputFile));
        saver.writeBatch();

    }
}

