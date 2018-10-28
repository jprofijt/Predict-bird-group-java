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
    private final String modelFile = "testdata/RandomForest_model.model";
    private String unknownFile;
    private String outputFile;
    public boolean CSV;




    public void start() {
        String datafile = "testdata/Bonelenghts.arff";


        try {
            Instances instances = loadArff(datafile);
            printInstances(instances);
            RandomForest randomForest = buildClassifier(instances);
            saveClassifier(randomForest);
            RandomForest fromFile = loadClassifier();
            Instances unknownInstances = loadArff(unknownFile);
            System.out.println("\nunclassified unknownInstances = \n" + unknownInstances);
            classifyNewInstance(fromFile, unknownInstances);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void classifyNewInstance(RandomForest tree, Instances unknownInstances) throws Exception {
        // create copy
        Instances labeled = new Instances(unknownInstances);
        // label instances
        for (int i = 0; i < unknownInstances.numInstances(); i++) {
            double clsLabel = tree.classifyInstance(unknownInstances.instance(i));
            labeled.instance(i).setClassValue(clsLabel);
        }
        //System.out.println("\nNew, labeled = \n" + labeled);

        if (CSV){
            writeToCSV(labeled);
        } else {
            writeToArff(labeled);
        }
    }

    private RandomForest loadClassifier() throws Exception {
        // deserialize model
        return (RandomForest) weka.core.SerializationHelper.read(modelFile);
    }

    private void saveClassifier(RandomForest randomForest) throws Exception {
        //post 3.5.5
        // serialize model
        weka.core.SerializationHelper.write(modelFile, randomForest);

        // serialize model pre 3.5.5
//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(modelFile));
//        oos.writeObject(j48);
//        oos.flush();
//        oos.close();
    }

    private RandomForest buildClassifier(Instances instances) throws Exception {
        int MaxDepth = 15;
        RandomForest tree = new RandomForest();         // new instance of tree
        tree.setMaxDepth(MaxDepth);
        tree.buildClassifier(instances);
        return tree;
    }

    private void printInstances(Instances instances) {
        int numAttributes = instances.numAttributes();

        for (int i = 0; i < numAttributes; i++) {
            System.out.println("attribute " + i + " = " + instances.attribute(i));
        }

        System.out.println("class index = " + instances.classIndex());
//        Enumeration<Instance> instanceEnumeration = instances.enumerateInstances();
//        while (instanceEnumeration.hasMoreElements()) {
//            Instance instance = instanceEnumeration.nextElement();
//            System.out.println("instance " + instance. + " = " + instance);
//        }

        //or
        int numInstances = instances.numInstances();
        for (int i = 0; i < numInstances; i++) {
            if (i == 5) break;
            Instance instance = instances.instance(i);
            System.out.println("instance = " + instance);
        }
    }

    private Instances loadArff(String datafile) throws IOException {
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
        this.unknownFile = datafile;
        return;

    }

    public void setOutputFile(String datafile) {
        this.outputFile = datafile;
        return;
    }

    private void writeToArff(Instances labels) throws IOException{
        ArffSaver saver = new ArffSaver();
        saver.setInstances(labels);
        saver.setFile(new File(this.outputFile));
        saver.writeBatch();
//        FileWriter writer = new FileWriter(this.outputFile);
//        writer.write(labels.toString());
//        writer.close();
    }
    private void writeToCSV(Instances labels) throws IOException{
        CSVSaver saver = new CSVSaver();
        saver.setInstances(labels);
        saver.setFile(new File(this.outputFile));
        saver.writeBatch();

    }
}

