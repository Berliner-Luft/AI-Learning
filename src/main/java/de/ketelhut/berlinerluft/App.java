package de.ketelhut.berlinerluft;

import java.util.Arrays;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.Perceptron;
import org.neuroph.util.TransferFunctionType;

/**
 * 
 * Frist draft of an AI
 * 
 * @author robertketelhut
 *
 */

public class App {

	public static void main(String[] args) {
		int suc = 0;
		int fail = 0;
		System.out.println("Start");
		// #input values , #output values, TransferFunctionType
		NeuralNetwork<?> neuralNetwork = new Perceptron(2, 1, TransferFunctionType.SGN);
		
		// create training set
		DataSet trainingSet = new DataSet(2, 1);

		// add training data to training set (logical OR function)
		// first array is input, second array output
		trainingSet.addRow(new DataSetRow(new double[] { 0, 0 }, new double[] { 0 }));
		trainingSet.addRow(new DataSetRow(new double[] { 1, 1 }, new double[] { 1 }));
		trainingSet.addRow(new DataSetRow(new double[] { 1, 0 }, new double[] { 1 }));
		trainingSet.addRow(new DataSetRow(new double[] { 1, 1 }, new double[] { 1 }));
		neuralNetwork.learnInNewThread(trainingSet);
		System.out.println(Arrays.toString(neuralNetwork.getWeights()));
		// 1000 test question to the network
		for (int i = 0; i < 10000; i++) {
			// 0.1 => fail
			// 0.2 => fail
			// >0.3 => suc
			neuralNetwork.setInput(0.3, 0);
			// calculate network
			neuralNetwork.calculate();
			// get network output
			double[] networkOutput = neuralNetwork.getOutput();
			double output = networkOutput[0];
			if (output == 1.0) {
				suc++;
			} else { 
				System.out.println(output);
				fail++;
			}
			neuralNetwork.stopLearning();
		}
		System.out.println("Succ:" + suc + "-- fail:" + fail);
		System.out.println("end");
	}
}
